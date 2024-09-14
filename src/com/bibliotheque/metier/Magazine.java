package com.bibliotheque.metier;

import java.time.LocalDate;
import java.util.UUID;

public class Magazine extends Document {
    private int numero;

    // Constructor with numero
    public Magazine(UUID id, String titre, String auteur, LocalDate datePublication, int nombreDePages, int numero) {
        super(id, titre, auteur, datePublication, nombreDePages);
        this.numero = numero; // Correctly initialize numero
    }

    // Getter for numero
    public int getNumero() {
        return numero;
    }

    // Setter for numero
    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String getType() {
        return "Magazine";
    }
}
