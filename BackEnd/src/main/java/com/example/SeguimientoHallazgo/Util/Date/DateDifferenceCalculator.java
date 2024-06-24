package com.example.SeguimientoHallazgo.Util.Date;

import java.util.Date;

public class DateDifferenceCalculator {

    public  DateDifference calculateDifference(Date pStartDate, Date pEndDate) {
        // Calcular la diferencia en milisegundos
        long differenceInMillis = pEndDate.getTime() - pStartDate.getTime();

        // Calcular el número de días
        long days = differenceInMillis / (1000 * 60 * 60 * 24);
        // Calcular el número de horas restantes
        long hours = (differenceInMillis % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        // Calcular el número de minutos restantes
        long minutes = (differenceInMillis % (1000 * 60 * 60)) / (1000 * 60);

        // Crear y devolver el objeto DateDifference
        return new DateDifference(days, hours, minutes);
    }

}
