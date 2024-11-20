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

//Formulaire d'ajout d'un individu
public class AjouterPersonneController {

    @FXML
    private TextField nomTextField;
    @FXML
    private TextField prenomTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField nationTextField;
    @FXML
    private TextField pereTextField;
    @FXML
    private TextField mereTextField;

    @FXML
    private Button validerButton;



    @FXML
    private void ajouterPersonne(ActionEvent e) throws IOException {
        String nom = nomTextField.getText();
        String prenom = prenomTextField.getText();
        String date = dateTextField.getText();
        String nation = nationTextField.getText();
        String pere = pereTextField.getText();
        String mere = mereTextField.getText();

        String prenomPere = "";
        String prenomMere = "";
        String id="";



        // Si l'utilisateur n'indique pas toutes les informations, on affiche un messsage d'erreur
        if( nom.isEmpty() || prenom.isEmpty() || date.isEmpty() || nation.isEmpty() || pere.isEmpty() || mere.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer toutes les informations");
            alert.showAndWait();
            return;
        }else{

            Arbre arbre = new Arbre();
            arbre.readData("\\src\\familles.csv");
            Map<Integer, Personne> personnes = arbre.getPersonnesMap();

            for(Personne p : personnes.values()){
                if(p.getPrenom().equalsIgnoreCase(pere)){
                    prenomPere=p.getPrenom()+"*"+p.getId();
                }
            }

            for(Personne p : personnes.values()){
                if(p.getPrenom().equalsIgnoreCase(mere)){
                    prenomMere=p.getPrenom()+"*"+p.getId();
                }
            }

            if(prenomPere.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Le prénom du père est incorrect");
                alert.showAndWait();
                return;
            } else if (prenomMere.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Le prénom de la mère est incorrect");
                alert.showAndWait();
                return;
            }else {
                String idPere = prenomPere.split("\\*")[1];
                String idMere = prenomMere.split("\\*")[1];

                Map.Entry<Integer, Personne> lastEntry = personnes.entrySet()
                        .stream()
                        .reduce((first, second) -> second)
                        .orElse(null);

                arbre.ajouterIndividu(String.valueOf(lastEntry.getKey()),nom,prenom,date,nation,idPere,idMere, "\\src\\familles.csv" );

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Enregistrement validé");
                alert.showAndWait();

            }
        }
    }
}
