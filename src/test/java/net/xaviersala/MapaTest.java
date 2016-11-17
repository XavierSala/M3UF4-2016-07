package net.xaviersala;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import acm.graphics.GRectangle;
import net.xaviersala.conqueridor.Cavaller;
import net.xaviersala.conqueridor.Comte;

public class MapaTest {

    @Mock
    private Comte comte;

    @Mock
    private Comte comte2;

    @Mock
    private Comarca comarca1;

    @Mock
    private Comarca comarca2;

    @Mock
    private Cavaller cavaller;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private Mapa mapa;

    /**
     * Iniciar-ho tot.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {

        when(comarca1.isCastell()).thenReturn(true);
        when(comarca1.getPosicio()).thenReturn(new GRectangle(10,10,10,10));
        when(comarca2.getPosicio()).thenReturn(new GRectangle(20,10,10,10));
        when(comte.getNom()).thenReturn("Un");
        when(comte2.getNom()).thenReturn("Dos");
        List<Comarca> comarques = new ArrayList<Comarca>();
        comarques.add(comarca1);
        comarques.add(comarca2);

        mapa = new Mapa("Quadrilàndia", comarques);
    }

    /**
     * Afegir els comptes al mapa
     * @throws InterruptedException
     */
    @Test
    public void testComprovaQueAcabaSiNomesHiHaUnCompte() throws InterruptedException {
        List<Comte> comtes = Arrays.asList(comte);
        mapa.afegirComtes(comtes);

        assertEquals("Un", mapa.start());
    }

    /**
     * Afegir els comptes al mapa
     * @throws InterruptedException
     */
    @Test
    public void testComprovaQueAcabaSiHiHaDosComptes() throws InterruptedException {
        when(comte2.isDerrotat()).thenReturn(true);
        List<Comte> comtes = Arrays.asList(comte, comte2);
        mapa.afegirComtes(comtes);

        String resultat = mapa.start();
        assertTrue(resultat.matches("Un"));

    }

    /**
     * Afegir els comptes al mapa
     * @throws InterruptedException
     */
    @Test
    public void testComprovaQueAcabaSiHiHaDosComptes2() throws InterruptedException {
        when(comte.isDerrotat()).thenReturn(true);
        List<Comte> comtes = Arrays.asList(comte, comte2);
        mapa.afegirComtes(comtes);

        String resultat = mapa.start();
        assertTrue(resultat.matches("Dos"));

    }

}


