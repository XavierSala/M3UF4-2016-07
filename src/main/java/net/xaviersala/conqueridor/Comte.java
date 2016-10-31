package net.xaviersala.conqueridor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import acm.graphics.GImage;
import acm.graphics.GRectangle;
import acm.util.RandomGenerator;

public class Comte {
	private String nom;
	private GImage cara;
	private List<Cavaller> cavallers;
	private Color color;
	private GRectangle casa; 
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	public Comte(String nom, GImage imatge) {
		this.nom = nom;
		this.cara = imatge;
		cavallers = new ArrayList<Cavaller>();
		color = rgen.nextColor(); 				
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

	public void setCasa(GRectangle lloc) {
		casa = lloc;		
	}

	public GRectangle getCasa() {
		return casa;
	}

	/**
	 * Col·loca els cavallers al castell inicial
	 */
	public void posaElsCavallersACasa() {
		for(Cavaller cavaller: cavallers) {
			cavaller.setPosicio(casa);		
			cavaller.setDesti(casa);
		}		
	}

	@Override
	public String toString() {
		return "Comte " + nom + " [" + cavallers + "]";
	}


	
	

}
