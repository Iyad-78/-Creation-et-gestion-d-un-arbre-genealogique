package Models;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Models.Personne;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;


public  class Arbre {

    // Map pour stocker les personnes par leur identifiant
    private Map<Integer, Personne> personnesMap= new HashMap<>();

    // Liste des racines de l'arbre généalogique
    private List<Personne> roots = new ArrayList<>();

    // Retourne la map des personnes
    public Map<Integer, Personne> getPersonnesMap() {
        return personnesMap;
    }

    // Retourne la liste des racines
    public List<Personne> getRoots() {
        return roots;
    }


    // Lit les données depuis un fichier CSV et remplit la map des personnes et la liste des racines
    public void readData(String pathname) {
        Personne personne = null;
        try {
            // Ouvre le fichier CSV
            FileReader filereader = new FileReader(pathname);
            CSVReader csvreader = new CSVReader(filereader);
            String[] nextLine;

            // Lit chaque ligne du fichier CSV
            while ((nextLine = csvreader.readNext()) != null) {
                String[] data = nextLine;

                // Crée une nouvelle personne à partir des données lues
                personne = new Personne(data[0], data[1], data[2], data[3], data[4], data[5],data[6],data[7]);

                // Ajoute la personne aux racines si elle n'a pas de père ou de mère
                if(personne.getIdPere().equals("0") || personne.getIdMere().equals("0")){
                    roots.add(personne);
                }

                // Ajoute la personne à la map
                personnesMap.put(Integer.valueOf(personne.getId()),personne);
            }
            // Ferme le lecteur CSV
            csvreader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Retourne la liste des enfants d'une personne à partir de l'identifiant de son père
    private List<Personne> getEnfantsById(String idPere) {
        List<Personne> enfants = new ArrayList<Personne>();
        for (Personne p : personnesMap.values()) {
            if (p.getIdPere().equals(idPere))
                enfants.add(p);
        }
        return enfants;
    }

    private List<Personne> getConjoints(String idPere) {
        List<Personne> conjoints = new ArrayList<Personne>();
        for (Personne p : personnesMap.values()) {
            if (p.getIdConjoint().equals(idPere))
                conjoints.add(p);
        }
        return conjoints;
    }


    // Construit l'arbre de hiérarchie en ajoutant récursivement les enfants
    public void buildHierarchyTree(Personne root) {
        Personne personne = root;
        List<Personne> enfants = getEnfantsById(personne.getId());
        List<Personne> conjoints = getConjoints(personne.getId());
        personne.setEnfants(enfants);
        personne.setConjoints(conjoints);
        if (personne.getEnfants().isEmpty())
            return;
        for (Personne p : personne.getEnfants())
            buildHierarchyTree(p);
    }

    // Affiche l'arbre de hiérarchie à partir de la racine et du niveau initial
    public void printHierarchyTree(Personne root, int level) {
        for (int i = 0; i < level; i++)
            System.out.print("\t");
        System.out.println(root.getNom()+" "+root.getPrenom());
        List<Personne> enfants = root.getEnfants();
        for (Personne p : enfants)
            printHierarchyTree(p, level+1);
    }

    // Crée un arbre en lisant les données depuis un fichier CSV et en construisant l'arbre de hiérarchie
    public static Arbre createTree(){
        Arbre arbre = new Arbre();
        arbre.readData("\\src\\familles.csv");

        for(Personne p : arbre.getRoots()){
            arbre.buildHierarchyTree(p);
        }
        return arbre;
    }

    // Ajoute un individu dans le fichier CSV spécifié
    public void ajouterIndividu(String id, String nom, String prenom, String dateNaissance, String nationalite, String idPere, String idMere,String pathname){

        try {
            File file = new File(pathname);

            CSVWriter writer = new CSVWriter(new FileWriter(file,true),
                    ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] data = {id, nom, prenom, dateNaissance, nationalite, idPere, idMere};
            writer.writeNext(data);
            writer.close();
        }catch (IOException e){

            e.printStackTrace();
        }
    }

    public void supprimerIndividu(String id, String pathname){
        try {
            // Lire toutes les lignes du fichier CSV
            CSVReader reader = new CSVReader(new FileReader(pathname));
            List<String[]> allLines = reader.readAll();
            reader.close();

            // Filtrer les lignes en éliminant celle contenant l'identifiant à supprimer
            List<String[]> filteredLines = allLines.stream()
                    .filter(line -> !line[0].equals(id))  // Assumant que l'ID est dans la première colonne
                    .collect(Collectors.toList());

            // Réécrire le fichier CSV sans la ligne supprimée
            CSVWriter writer = new CSVWriter(new FileWriter(pathname));
            writer.writeAll(filteredLines);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


