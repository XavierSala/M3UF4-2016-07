package net.xaviersala.conqueridor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import acm.graphics.GImage;
import acm.graphics.GRectangle;
import acm.util.RandomGenerator;

/**
 * Defineix un comte amb els seus dos cavallers.
 *
 * @author xavier
 *
 */
public class Comte {
    private String nom;
    private GImage cara;
    private List<Cavaller> cavallers;
    private Color color;
    private GRectangle casa;
    private RandomGenerator rgen = RandomGenerator.getInstance();

    /**
     * Crea un comte.
     * @param nom nom del comte
     * @param imatge imatge del comte
     */
    public Comte(String nom, GImage imatge) {
        this.nom = nom;
        this.cara = imatge;
        cavallers = new ArrayList<>();
        color = rgen.nextColor();
    }

    /**
     * Afegir un cavaller al comte.
     *
     * @param cavaller cavaller a afegir
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
     * @return el nom del compte
     */
    public String getNom() {
        return "Comte " + nom;
    }

    /**
     *
     * @return Retorna el color del comte
     */
    public Color getColor() {
        return color;
    }

    /**
     * Defineix quin és el castell pairal del comte.
     *
     * @param lloc rectangle de la casella
     */
    public void setCasa(GRectangle lloc) {
        casa = lloc;
    }

    /**
     * Col·loca els cavallers al castell pairal.
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
