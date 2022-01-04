package sae102;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	final static Scanner sc = new Scanner(System.in); // Scanner utilisable dans tout le programme
	
	final static String BASES_FOLDER_PATH = "./bases/";
	final static String SAVES_FOLDER_PATH = "./sauvegardes/";
	
	static ArrayList<Film> arrayList = new ArrayList<Film>();
	static LinkedList<Film> linkedList = new LinkedList<Film>();
	
	static int listeAUtiliser = 0;
	// 0 = ArrayList
	// 1 = LinkedList
	
	public static void main(String[] args) {
		int choix;
		
		lireBaseDeDonnees("IMDbmoviesCUT100.tsv");	// Initialisation par défaut
		
		System.out.println(" _____   _                                    ");
		System.out.println("/  __ \\ (_)                                   ");
		System.out.println("| /  \\/  _   _ __     ___   _ __ ___     __ _ ");
		System.out.println("| |     | | | '_ \\   / _ \\ | '_ ` _ \\   / _` |");
		System.out.println("| \\__/\\ | | | | | | |  __/ | | | | | | | (_| |");
		System.out.println(" \\____/ |_| |_| |_|  \\___| |_| |_| |_|  \\__,_|");
		System.out.println("                                              ");
		
		do {
			
			afficherMenu("", Arrays.asList("Initialiser la base", "Afficher la liste", "Trier", "Filtrer", "Rechercher", "Supprimer", "Charger une sauvegarde", "Sauvegarder", "Liste à utiliser (Actuelle : " + ((listeAUtiliser == 0) ? "ArrayList" : "LinkedList") + ")", "Quitter"), "");
			choix = sc.nextInt();	// Choix de l'action
			
			switch(choix) {
				case 1:
					initialiserBase();
					break;
				case 2:
					afficherListe((listeAUtiliser == 0) ? arrayList : linkedList);
					break;
				case 3:
					trierListe((listeAUtiliser == 0) ? arrayList : linkedList);
					break;
				case 4:
					filtrerListe((listeAUtiliser == 0) ? arrayList : linkedList);
					break;
				case 5:
					rechercherListe((listeAUtiliser == 0) ? arrayList : linkedList);
					break;
				case 6:
					supprimerListe((listeAUtiliser == 0) ? arrayList : linkedList);
					break;
				case 7:
					chargerSauvegarde();
					break;
				case 8:
					sauvegarderListe((listeAUtiliser == 0) ? arrayList : linkedList);
					break;
				case 9:
					choisirListe();
					break;
				case 10:
					break;
				default:
					System.out.println("Erreur : cette option n'existe pas !");
					break;
			}
		} while (choix != 10);
		
		System.out.println("Fin du programme !");
		
		sc.close();
	}
	
	
	private static void initialiserBase() {
		File dossierBases = new File(BASES_FOLDER_PATH);
		ArrayList<String> fichiersDeBase = new ArrayList<String>(Arrays.asList(dossierBases.list()));
		fichiersDeBase.add("Revenir");
		
		int choix;
		
		// Si des fichiers ont été trouvés
		if (fichiersDeBase != null && fichiersDeBase.size() > 0) {
			afficherMenu("Bases", fichiersDeBase, "Quelle base de données de film voulez-vous utiliser ?");
			choix = sc.nextInt();	// Choix de la base de données à utiliser
			
			if (choix != fichiersDeBase.size()) lireBaseDeDonnees(fichiersDeBase.get(choix - 1));	// Enregistrement des valeurs
			else return;
		}
	}
	
	private static void afficherListe(List<Film> filmsListe) {
		Iterator<Film> i = filmsListe.iterator();
		int j = 0;
		
		while (i.hasNext() && j <= 100) {
			System.out.println(i.next().toString());
			j++;
		}
		if (filmsListe.size() > 100) {
			System.out.println(filmsListe.size() - j + " films de plus...");
		}
		
	}
	
	private static void trierListe(List<Film> filmsListe) {
		int modeDeTri, critereDeTri, croissant;
		long start, stop;
		
		System.out.println("\n\n _____        _ ");
		System.out.println("|_   _|      (_)");
		System.out.println("  | |   _ __  _ ");
		System.out.println("  | |  | '__|| |");
		System.out.println("  | |  | |   | |");
		System.out.println("  \\_/  |_|   |_|");
		
		afficherMenu("Mode de tri",
				Arrays.asList("Tri sélection", "Tri fusion", "Tri Java", "Revenir"),
				"Quelle tri souhaitez-vous utiliser ?");
		modeDeTri = sc.nextInt();
		if (modeDeTri == 4) return;
		else if (modeDeTri > 4) {
			System.out.println("Erreur : Vous devez choisir un nombre entre 1 et 4");
			return;
		}
		
		afficherMenu("Critère de tri",
				Arrays.asList("Par titre", "Par année de réalisation", "Par genres",
						"Par durée", "Par pays de production", "Par langue", "Par réalisateurs",
						"Par scénaristes", "Par acteurs", "Par description",
						"Par nombre de votes des spectateurs", "Par moyenne des votes"),
				"Quel critère souhaitez-vous trier ?");
		critereDeTri = sc.nextInt();
		if (critereDeTri > 12) {
			System.out.println("Erreur : Vous devez choisir un nombre entre 1 et 11");
			return;
		}
		
		start = System.nanoTime();
		switch (critereDeTri) {
			case 1:
				if (modeDeTri == 1) {	// Tri sélection
					Tri.triSelectionChaine(filmsListe, critereDeTri);
				}
				else if (modeDeTri == 2) {	// Tri fusion
					Tri.triFusionChaine(filmsListe, 0, filmsListe.size()-1, critereDeTri);
				}
				else {	// Tri Java
					Collections.sort(filmsListe, Film.compareNom);
				}
				break;
			case 2:
				if (modeDeTri == 1) {	// Tri sélection
					Tri.triSelectionNombre(filmsListe, critereDeTri);
				}
				else if (modeDeTri == 2) {	// Tri fusion
					Tri.triFusionNombre(filmsListe, 0, filmsListe.size()-1, critereDeTri);
				}
				else {	// Tri Java
					//afficherMenu("Ordre", Arrays.asList("Croissant", "Décroissant"), "");
					//croissant = sc.nextInt();
					
					Collections.sort(filmsListe, Film.compareAnneeAsc);
				}
				break;
			case 3:
				if (modeDeTri == 1) {	// Tri sélection
					Tri.triSelectionChaine(filmsListe, critereDeTri);
				}
				else if (modeDeTri == 2) {	// Tri fusion
					Tri.triFusionChaine(filmsListe, 0, filmsListe.size()-1, critereDeTri);
				}
				else {	// Tri Java
					Collections.sort(filmsListe, Film.compareGenres);
				}
				break;
			case 4:
				if (modeDeTri == 1) {	// Tri sélection
					Tri.triSelectionNombre(filmsListe, critereDeTri);
				}
				else if (modeDeTri == 2) {	// Tri fusion
					Tri.triFusionNombre(filmsListe, 0, filmsListe.size()-1, critereDeTri);
				}
				else {	// Tri Java
					afficherMenu("Ordre de tri", Arrays.asList("Croissant", "Décroissant"), "");
					croissant = sc.nextInt();
					
					Collections.sort(filmsListe, (croissant == 1) ? Film.compareDureeAsc : Film.compareDureeDesc);
				}
				
				break;
			case 5:
				if (modeDeTri == 1) {
					Tri.triSelectionChaine(filmsListe, critereDeTri);
				}
				else if (modeDeTri == 2) {
					Tri.triFusionChaine(filmsListe, 0, filmsListe.size()-1, critereDeTri);
				}
				else if (modeDeTri == 3) {
					Collections.sort(filmsListe, Film.comparePays);
				}
				break;
			case 6:
				if (modeDeTri == 1) {
					Tri.triSelectionChaine(filmsListe, critereDeTri);
				}
				else if (modeDeTri == 2) {
					Tri.triFusionChaine(filmsListe, 0, filmsListe.size()-1, critereDeTri);
				}
				else if (modeDeTri == 3) {
					Collections.sort(filmsListe, Film.compareLangues);
				}
				break;
			case 7:
				if (modeDeTri == 1) {
					Tri.triSelectionChaine(filmsListe, critereDeTri);
				}
				else if (modeDeTri == 2) {
					Tri.triFusionChaine(filmsListe, 0, filmsListe.size()-1, critereDeTri);
				}
				else if (modeDeTri == 3) {
					Collections.sort(filmsListe, Film.compareRealisateurs);
				}
				break;
			case 8:
				if (modeDeTri == 1) {
					Tri.triSelectionChaine(filmsListe, critereDeTri);
				}
				else if (modeDeTri == 2) {
					Tri.triFusionChaine(filmsListe, 0, filmsListe.size()-1, critereDeTri);
				}
				else if (modeDeTri == 3) {
					Collections.sort(filmsListe, Film.compareScenaristes);
				}
				break;
			case 9:
				if (modeDeTri == 1) {
					Tri.triSelectionChaine(filmsListe, critereDeTri);
				}
				else if (modeDeTri == 2) {
					Tri.triFusionChaine(filmsListe, 0, filmsListe.size()-1, critereDeTri);
				}
				else if (modeDeTri == 3) {
					Collections.sort(filmsListe, Film.compareActeurs);
				}
				break;
			case 10:
				if (modeDeTri == 1) {
					Tri.triSelectionChaine(filmsListe, critereDeTri);
				}
				else if (modeDeTri == 2) {
					Tri.triFusionChaine(filmsListe, 0, filmsListe.size()-1, critereDeTri);
				}
				else if (modeDeTri == 3) {
					Collections.sort(filmsListe, Film.compareDescription);
				}
				break;
			case 11:
				if (modeDeTri == 1) {
					Tri.triSelectionNombre(filmsListe, critereDeTri);
				}
				else if (modeDeTri == 2) {
					Tri.triFusionNombre(filmsListe, 0, filmsListe.size()-1, critereDeTri);
				}
				else if (modeDeTri == 3) {
					afficherMenu("Ordre de tri", Arrays.asList("Croissant", "Décroissant"), "");
					croissant = sc.nextInt();
					
					Collections.sort(filmsListe, (croissant == 1) ? Film.compareNbVotesSpectateursAsc : Film.compareNbVotesSpectateursDesc);
				}
				
				break;
			case 12:
				if (modeDeTri == 1) {
					Tri.triSelectionNombre(filmsListe, critereDeTri);
				}
				else if (modeDeTri == 2) {
					Tri.triFusionNombre(filmsListe, 0, filmsListe.size()-1, critereDeTri);
				}
				else if (modeDeTri == 3) {
					afficherMenu("Ordre de tri", Arrays.asList("Croissant", "Décroissant"), "");
					croissant = sc.nextInt();
					
					Collections.sort(filmsListe, (croissant == 1) ? Film.compareMoyenneVotesAsc : Film.compareMoyenneVotesDesc);
				}
				
				break;
			default:
				break;
		}
		stop = System.nanoTime();
		
		System.out.println("\nLe tri a été effectué avec succès.");
		System.out.println("Temps d'exécution : " + (float) (stop - start)/1000000 + " ms");
	}
	
	private static void filtrerListe(List<Film> filmsListe) {
		int type, typeTri;
		boolean contient;
		String chaineAFiltrer;
		long start, stop;
		
		System.out.println("\n\n______  _  _  _                     ");
		System.out.println("|  ___|(_)| || |                    ");
		System.out.println("| |_    _ | || |_  _ __   ___  _ __ ");
		System.out.println("|  _|  | || || __|| '__| / _ \\| '__|");
		System.out.println("| |    | || || |_ | |   |  __/| |   ");
		System.out.println("\\_|    |_||_| \\__||_|    \\___||_|   ");
		
		afficherMenu("Type de tri", Arrays.asList("Tri linéaire", "Tri Java", "Revenir"), "Quel type de tri souhaitez-vous utiliser ?");
		typeTri = sc.nextInt();
		if (typeTri == 3) return;
		else if (typeTri > 3) {
			System.out.println("Erreur : Vous devez choisir un nombre entre 1 et 3");
			return;
		}
		
		afficherMenu("Critère de filtre", Arrays.asList("Un titre", "Une année de réalisation", "Un genre",
				"Une durée", "Un pays de production", "Une langue", "Un réalisateur",
				"Un scénariste", "Un acteur", "Une description",
				"Un nombre de votes des spectateurs", "Une moyenne des votes"), "Quel critère de filtre souhaitez-vous utiliser ?");
		type = sc.nextInt();
		if (type > 11) {
			System.out.println("Erreur : Vous devez choisir un nombre entre 1 et 11");
			return;
		}
		
		System.out.println("\nQuelle valeur souhaitez-vous garder ?");
		sc.nextLine();
		chaineAFiltrer = sc.nextLine().toLowerCase();
		
		start = System.nanoTime();
		switch (type) {
			case 1:				
				if (typeTri == 1) {
					Iterator<Film> titreIter = filmsListe.iterator();
					while (titreIter.hasNext()) {
						Film film = titreIter.next();
						if (film.titre.toLowerCase() != chaineAFiltrer.toLowerCase()) {
							titreIter.remove();
						}
						else {
							System.out.println(film);
						}
					}
				}
				else if (typeTri == 2) {
					filmsListe.removeIf(f -> !f.titre.contains(chaineAFiltrer));
				}
				
				break;
	
			case 2:				
				if (typeTri == 1) {
					Iterator<Film> anneeIter = filmsListe.iterator();
					while (anneeIter.hasNext()) {
						Film film = anneeIter.next();
						if (film.anneeRealisation != Integer.parseInt(chaineAFiltrer)) {
							anneeIter.remove();
						}
						else {
							System.out.println(film);
						}
					}
				}
				else if (typeTri == 2) {
					filmsListe.removeIf(f -> f.anneeRealisation != Integer.parseInt(chaineAFiltrer));
				}
				
				
				break;
	
			case 3:				
				if (typeTri == 1) {
					Iterator<Film> genreIter = filmsListe.iterator();
					while (genreIter.hasNext()) {
						contient = false;	// Si le film contient le genre recherché
						Film film = genreIter.next();
						for (int i = 0; i < film.genres.length; i++) {
							if (film.genres[i].equalsIgnoreCase(chaineAFiltrer)) contient = true;
						}
						if (contient == false) genreIter.remove();
						else System.out.println(film);
					}
				}
				else if (typeTri == 2) {
					filmsListe.removeIf(f -> !String.join(", ", f.genres).contains(chaineAFiltrer));
				}
				
				break;
	
			case 4:				
				if (typeTri == 1) {
					Iterator<Film> dureeIter = filmsListe.iterator();
					while (dureeIter.hasNext()) {
						Film film = dureeIter.next();
						if (film.duree != Integer.parseInt(chaineAFiltrer)) {
							dureeIter.remove();
						}
						else {
							System.out.println(film);
						}
					}
				}
				else if (typeTri == 2) {
					filmsListe.removeIf(f -> f.duree != Integer.parseInt(chaineAFiltrer));
				}
				
				break;
	
			case 5:				
				if (typeTri == 1) {
					Iterator<Film> paysIter = filmsListe.iterator();
					while (paysIter.hasNext()) {
						Film film = paysIter.next();
						if (film.paysProduction.toLowerCase() != chaineAFiltrer) {
							paysIter.remove();
						}
						else {
							System.out.println(film);
						}
					}
				}
				else if (typeTri == 2) {
					filmsListe.removeIf(f -> !f.paysProduction.contains(chaineAFiltrer));
				}
				
				break;
	
			case 6:				
				if (typeTri == 1) {
					Iterator<Film> langueIter = filmsListe.iterator();
					while (langueIter.hasNext()) {
						contient = false;	// Si le film contient le genre recherché
						Film film = langueIter.next();
						for (int i = 0; i < film.langues.length; i++) {
							if (film.langues[i].equalsIgnoreCase(chaineAFiltrer)) contient = true;
						}
						if (contient == false) langueIter.remove();
						else System.out.println(film);
					}
				}
				else if (typeTri == 2) {
					filmsListe.removeIf(f -> !String.join(", ", f.langues).contains(chaineAFiltrer));
				}
				
				break;
	
			case 7:				
				if (typeTri == 1) {
					Iterator<Film> realisateursIter = filmsListe.iterator();
					while (realisateursIter.hasNext()) {
						contient = false;	// Si le film contient le genre recherché
						Film film = realisateursIter.next();
						for (int i = 0; i < film.realisateurs.length; i++) {
							if (film.realisateurs[i].equalsIgnoreCase(chaineAFiltrer)) contient = true;
						}
						if (contient == false) realisateursIter.remove();
						else System.out.println(film);
					}
				}
				else if (typeTri == 2) {
					filmsListe.removeIf(f -> !String.join(", ", f.realisateurs).contains(chaineAFiltrer));
				}
				
				break;
	
			case 8:				
				if (typeTri == 1) {
					Iterator<Film> scenaristesIter = filmsListe.iterator();
					while (scenaristesIter.hasNext()) {
						contient = false;	// Si le film contient le genre recherché
						Film film = scenaristesIter.next();
						for (int i = 0; i < film.scenaristes.length; i++) {
							if (film.scenaristes[i].equalsIgnoreCase(chaineAFiltrer)) contient = true;
						}
						if (contient == false) scenaristesIter.remove();
						else System.out.println(film);
					}
				}
				else if (typeTri == 2) {
					filmsListe.removeIf(f -> !String.join(", ", f.scenaristes).contains(chaineAFiltrer));
				}
				
				break;
	
			case 9:				
				if (typeTri == 1) {
					Iterator<Film> acteursIter = filmsListe.iterator();
					while (acteursIter.hasNext()) {
						contient = false;	// Si le film contient le genre recherché
						Film film = acteursIter.next();
						for (int i = 0; i < film.listeActeurs.length; i++) {
							if (film.listeActeurs[i].equalsIgnoreCase(chaineAFiltrer)) contient = true;
						}
						if (contient == false) acteursIter.remove();
						else System.out.println(film);
					}
				}
				else if (typeTri == 2) {
					filmsListe.removeIf(f -> !String.join(", ", f.listeActeurs).contains(chaineAFiltrer));
				}
				
				break;
	
			case 10:				
				if (typeTri == 1) {
					Iterator<Film> descIter = filmsListe.iterator();
					while (descIter.hasNext()) {
						Film film = descIter.next();
						if (!film.description.equalsIgnoreCase(chaineAFiltrer)) {
							descIter.remove();
						}
						else {
							System.out.println(film);
						}
					}
				}
				else if (typeTri == 2) {
					filmsListe.removeIf(f -> !f.description.contains(chaineAFiltrer));
				}
				
				break;
	
			case 11:				
				if (typeTri == 1) {
					Iterator<Film> nbVotesIter = filmsListe.iterator();
					while (nbVotesIter.hasNext()) {
						Film film = nbVotesIter.next();
						if (film.nbVotesSpectateur != Integer.parseInt(chaineAFiltrer)) {
							nbVotesIter.remove();
						}
						else {
							System.out.println(film);
						}
					}
				}
				else if (typeTri == 2) {
					filmsListe.removeIf(f -> f.nbVotesSpectateur != Integer.parseInt(chaineAFiltrer));
				}
				
				break;
	
			case 12:				
				if (typeTri == 1) {
					Iterator<Film> moyVotesIter = filmsListe.iterator();
					while (moyVotesIter.hasNext()) {
						Film film = moyVotesIter.next();
						if (film.moyenneVotes != Float.parseFloat(chaineAFiltrer)) {
							moyVotesIter.remove();
						}
						else {
							System.out.println(film);
						}
					}
				}
				else if (typeTri == 2) {
					filmsListe.removeIf(f -> f.moyenneVotes != Integer.parseInt(chaineAFiltrer));
				}
				
				break;
				
			default:
				break;
		}
		stop = System.nanoTime();
		
		System.out.println("\nLe filtrage a été effectué correctement, affichez la liste pour voir le résultat.");
		System.out.println("Temps d'exécution : " + (float) (stop - start) / 1000000 + "ms");
	}
	
	@SuppressWarnings("unused")
	private static void rechercherListe(List<Film> listeFilms) {		
		int typeDeRecherche, modeDeRecherche;
		long start, stop;
		String chaineARechercher;
		boolean contient;
		
		System.out.println("\n\n______              _                        _                 ");
		System.out.println("| ___ \\            | |                      | |                ");
		System.out.println("| |_/ /  ___   ___ | |__    ___  _ __   ___ | |__    ___  _ __ ");
		System.out.println("|    /  / _ \\ / __|| '_ \\  / _ \\| '__| / __|| '_ \\  / _ \\| '__|");
		System.out.println("| |\\ \\ |  __/| (__ | | | ||  __/| |   | (__ | | | ||  __/| |   ");
		System.out.println("\\_| \\_| \\___| \\___||_| |_| \\___||_|    \\___||_| |_| \\___||_|   ");
		
		afficherMenu("Mode de recherche", Arrays.asList("Recherche linéaire", "Recherche dichotomique", "Revenir"), "Quel type de recherche souhaitez-vous utiliser ?");
		modeDeRecherche = sc.nextInt();
		if (modeDeRecherche == 4) return;
		
		if (modeDeRecherche == 1) {
			afficherMenu("Critère de recherche", Arrays.asList("Un titre", "Une année de réalisation", "Un genre",
					"Une durée", "Un pays de production", "Une langue", "Un réalisateur",
					"Un scénariste", "Un acteur", "Une description",
					"Un nombre de votes des spectateurs", "Une moyenne des votes"), "Quel critère de filtre souhaitez-vous utiliser ?");
			typeDeRecherche = sc.nextInt();
			
			System.out.println("\nQuelle valeur souhaitez-vous garder ?");
			sc.nextLine();
			chaineARechercher = sc.nextLine().toLowerCase();
			
			start = System.nanoTime();
			switch (typeDeRecherche) {
				case 1:
					System.out.println("\nVoici les films avec un titre qui contient " + chaineARechercher + " :\n");
					
					Iterator<Film> titreIter = listeFilms.iterator();
					while (titreIter.hasNext()) {
						Film film = titreIter.next();
						if (film.titre.toLowerCase().contains(chaineARechercher.toLowerCase())) {
							System.out.println(film);
						}
					}
					
					break;
		
				case 2:
					System.out.println("\nVoici les films avec une année de réalisation qui contient " + chaineARechercher + " :\n");
					
					Iterator<Film> anneeIter = listeFilms.iterator();
					while (anneeIter.hasNext()) {
						Film film = anneeIter.next();
						if (String.valueOf(film.anneeRealisation).contains(chaineARechercher)) {
							System.out.println(film);
						}
					}
					
					
					break;
		
				case 3:
					System.out.println("\nVoici les films avec un genre qui contient " + chaineARechercher + " :\n");
					
					Iterator<Film> genreIter = listeFilms.iterator();
					while (genreIter.hasNext()) {
						contient = false;	// Si le film contient le genre recherché
						Film film = genreIter.next();
						for (int i = 0; i < film.genres.length; i++) {
							if (film.genres[i].equalsIgnoreCase(chaineARechercher)) System.out.println(film);
						}
					}
					
					break;
		
				case 4:
					System.out.println("\nVoici les films avec un durée qui contient " + chaineARechercher + " :\n");
					
					Iterator<Film> dureeIter = listeFilms.iterator();
					while (dureeIter.hasNext()) {
						Film film = dureeIter.next();
						if (String.valueOf(film.duree).contains(chaineARechercher)) {
							System.out.println(film);
						}
					}
					
					break;
		
				case 5:
					System.out.println("\nVoici les films produits dans un pays qui contient " + chaineARechercher + " :\n");
					
					Iterator<Film> paysIter = listeFilms.iterator();
					while (paysIter.hasNext()) {
						Film film = paysIter.next();
						if (film.paysProduction.toLowerCase().contains(chaineARechercher)) {
							System.out.println(film);
						}
					}
					
					break;
		
				case 6:
					System.out.println("\nVoici les films produits dans une langue qui contient " + chaineARechercher + " :\n");
					
					Iterator<Film> langueIter = listeFilms.iterator();
					while (langueIter.hasNext()) {
						contient = false;	// Si le film contient le genre recherché
						Film film = langueIter.next();
						for (int i = 0; i < film.langues.length; i++) {
							if (film.langues[i].equalsIgnoreCase(chaineARechercher)) System.out.println(film);
						}
					}
					
					break;
		
				case 7:
					System.out.println("\nVoici les films avec un nom de réalisateur qui contient " + chaineARechercher + " :\n");
					
					Iterator<Film> realisateursIter = listeFilms.iterator();
					while (realisateursIter.hasNext()) {
						contient = false;	// Si le film contient le genre recherché
						Film film = realisateursIter.next();
						for (int i = 0; i < film.realisateurs.length; i++) {
							if (film.realisateurs[i].equalsIgnoreCase(chaineARechercher)) System.out.println(film);
						}
					}
					
					break;
		
				case 8:
					System.out.println("\nVoici les films avec un nom de scénariste qui contient " + chaineARechercher + " :\n");
					
					Iterator<Film> scenaristesIter = listeFilms.iterator();
					while (scenaristesIter.hasNext()) {
						contient = false;	// Si le film contient le genre recherché
						Film film = scenaristesIter.next();
						for (int i = 0; i < film.scenaristes.length; i++) {
							if (film.scenaristes[i].equalsIgnoreCase(chaineARechercher)) System.out.println(film);
						}
					}
					
					break;
		
				case 9:
					System.out.println("\nVoici les films avec un nom d'acteur qui contient " + chaineARechercher + " :\n");
					
					Iterator<Film> acteursIter = listeFilms.iterator();
					while (acteursIter.hasNext()) {
						contient = false;	// Si le film contient le genre recherché
						Film film = acteursIter.next();
						for (int i = 0; i < film.listeActeurs.length; i++) {
							if (film.listeActeurs[i].equalsIgnoreCase(chaineARechercher)) System.out.println(film);
						}
					}
					
					break;
		
				case 10:
					System.out.println("\nVoici les films avec une description qui contient " + chaineARechercher + " :\n");
					
					Iterator<Film> descIter = listeFilms.iterator();
					while (descIter.hasNext()) {
						Film film = descIter.next();
						if (film.description.equalsIgnoreCase(chaineARechercher)) {
							System.out.println(film);
						}
					}
					
					break;
		
				case 11:
					System.out.println("\nVoici les films avec un nombre de votes des spectateurs qui contient " + chaineARechercher + " :\n");
					
					Iterator<Film> nbVotesIter = listeFilms.iterator();
					while (nbVotesIter.hasNext()) {
						Film film = nbVotesIter.next();
						if (String.valueOf(film.nbVotesSpectateur).contains(chaineARechercher)) {
							System.out.println(film);
						}
					}
					
					break;
		
				case 12:
					System.out.println("\nVoici les films avec une moyenne des votes qui contient " + chaineARechercher + " :\n");
					
					Iterator<Film> moyVotesIter = listeFilms.iterator();
					while (moyVotesIter.hasNext()) {
						Film film = moyVotesIter.next();
						if (String.valueOf(film.moyenneVotes).contains(chaineARechercher)) {
							System.out.println(film);
						}
					}
					
					break;
					
				default:
					break;
			}
			stop = System.nanoTime();
			
			System.out.println("Temps d'exécution : " + (float) (stop - start)/1000000 + "ms");
		}
		else if (modeDeRecherche == 2) {
			// RECHERCHE DICHOTOMIQUE
			
			System.out.println("\nTri de la liste...");
			Collections.sort(listeFilms, Film.compareNom);
			System.out.println("La liste a été triée");
			
			start = System.nanoTime();
			System.out.println("\nRecherche dichotomique...");
			int resultatRecherche = rechercheDichotomique(listeFilms, 0, listeFilms.size()-1, "Le lion");
			if (resultatRecherche != -1) {
				System.out.println("Résultat(s) :");
				System.out.println(listeFilms.get(resultatRecherche));
			}
			else {
				System.out.println("\nAucun film n'a été trouvé");
			}
			stop = System.nanoTime();
			
			System.out.println("Temps d'exécution : " + (float) (stop - start) / 1000000 + "ms");
		}
	}
	
	public static int rechercheDichotomique(List<Film> tab, int f, int l, String val){
		 if (l >= f){
			 int mid = f + (l - f)/2;
			 if (tab.get(mid).titre.equalsIgnoreCase(val)){
				 return mid;
			 }
			 if (tab.get(mid).titre.compareToIgnoreCase(val) > 0){
				 // Recherche dans le sous-tableau à gauche
				 return rechercheDichotomique(tab, f, mid-1, val); 
			 }
			 else{
				 // Recherche dans le sous-tableau à droit
				 return rechercheDichotomique(tab, mid+1, l, val); 
			 }
		 }
		 return -1;
	 }
	
	private static void supprimerListe(List<Film> filmsListe) {
		int choix;
		long start, stop;
		
		System.out.println("\n\n _____                             _                        ");
		System.out.println("/  ___|                           (_)                       ");
		System.out.println("\\ `--.  _   _  _ __   _ __   _ __  _  _ __ ___    ___  _ __ ");
		System.out.println(" `--. \\| | | || '_ \\ | '_ \\ | '__|| || '_ ` _ \\  / _ \\| '__|");
		System.out.println("/\\__/ /| |_| || |_) || |_) || |   | || | | | | ||  __/| |   ");
		System.out.println("\\____/  \\__,_|| .__/ | .__/ |_|   |_||_| |_| |_| \\___||_|   ");
		System.out.println("              | |    | |                                    ");
		System.out.println("              |_|    |_|                                    ");
		
		afficherMenu("Mode de suppression", Arrays.asList("Suppression un à un", "Revenir"), "Quel mode de suppression souhaitez-vous utiliser ?");
		choix = sc.nextInt();
		if (choix == 2) return;
		if (choix > 2) {
			System.out.println("Erreur : Vous devez choisir un nombre entre 1 et 2");
			return;
		}
		
		switch(choix) {
			case 1:	// Suppression un à un
				
				start = System.nanoTime();
				
				Iterator<Film> i = filmsListe.iterator();
				while (i.hasNext()) {
					i.next();
					i.remove();
				}
				
				stop = System.nanoTime();
				
				System.out.println("\nLa liste a été vidée avec succès");
				System.out.println("Temps d'exécution : " + (float) (stop - start) / 1000000 + " ms");
				break;
			default:
				break;
		}
	}
	
	private static void chargerSauvegarde() {
		File dossierSauvegardes = new File(SAVES_FOLDER_PATH);
		ArrayList<String> fichiersDeSauvegardes = new ArrayList<String>(Arrays.asList(dossierSauvegardes.list()));
		fichiersDeSauvegardes.add("Revenir");
		
		System.out.println("\n\n _____                                                     _       ");
		System.out.println("/  ___|                                                   | |      ");
		System.out.println("\\ `--.   __ _  _   _ __   __  ___   __ _   __ _  _ __   __| |  ___ ");
		System.out.println(" `--. \\ / _` || | | |\\ \\ / / / _ \\ / _` | / _` || '__| / _` | / _ \\");
		System.out.println("/\\__/ /| (_| || |_| | \\ V / |  __/| (_| || (_| || |   | (_| ||  __/");
		System.out.println("\\____/  \\__,_| \\__,_|  \\_/   \\___| \\__, | \\__,_||_|    \\__,_| \\___|");
		System.out.println("                                    __/ |                          ");
		System.out.println("                                   |___/                           ");
		
		int choix, choixBinaireOuNon;
		
		// Si des fichiers ont été trouvés
		if (fichiersDeSauvegardes != null && fichiersDeSauvegardes.size() > 0) {
			afficherMenu("Sauvegardes", fichiersDeSauvegardes, "Quelle sauvegarde souhaitez-vous utiliser ?");
			choix = sc.nextInt();
			if (choix == fichiersDeSauvegardes.size()) return;
			
			afficherMenu("Type de sauvegarde", Arrays.asList("C'est un fichier non binaire", "C'est un fichier binaire"), "Quel type de fichier est-ce ?");
			choixBinaireOuNon = sc.nextInt();
			
			switch (choixBinaireOuNon) {
				case 1:
					lireSauvegardeNonBinaire(fichiersDeSauvegardes.get(choix - 1));
					break;
				case 2:
					lireSauvegardeBinaire(fichiersDeSauvegardes.get(choix - 1));
				default:
					break;
			}
		}
	}
	
	private static void lireSauvegardeNonBinaire(String nomFichier) {
		String row;
		
		arrayList = new ArrayList<Film>();
		linkedList = new LinkedList<Film>();

		try {

			InputStreamReader isr = new InputStreamReader(new FileInputStream(SAVES_FOLDER_PATH + nomFichier), "UTF8");
			BufferedReader tsvReader = new BufferedReader(isr);

			tsvReader.readLine();

			while ((row = tsvReader.readLine()) != null) {
				String[] data = row.split("\t");
				
				Film film = new Film(data[0], Integer.parseInt(data[1]), data[2], Integer.parseInt(data[3]), data[4],
						data[5], data[6], data[7], data[8], data[9], Integer.parseInt(data[10]),
						Float.parseFloat(data[11]));
				
				arrayList.add(film);
				linkedList.add(film);
			}

			tsvReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Erreur : Fichier \"" + nomFichier + "\" est introuvable !");
		} catch (IOException e) {
			System.out.println("Erreur : Lors de la lecture du fichier !");
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void lireSauvegardeBinaire(String nomFichier) {
		arrayList = new ArrayList<Film>();
		linkedList = new LinkedList<Film>();
		
		try {
			FileInputStream fis = new FileInputStream(SAVES_FOLDER_PATH + nomFichier);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			arrayList = (ArrayList<Film>) ois.readObject();
			linkedList = (LinkedList<Film>) ois.readObject();
			
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			System.out.println("Erreur : Fichier '" + nomFichier + "' inexistant");
		} catch (IOException e) {
			System.out.println("Erreur : lors de la lecture du fichier '" + nomFichier + "'");
		} catch (ClassNotFoundException e) {
			System.out.println("Erreur : classe introuvable '" + nomFichier + "'");
		}
	}
	
	private static void sauvegarderListe(List<Film> filmsListe) {
		int choix;
		String nomNouveauFichier;
		
		System.out.println("\n\n _____                                                     _       ");
		System.out.println("/  ___|                                                   | |      ");
		System.out.println("\\ `--.   __ _  _   _ __   __  ___   __ _   __ _  _ __   __| |  ___ ");
		System.out.println(" `--. \\ / _` || | | |\\ \\ / / / _ \\ / _` | / _` || '__| / _` | / _ \\");
		System.out.println("/\\__/ /| (_| || |_| | \\ V / |  __/| (_| || (_| || |   | (_| ||  __/");
		System.out.println("\\____/  \\__,_| \\__,_|  \\_/   \\___| \\__, | \\__,_||_|    \\__,_| \\___|");
		System.out.println("                                    __/ |                          ");
		System.out.println("                                   |___/                           ");
		
		afficherMenu("Mode d'enregistrement", Arrays.asList("Fichier non binaire (en .tsv)", "Fichier binaire (extension personnalisée)", "Revenir"), "Quel mode d'enregistrement souhaitez-vous utiliser ?");
		choix = sc.nextInt();
		if (choix == 3) return;
		else if (choix > 3) {
			System.out.println("\nErreur : Vous devez choisir un nombre entre 1 et 3");
			return;
		}
		
		if (choix == 1) {	// Format TSV (non binaire)
			System.out.println("Quel nom de fichier souhaitez-vous utiliser ? (sans extension)");
			sc.nextLine();
			nomNouveauFichier = sc.nextLine();
			
			enregistrerFichierNonBinaire((listeAUtiliser == 0) ? arrayList : linkedList, nomNouveauFichier + ".tsv");
		}
		else {
			System.out.println("Quel nom de fichier souhaitez-vous utiliser ? (avec extension)");
			sc.nextLine();
			nomNouveauFichier = sc.nextLine();
			
			enregistrerFichierBinaire(nomNouveauFichier);
		}
	}
	
	private static void enregistrerFichierNonBinaire(List<Film> filmsListe, String nomFichier) {
		try {
			PrintWriter pw = new PrintWriter(SAVES_FOLDER_PATH + nomFichier);
			
			filmsListe.forEach((film) -> {
				pw.println(film.toWrite());
			});
			
			pw.close();
			
			System.out.println("\nLa sauvegarde a été effectuée avec succès !");
		} catch (FileNotFoundException e) {
			System.out.println("\nErreur : Le fichier " + nomFichier + " n'a pas été trouvé");
		}
	}
	
	private static void enregistrerFichierBinaire(String nomFichier) {
		try {
			FileOutputStream fos = new FileOutputStream(SAVES_FOLDER_PATH + nomFichier);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(arrayList);
			oos.writeObject(linkedList);
			
			oos.close();
			fos.close();
			
			System.out.println(((listeAUtiliser == 0) ? "\nL'ArrayList " : "\nLa LinkedList ") + "a été sauvegardé avec succès dans le fichier \"" + nomFichier + "\"");
			
		} catch (FileNotFoundException e) {
			System.out.println("Erreur : le fichier \"" + nomFichier + "\" est introuvable");
		} catch (IOException e) {
			System.out.println("Erreur : lors de la sauvegarde dans le fichier \"" + nomFichier + "\"");
		}
	}
	
	private static void lireBaseDeDonnees(String nomFichier) {
		String row;
		
		arrayList = new ArrayList<Film>();
		linkedList = new LinkedList<Film>();

		try {

			InputStreamReader isr = new InputStreamReader(new FileInputStream(BASES_FOLDER_PATH + nomFichier), "UTF8");
			BufferedReader tsvReader = new BufferedReader(isr);

			tsvReader.readLine();

			while ((row = tsvReader.readLine()) != null) {
				String[] data = row.split("\t");
				
				Film film = new Film(data[1], Integer.parseInt(data[3]), data[5], Integer.parseInt(data[6]), data[7],
						data[8], data[9], data[10], data[12], data[13], Integer.parseInt(data[15]),
						Float.parseFloat(data[14]));
				
				arrayList.add(film);
				linkedList.add(film);
			}

			tsvReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Erreur : Fichier \"" + nomFichier + "\" est introuvable !");
		} catch (IOException e) {
			System.out.println("Erreur : Lors de la lecture du fichier !");
		}
	}
	
	private static void choisirListe() {
		int choix;
		
		System.out.println(" _      _       _         ");
		System.out.println("| |    (_)     | |        ");
		System.out.println("| |     _  ___ | |_   ___ ");
		System.out.println("| |    | |/ __|| __| / _ \\");
		System.out.println("| |____| |\\__ \\| |_ |  __/");
		System.out.println("\\_____/|_||___/ \\__| \\___|");
		
		afficherMenu("Listes", Arrays.asList("Utiliser l'ArrayList", "Utiliser la LinkedList", "Revenir"), "Sur quelle liste souhaitez-vous travailler ?");
		choix = sc.nextInt();
		if (choix == 3) return;
		if (choix > 3) {
			System.out.println("Erreur : Vous devez choisir un nombre entre 1 et 3");
			return;
		}
		
		listeAUtiliser = choix - 1;
	}
	private static void afficherMenu(String titre, List<String> choixPossibles, String question) {
		System.out.println((titre.length() > 0) ? "\n----------- FILMS > " + titre.toUpperCase() + " -----------" : "\n----------- FILMS -----------");
		for (int i = 1; i <= choixPossibles.size(); i++) {
			System.out.println(i + "- " + choixPossibles.get(i - 1));
		}
		System.out.println("-------------------------------------");
		System.out.println((question.length() > 0) ? question : "Que faire ?");
	}
}
