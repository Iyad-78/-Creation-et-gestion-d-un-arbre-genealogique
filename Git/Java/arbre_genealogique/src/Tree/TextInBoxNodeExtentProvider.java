package Tree;

import org.abego.treelayout.NodeExtentProvider;


public class TextInBoxNodeExtentProvider implements
        NodeExtentProvider<TextInBox> {

    // Implémentation de la méthode pour obtenir la largeur d'un noeud
    @Override
    public double getWidth(TextInBox treeNode) {
        return treeNode.width;
    }

    // Implémentation de la méthode pour obtenir la hauteur d'un noeud
    @Override
    public double getHeight(TextInBox treeNode) {
        return treeNode.height;
    }
}