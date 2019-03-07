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
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * A simple implementation of a bidirectional registry.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class BiRegistryImpl<K, V> extends AbstractRegistry<K, V> implements BiRegistry<K, V> {
  private final BiMap<K, V> map = HashBiMap.create();

  @Override
  public @Nullable K key(final @NonNull V value) {
    return this.map.inverse().get(value);
  }

  @Override
  public @Nullable V get(final @NonNull K key) {
    return this.map.get(key);
  }

  @Override
  protected @NonNull V register0(final @NonNull K key, final @NonNull V value) {
    this.map.put(key, value);
    return value;
  }

  @Override
  public @NonNull Set<K> keySet() {
    return Collections.unmodifiableSet(this.map.keySet());
  }

  @Override
  public @NonNull Iterator<V> iterator() {
    return Iterators.unmodifiableIterator(this.map.values().iterator());
  }
}
