package sae102;
import java.io.Serializable;
import java.util.Comparator;

public class Film implements Serializable {
	
	// Propriétés de la classe Film
	String titre;
	int anneeRealisation;
	String[] genres;
	int duree;
	String paysProduction;
	String[] langues;
	String[] realisateurs;
	String[] scenaristes;
	String[] listeActeurs;
	String description;
	int nbVotesSpectateur;
	float moyenneVotes;

	Film(String titre, int anneeRealisation, String genres, int duree, String paysProduction, String langues, String realisateurs, String scenaristes, String listeActeurs, String description, int nbVotesSpectateurs, float moyenneVote) {
		this.titre = titre;
		this.anneeRealisation = anneeRealisation;
		this.genres = genres.split(", ");
		this.duree = duree;
		this.paysProduction = paysProduction;
		this.langues = langues.split(", ");
		this.realisateurs = realisateurs.split(", ");
		this.scenaristes = scenaristes.split(", ");
		this.listeActeurs = listeActeurs.split(", ");
		this.description = description;
		this.nbVotesSpectateur = nbVotesSpectateurs;
		this.moyenneVotes = moyenneVote;
	}

	public String toString() {
		// Retourne la chaîne formatée
		return String.format("%37s %6s %22s %8s %22s %22s %22s %22s %22s %22s %5s %8s",formate(titre, 34), anneeRealisation, formate(String.join(", ", genres), 17), duree, formate(paysProduction, 17), formate(String.join(", ", langues), 17), formate(String.join(", ", realisateurs), 17), formate(String.join(", ", scenaristes), 17), formate(String.join(", ", listeActeurs), 17), formate(description, 17), nbVotesSpectateur, moyenneVotes);
	}
	
	public String toWrite() {
		// Retourne la chaîne formatée pour être enregistrée
		return String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", titre, anneeRealisation, String.join(", ", genres), duree, paysProduction, String.join(", ", langues), String.join(", ", realisateurs), String.join(", ", scenaristes), String.join(", ", listeActeurs), description, nbVotesSpectateur, moyenneVotes);
	}
	  
	private String formate(String chaine, int tailleMax) {
		// Cette fonction permet de réduire l'affichage des chaînes de caractères trop longues
		// en coupant la chaîne et en rajoutant ".." à la fin
	  
		return (chaine.length() > tailleMax) ? chaine.substring(0, tailleMax) + ".." : chaine;
	}
	
	
	// Pour trier par TITRE
	static public Comparator<Film> compareNom = new Comparator<Film>() {
		public int compare(Film film1, Film film2) {
			return film1.titre.compareToIgnoreCase(film2.titre);
		}
	};
	
	// Pour trier par ANNEE DE REALISATION (ordre croissant)
	static public Comparator<Film> compareAnneeAsc = new Comparator<Film>() {
		public int compare(Film film1, Film film2) {
			return film1.anneeRealisation - film2.anneeRealisation;
		}
	};
	
	// Pour trier par ANNEE DE REALISATION (ordre décroissant)
	static public Comparator<Film> compareAnneeDesc = new Comparator<Film>() {
		public int compare(Film film1, Film film2) {
			return film2.anneeRealisation - film1.anneeRealisation;
		}
	};
	
	// Pour trier par GENRES
	static public Comparator<Film> compareGenres = new Comparator<Film>() {
		public int compare(Film film1, Film film2) {
			return String.join(", ", film1.genres).compareToIgnoreCase(String.join(", ", film2.genres));
		}
	};
	
	// Pour trier par DUREE (ordre croissant)
	static public Comparator<Film> compareDureeAsc = new Comparator<Film>() {
		public int compare(Film film1, Film film2) {
			return film1.duree - film2.duree;
		}
	};
	
	// Pour trier par DUREE (ordre décroissant)
	static public Comparator<Film> compareDureeDesc = new Comparator<Film>() {
		public int compare(Film film1, Film film2) {
			return film2.duree - film1.duree;
		}
	};
	
	// Pour trier par PAYS  DE PRODUCTION
	static public Comparator<Film> comparePays = new Comparator<Film>() {
		public int compare(Film film1, Film film2) {
			return film1.paysProduction.compareToIgnoreCase(film2.paysProduction);
		}
	};
	
	// Pour trier par LANGUES
	static public Comparator<Film> compareLangues = new Comparator<Film>() {
		public int compare(Film film1, Film film2) {
			return String.join(", ", film1.langues).compareToIgnoreCase(String.join(", ", film2.langues));
		}
	};
	
	// Pour trier par REALISATEURS
	static public Comparator<Film> compareRealisateurs = new Comparator<Film>() {
		public int compare(Film film1, Film film2) {
			return String.join(", ", film1.realisateurs).compareToIgnoreCase(String.join(", ", film2.realisateurs));
		}
	};
	
	// Pour trier par SCENARISTES
	static public Comparator<Film> compareScenaristes = new Comparator<Film>() {
		public int compare(Film film1, Film film2) {
			return String.join(", ", film1.scenaristes).compareToIgnoreCase(String.join(", ", film2.scenaristes));
		}
	};
	
	// Pour trier par ACTEURS
	static public Comparator<Film> compareActeurs = new Comparator<Film>() {
		public int compare(Film film1, Film film2) {
			return String.join(", ", film1.listeActeurs).compareToIgnoreCase(String.join(", ", film2.listeActeurs));
		}
	};
	
	// Pour trier par DESCRIPTION
	static public Comparator<Film> compareDescription = new Comparator<Film>() {
		public int compare(Film film1, Film film2) {
			return film1.description.compareToIgnoreCase(film2.description);
		}
	};
	
	// Pour trier par NB VOTES SPECTATEURS (ordre croissant)
	static public Comparator<Film> compareNbVotesSpectateursAsc = new Comparator<Film>() {
		public int compare(Film film1, Film film2) {
			return film1.nbVotesSpectateur - film2.nbVotesSpectateur;
		}
	};
	
	// Pour trier par NB VOTES SPECTATEURS (ordre décroissant)
	static public Comparator<Film> compareNbVotesSpectateursDesc = new Comparator<Film>() {
		public int compare(Film film1, Film film2) {
			return film2.nbVotesSpectateur - film1.nbVotesSpectateur;
		}
	};
	
	// Pour trier par MOYENNE DES VOTES (ordre croissant)
	static public Comparator<Film> compareMoyenneVotesAsc = new Comparator<Film>() {
		public int compare(Film film1, Film film2) {
			return (film1.moyenneVotes - film2.moyenneVotes > 0) ? 1 : -1;
		}
	};
	
	// Pour trier par MOYENNE DES VOTES (ordre décroissant)
	static public Comparator<Film> compareMoyenneVotesDesc = new Comparator<Film>() {
		public int compare(Film film1, Film film2) {
			return (film2.moyenneVotes - film1.moyenneVotes > 0) ? 1 : -1;
		}
	};
	
}