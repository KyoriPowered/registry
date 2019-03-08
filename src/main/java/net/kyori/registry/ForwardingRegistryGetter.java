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

/**
 * A registry getter which forwards all its method calls to another registry getter.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public interface ForwardingRegistryGetter<K, V> extends RegistryGetter<K, V> {
  /**
   * Gets the forwarded registry.
   *
   * @return the forwarded registry
   */
  @NonNull RegistryGetter<K, V> registry();

  @Override
  default @Nullable V get(final @NonNull K key) {
    return this.registry().get(key);
  }

  @Override
  default @Nullable K key(final @NonNull V value) {
    return this.registry().key(value);
  }

  @Override
  default @NonNull Set<K> keySet() {
    return this.registry().keySet();
  }

  @Override
  default @NonNull Iterator<V> iterator() {
    return this.registry().iterator();
  }
}
