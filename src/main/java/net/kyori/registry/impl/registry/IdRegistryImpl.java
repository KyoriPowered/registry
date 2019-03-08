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
package net.kyori.registry.impl.registry;

import net.kyori.registry.api.registry.Registry;
import net.kyori.registry.api.map.IncrementalIdMap;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Iterator;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.BiConsumer;

public class IdRegistryImpl<K, V> implements Registry<K, V> {
    private final Registry<K, V> registry;
    protected final IncrementalIdMap<V> ids;

    public IdRegistryImpl(final @NonNull Registry<K, V> registry, final @NonNull IncrementalIdMap<V> ids) {
        this.registry = registry;
        this.ids = ids;
    }

    public @NonNull V register(final @NonNull K key, @NonNull V value) {
        return this.register(ids.next(), key, value);
    }

    public @NonNull V register(final int id, final @NonNull K key, @NonNull V value) {
        ids.put(id, value);
        registry.register(key, value);
        return value;
    }

    @Override
    public void addRegistrationListener(@NonNull BiConsumer<K, V> listener) {
        registry.addRegistrationListener(listener);
    }

    @Nullable
    @Override
    public V get(@NonNull K key) {
        return registry.get(key);
    }

    @Override
    public @NonNull Set<K> keySet() {
        return registry.keySet();
    }

    @Nullable
    @Override
    public K key(@NonNull V value) {
        return registry.key(value);
    }

    @Override
    public @NonNull Iterator<V> iterator() {
        return registry.iterator();
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
