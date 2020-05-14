package com.meetingapp.meetingapp.services;

import com.meetingapp.meetingapp.models.*;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.EmailAddressCollection;

import java.util.Collection;
import java.util.List;

public interface ExchangeService {
    List<RoomAppointment> getAllRoomAppointments() throws Exception;
    List<RoomAppointment> getRoomAppointmentsByRoomName(String roomName, boolean includePastAppointments) throws Exception;
    RoomStatus getRoomStatus(String roomName) throws Exception;
    Collection<RoomStatus> getFreeRooms() throws Exception;
    Collection<Room> getRooms() throws Exception;
    Room getRoomDataFromEmail(String email) throws Exception;
    EmailAddressCollection getRoomLists() throws Exception;
    boolean reserveFreeRoom(ReserveRoomAppointment reserveRoomAppointment) throws Exception;
    String getEmailAddressForSpecificRoom(String roomName) throws Exception;
    boolean updateRoomStartAndEnd(UpdateAppointmentTime updateAppointmentTime) throws Exception;
    boolean extendMeeting(ExtendAppointmentTime extendAppointmentTime) throws Exception;

}
