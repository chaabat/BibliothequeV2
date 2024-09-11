package com.bibliotheque.presentation;

import com.bibliotheque.dao.DocumentDAO;
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
            System.out.println("2. Modifier un document");
            System.out.println("3. Supprimer un document");
            System.out.println("4. Ajouter un utilisateur");
            System.out.println("5. Emprunter un document");
            System.out.println("6. Retourner un document");
            System.out.println("7. Réserver un document");
            System.out.println("8. Annuler la réservation d'un document");
            System.out.println("9. Afficher tous les documents");
            System.out.println("10. Afficher tous les utilisateurs");
            System.out.println("11. Quitter");
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
                    supprimerDocument();
                    break;
                case 4:
                    ajouterUtilisateur();
                    break;
                case 5:
                    emprunterDocument();
                    break;
                case 6:
                    retournerDocument();
                    break;
                case 7:
                    reserverDocument();
                    break;
                case 8:
                    annulerReservation();
                    break;
                case 9:
                    bibliotheque.afficherDocuments();
                    break;
                case 10:
                    bibliotheque.afficherUtilisateurs();
                    break;
                case 11:
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
                System.out.print("ISBN : ");
                String isbn = scanner.nextLine(); // Read the ISBN

                // Create a Livre instance with the ISBN
                document = new Livre(idDocument, titre, auteur, datePublication, nombreDePages, isbn);
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
            DocumentDAO documentDAO = new DocumentDAO();
            documentDAO.ajouterDocument(document);
            System.out.println("Document ajouté avec succès.");
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
