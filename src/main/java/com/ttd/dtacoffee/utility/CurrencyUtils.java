package com.ttd.dtacoffee.utility;

import java.text.DecimalFormat;

public class CurrencyUtils {

    public static String format(long value){
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(value).replace(",", ".");
    }

    public static int getValue(String money){
        return Integer.parseInt(money.replace(".", ""));
    }

}
