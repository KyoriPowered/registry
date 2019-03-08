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

import java.util.function.BiConsumer;

/**
 * A bidirectional registry.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public interface Registry<K, V> extends RegistryGetter<K, V> {
  /**
   * Creates a new registry.
   *
   * @param <K> the key type
   * @param <V> the value type
   * @return a new registry
   */
  static <K, V> @NonNull RegistryImpl<K, V> create() {
    return new RegistryImpl<>();
  }

  /**
   * Associates {@code key} to {@code value}.
   *
   * @param key   the key
   * @param value the value
   * @return the value
   */
  @NonNull V register(final @NonNull K key, final @NonNull V value);

  /**
   * Adds a callback function that will be executed after any call to
   * {@link Registry#register(Object, Object)}
   *
   * @param listener the callback function
   */
  void addRegistrationListener(final @NonNull BiConsumer<K, V> listener);
}
