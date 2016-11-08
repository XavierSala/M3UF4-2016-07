package net.xaviersala.conqueridor;

import java.awt.Color;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

/**
 * Defineix un dels cavallers que ocupen territori.
 *
 * @author xavier
 *
 */
public class Cavaller {

    private static final int VIDA_INICIAL = 1000;
    private static final int VELOCITAT_INICIAL = 5;
    private String nom;
    private GImage imatge;
    private Color color;
    private Comte comte;

    private int castellsConquerits;
    private int vida;

    private GRectangle desti;
    private double velocitat;
    private double angle;
    private boolean mort;

    /**
     * Crea un cavaller
     * @param nom nom del cavaller
     * @param imatge imatge del cavaller
     * @param comte comte al que segueix el cavaller
     */
    public Cavaller(String nom, GImage imatge, Comte comte) {

        this.nom = nom;
        this.comte = comte;

        this.imatge = imatge;

        color = comte.getColor();
        castellsConquerits = 0;
        vida = VIDA_INICIAL;
        mort = false;

        velocitat = VELOCITAT_INICIAL;
        angle = 0;
    }


    /**
     * @return diu a quin comte segueix aquest cavaller
     */
    public Comte getComte() {
        return comte;
    }

    /**
     * @return nom del cavaller
     */
    public String getNom() {
        return nom;
    }


    /**
     * El cavaller ha conquerit un castell més.
     */
    public void addCastellConquerit() {
        this.castellsConquerits ++;
    }

    /**
     * @return color del cavaller
     */
    public Color getColor() {
        return color;
    }


    /**
     * Posar el cavaller en la posició que s'especifica.
     *
     * @param casa casella on ha d'anar
     */
    public void setPosicio(GRectangle casa) {
        imatge.setLocation(casa.getX(), casa.getY());
    }

    /**
     * Obtenir la posició del cavaller.
     *
     * @return posició actual del cavaller
     */
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
        System.out.println("angle: " + angle);

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

    /**
     * @return determina si el cavaller és viu o mort.
     */
    public boolean isMort() {
        return mort;
    }

    /**
     * Mata el cavaller fent-lo desaparèixer i marcant-lo com a mort.
     */
    public void setMort() {
        imatge.setVisible(false);
        mort = true;
    }

    /**
     * Diu si el cavaller està a prop d'un altre cavaller.
     * @param altreCavaller l'altre cavaller
     * @return retorna si estan xocant
     */
    public boolean estaAProp(Cavaller altreCavaller) {
        return imatge.getBounds().intersects(altreCavaller.getPosicio());
    }

    @Override
    public String toString() {
        return "Cavaller " + getNom() + ", (" + castellsConquerits + "," + vida + ")";
    }


}
