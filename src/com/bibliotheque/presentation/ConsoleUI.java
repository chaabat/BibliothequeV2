package com.bibliotheque.presentation;

import com.bibliotheque.metier.Bibliotheque;
import com.bibliotheque.metier.Document;
import com.bibliotheque.metier.Livre;
import com.bibliotheque.metier.Magazine;

import java.time.LocalDate;
import java.util.Scanner;


public class ConsoleUI {
    private Bibliotheque bibliotheque;
    private Scanner scanner;

    public ConsoleUI() {
        bibliotheque = new Bibliotheque();
        scanner = new Scanner(System.in);
    }

    public void afficherMenu() {
        int choix = 0;
        do {
            System.out.println("\n--- Menu Bibliothèque ---");
            System.out.println("1. Ajouter un document");
            System.out.println("2. Emprunter un document");
            System.out.println("3. Retourner un document");
            System.out.println("4. Afficher tous les documents");
            System.out.println("5. Rechercher un document");
            System.out.println("6. Quitter");

            boolean choixValide = false;
            while (!choixValide) {
                System.out.print("Veuillez entrer votre choix (1-6) : ");
                if (scanner.hasNextInt()) {
                    choix = scanner.nextInt();
                    scanner.nextLine();
                    if (choix >= 1 && choix <= 6) {
                        choixValide = true;
                    } else {
                        System.out.println("Choix invalide, veuillez entrer un nombre entre 1 et 6.");
                    }
                } else {
                    System.out.println("Entrée non valide, veuillez entrer un nombre entier.");
                    scanner.next();
                }
            }

            switch (choix) {
                case 1:
                    ajouterDocument();
                    pause();
                    break;
                case 2:
                    emprunterDocument();
                    pause();
                    break;
                case 3:
                    retournerDocument();
                    pause();
                    break;
                case 4:
                    afficherTousLesDocuments();
                    pause();
                    break;
                case 5:
                    rechercherDocument();
                    pause();
                    break;
                case 6:
                    quitter();
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
                    pause();
            }
        } while (choix != 6);
    }


    private void ajouterDocument() {
        int type = 0;
        while (true) {
            System.out.print("Type de document (1 - Livre, 2 - Magazine) : ");
            if (scanner.hasNextInt()) {
                type = scanner.nextInt();
                scanner.nextLine();
                if (type == 1 || type == 2) {
                    break;
                } else {
                    System.out.println("Type invalide. Veuillez entrer 1 pour Livre ou 2 pour Magazine.");
                }
            } else {
                System.out.println("Veuillez entrer un nombre entier (1 ou 2).");
                scanner.next();
            }
        }

        System.out.print("Titre : ");
        String titre = scanner.nextLine();

        System.out.print("Auteur : ");
        String auteur = scanner.nextLine();

        LocalDate datePublication = null;
        while (datePublication == null) {
            System.out.print("Date de publication (yyyy-mm-dd) : ");
            String dateStr = scanner.nextLine();
            try {
                datePublication = LocalDate.parse(dateStr);
            } catch (Exception e) {
                System.out.println("Format de date invalide. Veuillez entrer une date au format yyyy-mm-dd.");
            }
        }

        int nombreDePages = 0;
        while (true) {
            System.out.print("Nombre de pages : ");
            if (scanner.hasNextInt()) {
                nombreDePages = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("Veuillez entrer un nombre entier pour le nombre de pages.");
                scanner.next();
            }
        }

        if (type == 1) {
            System.out.print("ISBN : ");
            String isbn = scanner.nextLine();
            Document livre = new Livre(bibliotheque.getNextId(), titre, auteur, datePublication, nombreDePages, isbn);
            bibliotheque.ajouterDocument(livre);
            System.out.println("Livre ajouté avec succès.");
        } else if (type == 2) {
            int numero = 0;
            while (true) {
                System.out.print("Numéro de magazine : ");
                if (scanner.hasNextInt()) {
                    numero = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } else {
                    System.out.println("Veuillez entrer un nombre entier pour le numéro de magazine.");
                    scanner.next();
                }
            }
            Document magazine = new Magazine(bibliotheque.getNextId(), titre, auteur, datePublication, nombreDePages, numero);
            bibliotheque.ajouterDocument(magazine);
            System.out.println("Magazine ajouté avec succès.");
        }
    }


    private void emprunterDocument() {
        System.out.print("ID du document à emprunter : ");
        int id = scanner.nextInt();

        Document doc = bibliotheque.rechercherDocumentParId(id);
        if (doc != null) {
            doc.emprunter();
        } else {
            System.out.println("Document non trouvé.");
        }
    }

    private void retournerDocument() {
        System.out.print("ID du document à retourner : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Document doc = bibliotheque.rechercherDocumentParId(id);
        if (doc != null) {
            doc.retourner();
        } else {
            System.out.println("Document non trouvé.");
        }
    }

    private void afficherTousLesDocuments() {
        System.out.println("\n--- Liste des documents ---");
        for (Document doc : bibliotheque.getDocuments()) {
            doc.afficherDetails();
        }
    }

    private void rechercherDocument() {
        System.out.print("Titre du document à rechercher : ");
        String titre = scanner.nextLine();
        Document doc = bibliotheque.rechercherDocument(titre);
        if (doc != null) {
            doc.afficherDetails();
        } else {
            System.out.println("Document non trouvé.");
        }
    }

    private void quitter() {
        System.out.println("Au revoir !");
    }

    // Méthode pour faire une pause et attendre une entrée de l'utilisateur
    private void pause() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        ui.afficherMenu();
    }
}
