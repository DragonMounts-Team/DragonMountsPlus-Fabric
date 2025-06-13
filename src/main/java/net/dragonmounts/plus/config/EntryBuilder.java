package net.dragonmounts.plus.config;

public class EntryBuilder {
    public static String makeName(String key) {
        return "options.dragonmounts.plus." + key;
    }

    public static String makeTooltip(String key) {
        return "options.dragonmounts.plus." + key + ".tooltip";
    }

    public static BooleanEntry config(ConfigHolder holder, String key, boolean fallback) {
        return new BooleanEntry(holder, key, makeName(key), makeTooltip(key), fallback);
    }

    public static DoubleEntry config(ConfigHolder holder, String key, double fallback) {
        return config(holder, key, fallback, DoubleEntry.MIN_DOUBLE, Double.MAX_VALUE);
    }

    public static DoubleEntry config(ConfigHolder holder, String key, double fallback, double min, double max) {
        return new DoubleEntry(holder, key, makeName(key), makeTooltip(key), fallback, min, max);
    }

    public static EntryBuilder config(ConfigHolder holder, String key) {
        return new EntryBuilder(holder, key);
    }

    public final ConfigHolder holder;
    public final String key;
    public String name;
    public String tooltip;

    public EntryBuilder(ConfigHolder holder, String key) {
        this.holder = holder;
        this.key = key;
    }

    public EntryBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public EntryBuilder withTooltip(String tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public BooleanEntry build(boolean fallback) {
        return new BooleanEntry(
                this.holder,
                this.key,
                this.name == null ? makeName(this.key) : this.name,
                this.tooltip == null ? makeTooltip(this.key) : this.tooltip,
                fallback
        );
    }

    public DoubleEntry build(double fallback) {
        return this.build(fallback, DoubleEntry.MIN_DOUBLE, Double.MAX_VALUE);
    }

    public DoubleEntry build(double fallback, double min, double max) {
        return new DoubleEntry(
                this.holder,
                this.key,
                this.name == null ? makeName(this.key) : this.name,
                this.tooltip == null ? makeTooltip(this.key) : this.tooltip,
                fallback,
                min,
                max
        );
    }
}
