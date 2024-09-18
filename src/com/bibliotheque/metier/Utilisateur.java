package com.bibliotheque.metier;

import java.util.UUID;

public abstract class Utilisateur {
    protected UUID id;
    protected String nom;
    protected String email;


    // Constructor
    public Utilisateur(UUID id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;

    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }


    // Abstract Methods
    public abstract void afficherDetails();
    public abstract boolean peutEmprunter();
    public abstract String getInformationsSupplementaires();
    public abstract int getLimiteEmprunt();

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +

                '}';
    }
}
