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

import net.kyori.registry.id.map.IncrementalIdMap;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultedIdRegistryImplTest {
  private static final int DEFAULT = -1000;
  private DefaultedIdRegistryImpl<String, String> registry = new DefaultedIdRegistryImpl<>(IncrementalIdMap.create(DEFAULT), "_default");

  @Test
  void testRegister() {
    assertEquals("The default value for key '_default' has not been registered yet!", assertThrows(IllegalStateException.class, () -> this.registry.get("_default")).getMessage());

    assertEquals(DEFAULT, this.registry.id("bar").orElse(DEFAULT));

    this.registry.register(32, "_default", "bar");

    assertEquals("bar", this.registry.get("_default"));
    assertEquals("_default", this.registry.key("bar"));
    assertEquals(32, this.registry.id("bar").orElse(DEFAULT));

    // default is now in, let's check
    assertEquals(32, this.registry.idOrDefault(UUID.randomUUID().toString()));
    assertEquals("bar", this.registry.byId(ThreadLocalRandom.current().nextInt()));

    // check incremental
    assertEquals("bar", this.registry.get("abc"));
    this.registry.register("abc", "def");
    assertEquals("def", this.registry.get("abc"));
    assertEquals("abc", this.registry.key("def"));
    assertEquals(33, this.registry.id("def").orElse(DEFAULT));
    assertEquals("def", this.registry.byId(33));
  }
}
