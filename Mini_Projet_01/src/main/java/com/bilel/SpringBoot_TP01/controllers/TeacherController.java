package com.bilel.SpringBoot_TP01.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bilel.SpringBoot_TP01.entities.Teacher;
import com.bilel.SpringBoot_TP01.services.TeacherService;

@Controller
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;	
	
	public void customFunction(ModelMap modelMap, int page, int size) {
		Page<Teacher> teachers = this.teacherService.getTeachersByPage(page, size);
		
		modelMap.addAttribute("teachers", teachers);
		modelMap.addAttribute("pages", new int[teachers.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
	}
	
	public boolean contains(Teacher teacher) {
		
		List<Teacher> teachers = this.teacherService.getAllTeachers()
				.stream()
				.filter(t -> teacher.getEmail().equals( t.getEmail() ) )
				.collect(Collectors.toList());
		return teachers.size() > 0;
	}
	
	@GetMapping("/new")
	public String getCreateTeacherPage() {
		return "/teacher/create_teacher";
	}
	
	@PostMapping("/createTeacher")
	public String createTeacher(
				@ModelAttribute("teacher") Teacher teacher,
				@RequestParam("password") String password, @RequestParam("date") String date,
				@RequestParam("passwordConfirm") String passwordConfirm,
				ModelMap modelMap ) throws ParseException {
		
		if (
				teacher.getFirstName().equals("") ||
				teacher.getLastName().equals("") ||
				teacher.getUserName().equals("") ||
				teacher.getEmail().equals("") ||
				date.equals("") ||
				password.equals("") ||
				passwordConfirm.equals("")
		) {
			String msg = "All The Fields Are Required .";
			modelMap.addAttribute("msg", msg);
			return "/teacher/create_teacher";
		}
		
		if (contains(teacher)) {
			String msg = "There is an account related to this email adress !!! Try onother one .";
			modelMap.addAttribute("msg", msg);
			return "/teacher/create_teacher";
		}
		
		if (!password.equals(passwordConfirm)) {
			String msg = "Passwords Don't Match !!!";
			modelMap.addAttribute("msg", msg);
			return "/teacher/create_teacher";
		}
		
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		String hashedPassword = bcrypt.encode(password);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date birthday = dateFormat.parse(date);
		
		teacher.setBirthday(birthday);
		teacher.setPassword(hashedPassword);
		teacher.setRole("teacher");
		
		this.teacherService.createTeacher(teacher);
		
		this.customFunction(modelMap, 0, 2);

		
		return "/teacher/list_teachers";
	}
	
	@GetMapping("/teachers")
	public String getTeachersPage(
				ModelMap modelMap,
				@RequestParam(name = "page", defaultValue = "0") int page,
				@RequestParam(name = "size", defaultValue = "2") int size ) {
		
		this.customFunction(modelMap, page, size);

		
		return "/teacher/list_teachers";
	}
	
	@GetMapping("/deleteTeacher")
	public String deleteTeacher(
			ModelMap modelMap,
			@RequestParam("id") Long teacherId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size ) {
		
		this.teacherService.deleteTeacherById(teacherId);

		this.customFunction(modelMap, page, size);

	
		return "/teacher/list_teachers";
	}
	
	@GetMapping("/editTeacher")
	public String getEditTeacherPage(
			ModelMap modelMap,
			@RequestParam("id") Long teacherId,
			@RequestParam("page") int page) {
		
		Teacher teacher = this.teacherService.getTeacher(teacherId);
		modelMap.addAttribute("teacher", teacher);
		Date birthday = teacher.getBirthday();
		LocalDateTime localDateTimeCreation = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		modelMap.addAttribute("birthday", localDateTimeCreation);
		modelMap.addAttribute("currentPage", page);

		
		return "/teacher/update_teacher";
	}
	
	@PostMapping("/editTeacher")
	public String editTeacher(
			ModelMap modelMap,
			@ModelAttribute("teacher") Teacher teacher,
			@RequestParam("date") String date,
			@RequestParam(name = "page", defaultValue = "0") int page
			) throws ParseException {
		
		Teacher t = this.teacherService.getTeacher(teacher.getTeacherId());
		
		if (
				teacher.getFirstName().equals("") ||
				teacher.getLastName().equals("") ||
				teacher.getUserName().equals("") ||
				teacher.getEmail().equals("") ||
				date.equals("")
		) {
			String error = "All The Fields Are Required .";
			modelMap.addAttribute("error", error);
			return "/teacher/update_teacher";
		}
		
		if (contains(teacher)) {
			if ( !t.getEmail().equals(teacher.getEmail())) {
				String error = "Email Already In Use .";
				modelMap.addAttribute("error", error);
				return "/teacher/update_teacher";
			}
		}
		
		System.out.println(teacher.getPassword());
		System.out.println(t.getPassword());

		
		if (!teacher.getPassword().equals("")) {
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			String hashedPassword = bcrypt.encode(teacher.getPassword());
			System.out.println(hashedPassword);

			teacher.setPassword(hashedPassword);
		} else {
			teacher.setPassword(t.getPassword());
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date birthday = dateFormat.parse(date);
		
		
		teacher.setBirthday(birthday);
		teacher.setRole(t.getRole());
		
		this.teacherService.createTeacher(teacher);
		
		this.customFunction(modelMap, page, 2);
		
		return "/teacher/list_teachers";
	}
}
