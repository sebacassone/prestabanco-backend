package cl.prestabanco.api.utils.functions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class functions {
    public static Date transformStringtoDate(String value) {
        try {
            // Convert the date string to a Date object
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dateValue;
            dateValue = formatter.parse(value);
            return dateValue;

        } catch (Exception e) {
            return null;
        }
    }
    public static Float transformIntegertoFloat(Object value){
        // Convertir amountIncome a Float
        Float numberFloat;
        if (value instanceof Integer) {
            numberFloat = ((Integer) value).floatValue();
        } else if (value instanceof Float) {
            numberFloat = (Float) value;
        } else {
            numberFloat = Float.parseFloat(value.toString());
        }
        return numberFloat;
    }

    public static Double transformIntegertoDouble(Object value){
        // Convertir amountIncome a Float
        Double numberFloat;
        if (value instanceof Integer) {
            numberFloat = ((Integer) value).doubleValue();
        } else if (value instanceof Float) {
            numberFloat = (Double) value;
        } else {
            numberFloat = Double.parseDouble(value.toString());
        }
        return numberFloat;
    }
}
