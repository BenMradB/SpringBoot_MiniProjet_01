package com.bilel.SpringBoot_TP01;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.bilel.SpringBoot_TP01.entities.Teacher;
import com.bilel.SpringBoot_TP01.services.TeacherService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootTest
class SpringBootTp01ApplicationTests {
	
	@Autowired
	private TeacherService teacherService;

	
	@Test
	public void testCreateNewTeacher() {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		System.out.println(bcrypt.encode("123456"));
		Teacher teacher = new Teacher("BenMrad Bilel", "BenMrad", "Bilel", "benmrad@gmail.com", bcrypt.encode("123456"), new Date());
		this.teacherService.createTeacher(teacher);
	}
}
