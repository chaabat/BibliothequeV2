package com.bibliotheque;

import com.bibliotheque.metier.Bibliotheque;
import com.bibliotheque.presentation.ConsoleUI;
import com.bibliotheque.dao.UtilisateurDAO;
import com.bibliotheque.dao.DocumentDAO;

public class Main {
    public static void main(String[] args) {
        // Initialiser les DAO (si nécessaire)
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO(); // Assurez-vous que ce DAO a un constructeur correct
        DocumentDAO documentDAO = new DocumentDAO(); // Assurez-vous que ce DAO a un constructeur correct

        // Créer une instance de Bibliotheque en passant les DAO
        Bibliotheque bibliotheque = new Bibliotheque(utilisateurDAO, documentDAO);

        // Créer une ConsoleUI avec l'instance Bibliotheque
        ConsoleUI consoleUI = new ConsoleUI(bibliotheque);

        // Afficher le menu principal
        consoleUI.afficherMenuPrincipal();
    }
}
