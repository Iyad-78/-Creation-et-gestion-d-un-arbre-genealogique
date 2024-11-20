package Models;

import java.util.List;

public class Personne {

    private String id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String nationalite;
    private String idPere;
    private String idMere;
    private String idConjoint;

    private List<Personne> conjoints;
    private List<Personne> enfants;

    public Personne(String id, String nom, String prenom, String dateNaissance, String nationalite, String idPere, String idMere, String idConjoint) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.nationalite = nationalite;
        this.idPere = idPere;
        this.idMere = idMere;
        this.idConjoint = idConjoint;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id='" + id + '\'' +
                ", idPere='" + idPere + '\'' +
                ", idMere='" + idMere + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance='" + dateNaissance + '\'' +
                ", nationalite='" + nationalite + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getIdPere() {
        return idPere;
    }

    public void setIdPere(String idPere) {
        this.idPere = idPere;
    }

    public String getIdMere() {
        return idMere;
    }

    public void setIdMere(String idMere) {
        this.idMere = idMere;
    }

    public List<Personne> getEnfants() {
        return enfants;
    }

    public void setEnfants(List<Personne> enfants) {
        this.enfants = enfants;
    }

    public List<Personne> getConjoints() {
        return conjoints;
    }

    public void setConjoints(List<Personne> conjoints) {
        this.conjoints = conjoints;
    }

    public String getIdConjoint() {
        return idConjoint;
    }

    public void setIdConjoint(String idConjoint) {
        this.idConjoint = idConjoint;
    }
}
