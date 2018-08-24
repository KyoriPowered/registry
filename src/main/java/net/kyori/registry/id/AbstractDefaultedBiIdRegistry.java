/*
 * This file is part of registry, licensed under the MIT License.
 *
 * Copyright (c) 2018 KyoriPowered
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

import net.kyori.registry.BiRegistryImpl;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * An abstract implementation of a bidirectional id registry with a default id, key, and value.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public abstract class AbstractDefaultedBiIdRegistry<K, V> extends BiRegistryImpl<K, V> implements DefaultedBiIdRegistry<K, V> {
  private final K defaultKey;
  private int defaultId;
  private V defaultValue;

  protected AbstractDefaultedBiIdRegistry(final @NonNull K defaultKey) {
    this.defaultKey = defaultKey;
  }

  @Override
  public int idOrDefault(final @NonNull V value) {
    return this.id(value).orElse(this.defaultId);
  }

  @Override
  public @NonNull K defaultKey() {
    return this.defaultKey;
  }

  @Override
  public @NonNull V getOrDefault(final @NonNull K key) {
    final @Nullable V value = this.get(key);
    return value != null ? value : this.defaultValue;
  }

  @Override
  public void register(@NonNull final K key, @NonNull final V value) {
    this.register(this.nextId(), key, value);
  }

  @Override
  public void register(final int id, @NonNull final K key, @NonNull final V value) {
    super.register(key, value);

    this.register0(id, key, value);

    if(this.defaultKey.equals(key)) {
      this.defaultId = id;
      this.defaultValue = value;
    }
  }

  protected abstract int nextId();

  protected abstract void register0(final int id, @NonNull final K key, @NonNull final V value);
}
