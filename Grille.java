import java.util.*;
import java.awt.Point;

public class Grille {
    int nb_ligne; // nombre de lignes de la grille
    int nb_colonne; // nombre de colonnes de la grille
    int R_capt; // rayon de captation des capteurs de la grille 
    int R_com; // rayon de communication des capteurs de la grille
    Cible[][] g; // matrice formant la grille
    
    public Grille() {
    }

    // creation d'une nouvelle grille
    public Grille(int nb_ligne, int nb_colonne, int R_capt, int R_com){
    	this.nb_ligne = nb_colonne;
    	this.nb_colonne = nb_colonne;
    	this.R_capt = R_capt;
    	this.R_com = R_com;
    	this.g = new Cible[nb_ligne][nb_colonne];

    	for (int i=0; i<nb_ligne; ++i){
    		for (int j=0; j<nb_colonne; ++j){
    			g[i][j] = new Cible(false);
    		}
    	}
    }

    // initialisation de la grille et de la premiere famille de capteurs
    public static Map<Point, Capteur> initialisation_grille(Grille grille, int R_capt, int R_com){
	Map<Point, Capteur> map_capteur;
	map_capteur = new HashMap<>();
	Point p;
	
	// Pour R_com = 1
	if (R_capt == 1){
	    for (int i=0; i<(grille.nb_ligne/2); ++i){
	    	for (int j=0; j<grille.nb_colonne; ++j){
	    		p = new Point(2*i, j);
	    		grille.g[2*i][j].capteur = true;
	    		map_capteur.put(p, Capteur.add_capteur(map_capteur,grille, 2*i, j, R_com)); 
	    	}
	    	p = new Point (2*i+1, 0);
	    	grille.g[2*i+1][0].capteur = true;
	    	map_capteur.put(p, Capteur.add_capteur(map_capteur,grille, 2*i+1, 0, R_com)); 
		
	    	grille.g[0][0].capteur = false; // pas de capteur sur la source
	    	map_capteur.remove(new Point(0,0));
	    }
	    for (int j=0; j<grille.nb_colonne; ++j){
	    	p = new Point (grille.nb_ligne-1, j);
			grille.g[grille.nb_ligne-1][j].capteur = true;
			map_capteur.put(p, Capteur.add_capteur(map_capteur,grille, grille.nb_ligne-1, j, R_com)); 
	    }
	}
	
	else if (R_capt == 2){
	    for (int i=0; i<(grille.nb_ligne/2); ++i){
	    	for (int j=0; j<grille.nb_colonne/2; ++j){
	    		p = new Point(2*i, 2*j);
	    		grille.g[2*i][2*j].capteur = true;
	    		map_capteur.put(p, Capteur.add_capteur(map_capteur,grille, 2*i, 2*j, R_com)); 
	    	}
	    	p = new Point(2*i,grille.nb_colonne-1);
	    	grille.g[2*i][grille.nb_colonne-1].capteur = true;
	    	map_capteur.put(p, Capteur.add_capteur(map_capteur,grille, 2*i, grille.nb_colonne-1, R_com)); 
	    	grille.g[0][0].capteur = false; // pas de capteur sur la source
	    	map_capteur.remove(new Point(0,0));
	    }
	    
	    for (int j=0; j<grille.nb_colonne/2; ++j){
	    	p = new Point (grille.nb_ligne-1, 2*j);
	    	grille.g[grille.nb_ligne-1][2*j].capteur = true;
	    	map_capteur.put(p, Capteur.add_capteur(map_capteur,grille, grille.nb_ligne-1, 2*j, R_com)); 
	    }
	    p = new Point(grille.nb_ligne-1, grille.nb_colonne-1);
	    grille.g[grille.nb_ligne-1][grille.nb_colonne-1].capteur = true;
		map_capteur.put(p, Capteur.add_capteur(map_capteur,grille, grille.nb_ligne-1, grille.nb_colonne-1, R_com)); 
	}

	else { // R >= 3
	  for (int i=0; i<(grille.nb_ligne/3); ++i){
		  for (int j=0; j<grille.nb_colonne/3; ++j){
			  p = new Point(3*i, 3*j);
			  grille.g[3*i][3*j].capteur = true;
			  map_capteur.put(p, Capteur.add_capteur(map_capteur,grille, 3*i, 3*j, R_com)); 
		  }
		  p = new Point(3*i,grille.nb_colonne-2);
		  grille.g[3*i][grille.nb_colonne-2].capteur = true;
		  map_capteur.put(p, Capteur.add_capteur(map_capteur,grille, 3*i, grille.nb_colonne-2, R_com)); 
		  grille.g[0][0].capteur = false; // pas de capteur sur la source
		  map_capteur.remove(new Point(0,0));
	    }
	  
	    for (int j=0; j<grille.nb_colonne/3; ++j){
	    	p = new Point (grille.nb_ligne-2, 3*j);
	    	grille.g[grille.nb_ligne-2][3*j].capteur = true;
	    	map_capteur.put(p, Capteur.add_capteur(map_capteur,grille, grille.nb_ligne-2, 3*j, R_com)); 
	    }
	    p = new Point(grille.nb_ligne-2, grille.nb_colonne-2);
	    grille.g[grille.nb_ligne-2][grille.nb_colonne-2].capteur = true;
		map_capteur.put(p, Capteur.add_capteur(map_capteur,grille, grille.nb_ligne-2, grille.nb_colonne-2, R_com)); 
	}
	     
	return map_capteur;
    }

    // dessine la grille avec les capteurs de map_capteur places
    public static void dessiner (Grille grille, Map<Point, Capteur> map_capteur){
	int nb_ligne = grille.nb_ligne;
	int nb_colonne = grille.nb_colonne;

	for (int i=0; i<nb_ligne; ++i){    
	    for (int j=0; j<nb_colonne; ++j){
	    	if (map_capteur.containsKey(new Point(i,j))){
	    		System.out.print("x ");
	    	}
	    	else {
	    		System.out.print("o ");
	    	}
	    }
	    System.out.print("  " + i);
	    System.out.println();
	}
		System.out.println("Nombre de capteurs : " + map_capteur.size());
    }

    // verifie si les capteurs mis en place verifient le probleme pose 
    public static boolean verif (Grille grille, Map<Point, Capteur> map_capteur, int R_capt, int R_com){
	int nb_ligne = grille.nb_ligne;
	int nb_colonne = grille.nb_colonne;
	boolean res = false;
	boolean res_com = true;
	boolean res_capt = true;

	// raz des acces_sources des capteurs
	zero_source(grille, map_capteur);

	// mise en place des acces_source des capteurs 
	verif_source(grille, map_capteur, R_com);

	// verification de la communication des capteurs vers la source
	for (Map.Entry<Point, Capteur> e : map_capteur.entrySet()){
	    res_com = res_com && e.getValue().acces_source;
	}	
	
	int nb_capt = 0;
	// verification de la captation des cibles par les capteurs 
	for (int i=0; i<nb_ligne; ++i){
	    for (int j=0; j<nb_colonne; ++j){
		nb_capt = 0;
		for(Map.Entry<Point, Capteur> e_capt : map_capteur.entrySet()){
		    int k = (int) e_capt.getKey().getX();
		    int l = (int) e_capt.getKey().getY();

		    if (appartient_cercle(i, j, k, l, R_capt)){
		    	++nb_capt;
		    }
		    else {}
		}
			if ((nb_capt == 0) && (i+j)!=0){ // la source n'a pas besoin d'etre captee
				res_capt = false;
			}
			else {}
	    }
	}
	
		res = res_com && res_capt;
		return res;
    }

    // Cette fonction retourne un capteurs au hasard.
    public static Capteur capteur_selection (Grille grille, Map<Point, Capteur> map_capteur){
    	Capteur cap;
    	cap = new Capteur(0,0,true);
    	int nb_capteur = 0;

	// Nombre de capteur.
	nb_capteur = map_capteur.size();

	// Construire une valeur aleatoire.
	int random;
	random =  (int)(Math.random() * (nb_capteur-1));
	
	for (Map.Entry<Point, Capteur> e : map_capteur.entrySet()){
	    if(random == 0){
	    	cap = e.getValue();
	    	random--;
	    }
	    else{
	    	random--;
	    }
	}
		return cap;
    }

    // Cette fonction retire n capteurs et retourne le nombre n de capteurs supprimes
    public static int capteur_suppresion (Grille grille, Map<Point, Capteur> map_capteur, int R_com, int mult, int i_suppimer, int j_supprimer){
    	int distance = mult * R_com;
    	int nombre_supprimer = 0;

    	// Probleme de bord.
    	for(int i = Math.max(i_suppimer - distance, 0);
    			i < Math.min(i_suppimer + distance + 1,grille.nb_ligne);
    			i++
    			){
	    for(int j = Math.max(j_supprimer - distance, 0);
	    	j < Math.min(j_supprimer + distance + 1, grille.nb_colonne);
	    	j++
	    	){
	    	Point p = new Point(i,j);
	    	if(map_capteur.containsKey(p) == true){
	    		map_capteur.remove(p);
	    		grille.g[i][j].capteur = false;
	    		nombre_supprimer++;
	    	}
	    }
	}
    	return nombre_supprimer;
    }

    // Retourne un point aleatoire de la grille
    public static Point generer_point (Grille grille, Map<Point, Capteur> map_capteur, int R_com, int mult, int i_supprimer, int j_supprimer){
    	int randomi;
    	int randomj;
    	int distance = mult*R_com;
    	Point p;
	
    	randomi =  (int)(Math.random() * (2 * distance + 1) + i_supprimer - distance);
    	randomj =  (int)(Math.random() * (2 * distance + 1) + j_supprimer - distance);
    	p = new Point(randomi,randomj);

    	return p;
    }

    // Insertion de capteurs dans la zone.
    public static void capteur_ajout (Grille grille, Map<Point, Capteur> map_capteur, int R_com, int nombre_ajout, int mult, int i_supprimer, int j_supprimer){
    	Point p;
    	int k = 0;

	while(k < nombre_ajout){
	    	p = generer_point(grille, map_capteur, R_com, mult, i_supprimer, j_supprimer);
	    	if(map_capteur.containsKey(p) == false && p.getX()>=0 && p.getY()>=0 && p.getX()<grille.nb_ligne && p.getY()<grille.nb_colonne && ((p.getX() + p.getY()) != 0)){
	    	map_capteur.put(p, new Capteur(i_supprimer, j_supprimer, false));
	    	grille.g[i_supprimer][j_supprimer].capteur = true;
	    	k++;
	    }
	}
    }

    // remise a zero des acces_source des capteurs de map_capteur
    public static void zero_source (Grille grille, Map<Point, Capteur> map_capteur){
    	for (Map.Entry<Point, Capteur> e : map_capteur.entrySet()){
    		e.getValue().acces_source = false;
    	}    

    }

    // retourne true si Point(i,j) appartient au cercle de rayon R et de centre (x,y)
    public static boolean appartient_cercle (int i, int j, int x, int y, int R){
    	return (Math.sqrt((i-x) * (i-x) + (j-y) * (j-y)) <= R);
    }

    // mise en place des acces_source des capteurs de map_capteur
    public static void verif_source (Grille grille, Map<Point, Capteur> map_capteur, int R_com){
    	int i_c;
    	int j_c;
    	List<Point> list_capt; 
    	list_capt = new LinkedList<Point>();

	// pour les capteurs les plus proches de la source	
	for (Map.Entry<Point, Capteur> e : map_capteur.entrySet()){
	    i_c = (int) e.getKey().getX();
	    j_c = (int) e.getKey().getY();
	    Point p_c = new Point (i_c, j_c);

	    if (appartient_cercle(i_c, j_c, 0, 0, R_com)){
	    	e.getValue().acces_source = true;
	    	list_capt.add(p_c); // on ajoute ce point aux points traites
	    }
	    else {}
	}

	// pour les autres capteurs
	int nb_capteurs = map_capteur.size();
	int compteur = 0;
	
	// mise en place de la liste des voisins a traiter 
	while(list_capt.size() <= nb_capteurs && compteur < list_capt.size()){
	    for (Map.Entry<Point, Capteur> v_capt : map_capteur.entrySet()){
		if (appartient_cercle((int)v_capt.getKey().getX(), (int)v_capt.getKey().getY(), (int)list_capt.get(compteur).getX(), (int)list_capt.get(compteur).getY(), R_com)){ 
		    if (!list_capt.contains(v_capt.getKey())){
			list_capt.add(v_capt.getKey());
		    }
		    else {}
		}
	    }
	    compteur++;
	}

	ListIterator<Point> e = list_capt.listIterator();
	Point tmp;
	while (e.hasNext()){
	    tmp = e.next();
	    // on cherche les voisins de e
	    for (Map.Entry<Point, Capteur> e_dist : map_capteur.entrySet()){
		if (appartient_cercle((int)e_dist.getKey().getX(), (int)e_dist.getKey().getY(), (int)tmp.getX(), (int)tmp.getY(), R_com)){
		    e_dist.getValue().acces_source = true; 
		}
		else {}
	    }
	}
    }

    // calcul du voisinage de map_capteur, retourne une nouvelle solution d'ensemble de capteurs
    public static Map<Point, Capteur> voisinage (Grille grille, Map<Point, Capteur> map_capteur, int R_capt, int R_com, int mult, int i_suppr, int j_suppr){
	int nombre_supprimer;
	Map<Point, Capteur> map_capteur_bis;
	int nombre_ajout;
	int nombre_fois = 0;
	
	map_capteur_bis = new HashMap<Point, Capteur> (map_capteur);
	
	nombre_supprimer = capteur_suppresion(grille, map_capteur_bis, R_com, mult, i_suppr, j_suppr);
	nombre_ajout = nombre_supprimer/2;

	// tant qu'on n'a pas une nouvelle solution
	while(!(verif(grille, map_capteur_bis, R_capt, R_com))){
	    nombre_fois = 0;
	    
	    // on essaie au maximum 100 fois
	    while(!verif(grille, map_capteur_bis, R_capt, R_com) && (nombre_fois < 100)){
	    	capteur_suppresion(grille, map_capteur_bis, R_com, mult, i_suppr, j_suppr);
	    	capteur_ajout(grille, map_capteur_bis, R_com, nombre_ajout , mult, i_suppr, j_suppr);
	    	nombre_fois++;
	    }
	    nombre_ajout++;
	}
    	return map_capteur_bis;
    }

	//################################
	//################################
	//################################
	//        Fonction Main
	//################################
	//################################
    //################################
    public static void main(String[] args){
	
    	// recuperation des parametres
    	int nb_ligne = Integer.parseInt(args[0]);
    	int nb_colonne = Integer.parseInt(args[1]);
    	int R_capt = Integer.parseInt(args[2]);
    	int R_com = Integer.parseInt(args[3]);
    	int nb_capteur_init = 0;
    	int i_supprimer;
    	int j_supprimer;
    	Capteur cap;

	// initialisation du temps de calcul
	long time_0 = System.currentTimeMillis();
	long time = 0;
	
	// initialisation de la grille
	Grille grille;
	grille = new Grille(nb_ligne, nb_colonne, R_capt, R_com);

	Map<Point, Capteur> map_capteur;
	map_capteur = new HashMap<Point, Capteur>();
	
	Map<Point, Capteur> map_capteur_tmp;
	map_capteur_tmp = new HashMap<>();

	map_capteur = initialisation_grille(grille, R_capt, R_com);
	nb_capteur_init = map_capteur.size();
	System.out.println("Nombre initial de capteurs : " + nb_capteur_init);
	while (time < 600000){ // temps d'execution du programme
		// on choisit un capteur au hasard
		cap = capteur_selection (grille, map_capteur);
		i_supprimer = cap.num_ligne;
		j_supprimer = cap.num_colonne;
		map_capteur_tmp = new HashMap<>();

		// on explore le voisinage de ce capteur
		map_capteur_tmp.putAll(voisinage(grille, map_capteur, R_capt, R_com, 2, i_supprimer, j_supprimer));
	
		if (map_capteur.size() > map_capteur_tmp.size()){ // si c'est une meilleure solution
			map_capteur.clear();	
			map_capteur = new HashMap<Point, Capteur> (map_capteur_tmp);
			//dessiner(grille,map_capteur);
			time = System.currentTimeMillis() - time_0;	
			System.out.println("Solution courante : " + map_capteur.size() + " capteurs obtenue en " + (double) time/1000 + "s");
		}
		
		// mise a jour du temps depuis le lancement du programme 
		time = System.currentTimeMillis() - time_0;	
	}

	// on affiche la solution finale
    dessiner(grille,map_capteur);
	
	if (verif(grille, map_capteur, R_capt, R_com)) {
	    System.out.println("Solution admissible");
	}
	else {
	    System.out.println("Solution non admissible");
	}
	
	System.out.println("Nombre initial de capteurs : " + nb_capteur_init);
	System.out.println("Nombre final de capteurs : " + map_capteur.size());
	System.out.println("(Rcapt, Rcom) : (" + R_capt + "," + R_com +")");
	
	// recuperation du temps de calcul
	time = System.currentTimeMillis() - time_0;
	System.out.println("temps de calcul : " + time/1000 + " s");
    }
}
