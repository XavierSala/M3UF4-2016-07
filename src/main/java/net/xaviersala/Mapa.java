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
	 * @param nom
	 *            Nom de la comarca
	 * @param comarques
	 *            comarques
	 */
	public Mapa(String nom, List<Comarca> comarques) {
		this.nom = nom;
		this.comarques = comarques;
		castells = new ArrayList<>();

		rectanglePais = new GRectangle(comarques.get(0).getPosicio());

		for (Comarca comarca : comarques) {
			rectanglePais = rectanglePais.union(comarca.getPosicio());
			if (comarca.isCastell()) {
				castells.add(comarca);
			}
		}
		comtes = new ArrayList<Comte>();

	}

	/**
	 * Afegir els comptes al mapa.
	 * 
	 * @param comtes
	 *            llista amb els comptes que volen ser el rei
	 */
	public void afegirComtes(List<Comte> comtes) {
		this.comtes = comtes;

		for (Comte comte : this.comtes) {
			LOG.info("Afegit el compte" + comte);
			GRectangle casa = getRandomCastellNoOcupat();
			comte.setCasa(casa);
			comte.posaElsCavallersACasa();
			ferConquestesDelCompte(comte);
		}
	}

	private void ferConquestesDelCompte(Comte comte) {
		for (Cavaller cavaller : comte.getCavallers()) {
			if (!cavaller.isMort()) {
				cavallerConquereix(cavaller);
			}
		}
	}

	/**
	 * El cavaller s'ha mogut i s'ha de mirar si conquereix noves comarques o
	 * no. A més pot ser que conquereixi castells
	 * 
	 * @param cavaller
	 *            cavaller conqueridor
	 * @return Número de castells conquerits en aquesta passada
	 */
	public int cavallerConquereix(Cavaller cavaller) {
		int castellsOcupats = 0;

		for (Comarca comarca : comarques) {
			if (comarca.ocupadaPer(cavaller)) {
				if (comarca.isCastell()) {
					cavaller.addCastellConquerit();
					LOG.info(cavaller + " ... ha ocupat un castell ");
				}
			}
		}
		return castellsOcupats;
	}

	/**
	 * Compta quants castells té el comte especificat
	 * 
	 * @param comte
	 *            comte del que en volem comptar els castells
	 * @return número de castells sota control del comte
	 */
	public int castellsSotaControlDelComte(Comte comte) {
		int numCastells = 0;
		for (Comarca comarca : castells) {
			if (comarca.getCavaller().getComte() == comte) {
				numCastells++;
			}
		}
		return numCastells;
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

	/**
	 * Busca un castell que no estigui ocupat per cap compte.
	 * 
	 * @return castell no ocupat
	 */
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

	/**
	 * Obtenir un castell que no estigui conquerit pel comte.
	 * 
	 * @param comte
	 *            Compte del que no volem el castell.
	 * @return Retorna la posició del castell
	 */
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

	/**
	 * Bucle principal del joc.
	 * 
	 * @throws InterruptedException
	 */
	public void start() throws InterruptedException {
		while (true) {
			for (Comte comte : comtes) {

				moureCavallersDelCompte(comte);
			}
			Thread.sleep(200);
		}
	}

	/**
	 * Mou tots els cavallers d'un compte determinat.
	 * 
	 * - Si ha arribat a un castell o sembla que se'n va de la pantalla li
	 * assigna un nou destí
	 * 
	 * @param comte
	 *            Compte del que es mouen els cavallers
	 */
	private void moureCavallersDelCompte(Comte comte) {
		for (Cavaller cavaller : comte.getCavallers()) {

			if (!cavaller.isMort()) {
				if (!cavaller.mou() || !foraDeRegio(cavaller)) {
					buscarNouDesti(cavaller);
				}
				// Comprovar si toca batallar
				// BATALLA
				comprovarCavallersEnemics(cavaller);
			}
		}
		ferConquestesDelCompte(comte);
	}

	/**
	 * Comprova si el cavaller ha de lluitar amb algú.
	 * 
	 * @param cavallerActual
	 *            cavaller que es comprova
	 */
	private void comprovarCavallersEnemics(Cavaller cavallerActual) {
		for (Comte comte : comtes) {
			if (comte != cavallerActual.getComte()) {
				for (Cavaller cavaller : comte.getCavallers()) {
					if (!cavaller.isMort() && cavaller.estaAProp(cavallerActual)) {
						// Matar-ne un
						batalla(cavallerActual, cavaller);
					}
				}
			}
		}
	}

	private void batalla(Cavaller cavallerActual, Cavaller cavaller) {
		LOG.info("BATALLA!");
		int primer = comarquesSotaControlDelCavaller(cavallerActual);
		int segon = comarquesSotaControlDelCavaller(cavaller);

		int resultat = aleatori.nextInt(primer + segon);

		if (resultat > primer) {
			cavallerActual.setMort();
		} else {
			cavaller.setMort();
		}
	}

	/**
	 * Compta les comarques sota control d'un determinat cavaller.
	 * 
	 * @param cavaller
	 *            Cavaller a comprovar
	 */
	public int comarquesSotaControlDelCavaller(Cavaller cavaller) {

		int comarquesSotaControl = 0;

		for (Comarca comarca : comarques) {
			if (comarca.isDelCavaller(cavaller)) {
				comarquesSotaControl++;
			}
		}
		return comarquesSotaControl;
	}

	/**
	 * Comprovar si un cavaller se n'està anant de la regió (COVARD!).
	 *
	 * @param cavaller
	 *            cavaller que comprovem
	 * @return Diu si el cavaller està marxant
	 */
	private boolean foraDeRegio(Cavaller cavaller) {
		GRectangle r = cavaller.getPosicio().intersection(rectanglePais);
		return r.equals(cavaller.getPosicio());
	}

	/**
	 * Ordena a un cavaller anar cap a un lloc (en principi només seran
	 * castells).
	 * 
	 * @param cavaller
	 *            cavaller al que se li dóna una ordre
	 */
	private void buscarNouDesti(Cavaller cavaller) {

		GRectangle nouDesti = getRandomCastellNoPropi(cavaller.getComte());
		cavaller.setDesti(nouDesti);
		LOG.info(" ... " + cavaller + " va a " + nouDesti);
	}
}
