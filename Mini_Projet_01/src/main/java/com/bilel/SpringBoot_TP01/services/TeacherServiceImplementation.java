package com.bilel.SpringBoot_TP01.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bilel.SpringBoot_TP01.entities.Teacher;
import com.bilel.SpringBoot_TP01.repos.TeacherRepo;

@Service
public class TeacherServiceImplementation implements TeacherService {
	
	@Autowired
	private TeacherRepo teacherRepo;

	@Override
	public Teacher createTeacher(Teacher teacher) {
		return this.teacherRepo.save(teacher);
	}

	@Override
	public Teacher updateTeacher(Teacher teacher) {
		return this.teacherRepo.save(teacher);
	}

	@Override
	public void deleteTeacher(Teacher teacher) {
		this.teacherRepo.delete(teacher);
	}

	@Override
	public void deleteTeacherById(Long id) {
		this.teacherRepo.deleteById(id);

	}

	@Override
	public List<Teacher> getAllTeachers() {
		return this.teacherRepo.findAll();
	}

	@Override
	public Page<Teacher> getTeachersByPage(int page, int size) {
		return this.teacherRepo.findAll(PageRequest.of(page, size));
	}

	@Override
	public Teacher getTeacher(Long teacherId) {
		// TODO Auto-generated method stub
		return this.teacherRepo.findById(teacherId).get();
	}

}
