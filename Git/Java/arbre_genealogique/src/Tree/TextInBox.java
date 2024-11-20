package Tree;

// Classe représentant du texte dans une boîte avec une largeur et une hauteur spécifiques.
public class TextInBox {

    // Texte contenu dans la boîte.
    public final String text;
    // Hauteur de la boîte.
    public final int height;
    // Largeur de la boîte.
    public final int width;

    // Constructeur pour initialiser le texte, la largeur et la hauteur de la boîte.
    public TextInBox(String text, int width, int height) {
        this.text = text;
        this.width = width;
        this.height = height;
    }
}