package com.bilel.SpringBoot_TP01.controllers;

import com.bilel.SpringBoot_TP01.entities.Course;
import com.bilel.SpringBoot_TP01.entities.Speciality;
import com.bilel.SpringBoot_TP01.entities.Teacher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bilel.SpringBoot_TP01.services.CourseService;
import com.bilel.SpringBoot_TP01.services.SpecialityService;
import com.bilel.SpringBoot_TP01.services.TeacherService;

@Controller
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private SpecialityService specialityService;
	
	public void listOfTeachers(ModelMap modelMap) {
		List<Teacher> teachers = this.teacherService.getAllTeachers();
		
		modelMap.addAttribute("teachers", teachers);
	}
	
	public void listOfSpecialities(ModelMap modelMap) {
		List<Speciality> specialities = this.specialityService.getSpecialities();
		
		modelMap.addAttribute("specialities", specialities);
	}
	
	public void paginate(ModelMap modelMap, int page, int size) {
		Page<Course> courses = this.courseService.getAllCourseByPage(page, size);
		modelMap.addAttribute("courses", courses);
		modelMap.addAttribute("pages", new int[courses.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
	}
	
	
	@GetMapping("/newCourse")
	public String getCreateCoursePage(ModelMap modelMap) {
		this.listOfTeachers(modelMap);
		this.listOfSpecialities(modelMap);
		return "/course/create_course";
	}
	
	@PostMapping("/createCourse")
	public String createCourse(
			ModelMap modelMap,
			@ModelAttribute("course") Course course,
			@RequestParam("teacher") Long teacherId) {
		
		if (course.getCourseName().equals("") || course.getCourseDesc().equals("")) {
			String error = "All Fields R required while creating a new course !!!";
			modelMap.addAttribute("error", error);
			this.listOfTeachers(modelMap);
			return "/course/create_course";
		}
		

		this.courseService.addCourse(course);

		this.paginate(modelMap, 0, 2);
		return "/course/list_courses";
	}
	
	@GetMapping("/courses")
	public String coursesPage(
					ModelMap modelMap,
					@RequestParam(name = "page", defaultValue = "0") int page,
					@RequestParam(name = "size", defaultValue = "2") int size) {
		
		this.paginate(modelMap, page, size);
		return "/course/list_courses";
	}
	
	@GetMapping("/deleteCourse")
	public String deleteCourse(
				ModelMap modelMap,
				@RequestParam("id") Long courseId,
				@RequestParam(name = "page", defaultValue = "0") int page,
				@RequestParam(name = "size", defaultValue = "2") int size ){
		this.courseService.deleteCourseById(courseId);
		this.paginate(modelMap, page, size);
		
		return "/course/list_courses";
	}
	
	@GetMapping("/editCourse")
	public String getUpdateCoursePage(ModelMap modelMap, @RequestParam("id") Long courseId, @RequestParam("page") int page) {
		Course course = this.courseService.getCourse(courseId);
		Teacher currentTeacher = course.getTeacher();
		Speciality currentSpeciality = course.getSpeciality();
		
		this.listOfTeachers(modelMap);
		this.listOfSpecialities(modelMap);
		modelMap.addAttribute("course", course);
		modelMap.addAttribute("currentTeacher", currentTeacher);
		modelMap.addAttribute("currentSpeciality", currentSpeciality);
		modelMap.addAttribute("currentPage", page);


		return "/course/update_course";
	}
	
	@PostMapping("/updateCourse")
	public String updateCourse(
			ModelMap modelMap,
			@ModelAttribute("course") Course course,
			@RequestParam("teacher") Long teacherId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size) {
		
		if (course.getCourseName().equals("") || course.getCourseDesc().equals("")) {
			String error = "All The Fields Are Required .";
			Teacher currentTeacher = course.getTeacher();
			
			modelMap.addAttribute("currentTeacher", currentTeacher);
			modelMap.addAttribute("error", error);
			return "/course/update_course";
		}
		
		this.courseService.addCourse(course);
		
		this.paginate(modelMap, page, size);
		
		return "/course/list_courses";
	}
	
	
}
