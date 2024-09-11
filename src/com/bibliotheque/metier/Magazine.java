package com.bibliotheque.metier;

import java.time.LocalDate;
import java.util.UUID;

public class Magazine extends Document {
    public Magazine(UUID idDocument, String titre, String auteur, LocalDate datePublication, int nombreDePages) {
        super(titre, auteur, datePublication, nombreDePages);
    }

    @Override
    public String getType() {
        return "Magazine";
    }
}
