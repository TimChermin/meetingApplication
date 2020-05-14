package com.meetingapp.meetingapp.services;

import com.meetingapp.meetingapp.models.UserModel;

import java.util.List;

public interface GraphService {
    List<UserModel> getUsers();
}
