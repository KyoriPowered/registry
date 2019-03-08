package net.kyori.registry.api;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface RegistryView<K, V> extends Iterable<V> {
    /**
     * Gets the value for {@code key}.
     *
     * @param key the key
     * @return the value
     */
    @Nullable V get(final @NonNull K key);

    /**
     * Gets a set of the keys contained in this registry.
     *
     * @return a set of the keys contained in this registry
     */
    @NonNull Set<K> keySet();

    /**
     * Gets the key for {@code value}.
     *
     * @param value the value
     * @return the key
     */
    @Nullable K key(final @NonNull V value);

    /**
     * Creates an unmodifiable iterator of values.
     *
     * @return an unmodifiable iterator
     */
    @NonNull Iterator<V> iterator();

    /**
     * Creates a stream of values.
     *
     * @return a stream
     */
    default @NonNull Stream<V> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }
}
