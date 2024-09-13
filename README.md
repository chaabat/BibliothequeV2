
# Gestion de Bibliothèque V2

## Vue d'ensemble

Ce projet est une application de gestion de bibliothèque municipale développée en Java. L'application permet de gérer l'inventaire des livres et des magazines, ainsi que les opérations d'emprunt et de retour. Elle suit une architecture en couches (présentation, logique métier, persistance) et utilise PostgreSQL pour la persistance des données. Le projet intègre également des énumérations (enum) pour la gestion des types de documents (livres, magazines, journaux scientifiques, thèses universitaires).

## Fonctionnalités

### 1. Gestion des Documents
- **Ajouter un Document** : Permet d'ajouter de nouveaux documents (livres, magazines, etc.) à la bibliothèque.
- **Emprunter un Document** : Permet aux utilisateurs d'emprunter des documents pour une période définie.
- **Retourner un Document** : Permet aux utilisateurs de retourner des documents empruntés pour les rendre disponibles à d'autres.
- **Afficher tous les Documents** : Affiche une liste de tous les documents disponibles dans la bibliothèque.
- **Rechercher un Document** : Permet de rechercher des documents par titre ou par auteur.

### 2. Gestion des Utilisateurs
- **Ajouter un Utilisateur** : Ajoute un nouvel utilisateur au système, permettant l'emprunt de documents.
- **Modifier un Utilisateur** : Permet de mettre à jour les informations d'un utilisateur existant.
- **Supprimer un Utilisateur** : Supprime un utilisateur du système.
- **Afficher tous les Utilisateurs** : Affiche une liste de tous les utilisateurs enregistrés.
- **Gérer les Droits d'Emprunt** : Permet de définir et de gérer les droits d'emprunt pour chaque utilisateur.

### 3. Gestion des Emprunts
- **Emprunter un Document** : Gestion des opérations d'emprunt de documents par les utilisateurs.
- **Retourner un Document** : Gestion des opérations de retour des documents empruntés.

### 4. Gestion des Réservations
- **Réserver un Document** : Permet aux utilisateurs de réserver des documents qui sont actuellement empruntés par d'autres.
- **Annuler une Réservation** : Permet d'annuler des réservations effectuées précédemment.

### 5. Interface Utilisateur
- **Menu Interactif** : Un menu interactif basé sur la console pour permettre aux utilisateurs de naviguer entre les différentes fonctionnalités de l'application.
- **Validation des Entrées** : Validation des données saisies par les utilisateurs pour garantir l'intégrité des informations.

### 6. Persistante des Données
- **PostgreSQL** : Utilisation de PostgreSQL pour la persistance des données, assurant la sauvegarde et la récupération des informations.
- **DAO (Data Access Object)** : Implémentation des DAO pour gérer l'accès aux données dans la base de données.

## Technologies Utilisées
- **Java 8**
- **PostgreSQL** : Pour la persistance des données.
- **Git** : Pour le contrôle de version.
- **JIRA** : Pour la gestion des tâches et des sprints.

## Prérequis
- Java 8 ou version ultérieure
- PostgreSQL installé et configuré
- Git pour la gestion du code source

## Installation et Configuration

1. **Cloner le dépôt :**
   ```bash
   git clone https://github.com/JavaAura/Chaabat_Ayoub_S1_B2_gestionBiblioV2.git
   cd gestionBiblioV2
## Configuration de la base de données PostgreSQL

1. **Créer une base de données pour l'application :**
    - Utilisez PostgreSQL pour créer une nouvelle base de données pour le projet.

2. **Mettre à jour les paramètres de connexion :**
    - Modifiez les paramètres de connexion à la base de données dans le fichier `application.properties` selon les configurations locales.

## Exécuter l'application :

```bash
java -jar target/gestionBiblioV2.jar
```
## Utilisation

1. **Lancez l'application depuis la console :**
    - Exécutez la commande suivante pour lancer l'application :
      ```bash
      java -jar target/gestionBiblioV2.jar
      ```

2. **Navigation dans l'application :**
    - Suivez les instructions du menu interactif pour accéder aux différentes fonctionnalités, telles que :
        - **Ajout de documents**
        - **Emprunt de documents**
        - **Retour de documents**

3. **Recherche de documents :**
    - Utilisez les options de recherche pour trouver des documents spécifiques par titre ou par auteur.

4. **Gestion des utilisateurs :**
    - Les administrateurs peuvent gérer les utilisateurs enregistrés et leurs droits d'emprunt.

## Développeur Principal
- **Ayoub Chaabat**
