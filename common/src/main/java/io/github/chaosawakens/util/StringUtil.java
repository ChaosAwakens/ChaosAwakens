package io.github.chaosawakens.util;

import java.util.List;

public final class StringUtil {
    public static final String[] MULTICHAR_FOSSIL_SUFFIXES = new String[]{
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

    public static String formatRoboBlockName(String localisedRoboBlockName) {
        return com.mememan.nexus.util.StringUtil.lastToken(localisedRoboBlockName.replaceAll("\\s+", "_")).length() != 1 ? localisedRoboBlockName : wrapSuffixInParentheses(localisedRoboBlockName);
    }

    public static String formatBlockSoundEventSubtitle(String blockSoundSubtitleKey) {
        if (!blockSoundSubtitleKey.contains("subtitles.")) return blockSoundSubtitleKey;

        String blockSoundId = blockSoundSubtitleKey.substring(blockSoundSubtitleKey.lastIndexOf(".") + 1);
        String literallyLocalizedBlockSoundId = com.mememan.nexus.util.StringUtil.literallyLocalize(blockSoundSubtitleKey, List.of());

        if (blockSoundId.endsWith("_break")) return literallyLocalizedBlockSoundId.replace("Break", "broken");
        if (blockSoundId.endsWith("_step"))
            return "Footsteps (%s)".formatted(literallyLocalizedBlockSoundId.replace(" Step", ""));
        if (blockSoundId.endsWith("_fall"))
            return "Falling (%s)".formatted(literallyLocalizedBlockSoundId.replace(" Fall", ""));
        if (blockSoundId.endsWith("_place")) return literallyLocalizedBlockSoundId.replace("Place", "placed");
        if (blockSoundId.endsWith("_hit")) return literallyLocalizedBlockSoundId.replace("Hit", "breaking");

        return literallyLocalizedBlockSoundId.concat("s");
    }

    public static String formatEntitySoundEventSubtitle(String entitySoundEventSubtitleKey) {
        if (!entitySoundEventSubtitleKey.contains("subtitles.")) return entitySoundEventSubtitleKey;

        String entitySoundEventId = entitySoundEventSubtitleKey.substring(entitySoundEventSubtitleKey.lastIndexOf(".") + 1);
        String literallyLocalizedEntitySoundEventId = com.mememan.nexus.util.StringUtil.literallyLocalize(entitySoundEventSubtitleKey, List.of());

        if (entitySoundEventId.endsWith("_idle")) return literallyLocalizedEntitySoundEventId.replace("Idle", "idles");

        return literallyLocalizedEntitySoundEventId;
    }

    public static String formatSoundtrackSubtitle(String soundtrackSubtitleKey) {
        if (!soundtrackSubtitleKey.contains("subtitles.")) return soundtrackSubtitleKey;

        return com.mememan.nexus.util.StringUtil.literallyLocalize(soundtrackSubtitleKey, List.of()) + " Plays [Soundtrack]";
    }
}