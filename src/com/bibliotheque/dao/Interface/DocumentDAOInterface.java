package com.bibliotheque.dao.Interface;

import com.bibliotheque.metier.Document;
import java.util.List;
import java.util.UUID;

public interface DocumentDAOInterface {
    void ajouterDocument(Document document);
    List<Document> getAllDocuments();
    void mettreAJourDocument(Document document);
    boolean supprimerDocument(UUID id);
    Document rechercherDocumentParId(UUID id);

    Document rechercherDocumentParTitreEtType(String titreDocument, int typeDocument);

    boolean supprimerDocumentParTitreEtType(String titreDocument, int typeDocument);
    List<Document> rechercherDocumentParTitre(String titre);



}
