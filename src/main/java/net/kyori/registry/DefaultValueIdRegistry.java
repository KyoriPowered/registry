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

