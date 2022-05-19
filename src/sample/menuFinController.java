package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

/**
 * Gestion de la fenêtre de menu de fin
 *
 * @author Christophe Verreault
 */
public class menuFinController {

    /**
     * Permet de fermer le programme
     */
    public void quitter()
    {
        System.exit(0);
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
