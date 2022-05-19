package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Gestion du contenu du menu principal
 *
 * @author Christophe Verreault
 */
public class MenuPrincipalController {

    @FXML
    public Button Btn_Editeur;
    public Button btn_jeu;

    /**
     * Permet de charger la fenêtre vers un autre fichier FXML
     *
     * (dans ce cas-ci l'éditeur)
     *
     * Source : https://www.youtube.com/watch?v=m5853rAfekE
     */
    @FXML
    private void editeur(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("editeur.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML

    /**
     * Bascule vers la sélection des niveaux
     */
    private void jeu(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("Selection.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Permet de quitter le programme à l'aide de l'option du menu
     */
    @FXML
    private void quitter()
    {
        System.exit(0);
    }

}
