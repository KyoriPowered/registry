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
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A simple implementation of an id map.
 *
 * @param <V> the value type
 */
public class IdMapImpl<V> implements IdMap<V> {
  private final Int2ObjectMap<V> idToV;
  private final Object2IntMap<V> vToId;

  protected IdMapImpl(final @NonNull Int2ObjectMap<V> idToV, final @NonNull Object2IntMap<V> vToId) {
    this.idToV = idToV;
    this.vToId = vToId;
  }

  @Override
  public @Nullable V get(final int id) {
    return this.idToV.get(id);
  }

  @Override
  public int id(final @NonNull V value) {
    return this.vToId.getInt(value);
  }

  @Override
  public int idOrDefault(@NonNull final V value, final int defaultId) {
    return this.vToId.getOrDefault(value, defaultId);
  }

  @Override
  public final @NonNull V put(final int id, final @NonNull V value) {
    this.put0(id, value);
    return value;
  }

  protected void put0(final int id, final @NonNull V value) {
    this.idToV.put(id, value);
    this.vToId.put(value, id);
  }
}
