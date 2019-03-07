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
package net.kyori.registry.id;

import net.kyori.registry.BiRegistry;
import net.kyori.registry.id.map.IncrementalIdMap;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A bidirectional id registry.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public interface BiIdRegistry<K, V> extends BiIdRegistryView<K, V>, BiRegistry<K, V>, IdRegistry<K, V> {
  /**
   * Creates a bidirectional id registry.
   *
   * @param ids the id map
   * @param <K> the key type
   * @param <V> the value type
   * @return a new bidirectional id registry
   */
  static <K, V> @NonNull BiIdRegistry<K, V> create(final @NonNull IncrementalIdMap<V> ids) {
    return new BiIdRegistryImpl<>(ids);
  }
}
