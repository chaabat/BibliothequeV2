package com.bibliotheque.metier;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Document {
    private UUID id;
    private String titre;
    private String auteur;
    private LocalDate datePublication;
    private int nombreDePages;
    private UUID empruntePar;
    private UUID reservePar;

    // Constructor with automatic UUID generation
    public Document(String titre, String auteur, LocalDate datePublication, int nombreDePages) {
        this.id = UUID.randomUUID(); // Automatically generate UUID
        this.titre = titre;
        this.auteur = auteur;
        this.datePublication = datePublication;
        this.nombreDePages = nombreDePages;
    }

    public Document(UUID id, String titre, String auteur, LocalDate datePublication, int nombreDePages) {




    }

    // Abstract method
    public abstract String getType();

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    public int getNombreDePages() {
        return nombreDePages;
    }

    public void setNombreDePages(int nombreDePages) {
        this.nombreDePages = nombreDePages;
    }

    public UUID getEmpruntePar() {
        return empruntePar;
    }

    public void setEmpruntePar(UUID empruntePar) {
        this.empruntePar = empruntePar;
    }

    public UUID getReservePar() {
        return reservePar;
    }

    public void setReservePar(UUID reservePar) {
        this.reservePar = reservePar;
    }

    // Method to display details
    public void afficherDetails() {
        System.out.println("ID: " + id);
        System.out.println("Titre: " + titre);
        System.out.println("Auteur: " + auteur);
        System.out.println("Date de Publication: " + datePublication);
        System.out.println("Nombre de Pages: " + nombreDePages);
        System.out.println("Type: " + getType());
        System.out.println("Emprunté par: " + (empruntePar != null ? empruntePar : "Non emprunté"));
        System.out.println("Réservé par: " + (reservePar != null ? reservePar : "Non réservé"));
    }
}
