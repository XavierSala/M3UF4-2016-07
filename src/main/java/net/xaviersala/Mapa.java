package net.xaviersala;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import acm.graphics.GRectangle;
import net.xaviersala.conqueridor.Cavaller;
import net.xaviersala.conqueridor.Comte;

/**
 * Defineix el mapa del territori a partir d'una llista de comarques.
 *
 * @author xavier
 *
 */
public class Mapa {

    private static final Logger LOG = Logger.getLogger("Mapa");

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
        comtes = new ArrayList<>();

    }

    /**
     * Afegir els comtes al mapa.
     *
     * @param comtes
     *            llista amb els comtes que volen ser el rei
     */
    public void afegirComtes(List<Comte> comtes) {
        this.comtes = comtes;

        for (Comte comte : this.comtes) {
            LOG.info("Afegit el comte" + comte);
            GRectangle casa = getRandomCastellNoOcupat();
            comte.setCasa(casa);
            comte.posaElsCavallersACasa();
            ferConquestesDelComte(comte);
        }
    }

    /**
     * Comprova quines conquestes han fet els cavallers del compte.
     *
     * @param comte
     *            compte a comprovar
     */
    private void ferConquestesDelComte(Comte comte) {
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
    private int cavallerConquereix(Cavaller cavaller) {
        int castellsOcupats = 0;

        for (Comarca comarca : comarques) {
            if (comarca.intentaOcuparla(cavaller) && comarca.isCastell()) {
                cavaller.addCastellConquerit();
                LOG.info(cavaller + " ... ha ocupat un castell ");

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
    private int castellsSotaControlDelComte(Comte comte) {
        int numCastells = 0;
        for (Comarca castell : castells) {
            if (castell != null && castell.isDelComte(comte)) {
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

        if (!castells.isEmpty()) {
            return aleatori.nextInt(castells.size());
        }
        return -1;
    }

    /**
     * Busca un castell que no estigui ocupat per cap comte.
     *
     * @return castell no ocupat
     */
    private GRectangle getRandomCastellNoOcupat() {

        int quinCastell = getRandomCastell();
        if (quinCastell == -1) {
            return null;
        }
        LOG.finer("... Provar castell " + quinCastell);
        Comarca castell = castells.get(quinCastell);

        while (castell.isOcupada()) {
            quinCastell = getRandomCastell();
            LOG.finer("... Provar castell " + quinCastell);
            castell = castells.get(quinCastell);
        }

        LOG.fine("castell " + quinCastell + " lliure!");
        return castell.getPosicio();
    }

    /**
     * Obtenir un castell que no estigui conquerit pel comte.
     *
     * @param comte
     *            Comte del que no volem el castell.
     * @return Retorna la posició del castell
     */
    private GRectangle getRandomCastellNoPropi(Comte comte) {

        int quinCastell = getRandomCastell();
        if (quinCastell == -1) {
            return null;
        }
        LOG.finer("... Provar si el castell és del comte " + quinCastell);
        Comarca castell = castells.get(quinCastell);
        while (castell.isDelComte(comte)) {
            quinCastell = getRandomCastell();
            LOG.finer("... Provar si el castell és del comte " + quinCastell);
            castell = castells.get(quinCastell);
        }

        LOG.fine("Atacar el castell " + quinCastell);
        return castell.getPosicio();
    }

    /**
     * Bucle principal del joc.
     *
     * @throws InterruptedException
     */
    public String start() throws InterruptedException {
        Comte guanyador = null;

        while (guanyador == null) {
            guanyador = moureComtes();
            Thread.sleep(150);
        }

        dominaTotesLesComarques(guanyador);

        LOG.info("Ja tenim nou rei de " + nom + ": El " + guanyador.getNom() + "!");
        return guanyador.getNom();
    }

    /**
     * El compte definit domina totes les comarques.
     *
     * @param guanyador Compte que dominarà totes les caselles
     */
    private void dominaTotesLesComarques(Comte guanyador) {
        for (Comarca casella : comarques) {
            casella.setColor(guanyador.getColor());
        }
    }

    /**
     * Moure tots els comptes per veure si hi ha algun guanyador.
     *
     * @return El compte guanyador o null si no ha guanyat ningú
     */
    private Comte moureComtes() {
        int numeroDeComtes = 0;
        for (Comte comte : comtes) {
            if (!comte.isDerrotat()) {
                boolean estaViu = moureCavallersDelComte(comte);
                if (!estaViu) {
                    comte.derrotat();
                }
                // Si el compte control·la tots els castells ha guanyat
                if (castellsSotaControlDelComte(comte) == castells.size()) {
                    return comte;
                }
                numeroDeComtes++;
            }
        }
        // Si només queda un compte viu, és el guanyador
        if (numeroDeComtes == 1) {
            return buscaElComteGuanyador();
        }
        return null;
    }

    // Cerca quin és el compte qeu no està mort
    private Comte buscaElComteGuanyador() {
        for (Comte comte : comtes) {
            if (!comte.isDerrotat()) {
                return comte;
            }
        }
        return null;
    }

    /**
     * Mou tots els cavallers d'un comte determinat.
     *
     * - Si ha arribat a un castell o sembla que se'n va de la pantalla li
     * assigna un nou destí
     *
     * @param comte
     *            Comte del que es mouen els cavallers
     * @return
     */
    private boolean moureCavallersDelComte(Comte comte) {
        boolean algunViu = false;

        for (Cavaller cavaller : comte.getCavallers()) {

            if (!cavaller.isMort()) {
                algunViu = true;

                if (!cavaller.mou() || !foraDeRegio(cavaller)) {
                    LOG.finer("... Cercar nou destí pel " + cavaller);
                    buscarNouDesti(cavaller);
                }

                comprovarCavallersEnemics(cavaller);
            }
        }
        ferConquestesDelComte(comte);
        return algunViu;
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
                comprovaSiHiHaUnaBatalla(cavallerActual, comte);
            }
        }
    }

    /**
     * Comprova si el cavaller té una batalla amb un del compte especificat.
     *
     * @param cavallerActual
     *            cavaller actual
     * @param comte
     *            compte del que es volen comprovar els cavallers
     */
    private void comprovaSiHiHaUnaBatalla(Cavaller cavallerActual, Comte comte) {
        for (Cavaller cavaller : comte.getCavallers()) {
            if (!cavaller.isMort() && cavaller.estaAProp(cavallerActual)) {
                // Matar-ne un
                batalla(cavallerActual, cavaller);
            }
        }
    }

    /**
     * Batalla entre dos cavallers. La batalla es resol amb un número aleatòri
     * però les possibilitats de victòria depenen de la quantitat de comarques
     * que estiguin en poder d'aquest cavaller.
     *
     * @param primerCavaller
     *            primer cavaller
     * @param segonCavaller
     *            segon cavaller
     */
    private void batalla(Cavaller primerCavaller, Cavaller segonCavaller) {

        int comarquesPrimer = comarquesSotaControlDelCavaller(primerCavaller);
        int comarquesSegon = comarquesSotaControlDelCavaller(segonCavaller);

        LOG.info(
                "** BATALLA!" + primerCavaller + ":" + comarquesPrimer + " vs " + segonCavaller + ":" + comarquesSegon);

        int suma = comarquesPrimer + comarquesSegon;

        // BUG: Peta quan hi ha batalla entre cavallers que no tenen territoris
        if (suma == 0) {
            suma++;
        }

        int resultat = aleatori.nextInt(suma);

        if (resultat > comarquesPrimer) {
            LOG.info("***** Victòria del " + segonCavaller);
            primerCavaller.setMort();
        } else {
            LOG.info("***** Victòria del " + primerCavaller);
            segonCavaller.setMort();
        }
    }

    /**
     * Compta les comarques sota control d'un determinat cavaller.
     *
     * @param cavaller
     *            Cavaller a comprovar
     */
    private int comarquesSotaControlDelCavaller(Cavaller cavaller) {

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
        LOG.fine(" ... " + cavaller + " va a " + nouDesti);
    }

}
