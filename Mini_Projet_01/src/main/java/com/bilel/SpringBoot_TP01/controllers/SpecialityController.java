package com.bilel.SpringBoot_TP01.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bilel.SpringBoot_TP01.entities.Speciality;
import com.bilel.SpringBoot_TP01.services.SpecialityService;

@Controller
public class SpecialityController {
	@Autowired
	private SpecialityService specialityService;
	
	public void paginate(ModelMap modelMap, int page, int size) {
		Page<Speciality> specialities = this.specialityService.getSpecialitiesByPage(page, size);
		modelMap.addAttribute("specialities", specialities);
		modelMap.addAttribute("pages", new int[specialities.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
	}
	
	@GetMapping("/newSpeciality")
	public String getCreateSpecialityPage(ModelMap modelMap) {
		
		return "Speciality/create_speciality";
	}
	
	@PostMapping("/createSpeciality")
	public String createSpeciality(
			ModelMap modelMap,
			@ModelAttribute("speciality") Speciality speciality,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size
			) {
		
		if (speciality.getSpecialityName().equals("")) {
			String error = "All Fields Are Required";
			modelMap.addAttribute("error", error);
			return "Speciality/create_speciality";
		}
		
		this.specialityService.createSpeciality(speciality);
		this.paginate(modelMap, page, size);
		
		return "Speciality/list_specialities";
	}
	
	@GetMapping("/specialities")
	public String getSpecialitiesPage(
			ModelMap modelMap,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size
			) {
		this.paginate(modelMap, page, size);
		return "Speciality/list_specialities";
	}
	
	@GetMapping("deleteSpeciality")
	public String deleteSpeciality(
			ModelMap modelMap,
			@RequestParam("id") Long specialityId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size) {
		this.specialityService.deleteSpecialityById(specialityId);
		this.paginate(modelMap, page, size);
		return "Speciality/list_specialities";
	}
	
	@GetMapping("/editSpeciality")
	public String getEditSpecialityPage(ModelMap modelMap, @RequestParam("id") Long specialityId, @RequestParam("page") int page) {
		Speciality speciality = this.specialityService.getSpecialityById(specialityId);
		modelMap.addAttribute("speciality", speciality);
		modelMap.addAttribute("currentPage", page);
		return "Speciality/update_speciality";
	}
	
	@PostMapping("/updateSpeciality")
	public String updateSpeciality(
			ModelMap modelMap,
			@ModelAttribute("speciality") Speciality speciality,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size 
			) {
		
		if (speciality.getSpecialityName().equals("")) {
			String error = "All Fields Are Required .";
			modelMap.addAttribute("error", error);
			return "Speciality/update_speciality";
		}
		
		this.specialityService.createSpeciality(speciality);
		this.paginate(modelMap, page, size);
		
		return "Speciality/list_specialities";
	}
}
