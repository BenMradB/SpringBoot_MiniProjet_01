package com.bilel.SpringBoot_TP01.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bilel.SpringBoot_TP01.entities.Speciality;

public interface SpecialityRepo extends JpaRepository<Speciality, Long> {

}
