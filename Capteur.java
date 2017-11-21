import java.util.*;
import java.awt.Point;

public class Capteur {
    int num_ligne;
    int num_colonne;
    boolean acces_source; 
    
    public Capteur() {
    }

    public Capteur(int num_ligne, int num_colonne, boolean acces_source){
	this.num_ligne = num_ligne;
	this.num_colonne = num_colonne;
	this.acces_source = acces_source;
    }

    public static boolean get_source (Map<Point, Capteur> map, int i, int j){
	boolean res = false;
	Point p = new Point(i,j);
	if (map.get(p) != null){
	    res = map.get(p).acces_source;
	}
	else {}
	
	return res;
    }

    public static Capteur add_capteur (Map<Point, Capteur> map, Grille grille, int i, int j, int R_com){
	Capteur res;
	res = new Capteur(i,j,false);

	// si le capteur est relié directement à la source
	if (Math.sqrt(i*i + j*j) <= R_com){
	    res.acces_source = true;
	}

	// sinon on regarde s'il est proche d'un capteur rejoignant la source
	for (int k = Math.max(i-R_com, 0); k< Math.min(k+R_com, grille.nb_ligne); ++k){
	    for (int l = Math.max(j-R_com,0); l<Math.min(j+R_com, grille.nb_colonne); ++l){
		if (Math.sqrt((i-k) * (i-k) + (j-l) * (j-l)) <= R_com){
		    res.acces_source = res.acces_source || get_source(map, k, l);
		}
	    }
	}
	return res;
    }
}
