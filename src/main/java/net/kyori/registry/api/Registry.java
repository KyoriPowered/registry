package net.kyori.registry.api;

import net.kyori.registry.impl.RegistryImpl;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Registry<K, V> extends Iterable<V> {
    static <K, V> Registry<K, V> create() {
        return new RegistryImpl<>();
    }

    static <K, V> Registry<K, V> createFromMap(Map<K, V> map) {
        return new RegistryImpl<>(map);
    }

    /**
     * Associates {@code key} to {@code value}.
     *
     * @param key   the key
     * @param value the value
     * @return the value
     */
    @NonNull V register(final @NonNull K key, final @NonNull V value);

    /**
     * Adds a callback function that will be executed after any call to
     * {@link Registry#register(K, V)}
     *
     * @param listener the callback function
     */
    void addRegistrationListener(final @NonNull BiConsumer<K, V> listener);

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
     * Creates an unmodifiable iterator of values.
     *
     * @return an unmodifiable iterator
     */
    @Override
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
