package sample;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe "backbone" qui gère le déroulement du jeu
 *
 * @author Christophe Verreault
 */
public class grilleArrierePlan {

    /**Instance unique de la classe*/
    private static grilleArrierePlan GAP = new grilleArrierePlan();

    /**Grille vide permettant de réinitialiser la grille de jeu*/
    private static int[][] grilleVide = {
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0}
    };

    /**Taille de la grille vide*/
    private static int[] tailleV = {9,9};

    /**Niveau Facile*/
    private static int[][] facile = {
            {1,0,0,0,0,0,0,0,0},
            {1,3,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0},
            {0,0,2,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0}
    };

    /**Taille du niveau Facile*/
    private static int[] tailleF = {4,3};

    /**Niveau Moyen*/
    private static int[][] moyen = {
            {1,1,0,0,0,0,0,0,0},
            {1,0,0,0,1,0,0,0,0},
            {1,0,0,0,0,0,0,0,0},
            {0,0,3,2,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0}
    };

    /**Taille du niveau Moyen*/
    private static int[] tailleM = {5,5};

    /**Niveau Difficile*/
    private static int[][] difficile = {
            {0,0,1,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,3,1,0,0},
            {0,1,0,2,0,0,0,0,0},
            {0,0,0,1,0,0,1,0,0},
            {0,0,0,0,0,0,0,0,0},
            {1,0,0,1,0,0,0,0,0},
            {0,0,0,1,1,0,0,0,0},
            {0,0,0,0,0,0,0,0,0}
    };

    /**Taille du niveau Difficile*/
    private static int[] tailleD = {8,7};

    /**Niveau Expert*/
    private static int[][] expert = {
            {2,0,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,1,0,0},
            {3,0,0,1,0,0,0,0,0},
            {1,0,0,0,0,0,0,1,0},
            {1,1,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1},
            {0,0,1,0,0,0,0,0,1},
            {0,0,0,0,1,0,0,0,0},
            {1,0,0,0,0,0,0,0,0}
    };

    /**Taille du niveau Expert*/
    private static int[] tailleE = {9,9};

    /** Utilisé pour stocker l'état de la grille de l'éditeur lorsque l'utilisateur la teste */
    private static int[][] grilleTest;

    /**Liste de tous les niveaux préenregistrés*/
    private static ArrayList<int[][]> tableaux = new ArrayList<>(Arrays.asList(grilleVide, facile, moyen, difficile, expert));

    /**Configuration actuelle de la grille*/
    private static int[][] grilleJeu;

    /**Liste de toutes les tailles des niveaux préenregistrés*/
    private static ArrayList<int[]> tailles = new ArrayList<>(Arrays.asList(tailleV, tailleF, tailleM, tailleD, tailleE));

    /**Variable générique représentant le nombre de rangées d'une grille*/
    static int rangees;

    /**Variable générique représentant le nombre de colonnes d'une grille*/
    static int colonnes;

    /** Utilisé lors du test d'une carte */
    static boolean entest;

    /**Indique si le jeu présent est gagné (utilisé lors du test d'un niveau)*/
    static boolean gagne;

    /**Constructeur privé, assure une instance unique*/
    private grilleArrierePlan()
    {
        //Constructeur vide et privé. Assure que une seule instance soit disponible
    }

    /**Retourne l'instance unique de la classe*/
    public static grilleArrierePlan getGAP()
    {
        return GAP;
    }

    /**
     * Initialise la grille de jeu à partir de l'index de la grille préexistante passée en paramètre
     *
     * @param index Index du tableau désiré dans la liste
     */
    public static void init(int index)
    {
        try
        {
            //Réinitialise la grille du jeu
            grilleJeu = tableaux.get(0);

            //Copie les données du tableau sélectionné vers la grille du jeu
            for(int c = 0; c < 9; c ++)
            {
                grilleJeu[c] = tableaux.get(index)[c].clone();
            }

            //Définit la taille de la grille de jeu
            rangees = tailles.get(index)[0];
            colonnes = tailles.get(index)[1];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Initialise la grille de jeu à partir de la grille passée en paramètres ainsi que sa taille
     *
     * @param grille Grille qui doit être jouée par l'utilisateur
     * @param rangeesP Nombre de rangées de la grille
     * @param colonnesP Nombre de colonnes de la grille
     */
    public static void init(int[][] grille, int rangeesP, int colonnesP)
    {
        //Réinitialise la grille du jeu
        grilleJeu = tableaux.get(0);

        //Copie la grille passée en paramètre dans la grille du jeu
        for(int c = 0; c < 9; c ++)
        {
            grilleJeu[c] = grille[c].clone();
        }

        rangees = rangeesP;
        colonnes = colonnesP;
    }

    public void setGagne(boolean gagne)
    {
        this.gagne = gagne;
    }

    public boolean getGagne()
    {
        return gagne;
    }

    public static int getRangees()
    {
        return rangees;
    }

    public static int getColonnes()
    {
        return colonnes;
    }

    public static ArrayList<int[][]> getTableaux()
    {
        return tableaux;
    }

    /**
     * Déplace le joueur vers le haut (distance maximale possible) dans la grille Jeu.
     */
    public static void haut()
    {
        //Trouve les coordonnées actuelles du joueur
        int[] res = trouver(grilleJeu,2);
        int ranJ = res[0];
        int colJ = res[1];

        //Compte le nombre de cases dont il peut se déplacer
        int compte = compter("w", grilleJeu);

        //Déplace le joueur
        grilleJeu[ranJ][colJ] = 0;
        grilleJeu[ranJ - compte][colJ] = 2;
    }

    /**
     * Déplace le joueur vers la gauche (distance maximale possible) dans la grille Jeu.
     */
    public static void gauche()
    {
        int[] res = trouver(grilleJeu,2);
        int ranJ = res[0];
        int colJ = res[1];
        int compte = compter("a", grilleJeu);

        grilleJeu[ranJ][colJ] = 0;
        grilleJeu[ranJ][colJ - compte] = 2;
    }

    /**
     * Déplace le joueur vers le bas (distance maximale possible) dans la grille Jeu.
     */
    public static void bas()
    {
        int[] res = trouver(grilleJeu,2);
        int ranJ = res[0];
        int colJ = res[1];
        int compte = compter("s", grilleJeu);

        grilleJeu[ranJ][colJ] = 0;
        grilleJeu[ranJ + compte][colJ] = 2;
    }

    /**
     * Déplace le joueur vers la droite (distance maximale possible) dans la grille Jeu.
     */
    public static void droite()
    {
        int[] res = trouver(grilleJeu,2);
        int ranJ = res[0];
        int colJ = res[1];
        int compte = compter("d", grilleJeu);

        grilleJeu[ranJ][colJ] = 0;
        grilleJeu[ranJ][colJ + compte] = 2;
    }

    /**
     * Retourne la grille du jeu en cours
     *
     * @return Grille du jeu interactive
     */
    public static int[][] get()
    {
        return grilleJeu;
    }

    /**
     * Retourne un tableau contenant la taille de la grille actuelle
     *
     * @return tableau de deux valeurs représentant le nombre de rangées et de colonnes de la grille actuelle
     */
    public static int[] getTaille()
    {
        int[] ret = {rangees,colonnes};
        return ret;
    };

    public static boolean getTest()
    {
        return entest;
    }

    public static void setTest(boolean valeur)
    {
        entest = valeur;
    }

    public static void setGrilleTest(int[][] grille)
    {
        grilleTest = grille;
    }

    public static int [][] getGrilleTest()
    {
        return grilleTest;
    }

    /**
     * Retourne un booléen qui indique le statut de la partie en cours (Terminé ou pas)
     *
     * @return True si terminé, false si pas terminé
     */
    public static boolean fini()
    {
        //Recherche la sortie (3) dans la grille jeu
        int[] res = trouver(grilleJeu, 3);

        //trouver retournera {-1, -1} si le chiffre demandé est absent de la grille
        if(res[0] == -1)
        {
            //La sortie est absente; le jeu est terminé
            return true;
        }
        else
        {
            //La sortie est présente; le jeu continue
            return false;
        }
    }

    /**
     * Permet d'ajouter une grille à la liste des grilles jouables
     */
    public static void add(int[][] nouvelleGrille, int rangees, int colonnes)
    {
        int[] taille = {rangees, colonnes};

        tailles.add(taille);
        tableaux.add(nouvelleGrille);
    }

    /**
     * Permet de compter le nombre de cases maximal dont le joueur peut se déplacer
     * en ligne droite et en s'arrêtant devant les obstacles dans une direction donnée
     *
     * @param direction (w,a,s,d) Direction du déplacement souhaité
     * @param grille Grille dans laquelle la vérification est effectuée
     * @return Nombre de cases (entier)
     */
    private static int compter(String direction, int [][] grille)
    {
        //Coordonnées du joueur
        int[] res = trouver(grille,2);
        int ranJ = res[0];
        int colJ = res[1];

        //Représente la case sélectionnée par les boucles
        int caseVisee;

        //Nombre de cases possibles du déplacement
        int compteur = 0;

        switch (direction)
        {
            //Haut
            case "w" :
            {
                //Le joueur est à la première rangée; il ne peut pas monter
                if(ranJ == 0)
                {
                    return 0;
                }
                else
                {
                    for(int c = ranJ - 1; c >= 0; c --)
                    {
                        caseVisee = grille[c][colJ];

                        //La case visée est soit l'air, soit la sortie; le joueur doit passer par-dessus
                        if(caseVisee == 0 || caseVisee == 3)
                        {
                            compteur ++;
                        }
                        else //La case est un mur, ou la fin de la grille; le joueur s'arrête
                        {
                            return compteur;
                        }
                    }
                    return compteur;
                }
            }

            //Gauche
            case "a":
            {
                //Le joueur est à la première colonne; il ne peut pas aller à gauche
                if(colJ == 0)
                {
                    return 0;
                }
                else
                {
                    for(int c = colJ - 1; c >= 0; c --)
                    {
                        caseVisee = grille[ranJ][c];

                        if(caseVisee == 0 || caseVisee == 3)
                        {
                            compteur ++;
                        }
                        else
                        {
                            return compteur;
                        }
                    }
                    return compteur;
                }
            }

            //bas
            case "s":
            {
                //Le joueur est à la dernière rangée; il ne peut pas descendre
                if(ranJ == rangees)
                {
                    return 0;
                }
                else
                {
                    for(int c = ranJ + 1; c < rangees; c ++)
                    {
                        caseVisee = grille[c][colJ];

                        if(caseVisee == 0 || caseVisee == 3)
                        {
                            compteur ++;
                        }
                        else
                        {
                            return compteur;
                        }
                    }
                    return compteur;
                }
            }

            //Droite
            case "d":
            {
                //Le joueur est à la dernière colonne; il ne peut pas aller à droite
                if(colJ == colonnes)
                {
                    return 0;
                }
                else
                {
                    for(int c = colJ + 1; c < colonnes; c ++)
                    {
                        caseVisee = grille[ranJ][c];

                        if(caseVisee == 0 || caseVisee == 3)
                        {
                            compteur ++;
                        }
                        else if (caseVisee == 1)
                        {
                            return compteur;
                        }
                    }
                    return compteur;
                }
            }
        }

        //Il y a un problème avec le switch si on arrive ici
        return 0;
    }

    /**
     * Permet de trouver les coordonnées du joueur ou de la sortie dans la grille passée en paramètre
     *
     * @param grille grille dans laquelle le joueur doit être trouvée
     * @param num numéro correspondant au joueur ou à la sortie
     * @return tableau de 2 emplacements contenant les coordonnées du joueur
     */
    static int [] trouver(int[][] grille, int num)
    {
        for (int c = 0; c < rangees; c++)
        {
            for (int i = 0; i < colonnes; i++)
            {
                //La case sélectionnée correspond à la case du joueur
                if(grille[c][i] == num)
                {
                    int[] retour = {c,i};
                    return retour;
                }
            }
        }

        int[] test = {-1,-1};
        return test;
    }

}
