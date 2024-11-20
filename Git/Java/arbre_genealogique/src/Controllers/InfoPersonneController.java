package Controllers;

import Models.Arbre;
import Models.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Map;

public class InfoPersonneController {
    @FXML
    private TextField nomTextField;

    @FXML
    private TextField prenomTextField;

    @FXML
    private Button validerButton;

    public void ouvrirFenetre(ActionEvent e) throws IOException {
        Arbre arbre = Arbre.createTree();
        Map<Integer, Personne> personneMap = arbre.getPersonnesMap();

        for(Personne p : personneMap.values()){
            if(p.getNom().equalsIgnoreCase(nomTextField.getText()) && p.getPrenom().equalsIgnoreCase(prenomTextField.getText())){
                loadWindow("/Views/voirDescendence.fxml", "Descendence de "+ p.getNom()+" "+p.getPrenom(),p);

            }
            return;
        }
    }

    public void loadWindow(String path, String titre, Personne p) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        stage.setTitle(titre);
        VoirFamillesController controller = loader.getController();
        controller.setData(p);
        stage.show();
    }


}
