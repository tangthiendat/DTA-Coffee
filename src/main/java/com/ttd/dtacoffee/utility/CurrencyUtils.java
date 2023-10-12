package com.ttd.dtacoffee.utility;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtils {

    public static String format(long value){
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(value);
    }

    public static int getValue(String money){
        return Integer.parseInt(money.replace(",", ""));
    }

}
