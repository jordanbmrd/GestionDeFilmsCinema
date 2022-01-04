package sae102;

import java.util.List;

public class Tri {
	
	// TRI SELECTION
	
	public static void triSelectionNombre(List<Film> tableau, int colonneATrier){
		int imin;
		Film temp;
		
		// Pour chaque élement du tableau
		for (int i = 0; i <= tableau.size()-2; i++) {
			imin = i;
			// On trouve le minimum dans la partie droite
			for (int j = i+1; j <= tableau.size()-1; j++) {
				boolean a = false;
				
				switch(colonneATrier) {
					case 2:
						a = tableau.get(j).anneeRealisation < tableau.get(imin).anneeRealisation;
						break;
					case 4:
						a = tableau.get(j).duree < tableau.get(imin).duree;
					case 11:
						a = tableau.get(j).nbVotesSpectateur < tableau.get(imin).nbVotesSpectateur;
					case 12:
						a = tableau.get(j).moyenneVotes < tableau.get(imin).moyenneVotes;
					default:
						break;
				}
				if (a) {
					imin = j;
				}
			}
			
			// On permute les 2 valeurs
			temp = tableau.get(i);
			tableau.set(i, tableau.get(imin));
			tableau.set(imin, temp);
		}
	}
	
	public static void triSelectionChaine(List<Film> tableau, int colonneATrier){
		int imin;
		Film temp;
		
		
		
		// Pour chaque élement du tableau
		for (int i = 0; i <= tableau.size()-2; i++) {
			imin = i;
			// On trouve le minimum dans la partie droite
			for (int j = i+1; j <= tableau.size()-1; j++) {
				int a = 0;
				
				switch(colonneATrier) {
					case 1:
						a = tableau.get(j).titre.compareToIgnoreCase(tableau.get(imin).titre);
						break;
					case 3:
						a = String.join(", ", tableau.get(j).genres).compareToIgnoreCase(String.join(", ", tableau.get(imin).genres));
					case 5:
						a = tableau.get(j).paysProduction.compareToIgnoreCase(tableau.get(imin).paysProduction);
						break;
					case 6:
						a = String.join(", ", tableau.get(j).langues).compareToIgnoreCase(String.join(", ", tableau.get(imin).langues));
						break;
					case 7:
						a = String.join(", ", tableau.get(j).realisateurs).compareToIgnoreCase(String.join(", ", tableau.get(imin).realisateurs));
						break;
					case 8:
						a = String.join(", ", tableau.get(j).scenaristes).compareToIgnoreCase(String.join(", ", tableau.get(imin).scenaristes));
						break;
					case 9:
						a = String.join(", ", tableau.get(j).listeActeurs).compareToIgnoreCase(String.join(", ", tableau.get(imin).listeActeurs));
						break;
					case 10:
						a = tableau.get(j).description.compareToIgnoreCase(tableau.get(imin).description);
						break;
					default:
						break;
				}
				
				if (a < 0) {
					imin = j;
				}
			}
			
			// On permute les 2 valeurs
			temp = tableau.get(i);
			tableau.set(i, tableau.get(imin));
			tableau.set(imin, temp);
		}
	}
	
	
	
	
	// TRI FUSION
	
	public static void triFusionNombre(List<Film> tableau, int g, int d, int colonneATrier){
		int n;
		
		if (g < d) {
			n = (g+d) / 2;
			
			triFusionNombre(tableau, g, n, colonneATrier);
			triFusionNombre(tableau, n+1, d, colonneATrier);
			fusionnerNombre(tableau, g, n, d, colonneATrier);
		}
		
	}
	
	private static void fusionnerNombre(List<Film> tab, int g, int n, int d, int colonneATrier) {
		int n1 = n - g + 1;
		int n2 = d - n;
		
		Film[] gauche = new Film[n1];	// Tableau de gauche
		Film[] droite = new Film[n2];	// Tableau de droite
		
		// On remplit les tableaux
		for (int i = 0; i < n1; i++) gauche[i] = tab.get(g + i);
		for (int j = 0; j < n2; j++) droite[j] = tab.get(n + 1 + j);
		
		// Index pour chaque tableau
		int i1 = 0, i2 = 0, i3 = g;
		
		while (i1 < n1 && i2 < n2) {
			boolean a = false;
			
			switch (colonneATrier) {
				case 2:
					a = gauche[i1].anneeRealisation < droite[i2].anneeRealisation;
					break;
				case 4:
					a = a = gauche[i1].duree < droite[i2].duree;
				case 11:
					a = gauche[i1].nbVotesSpectateur < droite[i2].nbVotesSpectateur;
					break;
				case 12:
					a = gauche[i1].moyenneVotes < droite[i2].moyenneVotes;
					break;
				default:
					break;
			}
			if (a) {
				tab.set(i3, gauche[i1]);
				i1++;
			}
			else {
				tab.set(i3, droite[i2]);
				i2++;
			}
			i3++;
		}
		
		while (i1 < n1) {
			tab.set(i3, gauche[i1]);
			i1++;
			i3++;
		}
		
		while (i2 < n2) {
			tab.set(i3, droite[i2]);
			i2++;
			i3++;
		}
	}
	
	public static void triFusionChaine(List<Film> tableau, int g, int d, int colonneATrier){
		int n;
		
		if (g < d) {
			n = (g+d) / 2;
			
			triFusionChaine(tableau, g, n, colonneATrier);
			triFusionChaine(tableau, n+1, d, colonneATrier);
			fusionnerChaine(tableau, g, n, d, colonneATrier);
		}
		
	}
	
	private static void fusionnerChaine(List<Film> tab, int g, int n, int d, int colonneATrier) {
		int n1 = n - g + 1;
		int n2 = d - n;
		
		Film[] gauche = new Film[n1];	// Tableau de gauche
		Film[] droite = new Film[n2];	// Tableau de droite
		
		// On remplit les tableaux
		for (int i = 0; i < n1; i++) gauche[i] = tab.get(g + i);
		for (int j = 0; j < n2; j++) droite[j] = tab.get(n + 1 + j);
		
		// Index pour chaque tableau
		int i1 = 0, i2 = 0, i3 = g;
		
		while (i1 < n1 && i2 < n2) {
			int a = 0;
			switch (colonneATrier) {
				case 1:
					a = gauche[i1].titre.compareToIgnoreCase(droite[i2].titre);
					break;
				case 3:
					a = String.join(", ", gauche[i1].genres).compareToIgnoreCase(String.join(", ", droite[i2].genres));
				case 5:
					a = gauche[i1].paysProduction.compareToIgnoreCase(droite[i2].paysProduction);
					break;
				case 6:
					a = String.join(", ", gauche[i1].langues).compareToIgnoreCase(String.join(", ", droite[i2].langues));
					break;
				case 7:
					a = String.join(", ", gauche[i1].realisateurs).compareToIgnoreCase(String.join(", ", droite[i2].realisateurs));
					break;
				case 8:
					a = String.join(", ", gauche[i1].scenaristes).compareToIgnoreCase(String.join(", ", droite[i2].scenaristes));
					break;
				case 9:
					a = String.join(", ", gauche[i1].listeActeurs).compareToIgnoreCase(String.join(", ", droite[i2].listeActeurs));
					break;
				case 10:
					a = gauche[i1].description.compareToIgnoreCase(droite[i2].description);
					break;
				default:
					break;
			}
			if (a < 0) {
				tab.set(i3, gauche[i1]);
				i1++;
			}
			else {
				tab.set(i3, droite[i2]);
				i2++;
			}
			i3++;
		}
		
		while (i1 < n1) {
			tab.set(i3, gauche[i1]);
			i1++;
			i3++;
		}
		
		while (i2 < n2) {
			tab.set(i3, droite[i2]);
			i2++;
			i3++;
		}
	}

}
