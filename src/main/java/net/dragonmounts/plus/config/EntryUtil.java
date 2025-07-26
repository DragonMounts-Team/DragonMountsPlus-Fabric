package net.dragonmounts.plus.config;

import com.google.common.collect.HashBiMap;
import net.minecraft.nbt.Tag;

import java.util.function.DoubleConsumer;

public class EntryUtil {
    public static String translate(String key) {
        return "options.dragonmounts.plus." + key;
    }

    public static String tooltip(String key) {
        return "options.dragonmounts.plus." + key + ".tooltip";
    }

    public static BooleanEntry config(String key, boolean fallback) {
        return config(key, fallback, translate(key));
    }

    public static BooleanEntry config(String key, boolean fallback, String name) {
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

    public static void register(HashBiMap<ConfigEntry<?>, Integer> registry, ConfigEntry<?> entry) {
        registry.put(entry, registry.size());
    }

    public static <T> void override(ConfigEntry<T> entry, Tag data) {
        entry.override(entry.load(data));
    }
}
