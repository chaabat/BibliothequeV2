package com.bibliotheque.presentation;

import com.bibliotheque.dao.DocumentDAO;
import com.bibliotheque.dao.Interface.DocumentDAOInterface;
import com.bibliotheque.dao.UtilisateurDAO;
import com.bibliotheque.metier.*;
import com.bibliotheque.utilitaire.InputValidator;

import java.sql.SQLException;
import java.time.LocalDate;
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

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

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

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

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
                    bibliotheque.afficherUtilisateurs();
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

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

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

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

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

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

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
            System.out.println("\n=== Menu Bibliothèque ===");
            System.out.println("1. Ajouter un utilisateur");
            System.out.println("2. Afficher tous les utilisateurs");
            System.out.println("3. Rechercher un utilisateur");
            System.out.println("4. Modifier un utilisateur");
            System.out.println("5. Supprimer un utilisateur");
            System.out.println("6. Quitter");
            System.out.print("Choisissez une option : ");

            int choix = Integer.parseInt(scanner.nextLine());

            switch (choix) {
                case 1:
                    ajouterUtilisateur();
                    break;
                case 2:
                    afficherTousUtilisateurs();
                    break;
                case 3:
                    rechercherUtilisateur();
                    break;
                case 4:
                    modifierUtilisateur();
                    break;
                case 5:
                    supprimerUtilisateur();
                    break;
                case 6:
                    System.out.println("Au revoir!");
                    return;
                default:
                    System.out.println("Option invalide, veuillez réessayer.");
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
                System.out.println(utilisateur); // Ensure toString() is well-defined for Utilisateur
            }
        }
    }

    private void rechercherUtilisateur() {
        System.out.println("\n=== Rechercher un Utilisateur ===");
        System.out.print("Le Nom d'utilisateur : ");
        String recherche = scanner.nextLine();


        try {
            UUID id = UUID.fromString(recherche);
            Utilisateur utilisateur = bibliotheque.rechercherUtilisateurParId(id);
            if (utilisateur != null) {
                System.out.println(utilisateur); // Ensure toString() is well-defined for Utilisateur
            } else {
                System.out.println("Utilisateur non trouvé.");
            }
        } catch (IllegalArgumentException e) {
            // If it's not a valid UUID, try to search by name
            Utilisateur utilisateur = bibliotheque.rechercherUtilisateurParNom(recherche);
            if (utilisateur != null) {
                System.out.println(utilisateur); // Ensure toString() is well-defined for Utilisateur
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

        System.out.print("Nouvel email de l'utilisateur : ");
        String nouvelEmail = scanner.nextLine();


        utilisateur.setNom(nouveauNom);
        utilisateur.setEmail(nouvelEmail);

        if (utilisateur instanceof Etudiant) {
            System.out.print("Nouveau programme d'études : ");
            String nouveauProgrammeEtudes = scanner.nextLine();
            ((Etudiant) utilisateur).setProgrammeEtudes(nouveauProgrammeEtudes);
        } else if (utilisateur instanceof Professeur) {
            System.out.print("Nouveau département : ");
            String nouveauDepartement = scanner.nextLine();
            ((Professeur) utilisateur).setDepartement(nouveauDepartement);
        }

        bibliotheque.modifierUtilisateur(utilisateur);
        System.out.println("Utilisateur modifié avec succès.");
    }

    private void supprimerUtilisateur() {
        System.out.println("\n=== Supprimer un Utilisateur ===");
        System.out.print("Nom de l'utilisateur à supprimer : ");
        String nomUtilisateur = scanner.nextLine();

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
            scanner.nextLine(); // Consommer la nouvelle ligne

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
            scanner.nextLine(); // Consommer la nouvelle ligne

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
                System.out.print("ISBN : ");
                String isbn = scanner.nextLine(); // Read the ISBN

                // Create a Livre instance with the ISBN
                document = new Livre(idDocument, titre, auteur, datePublication, nombreDePages, isbn);
                break;

            case 2: // Magazine
                System.out.print("Auteur : ");
                String auteurMagazine = scanner.nextLine();
                System.out.print("Date de publication (YYYY-MM-DD) : ");
                LocalDate datePublicationMagazine = LocalDate.parse(scanner.nextLine());
                System.out.print("Nombre de pages : ");
                int nombreDePagesMagazine = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne
                System.out.print("Numéro du magazine : ");
                int numero = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne

                document = new Magazine(idDocument, titre, auteurMagazine, datePublicationMagazine, nombreDePagesMagazine, numero);
                break;
            case 3: // Thèse Universitaire
                System.out.print("Auteur : ");
                String auteurThese = scanner.nextLine();
                System.out.print("Date de publication (YYYY-MM-DD) : ");
                LocalDate datePublicationThese = LocalDate.parse(scanner.nextLine());
                System.out.print("Nombre de pages : ");
                int nombreDePagesThese = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne

                System.out.print("Université : ");
                String universite = scanner.nextLine();
                System.out.print("Domaine d'étude : ");
                String domaineEtude = scanner.nextLine();
                System.out.print("Année de soumission : ");
                int anneeSoumission = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne

                document = new TheseUniversitaire(idDocument, titre, auteurThese, datePublicationThese, nombreDePagesThese, universite, domaineEtude, anneeSoumission);
                break;
            case 4: // Journal Scientifique
                System.out.print("Auteur : ");
                String auteurJournal = scanner.nextLine();
                System.out.print("Date de publication (YYYY-MM-DD) : ");
                LocalDate datePublicationJournal = LocalDate.parse(scanner.nextLine());
                System.out.print("Nombre de pages : ");
                int nombreDePagesJournal = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne

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
        String titre = scanner.nextLine();

        DocumentDAO documentDAO = new DocumentDAO();
        List<Document> documents = documentDAO.rechercherDocumentParTitre(titre);

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
        String titreDocument = scanner.nextLine();

        // Récupérer le type du document
        System.out.println("1. Livre");
        System.out.println("2. Magazine");
        System.out.println("3. Journal Scientifique");
        System.out.println("4. Thèse Universitaire");
        System.out.print("Choisissez le type du document (1/2/3/4) : ");
        int typeDocument = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne

        // Rechercher le document par titre et type
        DocumentDAO documentDAO = new DocumentDAO();
        Document document = documentDAO.rechercherDocumentParTitreEtType(titreDocument, typeDocument);
        if (document == null) {
            System.out.println("Document non trouvé.");
            return;
        }

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
        int choixModification = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne

        switch (choixModification) {
            case 1: // Modifier le titre
                System.out.print("Nouveau titre : ");
                String nouveauTitre = scanner.nextLine();
                document.setTitre(nouveauTitre);
                break;
            case 2: // Modifier l'auteur
                System.out.print("Nouvel auteur : ");
                String nouvelAuteur = scanner.nextLine();
                document.setAuteur(nouvelAuteur);
                break;
            case 3: // Modifier la date de publication
                System.out.print("Nouvelle date de publication (YYYY-MM-DD) : ");
                LocalDate nouvelleDatePublication = LocalDate.parse(scanner.nextLine());
                document.setDatePublication(nouvelleDatePublication);
                break;
            case 4: // Modifier le nombre de pages
                System.out.print("Nouveau nombre de pages : ");
                int nouveauNombreDePages = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne
                document.setNombreDePages(nouveauNombreDePages);
                break;
            case 5: // Modifier l'ISBN (pour les Livres)
                if (document instanceof Livre) {
                    System.out.print("Nouvel ISBN : ");
                    String nouvelIsbn = scanner.nextLine();
                    ((Livre) document).setIsbn(nouvelIsbn);
                } else {
                    System.out.println("Modification de l'ISBN uniquement possible pour les Livres.");
                }
                break;
            case 6: // Modifier l'université (pour les Thèses Universitaires)
                if (document instanceof TheseUniversitaire) {
                    System.out.print("Nouvelle université : ");
                    String nouvelleUniversite = scanner.nextLine();
                    ((TheseUniversitaire) document).setUniversite(nouvelleUniversite);
                } else {
                    System.out.println("Modification de l'université uniquement possible pour les Thèses Universitaires.");
                }
                break;
            case 7: // Modifier le domaine d'étude (pour les Thèses Universitaires)
                if (document instanceof TheseUniversitaire) {
                    System.out.print("Nouveau domaine d'étude : ");
                    String nouveauDomaineEtude = scanner.nextLine();
                    ((TheseUniversitaire) document).setDomaineEtude(nouveauDomaineEtude);
                } else {
                    System.out.println("Modification du domaine d'étude uniquement possible pour les Thèses Universitaires.");
                }
                break;
            case 8: // Modifier l'année de soumission (pour les Thèses Universitaires)
                if (document instanceof TheseUniversitaire) {
                    System.out.print("Nouvelle année de soumission : ");
                    int nouvelleAnneeSoumission = scanner.nextInt();
                    scanner.nextLine(); // Consommer la nouvelle ligne
                    ((TheseUniversitaire) document).setAnneeSoumission(nouvelleAnneeSoumission);
                } else {
                    System.out.println("Modification de l'année de soumission uniquement possible pour les Thèses Universitaires.");
                }
                break;
            default:
                System.out.println("Choix invalide.");
                return;
        }

        documentDAO.mettreAJourDocument(document);
        System.out.println("Document modifié avec succès.");
    }
    private void supprimerDocument() {
        System.out.println("\n=== Supprimer un Document ===");
        System.out.print("Titre du document à supprimer : ");
        String titreDocument = scanner.nextLine();

        // Récupérer le type du document
        System.out.println("1. Livre");
        System.out.println("2. Magazine");
        System.out.println("3. Journal Scientifique");
        System.out.println("4. Thèse Universitaire");
        System.out.print("Choisissez le type du document (1/2/3/4) : ");
        int typeDocument = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne

        // Supprimer le document par titre et type
        DocumentDAO documentDAO = new DocumentDAO();
        if (documentDAO.supprimerDocumentParTitreEtType(titreDocument, typeDocument)) {
            System.out.println("Document supprimé avec succès.");
        } else {
            System.out.println("La suppression du document a échoué.");
        }
    }






    private void emprunterDocument() {
        System.out.println("\n=== Emprunter un Document ===");
        System.out.print("Titre du document : ");
        String titreDocument = scanner.nextLine();

        System.out.print("ID de l'utilisateur : ");
        String idUtilisateurStr = scanner.nextLine();
        if (!InputValidator.isValidUUID(idUtilisateurStr)) {
            System.out.println("L'ID de l'utilisateur n'est pas valide.");
            return;
        }
        UUID idUtilisateur = UUID.fromString(idUtilisateurStr);

        // Rechercher le document par titre dans la bibliothèque
        Document document = (Document) bibliotheque.rechercherDocumentParTitre(titreDocument);
        if (document == null) {
            System.out.println("Document non trouvé.");
            return;
        }

        if (bibliotheque.emprunterDocument(document.getId(), idUtilisateur)) {
            System.out.println("Document emprunté avec succès.");
        } else {
            System.out.println("L'emprunt du document a échoué.");
        }
    }

    private void retournerDocument() {
        System.out.println("\n=== Retourner un Document ===");
        System.out.print("Titre du document : ");
        String titreDocument = scanner.nextLine();

        // Rechercher le document par titre dans la bibliothèque
        Document document = (Document) bibliotheque.rechercherDocumentParTitre(titreDocument);
        if (document == null) {
            System.out.println("Document non trouvé.");
            return;
        }

        if (bibliotheque.retournerDocument(document.getId())) {
            System.out.println("Document retourné avec succès.");
        } else {
            System.out.println("Le retour du document a échoué.");
        }
    }

    private void reserverDocument() {
        System.out.println("\n=== Réserver un Document ===");
        System.out.print("Titre du document : ");
        String titreDocument = scanner.nextLine();

        System.out.print("ID de l'utilisateur : ");
        String idUtilisateurStr = scanner.nextLine();
        if (!InputValidator.isValidUUID(idUtilisateurStr)) {
            System.out.println("L'ID de l'utilisateur n'est pas valide.");
            return;
        }
        UUID idUtilisateur = UUID.fromString(idUtilisateurStr);

        // Rechercher le document par titre dans la bibliothèque
        Document document = (Document) bibliotheque.rechercherDocumentParTitre(titreDocument);
        if (document == null) {
            System.out.println("Document non trouvé.");
            return;
        }

        if (bibliotheque.reserverDocument(document.getId(), idUtilisateur)) {
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
        Document document = (Document) bibliotheque.rechercherDocumentParTitre(titreDocument);
        if (document == null) {
            System.out.println("Document non trouvé.");
            return;
        }

        if (bibliotheque.annulerReservationDocument(document.getId())) {
            System.out.println("Réservation annulée avec succès.");
        } else {
            System.out.println("L'annulation de la réservation a échoué.");
        }
    }

}