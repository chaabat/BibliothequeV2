package com.bibliotheque.metier;

import java.util.UUID;

public class Etudiant extends Utilisateur {
    private String filiere;
    private String programmeEtudes;

    // Constructor
    public Etudiant(UUID id, String nom, String email, String programmeEtudes) {
        super(id, nom, email, programmeEtudes);
    }

    // Getter and Setter for 'programmeEtudes'
    public String getProgrammeEtudes() {
        return programmeEtudes;
    }

    public void setProgrammeEtudes(String programmeEtudes) {
        this.programmeEtudes = programmeEtudes;
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
        return "  Programme d'études: " + programmeEtudes;
    }

    @Override
    public int getLimiteEmprunt() {
        return 0; // Define the logic if needed
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", programmeEtudes='" + programmeEtudes + '\'' +

                '}';
    }
}
