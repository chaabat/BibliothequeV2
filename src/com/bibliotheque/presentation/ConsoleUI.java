package com.bibliotheque.presentation;

import com.bibliotheque.metier.*;
import com.bibliotheque.utilitaire.InputValidator;

import java.time.LocalDate;
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
        System.out.println("\n=== Ajouter un Document ===");
        System.out.print("Titre du document : ");
        String titre = scanner.nextLine();

        // Generate UUID automatically
        UUID idDocument = UUID.randomUUID();

        System.out.println("Choisissez le type de document :");
        System.out.println("1. Livre");
        System.out.println("2. Magazine");
        System.out.println("3. Thèse Universitaire");
        System.out.println("4. Journal Scientifique");
        System.out.print("Entrez le numéro de type : ");
        int typeDocument = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne

        Document document = null;

        switch (typeDocument) {
            case 1: // Livre
                System.out.print("Auteur : ");
                String auteur = scanner.nextLine();
                System.out.print("Date de publication (YYYY-MM-DD) : ");
                LocalDate datePublication = LocalDate.parse(scanner.nextLine());
                System.out.print("Nombre de pages : ");
                int nombreDePages = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne

                document = new Livre(idDocument, titre, auteur, datePublication, nombreDePages);
                break;
            case 2: // Magazine
                System.out.print("Auteur : ");
                auteur = scanner.nextLine();
                System.out.print("Date de publication (YYYY-MM-DD) : ");
                datePublication = LocalDate.parse(scanner.nextLine());
                System.out.print("Nombre de pages : ");
                nombreDePages = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne

                document = new Magazine(idDocument, titre, auteur, datePublication, nombreDePages);
                break;
            case 3: // Thèse Universitaire
                System.out.print("Auteur : ");
                auteur = scanner.nextLine();
                System.out.print("Date de publication (YYYY-MM-DD) : ");
                datePublication = LocalDate.parse(scanner.nextLine());
                System.out.print("Nombre de pages : ");
                nombreDePages = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne

                System.out.print("Université : ");
                String universite = scanner.nextLine();
                System.out.print("Domaine d'étude : ");
                String domaineEtude = scanner.nextLine();
                System.out.print("Année de soumission : ");
                int anneeSoumission = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne

                document = new TheseUniversitaire(idDocument, titre, auteur, datePublication, nombreDePages, universite, domaineEtude, anneeSoumission);
                break;
            case 4: // Journal Scientifique
                System.out.print("Auteur : ");
                auteur = scanner.nextLine();
                System.out.print("Date de publication (YYYY-MM-DD) : ");
                datePublication = LocalDate.parse(scanner.nextLine());
                System.out.print("Nombre de pages : ");
                nombreDePages = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne

                document = new JournalScientifique(idDocument, titre, auteur, datePublication, nombreDePages);
                break;
            default:
                System.out.println("Type de document invalide.");
                return;
        }

        if (document != null) {
            bibliotheque.ajouterDocument(document);
            System.out.println("Document ajouté avec succès.");
        }
    }


    // Method to generate a new UUID
    private UUID generateUuid() {
        return UUID.randomUUID();
    }




    private void ajouterUtilisateur() {
        System.out.println("\n=== Ajouter un Utilisateur ===");
        System.out.println("1. Étudiant");
        System.out.println("2. Professeur");
        System.out.print("Choisissez le type d'utilisateur (1/2) : ");
        int choix = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne

        // Generate a new UUID for the utilisateur
        UUID idUtilisateur = UUID.randomUUID();

        System.out.print("Nom de l'utilisateur : ");
        String nom = scanner.nextLine();

        System.out.print("Email de l'utilisateur : ");
        String email = scanner.nextLine();

        System.out.print("Mot de passe de l'utilisateur : ");
        String motDePasse = scanner.nextLine();

        Utilisateur utilisateur = null;
        switch (choix) {
            case 1:
                System.out.print("Filière de l'étudiant : ");
                String filiere = scanner.nextLine();
                utilisateur = new Etudiant(idUtilisateur, nom, email, motDePasse, filiere);
                break;
            case 2:
                System.out.print("Département du professeur : ");
                String departement = scanner.nextLine();
                utilisateur = new Professeur(idUtilisateur, nom, email, motDePasse, departement);
                break;
            default:
                System.out.println("Choix invalide.");
                return;
        }

        bibliotheque.ajouterUtilisateur(utilisateur);
        System.out.println("Utilisateur ajouté avec succès.");
    }




    private void emprunterDocument() {
        System.out.println("\n=== Emprunter un Document ===");
        System.out.print("ID du document : ");
        String idDocumentStr = scanner.nextLine();
        if (!InputValidator.isValidUUID(idDocumentStr)) {
            System.out.println("L'ID du document n'est pas valide.");
            return;
        }
        UUID idDocument = UUID.fromString(idDocumentStr);

        System.out.print("ID de l'utilisateur : ");
        String idUtilisateurStr = scanner.nextLine();
        if (!InputValidator.isValidUUID(idUtilisateurStr)) {
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
        if (!InputValidator.isValidUUID(idDocumentStr)) {
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
        if (!InputValidator.isValidUUID(idDocumentStr)) {
            System.out.println("L'ID du document n'est pas valide.");
            return;
        }
        UUID idDocument = UUID.fromString(idDocumentStr);

        System.out.print("ID de l'utilisateur : ");
        String idUtilisateurStr = scanner.nextLine();
        if (!InputValidator.isValidUUID(idUtilisateurStr)) {
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
        if (!InputValidator.isValidUUID(idDocumentStr)) {
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
