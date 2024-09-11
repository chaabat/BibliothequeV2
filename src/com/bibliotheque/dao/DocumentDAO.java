package com.bibliotheque.dao;

import com.bibliotheque.metier.Document;
import com.bibliotheque.metier.Livre;
import com.bibliotheque.metier.Magazine;
import com.bibliotheque.metier.JournalScientifique;
import com.bibliotheque.metier.TheseUniversitaire;
import com.bibliotheque.dao.Interface.DocumentDAOInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DocumentDAO implements DocumentDAOInterface {

    private Connection connection;

    public DocumentDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }
    }

    @Override
    public void ajouterDocument(Document document) {
        String sql = "INSERT INTO documents (id, title, author, releaseDate, pages, type, borrowedBy, reservedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, document.getId());
            pstmt.setString(2, document.getTitre());
            pstmt.setString(3, document.getAuteur());
            pstmt.setDate(4, Date.valueOf(document.getDatePublication()));
            pstmt.setInt(5, document.getNombreDePages());
            pstmt.setString(6, document.getClass().getSimpleName()); // Document type
            pstmt.setObject(7, document.getEmpruntePar() != null ? document.getEmpruntePar() : null);
            pstmt.setObject(8, document.getReservePar() != null ? document.getReservePar() : null);

            pstmt.executeUpdate();
            System.out.println("Document ajouté avec succès.");
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }
    }

    @Override
    public List<Document> getAllDocuments() {
        List<Document> documents = new ArrayList<>();
        String sql = "SELECT * FROM documents";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                Date releaseDate = rs.getDate("releaseDate");
                int pages = rs.getInt("pages");
                UUID borrowedBy = rs.getObject("borrowedBy") != null ? (UUID) rs.getObject("borrowedBy") : null;
                UUID reservedBy = rs.getObject("reservedBy") != null ? (UUID) rs.getObject("reservedBy") : null;
                String type = rs.getString("type");

                Document document = null;

                switch (type) {
                    case "Livre":
                        document = new Livre(id, title, author, releaseDate.toLocalDate(), pages);
                        break;
                    case "Magazine":
                        document = new Magazine(id, title, author, releaseDate.toLocalDate(), pages);
                        break;
                    case "JournalScientifique":
                        document = new JournalScientifique(id, title, author, releaseDate.toLocalDate(), pages);
                        break;
                    case "TheseUniversitaire":
                        document = new TheseUniversitaire(id, title, author, releaseDate.toLocalDate(), pages);
                        break;
                }

                if (document != null) {
                    document.setEmpruntePar(borrowedBy);
                    document.setReservePar(reservedBy);
                    documents.add(document);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }

        return documents;
    }

    @Override
    public void mettreAJourDocument(Document document) {
        String sql = "UPDATE documents SET title = ?, author = ?, releaseDate = ?, pages = ?, borrowedBy = ?, reservedBy = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, document.getTitre());
            pstmt.setString(2, document.getAuteur());
            pstmt.setDate(3, Date.valueOf(document.getDatePublication()));
            pstmt.setInt(4, document.getNombreDePages());
            pstmt.setObject(5, document.getEmpruntePar() != null ? document.getEmpruntePar() : null);
            pstmt.setObject(6, document.getReservePar() != null ? document.getReservePar() : null);
            pstmt.setObject(7, document.getId());

            pstmt.executeUpdate();
            System.out.println("Document mis à jour avec succès.");
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }
    }

    @Override
    public void supprimerDocument(UUID id) {
        String sql = "DELETE FROM documents WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            pstmt.executeUpdate();
            System.out.println("Document supprimé avec succès.");
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }
    }

    @Override
    public Document rechercherDocumentParId(UUID id) {
        String sql = "SELECT * FROM documents WHERE id = ?";
        Document document = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                Date releaseDate = rs.getDate("releaseDate");
                int pages = rs.getInt("pages");
                UUID borrowedBy = rs.getObject("borrowedBy") != null ? (UUID) rs.getObject("borrowedBy") : null;
                UUID reservedBy = rs.getObject("reservedBy") != null ? (UUID) rs.getObject("reservedBy") : null;
                String type = rs.getString("type");

                switch (type) {
                    case "Livre":
                        document = new Livre(id, title, author, releaseDate.toLocalDate(), pages);
                        break;
                    case "Magazine":
                        document = new Magazine(id, title, author, releaseDate.toLocalDate(), pages);
                        break;
                    case "JournalScientifique":
                        document = new JournalScientifique(id, title, author, releaseDate.toLocalDate(), pages);
                        break;
                    case "TheseUniversitaire":
                        document = new TheseUniversitaire(id, title, author, releaseDate.toLocalDate(), pages);
                        break;
                }

                if (document != null) {
                    document.setEmpruntePar(borrowedBy);
                    document.setReservePar(reservedBy);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }

        return document;
    }
}
