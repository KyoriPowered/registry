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

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.kyori.registry.id.map.IncrementalIdMap;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DefaultedBiIdRegistryImplTest {
  private static final int DEFAULT = -1000;
  private final DefaultedIdRegistry<String, String> registry = DefaultedIdRegistry.create(IncrementalIdMap.create(new Int2ObjectOpenHashMap<>(), new Object2IntOpenHashMap<String>() {
    {
      this.defaultReturnValue(DEFAULT);
    }
  }), "foo");

  @Test
  void testRegister() {
    assertNull(this.registry.get("foo"));
    assertEquals(DEFAULT, this.registry.id("bar"));
    this.registry.register(32, "foo", "bar");
    assertEquals("bar", this.registry.get("foo"));
    assertEquals("foo", this.registry.key("bar"));
    assertEquals(32, this.registry.id("bar"));

    // default is now in, let's check

    assertEquals(32, this.registry.idOrDefault(UUID.randomUUID().toString()));
    assertEquals("bar", this.registry.byId(ThreadLocalRandom.current().nextInt()));

    // check incremental

    assertNull(this.registry.get("abc"));
    this.registry.register("abc", "def");
    assertEquals("def", this.registry.get("abc"));
    assertEquals("abc", this.registry.key("def"));
    assertEquals(33, this.registry.id("def"));
    assertEquals("def", this.registry.byId(33));
  }
}
