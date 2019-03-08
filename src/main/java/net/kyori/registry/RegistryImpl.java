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
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

import static java.util.Objects.requireNonNull;

/**
 * An implementation of a {@link Registry} that forms a
 * simple key-to-value map.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class RegistryImpl<K, V> implements Registry<K, V> {
  protected final BiMap<K, V> map;
  private @MonotonicNonNull List<BiConsumer<K, V>> registrationListeners;

  protected RegistryImpl() {
    this(HashBiMap.create());
  }

  protected RegistryImpl(final @NonNull BiMap<K, V> map) {
    this.map = map;
  }

  @Override
  public final @NonNull V register(final @NonNull K key, final @NonNull V value) {
    requireNonNull(key, "key");
    requireNonNull(value, "value");

    this.map.put(key, value);

    if(this.registrationListeners != null) {
      this.registrationListeners.forEach(listener -> listener.accept(key, value));
    }

    return value;
  }

  @Override
  public void addRegistrationListener(final @NonNull BiConsumer<K, V> listener) {
    if(this.registrationListeners == null) {
      this.registrationListeners = new ArrayList<>();
    }
    this.registrationListeners.add(listener);
  }

  @Override
  public @Nullable V get(final @NonNull K key) {
    requireNonNull(key, "key");
    return this.map.get(key);
  }

  @Override
  public @Nullable K key(final @NonNull V value) {
    return this.map.inverse().get(value);
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
