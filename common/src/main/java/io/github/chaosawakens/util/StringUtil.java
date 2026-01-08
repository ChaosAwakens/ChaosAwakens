package io.github.chaosawakens.util;

public final class StringUtil {
    public static final String[] MULTICHAR_FOSSIL_SUFFIXES = new String[] {
            "End Stone",
            "Packed Ice",
            "Soul Soil"
    };

    private StringUtil() {
        throw new IllegalAccessError("Attempted to construct instance of utility class! (StringUtil)");
    }

    public static String wrapSuffixInParentheses(String targetString, String targetSuffix) {
        int lastSpaceIndex = targetSuffix != null && !targetSuffix.isBlank() ? targetString.indexOf(targetSuffix) - 1 : targetString.lastIndexOf(" ");
        return lastSpaceIndex == -1 ? targetString : targetString.substring(0, lastSpaceIndex) + " (" + targetString.substring(lastSpaceIndex + 1) + ")";
    }

    public static String wrapSuffixInParentheses(String targetString) {
        return wrapSuffixInParentheses(targetString, null);
    }

    public static String formatFossilName(String localisedFossilName) {
        for (String curSuffix : MULTICHAR_FOSSIL_SUFFIXES) {
            if (localisedFossilName.endsWith(curSuffix)) return wrapSuffixInParentheses(localisedFossilName, curSuffix);
        }

        return wrapSuffixInParentheses(localisedFossilName);
    }
}