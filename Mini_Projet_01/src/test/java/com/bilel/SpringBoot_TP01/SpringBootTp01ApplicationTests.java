package com.bilel.SpringBoot_TP01;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.bilel.SpringBoot_TP01.entities.Produit;
import com.bilel.SpringBoot_TP01.entities.Teacher;
import com.bilel.SpringBoot_TP01.repos.ProduitRepository;
import com.bilel.SpringBoot_TP01.services.ProduitService;
import com.bilel.SpringBoot_TP01.services.TeacherService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootTest
class SpringBootTp01ApplicationTests {

	@Autowired
	private ProduitRepository produitRepo;
	
	@Autowired
	private ProduitService produitService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Test
	public void testCreateProduit() {
		Produit produit = new Produit("Smart TV 2", 6660.0, new Date());

		this.produitRepo.save(produit);
	}
	
	@Test
	public Produit testFindProduitByID(long id) {
		Produit produit = this.produitRepo.findById(id).get();
		
		System.out.println("Debut De La Fonction ' findProduitByID(id)' ");
		
		System.out.println(
				"Produit Id : " + produit.getIdProduit() + 
				" \nLe Nom De Produit : " + produit.getNomProduit() + 
				" \nLe Prix De Produit : " + produit.getPrixProduit() + " DT" +  
				" \nLa Date De Creation : " + produit.getDateCreation()
		);
		
		System.out.println("Fin La Fonction ' findProduitByID(id)' ");

		
		return produit;
	}
	
	@Test
	public void testUpdateProduit() {
		Produit produit = this.testFindProduitByID(1L);
		
		System.out.println("Debut La Fonction ' updateProduit()' ");

		
		produit.setPrixProduit(1500.0);
		
		this.produitRepo.save(produit);
		
		System.out.println("\n Produit Aprés Le Modifier \n");
		
		System.out.println(
				"Produit Id : " + produit.getIdProduit() + 
				" \nLe Nom De Produit : " + produit.getNomProduit() + 
				" \nLe Prix De Produit : " + produit.getPrixProduit() + " DT" +  
				" \nLa Date De Creation : " + produit.getDateCreation()
		);
		
		System.out.println("Fin La Fonction ' updateProduit()' ");
	}
	
	@Test
	public void testDeleteProduit() {
		Produit produit = this.produitRepo.findById(1L).get();
		this.produitRepo.delete(produit);
	}
	
	@Test
	public void testGetAllProduits() {
		List<Produit> produits = this.produitRepo.findAll();
		
		for(Produit produit : produits) {
			System.out.println(produit);
		}
	}
	
	@Test
	public void testFindByNomProduitContains() {
		Page<Produit> prods = this.produitService.getAllProduitsByPage(0, 2);
		System.out.println(prods.getSize());
		System.out.println(prods.getTotalElements());
		System.out.println(prods.getTotalPages());
		
		prods.getContent().forEach(prod -> {
			System.out.println(prod);
		});
	}
	
	@Test
	public void testCreateNewTeacher() {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		System.out.println(bcrypt.encode("123456"));
		Teacher teacher = new Teacher("BenMrad Bilel", "BenMrad", "Bilel", "benmrad@gmail.com", bcrypt.encode("123456"), new Date());
		this.teacherService.createTeacher(teacher);
	}
}
