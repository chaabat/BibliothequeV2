package com.bibliotheque.utilitaire;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    public static boolean estDateValide(LocalDate date){
        // Vous pouvez ajouter des validations spécifiques si nécessaire
        return date != null;
    }

    public static LocalDate obtenirDateActuelle(){
        return LocalDate.now();
    }

    public static long calculerJoursEntre(LocalDate date1, LocalDate date2){
        return ChronoUnit.DAYS.between(date1, date2);
    }
}
