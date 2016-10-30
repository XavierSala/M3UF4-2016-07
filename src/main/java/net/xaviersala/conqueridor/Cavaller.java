package net.xaviersala.conqueridor;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRectangle;

public class Cavaller {

	String nom;
	GImage imatge;
	Color color;
	Comte comte;
	
	int castellsConquerits;
	int vida;
	
	public Cavaller(String nom, GImage imatge, Comte comte) {
		
		this.nom = nom;
		this.comte = comte;
		
		this.imatge = imatge;
		
		color = comte.getColor();		
		castellsConquerits = 0;
		vida = 1000;
	}
	
	
	public Comte getComte() {
		return comte;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getCastellsConquerits() {
		return castellsConquerits;
	}

	public void setCastellsConquerits(int castellsConquerits) {
		this.castellsConquerits = castellsConquerits;
	}

	public void addCastellConquerit() {
		this.castellsConquerits ++;
	}
	
	public void removeCastellConquerit() {
		this.castellsConquerits--;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public GRectangle getPosicio() {
		return imatge.getBounds();
	}
	
	
	
}
