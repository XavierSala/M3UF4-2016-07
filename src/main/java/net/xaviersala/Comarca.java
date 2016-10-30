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
		rectangle.setFilled(true);
		rectangle.setFillColor(Color.WHITE);
		
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

	public Cavaller getCavaller() {
		return propietari;
	}

	public void setCavaller(Cavaller cavaller) {
		this.propietari = cavaller;		
	}
	
	public boolean isCastell() {
		return castell;
	}

	public void setCastell() {
		castell = true;		
	}

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
