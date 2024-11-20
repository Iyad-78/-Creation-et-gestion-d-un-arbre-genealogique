package Controllers;

import Models.Arbre;
import Models.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.util.Map;

public class SupprimerPersonneController {

    @FXML
    private TextField nomTextField;
    @FXML
    private TextField prenomTextField;

    @FXML
    private Button supprimerButton;

    public void delete(ActionEvent e ) throws IOException {
        String nom = nomTextField.getText();
        String prenom = prenomTextField.getText();

        Arbre arbre = new Arbre();
        arbre.readData("\\src\\familles.csv");

        for(Personne p : arbre.getRoots()){
            arbre.buildHierarchyTree(p);
        }
        Map<Integer, Personne> personnes = arbre.getPersonnesMap();

        for (Personne p : personnes.values()) {
            if (p.getNom().equalsIgnoreCase(nom) && p.getPrenom().equalsIgnoreCase(prenom)) {
                if (p.getEnfants().isEmpty()) {
                    arbre.supprimerIndividu(p.getId(), "\\src\\familles.csv");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Suppression Enregistrement validé");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText(p.getNom() + " " + p.getPrenom() + " ne peut pas être supprimer");
                    alert.showAndWait();
                    return;
                }
                return;
            }
        }
    }
}
