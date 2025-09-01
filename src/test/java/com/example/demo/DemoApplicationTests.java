package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.repositories.DayRepo;
import com.example.demo.services.DayService;
import com.example.demo.services.MonthService;

@SpringBootTest
class DemoApplicationTests {

	//todo start tests, i dont remember a shit.
	


	@Mock
	DayRepo dayRepo;


	@Autowired
	@InjectMocks
	DayService dayService;


	@Autowired
	MonthService monthService;

	

	@BeforeEach
	void initialize(){

	}

	@Test
	void saveMonth() {

	}

	@Test
	void test1(){
		
	}
}
