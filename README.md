#FpInfor #DawMp03Uf04 #Programació

Las conquesta de Quadrilàndia
-------------------------------
S’ha mort el rei de Quadrilandia i tots els cavallers de la contrada tenen intenció de ocupar el lloc vacant. Són temps de guerra…

L’anterior rei era un fanàtic de les matemàtiques i va canviar totes les comarques de la contrada i les va fer quadrades.

![Mapa de Quadrilàndia](imatges/quadrilandia.png)

L’objectiu de tots els Comtes de la contrada és aconseguir el domini de tots els castells per convertir-se en el rei… 

* Cada un dels comptes té dos cavallers i els fa servir per conquerir el regne.
* Cada cavaller té 1000 punts de vida
 
![Cavallers del comtat](imatges/comtes.png)

Els pagesos del país són prou espavilats per passar-se al bàndol del comte que els ha visitat: 
* Si el compte verd passa per la seva comarca, els pagesos es fan partidaris del compte verd 
(només cal que hi toqui en part perquè la gent passi al seu bàndol)

![Conquesta](imatges/conquesta.png)


* Si després hi passa un cavaller del compte groc, es fan partidaris del compte groc … 
etc …

![Batallant](imatges/play.png)


Quan es troben dos cavallers de diferent compte en una casella es produeix una batalla. 

* La provabilitat de guanyar dependrà del nombre de comarques que estiguin a favor d’aquest cavaller.
* El cavaller derrotat perdrà la meitat dels punts de vida, i si encara no està mort fugirà cap al seu castell. 

![Batalla entre cavallers](imatges/batalla.png)


Si un compte domina tots els castells, o ha eliminat a tots els altres cavallers es converteix en rei

Tasca
----------------
Creeu en Java un programa que permeti veure com transcorre la lluita pel regnat del regne de Quadrilàndia

1. Hem de poder determinar quants Comtes hi ha
2. S’han de poder definir el número de castells. Els castells es generaran automàticament en el mapa al començar la partida

### OPCIONAL: 

* Es pot fer que un jugador pugui control·lar els cavallers d'un dels comptes definint cap a on han d'anar
    * Clic sobre el cavaller i clic a on ha d'anar i el cavaller s'hi dirigirà (el destí sempre serà el mig de la casella on s'ha clicat)

### Exemple de funcionament

[![Vídeo](imatges/video.png)](https://youtu.be/_QU-LyPIezM)
