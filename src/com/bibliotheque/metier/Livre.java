package com.bibliotheque.metier;

import java.time.LocalDate;
import java.util.UUID;

public class Livre extends Document {
    private String isbn;

    // Constructor without ISBN
    public Livre(UUID id, String titre, String auteur, LocalDate datePublication, int nombreDePages) {
        super(id, titre, auteur, datePublication, nombreDePages);
        this.isbn = null; // Default value if ISBN is not provided
    }

    // Constructor with ISBN
    public Livre(UUID id, String titre, String auteur, LocalDate datePublication, int nombreDePages, String isbn) {
        super(id, titre, auteur, datePublication, nombreDePages);
        this.isbn = isbn;
    }

    // Getter for ISBN
    public String getIsbn() {
        return isbn;
    }

    @Override
    public String getType() {
        return "Livre";
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
