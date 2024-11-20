package Tree;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;

import java.awt.geom.Rectangle2D;

public class TreeLayoutView extends Group {

    // Le layout de l'arbre qui gère la disposition des TextInBox.
    private final TreeLayout<TextInBox> treeLayout;
    // Indique si la boîte est visible.
    private boolean boxVisible = true;

    // Retourne l'arbre de TreeLayout.
    private TreeForTreeLayout<TextInBox> getTree() {
        return treeLayout.getTree();
    }


    // Retourne les enfants d'un parent donné dans l'arbre.
    private Iterable<TextInBox> getChildren(TextInBox parent) {
        return getTree().getChildren(parent);
    }

    // Retourne les limites (bounding box) d'un nœud donné.
    private Rectangle2D.Double getBoundsOfNode(TextInBox node) {
        return treeLayout.getNodeBounds().get(node);
    }


    public TreeLayoutView(TreeLayout<TextInBox> treeLayout) {
        this.treeLayout = treeLayout;

        addEdges(getTree().getRoot());

        // paint the boxes
        for (TextInBox textInBox : treeLayout.getNodeBounds().keySet()) {
            addBox(textInBox);
        }
    }

    // Vérifie si les boîtes sont visibles.
    public boolean isBoxVisible() {
        return boxVisible;
    }

    // Définit la visibilité des boîtes.
    public void setBoxVisible(boolean boxVisible) {
        this.boxVisible = boxVisible;
    }

    // Taille des arcs des coins des boîtes.
    private final static int ARC_SIZE = 30;
    // Couleur de fond des boîtes.
    private final static Color BOX_COLOR = Color.LIGHTGRAY;
    // Couleur des bordures des boîtes.
    private final static Color BORDER_COLOR = Color.DARKGRAY;
    // Couleur du texte à l'intérieur des boîtes.
    private final static Color TEXT_COLOR = Color.BLACK;


    // Ajoute les arêtes entre le parent et ses enfants dans l'arbre.
    private void addEdges(TextInBox parent) {
        if (!getTree().isLeaf(parent)) {
            Rectangle2D.Double b1 = getBoundsOfNode(parent);
            double x1 = b1.getCenterX();
            double y1 = b1.getCenterY();
            for (TextInBox child : getChildren(parent)) {
                Rectangle2D.Double b2 = getBoundsOfNode(child);
                getChildren().add(
                        new Line(
                                x1, y1,
                                b2.getCenterX(), b2.getCenterY()
                        )
                );

                addEdges(child);
            }
        }
    }


    // Ajoute une boîte pour un nœud donné.
    private void addBox(TextInBox textInBox) {
        Rectangle2D.Double box = getBoundsOfNode(textInBox);

        Rectangle rectangle = new Rectangle(
                box.x, box.y, box.width - 1, box.height - 1
        );
        rectangle.setArcWidth(ARC_SIZE);
        rectangle.setArcHeight(ARC_SIZE);
        rectangle.setFill(BOX_COLOR);
        rectangle.setStroke(BORDER_COLOR);

        if (isBoxVisible()) {
            getChildren().add(
                    rectangle
            );
        }

        // Dessine le texte sur la boîte (possiblement sur plusieurs lignes).
        Label label = new Label(textInBox.text);
        label.setStyle("-fx-alignment: center; -fx-text-alignment: center; -fx-text-fill: " + colorStyleString(TEXT_COLOR) + ";");
        label.relocate(box.x, box.y);
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        label.setPrefSize(box.width, box.height);
        label.setMaxSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        label.setWrapText(true);

        getChildren().add(label);
    }

    // Convertit une couleur en une chaîne de style CSS.
    private String colorStyleString(Color color) {
        return "rgba("
                + ((int) (color.getRed()   * 255)) + ","
                + ((int) (color.getGreen() * 255)) + ","
                + ((int) (color.getBlue()  * 255)) + ","
                + color.getOpacity() +
                ")";
    }
}