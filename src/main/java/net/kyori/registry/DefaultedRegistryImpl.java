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

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A {@link DefaultedRegistryImpl} is an extension of a {@link RegistryImpl}. This type of registry provides
 * a default value, as registered, for missing keys.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class DefaultedRegistryImpl<K, V> extends RegistryImpl<K, V> implements DefaultedRegistry<K, V> {
  private final K defaultKey;
  private @MonotonicNonNull V defaultValue;

  protected DefaultedRegistryImpl(final @NonNull K defaultKey) {
    this.defaultKey = defaultKey;

    this.addRegistrationListener((key, value) -> {
      if(defaultKey.equals(key)) {
        this.defaultValue = value;
      }
    });
  }

  @Override
  public @NonNull K defaultKey() {
    return this.defaultKey;
  }

  @Override
  public @NonNull V get(final @NonNull K key) {
    final V value = super.get(key);
    if(value != null) {
      return value;
    }
    return DefaultedRegistry.defaultValue(this.defaultKey, this.defaultValue);
  }
}
