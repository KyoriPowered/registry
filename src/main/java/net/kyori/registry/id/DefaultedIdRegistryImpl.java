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

import net.kyori.registry.DefaultedRegistry;
import net.kyori.registry.id.map.IncrementalIdMap;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.OptionalInt;

/**
 * A simple implementation of a bidirectional id registry with a default id, key, and value.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class DefaultedIdRegistryImpl<K, V> extends IdRegistryImpl<K, V> implements DefaultedIdRegistry<K, V> {
  private final K defaultKey;
  private @MonotonicNonNull V defaultValue;
  private int defaultId;

  protected DefaultedIdRegistryImpl(final @NonNull IncrementalIdMap<V> ids, final @NonNull K defaultKey) {
    super(ids);
    this.defaultKey = defaultKey;

    this.addRegistrationListener((key, value) -> {
      if(defaultKey.equals(key)) {
        this.defaultValue = value;
        this.defaultId = this.id(value).orElseThrow(() -> new IllegalStateException("This shouldn't happen!"));
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
    return value != null ? value : this.defaultValue();
  }

  @Override
  public int idOrDefault(final @NonNull V value) {
    final OptionalInt id = this.id(value);
    return id.isPresent() ? id.getAsInt() : this.defaultId;
  }

  @Override
  public @Nullable V byId(final int id) {
    final V value = super.byId(id);
    if(value != null) {
      return value;
    }
    return this.defaultValue();
  }

  private @NonNull V defaultValue() {
    return DefaultedRegistry.defaultValue(this.defaultKey, this.defaultValue);
  }
}
