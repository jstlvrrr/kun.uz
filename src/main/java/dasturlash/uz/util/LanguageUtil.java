package dasturlash.uz.util;

import java.util.Locale;

public final class LanguageUtil {

    private LanguageUtil() {
    }

    public static String pick(String lang, String nameUz, String nameRu, String nameEn) {
        if (lang == null) {
            return nameUz;
        }

        return switch (lang.toLowerCase(Locale.ROOT)) {
            case "ru" -> nameRu;
            case "en" -> nameEn;
            default -> nameUz;
        };
    }
}
