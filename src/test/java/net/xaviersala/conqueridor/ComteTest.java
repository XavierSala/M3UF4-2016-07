package net.xaviersala.conqueridor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import acm.graphics.GImage;
import acm.graphics.GRectangle;

public class ComteTest {

    private static final GRectangle CASA = new GRectangle(10,10,10,10);

    @Mock
    Cavaller  cavaller;

    @Mock
    Cavaller cavaller2;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    Comte comte;

    @Before
    public void setUp() throws Exception {
        comte = new Comte("Gaufred");

    }

    @Test
    public void testGetDadesBasiques() {
        assertEquals("Comte Gaufred", comte.getNom());
        assertNotNull(comte.getColor());
        assertTrue(comte.getCavallers().isEmpty());
        assertEquals("Comte Gaufred [[]]", comte.toString());
    }

    @Test
    public void testLlogarCavallers() {
        assertTrue("Al començar no hi ha d'haver cavallers", comte.getCavallers().isEmpty());
        comte.afegirCavaller(cavaller);
        assertTrue("Hi hauria d'haver un cavaller", comte.getCavallers().size() == 1);
        Cavaller c = (Cavaller) comte.getCavallers().get(0);
        assertEquals("El cavaller no és el que s'ha entrat", cavaller, c);

        comte.afegirCavaller(cavaller2);
        assertTrue("Hi hauria d'haver dos cavallers", comte.getCavallers().size() == 2);
        c = (Cavaller) comte.getCavallers().get(1);
        assertEquals("El cavaller no és el que s'ha entrat", cavaller2, c);

    }


    /**
     * Comprova si el comte es pot matar.
     */
    @Test
    public void testMataComte() {

        // Neix no derrotat
        assertTrue("El comte ha de neixer no derrotat", !comte.isDerrotat());

        // Derrotem el comte
        comte.derrotat();
        assertTrue("El comte ha d'estar derrotat", comte.isDerrotat());

        // Repetir-ho no canvia res
        comte.derrotat();
        assertTrue("El comte ha de continuar derrotat", comte.isDerrotat());
    }


    @Test
    public void testCasa() {
        comte.setCasa(CASA);
        comte.afegirCavaller(cavaller);
        comte.afegirCavaller(cavaller2);

        comte.posaElsCavallersACasa();

        verify(cavaller).setPosicio(CASA);
        verify(cavaller).setDesti(CASA);
        verify(cavaller2).setPosicio(CASA);
        verify(cavaller2).setDesti(CASA);
    }
}
