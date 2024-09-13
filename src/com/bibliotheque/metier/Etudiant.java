package com.bibliotheque.metier;

import java.util.UUID;

public class Etudiant extends Utilisateur {
    private String filiere;



    public Etudiant(UUID id, String nom, String email, String programmeEtudes) {
        super(id, nom, email, programmeEtudes);
    }



    public String getFiliere() { return filiere; }
    public void setFiliere(String filiere) { this.filiere = filiere; }

    @Override
    public void afficherDetails() { /* Implémentation */ }
    @Override
    public boolean peutEmprunter() { return true; }
    @Override
    public String getInformationsSupplementaires() { return "Filière: " + filiere; }

    public String getProgrammeEtudes() {
        return null;
    }

    public int getLimiteEmprunt() {
        return 0;
    }
}
