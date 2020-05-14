package com.meetingapp.meetingapp;

import com.meetingapp.meetingapp.repository.MockApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MeetingappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingappApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx)  {
		MockApi wow = new MockApi();
		return args -> {
		};
	}
}
