package com.bibliotheque.dao;

import com.bibliotheque.metier.Utilisateur;
import com.bibliotheque.metier.Etudiant;
import com.bibliotheque.metier.Professeur;
import com.bibliotheque.dao.Interface.UtilisateurDAOInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UtilisateurDAO implements UtilisateurDAOInterface {

    private Connection connection;

    public UtilisateurDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
            this.connection.setAutoCommit(false); // Disable auto-commit for manual transaction management
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        String sqlUtilisateur = "INSERT INTO utilisateurs (id, nom, email) VALUES (?, ?, ?)";
        String sqlEtudiant = "INSERT INTO etudiants (id, programmeEtude) VALUES (?, ?)";
        String sqlProfesseur = "INSERT INTO professeurs (id, departement) VALUES (?, ?)";

        try (PreparedStatement pstmtUtilisateur = connection.prepareStatement(sqlUtilisateur)) {

            // Insertion dans la table utilisateurs (Parent table)
            pstmtUtilisateur.setObject(1, utilisateur.getId());
            pstmtUtilisateur.setString(2, utilisateur.getNom());
            pstmtUtilisateur.setString(3, utilisateur.getEmail());
            pstmtUtilisateur.executeUpdate();

            // If the user is an instance of Etudiant, insert into etudiants table
            if (utilisateur instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) utilisateur; // Cast utilisateur to Etudiant to access its fields
                try (PreparedStatement pstmtEtudiant = connection.prepareStatement(sqlEtudiant)) {
                    pstmtEtudiant.setObject(1, etudiant.getId());
                    pstmtEtudiant.setString(2, etudiant.getProgrammeEtudes()); // Ensure the correct field is set
                    pstmtEtudiant.executeUpdate();
                }
            }
            // If the user is an instance of Professeur, insert into professeurs table
            else if (utilisateur instanceof Professeur) {
                Professeur professeur = (Professeur) utilisateur; // Cast utilisateur to Professeur to access its fields
                try (PreparedStatement pstmtProfesseur = connection.prepareStatement(sqlProfesseur)) {
                    pstmtProfesseur.setObject(1, professeur.getId());
                    pstmtProfesseur.setString(2, professeur.getDepartement()); // Ensure the correct field is set
                    pstmtProfesseur.executeUpdate();
                }
            }

            // Commit the transaction if everything goes well
            connection.commit();
            System.out.println("Utilisateur ajouté avec succès.");

        } catch (SQLException e) {
            // Rollback the transaction in case of any error
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback changes in case of error
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
    }


    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM utilisateurs";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String nom = rs.getString("nom");
                String email = rs.getString("email");

                Utilisateur utilisateur = null;

                // Check if the user is a student
                String sqlEtudiant = "SELECT programmeEtude FROM etudiants WHERE id = ?";
                try (PreparedStatement pstmtEtudiant = connection.prepareStatement(sqlEtudiant)) {
                    pstmtEtudiant.setObject(1, id);
                    ResultSet rsEtudiant = pstmtEtudiant.executeQuery();
                    if (rsEtudiant.next()) {
                        String programmeEtudes = rsEtudiant.getString("programmeEtude");
                        utilisateur = new Etudiant(id, nom, email, programmeEtudes);
                    }
                }

                // If not a student, check if they are a professor
                if (utilisateur == null) {
                    String sqlProfesseur = "SELECT departement FROM professeurs WHERE id = ?";
                    try (PreparedStatement pstmtProfesseur = connection.prepareStatement(sqlProfesseur)) {
                        pstmtProfesseur.setObject(1, id);
                        ResultSet rsProfesseur = pstmtProfesseur.executeQuery();
                        if (rsProfesseur.next()) {
                            String departement = rsProfesseur.getString("departement");
                            utilisateur = new Professeur(id, nom, email, departement);
                        }
                    }
                }

                if (utilisateur != null) {
                    utilisateurs.add(utilisateur);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateurs;
    }

    @Override
    public void mettreAJourUtilisateur(Utilisateur utilisateur) {
        String sqlUtilisateur = "UPDATE utilisateurs SET nom = ?, email = ? WHERE id = ?";
        String sqlEtudiant = "UPDATE etudiants SET programmeEtude = ? WHERE id = ?";
        String sqlProfesseur = "UPDATE professeurs SET departement = ? WHERE id = ?";

        try (PreparedStatement pstmtUtilisateur = connection.prepareStatement(sqlUtilisateur)) {
            pstmtUtilisateur.setString(1, utilisateur.getNom());
            pstmtUtilisateur.setString(2, utilisateur.getEmail());
            pstmtUtilisateur.setObject(3, utilisateur.getId());
            pstmtUtilisateur.executeUpdate();

            if (utilisateur instanceof Etudiant) {
                try (PreparedStatement pstmtEtudiant = connection.prepareStatement(sqlEtudiant)) {
                    pstmtEtudiant.setString(1, ((Etudiant) utilisateur).getProgrammeEtudes());
                    pstmtEtudiant.setObject(2, utilisateur.getId());
                    pstmtEtudiant.executeUpdate();
                }
            } else if (utilisateur instanceof Professeur) {
                try (PreparedStatement pstmtProfesseur = connection.prepareStatement(sqlProfesseur)) {
                    pstmtProfesseur.setString(1, ((Professeur) utilisateur).getDepartement());
                    pstmtProfesseur.setObject(2, utilisateur.getId());
                    pstmtProfesseur.executeUpdate();
                }
            }

            connection.commit();
            System.out.println("Utilisateur mis à jour avec succès.");
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback changes in case of error
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerUtilisateur(UUID id) {
        String sqlUtilisateur = "DELETE FROM utilisateurs WHERE id = ?";
        String sqlEtudiant = "DELETE FROM etudiants WHERE id = ?";
        String sqlProfesseur = "DELETE FROM professeurs WHERE id = ?";

        try (PreparedStatement pstmtUtilisateur = connection.prepareStatement(sqlUtilisateur)) {
            pstmtUtilisateur.setObject(1, id);
            pstmtUtilisateur.executeUpdate();

            try (PreparedStatement pstmtEtudiant = connection.prepareStatement(sqlEtudiant)) {
                pstmtEtudiant.setObject(1, id);
                pstmtEtudiant.executeUpdate();
            }

            try (PreparedStatement pstmtProfesseur = connection.prepareStatement(sqlProfesseur)) {
                pstmtProfesseur.setObject(1, id);
                pstmtProfesseur.executeUpdate();
            }

            connection.commit();
            System.out.println("Utilisateur supprimé avec succès.");
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback changes in case of error
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Utilisateur rechercherUtilisateurParId(UUID id) {
        String sqlUtilisateur = "SELECT * FROM utilisateurs WHERE id = ?";
        Utilisateur utilisateur = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sqlUtilisateur)) {
            pstmt.setObject(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String email = rs.getString("email");

                // Check if the user is a student
                String sqlEtudiant = "SELECT programmeEtude FROM etudiants WHERE id = ?";
                try (PreparedStatement pstmtEtudiant = connection.prepareStatement(sqlEtudiant)) {
                    pstmtEtudiant.setObject(1, id);
                    ResultSet rsEtudiant = pstmtEtudiant.executeQuery();
                    if (rsEtudiant.next()) {
                        String programmeEtudes = rsEtudiant.getString("programmeEtude");
                        utilisateur = new Etudiant(id, nom, email, programmeEtudes);
                    }
                }

                // If not a student, check if they are a professor
                if (utilisateur == null) {
                    String sqlProfesseur = "SELECT departement FROM professeurs WHERE id = ?";
                    try (PreparedStatement pstmtProfesseur = connection.prepareStatement(sqlProfesseur)) {
                        pstmtProfesseur.setObject(1, id);
                        ResultSet rsProfesseur = pstmtProfesseur.executeQuery();
                        if (rsProfesseur.next()) {
                            String departement = rsProfesseur.getString("departement");
                            utilisateur = new Professeur(id, nom, email, departement);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }

    @Override
    public Utilisateur trouverUtilisateurParNom(String nom) {
        String sqlUtilisateur = "SELECT * FROM utilisateurs WHERE nom = ?";
        Utilisateur utilisateur = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sqlUtilisateur)) {
            pstmt.setString(1, nom);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String email = rs.getString("email");

                // Check if the user is a student
                String sqlEtudiant = "SELECT programmeEtude FROM etudiants WHERE id = ?";
                try (PreparedStatement pstmtEtudiant = connection.prepareStatement(sqlEtudiant)) {
                    pstmtEtudiant.setObject(1, id);
                    ResultSet rsEtudiant = pstmtEtudiant.executeQuery();
                    if (rsEtudiant.next()) {
                        String programmeEtudes = rsEtudiant.getString("programmeEtude");
                        utilisateur = new Etudiant(id, nom, email, programmeEtudes);
                    }
                }

                // If not a student, check if they are a professor
                if (utilisateur == null) {
                    String sqlProfesseur = "SELECT departement FROM professeurs WHERE id = ?";
                    try (PreparedStatement pstmtProfesseur = connection.prepareStatement(sqlProfesseur)) {
                        pstmtProfesseur.setObject(1, id);
                        ResultSet rsProfesseur = pstmtProfesseur.executeQuery();
                        if (rsProfesseur.next()) {
                            String departement = rsProfesseur.getString("departement");
                            utilisateur = new Professeur(id, nom, email, departement);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }
}
