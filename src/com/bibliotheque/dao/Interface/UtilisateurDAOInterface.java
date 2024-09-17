package com.bibliotheque.dao.Interface;

import com.bibliotheque.metier.Utilisateur;

import java.util.List;
import java.util.UUID;

public interface UtilisateurDAOInterface {
    void ajouterUtilisateur(Utilisateur utilisateur);
    Utilisateur rechercherUtilisateurParId(UUID utilisateurId);
    List<Utilisateur> getAllUtilisateurs();
    void mettreAJourUtilisateur(Utilisateur utilisateur);
    void supprimerUtilisateur(UUID id);
    Utilisateur trouverUtilisateurParNom(String nom);
    int countDocumentsEmpruntes(UUID utilisateurId); // Ensure this method is defined
}
