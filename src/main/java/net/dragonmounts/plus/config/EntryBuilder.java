package net.dragonmounts.plus.config;

import java.util.function.DoubleConsumer;

public class EntryBuilder {
    public static String translate(String key) {
        return "options.dragonmounts.plus." + key;
    }

    public static String tooltip(String key) {
        return "options.dragonmounts.plus." + key + ".tooltip";
    }

    public static BooleanEntry config(String key, boolean fallback) {
        return config(key, translate(key), fallback);
    }

    public static BooleanEntry config(String key, String name, boolean fallback) {
        return new BooleanEntry(key, name, tooltip(key), fallback);
    }

    public static DoubleEntry config(String key, double fallback) {
        return config(key, fallback, DoubleEntry.MIN_DOUBLE, Double.MAX_VALUE);
    }

    public static DoubleEntry config(String key, double fallback, double min, double max) {
        return config(key, fallback, min, max, null);
    }

    public static DoubleEntry config(String key, double fallback, double min, double max, DoubleConsumer onChanged) {
        return new DoubleEntry(key, translate(key), tooltip(key), fallback, min, max, onChanged);
    }
}
