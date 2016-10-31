package net.xaviersala.conqueridor;

import java.awt.Color;
import java.util.logging.Logger;

import acm.graphics.GImage;
import acm.graphics.GRectangle;

public class Cavaller {

	private static final int VIDA = 1000;
	private static final int VELOCITAT = 5;
	private String nom;
	private GImage imatge;
	private Color color;
	private Comte comte;
	
	private int castellsConquerits;
	private int vida;
	
	private GRectangle desti;
	private double velocitat;
	private double angle;
	
	static final Logger LOG = Logger.getLogger("Cavaller");
	
	public Cavaller(String nom, GImage imatge, Comte comte) {
		
		this.nom = nom;
		this.comte = comte;
		
		this.imatge = imatge;
		
		color = comte.getColor();		
		castellsConquerits = 0;
		vida = VIDA;
		
		velocitat = VELOCITAT;
		angle = 0;
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

	public void setPosicio(GRectangle casa) {
		imatge.setLocation(casa.getX(), casa.getY());
	}
	
	public GRectangle getPosicio() {
		return imatge.getBounds();
	}
	
	/**
	 * Defineix cap a on ha d'anar el cavaller
	 * @param punt destí del cavaller
	 */
	public void setDesti(GRectangle on) {
		desti = on;
		double dx = (desti.getX() - desti.getWidth()*0.5) - (imatge.getX() + imatge.getWidth()*0.5);
		double dy = (imatge.getY() - imatge.getHeight()*0.5) - (desti.getY() + desti.getHeight()*0.5);
		
		double result = Math.toDegrees(Math.atan2(dy , dx));
		angle = (result < 0) ? (360d + result) : result;
		
		LOG.info(this.nom + " (" + imatge.getX() + ","  + imatge.getY() + ") - (" + desti.getX() + "," + desti.getY() + ") -> " + angle);
	}
	
	/**
	 * Mou el cavaller en la direcció que li toca
	 * @return Retorna si ja ha arribat al destí
	 */
	public boolean mou() {
						
		if (imatge.getBounds().intersects(desti)) {
			imatge.setLocation(desti.getX(), desti.getY());
			return false;
		}
		imatge.movePolar(velocitat, angle);
		return true;
	}


	@Override
	public String toString() {
		return "Cavaller " + nom + ", (" + castellsConquerits + "," + vida + ")";
	}
	
	
}
