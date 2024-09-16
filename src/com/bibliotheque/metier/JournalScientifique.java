package com.bibliotheque.metier;

import java.time.LocalDate;
import java.util.UUID;

public class JournalScientifique extends Document {
    private String domaineRecherche;
    private String editeur;

    // Constructor without domaineRecherche and editeur
    public JournalScientifique(UUID id, String titre, String auteur, LocalDate datePublication, int nombreDePages) {
        super(id, titre, auteur, datePublication, nombreDePages);
    }

    // Constructor with domaineRecherche and editeur
    public JournalScientifique(UUID id, String titre, String auteur, LocalDate datePublication, int nombreDePages, String domaineRecherche, String editeur) {
        super(id, titre, auteur, datePublication, nombreDePages);
        this.domaineRecherche = domaineRecherche;
        this.editeur = editeur;
    }

    // Getters and Setters for domaineRecherche and editeur
    public String getDomaineRecherche() {
        return domaineRecherche;
    }

    public void setDomaineRecherche(String domaineRecherche) {
        this.domaineRecherche = domaineRecherche;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    @Override
    public String getType() {
        return "Journal Scientifique";
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %s\nTitre: %s\nAuteur: %s\nDate de Publication: %s\nNombre de Pages: %d\nDomaine de Recherche: %s\nÉditeur: %s\n",
                getId(), getTitre(), getAuteur(), getDatePublication(), getNombreDePages(), domaineRecherche, editeur
        );
    }
}
