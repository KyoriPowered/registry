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

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A read-only view of a bidirectional registry.
 *
 * @param <K> the key type
 * @param <V> the value type
 * @see Registry
 */
public interface RegistryGetter<K, V> extends Iterable<V> {
  /**
   * Gets the value for {@code key}.
   *
   * @param key the key
   * @return the value
   */
  @Nullable V get(final @NonNull K key);

  /**
   * Gets the key for {@code value}.
   *
   * @param value the value
   * @return the key
   */
  @Nullable K key(final @NonNull V value);

  /**
   * Gets a set of the keys contained in this registry.
   *
   * @return a set of the keys contained in this registry
   */
  @NonNull Set<K> keySet();

  /**
   * Creates an unmodifiable iterator of values.
   *
   * @return an unmodifiable iterator
   */
  @Override
  @NonNull Iterator<V> iterator();

  /**
   * Creates a stream of values.
   *
   * @return a stream
   */
  default @NonNull Stream<V> stream() {
    return StreamSupport.stream(this.spliterator(), false);
  }
}
