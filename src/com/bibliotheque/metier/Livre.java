package com.bibliotheque.metier;

import java.time.LocalDate;

public class Livre extends Document {
     private String isbn;

     public Livre(int id, String titre, String auteur , LocalDate datePublication, int nombreDePages ,String isbn){
         super(id,titre,auteur,datePublication,nombreDePages);
         this.isbn = isbn;
     }

     @Override
    public void emprunter() {
           if(emprunte){
             System.out.println("Le livre " + titre + " a déja emprunté ");
           }else{
               emprunte=true ;
               System.out.println("Le livre " + titre + " a été emprunté avec succès ");

           }
    }

    @Override
    public  void retourner(){
         if(!emprunte){
             System.out.println("Le livre " + titre + " a n'a pas emprunté pour le retourner.");
         }else{
             emprunte=false;
             System.out.println("Le livre " + titre + " a été retourné avec succès.");

         }
    }

    @Override
    public void afficherDetails(){
        System.out.println("Livre [ISBN=" + isbn + ", titre=" + titre + ", auteur=" + auteur + "]");
    }

}