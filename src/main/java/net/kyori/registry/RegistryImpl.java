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

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Iterators;
import net.kyori.registry.api.Registry;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;
import java.util.function.BiConsumer;

import static java.util.Objects.requireNonNull;

/**
 * An implementation of a {@link net.kyori.registry.api.Registry} that forms a
 * simple key-to-value map.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class RegistryImpl<K, V> implements Registry<K, V> {
    protected final BiMap<K, V> map;
    private final List<BiConsumer<K, V>> registrationListeners = new ArrayList<>();

    public RegistryImpl() {
        this(HashBiMap.create());
    }

    public RegistryImpl(BiMap<K, V> map) {
        this.map = map;
    }

    @Override
    public final @NonNull V register(final @NonNull K key, @NonNull V value) {
        requireNonNull(key, "key");
        requireNonNull(value, "value");

        map.put(key, value);
        registrationListeners.forEach(listener -> listener.accept(key, value));

        return value;
    }

    @Override
    public void addRegistrationListener(@NonNull BiConsumer<K, V> listener) {
        registrationListeners.add(listener);
    }

    @Override
    public @Nullable V get(final @NonNull K key) {
        requireNonNull(key, "key");
        return map.get(key);
    }

    @Override
    public @NonNull Set<K> keySet() {
        return Collections.unmodifiableSet(map.keySet());
    }

    @Nullable
    @Override
    public K key(@NonNull V value) {
        return map.inverse().get(value);
    }

    @Override
    public @NonNull Iterator<V> iterator() {
        return Iterators.unmodifiableIterator(map.values().iterator());
    }
}
