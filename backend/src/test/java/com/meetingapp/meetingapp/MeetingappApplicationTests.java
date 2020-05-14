package com.meetingapp.meetingapp;

import com.meetingapp.meetingapp.repository.MockApi;
import com.meetingapp.meetingapp.serviceimpl.ExchangeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MeetingappApplicationTests {
	private ExchangeServiceImpl service = new ExchangeServiceImpl(new MockApi());

	@Test
	void getRooms_AssertSize5() throws Exception {
		var result = service.getRooms();
		assert result.size()==5;
	}

	@Test
	void getAllRoom_AssertSize8() throws Exception {
		var result = service.getAllRoomAppointments();
		assert result.size()==8;
	}

	@Test
	void getRoomsList_AssertMeetingRooms() throws Exception {
		var result = service.getRoomLists();
		assert result.getItems().get(0).getAddress().equals("MeetingRooms@isaacfontys.onmicrosoft.com");
	}

}
