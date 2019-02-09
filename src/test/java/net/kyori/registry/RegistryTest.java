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
package net.kyori.registry;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth8.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistryTest {
  @Test
  void testGetPut() {
    final Registry<String, String> registry = Registry.create();
    assertNull(registry.get("aaa"));
    registry.register("aaa", "bbb");
    assertEquals("bbb", registry.get("aaa"));
  }

  @Test
  void testKeySet() {
    final Registry<String, String> registry = Registry.create();
    assertThat(registry.keySet()).isEmpty();
    registry.register("eee", "fff");
    registry.register("aaa", "bbb");
    registry.register("ccc", "ddd");
    assertThat(registry.keySet()).containsExactly("aaa", "ccc", "eee");
  }

  @Test
  void testIterator() {
    final Registry<String, String> registry = Registry.create();
    assertThat(ImmutableList.copyOf(registry.iterator())).isEmpty();
    registry.register("eee", "fff");
    registry.register("aaa", "bbb");
    registry.register("ccc", "ddd");
    assertThat(ImmutableList.copyOf(registry.iterator())).containsExactly("bbb", "ddd", "fff");
  }

  @Test
  void testIteratorRemove() {
    final Registry<String, String> registry = Registry.create();
    registry.register("aaa", "bbb");
    final Iterator<String> it = registry.iterator();
    assertTrue(it.hasNext());
    assertEquals("bbb", it.next());
    assertThrows(UnsupportedOperationException.class, it::remove);
  }

  @Test
  void testStream() {
    final Registry<String, String> registry = Registry.create();
    assertThat(registry.stream()).isEmpty();
    registry.register("eee", "fff");
    registry.register("aaa", "bbb");
    registry.register("ccc", "ddd");
    assertThat(registry.stream()).containsExactly("bbb", "ddd", "fff");
  }
}
