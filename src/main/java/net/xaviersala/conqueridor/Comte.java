package net.xaviersala.conqueridor;

import acm.graphics.GRectangle;
import acm.util.RandomGenerator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


/**
 * Defineix un comte amb els seus dos cavallers.
 *
 * @author xavier
 *
 */
public class Comte {
  private String nom;
  private List<Cavaller> cavallers;
  private Color color;
  private GRectangle casa;
  private RandomGenerator rgen = RandomGenerator.getInstance();
  private boolean derrotat;

  /**
   * Crea un comte.
   *
   * @param nom
   *          nom del comte
   */
  public Comte(String nom) {
    this.nom = nom;
    cavallers = new ArrayList<>();
    color = rgen.nextColor();
    derrotat = false;
  }

  /**
   * Afegir un cavaller al comte.
   *
   * @param cavaller
   *          cavaller a afegir
   */
  public void afegirCavaller(Cavaller cavaller) {
    cavallers.add(cavaller);
  }

  /**
   * @return Obtenir la llista de cavallers del comte.
   */
  public List<Cavaller> getCavallers() {
    return cavallers;
  }

  /**
   * @return el nom del compte.
   */
  public String getNom() {
    return "Comte " + nom;
  }

  /**
   * @return Retorna el color del comte.
   */
  public Color getColor() {
    return color;
  }

  /**
   * Defineix quin és el castell pairal del comte.
   *
   * @param lloc
   *          rectangle de la casella
   */
  public void setCasa(GRectangle lloc) {
    casa = lloc;
  }

  /**
   * Col·loca els cavallers al castell pairal.
   */
  public void posaElsCavallersACasa() {
    for (Cavaller cavaller : cavallers) {
      cavaller.setPosicio(casa);
      cavaller.setDesti(casa);
    }
  }

  public void derrotat() {
    derrotat = true;

  }

  public boolean isDerrotat() {
    return derrotat;
  }

  @Override
  public String toString() {
    return "Comte " + nom + " [" + cavallers + "]";
  }

}
