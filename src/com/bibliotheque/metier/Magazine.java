package com.bibliotheque.metier;
import java.time.LocalDate;

public class Magazine extends Document {
    private int numero;

    public Magazine(int id, String titre, String auteur, LocalDate datePublication, int nombreDePages, int numero) {
        super(id, titre, auteur, datePublication, nombreDePages);
        this.numero = numero;
    }

    @Override
    public void emprunter() {
        if(emprunte){
            System.out.println("Le magazine " + titre + " a déja emprunté.");

        }else {
            emprunte = true;
            System.out.println("Le magazine " + titre + " a été emprunté avec succès.");
        }
    }

    @Override
    public void retourner() {
        if(!emprunte){

            System.out.println("Le magazine " + titre + " a n'a pas emprunté pour le retourner.");
        }else {
            emprunte = false;
            System.out.println("Le magazine " + titre + " a été retourné avec succès.");

        }
    }

    @Override
    public void afficherDetails() {
        System.out.println("Magazine [Numéro=" + numero + ", titre=" + titre + ", auteur=" + auteur + "]");
    }
}
