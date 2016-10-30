package net.xaviersala;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import acm.graphics.GPoint;
import net.xaviersala.conqueridor.Cavaller;
import net.xaviersala.conqueridor.Comte;

public class Mapa {

	private String nom;

	// Llista de les comarques i els castells
	private List<Comarca> comarques;
	private List<Comarca> castells;
	
	private List<Comte> comtes;

	
	public Mapa(String nom, List<Comarca> comarques) {
		this.nom = nom;
		this.comarques = comarques;		
		castells = new ArrayList<>();
				
		for(Comarca comarca: comarques) {
			if (comarca.isCastell()) {
				castells.add(comarca);
			}
		}
		comtes = new ArrayList<Comte>();
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
					// TODO Sumar 1 a castells ocupats del cavaller
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
	 * Afegir els comptes al mapa.
	 * @param comtes llista amb els comptes que volen ser el rei
	 */
	public void afegirComtes(List<Comte> comtes) {
		this.comtes = comtes;		
	}

	/**
	 * Obtenir un castell de forma aleatòria.
	 * 
 	 * @return posició on ha d'estar
	 */
	public GPoint getRandomCastell() {
		
		if (castells.size() > 0) {
						
			Random aleatori = new Random(); 			
			int castellTriat = aleatori.nextInt(castells.size());
			return castells.get(castellTriat).getPosicio();
		}
		return null;
	}
}
