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
package net.kyori.registry.impl.registry;

import net.kyori.registry.ForwardingRegistry;
import net.kyori.registry.Registry;
import net.kyori.registry.map.IncrementalIdMap;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.OptionalInt;

public class IdRegistryImpl<K, V> implements ForwardingRegistry<K, V> {
  private final Registry<K, V> registry;
  protected final IncrementalIdMap<V> ids;

  public IdRegistryImpl(final @NonNull Registry<K, V> registry, final @NonNull IncrementalIdMap<V> ids) {
    this.registry = registry;
    this.ids = ids;
  }

  @Override
  public @NonNull Registry<K, V> registry() {
    return this.registry;
  }

  @Override
  public @NonNull V register(final @NonNull K key, final @NonNull V value) {
    return this.register(this.ids.next(), key, value);
  }

  // TODO: should be an interface method that we implement
  public @NonNull V register(final int id, final @NonNull K key, final @NonNull V value) {
    this.ids.put(id, value);
    this.registry.register(key, value);
    return value;
  }

  // TODO: should be an interface method that we implement
  /**
   * Gets the id for {@code value}.
   *
   * @param value the value
   * @return the id
   */
  public @NonNull OptionalInt id(final @NonNull V value) {
    return this.ids.id(value);
  }

  // TODO: should be an interface method that we implement
  /**
   * Gets the value for {@code id}.
   *
   * @param id the id
   * @return the value
   */
  public @Nullable V byId(final int id) {
    return this.ids.get(id);
  }
}
