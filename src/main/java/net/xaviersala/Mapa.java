package net.xaviersala;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import acm.graphics.GRectangle;
import net.xaviersala.conqueridor.Cavaller;
import net.xaviersala.conqueridor.Comte;

public class Mapa {
	
	static final Logger LOG = Logger.getLogger("Mapa");


	private Random aleatori = new Random();
	
	private String nom; 	
	private List<Comarca> comarques;
	private List<Comarca> castells;	
	private List<Comte> comtes;


	private GRectangle rectanglePais;

	/**
	 * Crea un mapa a partir de les caselles que se li han especificat.
	 * 
	 * @param nom Nom de la comarca
	 * @param comarques comarques
	 */
	public Mapa(String nom, List<Comarca> comarques) {
		this.nom = nom;
		this.comarques = comarques;		
		castells = new ArrayList<>();
		
		rectanglePais = new GRectangle(comarques.get(0).getPosicio());
		
		for(Comarca comarca: comarques) {					
			rectanglePais = rectanglePais.union(comarca.getPosicio());
			if (comarca.isCastell()) {
				castells.add(comarca);
			}
		}
		comtes = new ArrayList<Comte>();

	}
	
	/**
	 * Afegir els comptes al mapa.
	 * @param comtes llista amb els comptes que volen ser el rei
	 */
	public void afegirComtes(List<Comte> comtes) {
		this.comtes = comtes;	
		
		for(Comte comte: this.comtes) {
			LOG.info("Afegit el compte" + comte);
			GRectangle casa = getRandomCastellNoOcupat();
			comte.setCasa(casa);
			comte.posaElsCavallersACasa();
			ferConquestesDelCompte(comte);			
		}
	}

	private void ferConquestesDelCompte(Comte comte) {
		for(Cavaller cavaller: comte.getCavallers()) {
			cavallerConquereix(cavaller);
		}
	}
	
	/**
	 * El cavaller s'ha mogut i s'ha de mirar si conquereix noves
	 * comarques o no. A més pot ser que conquereixi castells
	 *  
	 * @param cavaller cavaller conqueridor
	 * @return Número de castells conquerits en aquesta passada
	 */
	public int cavallerConquereix(Cavaller cavaller) {
		int castellsOcupats = 0;

		for(Comarca comarca: comarques) {
			if (comarca.ocupadaPer(cavaller)) {
				if (comarca.isCastell() ) {	
					LOG.info(cavaller + " ... Ocupat un castell ");
					castellsOcupats++;
				}
			}
		}
		
		return castellsOcupats;
	}
	
	/**
	 * Compta quants castells té el comte especificat
	 * 
	 * @param comte comte del que en volem comptar els castells
	 * @return número de castells sota control del comte
	 */
	public int castellsSotaControlDelComte(Comte comte) {
		int numCastells = 0;
		for(Comarca comarca: castells) {
			if (comarca.getCavaller().getComte() == comte) {
				numCastells++;
			}
		}
		return numCastells;
	}
	
	/**
	 * Compta les comarques sota control d'un determinat cavaller.
	 * 
	 * @param cavaller Cavaller a comprovar
	 */
	public int comarquesSotaControlDelCavaller(Cavaller cavaller) {
		
		int comarquesSotaControl = 0;
		
		for (Comarca comarca: comarques) {
			if (comarca.isDelCavaller(cavaller)) {
				comarquesSotaControl++;
			}
		}
		return comarquesSotaControl;		
	}



	/**
	 * Obtenir un castell de forma aleatòria.
	 * 
 	 * @return posició del castell
	 */
	private int getRandomCastell() {
		
		if (castells.size() > 0) {						
					
			int castellTriat = aleatori.nextInt(castells.size());
			return castellTriat;
		}
		return -1;
	}
	
	public GRectangle getRandomCastellNoOcupat() {
		
		int quinCastell = getRandomCastell();
		if (quinCastell == -1) {
			return null;
		}
		LOG.info("... Provar si el castell " + quinCastell + " està lliure");
		Comarca castell = castells.get(quinCastell);
		
		while (castell.isOcupada()) {
			quinCastell = getRandomCastell();
			LOG.info("... Provar si el castell " + quinCastell + " està lliure");
			castell = castells.get(quinCastell);
		} 		
		LOG.info("CASTELL " + quinCastell + " lliure!");
		return castell.getPosicio();
	}
	
	public GRectangle getRandomCastellNoPropi(Comte comte) {
				
		int quinCastell = getRandomCastell();
		if (quinCastell == -1) {
			return null;
		}

		Comarca castell = castells.get(quinCastell);		
		while (castell.isDelComte(comte)) {
			quinCastell = getRandomCastell();
			castell = castells.get(quinCastell);
		} 		
		
		LOG.info("Atacar el CASTELL " + quinCastell);
		return castell.getPosicio();
	}

	public void start() throws InterruptedException {
		while(true) {
			for(Comte comte: comtes) {
				// Moure els cavallers del compte
				moureCavallersDelCompte(comte); 
				// Comprovar si hi ha una batalla
				
			
			}
			Thread.sleep(200);
		}
	}

	private void moureCavallersDelCompte(Comte comte) {
		for(Cavaller cavaller: comte.getCavallers()) {
			if (!cavaller.mou() || !foraDeRegio(cavaller)) {
				buscarNouDesti(cavaller);
			}
		}
		ferConquestesDelCompte(comte);		
	}

	private boolean foraDeRegio(Cavaller cavaller) {
		GRectangle r = cavaller.getPosicio().intersection(rectanglePais);
		return r.equals(cavaller.getPosicio());
	}

	private void buscarNouDesti(Cavaller cavaller) {
		
		GRectangle nouDesti = getRandomCastellNoPropi(cavaller.getComte());
		cavaller.setDesti(nouDesti);
		LOG.info(" ... " + cavaller + " va a " + nouDesti);
	}
}
