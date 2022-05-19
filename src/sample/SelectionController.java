package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Gestion du contenu de la fenêtre de sélection
 *
 * @author Christophe Verreault
 */
public class SelectionController {

    /**Listview contenant les noms des niveaux*/
    public ListView tabniv;

    /**Instance unique du gestionnaire*/
    grilleArrierePlan GAP = grilleArrierePlan.getGAP();

    public void initialize()
    {
        //Ajoute les niveaux préexistants
        tabniv.getItems().add("Facile");
        tabniv.getItems().add("Moyen");
        tabniv.getItems().add("Difficile");
        tabniv.getItems().add("Expert");

        int compteur = 0;

        //Ajoute tous les niveaux après l'index #4 de la liste de tous les niveaux.
        //Les niveaux après l'index #4 sont ceux créés par l'utilisateurs et ont donc un nom défini automatiquement
        for (int[][] tab : GAP.getTableaux()
             ) {
            if(compteur > 4)
            {
                tabniv.getItems().add(compteur - 4);
            }

            compteur ++;
        }
    }

    /**
     * Permet de démarrer le niveau sélectionné dans le listview
     */
    public void jouer(ActionEvent event) throws IOException {

        //Initialise la grille du jeu selon celle sélectionnée dans le listview
        //Ne fait rien si aucun niveau n'est sélectionné
        if(!tabniv.getSelectionModel().getSelectedItems().isEmpty())
        {
            //Initialise le gestionnaire
            GAP.init(tabniv.getSelectionModel().getSelectedIndex() + 1);

            //Bascule vers la fenêtre de jeu
            Parent root = FXMLLoader.load(getClass().getResource("jeu.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Permet de retourner au menu principal
     */
    public void menuPrincipal(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("MenuPrincipal.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}


