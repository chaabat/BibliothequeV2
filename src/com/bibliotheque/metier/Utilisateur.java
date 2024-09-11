package com.bibliotheque.metier;

import java.util.UUID;

public abstract class Utilisateur {
    protected UUID id;
    protected String nom;
    protected String email;
    protected String motDePasse;

    public Utilisateur(UUID id, String nom, String email, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public abstract void afficherDetails();
    public abstract boolean peutEmprunter();
    public abstract String getInformationsSupplementaires();
}
