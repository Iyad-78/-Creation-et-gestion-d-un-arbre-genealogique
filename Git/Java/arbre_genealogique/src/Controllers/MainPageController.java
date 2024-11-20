package Controllers;

import Models.Arbre;
import Models.Personne;
import Tree.SampleTreeFactory;
import Tree.TextInBox;
import Tree.TextInBoxNodeExtentProvider;
import Tree.TreeLayoutView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;


public class MainPageController implements Initializable {

    @FXML
    private FlowPane layout;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button buttonListe;
    @FXML
    private HBox hBox;
    @FXML
    private Button supprimerPersonne;


    public void loadWindow(String path, String titre) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource(path));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle(titre);
        stage.setScene(new Scene(parent));
        stage.show();
    }
    public void loadWindow(String path) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource(path));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void loadWindow(String path, String titre, Personne p) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        stage.setTitle(titre);
        VoirFamillesController controller = loader.getController();
        controller.setData(p);
        stage.show();
    }

    @FXML
    private void voireListe(ActionEvent e) throws IOException{
        loadWindow("/Views/listeTableau.fxml","Membres des familles");
    }

    @FXML
    private void formulairePersonne(ActionEvent e) throws IOException{
        loadWindow("/Views/ajouterPersonne.fxml","Ajouter un individu");
    }
    @FXML
    private void supprimerPersonne(ActionEvent e) throws IOException{
        loadWindow("/Views/supprimerPersonne.fxml","Supprimer un individu");

    }

    @FXML
    private void voirDescendence(ActionEvent e) throws IOException{
        loadWindow("/Views/infoPersonne.fxml");
    }

    public TreeLayoutView buildTree(boolean boxVisible, Personne racine) {

        // Obtention d'un arbre d'exemple
        TreeForTreeLayout<TextInBox> tree = getSampleTree(racine);

        // Configure l'espacement entre les niveaux et les noeuds de l'arbre
        double gapBetweenLevels = 60;
        double gapBetweenNodes = 10;
        DefaultConfiguration<TextInBox> configuration = new DefaultConfiguration<>(
                gapBetweenLevels,
                gapBetweenNodes
        );

        // Crée un panneau qui dessine les noeuds et les arêtes et affiche le panneau
        TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();

        // Crée la disposition de l'arbre avec les paramètres et le fournisseur de noeuds
        TreeLayout<TextInBox> treeLayout = new TreeLayout<>(
                tree,
                nodeExtentProvider,
                configuration
        );

        // Crée un panneau qui dessine les noeuds et les arêtes et affiche le panneau
        TreeLayoutView treePane = new TreeLayoutView(treeLayout);
        treePane.setBoxVisible(boxVisible);

        // Retourne le panneau de disposition de l'arbre
        return treePane;
    }

    private TreeForTreeLayout<TextInBox> getSampleTree(Personne racine){

        /*// Crée un nouvel arbre généalogique
        Arbre arbre = new Arbre();

        // Lit les données à partir d'un fichier CSV
        arbre.readData("h:\\Documents\\CoursING1\\Projet\\arbre_genealogique\\src\\personnes.csv");

        // Construit l'arbre de hiérarchie pour chaque racine
        for(Personne p : arbre.getRoots()){
            arbre.buildHierarchyTree(p);
        }*/

        // Utilise SampleTreeFactory pour construire un arbre de disposition à partir de la racine
        return SampleTreeFactory.buildTree(racine);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialise une variable pour contrôler la visibilité des boîtes
        boolean boxVisible = true;
        // Parcourt les paramètres pour détecter l'option "--nobox"

        // Configure le layout avec des marges et des espacements
        /*FlowPane layout = new FlowPane();*/
        layout.setPadding(new Insets(20));
        layout.setHgap(20);
        layout.setVgap(20);

        // Crée un nouvel arbre généalogique
        Arbre arbre = new Arbre();

        // Lit les données à partir d'un fichier CSV
        arbre.readData("\\src\\familles.csv");

        // Construit l'arbre de hiérarchie pour chaque racine
        for(Personne p : arbre.getRoots()){
            arbre.buildHierarchyTree(p);
        }
        // Ajoute l'arbre au layout pour chacune des racines , Ajout des bouttons pour selectionnner la famille à afficher
        for (Personne p : arbre.getRoots()) {
            layout.getChildren().add(buildTree(boxVisible,p));

            hBox.setSpacing(10);
            hBox.getStyleClass().add("centered-hbox");
            Button button = new Button();
            button.setOnAction(event -> {
                try {
                    loadWindow("/Views/voirFamille.fxml", "Famille " + p.getNom(),p);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            button.setText("Famille " + p.getNom());
            button.getStyleClass().add("button-custom");
            hBox.getChildren().add(button);

        }

        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);



    }


}
