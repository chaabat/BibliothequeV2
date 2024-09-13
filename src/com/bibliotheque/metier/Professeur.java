package com.bibliotheque.metier;

import java.util.UUID;

public class Professeur extends Utilisateur {
    private String departement;


    public Professeur(UUID id, String nom, String email, String departement) {
        super(id, nom, email, departement);
    }



    public String getDepartement() { return departement; }
    public void setDepartement(String departement) { this.departement = departement; }

    @Override
    public void afficherDetails() { /* Implémentation */ }
    @Override
    public boolean peutEmprunter() { return true; }
    @Override
    public String getInformationsSupplementaires() { return "Département: " + departement; }
}
