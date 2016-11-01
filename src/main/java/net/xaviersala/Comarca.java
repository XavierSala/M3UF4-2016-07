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
     * @return retorna quin és el comte que domina la comarca.
     */
    public Comte deQuinComte() {
        if (propietari == null) {
            return null;
        }
        else {
            return propietari.getComte();
        }
    }

    /**
     * Comprova si la posició d'un cavaller xoca amb la comarca.
     *
     * @param cavallerRect Rectangle que ocupa el cavaller.
     * @return
     */
    public boolean xocaAmb(GRectangle cavallerRect) {
        return rectangle.getBounds().intersects(cavallerRect);
    }

    /**
     * @return Retorna quin és el cavaller que domina la comarca.
     */
    public Cavaller getCavaller() {
        return propietari;
    }

    /**
     * El cavaller domina la comarca.
     *
     * @param cavaller cavaller que la domina
     */
    public void setCavaller(Cavaller cavaller) {
        this.propietari = cavaller;
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
    public boolean ocupadaPer(Cavaller cavaller) {
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

    @Override
    public String toString() {
        return "[ " + castell + "]";
    }


}
