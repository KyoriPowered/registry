package net.kyori.registry;

import net.kyori.registry.api.IRegistry;
import net.kyori.registry.api.map.IncrementalIdMap;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.OptionalInt;

public class IdRegistry<K, V> {
    private final IRegistry<K, V> registry;
    protected final IncrementalIdMap<V> ids;

    public IdRegistry(final @NonNull IRegistry<K, V> registry, final @NonNull IncrementalIdMap<V> ids) {
        this.registry = registry;
        this.ids = ids;
    }

    public final IRegistry<K, V> getRegistry() {
        return registry;
    }

    public @NonNull V register(final @NonNull K key, @NonNull V value) {
        return register(ids.next(), key, value);
    }

    public @NonNull V register(final int id, final @NonNull K key, @NonNull V value) {
        ids.put(id, value);
        getRegistry().register(key, value);
        return value;
    }

    /**
     * Gets the id for {@code value}.
     *
     * @param value the value
     * @return the id
     */
    public @NonNull OptionalInt id(final @NonNull V value) {
        return this.ids.id(value);
    }

    /**
     * Gets the value for {@code id}.
     *
     * @param id the id
     * @return the value
     */
    public @Nullable V byId(final int id) {
        return this.ids.get(id);
    }
}
