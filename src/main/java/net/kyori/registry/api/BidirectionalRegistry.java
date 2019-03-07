package net.kyori.registry.api;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface BidirectionalRegistry<K, V> extends Registry<K, V> {
    /**
     * Gets the key for {@code value}.
     *
     * @param value the value
     * @return the key
     */
    @Nullable K key(final @NonNull V value);
}
