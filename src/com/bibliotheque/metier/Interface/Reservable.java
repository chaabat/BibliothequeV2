package com.bibliotheque.metier.Interface;

import com.bibliotheque.metier.Utilisateur;

public interface Reservable {
    void reserver(Utilisateur utilisateur); // Update to accept Utilisateur
    boolean estReserve();
    void annulerReservation();
}
