package net.kyori.registry.api;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.kyori.registry.impl.BidirectionalRegistryImpl;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface BidirectionalRegistry<K, V> extends Registry<K, V> {
    static <K, V> BidirectionalRegistry<K, V> create() {
        return new BidirectionalRegistryImpl<>();
    }

    static <K, V> BidirectionalRegistry<K, V> createFromMap(HashBiMap<K, V> map) {
        return new BidirectionalRegistryImpl<>(map);
    }

    /**
     * Gets the key for {@code value}.
     *
     * @param value the value
     * @return the key
     */
    @Nullable K key(final @NonNull V value);
}
