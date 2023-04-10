package com.bilel.SpringBoot_TP01.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bilel.SpringBoot_TP01.entities.Produit;
import com.bilel.SpringBoot_TP01.repos.ProduitRepository;

@Service
public class ProduitServiceImpl implements ProduitService {
	
	@Autowired
	private ProduitRepository produitRepo;

	@Override
	public Produit saveProduit(Produit p) {
		return this.produitRepo.save(p);
	}

	@Override
	public Produit updateProduit(Produit p) {
		return this.produitRepo.save(p);
	}

	@Override
	public void deleteProduit(Produit p) {
		this.produitRepo.delete(p);
		
	}

	@Override
	public void deleteProduitById(Long id) {
		this.produitRepo.deleteById(id);
		
	}

	@Override
	public Produit getProduit(Long id) {
		return this.produitRepo.findById(id).get();
	}

	@Override
	public List<Produit> getAllProduits() {
		return this.produitRepo.findAll();
	}

	@Override
	public Page<Produit> getAllProduitsByPage(int page, int size) {
		return this.produitRepo.findAll(PageRequest.of(page, size));
	}
}
