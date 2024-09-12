package com.bibliotheque.metier.Interface;

import com.bibliotheque.metier.Utilisateur;

public interface Empruntable {
    void emprunter(Utilisateur utilisateur); // Update to accept Utilisateur
    boolean estDisponible();
    void retourner();
}
