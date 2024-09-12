package com.bibliotheque;

import com.bibliotheque.metier.Bibliotheque;
import com.bibliotheque.presentation.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        // Create a Bibliotheque instance
        Bibliotheque bibliotheque = new Bibliotheque();

        // Create a ConsoleUI instance with the Bibliotheque instance
        ConsoleUI consoleUI = new ConsoleUI(bibliotheque);

        // Display the main menu
        consoleUI.afficherMenuPrincipal();
    }
}
