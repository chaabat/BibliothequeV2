package com.bibliotheque.metier;

import java.time.LocalDate;
import java.util.UUID;

public class Livre extends Document {
    // Constructor
    public Livre(UUID id, String titre, String auteur, LocalDate datePublication, int nombreDePages) {
        super(id, titre, auteur, datePublication, nombreDePages);
    }


    @Override
    public String getType() {
        return "Livre";
    }
}
