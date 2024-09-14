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

    // User methods
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.ajouterUtilisateur(utilisateur);
    }

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurDAO.getAllUtilisateurs();
    }

    public Utilisateur rechercherUtilisateurParId(UUID id) {
        return utilisateurDAO.rechercherUtilisateurParId(id);
    }

    public Utilisateur rechercherUtilisateurParNom(String nom) {
        return utilisateurDAO.trouverUtilisateurParNom(nom);
    }

    public void modifierUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.mettreAJourUtilisateur(utilisateur);
    }

    public boolean supprimerUtilisateur(UUID id) {
        try {
            utilisateurDAO.supprimerUtilisateur(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Document methods
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
        } else {
            System.out.println("Le document ou l'utilisateur est invalide ou le document n'est pas réservable.");
        }
        return false;
    }

    public boolean emprunterDocument(UUID idDocument, UUID idUtilisateur) {
        Document document = documentDAO.rechercherDocumentParId(idDocument);
        Utilisateur utilisateur = utilisateurDAO.rechercherUtilisateurParId(idUtilisateur);

        if (document == null || utilisateur == null) {
            System.out.println("Document ou utilisateur non trouvé.");
            return false;
        }

        // Check if the user is a student and enforce the borrowing rules
        if (utilisateur instanceof Etudiant) {
            int borrowedCount = utilisateurDAO.countDocumentsEmpruntes(utilisateur.getId());
            if (borrowedCount >= 5) {
                System.out.println("L'étudiant a déjà emprunté 5 documents.");
                return false;
            }
            if (!(document instanceof Livre || document instanceof Magazine)) {
                System.out.println("Les étudiants ne peuvent emprunter que des livres ou des magazines.");
                return false;
            }
        }

        // Professors can borrow any document type, so no need for specific checks here
        if (document instanceof Empruntable) {
            Empruntable empruntableDoc = (Empruntable) document;
            if (empruntableDoc.estDisponible()) {
                empruntableDoc.emprunter(utilisateur);
                documentDAO.mettreAJourDocument(document);
                System.out.println("Le document a été emprunté avec succès.");
                return true;
            } else {
                System.out.println("Le document n'est pas disponible.");
            }
        } else {
            System.out.println("Ce document n'est pas empruntable.");
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
        } else {
            System.out.println("Le document est invalide ou n'est pas réservable.");
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
        } else {
            System.out.println("Le document est invalide ou n'est pas empruntable.");
        }
        return false;
    }

    public Document rechercherDocumentParTitre(String titre) {
        return (Document) documentDAO.rechercherDocumentParTitre(titre);
    }
}
