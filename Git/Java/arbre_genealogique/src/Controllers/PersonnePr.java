package Controllers;

import javafx.beans.property.SimpleStringProperty;

public class PersonnePr {
    private final SimpleStringProperty nom;
    private final SimpleStringProperty prenom;
    private final SimpleStringProperty date;
    private final SimpleStringProperty nationalite;
    private final SimpleStringProperty pere;
    private final SimpleStringProperty mere;

    public PersonnePr(String nom, String prenom, String date, String nationalite, String pere, String mere) {
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.date = new SimpleStringProperty(date);
        this.nationalite = new SimpleStringProperty(nationalite);
        this.pere = new SimpleStringProperty(pere);
        this.mere = new SimpleStringProperty(mere);
    }

    public String getNom() {
        return nom.get();
    }
    public String getPrenom() {
        return prenom.get();
    }
    public String getDate() {
        return date.get();
    }
    public String getNationalite() {
        return nationalite.get();
    }
    public String getPere() {
        return pere.get();
    }
    public String getMere() {
        return mere.get();
    }

}
