package com.meetingapp.meetingapp.controllers;

import com.meetingapp.meetingapp.exceptions.ApiRequestException;
import com.meetingapp.meetingapp.models.*;
import com.meetingapp.meetingapp.repository.ExchangeApi;
import com.meetingapp.meetingapp.serviceimpl.ExchangeServiceImpl;
import com.meetingapp.meetingapp.services.AuthenticationService;
import com.meetingapp.meetingapp.services.AuthorizationService;
import microsoft.exchange.webservices.data.property.complex.EmailAddressCollection;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8082", "http://localhost:8081", "localhost:8080"})
@RestController
@RequestMapping("/rooms")
public class RoomController {
    final ThreadLocal<ExchangeServiceImpl> service = ThreadLocal.withInitial(() -> new ExchangeServiceImpl(new ExchangeApi()));
    private AuthenticationService authenticationService;
    private AuthorizationService authorizationService;

    public RoomController(AuthenticationService authenticationService, AuthorizationService authorizationService) {
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<RoomAppointment>> getRoomAppointments() throws Exception {
        return new ResponseEntity<>(service.get().getAllRoomAppointments(), HttpStatus.OK);
    }

    @GetMapping("/{roomName}/appointments")
    public ResponseEntity<List<RoomAppointment>> getARoomsAppointments(@PathVariable(value = "roomName") String roomName) throws Exception {
        return new ResponseEntity<>(service.get().getRoomAppointmentsByRoomName(roomName, false), HttpStatus.OK);
    }

    @GetMapping("/{roomName}/allappointments")
    public ResponseEntity<List<RoomAppointment>> getAllRoomsAppointments(@PathVariable(value = "roomName") String roomName) throws Exception {
        return new ResponseEntity<>(service.get().getRoomAppointmentsByRoomName(roomName, true), HttpStatus.OK);
    }

    @GetMapping("/{roomName}/status")
    public ResponseEntity<RoomStatus> getRoomStatus(@PathVariable(value = "roomName") String roomName) throws Exception {
        return new ResponseEntity<>(service.get().getRoomStatus(roomName), HttpStatus.OK);
    }

    @GetMapping("/free")
    public ResponseEntity<Collection<RoomStatus>> getFreeRooms() throws Exception {
        return new ResponseEntity<>(service.get().getFreeRooms(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<Room>> getRooms() throws Exception {
        return new ResponseEntity<>(service.get().getRooms(), HttpStatus.OK);
    }

    @GetMapping("/lists")
    public ResponseEntity<EmailAddressCollection> getRoomLists() throws Exception {
        return new ResponseEntity<>(service.get().getRoomLists(), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<Room> validateRoom(HttpServletRequest request) throws Exception {
        String jwt = request.getHeader("Authorization");
        String email = authenticationService.getTokenSubject(jwt);

        if(authenticationService.validateRoomToken(jwt, email)){
            Room roomData = service.get().getRoomDataFromEmail(email);
            if(roomData == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            else return new ResponseEntity<>(roomData, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{roomName}")
    public HttpStatus reserveFreeRoom(@PathVariable(value = "roomName") String roomName,@RequestBody String body) throws Exception {
        JSONObject jsonObject = new JSONObject(body);
        ReserveRoomAppointment appointment = getReserveRoomAppointment(jsonObject, roomName);
        if(service.get().reserveFreeRoom(appointment)){
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PostMapping("/assign")
    public ResponseEntity<String> assignRoom(@RequestBody String bodyString){
        JSONObject body = new JSONObject(bodyString);
        Room assignedRoom = new Room(body.getString("name"), body.getString("location"), body.getString("emailAddress"));
        String jwt = body.getString("JWT");
        if(assignedRoom.getEmailAddress() == null || assignedRoom.getEmailAddress().isEmpty() || jwt == null || jwt.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(!authenticationService.validateAdminToken(jwt)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        String token = authenticationService.authenticateAssignRoomRequest(assignedRoom.getEmailAddress());

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PutMapping("/{roomName}/updateStartOrEnd")
    public synchronized HttpStatus updateRoomStartOrEnd(@PathVariable(value = "roomName") String roomName, @RequestBody String body) throws Exception {

        JSONObject jsonObject = new JSONObject(body);
        UpdateAppointmentTime updateAppointmentTime = getUpdateAppointmentTime(jsonObject, roomName);
        if (service.get().updateRoomStartAndEnd(updateAppointmentTime)) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    private UpdateAppointmentTime getUpdateAppointmentTime(JSONObject jsonObject, String roomName){
        UpdateAppointmentTime updateAppointmentTime = new UpdateAppointmentTime(roomName);
        Date now = DateTime.now().toDate();
        if (jsonObject.get("type").toString().equals("updateStart")){
            updateAppointmentTime.setUpdateTime(now);
            updateAppointmentTime.setIsStartDate(true);
        }
        else if(jsonObject.get("type").toString().equals("updateEnd")){
            updateAppointmentTime.setUpdateTime(now);
            updateAppointmentTime.setIsStartDate(false);
        }
        else{
            throw new ApiRequestException("No updateStart or updateEnd send");
        }
        return updateAppointmentTime;
    }

    @PutMapping("/{roomName}/extendMeeting")
    public synchronized HttpStatus extendMeeting(@PathVariable(value = "roomName") String roomName, @RequestBody String body) throws Exception {
        JSONObject jsonObject = new JSONObject(body);
        if (service.get().extendMeeting(new ExtendAppointmentTime((int) jsonObject.get("extendTime"), roomName))) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    public ReserveRoomAppointment getReserveRoomAppointment(JSONObject jsonObject, String roomName){
        ReserveRoomAppointment appointment = new ReserveRoomAppointment();
        try{
            appointment.setLocation(roomName);
            appointment.setSubject(jsonObject.get("subject").toString());
            appointment.setStartTime(DateTime.parse(jsonObject.get("startDate").toString()).toDate());
            appointment.setEndTime(DateTime.parse(jsonObject.get("endDate").toString()).toDate());
            appointment.setRoomEmail(service.get().getEmailAddressForSpecificRoom(roomName));
            appointment.setUserEmail(jsonObject.get("userEmail").toString());
            return appointment;
        }
        catch (Exception ex){
            throw new ApiRequestException(ex.toString());
        }

    }
}