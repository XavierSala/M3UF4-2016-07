package net.xaviersala.conqueridor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public class Comte {
	GImage cara;
	List<Cavaller> cavallers;
	Color color;
	GPoint casa; 
	
	public Comte(GImage imatge, GPoint casa) {
		Random aleatori = new Random();
		this.cara = imatge;
		cavallers = new ArrayList<Cavaller>();
		color = new Color(aleatori.nextInt(256), aleatori.nextInt(256), aleatori.nextInt(256)); 
		
		this.casa = casa;
	}
	
	public void afegirCavaller(Cavaller cavaller) {
		cavallers.add(cavaller);
	}
	
	public List<Cavaller> getCavallers() {
		return cavallers;
	}
	public void setCavallers(List<Cavaller> cavallers) {
		this.cavallers = cavallers;
	}
	
	public int quantsCastellsDomina() {
		int quants = 0;
		for (Cavaller cavaller: cavallers) {
			quants += cavaller.getCastellsConquerits();
		}
		return quants;
	}

	public Color getColor() {		
		return color;
	}

	public void setCasa(GPoint lloc) {
		casa = lloc;		
	}

	public GPoint getCasa() {
		return casa;
	}
	
	

}
