package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Gestion du contenu de l'éditeur
 *
 * @author Christophe Verreault
 */
public class EditeurController {

    //Définition des couleurs selon les noms de cases
    /**Case Joueur (rouge)*/
    public static final String JOUEUR = "red";
    /**Case Sortie (vert)*/
    public static final String SORTIE = "green";
    /**Case Mur (noir)*/
    public static final String MUR = "black";
    /**Case non initialisée*/
    public static final String AIR = "white";
    /**Case désactivée*/
    public static final String DESACT = "#f4f4f4";

    /**Nombre minimal de rangées dans la grille*/
    public static final int MINR = 3;
    /**Nombre minimal de colonnes dans la grille*/
    public static final int MINC = 3;
    /**Nombre maximal de rangées dans la grille*/
    public static final int MAXR = 9;
    /**Nombre maximal de colonnes dans la grille*/
    public static final int MAXC = 9;

    /**Nombre courant de rangées dans la grille*/
    int nbC = 3;
    /**Nombre courant de colonnes dans la grille*/
    int nbR = 3;

    /**Panneau générique qui permet de gérer les cases de la grille*/
    public Pane panneau;

    @FXML
    /**Représente la grille dans laquelle le jeu se déroule*/
    public GridPane grille;
    /**Label montrant le nombre de lignes disponibles dans la grille*/
    public Label lblL;
    /**Label montrant le nombre de colonnes disponibles dans la grille*/
    public Label lblC;
    /**Permet de sélectionner la couleur à mettre dans la grille*/
    public ToggleGroup Couleur;
    /**Permet d'afficher un message d'erreur à l'écran*/
    public Label message;
    /**Permet de sauvegarder une grille en cours de création*/
    public Button sauvegarde;

    /**Permet de manipuler le gestionnaire du jeu*/
    grilleArrierePlan GAP = grilleArrierePlan.getGAP();

    /**
     * Procédure qui exécute les instructions qu'elle contient au démarrage du programme, avant d'afficher la fenêtre
     */
    public void initialize()
    {
        //Rend la grille clickable, ce qui permet de colorer les cases selon le type sélectionné
        grille.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                //Permet de déterminer la case sur laquelle on clique à partir des coordonnées de la souris
                int colonne = (int)(mouseEvent.getX() / 50);
                int rangee = (int)(mouseEvent.getY() / 50);

                //Détermine quel type de case est sélectionné
                RadioButton rb = (RadioButton) Couleur.getSelectedToggle();
                String valeur = rb.getText();

                //Colore la case selon son type
                if(colonne <= nbC && rangee <= nbR)
                {
                    message.setText("");

                    switch (valeur)
                    {
                        case "Air" :
                        {
                            changerType(rangee, colonne, AIR);
                            break;
                        }
                        case "Mur" :
                        {
                            changerType(rangee, colonne, MUR);
                            break;
                        }
                        case "Joueur" :
                        {
                            //Assure qu'il n'existe qu'un seul joueur dans la grille
                            if(verifUnique(JOUEUR))
                            {
                                changerType(rangee, colonne, JOUEUR);
                            }
                            else
                            {
                                message.setText("Un joueur est déjà présent");
                            }

                            break;
                        }
                        case "Sortie" :
                        {
                            //Assure qu'il n'existe qu'une seule sortie dans la grille
                            if(verifUnique(SORTIE))
                            {
                                changerType(rangee, colonne, SORTIE);
                            }
                            else
                            {
                                message.setText("Une sortie est déjà présente");
                            }

                            break;
                        }
                    }

                    //Désactive le bouton sauvegarder à toutes les fois que la grille est modifiée
                    sauvegarde.setDisable(true);
                }
            }
        });

        //Une grille était en cours de test. Elle est restaurée
        if(GAP.getTest())
        {
            //Ajuste le nombre de colonnes et de rangées disponibles
            nbC = GAP.getColonnes();
            nbR = GAP.getRangees();

            //Ajuste l'affichage du nombre de colonnes et de rangées
            lblL.setText("Lignes : " + nbR);
            lblC.setText("Colonnes : " + nbC);

            //Restaure le contenu de la grille testée
            generer(GAP.getGrilleTest());

            //La partie en cours de test a été gagnée et peut donc être sauvegardée
            if(GAP.getGagne())
            {
                //La partie n'est plus en cours de test
                GAP.setTest(false);

                //Le bouton sauvegarde est activé
                sauvegarde.setDisable(false);
            }
        }
        else
        {
            //Apparence par défaut
            colorerAire(3,3, "white");
        }
    }

    /**
     * Génère un gridpane à l'écran selon la grille passée en paramètres et dont la taille varie selon les variables
     * globales "colonnes" et "rangees"
     *
     * @param tableau de 9x9 contenant les types de chaque case à mettre dans le GridPane
     */
    public void generer(int[][] tableau)
    {
        for(int c = 0; c < nbC; c++)
        {
            for(int i = 0; i < nbR; i++)
            {
                //Prend la valeur de chaque Pane que contient le GridPane séquentiellement
                panneau = (Pane) grille.getChildren().get(i * 9 + c);
                panneau.setPrefSize(50,50);

                //Colore chaque panneau selon les valeurs de la grille passée en paramètre
                switch (tableau[i][c])
                {
                    case 0:
                    {
                        panneau.setStyle("-fx-background-color:" + AIR);
                        break;
                    }
                    case 1:
                    {
                        panneau.setStyle("-fx-background-color:" + MUR);
                        break;
                    }
                    case 2:
                    {
                        panneau.setStyle("-fx-background-color:" + JOUEUR);
                        break;
                    }
                    case 3:
                    {
                        panneau.setStyle("-fx-background-color:" + SORTIE);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Permet de changer le type de la case sélectionnée
     *
     * @param rangee L'indice de la rangée en Int commençant à 0
     * @param colonne L'indice de la colonne en Int commençant à 0
     * @param type Type de la colonne, String. Préférablement une des variables statiques finales définies plus haut
     */
    public void changerType(int rangee, int colonne, String type)
    {
        //Sélectionne le panneau aux coordonnées demandées dans la grille
        panneau = (Pane) grille.getChildren().get(rangee * MAXR + colonne);

        //Change la couleur
        panneau.setStyle("-fx-background-color:" + type);
    }

    /**
     * Permet de colorer une aire dans la grille de dimensions X par Y en partant de en haut à gauche
     *
     * @param colonnes Nombre de colonnes qui seront colorées
     * @param rangees Nombre de rangées qui seront colorées
     * @param type Type de case utilisé pour déterminer sa couleur
     */
    public void colorerAire(int colonnes, int rangees, String type)
    {
        for(int c = 0; c < colonnes; c++)
        {
            for(int i = 0; i < rangees; i++)
            {
                panneau = (Pane) grille.getChildren().get(i * MAXR + c);
                panneau.setStyle("-fx-background-color:" + type);
            }
        }
    }

    /**
     * Permet d'ajouter une rangée à la grille
     */
    public void ajouterRangee()
    {
        //S'assure de ne jamais tenter d'ajouter une rangée hors de la grille
        if(nbR < MAXR)
        {
            //Colore un panneau à la fois dans la nouvelle rangée dans la grille
            for (int c = 0; c < nbC; c++) {
                panneau = (Pane) grille.getChildren().get(nbR * MAXR + c);
                panneau.setStyle("-fx-background-color:" + AIR);
            }

            //Incrémente le nombre de rangées actives dans la grille
            nbR++;

            updatePos();
        }
        sauvegarde.setDisable(true);
    }

    /**
     * Permet d'ajouter une colonne à la grille
     */
    public void ajouterColonne() {
        //S'assure de ne jamais tenter d'ajouter une rangée hors de la grille
        if (nbC < grille.getColumnCount())
        {
            for(int c = 0; c < nbR; c++)
            {
                panneau = (Pane) grille.getChildren().get(c * MAXR + nbC);
                panneau.setStyle("-fx-background-color:" + AIR);
            }

            //Incrémente le nombre de colonnes actives dans la grille
            nbC ++;

            updatePos();
        }
        sauvegarde.setDisable(true);
    }

    /**
     * Permet de supprimer une rangée de la grille
     */
    public void supprRangee()
    {
        //S'assure de ne jamais tenter d'ajouter une rangée hors de la grille
        if(nbR > MINR)
        {
            //Décrémente la variable avant de supprimer la rangée
            nbR--;

            //Colore un panneau à la fois dans la nouvelle rangée dans la grille
            for (int c = 0; c < nbC; c++) {
                panneau = (Pane) grille.getChildren().get(nbR * MAXR + c);
                panneau.setStyle("-fx-background-color:" + DESACT);
            }

            updatePos();
        }
        sauvegarde.setDisable(true);
    }

    /**
     * Permet de supprimer une colonne de la grille
     */
    public void supprColonne() {
        //S'assure de ne jamais tenter d'ajouter une rangée hors de la grille
        if (nbC > MINC)
        {
            //Décrémente la variable avant de supprimer la colonne
            nbC --;

            for(int c = 0; c < nbR; c++)
            {
                panneau = (Pane) grille.getChildren().get(c * MAXR + nbC);
                panneau.setStyle("-fx-background-color:" + DESACT);
            }

            updatePos();
        }
        sauvegarde.setDisable(true);
    }

    /**
     * Met à jour la position de la grille selon son nombre de colonnes et de rangées
     * (met aussi à jour l'affichage des nombres de rangées et de colonnes)
     */
    public void updatePos()
    {
        double nouvposX = (400 - (nbC * 25));
        double nouvposY = (300 - (nbR * 25));

        grille.setLayoutX(nouvposX);
        grille.setLayoutY(nouvposY);

        lblL.setText("Lignes : " + nbR);
        lblC.setText("Colonnes : " + nbC);
    }

    /**
     * Remplit la grille de cases blanches
     */
    public void reset()
    {
        colorerAire(nbC, nbR, AIR);
    }

    /**
     * Permet de vérifier si une case d'un type donné existe déjà dans la grille.
     * Utilisé pour s'assurer de n'avoir qu'une case "joueur" et une case "sortie"
     *
     * @param type Type de la case, soit Joueur ou Sortie
     * @return True si une case du type donné n'existe pas dans la grille, false dans le cas contraire
     */
    public boolean verifUnique(String type)
    {
        //Parcourt la grille au complet
        for(int c = 0; c < MAXC; c++)
        {
            for(int i = 0; i < MAXR; i++)
            {
                panneau = (Pane) grille.getChildren().get(i * MAXR + c);

                //Si la case sélectionnée a la couleur demandée
                if(panneau.getStyle().equals("-fx-background-color:" + type))
                {
                    //Indique que la couleur n'est pas unique et donc ne peut être placée
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Permet de faire tester au joueur la carte en cours de création
     */
    public void tester(ActionEvent event) throws IOException {
        //Convertit la grille graphique en grille de nombres
        int [][] grilleTemp = convertirGrille();

        //Définit la grille courante comme grille en cours de test
        GAP.setGrilleTest(grilleTemp);

        //Le jeu entre en mode test
        GAP.setTest(true);

        //Initialise gestionnaire avec la grille en cours de test
        GAP.init(GAP.getGrilleTest(), nbR, nbC);

        //Bascule vers la fenêtre de jeu
        Parent root = FXMLLoader.load(getClass().getResource("jeu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Convertit le contenu du GridPane en grille numérique
     *
     * @return grille de nombres
     */
    public int[][] convertirGrille()
    {
        int[][] grilleTemp = new int[9][9];

        //Parcours toute le GridPane
        for(int c = 0; c < 9; c++)
        {
            for(int i = 0; i < 9; i++)
            {
                //Convertit la grille graphique en grille numérique selon la couleur des cases
                panneau = (Pane) grille.getChildren().get(i * MAXR + c);

                switch (panneau.getStyle())
                {
                    case "-fx-background-color:" + AIR:
                    {
                        grilleTemp[i][c] = 0;
                        break;
                    }
                    case "-fx-background-color:" + MUR:
                    {
                        grilleTemp[i][c] = 1;
                        break;
                    }
                    case "-fx-background-color:" + JOUEUR:
                    {
                        grilleTemp[i][c] = 2;
                        break;
                    }
                    case "-fx-background-color:" + SORTIE:
                    {
                        grilleTemp[i][c] = 3;
                        break;
                    }
                }

            }
        }

        return grilleTemp;
    }

    /**
     * Permet d'ajouter la grille nouvellement créée aux grilles disponibles du gestionnaire
     */
    public void sauvegarder(ActionEvent event) throws IOException {
        //Convertit le GridPane en grille numérique
        int[][] grilleTemp = convertirGrille();

        //Ajoute la grille à la liste des grilles jouables
        GAP.add(grilleTemp, nbR, nbC);

        //Bascule vers le menu principal
        Parent root = FXMLLoader.load(getClass().getResource("MenuPrincipal.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Permet de retourner au menu principal
     */
    public void basculer(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("MenuPrincipal.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
