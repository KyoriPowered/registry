package net.kyori.registry;

import net.kyori.registry.api.IRegistry;
import net.kyori.registry.api.WithDefaultIdentifier;
import net.kyori.registry.api.WithDefaultValue;
import net.kyori.registry.api.map.IncrementalIdMap;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.OptionalInt;

public class DefaultValueIdRegistry<K, V> extends IdRegistry<K, V> implements WithDefaultValue<K, V>, WithDefaultIdentifier<V> {
    private final K defaultKey;
    @MonotonicNonNull
    private V defaultValue;
    private int defaultId;

    public DefaultValueIdRegistry(IRegistry<K, V> registry, final @NonNull K defaultKey, final @NonNull IncrementalIdMap<V> ids) {
        super(registry, ids);

        this.defaultKey = defaultKey;

        registry.addRegistrationListener((key, value) -> {
            if (defaultKey.equals(key)) {
                this.defaultValue = value;
                this.defaultId = id(value).orElseThrow(() -> new IllegalStateException("This shouldn't happen!"));
            }
        });
    }

    @NonNull
    @Override
    public K defaultKey() {
        return defaultKey;
    }

    @NonNull
    @Override
    public V getOrDefault(@NonNull K key) {
        final V value = getRegistry().get(key);
        return value != null ? value : this.defaultValue;
    }

    @Override
    public int idOrDefault(@NonNull V value) {
        final OptionalInt id = id(value);
        return id.isPresent() ? id.getAsInt() : defaultId;
    }

    @Override
    public @Nullable V byId(int id) {
        final V value = super.byId(id);
        return value != null ? value : this.defaultValue;
    }
}

