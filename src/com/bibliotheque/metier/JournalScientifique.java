package com.bibliotheque.metier;

import java.time.LocalDate;
import java.util.UUID;

public class JournalScientifique extends Document {
    public JournalScientifique(UUID idDocument, String titre, String auteur, LocalDate datePublication, int nombreDePages) {
        super(titre, auteur, datePublication, nombreDePages);
    }

    @Override
    public String getType() {
        return "JournalScientifique";
    }
}
