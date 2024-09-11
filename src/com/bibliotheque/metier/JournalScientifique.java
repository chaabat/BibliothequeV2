package com.bibliotheque.metier;

import java.time.LocalDate;
import java.util.UUID;

public class JournalScientifique extends Document {
    // Constructor
    public JournalScientifique(UUID id, String titre, String auteur, LocalDate datePublication, int nombreDePages) {
        super(id, titre, auteur, datePublication, nombreDePages);
    }

    @Override
    public String getType() {
        return "JournalScientifique";
    }
}
