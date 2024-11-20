package Controllers;

import Models.Arbre;
import Models.Personne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ListeTableauController implements Initializable {
    ObservableList<PersonnePr> listePersonnes = FXCollections.observableArrayList();
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<PersonnePr> tableView;
    @FXML
    private TableColumn<PersonnePr,String> nomCol;
    @FXML
    private TableColumn<PersonnePr,String> prenomCol;
    @FXML
    private TableColumn<PersonnePr,String> dateNaissanceCol;
    @FXML
    private TableColumn<PersonnePr,String> nationCol;
    @FXML
    private TableColumn<PersonnePr,String> pereCol;
    @FXML
    private TableColumn<PersonnePr,String> mereCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        loadData();
    }

    public void initCol(){
        dateNaissanceCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        nationCol.setCellValueFactory(new PropertyValueFactory<>("nationalite"));
        pereCol.setCellValueFactory(new PropertyValueFactory<>("pere"));
        mereCol.setCellValueFactory(new PropertyValueFactory<>("mere"));
    }

    private void loadData(){
        Arbre arbre = Arbre.createTree();

        Map<Integer,Personne> personnesMap = arbre.getPersonnesMap();

        for(Map.Entry<Integer,Personne> personneEntry : personnesMap.entrySet()){

            Personne p = personneEntry.getValue();

            Personne Pere = personnesMap.get(Integer.valueOf(p.getIdPere().equals("x")? "0":p.getIdPere()));
            Personne mere = personnesMap.get(Integer.valueOf(p.getIdMere().equals("x")? "0":p.getIdMere()));



            listePersonnes.add(new PersonnePr(p.getNom(),p.getPrenom(),p.getDateNaissance(),p.getNationalite(), Pere == null ? "Inconnu": Pere.getNom() +" "+Pere.getPrenom(),mere == null ? "Inconnu": mere.getNom()+" "+mere.getPrenom()));
        }
        tableView.getItems().setAll(listePersonnes);
    }
}
