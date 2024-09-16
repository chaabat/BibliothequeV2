package com.bibliotheque.presentation;

import com.bibliotheque.dao.DocumentDAO;
import com.bibliotheque.dao.Interface.DocumentDAOInterface;
import com.bibliotheque.metier.*;
import com.bibliotheque.utilitaire.InputValidator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ConsoleUI {
    private Bibliotheque bibliotheque;
    private Scanner scanner;
    private DocumentDAOInterface documentDAO;

    public ConsoleUI(Bibliotheque bibliotheque) {
        this.bibliotheque = bibliotheque;
        this.scanner = new Scanner(System.in);
    }

    public void afficherMenuPrincipal() {
        while (true) {
            System.out.println("\n=== Sélectionnez le type d'utilisateur ===");
            System.out.println("1. Admin");
            System.out.println("2. Étudiant");
            System.out.println("3. Professeur");
            System.out.print("Choisissez une option : ");

            String choixStr = scanner.nextLine();

            // Validate if the input is a valid integer
            if (!InputValidator.isValidInteger(choixStr)) {
                System.out.println("Choix invalide, veuillez entrer un nombre.");
                continue;
            }

            int choix = Integer.parseInt(choixStr);

            switch (choix) {
                case 1:
                    afficherMenuAdmin();
                    break;
                case 2:
                    afficherMenuEtudiant();
                    break;
                case 3:
                    afficherMenuProfesseur();
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }


    private void afficherMenuAdmin() {
        while (true) {
            System.out.println("\n=== Menu Admin ===");
            System.out.println("1. Gestion des documents");
            System.out.println("2. Gestion des utilisateurs");
            System.out.println("3. Gestion des emprunts");
            System.out.println("4. Gestion des réservations");
            System.out.println("5. Afficher tous les documents");
            System.out.println("6. Afficher tous les utilisateurs");
            System.out.println("7. Quitter");
            System.out.print("Choisissez une option : ");

            String choixStr = scanner.nextLine();

            // Validate if the input is a valid integer
            if (!InputValidator.isValidInteger(choixStr)) {
                System.out.println("Choix invalide, veuillez entrer un nombre.");
                continue;
            }

            int choix = Integer.parseInt(choixStr);

            switch (choix) {
                case 1:
                    afficherMenuDocuments();
                    break;
                case 2:
                    afficherMenuUtilisateurs();
                    break;
                case 3:
                    afficherMenuEmprunts();
                    break;
                case 4:
                    afficherMenuReservations();
                    break;
                case 5:
                    bibliotheque.afficherDocuments();
                    break;
                case 6:
                    afficherTousUtilisateurs();
                    break;
                case 7:
                    System.out.println("Au revoir !");
                    return;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }


    private void afficherMenuEtudiant() {
        while (true) {
            System.out.println("\n=== Menu Étudiant ===");
            System.out.println("1. Emprunter un livre ou un magazine");
            System.out.println("2. Retourner un document");
            System.out.println("3. Réserver un livre ou un magazine");
            System.out.println("4. Annuler une réservation");
            System.out.println("5. Quitter");
            System.out.print("Choisissez une option : ");

            String choixStr = scanner.nextLine();

            if (!InputValidator.isValidInteger(choixStr)) {
                System.out.println("Choix invalide, veuillez entrer un nombre.");
                continue;
            }

            int choix = Integer.parseInt(choixStr);

            switch (choix) {
                case 1:
                    emprunterDocument();
                    break;
                case 2:
                    retournerDocument();
                    break;
                case 3:
                    reserverDocument();
                    break;
                case 4:
                    annulerReservation();
                    break;
                case 5:
                    System.out.println("Au revoir !");
                    return;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }


    private void afficherMenuProfesseur() {
        while (true) {
            System.out.println("\n=== Menu Professeur ===");
            System.out.println("1. Emprunter un document");
            System.out.println("2. Retourner un document");
            System.out.println("3. Réserver un document");
            System.out.println("4. Annuler une réservation");
            System.out.println("5. Quitter");
            System.out.print("Choisissez une option : ");

            String choixStr = scanner.nextLine();

            if (!InputValidator.isValidInteger(choixStr)) {
                System.out.println("Choix invalide, veuillez entrer un nombre.");
                continue;
            }

            int choix = Integer.parseInt(choixStr);

            switch (choix) {
                case 1:
                    emprunterDocument();
                    break;
                case 2:
                    retournerDocument();
                    break;
                case 3:
                    reserverDocument();
                    break;
                case 4:
                    annulerReservation();
                    break;
                case 5:
                    System.out.println("Au revoir !");
                    return;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }





    // Methods for document management
    private void afficherMenuDocuments() {
        while (true) {
            System.out.println("\n=== Gestion des Documents ===");
            System.out.println("1. Ajouter un document");
            System.out.println("2. Modifier un document");
            System.out.println("3. Rechercher un document");
            System.out.println("4. Supprimer un document");
            System.out.println("5. Retour au menu principal");
            System.out.print("Choisissez une option : ");

            String choixStr = scanner.nextLine();

            if (!InputValidator.isValidInteger(choixStr)) {
                System.out.println("Choix invalide, veuillez entrer un nombre.");
                continue;
            }

            int choix = Integer.parseInt(choixStr);

            switch (choix) {
                case 1:
                    ajouterDocument();
                    break;
                case 2:
                    modifierDocument();
                    break;
                case 3:
                    rechercherDocument();
                    break;
                case 4:
                    supprimerDocument();
                    break;
                case 5:
                    return; // Retour au menu principal
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }


    // Methods for user management
    public void afficherMenuUtilisateurs() {
        while (true) {
            System.out.println("\n=== Menu Utilisateur ===");
            System.out.println("1. Ajouter un utilisateur");
            System.out.println("2. Rechercher un utilisateur");
            System.out.println("3. Modifier un utilisateur");
            System.out.println("4. Supprimer un utilisateur");
            System.out.println("5. Quitter");
            System.out.print("Choisissez une option : ");

            String choixStr = scanner.nextLine();

            // Validate input
            if (!InputValidator.isValidInteger(choixStr)) {
                System.out.println("Option invalide, veuillez entrer un nombre.");
                continue;
            }

            int choix = Integer.parseInt(choixStr);

            // Check if the choice is within the valid range
            if (choix < 1 || choix > 6) {
                System.out.println("Option invalide, veuillez réessayer.");
                continue;
            }

            switch (choix) {
                case 1:
                    ajouterUtilisateur();
                    break;

                case 2:
                    rechercherUtilisateur();
                    break;
                case 3:
                    modifierUtilisateur();
                    break;
                case 4:
                    supprimerUtilisateur();
                    break;
                case 5:
                    System.out.println("Au revoir!");
                    return;
            }
        }
    }


    private void ajouterUtilisateur() {
        System.out.println("\n=== Ajouter un Utilisateur ===");

        System.out.println("1. Ajouter un étudiant");
        System.out.println("2. Ajouter un professeur");
        System.out.print("Choisissez une option (1 ou 2) : ");
        String choixStr = scanner.nextLine();

        if (!InputValidator.isValidInteger(choixStr)) {
            System.out.println("Option invalide. Veuillez entrer un nombre.");
            return;
        }

        int choix = Integer.parseInt(choixStr);
        UUID id = UUID.randomUUID(); // Generate a unique ID
        String nom, email;

        // Validate name
        do {
            System.out.print("Nom de l'utilisateur : ");
            nom = scanner.nextLine();
            if (!InputValidator.isNotEmpty(nom)) {
                System.out.println("Le nom ne peut pas être vide.");
            }
        } while (!InputValidator.isNotEmpty(nom));

        // Validate email
        do {
            System.out.print("Email de l'utilisateur : ");
            email = scanner.nextLine();
            if (!InputValidator.isValidEmail(email)) {
                System.out.println("L'email est invalide.");
            }
        } while (!InputValidator.isValidEmail(email));

        switch (choix) {
            case 1:
                // Adding an Etudiant
                String programmeEtudes;
                do {
                    System.out.print("Programme d'études : ");
                    programmeEtudes = scanner.nextLine();
                    if (!InputValidator.isNotEmpty(programmeEtudes)) {
                        System.out.println("Le programme d'études ne peut pas être vide.");
                    }
                } while (!InputValidator.isNotEmpty(programmeEtudes));

                Utilisateur etudiant = new Etudiant(id, nom, email, programmeEtudes);
                bibliotheque.ajouterUtilisateur(etudiant);
                System.out.println("Étudiant ajouté avec succès.");
                break;

            case 2:
                // Adding a Professeur
                String departement;
                do {
                    System.out.print("Département : ");
                    departement = scanner.nextLine();
                    if (!InputValidator.isNotEmpty(departement)) {
                        System.out.println("Le département ne peut pas être vide.");
                    }
                } while (!InputValidator.isNotEmpty(departement));

                Utilisateur professeur = new Professeur(id, nom, email, departement);
                bibliotheque.ajouterUtilisateur(professeur);
                System.out.println("Professeur ajouté avec succès.");
                break;

            default:
                System.out.println("Option invalide. Veuillez choisir 1 pour étudiant ou 2 pour professeur.");
        }
    }





    private void afficherTousUtilisateurs() {
        List<Utilisateur> utilisateurs = bibliotheque.getAllUtilisateurs();

        if (utilisateurs.isEmpty()) {
            System.out.println("Aucun utilisateur trouvé.");
        } else {
            for (Utilisateur utilisateur : utilisateurs) {
                System.out.println(utilisateur);
            }
        }
    }

    private void rechercherUtilisateur() {
        System.out.println("\n=== Rechercher un Utilisateur ===");
        System.out.print("Le Nom d'utilisateur ou ID : ");
        String recherche = scanner.nextLine();

        if (InputValidator.isValidUUID(recherche)) {
            UUID id = UUID.fromString(recherche);
            Utilisateur utilisateur = bibliotheque.rechercherUtilisateurParId(id);
            if (utilisateur != null) {
                System.out.println(utilisateur);
            } else {
                System.out.println("Utilisateur non trouvé.");
            }
        } else {
            Utilisateur utilisateur = bibliotheque.rechercherUtilisateurParNom(recherche);
            if (utilisateur != null) {
                System.out.println(utilisateur);
            } else {
                System.out.println("Utilisateur non trouvé.");
            }
        }
    }


    private void modifierUtilisateur() {
        System.out.println("\n=== Modifier un Utilisateur ===");
        System.out.print("Nom de l'utilisateur à modifier : ");
        String nomUtilisateur = scanner.nextLine();

        Utilisateur utilisateur = bibliotheque.rechercherUtilisateurParNom(nomUtilisateur);
        if (utilisateur == null) {
            System.out.println("Utilisateur non trouvé.");
            return;
        }

        System.out.print("Nouveau nom de l'utilisateur : ");
        String nouveauNom = scanner.nextLine();
        if (nouveauNom.isEmpty()) {
            System.out.println("Le nom ne peut pas être vide.");
            return;
        }

        System.out.print("Nouvel email de l'utilisateur : ");
        String nouvelEmail = scanner.nextLine();
        if (!InputValidator.isValidEmail(nouvelEmail)) {
            System.out.println("Adresse email invalide.");
            return;
        }

        utilisateur.setNom(nouveauNom);
        utilisateur.setEmail(nouvelEmail);

        if (utilisateur instanceof Etudiant) {
            System.out.print("Nouveau programme d'études : ");
            String nouveauProgrammeEtudes = scanner.nextLine();
            if (nouveauProgrammeEtudes.isEmpty()) {
                System.out.println("Le programme d'études ne peut pas être vide.");
                return;
            }
            ((Etudiant) utilisateur).setProgrammeEtudes(nouveauProgrammeEtudes);
        } else if (utilisateur instanceof Professeur) {
            System.out.print("Nouveau département : ");
            String nouveauDepartement = scanner.nextLine();
            if (nouveauDepartement.isEmpty()) {
                System.out.println("Le département ne peut pas être vide.");
                return;
            }
            ((Professeur) utilisateur).setDepartement(nouveauDepartement);
        }

        bibliotheque.modifierUtilisateur(utilisateur);
        System.out.println("Utilisateur modifié avec succès.");
    }


    private void supprimerUtilisateur() {
        System.out.println("\n=== Supprimer un Utilisateur ===");
        System.out.print("Nom de l'utilisateur à supprimer : ");
        String nomUtilisateur = scanner.nextLine();

        if (nomUtilisateur.isEmpty()) {
            System.out.println("Le nom d'utilisateur ne peut pas être vide.");
            return;
        }

        Utilisateur utilisateur = bibliotheque.rechercherUtilisateurParNom(nomUtilisateur);
        if (utilisateur == null) {
            System.out.println("Utilisateur non trouvé.");
            return;
        }

        boolean success = bibliotheque.supprimerUtilisateur(utilisateur.getId());
        if (success) {
            System.out.println("Utilisateur supprimé avec succès.");
        } else {
            System.out.println("Erreur lors de la suppression de l'utilisateur.");
        }
    }


    // Methods for loan management
    private void afficherMenuEmprunts() {
        while (true) {
            System.out.println("\n=== Gestion des Emprunts ===");
            System.out.println("1. Emprunter un document");
            System.out.println("2. Retourner un document");
            System.out.println("3. Retour au menu principal");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    emprunterDocument();
                    break;
                case 2:
                    retournerDocument();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }

    // Methods for reservation management
    private void afficherMenuReservations() {
        while (true) {
            System.out.println("\n=== Gestion des Réservations ===");
            System.out.println("1. Réserver un document");
            System.out.println("2. Annuler la réservation d'un document");
            System.out.println("3. Retour au menu principal");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    reserverDocument();
                    break;
                case 2:
                    annulerReservation();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }





    private void ajouterDocument() {
        System.out.println("\n=== Ajouter un Document ===");

        // Document title
        String titre;
        while (true) {
            System.out.print("Titre du document : ");
            titre = scanner.nextLine().trim();
            if (!titre.isEmpty()) {
                break;
            }
            System.out.println("Le titre du document ne peut pas être vide.");
        }

        // Generate UUID automatically
        UUID idDocument = UUID.randomUUID();

        // Document type
        int typeDocument;
        while (true) {
            System.out.println("Choisissez le type de document :");
            System.out.println("1. Livre");
            System.out.println("2. Magazine");
            System.out.println("3. Thèse Universitaire");
            System.out.println("4. Journal Scientifique");
            System.out.print("Entrez le numéro de type : ");
            try {
                typeDocument = Integer.parseInt(scanner.nextLine().trim());
                if (typeDocument >= 1 && typeDocument <= 4) {
                    break;
                } else {
                    System.out.println("Type de document invalide. Veuillez entrer un numéro entre 1 et 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
            }
        }

        Document document = null;

        switch (typeDocument) {
            case 1: // Livre
                String auteur;
                while (true) {
                    System.out.print("Auteur : ");
                    auteur = scanner.nextLine().trim();
                    if (!auteur.isEmpty()) {
                        break;
                    }
                    System.out.println("L'auteur ne peut pas être vide.");
                }

                LocalDate datePublication;
                while (true) {
                    System.out.print("Date de publication (YYYY-MM-DD) : ");
                    try {
                        datePublication = LocalDate.parse(scanner.nextLine().trim());
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Format de date invalide. Veuillez utiliser YYYY-MM-DD.");
                    }
                }

                int nombreDePages;
                while (true) {
                    System.out.print("Nombre de pages : ");
                    try {
                        nombreDePages = Integer.parseInt(scanner.nextLine().trim());
                        if (nombreDePages > 0) {
                            break;
                        } else {
                            System.out.println("Le nombre de pages doit être un nombre positif.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Nombre de pages invalide.");
                    }
                }

                String isbn;
                while (true) {
                    System.out.print("ISBN : ");
                    isbn = scanner.nextLine().trim();
                    if (!isbn.isEmpty()) {
                        break;
                    }
                    System.out.println("L'ISBN ne peut pas être vide.");
                }

                document = new Livre(idDocument, titre, auteur, datePublication, nombreDePages, isbn);
                break;

            case 2: // Magazine
                String auteurMagazine;
                while (true) {
                    System.out.print("Auteur : ");
                    auteurMagazine = scanner.nextLine().trim();
                    if (!auteurMagazine.isEmpty()) {
                        break;
                    }
                    System.out.println("L'auteur ne peut pas être vide.");
                }

                LocalDate datePublicationMagazine;
                while (true) {
                    System.out.print("Date de publication (YYYY-MM-DD) : ");
                    try {
                        datePublicationMagazine = LocalDate.parse(scanner.nextLine().trim());
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Format de date invalide. Veuillez utiliser YYYY-MM-DD.");
                    }
                }

                int nombreDePagesMagazine;
                while (true) {
                    System.out.print("Nombre de pages : ");
                    try {
                        nombreDePagesMagazine = Integer.parseInt(scanner.nextLine().trim());
                        if (nombreDePagesMagazine > 0) {
                            break;
                        } else {
                            System.out.println("Le nombre de pages doit être un nombre positif.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Nombre de pages invalide.");
                    }
                }

                int numero;
                while (true) {
                    System.out.print("Numéro du magazine : ");
                    try {
                        numero = Integer.parseInt(scanner.nextLine().trim());
                        if (numero > 0) {
                            break;
                        } else {
                            System.out.println("Le numéro du magazine doit être un nombre positif.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Numéro de magazine invalide.");
                    }
                }

                document = new Magazine(idDocument, titre, auteurMagazine, datePublicationMagazine, nombreDePagesMagazine, numero);
                break;

            case 3: // Thèse Universitaire
                String auteurThese;
                while (true) {
                    System.out.print("Auteur : ");
                    auteurThese = scanner.nextLine().trim();
                    if (!auteurThese.isEmpty()) {
                        break;
                    }
                    System.out.println("L'auteur ne peut pas être vide.");
                }

                LocalDate datePublicationThese;
                while (true) {
                    System.out.print("Date de publication (YYYY-MM-DD) : ");
                    try {
                        datePublicationThese = LocalDate.parse(scanner.nextLine().trim());
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Format de date invalide. Veuillez utiliser YYYY-MM-DD.");
                    }
                }

                int nombreDePagesThese;
                while (true) {
                    System.out.print("Nombre de pages : ");
                    try {
                        nombreDePagesThese = Integer.parseInt(scanner.nextLine().trim());
                        if (nombreDePagesThese > 0) {
                            break;
                        } else {
                            System.out.println("Le nombre de pages doit être un nombre positif.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Nombre de pages invalide.");
                    }
                }

                String universite;
                while (true) {
                    System.out.print("Université : ");
                    universite = scanner.nextLine().trim();
                    if (!universite.isEmpty()) {
                        break;
                    }
                    System.out.println("L'université ne peut pas être vide.");
                }

                String domaineEtude;
                while (true) {
                    System.out.print("Domaine d'étude : ");
                    domaineEtude = scanner.nextLine().trim();
                    if (!domaineEtude.isEmpty()) {
                        break;
                    }
                    System.out.println("Le domaine d'étude ne peut pas être vide.");
                }

                int anneeSoumission;
                while (true) {
                    System.out.print("Année de soumission : ");
                    try {
                        anneeSoumission = Integer.parseInt(scanner.nextLine().trim());
                        if (anneeSoumission > 0) {
                            break;
                        } else {
                            System.out.println("L'année de soumission doit être une année valide.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Année de soumission invalide.");
                    }
                }

                document = new TheseUniversitaire(idDocument, titre, auteurThese, datePublicationThese, nombreDePagesThese, universite, domaineEtude, anneeSoumission);
                break;

            case 4: // Journal Scientifique
                String auteurJournal;
                while (true) {
                    System.out.print("Auteur : ");
                    auteurJournal = scanner.nextLine().trim();
                    if (!auteurJournal.isEmpty()) {
                        break;
                    }
                    System.out.println("L'auteur ne peut pas être vide.");
                }

                LocalDate datePublicationJournal;
                while (true) {
                    System.out.print("Date de publication (YYYY-MM-DD) : ");
                    try {
                        datePublicationJournal = LocalDate.parse(scanner.nextLine().trim());
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Format de date invalide. Veuillez utiliser YYYY-MM-DD.");
                    }
                }

                int nombreDePagesJournal;
                while (true) {
                    System.out.print("Nombre de pages : ");
                    try {
                        nombreDePagesJournal = Integer.parseInt(scanner.nextLine().trim());
                        if (nombreDePagesJournal > 0) {
                            break;
                        } else {
                            System.out.println("Le nombre de pages doit être un nombre positif.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Nombre de pages invalide.");
                    }
                }

                document = new JournalScientifique(idDocument, titre, auteurJournal, datePublicationJournal, nombreDePagesJournal);
                break;

            default:
                System.out.println("Type de document invalide.");
                return;
        }

        if (document != null) {
            DocumentDAO documentDAO = new DocumentDAO();
            documentDAO.ajouterDocument(document);
            System.out.println("Document ajouté avec succès.");
        }
    }




    private void rechercherDocument() {
        System.out.println("\n=== Rechercher un Document ===");
        System.out.print("Titre du document : ");
        String titre = scanner.nextLine().trim(); // Trim to remove leading/trailing spaces

        // Validate input
        if (titre.isEmpty()) {
            System.out.println("Le titre du document ne peut pas être vide.");
            return;
        }

        DocumentDAO documentDAO = new DocumentDAO();
        List<Document> documents;

        try {
            documents = documentDAO.rechercherDocumentParTitre(titre);
        } catch (Exception e) {
            System.out.println("Une erreur est survenue lors de la recherche du document : " + e.getMessage());
            return;
        }

        if (documents.isEmpty()) {
            System.out.println("Aucun document trouvé avec ce titre.");
        } else {
            for (Document document : documents) {
                document.afficherDetails(); // Assuming a method to display document details
            }
        }
    }



    private void modifierDocument() {
        System.out.println("\n=== Modifier un Document ===");
        System.out.print("Titre du document à modifier : ");
        String titreDocument = scanner.nextLine().trim();

        if (titreDocument.isEmpty()) {
            System.out.println("Le titre du document ne peut pas être vide.");
            return;
        }

        // Récupérer le type du document
        System.out.println("1. Livre");
        System.out.println("2. Magazine");
        System.out.println("3. Journal Scientifique");
        System.out.println("4. Thèse Universitaire");
        System.out.print("Choisissez le type du document (1/2/3/4) : ");
        int typeDocument;

        try {
            typeDocument = Integer.parseInt(scanner.nextLine());
            if (typeDocument < 1 || typeDocument > 4) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide pour le type de document.");
            return;
        }

        // Rechercher le document par titre et type
        DocumentDAO documentDAO = new DocumentDAO();
        Document document;

        try {
            document = documentDAO.rechercherDocumentParTitreEtType(titreDocument, typeDocument);
            if (document == null) {
                throw new Exception("Document non trouvé.");
            }
        } catch (Exception e) {
            System.out.println("Une erreur est survenue lors de la recherche du document : " + e.getMessage());
            return;
        }

        // Afficher les options de modification
        System.out.println("Choisissez les attributs à modifier :");
        System.out.println("1. Titre");
        System.out.println("2. Auteur");
        System.out.println("3. Date de publication");
        System.out.println("4. Nombre de pages");
        System.out.println("5. ISBN (pour les Livres)");
        System.out.println("6. Université (pour les Thèses Universitaires)");
        System.out.println("7. Domaine d'étude (pour les Thèses Universitaires)");
        System.out.println("8. Année de soumission (pour les Thèses Universitaires)");
        System.out.print("Entrez le numéro de l'attribut à modifier : ");
        int choixModification;

        try {
            choixModification = Integer.parseInt(scanner.nextLine());
            if (choixModification < 1 || choixModification > 8) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide pour l'attribut à modifier.");
            return;
        }

        switch (choixModification) {
            case 1: // Modifier le titre
                System.out.print("Nouveau titre : ");
                String nouveauTitre = scanner.nextLine().trim();
                if (nouveauTitre.isEmpty()) {
                    System.out.println("Le titre ne peut pas être vide.");
                    return;
                }
                document.setTitre(nouveauTitre);
                break;

            case 2: // Modifier l'auteur
                System.out.print("Nouvel auteur : ");
                String nouvelAuteur = scanner.nextLine().trim();
                if (nouvelAuteur.isEmpty()) {
                    System.out.println("L'auteur ne peut pas être vide.");
                    return;
                }
                document.setAuteur(nouvelAuteur);
                break;

            case 3: // Modifier la date de publication
                System.out.print("Nouvelle date de publication (YYYY-MM-DD) : ");
                try {
                    LocalDate nouvelleDatePublication = LocalDate.parse(scanner.nextLine());
                    document.setDatePublication(nouvelleDatePublication);
                } catch (DateTimeParseException e) {
                    System.out.println("Format de date invalide. Assurez-vous d'utiliser le format YYYY-MM-DD.");
                    return;
                }
                break;

            case 4: // Modifier le nombre de pages
                System.out.print("Nouveau nombre de pages : ");
                try {
                    int nouveauNombreDePages = Integer.parseInt(scanner.nextLine());
                    if (nouveauNombreDePages <= 0) {
                        throw new NumberFormatException();
                    }
                    document.setNombreDePages(nouveauNombreDePages);
                } catch (NumberFormatException e) {
                    System.out.println("Nombre de pages invalide. Veuillez entrer un nombre entier positif.");
                    return;
                }
                break;

            case 5: // Modifier l'ISBN (pour les Livres)
                if (document instanceof Livre) {
                    System.out.print("Nouvel ISBN : ");
                    String nouvelIsbn = scanner.nextLine().trim();
                    if (nouvelIsbn.isEmpty()) {
                        System.out.println("L'ISBN ne peut pas être vide.");
                        return;
                    }
                    ((Livre) document).setIsbn(nouvelIsbn);
                } else {
                    System.out.println("Modification de l'ISBN uniquement possible pour les Livres.");
                    return;
                }
                break;

            case 6: // Modifier l'université (pour les Thèses Universitaires)
                if (document instanceof TheseUniversitaire) {
                    System.out.print("Nouvelle université : ");
                    String nouvelleUniversite = scanner.nextLine().trim();
                    if (nouvelleUniversite.isEmpty()) {
                        System.out.println("L'université ne peut pas être vide.");
                        return;
                    }
                    ((TheseUniversitaire) document).setUniversite(nouvelleUniversite);
                } else {
                    System.out.println("Modification de l'université uniquement possible pour les Thèses Universitaires.");
                    return;
                }
                break;

            case 7: // Modifier le domaine d'étude (pour les Thèses Universitaires)
                if (document instanceof TheseUniversitaire) {
                    System.out.print("Nouveau domaine d'étude : ");
                    String nouveauDomaineEtude = scanner.nextLine().trim();
                    if (nouveauDomaineEtude.isEmpty()) {
                        System.out.println("Le domaine d'étude ne peut pas être vide.");
                        return;
                    }
                    ((TheseUniversitaire) document).setDomaineEtude(nouveauDomaineEtude);
                } else {
                    System.out.println("Modification du domaine d'étude uniquement possible pour les Thèses Universitaires.");
                    return;
                }
                break;

            case 8: // Modifier l'année de soumission (pour les Thèses Universitaires)
                if (document instanceof TheseUniversitaire) {
                    System.out.print("Nouvelle année de soumission : ");
                    try {
                        int nouvelleAnneeSoumission = Integer.parseInt(scanner.nextLine());
                        if (nouvelleAnneeSoumission <= 0) {
                            throw new NumberFormatException();
                        }
                        ((TheseUniversitaire) document).setAnneeSoumission(nouvelleAnneeSoumission);
                    } catch (NumberFormatException e) {
                        System.out.println("Année de soumission invalide. Veuillez entrer un nombre entier positif.");
                        return;
                    }
                } else {
                    System.out.println("Modification de l'année de soumission uniquement possible pour les Thèses Universitaires.");
                    return;
                }
                break;

            default:
                System.out.println("Choix invalide.");
                return;
        }

        try {
            documentDAO.mettreAJourDocument(document);
            System.out.println("Document modifié avec succès.");
        } catch (Exception e) {
            System.out.println("Une erreur est survenue lors de la modification du document : " + e.getMessage());
        }
    }


    private void supprimerDocument() {
        System.out.println("\n=== Supprimer un Document ===");
        System.out.print("Titre du document à supprimer : ");
        String titreDocument = scanner.nextLine().trim(); // Trim to remove leading/trailing spaces

        // Validate input
        if (titreDocument.isEmpty()) {
            System.out.println("Le titre du document ne peut pas être vide.");
            return;
        }

        // Récupérer le type du document
        System.out.println("1. Livre");
        System.out.println("2. Magazine");
        System.out.println("3. Journal Scientifique");
        System.out.println("4. Thèse Universitaire");
        System.out.print("Choisissez le type du document (1/2/3/4) : ");
        int typeDocument;

        try {
            typeDocument = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide pour le type de document.");
            return;
        }

        // Validate typeDocument
        if (typeDocument < 1 || typeDocument > 4) {
            System.out.println("Type de document invalide.");
            return;
        }

        DocumentDAO documentDAO = new DocumentDAO();

        // Check if the document exists
        Document document = documentDAO.rechercherDocumentParTitreEtType(titreDocument, typeDocument);
        if (document == null) {
            System.out.println("Document non trouvé.");
            return;
        }

        // Supprimer le document par titre et type
        boolean success = false;
        try {
            success = documentDAO.supprimerDocumentParTitreEtType(titreDocument, typeDocument);
        } catch (Exception e) {
            System.out.println("Une erreur est survenue lors de la suppression du document : " + e.getMessage());
        }

        if (success) {
            System.out.println("Document supprimé avec succès.");
        } else {
            System.out.println("La suppression du document a échoué.");
        }
    }

    private void emprunterDocument() {
        System.out.println("\n=== Emprunter un Document ===");
        System.out.print("Titre du document : ");
        String titreDocument = scanner.nextLine();

        // Rechercher le document par titre
        List<Document> documents = (List<Document>) bibliotheque.rechercherDocumentParTitre(titreDocument);
        if (documents.isEmpty()) {
            System.out.println("Aucun document trouvé avec ce titre.");
            return;
        }

        // Afficher les documents trouvés
        for (int i = 0; i < documents.size(); i++) {
            System.out.println((i + 1) + ". " + documents.get(i));  // Affiche le numéro et le document
        }

        System.out.print("Sélectionner le numéro du document à emprunter : ");
        int numeroDocument = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (numeroDocument < 1 || numeroDocument > documents.size()) {
            System.out.println("Numéro de document invalide.");
            return;
        }

        Document document = documents.get(numeroDocument - 1);  // Sélectionner le document basé sur le numéro

        System.out.print("Nom de l'utilisateur : ");
        String nomUtilisateur = scanner.nextLine();

        // Rechercher l'utilisateur par nom
        Utilisateur utilisateur = bibliotheque.rechercherUtilisateurParNom(nomUtilisateur);
        if (utilisateur == null) {
            System.out.println("Utilisateur non trouvé.");
            return;
        }

        // Emprunter le document
        if (bibliotheque.emprunterDocument(document.getId(), utilisateur.getId())) {
            System.out.println("Document emprunté avec succès.");
        } else {
            System.out.println("L'emprunt du document a échoué. Vérifiez les règles d'emprunt ou si le document est déjà emprunté.");
        }
    }





    private void retournerDocument() {
        System.out.println("\n=== Retourner un Document ===");
        System.out.print("Titre du document : ");
        String titreDocument = scanner.nextLine();

        // Rechercher les documents par titre dans la bibliothèque
        List<Document> documents = (List<Document>) bibliotheque.rechercherDocumentParTitre(titreDocument);
        if (documents.isEmpty()) {
            System.out.println("Aucun document trouvé avec ce titre.");
            return;
        }

        Document document = documents.get(0);  // Sélectionner le premier document si plusieurs existent

        // Retourner le document et vérifier les réservations
        if (bibliotheque.annulerReservationDocument(document.getId())) {
            System.out.println("Document retourné avec succès.");
        } else {
            System.out.println("Le retour du document a échoué.");
        }
    }



    private void reserverDocument() {
        System.out.println("\n=== Réserver un Document ===");
        System.out.print("Titre du document : ");
        String titreDocument = scanner.nextLine();

        System.out.print("Nom de l'utilisateur : ");
        String nomUtilisateur = scanner.nextLine();

        // Rechercher le document par titre dans la bibliothèque
        List<Document> documents = bibliotheque.rechercherDocumentParTitre(titreDocument);
        if (documents == null || documents.isEmpty()) {
            System.out.println("Aucun document trouvé avec ce titre.");
            return;
        }

        // Afficher les documents trouvés
        for (int i = 0; i < documents.size(); i++) {
            System.out.println((i + 1) + ". " + documents.get(i));
        }

        System.out.print("Sélectionnez le numéro du document à réserver : ");
        int numeroDocument = scanner.nextInt();
        scanner.nextLine();  // Consommer la nouvelle ligne

        if (numeroDocument < 1 || numeroDocument > documents.size()) {
            System.out.println("Numéro de document invalide.");
            return;
        }

        // Sélectionner le document basé sur l'index
        Document document = documents.get(numeroDocument - 1);

        // Rechercher l'utilisateur par nom
        Utilisateur utilisateur = bibliotheque.rechercherUtilisateurParNom(nomUtilisateur);
        if (utilisateur == null) {
            System.out.println("Utilisateur non trouvé.");
            return;
        }

        // Réserver le document
        if (bibliotheque.reserverDocument(document.getId(), utilisateur.getId())) {
            System.out.println("Document réservé avec succès.");
        } else {
            System.out.println("La réservation du document a échoué.");
        }
    }


    private void annulerReservation() {
        System.out.println("\n=== Annuler la Réservation d'un Document ===");
        System.out.print("Titre du document : ");
        String titreDocument = scanner.nextLine();

        // Rechercher le document par titre dans la bibliothèque
        List<Document> documents = bibliotheque.rechercherDocumentParTitre(titreDocument);
        if (documents == null || documents.isEmpty()) {
            System.out.println("Aucun document trouvé avec ce titre.");
            return;
        }

        // Afficher les documents trouvés
        for (int i = 0; i < documents.size(); i++) {
            System.out.println((i + 1) + ". " + documents.get(i));
        }

        System.out.print("Sélectionnez le numéro du document à annuler la réservation : ");
        int numeroDocument = scanner.nextInt();
        scanner.nextLine();  // Consommer la nouvelle ligne

        if (numeroDocument < 1 || numeroDocument > documents.size()) {
            System.out.println("Numéro de document invalide.");
            return;
        }

        // Sélectionner le document basé sur l'index
        Document document = documents.get(numeroDocument - 1);

        // Annuler la réservation du document
        if (bibliotheque.annulerReservationDocument(document.getId())) {
            System.out.println("Réservation annulée avec succès.");
        } else {
            System.out.println("L'annulation de la réservation a échoué.");
        }
    }



}