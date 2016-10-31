package net.xaviersala;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;

import acm.program.GraphicsProgram;
import net.xaviersala.conqueridor.Cavaller;
import net.xaviersala.conqueridor.Comte;

/**
 * Plantilla base per fer un programa fent servir les llibreries
 * ACM.
 *
 */
public class App extends GraphicsProgram
{
	private static final int QUANTITAT_DE_CASTELLS = 6;
	private static final String[] NOMS_DELS_COMTES = { "Hug", "Ramon", "Bernat" };
	private static final int CAVALLERS_DEL_COMTE = 2;

	/**
    *
    */
   private static final long serialVersionUID = 1299094805237490891L;
   private static final int AMPLADAPANTALLA = 1024;
   private static final int ALTURAPANTALLA = 768;
   private static final double AMPLE = 40;
   private static final double ALT = 40;
   private static final int AMPLEMAPA = 25;
   private static final int ALTMAPA = 15;
   
   
    Mapa mapa;
    /**
     * Programa principal...
     */
    @Override
    public final void run() {
       setSize(AMPLADAPANTALLA, ALTURAPANTALLA);
       
       mapa = new Mapa("Quadrilàndia", crearComarques(5, 5));       
       mapa.afegirComtes(crearComtes());
       
       clicaPerComencar();
       
       try {
		mapa.start();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }

/**
 * Crea la llista de comptes amb els seus cavallers.
 * 
 * @return llista de comptes
 */
private List<Comte> crearComtes() {
		List<Comte> comptes = new ArrayList<>();
		for(String nom: NOMS_DELS_COMTES) {
			
			// GImage imatgeCompte = new GImage(nom + ".png");			
			Comte comte = new Comte(nom, null);			
			
			for (int i=0; i<CAVALLERS_DEL_COMTE; i++) {
				GImage imatgeCavaller = new GImage("cavaller.png");				
				add(imatgeCavaller);
				comte.afegirCavaller(new Cavaller(nom + i, imatgeCavaller, comte));				
			}
			
			comptes.add(comte);
		}
		return comptes;
	}





private List<Comarca> crearComarques(int xInicial, int yInicial) {
	
		Random aleatori = new Random(); 
		
		List<Comarca> comarques = new ArrayList<Comarca>(xInicial*yInicial);

		int posx = xInicial;
		int posy = yInicial;

		for(int i=0; i < ALTMAPA; i++) {
			for(int j=0; j < AMPLEMAPA; j++) {
				GRect posicio = new GRect(posx, posy, AMPLE, ALT);
				add(posicio);
				Comarca comarca = new Comarca(posicio, null, false);
				comarques.add(comarca);
				posx += AMPLE;
			}			
			posy += ALT;
			posx = xInicial;
		}
		
		// Crear castells en el mapa
		for(int i=0; i< QUANTITAT_DE_CASTELLS; i++) {
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
