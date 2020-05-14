package com.meetingapp.meetingapp.repository;

import com.meetingapp.meetingapp.models.*;
import com.meetingapp.meetingapp.repository.interfaces.ExchangeRepository;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.EmailAddressCollection;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MockApi implements ExchangeRepository {

    @Override
    public List<RoomAppointment> getAppointments() {
        List<RoomAppointment> roomAppointments = new ArrayList<>();
        roomAppointments.add(new RoomAppointment("1","1", DateTime.now().toDate().getTime(),DateTime.now().plusHours(1).toDate().getTime() , "Newton"));
        roomAppointments.add(new RoomAppointment("2","2", DateTime.now().toDate().getTime(),DateTime.now().plusHours(1).toDate().getTime() , "Isaac"));
        roomAppointments.add(new RoomAppointment("3","3", DateTime.now().toDate().getTime(),DateTime.now().plusHours(1).toDate().getTime() , "Wessel"));
        roomAppointments.add(new RoomAppointment("4","4", DateTime.now().toDate().getTime(),DateTime.now().plusHours(1).toDate().getTime() , "Ensar"));
        roomAppointments.add(new RoomAppointment("5","5", DateTime.now().toDate().getTime(),DateTime.now().plusHours(1).toDate().getTime() , "Room1"));
        roomAppointments.add(new RoomAppointment("6","6", DateTime.now().toDate().getTime(),DateTime.now().plusHours(1).toDate().getTime() , "Room2"));
        roomAppointments.add(new RoomAppointment("7","7", DateTime.now().toDate().getTime(),DateTime.now().plusHours(1).toDate().getTime() , "Room3"));
        roomAppointments.add(new RoomAppointment("8","8", DateTime.now().toDate().getTime(),DateTime.now().plusHours(1).toDate().getTime() , "Room4"));
        return roomAppointments;
    }

    @Override
    public List<RoomAppointment> getAllAppointments() throws Exception {
        List<RoomAppointment> roomAppointments = new ArrayList<>();
        roomAppointments.add(new RoomAppointment("1","1", DateTime.now().toDate().getTime(),DateTime.now().plusHours(1).toDate().getTime() , "Newton"));
        roomAppointments.add(new RoomAppointment("2","2", DateTime.now().toDate().getTime(),DateTime.now().plusHours(1).toDate().getTime() , "Isaac"));
        roomAppointments.add(new RoomAppointment("3","3", DateTime.now().toDate().getTime(),DateTime.now().plusHours(1).toDate().getTime() , "Wessel"));
        roomAppointments.add(new RoomAppointment("4","4", DateTime.now().toDate().getTime(),DateTime.now().plusHours(1).toDate().getTime() , "Ensar"));
        roomAppointments.add(new RoomAppointment("5","5", DateTime.now().toDate().getTime(),DateTime.now().plusHours(1).toDate().getTime() , "Room1"));
        roomAppointments.add(new RoomAppointment("6","6", DateTime.now().toDate().getTime(),DateTime.now().plusHours(1).toDate().getTime() , "Room2"));
        roomAppointments.add(new RoomAppointment("7","7", DateTime.now().toDate().getTime(),DateTime.now().plusHours(1).toDate().getTime() , "Room3"));
        roomAppointments.add(new RoomAppointment("8","8", DateTime.now().toDate().getTime(),DateTime.now().plusHours(1).toDate().getTime() , "Room4"));
        return roomAppointments;
    }

    @Override
    public String getRoomLocation(String roomName) {
        return "roomLocation";
    }

    @Override
    public boolean reserveRoomAppointment(ReserveRoomAppointment reserveRoomAppointment) throws Exception {
        return false;
    }

    @Override
    public EmailAddressCollection getRoomLists() {
        EmailAddressCollection rooms = new EmailAddressCollection();
        rooms.add("MeetingRooms@isaacfontys.onmicrosoft.com");
        return rooms;
    }

    @Override
    public Collection<EmailAddress> getRooms() {
        Collection<EmailAddress> rooms = new ArrayList<>();
        rooms.add(new EmailAddress("kungfu@isaacfontys.onmicrosoft.com"));
        rooms.add(new EmailAddress("newton@isaacfontys.onmicrosoft.com"));
        rooms.add(new EmailAddress("stern@isaacfontys.onmicrosoft.com"));
        rooms.add(new EmailAddress("babel@isaacfontys.onmicrosoft.com"));
        rooms.add(new EmailAddress("levitan@isaacfontys.onmicrosoft.com"));
        return rooms;
    }
    
    @Override
    public boolean updateRoomStartAndEnd(UpdateAppointmentTime updateAppointmentTime, List<RoomAppointment> roomAppointments) throws Exception {
        return false;
    }

    @Override
    public boolean extendMeeting(ExtendAppointmentTime extendAppointmentTime, List<RoomAppointment> roomAppointments) throws Exception {
        return false;
    }

}
