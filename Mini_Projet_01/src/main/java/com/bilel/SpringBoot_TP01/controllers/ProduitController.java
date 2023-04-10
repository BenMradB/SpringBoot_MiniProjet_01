package com.bilel.SpringBoot_TP01.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bilel.SpringBoot_TP01.entities.Produit;
import com.bilel.SpringBoot_TP01.services.ProduitService;

@Controller
public class ProduitController {
	
	@Autowired
	private ProduitService produitsService;
	
	@GetMapping("/create")
	public String createProduitPage() {
		return "createProduit";
	}
	
	@PostMapping("/saveProduit")
	public String saveProduit(@ModelAttribute("produit") Produit produit, @RequestParam("date") String date, ModelMap modelMap) throws ParseException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date dateCreation = dateFormat.parse(date);
		produit.setDateCreation(dateCreation);
		
		Produit savedProduit = this.produitsService.saveProduit(produit);
		String msg = "Produit Avec ID : " + savedProduit.getIdProduit() + " Et Du Nom : " + savedProduit.getNomProduit() +  " Enregistrer Avec Succes";
		modelMap.addAttribute("msg", msg);
		
		Page<Produit> prods = this.produitsService.getAllProduitsByPage(0, 2);
		modelMap.addAttribute("produits", prods);
		modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
		
		
		modelMap.addAttribute("currentPage", prods.getTotalPages() - 1);
		
		return "listerProduits";
	}
	
	@GetMapping("/lister")
	public String listeProduits(ModelMap modelMap, @RequestParam(name="page", defaultValue="0") int page, @RequestParam(name="size", defaultValue="2") int size) {

		Page<Produit> prods = this.produitsService.getAllProduitsByPage(page, size);
		modelMap.addAttribute("produits", prods);
		modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
		
		// System.out.println(Arrays.toString(new int[prods.getTotalPages()]));
		
		modelMap.addAttribute("currentPage", page);
		return "listerProduits";
	}
	
	@GetMapping("/editerProduit")
	public String editerProduPage(@RequestParam("id") Long id, ModelMap modelMap) {
		Produit produit = this.produitsService.getProduit(id);
		
		Date dateCreation = produit.getDateCreation();
		LocalDateTime localDateTimeCreation = dateCreation.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		modelMap.addAttribute("dateCreation", localDateTimeCreation);
		modelMap.addAttribute("produit", produit);
		
		return "editerProduit";
	}
	
	@PostMapping("/editerProduit")
	public String editerProduit(
			@ModelAttribute("produit") Produit produit,
			@RequestParam("date") String date,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="size", defaultValue="2") int size,
			ModelMap modelMap
	) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date dateCreation = dateFormat.parse(date);
		produit.setDateCreation(dateCreation);
		
		this.produitsService.updateProduit(produit);
		
		System.out.println("fdsfsfsf");
		
		Page<Produit> prods = this.produitsService.getAllProduitsByPage(page, size);
		
		modelMap.addAttribute("produits", prods);
		modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
		
		modelMap.addAttribute("currentPage", page);

		return "listerProduits";
	}
	
	@GetMapping("/supprimerProduit")
	public String supprimerProduit(@RequestParam("id") Long id, ModelMap modelMap, @RequestParam(name="page", defaultValue="0") int page, @RequestParam(name="size", defaultValue="2") int size) {
		System.out.println(page);
		Page<Produit> prods1 = this.produitsService.getAllProduitsByPage(page, size);
		System.out.println(prods1.getTotalPages());


		this.produitsService.deleteProduitById(id);
		
		Page<Produit> prods = this.produitsService.getAllProduitsByPage(page, size);
		
		System.out.println(prods.getTotalPages());

		
		modelMap.addAttribute("produits", prods);
		modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
		
		modelMap.addAttribute("currentPage", page);
		
		return "listerProduits";
	}
}