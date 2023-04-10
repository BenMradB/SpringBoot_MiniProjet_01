package com.bilel.SpringBoot_TP01.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bilel.SpringBoot_TP01.entities.Course;
import com.bilel.SpringBoot_TP01.repos.CourseRepo;

@Service
public class CourseServiceImplementation implements CourseService{
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Override
	public Course addCourse(Course course) {
		// TODO Auto-generated method stub
		return this.courseRepo.save(course);
	}

	@Override
	public Course updateCourse(Course course) {
		// TODO Auto-generated method stub
		return this.courseRepo.save(course);
	}

	@Override
	public Course getCourse(Long courseId) {
		// TODO Auto-generated method stub
		return this.courseRepo.findById(courseId).get();
	}

	@Override
	public List<Course> getAllCourses() {
		// TODO Auto-generated method stub
		return this.courseRepo.findAll();
	}

	@Override
	public Page<Course> getAllCourseByPage(int page, int size) {
		// TODO Auto-generated method stub
		return this.courseRepo.findAll(PageRequest.of(page, size));
	}

	@Override
	public void deleteCourseById(Long courseId) {
		// TODO Auto-generated method stub
		this.courseRepo.deleteById(courseId);
	}

	@Override
	public void deleteCourse(Course course) {
		// TODO Auto-generated method stub
		this.courseRepo.delete(course);
	}

}
