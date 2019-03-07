package net.kyori.registry.api;

import net.kyori.registry.api.map.IncrementalIdMap;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.OptionalInt;

public abstract class IdentifiableRegistry<K, V> {
    private final Registry<K, V> registry;
    protected final IncrementalIdMap<V> ids;

    public IdentifiableRegistry(final @NonNull Registry<K, V> registry, final @NonNull IncrementalIdMap<V> ids) {
        this.registry = registry;
        this.ids = ids;
    }

    public final Registry<K, V> getRegistry() {
        return registry;
    }

    public @NonNull V register(final int id, final @NonNull K key, @NonNull V value) {
        ids.put(id, value);
        return getRegistry().register(key, value);
    }

    /**
     * Gets the id for {@code value}.
     *
     * @param value the value
     * @return the id
     */
    public abstract @NonNull OptionalInt id(final @NonNull V value);

    /**
     * Gets the value for {@code id}.
     *
     * @param id the id
     * @return the value
     */
    public abstract @Nullable V byId(final int id);
}
