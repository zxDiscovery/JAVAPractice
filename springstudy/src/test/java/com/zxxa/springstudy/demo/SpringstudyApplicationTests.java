package com.zxxa.springstudy.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zxxa.springstudy.entity.Person;

@SpringBootTest
class SpringstudyApplicationTests {

	@Autowired
	Person person;
	
	@Test
	void contextLoads() {
		
		System.out.println(person);
	}

}
