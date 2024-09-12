package com.bibliotheque.dao.Interface;

import com.bibliotheque.metier.Utilisateur;

import java.util.List;
import java.util.UUID;

public interface UtilisateurDAOInterface {
    void ajouterUtilisateur(Utilisateur utilisateur);
    List<Utilisateur> getAllUtilisateurs();
    void mettreAJourUtilisateur(Utilisateur utilisateur);
    void supprimerUtilisateur(UUID id);
    Utilisateur rechercherUtilisateurParId(UUID id);

}
