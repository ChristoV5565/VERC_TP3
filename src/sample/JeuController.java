package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Gestion du contenu de la fenêtre de jeu
 *
 * @author Christophe Verreault
 */
public class JeuController {

    //Définition des couleurs selon les noms de cases
    /**Case Joueur (rouge)*/
    public static final String JOUEUR = "red";
    /**Case Sortie (vert)*/
    public static final String SORTIE = "green";
    /**Case Mur (noir)*/
    public static final String MUR = "black";
    /**Case non initialisée*/
    public static final String VIDE = "white";
    /**Case désactivée*/
    public static final String DESACT = "#f4f4f4";

    @FXML
    public GridPane grille;
    public Button retour;

    int colonnes = 9;
    int rangees = 9;

    /**Instance du gestionnaire de jeu*/
    grilleArrierePlan GAP = grilleArrierePlan.getGAP();

    public void initialize()
    {
        //Génère la grille contenant les cases colorées à l'écran
        generer(GAP.get());

        colonnes = GAP.getTaille()[1];
        rangees = GAP.getTaille()[0];

        //Le jeu est en mode test d'une nouvelle carte
        if(GAP.getTest())
        {
            retour.setVisible(true);
        }
        else
        {
            retour.setVisible(false);
        }

        //Rend les cases hors-limite "invisibles"
        vider();
    }

    /**
     * Permet de retourner à l'éditeur en conservant la grille actuelle
     */
    public void retour(ActionEvent event) throws IOException {
        GAP.setGagne(false);

        Parent root = FXMLLoader.load(getClass().getResource("editeur.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Bouge le joueur vers le haut
     */
    public void haut() throws IOException {
        //Bouge le joueur dans le gestionnaire
        GAP.haut();

        //Regénère la grille à l'écran
        generer(GAP.get());

        //Vérifie si la partie a été gagnée
        if(GAP.fini())
        {
            terminer();
        }
    }

    /**
     * Bouge le joueur vers le vas
     */
    public void bas() throws IOException {
        GAP.bas();
        generer(GAP.get());
        if(GAP.fini())
        {
            terminer();
        }
    }

    /**
     * Bouge le joueur vers la gauche
     */
    public void gauche() throws IOException {
        GAP.gauche();
        generer(GAP.get());
        if(GAP.fini())
        {
            terminer();
        }
    }

    /**
     * Bouge le joueur vers la droite
     */
    public void droite() throws IOException {
        GAP.droite();
        generer(GAP.get());
        if(GAP.fini())
        {
            terminer();
        }
    }

    /**
     * Permet de terminer la partie et d'aller :
     * - Au menu de fin avec félicitation dans le cas d'une vraie partie
     * - À l'éditeur dans le cas d'une partie créée par l'utilisateur
     */
    public void terminer() throws IOException {

        //Si la carte est en cours de test, retourne à l'éditeur
        if(GAP.getTest())
        {
            System.out.println("OK");

            GAP.setGagne(true);

            Parent root = FXMLLoader.load(getClass().getResource("editeur.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) (grille).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            //Montre le menu de fin
            Parent root = FXMLLoader.load(getClass().getResource("menuFin.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) (grille).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Génère un gridpane à l'écran selon la grille passée en paramètres et dont la taille varie selon les variables
     * globales "colonnes" et "rangees"
     *
     * @param tableau de 9x9 contenant les types de chaque case à mettre dans le GridPane
     * @see EditeurController pour une définition complète et commentée
     */
    public void generer(int[][] tableau)
    {
        Pane pane;

        for(int c = 0; c < colonnes; c++)
        {
            for(int i = 0; i < rangees; i++)
            {
                pane = (Pane) grille.getChildren().get(i * 9 + c);
                pane.setPrefSize(50,50);

                switch (tableau[i][c])
                {
                    case 0:
                    {
                        pane.setStyle("-fx-background-color:" + VIDE);
                        break;
                    }
                    case 1:
                    {
                        pane.setStyle("-fx-background-color:" + MUR);
                        break;
                    }
                    case 2:
                    {
                        pane.setStyle("-fx-background-color:" + JOUEUR);
                        break;
                    }
                    case 3:
                    {
                        pane.setStyle("-fx-background-color:" + SORTIE);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Permet de rendre les cases hors-limites du jeu "invisibles"
     */
    public void vider()
    {
        Pane pane;

        for(int c = 0; c < 9; c++)
        {
            for(int i = 0; i < 9; i ++)
            {
                if(i >= rangees || c >= colonnes)
                {
                    pane = (Pane) grille.getChildren().get(i * 9 + c);
                    pane.setPrefSize(50,50);
                    pane.setStyle("-fx-background-color:" + DESACT);
                }
            }
        }
    }
}
