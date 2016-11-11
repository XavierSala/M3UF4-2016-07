package net.xaviersala;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.*;

import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.graphics.GRectangle;
import net.xaviersala.conqueridor.Cavaller;
import net.xaviersala.conqueridor.Comte;

public class ComarcaTest {

    private static final GRectangle CASELLA = new GRectangle(10,10,10,10);
    private static final GRectangle CASELLA2 = new GRectangle(60,60,10,10);

    @Mock
    private Cavaller cavaller;

    @Mock
    private Cavaller cavaller2;

    @Mock
    private GRect rectangle;

    @Mock
    private GImage imatgeCastell;

    @Mock
    private Comte comte;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private Comarca comarca;
    private Comarca castell;

    @Before
    public void setUp() throws Exception {

        when(rectangle.getBounds()).thenReturn(CASELLA);
        comarca = new Comarca(rectangle, null ,false);
        castell = new Comarca(rectangle, imatgeCastell, true);

    }

    @Test
    public void testCondicionsBasiques() {
        assertEquals(CASELLA, comarca.getPosicio());
        assertEquals(CASELLA, castell.getPosicio());

        assertFalse(comarca.isCastell());
        assertTrue(castell.isCastell());

        assertFalse(comarca.isOcupada());
        assertFalse(castell.isOcupada());
    }

    @Test
    public void testFuncionamentDelsColors() {
        comarca.setColor(Color.BLUE);
        verify(rectangle).setFillColor(Color.BLUE);
    }


    /**
     * Convertir una casella en un castell i passar-li la imatge.
     */
    @Test
    public void testConvertirUnaComarcaEnCastell2() {
        assertFalse(comarca.isCastell());

        comarca.setCastell(imatgeCastell);

        assertTrue(comarca.isCastell());
        verify(imatgeCastell).setBounds(CASELLA);
    }

    /**
     * Intenta ocupar una casella que no està ocupada per ningú.
     */
    @Test
    public void testOcuparUnaComarcaQueNoEstaOcupada() {

        when(cavaller.getPosicio()).thenReturn(CASELLA);
        assertFalse(comarca.isOcupada());

        assertTrue(comarca.intentaOcuparla(cavaller));

        assertTrue(comarca.isOcupada());
        assertTrue(comarca.isDelCavaller(cavaller));
    }

    /**
     * Intenta ocupar una casella que està ocupada per el mateix cavaller
     */
    @Test
    public void testOcuparUnaComarcaQueEsMeva() {
        when(cavaller.getPosicio()).thenReturn(CASELLA);

        comarca.intentaOcuparla(cavaller);
        assertTrue(comarca.isOcupada());
        assertTrue(comarca.isDelCavaller(cavaller));

        assertFalse(comarca.intentaOcuparla(cavaller));
    }

    /**
     * Intenta ocupar una casella que està ocupada per un altre cavaller
     */
    @Test
    public void testOcuparUnaComarcaQueEsDUnAltre() {
        when(cavaller.getPosicio()).thenReturn(CASELLA);
        when(cavaller2.getPosicio()).thenReturn(CASELLA);
        ocupaComarca(cavaller2);

        assertTrue("El cavaller ha de conquerir la casella", comarca.intentaOcuparla(cavaller));

        assertTrue(comarca.isOcupada());
        assertTrue(comarca.isDelCavaller(cavaller));

    }

    /**
     * Intenta ocupar una comarca amb la que no xoca i no és de ningú.
     */
    @Test
    public void testOcuparUnaComarcaLlunyanaNoEsPossibleSiNoEsDeNingu() {
        when(cavaller.getPosicio()).thenReturn(CASELLA2);
        assertFalse(comarca.isOcupada());

        assertFalse("No es poden ocupar comarques llunyanes", comarca.intentaOcuparla(cavaller));

        assertFalse(comarca.isOcupada());

    }

    /**
     * Intenta ocupar una comarca amb la que no xoca i és d'un altre.
     */
    @Test
    public void testOcuparUnaComarcaLlunyanaNoEsPossibleSiTePropietari() {
        when(cavaller.getPosicio()).thenReturn(CASELLA2);
        when(cavaller2.getPosicio()).thenReturn(CASELLA);
        ocupaComarca(cavaller2);

        assertFalse("No es poden ocupar comarques llunyanes", comarca.intentaOcuparla(cavaller));

        assertTrue(comarca.isOcupada());
        assertTrue(comarca.isDelCavaller(cavaller2));
        assertFalse(comarca.isDelCavaller(cavaller));

    }

    /**
     * Les comarques buides no són de cap compte
     */
    @Test
    public void testComarcaBuidaNoTeComte() {
        assertFalse(comarca.isOcupada());

        assertFalse(comarca.isDelComte(comte));
    }

    /**
     * Comprova que una comarca d'un dels cavallers del comte és del comte.
     */
    @Test
    public void testComarcaDelCompte() {
        when(cavaller.getPosicio()).thenReturn(CASELLA);
        when(cavaller.getComte()).thenReturn(comte);

        ocupaComarca(cavaller);

        assertTrue(comarca.isDelComte(comte));
    }

    /**
     * Comprova que una comarca d'un cavaller d'un altre no és del comte.
     */
    @Test
    public void testComarcaDUnAltre() {
        when(cavaller.getPosicio()).thenReturn(CASELLA);
        when(cavaller.getComte()).thenReturn(new Comte("X"));

        ocupaComarca(cavaller);

        assertFalse(comarca.isDelComte(comte));
    }

    /*
     * Un cavaller ocupa la comarca
     */
    private void ocupaComarca(Cavaller cavaller) {
        assertTrue("El cavaller ha d'ocupar la casella", comarca.intentaOcuparla(cavaller));
        assertTrue(comarca.isOcupada());
        assertTrue(comarca.isDelCavaller(cavaller));
    }
}
