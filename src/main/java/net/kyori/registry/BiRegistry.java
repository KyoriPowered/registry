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
import net.kyori.registry.api.IBiRegistry;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * An extension of the {@link Registry} implementation, but also implementing a {@link IBiRegistry}
 * to create a bidirectional key-to-value map.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class BiRegistry<K, V> extends Registry<K, V> implements IBiRegistry<K, V> {
    public BiRegistry() {
        this(HashBiMap.create());
    }

    public BiRegistry(BiMap<K, V> map) {
        super(map);
    }

    @Nullable
    @Override
    public K key(@NonNull V value) {
        BiMap<K, V> cast = (HashBiMap<K, V>) map;
        return cast.inverse().get(value);
    }
}
