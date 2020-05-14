
package com.meetingapp.meetingapp.repository;

import com.meetingapp.meetingapp.exceptions.ApiRequestException;
import com.meetingapp.meetingapp.helpers.AuthenticationConstants;
import com.meetingapp.meetingapp.models.ExtendAppointmentTime;
import com.meetingapp.meetingapp.models.ReserveRoomAppointment;
import com.meetingapp.meetingapp.models.RoomAppointment;
import com.meetingapp.meetingapp.repository.interfaces.ExchangeRepository;
import com.meetingapp.meetingapp.models.UpdateAppointmentTime;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.availability.AvailabilityData;
import microsoft.exchange.webservices.data.core.enumeration.availability.FreeBusyViewType;
import microsoft.exchange.webservices.data.core.enumeration.availability.MeetingAttendeeType;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.ResolveNameSearchLocation;
import microsoft.exchange.webservices.data.core.enumeration.service.ConflictResolutionMode;
import microsoft.exchange.webservices.data.core.enumeration.service.SendInvitationsOrCancellationsMode;
import microsoft.exchange.webservices.data.core.response.AttendeeAvailability;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.schema.AppointmentSchema;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.misc.availability.AttendeeInfo;
import microsoft.exchange.webservices.data.misc.availability.AvailabilityOptions;
import microsoft.exchange.webservices.data.misc.availability.GetUserAvailabilityResults;
import microsoft.exchange.webservices.data.misc.availability.TimeWindow;
import microsoft.exchange.webservices.data.property.complex.Attendee;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.EmailAddressCollection;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import microsoft.exchange.webservices.data.property.complex.availability.CalendarEvent;
import microsoft.exchange.webservices.data.search.CalendarView;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Primary
public class ExchangeApi implements ExchangeRepository {
    private ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
    private static final Logger LOGGER = Logger.getLogger( ExchangeApi.class.getName() );

    public ExchangeApi() {
        Properties meetingAppProps = getProperties();
        ExchangeCredentials credentials = new WebCredentials(meetingAppProps.getProperty(AuthenticationConstants.USERNAME_EXCHANGE), meetingAppProps.getProperty(AuthenticationConstants.PASSWORD_EXCHANGE));
        service.setCredentials(credentials);
        String exchangeURI = "https://outlook.office365.com/EWS/Exchange.asmx";
        try{
            service.setUrl(new URI(exchangeURI));
        } catch (Exception ex){
            LOGGER.log(Level.WARNING,  "FileNotFound", ex);
        }
    }

    @Override
    public List<RoomAppointment> getAppointments() throws Exception {
        Date startDate = DateTime.now().toDate();
        Date endDate = DateTime.now().plusHours(24).toDate();
        return getRoomAppointments(startDate, endDate);
    }

    @Override
    public List<RoomAppointment> getAllAppointments() throws Exception {
        Long time = new Date().getTime();
        Date startDate = new Date(time - time % (24 * 60 * 60 * 1000));
        Date endDate = new Date(time + 24 * 60 * 60 * 1000);
        return getRoomAppointments(startDate, endDate);
    }

    private List<RoomAppointment> getRoomAppointments(Date startDate, Date endDate) throws Exception {
        CalendarFolder calendar = CalendarFolder.bind(service, WellKnownFolderName.Calendar);
        CalendarView cView = new CalendarView(startDate, endDate);

        List<RoomAppointment> roomAppointments = new ArrayList<>();
        for (Appointment appointment : calendar.findAppointments(cView)) {
            roomAppointments.add(new RoomAppointment(appointment.getId().toString(), appointment.getSubject(), appointment.getStart().getTime() / 1000, appointment.getEnd().getTime() / 1000, appointment.getLocation()));
        }
        return roomAppointments;
    }

    @Override
    public String getRoomLocation(String emailAddress) throws Exception {
        return service.resolveName(emailAddress, ResolveNameSearchLocation.DirectoryOnly, true)
                .nameResolutionCollection(0).getContact().getOfficeLocation();
        //todo fix gets index 0
    }

    @Override
    public boolean extendMeeting(ExtendAppointmentTime extendAppointmentTime, List<RoomAppointment> roomAppointments) throws Exception {
        if (roomAppointments.isEmpty()) {
            throw new ApiRequestException("No appointment found");
        }

        Appointment room = getAppointment(roomAppointments, 0);

        Date endTimeBefore = room.getEnd();
        int add = extendAppointmentTime.getExtendedTime(); 
        Date targetTime = DateUtils.addMinutes(endTimeBefore, add);

        if (roomAppointments.size() >= 2) {
            Appointment appointment2 = getAppointment(roomAppointments, 1);
            if(isOverlapping(room.getStart(), targetTime, appointment2.getStart(), appointment2.getEnd())){
                throw new ApiRequestException("Appointments overlap");
            }
        }
        room.setEnd(targetTime);
        // Update properties on the appointment with a new subject, location, an additional required attendee, and a resource.
        room.update(ConflictResolutionMode.AlwaysOverwrite, SendInvitationsOrCancellationsMode.SendToAllAndSaveCopy);

        return true;
    }

    public static boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && start2.before(end1);
    }

    @Override
    public boolean updateRoomStartAndEnd(UpdateAppointmentTime updateAppointmentTime, List<RoomAppointment> roomAppointments) throws Exception {
        if (roomAppointments.isEmpty()) {
            throw new ApiRequestException("No appointment found");
        }

        Appointment room = getAppointment(roomAppointments, 0);
        setTime(updateAppointmentTime, room);
        // Update properties on the appointment with a new subject, location, an additional required attendee, and a resource.
        room.update(ConflictResolutionMode.AlwaysOverwrite, SendInvitationsOrCancellationsMode.SendToAllAndSaveCopy);

        return true;
    }

    private Appointment getAppointment(List<RoomAppointment> roomAppointments, int appointmentNr) throws Exception {
        ItemId itemId = new ItemId(roomAppointments.get(appointmentNr).getId());
        PropertySet prop = new PropertySet(AppointmentSchema.Start, AppointmentSchema.End);
        return Appointment.bind(service, itemId, prop);
    }

    private void setTime(UpdateAppointmentTime updateAppointmentTime, Appointment room) throws Exception {
        if (updateAppointmentTime.isStartDate()){
            room.setStart(updateAppointmentTime.getUpdateTime());
        }
        else{
            room.setEnd(updateAppointmentTime.getUpdateTime());
        }
    }

    @Override
    public boolean reserveRoomAppointment(ReserveRoomAppointment reserveRoomAppointment) throws Exception {
        Date begin = reserveRoomAppointment.getStartTime();
        Date end = reserveRoomAppointment.getEndTime();

        AvailabilityOptions availabilityOptions = getAvailabilityOptions(begin, end);

        ArrayList<AttendeeInfo> attendees = new ArrayList<>();
        attendees.add(new AttendeeInfo(reserveRoomAppointment.getRoomEmail(), MeetingAttendeeType.Required, true));
        attendees.add(new AttendeeInfo(reserveRoomAppointment.getUserEmail(), MeetingAttendeeType.Required, true));
        GetUserAvailabilityResults availabilityResults = service.getUserAvailability(attendees, new TimeWindow(DateTime.now().minusHours(24).toDate(), DateTime.now().plusHours(24).toDate()), AvailabilityData.FreeBusyAndSuggestions, availabilityOptions);

        for (AttendeeAvailability available : availabilityResults.getAttendeesAvailability()){
            for (CalendarEvent existingAppointment: available.getCalendarEvents()) {
                if (isOverlapping(existingAppointment.getStartTime(),existingAppointment.getEndTime(), begin,end)){
                    throw new ApiRequestException("Appointments overlap");
                }
            }
        }
        createAppointment(reserveRoomAppointment);
        return true;
    }

    private AvailabilityOptions getAvailabilityOptions(Date begin, Date end){
        if (!end.before(begin)){
            int appointmentDuration = (int) (end.getTime() - begin.getTime())/1000/60;
            AvailabilityOptions availabilityOptions = new AvailabilityOptions();
            //TODO fix for 15 min?
            //availabilityOptions.setMeetingDuration(appointmentDuration);
            availabilityOptions.setRequestedFreeBusyView(FreeBusyViewType.FreeBusy);
            return availabilityOptions;
        }
        throw new ApiRequestException("End time is before the start time");
    }

    private void createAppointment(ReserveRoomAppointment reserveRoomAppointment) throws Exception {
        Appointment appointment = new Appointment(service);
        appointment.setLocation(reserveRoomAppointment.getLocation());
        appointment.getResources().add(reserveRoomAppointment.getRoomEmail());
        appointment.getRequiredAttendees().add(new Attendee(reserveRoomAppointment.getUserEmail()));
        appointment.setStart(reserveRoomAppointment.getStartTime());
        appointment.setEnd(reserveRoomAppointment.getEndTime());
        appointment.setSubject(reserveRoomAppointment.getSubject());
        appointment.validate();
        appointment.save(WellKnownFolderName.Calendar);
    }

    @Override
    public EmailAddressCollection getRoomLists() throws Exception {
        return service.getRoomLists();
    }

    @Override
    public Collection<EmailAddress> getRooms() {
        try {
            return service.getRooms(new EmailAddress("MeetingRooms@isaacfontys.onmicrosoft.com"));
        } catch (Exception e) {
            LOGGER.log(Level.WARNING,  "FileNotFound", e);
        }
        return null;
    }

    private void checkForEmptyList(List<RoomAppointment> roomAppointments){
        if (roomAppointments.isEmpty()){
            throw new ApiRequestException("No appointment found");
        }
    }

    private Properties getProperties() {
        Properties meetingAppProps = new Properties();
        InputStream is = null;
        try {
            is = getClass().getClassLoader().getResourceAsStream(AuthenticationConstants.PROP_FILE_NAME);
            meetingAppProps.load(is);
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING,  "FileNotFound", ex);
        }

        return meetingAppProps;
    }

}
