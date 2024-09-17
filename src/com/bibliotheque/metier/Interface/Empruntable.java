package com.bibliotheque.metier.Interface;

import com.bibliotheque.metier.Utilisateur;

import java.time.LocalDate;
import java.util.UUID;

public interface Empruntable {
    boolean estDisponible();
    void emprunter(Utilisateur utilisateur);
    void retourner();
    UUID getEmpruntePar();
    LocalDate getEmpruntDate();
}

