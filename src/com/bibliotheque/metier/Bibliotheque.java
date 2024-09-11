package com.bibliotheque.metier;

import com.bibliotheque.metier.Interface.Reservable;
import com.bibliotheque.metier.Interface.Empruntable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bibliotheque {
    private List<Document> documents;
    private List<Utilisateur> utilisateurs;

    public Bibliotheque() {
        documents = new ArrayList<>();
        utilisateurs = new ArrayList<>();
    }

    // Ajoute un document à la bibliothèque
    public void ajouterDocument(Document document) {
        documents.add(document);
    }

    // Ajoute un utilisateur à la bibliothèque
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
    }

    // Affiche les détails de tous les documents
    public void afficherDocuments() {
        for (Document document : documents) {
            document.afficherDetails();
        }
    }

    // Affiche les détails de tous les utilisateurs
    public void afficherUtilisateurs() {
        for (Utilisateur utilisateur : utilisateurs) {
            utilisateur.afficherDetails();
        }
    }

    // Méthode pour réserver un document
    public boolean reserverDocument(UUID idDocument, UUID idUtilisateur) {
        Document document = trouverDocumentParId(idDocument);
        Utilisateur utilisateur = trouverUtilisateurParId(idUtilisateur);
        if (document != null && utilisateur != null && document instanceof Reservable) {
            Reservable reservableDoc = (Reservable) document;
            if (!reservableDoc.estReserve()) {
                reservableDoc.reserver(utilisateur); // Now accepts Utilisateur
                System.out.println("Le document a été réservé avec succès.");
                return true;
            } else {
                System.out.println("Le document est déjà réservé.");
            }
        }
        return false;
    }

    public boolean emprunterDocument(UUID idDocument, UUID idUtilisateur) {
        Document document = trouverDocumentParId(idDocument);
        Utilisateur utilisateur = trouverUtilisateurParId(idUtilisateur);
        if (document != null && utilisateur != null && document instanceof Empruntable) {
            Empruntable empruntableDoc = (Empruntable) document;
            if (empruntableDoc.estDisponible()) {
                empruntableDoc.emprunter(utilisateur); // Now accepts Utilisateur
                System.out.println("Le document a été emprunté avec succès.");
                return true;
            } else {
                System.out.println("Le document n'est pas disponible.");
            }
        }
        return false;
    }


    // Méthode pour annuler une réservation de document
    public boolean annulerReservationDocument(UUID idDocument) {
        Document document = trouverDocumentParId(idDocument);
        if (document != null && document instanceof Reservable) {
            Reservable reservableDoc = (Reservable) document;
            if (reservableDoc.estReserve()) {
                reservableDoc.annulerReservation();
                System.out.println("La réservation a été annulée.");
                return true;
            } else {
                System.out.println("Le document n'est pas réservé.");
            }
        }
        return false;
    }



    // Méthode pour retourner un document emprunté
    public boolean retournerDocument(UUID idDocument) {
        Document document = trouverDocumentParId(idDocument);
        if (document != null && document instanceof Empruntable) {
            Empruntable empruntableDoc = (Empruntable) document;
            empruntableDoc.retourner();
            System.out.println("Le document a été retourné.");
            return true;
        }
        return false;
    }

    // Trouve un document par son UUID
    private Document trouverDocumentParId(UUID idDocument) {
        for (Document doc : documents) {
            if (doc.getId().equals(idDocument)) {
                return doc;
            }
        }
        return null;
    }

    // Trouve un utilisateur par son UUID
    private Utilisateur trouverUtilisateurParId(UUID idUtilisateur) {
        for (Utilisateur user : utilisateurs) {
            if (user.getId().equals(idUtilisateur)) {
                return user;
            }
        }
        return null;
    }

    // Méthode publique pour annuler une réservation en utilisant l'ID du document
    public boolean annulerReservation(UUID idDocument) {
        return annulerReservationDocument(idDocument); // Delegates to the existing method
    }
}
