package com.bibliotheque.metier;

import com.bibliotheque.dao.DocumentDAO;
import com.bibliotheque.dao.UtilisateurDAO;
import com.bibliotheque.metier.Interface.Reservable;
import com.bibliotheque.metier.Interface.Empruntable;

import java.util.List;
import java.util.UUID;

public class Bibliotheque {
    private UtilisateurDAO utilisateurDAO;
    private DocumentDAO documentDAO;

    public Bibliotheque(UtilisateurDAO utilisateurDAO, DocumentDAO documentDAO) {
        this.utilisateurDAO = utilisateurDAO;
        this.documentDAO = documentDAO;
    }

    public void ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.ajouterUtilisateur(utilisateur);
    }

    public void ajouterDocument(Document document) {
        documentDAO.ajouterDocument(document);
    }

    public void afficherDocuments() {
        List<Document> documents = documentDAO.getAllDocuments();
        for (Document document : documents) {
            document.afficherDetails();
        }
    }

    public void afficherUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurDAO.getAllUtilisateurs();
        for (Utilisateur utilisateur : utilisateurs) {
            utilisateur.afficherDetails();
        }
    }

    public boolean reserverDocument(UUID idDocument, UUID idUtilisateur) {
        Document document = documentDAO.rechercherDocumentParId(idDocument);
        Utilisateur utilisateur = utilisateurDAO.rechercherUtilisateurParId(idUtilisateur);
        if (document != null && utilisateur != null && document instanceof Reservable) {
            Reservable reservableDoc = (Reservable) document;
            if (!reservableDoc.estReserve()) {
                reservableDoc.reserver(utilisateur);
                documentDAO.mettreAJourDocument(document);
                System.out.println("Le document a été réservé avec succès.");
                return true;
            } else {
                System.out.println("Le document est déjà réservé.");
            }
        }
        return false;
    }

    public boolean emprunterDocument(UUID idDocument, UUID idUtilisateur) {
        Document document = documentDAO.rechercherDocumentParId(idDocument);
        Utilisateur utilisateur = utilisateurDAO.rechercherUtilisateurParId(idUtilisateur);
        if (document != null && utilisateur != null && document instanceof Empruntable) {
            Empruntable empruntableDoc = (Empruntable) document;
            if (empruntableDoc.estDisponible()) {
                empruntableDoc.emprunter(utilisateur);
                documentDAO.mettreAJourDocument(document);
                System.out.println("Le document a été emprunté avec succès.");
                return true;
            } else {
                System.out.println("Le document n'est pas disponible.");
            }
        }
        return false;
    }

    public boolean annulerReservationDocument(UUID idDocument) {
        Document document = documentDAO.rechercherDocumentParId(idDocument);
        if (document != null && document instanceof Reservable) {
            Reservable reservableDoc = (Reservable) document;
            if (reservableDoc.estReserve()) {
                reservableDoc.annulerReservation();
                documentDAO.mettreAJourDocument(document);
                System.out.println("La réservation a été annulée.");
                return true;
            } else {
                System.out.println("Le document n'est pas réservé.");
            }
        }
        return false;
    }

    public boolean retournerDocument(UUID idDocument) {
        Document document = documentDAO.rechercherDocumentParId(idDocument);
        if (document != null && document instanceof Empruntable) {
            Empruntable empruntableDoc = (Empruntable) document;
            empruntableDoc.retourner();
            documentDAO.mettreAJourDocument(document);
            System.out.println("Le document a été retourné.");
            return true;
        }
        return false;
    }

    public List<Document> rechercherDocumentParTitre(String titre) {
        return documentDAO.rechercherDocumentParTitre(titre); // Utilisation de l'instance de documentDAO
    }


}
