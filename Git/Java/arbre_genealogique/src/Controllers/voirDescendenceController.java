package Controllers;

import Models.Arbre;
import Models.Personne;
import Tree.SampleTreeFactory;
import Tree.TextInBox;
import Tree.TextInBoxNodeExtentProvider;
import Tree.TreeLayoutView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import java.util.Map;

public class voirDescendenceController {
    @FXML
    private FlowPane layout;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label prenomLabel;

    @FXML
    private Button genererArbreButton;

    public void setData(Personne p ){
        prenomLabel.setText(p.getNom());
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

        // Crée un nouvel arbre généalogique
        Arbre arbre = new Arbre();

        // Lit les données à partir d'un fichier CSV
        arbre.readData("\\src\\familles.csv");

        // Construit l'arbre de hiérarchie pour chaque racine
        for(Personne p : arbre.getRoots()){
            arbre.buildHierarchyTree(p);
        }

        // Utilise SampleTreeFactory pour construire un arbre de disposition à partir de la racine
        return SampleTreeFactory.buildTree(racine);
    }

    @FXML
    public void genererArbre(ActionEvent e){
        // Initialise une variable pour contrôler la visibilité des boîtes
        boolean boxVisible = true;

        Personne racine = null;
        // Parcourt les paramètres pour détecter l'option "--nobox"

        // Configure le layout avec des marges et des espacements
        FlowPane layout = new FlowPane();
        layout.setPadding(new Insets(20));
        layout.setHgap(20);
        layout.setVgap(20);

        // Crée un nouvel arbre généalogique
        Arbre arbre = new Arbre();

        // Lit les données à partir d'un fichier CSV
        arbre.readData("\\src\\familles.csv");

        // Construit l'arbre de hiérarchie pour chaque racine

        Map<Integer, Personne> personneMap = arbre.getPersonnesMap();

        for(Personne p : personneMap.values()){
            if(p.getPrenom().equals(prenomLabel.getText())){
                racine=p;
            }
        }

        arbre.buildHierarchyTree(racine);
        layout.getChildren().add(buildTree(boxVisible,racine));


        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }
}
