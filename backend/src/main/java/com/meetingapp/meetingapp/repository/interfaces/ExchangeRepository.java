package com.meetingapp.meetingapp.repository.interfaces;

import com.meetingapp.meetingapp.models.*;
import microsoft.exchange.webservices.data.core.service.item.Contact;
import com.meetingapp.meetingapp.models.ReserveRoomAppointment;
import com.meetingapp.meetingapp.models.RoomAppointment;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.EmailAddressCollection;

import java.util.Collection;
import java.util.List;

public interface ExchangeRepository {
    List<RoomAppointment> getAppointments() throws Exception;
    List<RoomAppointment> getAllAppointments() throws Exception;
    String getRoomLocation(String roomName) throws Exception;
    boolean reserveRoomAppointment(ReserveRoomAppointment reserveRoomAppointment) throws Exception;
    EmailAddressCollection getRoomLists() throws Exception;
    Collection<EmailAddress> getRooms() throws Exception;
    boolean updateRoomStartAndEnd(UpdateAppointmentTime updateAppointmentTime, List<RoomAppointment> roomAppointments) throws Exception;
    boolean extendMeeting(ExtendAppointmentTime extendAppointmentTime, List<RoomAppointment> roomAppointments) throws Exception;
}
