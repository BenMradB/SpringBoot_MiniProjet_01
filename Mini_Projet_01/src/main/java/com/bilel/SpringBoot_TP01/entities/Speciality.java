package com.bilel.SpringBoot_TP01.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Speciality {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long specialityId;
	private String specialityName;
	
	@OneToMany(mappedBy = "speciality")
	private List<Course> courses;

	public Speciality() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Speciality(String specialityName, List<Course> courses) {
		super();
		this.specialityName = specialityName;
	}

	public Long getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(Long specialityId) {
		this.specialityId = specialityId;
	}

	public String getSpecialityName() {
		return specialityName;
	}

	public void setSpecialityName(String specialityName) {
		this.specialityName = specialityName;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Speciality [specialityId=" + specialityId + ", specialityName=" + specialityName + ", courses="
				+ courses + "]";
	}
}
