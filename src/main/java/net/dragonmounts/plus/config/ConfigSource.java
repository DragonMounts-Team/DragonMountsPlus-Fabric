package net.dragonmounts.plus.config;

public interface ConfigSource {
    boolean isReadOnly();

    void save(ConfigHolder holder);

    void load(ConfigHolder holder);
}
