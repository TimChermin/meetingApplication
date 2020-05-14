package com.meetingapp.meetingapp.repository.interfaces;

import com.microsoft.graph.models.extensions.User;

import java.util.List;

public interface GraphRepository {
    List<User> getUsers();
}
