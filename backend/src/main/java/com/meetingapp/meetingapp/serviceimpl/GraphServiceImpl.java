package com.meetingapp.meetingapp.serviceimpl;

import com.meetingapp.meetingapp.models.UserModel;
import com.meetingapp.meetingapp.repository.ExchangeApi;
import com.meetingapp.meetingapp.repository.interfaces.GraphRepository;
import com.meetingapp.meetingapp.services.GraphService;
import com.microsoft.graph.models.extensions.User;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GraphServiceImpl implements GraphService {
    private GraphRepository repo;

    public GraphServiceImpl(GraphRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<UserModel> getUsers() {
        ArrayList<UserModel> users = new ArrayList<>();
        ExchangeApi exchangeApi = new ExchangeApi();
        Collection<EmailAddress> rooms = exchangeApi.getRooms();
            for (User user : repo.getUsers()) {
                isUserRoom(users, user, rooms);
            }
        return users;
    }

    private void isUserRoom(ArrayList<UserModel> users, User user, Collection<EmailAddress> rooms){
        for (EmailAddress room : rooms) {
            if (room.getAddress().equalsIgnoreCase(user.mail)) {
                return;
            }
        }
        //TODO: Add configurable company domain to the end of mailNickname, or use normal user.mail
        //User.mail is empty for normal users in the test environment, because there is no license/mailbox available for them.
        users.add(new UserModel(user.displayName, user.mailNickname + "@isaacfontys.onmicrosoft.com"));
    }
}
