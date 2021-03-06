package net.xaviersala;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import net.xaviersala.conqueridor.Cavaller;
import net.xaviersala.conqueridor.Comte;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;


/**
 * Conquesta és un programa que consisteix en que els cavallers d'un comte han
 * de conquerir tots els castells per esdevenir rei.
 *<p></p>
 * El problema és que n'hi ha d'altres i que els habitants de la comarca són
 * molt volàtils i es posen al costat del comte que ha passat per darrera vegada
 * per la seva comarca.
 *
 */
public class App extends GraphicsProgram {

  private static final Logger LOG = Logger.getLogger("App");

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
  private static final String[] NOMS_DELS_COMTES = {
      "Hug", "Ramon", "Bernat", "Marimon", "Berenguer", "Jaume I"
      };
  private static final int CAVALLERS_DEL_COMTE = 12;

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
      String guanyador = mapa.start();
      missatge(guanyador, "Helvetica-*-30");
    } catch (InterruptedException ex) {
      LOG.severe(ex.getMessage());
      Thread.currentThread().interrupt();
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

      Comte comte = new Comte(nom);

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
   * Crear les comarques a partir de la posició especificada.
   *
   * @param xinicial
   *          : posició x inicial
   * @param yinicial
   *          : posició y inicial
   * @param xCaselles:
   *          caselles x
   * @param yCaselles:
   *          caselles y
   * @return Llista amb les comarques
   */

  private List<Comarca> crearComarques(int xinicial, int yinicial,
      int casellesAmple, int casellesAlt) {

    Random aleatori = new Random();

    List<Comarca> comarques = new ArrayList<>(xinicial * yinicial);

    int posx = xinicial;
    int posy = yinicial;

    for (int i = 0; i < casellesAlt; i++) {
      for (int j = 0; j < casellesAmple; j++) {
        GRect posicio = new GRect(posx, posy, AMPLECASELLA, ALTCASELLA);
        add(posicio);
        Comarca comarca = new Comarca(posicio, null, false);
        comarques.add(comarca);
        posx += AMPLECASELLA;
      }
      posy += ALTCASELLA;
      posx = xinicial;
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
    GLabel label = missatge("Clica per començar", null);
    waitForClick();
    remove(label);
  }

  private GLabel missatge(String text, String format) {
    GLabel label = new GLabel(text);
    if (format != null) {
      label.setFont(format);
    }
    double posiciox = (getWidth() - label.getWidth()) / 2;
    double posicioy = (getHeight() + label.getAscent()) / 2;
    add(label, posiciox, posicioy);
    return label;
  }

}
