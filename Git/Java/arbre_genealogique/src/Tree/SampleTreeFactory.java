package Tree;

import Models.Personne;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;


public class SampleTreeFactory {

    // Méthode pour construire l'arbre à partir de la racine
    public static TreeForTreeLayout<TextInBox> buildTree(Personne racine) {
        // Crée le noeud racine avec les informations de la personne racine

        Personne conjoint= racine.getConjoints().get(0);
        TextInBox root = new TextInBox(racine.getNom() + " " + racine.getPrenom() + " - " + conjoint.getNom() + " " + conjoint.getPrenom()+  "\n" + racine.getDateNaissance() + " - " + conjoint.getDateNaissance(), 180, 70);
        // Initialise l'arbre avec le noeud racine
        DefaultTreeForTreeLayout<TextInBox> tree = new DefaultTreeForTreeLayout<>(root);
        // Ajoute les enfants à l'arbre de manière récursive
        addChildrenToTree(tree, root, racine);
        // Retourne l'arbre construit
        return tree;
    }

    // Méthode récursive pour ajouter les enfants à l'arbre
    private static void addChildrenToTree(DefaultTreeForTreeLayout<TextInBox> tree, TextInBox parentBox, Personne parentPerson) {
        // Parcourt tous les enfants de la personne parent
        for (Personne child : parentPerson.getEnfants()) {
            // Crée un noeud pour chaque enfant avec ses informations

            if(!child.getConjoints().isEmpty()){
                Personne conjoint = child.getConjoints().get(0);
                TextInBox childBox = new TextInBox(child.getNom() + " " + child.getPrenom() +" - "+conjoint.getNom() + " " + conjoint.getPrenom()+ "\n" + child.getDateNaissance() + " - " + conjoint.getDateNaissance(), 180, 70);
                // Ajoute le noeud enfant à l'arbre sous le noeud parent
                tree.addChild(parentBox, childBox);
                // Appelle récursivement pour ajouter les enfants du noeud enfant
                addChildrenToTree(tree, childBox, child);
            }else{
                TextInBox childBox = new TextInBox(child.getNom() + " " + child.getPrenom() + "\n" + child.getDateNaissance(), 140, 70);
                // Ajoute le noeud enfant à l'arbre sous le noeud parent
                tree.addChild(parentBox, childBox);
                // Appelle récursivement pour ajouter les enfants du noeud enfant
                addChildrenToTree(tree, childBox, child);
            }


        }
    }
}
