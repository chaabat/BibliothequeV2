package com.bibliotheque.dao;

import com.bibliotheque.metier.Utilisateur;
import com.bibliotheque.metier.Etudiant;
import com.bibliotheque.metier.Professeur;
import com.bibliotheque.dao.Interface.UtilisateurDAOInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bibliotheque.dao.DatabaseConnection.connection;

public class UtilisateurDAO implements UtilisateurDAOInterface {

    public UtilisateurDAO() {

    }

    @Override
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        String sql = "INSERT INTO users (id, name, email, mot_de_passe, type_utilisateur, informations) VALUES (?, ?, ?, ?, ?, ?)";

        try (

               Connection  connection=DatabaseConnection.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, utilisateur.getId());
            pstmt.setString(2, utilisateur.getNom());
            pstmt.setString(3, utilisateur.getEmail());
            pstmt.setString(4, utilisateur.getMotDePasse());

            if (utilisateur instanceof Etudiant) {
                pstmt.setString(5, "Etudiant");
                pstmt.setString(6, ((Etudiant) utilisateur).getProgrammeEtudes());
            } else if (utilisateur instanceof Professeur) {
                pstmt.setString(5, "Professeur");
                pstmt.setString(6, ((Professeur) utilisateur).getDepartement());
            }

            pstmt.executeUpdate();
            System.out.println("Utilisateur ajouté avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String nom = rs.getString("name");
                String email = rs.getString("email");
                String motDePasse = rs.getString("mot_de_passe");
                String typeUtilisateur = rs.getString("type_utilisateur");
                String informations = rs.getString("informations");

                Utilisateur utilisateur = null;

                if (typeUtilisateur.equals("Etudiant")) {
                    utilisateur = new Etudiant(id, nom, email, motDePasse, informations);
                } else if (typeUtilisateur.equals("Professeur")) {
                    utilisateur = new Professeur(id, nom, email, motDePasse, informations);
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
        String sql = "UPDATE users SET name = ?, email = ?, mot_de_passe = ?, type_utilisateur = ?, informations = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getEmail());
            pstmt.setString(3, utilisateur.getMotDePasse());

            if (utilisateur instanceof Etudiant) {
                pstmt.setString(4, "Etudiant");
                pstmt.setString(5, ((Etudiant) utilisateur).getProgrammeEtudes());
            } else if (utilisateur instanceof Professeur) {
                pstmt.setString(4, "Professeur");
                pstmt.setString(5, ((Professeur) utilisateur).getDepartement());
            }

            pstmt.setObject(6, utilisateur.getId());

            pstmt.executeUpdate();
            System.out.println("Utilisateur mis à jour avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerUtilisateur(UUID id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            pstmt.executeUpdate();
            System.out.println("Utilisateur supprimé avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Utilisateur rechercherUtilisateurParId(UUID id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        Utilisateur utilisateur = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("name");
                String email = rs.getString("email");
                String motDePasse = rs.getString("mot_de_passe");
                String typeUtilisateur = rs.getString("type_utilisateur");
                String informations = rs.getString("informations");

                if (typeUtilisateur.equals("Etudiant")) {
                    utilisateur = new Etudiant(id, nom, email, motDePasse, informations);
                } else if (typeUtilisateur.equals("Professeur")) {
                    utilisateur = new Professeur(id, nom, email, motDePasse, informations);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }


}
