package net.xaviersala;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.graphics.GRectangle;
import net.xaviersala.conqueridor.Cavaller;
import net.xaviersala.conqueridor.Comte;

public class Comarca {
	
	GRect rectangle;
	GImage imatgeCastell;
	boolean castell;
	Cavaller propietari;
	
	public Comarca(GRect rectangle, GImage castell, boolean teCastell) {
		
		this.rectangle = rectangle;
		this.rectangle.setFilled(true);
		this.rectangle.setFillColor(Color.WHITE);
		
		this.imatgeCastell = castell;
		this.castell = teCastell;		
		
		this.propietari = null;
	}
	
	public Comte deQuinCompte() {
		if (propietari == null) {
			return null;
		}
		else {
			return propietari.getComte();
		}
	}

	// Diu si el rectangle del cavaller toca amb la casella...
	public boolean xocaAmb(GRectangle cavallerRect) {
		return rectangle.getBounds().intersects(cavallerRect);
	}

	/**
	 * 
	 * @return Obtenir el cavaller que domina la casella. 
	 */
	public Cavaller getCavaller() {
		return propietari;
	}

	/**
	 * El cavaller ara domina la comarca.
	 * 
	 * @param cavaller Cavaller dominador
	 */
	public void setCavaller(Cavaller cavaller) {
		this.propietari = cavaller;		
	}
	
	/**
	 * @return Determina si la casella és un castell o no.
	 */
	public boolean isCastell() {
		return castell;
	}

	/**
	 * Converteix la casella en un castell.
	 */
	public void setCastell() {
		castell = true;		
		// TODO: Eliminar-lo quan hi hagi imatge
		rectangle.setFillColor(Color.BLACK);
		
	}

	/**
	 * Mira si el cavaller està ocupant alguna casella i retorna els cavallers
	 * usats.
	 * 
	 * @param cavaller cavaller que conquereix
	 * @return número de castells conquerits
	 */
	public boolean ocupadaPer(Cavaller cavaller) {
		if (cavaller == propietari) {
			return false;
		}
		
		if (rectangle.getBounds().intersects(cavaller.getPosicio())) {
			// Canvia el propietari
			propietari = cavaller;
			rectangle.setFillColor(propietari.getColor());
			return (castell);
		}
				
		return false;
	}

	public boolean isDelCavaller(Cavaller cavaller) {		
		return (propietari == cavaller);
	}

	public GPoint getPosicio() {
		return rectangle.getLocation();
	}


}
