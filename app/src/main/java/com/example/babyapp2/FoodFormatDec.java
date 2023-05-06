package com.example.babyapp2;

import java.text.DecimalFormat;

public class FoodFormatDec {

    public static String formatNumber(int value){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formatted = formatter.format(value);

        return formatted;
    }
}
