package net.xaviersala;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;

import acm.program.GraphicsProgram;
import net.xaviersala.conqueridor.Cavaller;
import net.xaviersala.conqueridor.Comte;

/**
 * Conquesta és un programa que consisteix en que els cavallers d'un comte
 * han de conquerir tots els castells per esdevenir rei.
 *
 * El problema és que n'hi ha d'altres i que els habitants de la comarca
 * són molt volàtils i es posen al costat del comte que ha passat per darrera
 * vegada per la seva comarca.
 *
 */
public class App extends GraphicsProgram {

    static final Logger LOG = Logger.getLogger("App");

    private static final long serialVersionUID = 1299094805237490891L;
    private static final int AMPLADAPANTALLA = 1024;
    private static final int ALTURAPANTALLA = 768;
    /**
     * Característiques de les caselles.
     */
    private static final double AMPLECASELLA = 40;
    private static final double ALTCASELLA = 40;
    private static final int CASELLESAMPLE = 25;
    private static final int CASELLESALT = 15;

    private static final int QUANTITAT_DE_CASTELLS = 12;

    /**
     * Característiques dels comtes.
     */
    private static final int CAVALLERS_DEL_COMTE = 2;
    private static final String[] NOMS_DELS_COMTES = { "Hug", "Ramon", "Bernat", "Marimon", "Berenguer" };



    /**
     * Programa principal...
     */
    @Override
    public final void run() {
        setSize(AMPLADAPANTALLA, ALTURAPANTALLA);

        Mapa mapa;
        mapa = new Mapa("Quadrilàndia", crearComarques(5, 5, CASELLESAMPLE, CASELLESALT));
        mapa.afegirComtes(crearComtes());

        clicaPerComencar();

        try {
            mapa.start();
        } catch (InterruptedException e) {
             LOG.severe(e.getMessage());
        }
    }

    /**
     * Crea la llista de comptes amb els seus cavallers.
     *
     * @return llista de comptes
     */
    private List<Comte> crearComtes() {
        List<Comte> comptes = new ArrayList<>();
        for (String nom : NOMS_DELS_COMTES) {

            // GImage imatgeCompte = new GImage(nom + ".png");
            Comte comte = new Comte(nom, null);

            for (int i = 0; i < CAVALLERS_DEL_COMTE; i++) {
                GImage imatgeCavaller = new GImage("cavaller.png");
                add(imatgeCavaller);
                comte.afegirCavaller(new Cavaller(nom + i, imatgeCavaller, comte));
            }

            comptes.add(comte);
        }
        return comptes;
    }

    /**
     * Crear les comarques a partir de la posició especificada
     *
     * @param xInicial
     *            : posició x inicial
     * @param yInicial
     *            : posició y inicial
     * @param xCaselles:
     *            caselles x
     * @param yCaselles:
     *            caselles y
     * @return
     */

    private List<Comarca> crearComarques(int xInicial, int yInicial, int xCaselles, int yCaselles) {

        Random aleatori = new Random();

        List<Comarca> comarques = new ArrayList<>(xInicial * yInicial);

        int posx = xInicial;
        int posy = yInicial;

        for (int i = 0; i < yCaselles; i++) {
            for (int j = 0; j < xCaselles; j++) {
                GRect posicio = new GRect(posx, posy, AMPLECASELLA, ALTCASELLA);
                add(posicio);
                Comarca comarca = new Comarca(posicio, null, false);
                comarques.add(comarca);
                posx += AMPLECASELLA;
            }
            posy += ALTCASELLA;
            posx = xInicial;
        }

        // Crear castells en el mapa
        for (int i = 0; i < QUANTITAT_DE_CASTELLS; i++) {
            int quina = aleatori.nextInt(comarques.size());
            GImage imatge = new GImage("castell.png");
            add(imatge);
            comarques.get(quina).setCastell(imatge);
        }

        return comarques;
    }

    /**
     * Clica per començar.
     */
    private void clicaPerComencar() {
        GLabel label = new GLabel("Clica per començar");
        double x = (getWidth() - label.getWidth()) / 2;
        double y = (getHeight() + label.getAscent()) / 2;
        add(label, x, y);
        waitForClick();
        remove(label);
    }

}
