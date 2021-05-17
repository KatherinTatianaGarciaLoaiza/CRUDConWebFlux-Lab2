package com.example.validateCard;

import java.util.HashMap;
import java.util.Map;

public class Validate {

    public static String validateCard(String code){
        String validateType = checkType().get(code);
        if (validateType == null) {
            throw new IllegalArgumentException("El codigo ingresado no es valido.");
        }
        return validateType;
    }

    public static Map<String, String> checkType(){
        Map<String, String> types = new HashMap<>();
        types.put("12", "PRIME");
        types.put("03", "MASTERCARD");
        types.put("06", "VISA");
        return types;
    }
}
