package net.xaviersala;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GPoint;
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
   
   private static final String[] NOMS = { "Hug", "Ramon", "Bernat" };


    Mapa mapa;
    /**
     * Programa principal...
     */
    @Override
    public final void run() {
       setSize(AMPLADAPANTALLA, ALTURAPANTALLA);
       
       mapa = new Mapa("Quadrilàndia", generarCaselles(5, 5));
       
       mapa.afegirComtes(crearComtes());
       
       clicaPerComencar();
    }





private List<Comte> crearComtes() {
		List<Comte> comptes = new ArrayList<>();
		for(String nom: NOMS) {
			// GImage imatgeCompte = new GImage(nom + ".png");
			GPoint lloc = mapa.getRandomCastell();
			Comte comte = new Comte(null, lloc);			
			
			for (int i=0; i<2; i++) {
				GImage imatgeCavaller = new GImage("cavaller.png");
				imatgeCavaller.setLocation(lloc);
				add(imatgeCavaller);
				comte.afegirCavaller(new Cavaller(nom + i, imatgeCavaller, comte));
			}
		}
		return comptes;
	}





private List<Comarca> generarCaselles(int xInicial, int yInicial) {
	
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
		
		
		for(int i=0; i<6; i++) {
			int quina = aleatori.nextInt(comarques.size());
			comarques.get(quina).setCastell();
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
