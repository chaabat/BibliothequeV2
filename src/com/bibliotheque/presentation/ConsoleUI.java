package com.bibliotheque.presentation;

import com.bibliotheque.metier.*;
import com.bibliotheque.utilitaire.InputValidator;

import java.util.Scanner;
import java.util.UUID;

public class ConsoleUI {
    private Bibliotheque bibliotheque;
    private Scanner scanner;

    public ConsoleUI(Bibliotheque bibliotheque) {
        this.bibliotheque = bibliotheque;
        this.scanner = new Scanner(System.in);
    }

    public void afficherMenuPrincipal() {
        while (true) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Ajouter un document");
            System.out.println("2. Ajouter un utilisateur");
            System.out.println("3. Emprunter un document");
            System.out.println("4. Retourner un document");
            System.out.println("5. Réserver un document");
            System.out.println("6. Annuler la réservation d'un document");
            System.out.println("7. Afficher tous les documents");
            System.out.println("8. Afficher tous les utilisateurs");
            System.out.println("9. Quitter");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            switch (choix) {
                case 1:
                    ajouterDocument();
                    break;
                case 2:
                    ajouterUtilisateur();
                    break;
                case 3:
                    emprunterDocument();
                    break;
                case 4:
                    retournerDocument();
                    break;
                case 5:
                    reserverDocument();
                    break;
                case 6:
                    annulerReservation();
                    break;
                case 7:
                    bibliotheque.afficherDocuments();
                    break;
                case 8:
                    bibliotheque.afficherUtilisateurs();
                    break;
                case 9:
                    System.out.println("Au revoir !");
                    return;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }

    private void ajouterDocument() {
        // Votre implémentation existante pour ajouter un document
    }

    private void ajouterUtilisateur() {
        // Votre implémentation existante pour ajouter un utilisateur
    }

    private void emprunterDocument() {
        System.out.println("\n=== Emprunter un Document ===");
        System.out.print("ID du document : ");
        String idDocumentStr = scanner.nextLine();
        if (InputValidator.isValidUUID(idDocumentStr)) {
            System.out.println("L'ID du document n'est pas valide.");
            return;
        }
        UUID idDocument = UUID.fromString(idDocumentStr);

        System.out.print("ID de l'utilisateur : ");
        String idUtilisateurStr = scanner.nextLine();
        if (InputValidator.isValidUUID(idUtilisateurStr)) {
            System.out.println("L'ID de l'utilisateur n'est pas valide.");
            return;
        }
        UUID idUtilisateur = UUID.fromString(idUtilisateurStr);

        if (bibliotheque.emprunterDocument(idDocument, idUtilisateur)) {
            System.out.println("Document emprunté avec succès.");
        } else {
            System.out.println("L'emprunt du document a échoué.");
        }
    }

    private void retournerDocument() {
        System.out.println("\n=== Retourner un Document ===");
        System.out.print("ID du document : ");
        String idDocumentStr = scanner.nextLine();
        if (InputValidator.isValidUUID(idDocumentStr)) {
            System.out.println("L'ID du document n'est pas valide.");
            return;
        }
        UUID idDocument = UUID.fromString(idDocumentStr);

        if (bibliotheque.retournerDocument(idDocument)) {
            System.out.println("Document retourné avec succès.");
        } else {
            System.out.println("Le retour du document a échoué.");
        }
    }

    private void reserverDocument() {
        System.out.println("\n=== Réserver un Document ===");
        System.out.print("ID du document : ");
        String idDocumentStr = scanner.nextLine();
        if (InputValidator.isValidUUID(idDocumentStr)) {
            System.out.println("L'ID du document n'est pas valide.");
            return;
        }
        UUID idDocument = UUID.fromString(idDocumentStr);

        System.out.print("ID de l'utilisateur : ");
        String idUtilisateurStr = scanner.nextLine();
        if (InputValidator.isValidUUID(idUtilisateurStr)) {
            System.out.println("L'ID de l'utilisateur n'est pas valide.");
            return;
        }
        UUID idUtilisateur = UUID.fromString(idUtilisateurStr);

        if (bibliotheque.reserverDocument(idDocument, idUtilisateur)) {
            System.out.println("Document réservé avec succès.");
        } else {
            System.out.println("La réservation du document a échoué.");
        }
    }

    private void annulerReservation() {
        System.out.println("\n=== Annuler la Réservation d'un Document ===");
        System.out.print("ID du document : ");
        String idDocumentStr = scanner.nextLine();
        if (InputValidator.isValidUUID(idDocumentStr)) {
            System.out.println("L'ID du document n'est pas valide.");
            return;
        }
        UUID idDocument = UUID.fromString(idDocumentStr);

        if (bibliotheque.annulerReservation(idDocument)) {
            System.out.println("Réservation annulée avec succès.");
        } else {
            System.out.println("L'annulation de la réservation a échoué.");
        }
    }


}
