package net.xaviersala;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.graphics.GRectangle;
import net.xaviersala.conqueridor.Cavaller;
import net.xaviersala.conqueridor.Comte;

/**
 * Defineix una comarca.
 *
 * Una comarca es pinta del color del compte que la domina (a través del
 * seu cavaller) i pot tenir un castell o no.
 *
 * @author xavier
 *
 */
public class Comarca {

    GRect rectangle;

    GImage imatgeCastell;
    boolean castell;

    Cavaller propietari;

    /**
     * Crea una comarca en les posicions del rectangle
     *
     * @param rectangle Rectangle de la comarca
     * @param castell Imatge del castell
     * @param teCastell Defineix si té castell o no
     */
    public Comarca(GRect rectangle, GImage castell, boolean teCastell) {

        this.rectangle = rectangle;
        rectangle.setFilled(true);
        rectangle.setFillColor(Color.WHITE);

        this.imatgeCastell = castell;
        this.castell = teCastell;

        this.propietari = null;
    }

    /**
     * @return diu si la comarca té un castell o no
     */
    public boolean isCastell() {
        return castell;
    }

    /**
     * Posa un castell a la comarca.
     * @param gImage Imatge del castell
     */
    public void setCastell(GImage gImage) {
        castell = true;
        imatgeCastell = gImage;
        imatgeCastell.setBounds(rectangle.getBounds());
    }

    /**
     * El cavaller prova d'ocupar la comarca.
     *
     * @param cavaller cavaller que la ocupa
     * @return si ha estat ocupada o no
     */
    public boolean intentaOcuparla(Cavaller cavaller) {
        if (cavaller == propietari) {
            return false;
        }

        if (rectangle.getBounds().intersects(cavaller.getPosicio())) {
            // Canvia el propietari
            propietari = cavaller;
            rectangle.setFillColor(propietari.getColor());
            return true;
        }

        return false;
    }

    /**
     * @param cavaller cavaller que es comprova
     * @return diu si el cavaller domina aquesta comarca o no
     */
    public boolean isDelCavaller(Cavaller cavaller) {
        return propietari == cavaller;
    }

    /**
     * @param comte comte que es comprova
     * @return diu si el comte és el propietari de la comarca
     */
    public boolean isDelComte(Comte comte) {
        if (propietari == null) {
            return false;
        }

        return propietari.getComte() == comte;
    }

    /**
     * @return posició de la comarca
     */
    public GRectangle getPosicio() {
        return rectangle.getBounds();
    }

    /**
     * @return Diu si la comarca està ocupada o no
     */
    public boolean isOcupada() {
        return propietari != null;
    }

    /**
     * Pinta el rectangle del color especificat.
     *
     * @param color color
     */
    public void setColor(Color color) {
        rectangle.setFillColor(color);
    }

}
