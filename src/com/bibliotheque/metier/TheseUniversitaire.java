package com.bibliotheque.metier;

import java.time.LocalDate;
import java.util.UUID;

public class TheseUniversitaire extends Document {
    private String universite;
    private String domaineEtude;
    private int anneeSoumission;

    public TheseUniversitaire(UUID id, String titre, String auteur, LocalDate datePublication, int nombreDePages, String universite, String domaineEtude, int anneeSoumission) {
        super(id, titre, auteur, datePublication, nombreDePages);
        this.universite = universite;
        this.domaineEtude = domaineEtude;
        this.anneeSoumission = anneeSoumission;
    }

    @Override
    public String getType() {
        return "TheseUniversitaire";
    }

    // Getters and setters
    public String getUniversite() {
        return universite;
    }

    public void setUniversite(String universite) {
        this.universite = universite;
    }

    public String getDomaineEtude() {
        return domaineEtude;
    }

    public void setDomaineEtude(String domaineEtude) {
        this.domaineEtude = domaineEtude;
    }

    public int getAnneeSoumission() {
        return anneeSoumission;
    }

    public void setAnneeSoumission(int anneeSoumission) {
        this.anneeSoumission = anneeSoumission;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %s\nTitre: %s\nAuteur: %s\nDate de Publication: %s\nNombre de Pages: %d\nUniversité: %s\nDomaine d'Étude: %s\nAnnée de Soumission: %d\n",
                getId(), getTitre(), getAuteur(), getDatePublication(), getNombreDePages(), universite, domaineEtude, anneeSoumission
        );
    }
}
