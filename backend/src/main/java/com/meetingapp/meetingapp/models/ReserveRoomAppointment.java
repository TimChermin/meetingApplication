package com.meetingapp.meetingapp.models;

import java.util.Date;

public class ReserveRoomAppointment {

    private String subject;
        private String roomEmail;
        private String userEmail;
        private Date startTime;
        private Date endTime;
        private String location;

        public ReserveRoomAppointment(String subject, Date startTime, Date endTime, String location, String roomEmail, String userEmail) {
            this.subject = subject;
            this.startTime = startTime;
            this.endTime = endTime;
            this.location = location;
            this.roomEmail = roomEmail;
            this.userEmail = userEmail;
        }

        public ReserveRoomAppointment(){

        }

        public String getSubject() {
            return subject;
        }

        public Date getStartTime() {
            return startTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public String getLocation() {
            return location;
        }


    public String getRoomEmail() {
        return roomEmail;
    }

    public void setRoomEmail(String roomEmail) {
        this.roomEmail = roomEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
