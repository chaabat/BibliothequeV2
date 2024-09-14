package com.bibliotheque.metier;

import java.util.UUID;

public class Professeur extends Utilisateur {
    private String departement;

    // Constructor
    public Professeur(UUID id, String nom, String email, String motDePasse) {
        super(id, nom, email, motDePasse);
        this.departement = departement;
    }

    // Getters and Setters
    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    @Override
    public void afficherDetails() {
        // Implementation here
    }

    @Override
    public boolean peutEmprunter() {
        return true; // Define the logic if needed
    }

    @Override
    public String getInformationsSupplementaires() {
        return "Département: " + departement;
    }

    @Override
    public int getLimiteEmprunt() {
        // Define the limit for Professeur
        return 10; // Example limit
    }

    @Override
    public String toString() {
        return "Professeur{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", departement='" + departement + '\'' +
                '}';
    }
}
