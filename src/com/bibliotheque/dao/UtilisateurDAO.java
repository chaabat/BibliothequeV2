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
        String sqlEtudiant = "INSERT INTO etudiants (id, programmeEtude, limiteEmprunt) VALUES (?, ?, ?)";
        String sqlProfesseur = "INSERT INTO professeurs (id, departement) VALUES (?, ?)";

        try {
            // Insert into the utilisateurs table
            try (PreparedStatement pstmtUtilisateur = connection.prepareStatement(sqlUtilisateur)) {
                pstmtUtilisateur.setObject(1, utilisateur.getId());
                pstmtUtilisateur.setString(2, utilisateur.getNom());
                pstmtUtilisateur.setString(3, utilisateur.getEmail());
                pstmtUtilisateur.executeUpdate();
            }

            // Insert into the child table based on user type
            if (utilisateur instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) utilisateur;
                try (PreparedStatement pstmtEtudiant = connection.prepareStatement(sqlEtudiant)) {
                    pstmtEtudiant.setObject(1, etudiant.getId());
                    pstmtEtudiant.setString(2, etudiant.getProgrammeEtudes());
                    pstmtEtudiant.setInt(3, etudiant.getLimiteEmprunt());
                    pstmtEtudiant.executeUpdate();
                }
            } else if (utilisateur instanceof Professeur) {
                Professeur professeur = (Professeur) utilisateur;
                try (PreparedStatement pstmtProfesseur = connection.prepareStatement(sqlProfesseur)) {
                    pstmtProfesseur.setObject(1, professeur.getId());
                    pstmtProfesseur.setString(2, professeur.getDepartement());
                    pstmtProfesseur.executeUpdate();
                }
            }

            // Commit transaction
            connection.commit();
            System.out.println("Utilisateur ajouté avec succès.");
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
    }





    private Utilisateur createUtilisateurFromResultSet(ResultSet rs) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));
        String nom = rs.getString("nom");
        String email = rs.getString("email");
        String programmeEtude = rs.getString("programmeEtude");
        String departement = rs.getString("departement");

        if (programmeEtude != null) {
            // This means the user is a student
            return new Etudiant(id, nom, email, programmeEtude);
        } else if (departement != null) {
            // This means the user is a professor
            return new Professeur(id, nom, email, departement);
        }
        return null;
    }

    @Override
    public Utilisateur rechercherUtilisateurParId(UUID utilisateurId) {
        String sql = "SELECT u.*, e.programmeEtude, p.departement FROM utilisateurs u " +
                "LEFT JOIN etudiants e ON u.id = e.id " +
                "LEFT JOIN professeurs p ON u.id = p.id " +
                "WHERE u.id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, utilisateurId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return createUtilisateurFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT u.*, e.programmeEtude, p.departement FROM utilisateurs u " +
                "LEFT JOIN etudiants e ON u.id = e.id " +
                "LEFT JOIN professeurs p ON u.id = p.id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Utilisateur utilisateur = createUtilisateurFromResultSet(rs);
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
        String sqlEtudiant = "UPDATE etudiants SET programmeEtude = ?, limiteEmprunt = ? WHERE id = ?";
        String sqlProfesseur = "UPDATE professeurs SET departement = ? WHERE id = ?";

        try {
            // Update the utilisateur table
            try (PreparedStatement pstmtUtilisateur = connection.prepareStatement(sqlUtilisateur)) {
                pstmtUtilisateur.setString(1, utilisateur.getNom());
                pstmtUtilisateur.setString(2, utilisateur.getEmail());
                pstmtUtilisateur.setObject(3, utilisateur.getId());
                pstmtUtilisateur.executeUpdate();
            }

            // Update the child table based on user type
            if (utilisateur instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) utilisateur;
                try (PreparedStatement pstmtEtudiant = connection.prepareStatement(sqlEtudiant)) {
                    pstmtEtudiant.setString(1, etudiant.getProgrammeEtudes());
                    pstmtEtudiant.setInt(2, etudiant.getLimiteEmprunt()); // Update limiteEmprunt
                    pstmtEtudiant.setObject(3, etudiant.getId());
                    pstmtEtudiant.executeUpdate();
                }
            } else if (utilisateur instanceof Professeur) {
                Professeur professeur = (Professeur) utilisateur;
                try (PreparedStatement pstmtProfesseur = connection.prepareStatement(sqlProfesseur)) {
                    pstmtProfesseur.setString(1, professeur.getDepartement());
                    pstmtProfesseur.setObject(2, professeur.getId());
                    pstmtProfesseur.executeUpdate();
                }
            }

            // Commit transaction
            connection.commit();
            System.out.println("Utilisateur mis à jour avec succès.");
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
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

        try {
            // Delete from the utilisateurs table
            try (PreparedStatement pstmtUtilisateur = connection.prepareStatement(sqlUtilisateur)) {
                pstmtUtilisateur.setObject(1, id);
                pstmtUtilisateur.executeUpdate();
            }

            // Delete from the child tables
            try (PreparedStatement pstmtEtudiant = connection.prepareStatement(sqlEtudiant)) {
                pstmtEtudiant.setObject(1, id);
                pstmtEtudiant.executeUpdate();
            }

            try (PreparedStatement pstmtProfesseur = connection.prepareStatement(sqlProfesseur)) {
                pstmtProfesseur.setObject(1, id);
                pstmtProfesseur.executeUpdate();
            }

            // Commit transaction
            connection.commit();
            System.out.println("Utilisateur supprimé avec succès.");
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Utilisateur trouverUtilisateurParNom(String nom) {
        String sql = "SELECT u.id, u.nom, u.email, e.programmeEtude, p.departement " +
                "FROM utilisateurs u " +
                "LEFT JOIN etudiants e ON u.id = e.id " +
                "LEFT JOIN professeurs p ON u.id = p.id " +
                "WHERE u.nom = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Debugging output: to verify data is fetched correctly
                System.out.println("ID: " + rs.getString("id"));
                System.out.println("Nom: " + rs.getString("nom"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Programme Etude: " + rs.getString("programmeEtude")); // May be null
                System.out.println("Departement: " + rs.getString("departement")); // May be null

                // Create utilisateur object from result set
                return createUtilisateurFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public int countDocumentsEmpruntes(UUID utilisateurId) {
        String sql = "SELECT COUNT(*) AS count FROM documents WHERE empruntePar = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, utilisateurId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
