package com.bibliotheque.dao;

import com.bibliotheque.metier.*;
import com.bibliotheque.dao.Interface.DocumentDAOInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DocumentDAO implements DocumentDAOInterface {

    private static Connection connection;

    public DocumentDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
            this.connection.setAutoCommit(false); // Disable auto-commit for manual transactions
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger for better error handling
        }
    }

    @Override
    public void ajouterDocument(Document document) {
        String sql = null;
        PreparedStatement pstmt = null;

        try {
            if (document instanceof Livre) {
                sql = "INSERT INTO livres (id, titre, auteur, datePublication, nombrePages, ISBN, empruntePar, reservePar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                pstmt = connection.prepareStatement(sql);
                Livre livre = (Livre) document;
                pstmt.setObject(1, livre.getId());
                pstmt.setString(2, livre.getTitre());
                pstmt.setString(3, livre.getAuteur());
                pstmt.setDate(4, Date.valueOf(livre.getDatePublication()));
                pstmt.setInt(5, livre.getNombreDePages());
                pstmt.setString(6, livre.getIsbn());
                pstmt.setObject(7, livre.getEmpruntePar() != null ? livre.getEmpruntePar() : null);
                pstmt.setObject(8, livre.getReservePar() != null ? livre.getReservePar() : null);
            } else if (document instanceof Magazine) {
                sql = "INSERT INTO magazines (id, titre, auteur, datePublication, nombrePages, numero, empruntePar, reservePar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                pstmt = connection.prepareStatement(sql);
                Magazine magazine = (Magazine) document;
                pstmt.setObject(1, magazine.getId());
                pstmt.setString(2, magazine.getTitre());
                pstmt.setString(3, magazine.getAuteur());
                pstmt.setDate(4, Date.valueOf(magazine.getDatePublication()));
                pstmt.setInt(5, magazine.getNombreDePages());
                pstmt.setInt(6, magazine.getNumero());
                pstmt.setObject(7, magazine.getEmpruntePar() != null ? magazine.getEmpruntePar() : null);
                pstmt.setObject(8, magazine.getReservePar() != null ? magazine.getReservePar() : null);
            } else if (document instanceof JournalScientifique) {
                sql = "INSERT INTO journauxScientifiques (id, titre, auteur, datePublication, nombrePages, domaineRecherche, editeur, empruntePar, reservePar) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pstmt = connection.prepareStatement(sql);
                JournalScientifique journal = (JournalScientifique) document;
                pstmt.setObject(1, journal.getId());
                pstmt.setString(2, journal.getTitre());
                pstmt.setString(3, journal.getAuteur());
                pstmt.setDate(4, Date.valueOf(journal.getDatePublication()));
                pstmt.setInt(5, journal.getNombreDePages());
                pstmt.setString(6, journal.getDomaineRecherche());
                pstmt.setString(7, journal.getEditeur());
                pstmt.setObject(8, journal.getEmpruntePar() != null ? journal.getEmpruntePar() : null);
                pstmt.setObject(9, journal.getReservePar() != null ? journal.getReservePar() : null);
            } else if (document instanceof TheseUniversitaire) {
                sql = "INSERT INTO theseUniversitaires (id, titre, auteur, datePublication, nombrePages, universite, domaineEtude, anneeSoumission, empruntePar, reservePar) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pstmt = connection.prepareStatement(sql);
                TheseUniversitaire these = (TheseUniversitaire) document;
                pstmt.setObject(1, these.getId());
                pstmt.setString(2, these.getTitre());
                pstmt.setString(3, these.getAuteur());
                pstmt.setDate(4, Date.valueOf(these.getDatePublication()));
                pstmt.setInt(5, these.getNombreDePages());
                pstmt.setString(6, these.getUniversite());
                pstmt.setString(7, these.getDomaineEtude());
                pstmt.setInt(8, these.getAnneeSoumission());
                pstmt.setObject(9, these.getEmpruntePar() != null ? these.getEmpruntePar() : null);
                pstmt.setObject(10, these.getReservePar() != null ? these.getReservePar() : null);
            }

            if (pstmt != null) {
                pstmt.executeUpdate();
                connection.commit(); // Commit the transaction
                System.out.println("Document ajouté avec succès.");
            }
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback the transaction on error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace(); // Consider using a logger
            }
            e.printStackTrace(); // Consider using a logger
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Consider using a logger
            }
        }
    }

    @Override
    public List<Document> getAllDocuments() {
        List<Document> documents = new ArrayList<>();
        String sql = "SELECT * FROM documents LEFT JOIN livres ON documents.id = livres.id LEFT JOIN magazines ON documents.id = magazines.id LEFT JOIN journauxScientifiques ON documents.id = journauxScientifiques.id LEFT JOIN theseUniversitaires ON documents.id = theseUniversitaires.id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String titre = rs.getString("titre");
                String auteur = rs.getString("auteur");
                Date datePublication = rs.getDate("datePublication");
                int nombrePages = rs.getInt("nombrePages");
                UUID empruntePar = rs.getObject("empruntePar") != null ? (UUID) rs.getObject("empruntePar") : null;
                UUID reservePar = rs.getObject("reservePar") != null ? (UUID) rs.getObject("reservePar") : null;

                Document document = null;

                if (rs.getString("ISBN") != null) {
                    document = new Livre(id, titre, auteur, datePublication.toLocalDate(), nombrePages, rs.getString("ISBN"));
                } else if (rs.getInt("numero") != 0) {
                    document = new Magazine(id, titre, auteur, datePublication.toLocalDate(), nombrePages);
                } else if (rs.getString("domaineRecherche") != null) {
                    document = new JournalScientifique(id, titre, auteur, datePublication.toLocalDate(), nombrePages, rs.getString("domaineRecherche"), rs.getString("editeur"));
                } else if (rs.getString("universite") != null) {
                    document = new TheseUniversitaire(id, titre, auteur, datePublication.toLocalDate(), nombrePages, rs.getString("universite"), rs.getString("domaineEtude"), rs.getInt("anneeSoumission"));
                }

                if (document != null) {
                    document.setEmpruntePar(empruntePar);
                    document.setReservePar(reservePar);
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
        String sql = null;

        // Determine SQL query based on document type
        try {
            if (document instanceof Livre) {
                sql = "UPDATE livres SET titre = ?, auteur = ?, datePublication = ?, nombrePages = ?, ISBN = ?, empruntePar = ?, reservePar = ? WHERE id = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    Livre livre = (Livre) document;
                    pstmt.setString(1, livre.getTitre());
                    pstmt.setString(2, livre.getAuteur());
                    pstmt.setDate(3, Date.valueOf(livre.getDatePublication()));
                    pstmt.setInt(4, livre.getNombreDePages());
                    pstmt.setString(5, livre.getIsbn());
                    pstmt.setObject(6, livre.getEmpruntePar() != null ? livre.getEmpruntePar() : null);
                    pstmt.setObject(7, livre.getReservePar() != null ? livre.getReservePar() : null);
                    pstmt.setObject(8, livre.getId());
                    pstmt.executeUpdate();
                    connection.commit(); // Commit the transaction
                    System.out.println("Document mis à jour avec succès.");
                }
            } else if (document instanceof Magazine) {
                sql = "UPDATE magazines SET titre = ?, auteur = ?, datePublication = ?, nombrePages = ?, numero = ?, empruntePar = ?, reservePar = ? WHERE id = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    Magazine magazine = (Magazine) document;
                    pstmt.setString(1, magazine.getTitre());
                    pstmt.setString(2, magazine.getAuteur());
                    pstmt.setDate(3, Date.valueOf(magazine.getDatePublication()));
                    pstmt.setInt(4, magazine.getNombreDePages());
                    pstmt.setInt(5, magazine.getNumero());
                    pstmt.setObject(6, magazine.getEmpruntePar() != null ? magazine.getEmpruntePar() : null);
                    pstmt.setObject(7, magazine.getReservePar() != null ? magazine.getReservePar() : null);
                    pstmt.setObject(8, magazine.getId());
                    pstmt.executeUpdate();
                    connection.commit(); // Commit the transaction
                    System.out.println("Document mis à jour avec succès.");
                }
            } else if (document instanceof JournalScientifique) {
                sql = "UPDATE journauxScientifiques SET titre = ?, auteur = ?, datePublication = ?, nombrePages = ?, domaineRecherche = ?, editeur = ?, empruntePar = ?, reservePar = ? WHERE id = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    JournalScientifique journal = (JournalScientifique) document;
                    pstmt.setString(1, journal.getTitre());
                    pstmt.setString(2, journal.getAuteur());
                    pstmt.setDate(3, Date.valueOf(journal.getDatePublication()));
                    pstmt.setInt(4, journal.getNombreDePages());
                    pstmt.setString(5, journal.getDomaineRecherche());
                    pstmt.setString(6, journal.getEditeur());
                    pstmt.setObject(7, journal.getEmpruntePar() != null ? journal.getEmpruntePar() : null);
                    pstmt.setObject(8, journal.getReservePar() != null ? journal.getReservePar() : null);
                    pstmt.setObject(9, journal.getId());
                    pstmt.executeUpdate();
                    connection.commit(); // Commit the transaction
                    System.out.println("Document mis à jour avec succès.");
                }
            } else if (document instanceof TheseUniversitaire) {
                sql = "UPDATE theseUniversitaires SET titre = ?, auteur = ?, datePublication = ?, nombrePages = ?, universite = ?, domaineEtude = ?, anneeSoumission = ?, empruntePar = ?, reservePar = ? WHERE id = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    TheseUniversitaire these = (TheseUniversitaire) document;
                    pstmt.setString(1, these.getTitre());
                    pstmt.setString(2, these.getAuteur());
                    pstmt.setDate(3, Date.valueOf(these.getDatePublication()));
                    pstmt.setInt(4, these.getNombreDePages());
                    pstmt.setString(5, these.getUniversite());
                    pstmt.setString(6, these.getDomaineEtude());
                    pstmt.setInt(7, these.getAnneeSoumission());
                    pstmt.setObject(8, these.getEmpruntePar() != null ? these.getEmpruntePar() : null);
                    pstmt.setObject(9, these.getReservePar() != null ? these.getReservePar() : null);
                    pstmt.setObject(10, these.getId());
                    pstmt.executeUpdate();
                    connection.commit(); // Commit the transaction
                    System.out.println("Document mis à jour avec succès.");
                }
            }
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback the transaction on error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace(); // Consider using a logger
            }
            e.printStackTrace(); // Consider using a logger
        }
    }

    @Override
    public boolean supprimerDocument(UUID id) {
        String sql = "DELETE FROM documents WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            pstmt.executeUpdate();
            connection.commit(); // Commit the transaction
            System.out.println("Document supprimé avec succès.");
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback the transaction on error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace(); // Consider using a logger
            }
            e.printStackTrace(); // Consider using a logger
        }
        return false;
    }

    @Override
    public Document rechercherDocumentParId(UUID id) {
        String sql = "SELECT * FROM documents LEFT JOIN livres ON documents.id = livres.id LEFT JOIN magazines ON documents.id = magazines.id LEFT JOIN journauxScientifiques ON documents.id = journauxScientifiques.id LEFT JOIN theseUniversitaires ON documents.id = theseUniversitaires.id WHERE documents.id = ?";
        Document document = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String titre = rs.getString("titre");
                    String auteur = rs.getString("auteur");
                    Date datePublication = rs.getDate("datePublication");
                    int nombrePages = rs.getInt("nombrePages");
                    UUID empruntePar = rs.getObject("empruntePar") != null ? (UUID) rs.getObject("empruntePar") : null;
                    UUID reservePar = rs.getObject("reservePar") != null ? (UUID) rs.getObject("reservePar") : null;

                    if (rs.getString("ISBN") != null) {
                        document = new Livre(id, titre, auteur, datePublication.toLocalDate(), nombrePages, rs.getString("ISBN"));
                    } else if (rs.getInt("numero") != 0) {
                        document = new Magazine(id, titre, auteur, datePublication.toLocalDate(), nombrePages);
                    } else if (rs.getString("domaineRecherche") != null) {
                        document = new JournalScientifique(id, titre, auteur, datePublication.toLocalDate(), nombrePages, rs.getString("domaineRecherche"), rs.getString("editeur"));
                    } else if (rs.getString("universite") != null) {
                        document = new TheseUniversitaire(id, titre, auteur, datePublication.toLocalDate(), nombrePages, rs.getString("universite"), rs.getString("domaineEtude"), rs.getInt("anneeSoumission"));
                    }

                    if (document != null) {
                        document.setEmpruntePar(empruntePar);
                        document.setReservePar(reservePar);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }

        return document;
    }


    @Override
    public Document rechercherDocumentParTitreEtType(String titreDocument, int typeDocument) {
        String sql = null;
        Document document = null;

        // Determine SQL query based on document type
        switch (typeDocument) {
            case 1: // Livre
                sql = "SELECT * FROM livres WHERE titre = ?";
                break;
            case 2: // Magazine
                sql = "SELECT * FROM magazines WHERE titre = ?";
                break;
            case 3: // Journal Scientifique
                sql = "SELECT * FROM journauxScientifiques WHERE titre = ?";
                break;
            case 4: // Thèse Universitaire
                sql = "SELECT * FROM theseUniversitaires WHERE titre = ?";
                break;
            default:
                System.out.println("Type de document invalide.");
                return null;
        }

        // Execute the query and process the result
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, titreDocument);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    UUID id = (UUID) rs.getObject("id");
                    String auteur = rs.getString("auteur");
                    Date datePublication = rs.getDate("datePublication");
                    int nombrePages = rs.getInt("nombrePages");
                    UUID empruntePar = rs.getObject("empruntePar") != null ? (UUID) rs.getObject("empruntePar") : null;
                    UUID reservePar = rs.getObject("reservePar") != null ? (UUID) rs.getObject("reservePar") : null;

                    // Create the appropriate Document object based on the type
                    switch (typeDocument) {
                        case 1: // Livre
                            String isbn = rs.getString("ISBN");
                            document = new Livre(id, titreDocument, auteur, datePublication.toLocalDate(), nombrePages, isbn);
                            break;
                        case 2: // Magazine
                            int numero = rs.getInt("numero");
                            document = new Magazine(id, titreDocument, auteur, datePublication.toLocalDate(), nombrePages);
                            break;
                        case 3: // Journal Scientifique
                            String domaineRecherche = rs.getString("domaineRecherche");
                            String editeur = rs.getString("editeur");
                            document = new JournalScientifique(id, titreDocument, auteur, datePublication.toLocalDate(), nombrePages, domaineRecherche, editeur);
                            break;
                        case 4: // Thèse Universitaire
                            String universite = rs.getString("universite");
                            String domaineEtude = rs.getString("domaineEtude");
                            int anneeSoumission = rs.getInt("anneeSoumission");
                            document = new TheseUniversitaire(id, titreDocument, auteur, datePublication.toLocalDate(), nombrePages, universite, domaineEtude, anneeSoumission);
                            break;
                    }

                    // Set additional properties if needed
                    if (document != null) {
                        document.setEmpruntePar(empruntePar);
                        document.setReservePar(reservePar);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }

        return document;
    }





    @Override
    public boolean supprimerDocumentParTitreEtType(String titreDocument, int typeDocument) {
        String sql = null;
        boolean isDeleted = false;

        // Determine SQL query based on document type
        switch (typeDocument) {
            case 1: // Livre
                sql = "DELETE FROM livres WHERE titre = ?";
                break;
            case 2: // Magazine
                sql = "DELETE FROM magazines WHERE titre = ?";
                break;
            case 3: // Journal Scientifique
                sql = "DELETE FROM journauxScientifiques WHERE titre = ?";
                break;
            case 4: // Thèse Universitaire
                sql = "DELETE FROM theseUniversitaires WHERE titre = ?";
                break;
            default:
                System.out.println("Type de document invalide.");
                return false;
        }

        // Execute the query and handle the result
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, titreDocument);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                connection.commit(); // Commit the transaction if successful
                isDeleted = true;
                System.out.println("Document supprimé avec succès.");
            } else {
                // If no rows are affected, document was not found
                System.out.println("Aucun document trouvé avec ce titre.");
            }
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback the transaction on error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace(); // Consider using a logger
            }
            e.printStackTrace(); // Consider using a logger
        }

        return isDeleted;
    }



        // Emprunter un document
        public void emprunterDocument(UUID documentId, UUID utilisateurId) {
            String sql = "UPDATE documents SET empruntePar = ? WHERE id = ? AND empruntePar IS NULL";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setObject(1, utilisateurId);
                pstmt.setObject(2, documentId);

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    connection.commit();
                    System.out.println("Document emprunté avec succès.");
                } else {
                    System.out.println("Ce document est déjà emprunté ou n'existe pas.");
                }
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                e.printStackTrace();
            }
        }

        // Retourner un document
        public void retournerDocument(UUID documentId) {
            String sql = "UPDATE documents SET empruntePar = NULL WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setObject(1, documentId);

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    connection.commit();
                    System.out.println("Document retourné avec succès.");
                } else {
                    System.out.println("Ce document n'a pas été emprunté ou n'existe pas.");
                }
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                e.printStackTrace();
            }
        }





    @Override
    public List<Document> rechercherDocumentParTitre(String titre) {
      return null;


}
}


