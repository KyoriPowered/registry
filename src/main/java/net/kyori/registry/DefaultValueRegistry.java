/*
 * This file is part of registry, licensed under the MIT License.
 *
 * Copyright (c) 2018-2019 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.registry;

import net.kyori.registry.api.Registry;
import net.kyori.registry.api.WithDefaultValue;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Iterator;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * A {@link DefaultValueRegistry} is an extension of a {@link RegistryImpl}. This type of registry provides
 * a default values, as registered, for missing keys.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class DefaultValueRegistry<K, V> implements Registry<K, V>, WithDefaultValue<K, V> {
    private final Registry<K, V> registry;
    private final K defaultKey;
    @MonotonicNonNull
    private V defaultValue;

    public DefaultValueRegistry(Registry<K, V> registry, final @NonNull K defaultKey) {
        registry.addRegistrationListener((key, value) -> {
            if (defaultKey.equals(key)) {
                this.defaultValue = value;
            }
        });

        this.registry = registry;
        this.defaultKey = defaultKey;
    }

    @Override
    public @NonNull V register(@NonNull K key, @NonNull V value) {
        return registry.register(key, value);
    }

    @Override
    public void addRegistrationListener(@NonNull BiConsumer<K, V> listener) {
        registry.addRegistrationListener(listener);
    }

    @Nullable
    @Override
    public V get(@NonNull K key) {
        return getOrDefault(key);
    }

    @Override
    public @NonNull Set<K> keySet() {
        return registry.keySet();
    }

    @Override
    public @Nullable K key(@NonNull V value) {
        return registry.key(value);
    }

    @Override
    public @NonNull Iterator<V> iterator() {
        return registry.iterator();
    }

    @Override
    public @NonNull K defaultKey() {
        return this.defaultKey;
    }

    @Override
    public @NonNull V getOrDefault(final @NonNull K key) {
        final /* @Nullable */ V value = registry.get(key);
        return value != null ? value : this.defaultValue;
    }

    public final V getDefaultValue() {
        return defaultValue;
    }
}
