package net.kyori.registry.api.defaultable;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface Defaultable<K, V> {
    @NonNull K defaultKey();
    @NonNull V getOrDefault(@NonNull K key);
}
