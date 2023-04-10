package com.bilel.SpringBoot_TP01.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Produit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idProduit;
	private String nomProduit;
	private double prixProduit;
	private Date dateCreation;
	
	public Produit() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Produit(String nomProduit, double prixProduit, Date dateCreation) {
		super();
		this.nomProduit = nomProduit;
		this.prixProduit = prixProduit;
		this.dateCreation = dateCreation;
	}


	public long getIdProduit() {
		return idProduit;
	}
	
	public void setIdProduit(long idProduit) {
		this.idProduit = idProduit;
	}
	
	public String getNomProduit() {
		return nomProduit;
	}
	
	public void setNomProduit(String nomProduit) {
		this.nomProduit = nomProduit;
	}
	
	public double getPrixProduit() {
		return prixProduit;
	}
	
	public void setPrixProduit(double prixProduit) {
		this.prixProduit = prixProduit;
	}
	
	public Date getDateCreation() {
		return dateCreation;
	}
	
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	@Override
	public String toString() {
		return "ProduitEntity [idProduit=" + idProduit + ", nomProduit=" + nomProduit + ", prixProduit=" + prixProduit
				+ ", dateCreation=" + dateCreation + "]";
	}

}
