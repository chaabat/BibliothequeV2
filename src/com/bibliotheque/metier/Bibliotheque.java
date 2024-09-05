package com.bibliotheque.metier;

import java.util.ArrayList;
import java.util.HashMap;

public class Bibliotheque {
    private ArrayList<Document> documents;
    private HashMap<String, Document> rechercheRapide;
    private int nextId;

    public Bibliotheque() {
        documents = new ArrayList<>();
        rechercheRapide = new HashMap<>();
        nextId = 1;
    }

    // Méthode pour obtenir le prochain ID unique
    public int getNextId() {
        return nextId++;
    }

    public void ajouterDocument(Document doc) {
        documents.add(doc);
        rechercheRapide.put(doc.getTitre(), doc);
    }

    public void emprunterDocument(int id) {
        Document doc = rechercherDocumentParId(id);
        if (doc != null) {
            doc.emprunter();
        } else {
            System.out.println("Document non trouvé.");
        }
    }

    public void retournerDocument(int id) {
        Document doc = rechercherDocumentParId(id);
        if (doc != null) {
            doc.retourner();
        } else {
            System.out.println("Document non trouvé.");
        }
    }

    public void afficherTousLesDocuments() {
        for (Document doc : documents) {
            doc.afficherDetails();
        }
    }

    public Document rechercherDocument(String critere) {
        return rechercheRapide.get(critere);
    }

    // Méthode pour rechercher un document par ID
    public Document rechercherDocumentParId(int id) {
        for (Document doc : documents) {
            if (doc.getId() == id) {
                return doc;
            }
        }
        return null; // Retourne null si le document n'est pas trouvé
    }

    // Méthode pour obtenir la liste des documents
    public ArrayList<Document> getDocuments() {
        return documents;
    }
}
