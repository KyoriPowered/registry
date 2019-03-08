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
package net.kyori.registry.impl.map;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.kyori.registry.api.map.IncrementalIdMap;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.IntPredicate;

/**
 * A simple implementation of an incremental id map.
 *
 * @param <V> the value type
 */
public class IncrementalIdMapImpl<V> extends IdMapImpl<V> implements IncrementalIdMap<V> {
  private int nextId;

  public IncrementalIdMapImpl(final @NonNull Int2ObjectMap<V> idToV, final @NonNull Object2IntMap<V> vToId, final @NonNull IntPredicate empty) {
    super(idToV, vToId, empty);
  }

  @Override
  public int next() {
    return this.nextId;
  }

  @Override
  public int put(@NonNull final V value) {
    final int id = this.nextId;
    this.put(id, value);
    return id;
  }

  @Override
  protected void put0(final int id, @NonNull final V value) {
    super.put0(id, value);
    if(this.nextId <= id) {
      this.nextId = id + 1;
    }
  }
}
