package com.bibliotheque.metier;

import java.time.LocalDate;
import java.util.UUID;

public class Magazine extends Document {
    // Constructor
    public Magazine(UUID id, String titre, String auteur, LocalDate datePublication, int nombreDePages) {
        super(id, titre, auteur, datePublication, nombreDePages);
    }

    @Override
    public String getType() {
        return "Magazine";
    }
}
