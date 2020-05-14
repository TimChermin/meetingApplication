package com.meetingapp.meetingapp.serviceimpl;

import com.meetingapp.meetingapp.models.*;
import com.meetingapp.meetingapp.repository.interfaces.ExchangeRepository;
import com.meetingapp.meetingapp.services.ExchangeService;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.EmailAddressCollection;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.meetingapp.meetingapp.models.RoomStatusEnum.*;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    private ExchangeRepository repo;

    public ExchangeServiceImpl(ExchangeRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<RoomAppointment> getAllRoomAppointments() throws Exception {
        return repo.getAppointments();
    }

    @Override
    public List<RoomAppointment> getRoomAppointmentsByRoomName(String roomName, boolean includePastAppointments) throws Exception {
        //todo refactor
        List<RoomAppointment> data;
        if(includePastAppointments){
            data = repo.getAllAppointments();
        } else {
           data = repo.getAppointments();
        }
        List<RoomAppointment> roomAppointments = new ArrayList<>();
        for(RoomAppointment appointment : data){
            if(!appointment.getLocation().equalsIgnoreCase(roomName)) continue;
            roomAppointments.add(appointment);
        }
        return roomAppointments;
    }

    @Override
    public RoomStatus getRoomStatus(String roomName) throws Exception {
        RoomAppointment firstMeeting = null;
        for(RoomAppointment appointment : repo.getAppointments()){
            if(!appointment.getLocation().equalsIgnoreCase( roomName)) continue;
            firstMeeting = appointment;
            break;
        }

        //Create an empty appointment is nothing is planned.
        if(firstMeeting == null){
            return new RoomStatus(roomName, repo.getRoomLocation(roomName), UNBOOKED);
        }

        Date now = new Date();
        long beforeStart = (firstMeeting.getStartTime() - now.getTime()/1000) / (60);
        RoomStatusEnum status;
        if(beforeStart <= 0) {
            status = OCCUPIED;
        } else if (beforeStart <= 10) {
            status = RESERVED;
        } else {
            status = FREE;
        }

        return new RoomStatus(firstMeeting.getSubject(),
                firstMeeting.getStartTime(), firstMeeting.getEndTime(),
                firstMeeting.getLocation(), repo.getRoomLocation(roomName), status);
    }

    @Override
    public Collection<RoomStatus> getFreeRooms() throws Exception {
        List<RoomAppointment> appointments = repo.getAppointments();
        Collection<Room> rooms = getRooms();
        Collection<RoomStatus> freeRooms = new ArrayList<>();
        Date now = new Date();
        for(Room room :rooms) {
            int i = 0;
            for (RoomAppointment appointment : appointments) {
                long beforeStart = (appointment.getStartTime() - now.getTime()/1000) / (60);
                if(appointment.getLocation().equalsIgnoreCase(room.getName())){
                    if(beforeStart >= 10) {
                        freeRooms.add(new RoomStatus(appointment.getSubject(), appointment.getStartTime(), appointment.getEndTime(), room.getName(), room.getLocation(), FREE));
                        break;
                    }
                    break;
                } else if (i == appointments.size()-1) {
                    freeRooms.add(new RoomStatus(room.getName(), room.getLocation(), UNBOOKED));
                    break;
                }
                i++;
            }
            if(appointments.isEmpty()) {
                freeRooms.add(new RoomStatus(room.getName(), room.getLocation(), UNBOOKED));
            }
        }
        return freeRooms;
    }



    @Override
    public Collection<Room> getRooms() throws Exception {
        List<Room> rooms = new ArrayList<>();

        for(EmailAddress room: repo.getRooms()){
            rooms.add(new Room(room.getName(), repo.getRoomLocation(room.getAddress()), room.getAddress()));
        }

        return rooms;
    }

    @Override
    public Room getRoomDataFromEmail(String email) throws Exception {
        for(EmailAddress room: repo.getRooms()){
            if(room.getAddress().equals(email)) return new Room(room.getName(), repo.getRoomLocation(room.getAddress()), room.getAddress());
        }
        return null;
    }

    @Override
    public EmailAddressCollection getRoomLists() throws Exception {
        return repo.getRoomLists();
    }

    @Override
    public synchronized boolean updateRoomStartAndEnd(UpdateAppointmentTime updateAppointmentTime) throws Exception{
        List<RoomAppointment> roomAppointments = getRoomAppointmentsByRoomName(updateAppointmentTime.getRoomName(), false);
        return repo.updateRoomStartAndEnd(updateAppointmentTime, roomAppointments);
    }

    @Override
    public synchronized boolean extendMeeting(ExtendAppointmentTime extendAppointmentTime) throws Exception{
        List<RoomAppointment> roomAppointments = getRoomAppointmentsByRoomName(extendAppointmentTime.getRoomName(), false);
        return repo.extendMeeting(extendAppointmentTime, roomAppointments);
    }

    @Override
    public boolean reserveFreeRoom(ReserveRoomAppointment reserveRoomAppointment) throws Exception {
        return repo.reserveRoomAppointment(reserveRoomAppointment);
    }
    @Override
    public String getEmailAddressForSpecificRoom(String roomName) throws Exception {
        for(EmailAddress appointment : repo.getRooms()){
            if(appointment.getName().equalsIgnoreCase(roomName)){
                return appointment.getAddress();
            }
        }
        return "";
    }
}
