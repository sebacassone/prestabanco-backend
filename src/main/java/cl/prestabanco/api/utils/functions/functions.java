package cl.prestabanco.api.utils.functions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class functions {
    public static Long transformLong(Object value) {
        try {
            // Convert the userJob to Long
            Long numberLong = null;
            if (value instanceof Integer) {
                numberLong  = ((Integer) value).longValue();
            } else if (value instanceof Long) {
                numberLong  = (Long) value;
            } else if (value instanceof String) {
                numberLong = Long.parseLong((String) value);
            }
            return numberLong;
        } catch (Exception e) {
            return null;
        }
    }
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
}
