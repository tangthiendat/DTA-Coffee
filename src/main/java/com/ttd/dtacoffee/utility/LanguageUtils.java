package com.ttd.dtacoffee.utility;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class LanguageUtils {
    //Remove accent from Vietnamese text
    public static String normalize(String text){
        //remove diacritics
        text = text.replace('ă', 'a');
        text = text.replace('Ă', 'A');
        text = text.replace('â', 'a');
        text = text.replace('Â', 'A');
        text = text.replace('đ', 'd');
        text = text.replace('Đ', 'D');
        text = text.replace('ê', 'e');
        text = text.replace('Ê', 'e');
        text = text.replace('Ê', 'e');
        text = text.replace('ô', 'o');
        text = text.replace('Ô', 'O');
        text = text.replace('ơ', 'o');
        text = text.replace('Ơ', 'O');
        text = text.replace('ư', 'u');
        text = text.replace('Ư', 'U');

        //remove accent
        String nfdNormalizedString = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{M}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }


}
