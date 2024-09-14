-- Table des Utilisateurs
CREATE TABLE utilisateurs (
    id UUID PRIMARY KEY,
    nom VARCHAR(50),
    email VARCHAR(150)
);

-- Table des Étudiants
CREATE TABLE etudiants (
    programmeEtude VARCHAR(100),
    limiteEmprunt INT DEFAULT 150
) INHERITS (utilisateurs);

-- Table des Professeurs
CREATE TABLE professeurs (
    departement VARCHAR(100)
) INHERITS (utilisateurs);

-- Table des Documents
CREATE TABLE documents (
    id UUID PRIMARY KEY,
    titre VARCHAR(250),
    auteur VARCHAR(100),
    datePublication DATE,
    nombrePages INT,
    empruntePar UUID,
    reservePar UUID,
    FOREIGN KEY (empruntePar) REFERENCES utilisateurs(id),
    FOREIGN KEY (reservePar) REFERENCES utilisateurs(id)
);

-- Table des Livres
CREATE TABLE livres (
    ISBN VARCHAR(13),
    PRIMARY KEY (id)  -- Utilisation de la clé primaire héritée
) INHERITS (documents);

-- Table des Magazines
CREATE TABLE magazines (
    numero INT,
    PRIMARY KEY (id)  -- Utilisation de la clé primaire héritée
) INHERITS (documents);

-- Table des Journaux Scientifiques
CREATE TABLE journauxScientifiques (
    domaineRecherche VARCHAR(100),
    editeur VARCHAR(50),
    PRIMARY KEY (id)  -- Utilisation de la clé primaire héritée
) INHERITS (documents);

-- Table des Thèses Universitaires
CREATE TABLE theseUniversitaires (
    universite VARCHAR(250),
    domaineEtude VARCHAR(100),
    anneeSoumission INT,
    PRIMARY KEY (id)  -- Utilisation de la clé primaire héritée
) INHERITS (documents);
