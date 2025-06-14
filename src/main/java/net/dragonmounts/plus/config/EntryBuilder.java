package net.dragonmounts.plus.config;

public class EntryBuilder {
    public static String translate(String key) {
        return "options.dragonmounts.plus." + key;
    }

    public static String tooltip(String key) {
        return "options.dragonmounts.plus." + key + ".tooltip";
    }

    public static BooleanEntry config(ConfigHolder holder, String key, boolean fallback) {
        return config(holder, key, translate(key), fallback);
    }

    public static BooleanEntry config(ConfigHolder holder, String key, String name, boolean fallback) {
        return new BooleanEntry(holder, key, name, tooltip(key), fallback);
    }

    public static DoubleEntry config(ConfigHolder holder, String key, double fallback) {
        return config(holder, key, fallback, DoubleEntry.MIN_DOUBLE, Double.MAX_VALUE);
    }

    public static DoubleEntry config(ConfigHolder holder, String key, double fallback, double min, double max) {
        return new DoubleEntry(holder, key, translate(key), tooltip(key), fallback, min, max);
    }
}
