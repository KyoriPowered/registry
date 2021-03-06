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
package net.kyori.registry.id.map;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * An incremental id map.
 *
 * @param <V> the value type
 */
public interface IncrementalIdMap<V> extends IdMap<V> {
  /**
   * Creates an incremental id map.
   *
   * @param idToV the id to value map
   * @param vToId the value to id map
   * @param <V> the value type
   * @return an incremental id map
   */
  static <V> @NonNull IncrementalIdMap<V> create(final @NonNull Int2ObjectMap<V> idToV, final @NonNull Object2IntMap<V> vToId) {
    return new IncrementalIdMapImpl<>(idToV, vToId);
  }

  /**
   * Gets the next id.
   *
   * @return the next id
   */
  int next();

  /**
   * Associates {@code value} with the next available id.
   *
   * @param value the value
   * @return the id
   */
  int put(final @NonNull V value);
}
