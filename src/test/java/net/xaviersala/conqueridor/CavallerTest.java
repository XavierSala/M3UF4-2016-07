package net.xaviersala.conqueridor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import acm.graphics.GImage;
import acm.graphics.GRectangle;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.awt.Color;

public class CavallerTest {

  private static final GRectangle FORA2 = new GRectangle(0, 0, 10, 10);
  private static final GRectangle FORA = new GRectangle(40, 40, 10, 10);
  private static final GRectangle CASA = new GRectangle(20, 20, 10, 10);
  private static final GRectangle XOCA = new GRectangle(30, 30, 10, 10);

  @Mock
  private Comte delComte;

  @Mock
  private GImage imatge;

  @Mock
  private GImage imatge2;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  private Cavaller cavaller;

  /**
   * Crea l'objecte de proves.
   *
   * @throws Exception Alguna cosa ha fallat
   */
  @Before
  public void setUp() throws Exception {

    when(delComte.getColor()).thenReturn(Color.RED);
    cavaller = new Cavaller("Pep", imatge, delComte);
  }

  /**
   * Recuperar les dades bàsiques.
   */
  @Test
  public void testRecuperarDades() {
    assertEquals("Pep", cavaller.getNom());
    assertEquals("Cavaller Pep, (0,1000)", cavaller.toString());
    cavaller.addCastellConquerit();
    assertEquals("Cavaller Pep, (1,1000)", cavaller.toString());

  }

  /**
   * Comprova que el compte es retorna bé i que el cavaller ha recollit el color
   * adequat.
   */
  @Test
  public void testGetComte() {

    Comte noucavaller = cavaller.getComte();

    assertEquals("El comte no és el que tenia", delComte, noucavaller);
    assertEquals("El cavaller no té el color del comte", Color.RED, cavaller.getColor());
  }

  /**
   * Comprova el funcionament del procés de morir.
   */
  @Test
  public void testSiEsMor() {

    assertFalse("El cavaller ha de crear-se viu", cavaller.isMort());

    cavaller.setMort();

    // Comprovar que s'ha amagat la imatge
    verify(imatge).setVisible(false);
    assertTrue("El cavaller ha d'estar mort", cavaller.isMort());

  }

  @Test
  public void testSiEstaAProp() {

    Cavaller cavaller2 = new Cavaller("Pito", imatge2, delComte);

    when(imatge.getBounds()).thenReturn(CASA);
    when(imatge2.getBounds())
        // Primer ha de coincidir ...
        .thenReturn(XOCA)
        // Després no...
        .thenReturn(FORA);

    assertTrue(cavaller.estaAProp(cavaller2));
    assertFalse(cavaller.estaAProp(cavaller2));

  }

  @Test
  public void testDestinacions() {
    when(imatge.getBounds()).thenReturn(CASA);
    when(imatge.getBounds()).thenReturn(CASA);

    when(imatge.getX()).thenReturn(CASA.getX());
    when(imatge.getY()).thenReturn(CASA.getY());
    when(imatge.getWidth()).thenReturn(CASA.getWidth());
    when(imatge.getHeight()).thenReturn(CASA.getHeight());

    cavaller.setPosicio(CASA);
    assertEquals("El cavaller no està a la posició correcta", CASA, cavaller.getPosicio());

    cavaller.setDesti(CASA);

    // No s'ha de moure
    assertFalse(cavaller.mou());

    // No s'ha de moure però la posa al destí
    cavaller.setDesti(XOCA);
    assertFalse(cavaller.mou());
    verify(imatge).setLocation(XOCA.getX(), XOCA.getY());

    // Si que s'ha de moure...
    cavaller.setDesti(FORA);
    assertTrue(cavaller.mou());

    cavaller.setDesti(FORA2);
    assertTrue(cavaller.mou());
    verify(imatge, times(2)).movePolar(anyDouble(), anyDouble());

  }

  // was the method called twice?
  // verify(test, times(2)).getUniqueId();

}
