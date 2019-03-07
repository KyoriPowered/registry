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

import net.kyori.registry.DefaultedBiRegistryImpl;
import net.kyori.registry.id.map.IncrementalIdMap;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.OptionalInt;

public class DefaultedBiIdRegistryImpl<K, V> extends DefaultedBiRegistryImpl<K, V> implements DefaultedBiIdRegistry<K, V> {
  private final IncrementalIdMap<V> ids;
  private int defaultId;

  public DefaultedBiIdRegistryImpl(final @NonNull IncrementalIdMap<V> ids, final @NonNull K defaultKey) {
    super(defaultKey);
    this.ids = ids;
  }

  @Override
  protected @NonNull V register0(final @NonNull K key, final @NonNull V value) {
    return this.register(this.ids.next(), key, value);
  }

  @Override
  public @NonNull V register(final int id, final @NonNull K key, @NonNull V value) {
    this.ids.put(id, value);
    value = super.register0(key, value);
    this.registered(key, value);
    return value;
  }

  @Override
  protected void defaultRegistered(@NonNull final K key, @NonNull final V value) {
    super.defaultRegistered(key, value);
    this.defaultId = this.id(value).orElseThrow(() -> new IllegalStateException("This shouldn't happen!"));
  }

  @Override
  public @NonNull OptionalInt id(final @NonNull V value) {
    return this.ids.id(value);
  }

  @Override
  public int idOrDefault(final @NonNull V value) {
    final OptionalInt id = this.id(value);
    return id.isPresent() ? id.getAsInt() : this.defaultId;
  }

  @Override
  public @Nullable V byId(final int id) {
    final V value = this.ids.get(id);
    return value != null ? value : this.defaultValue;
  }
}
