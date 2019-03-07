package net.kyori.registry.api;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface WithDefaultValue<K, V> {
    @NonNull K defaultKey();

    @NonNull V getOrDefault(@NonNull K key);
}
