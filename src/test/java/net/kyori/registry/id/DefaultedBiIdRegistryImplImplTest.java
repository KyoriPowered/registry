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
import net.kyori.registry.api.IdentifiableRegistry;
import net.kyori.registry.api.map.IncrementalIdMap;
import net.kyori.registry.impl.id.DefaultableIdentifiableRegistry;
import net.kyori.registry.impl.id.IdentifiableRegistryImpl;
import net.kyori.registry.impl.nonid.BidiRegistry;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DefaultedBiIdRegistryImplImplTest {
  private static final int DEFAULT = -1000;
  private final DefaultableIdentifiableRegistry<String, String> container = new DefaultableIdentifiableRegistry<>(
          new IdentifiableRegistryImpl<>(
                  new BidiRegistry<>(),
                  IncrementalIdMap.create(
                          new Int2ObjectOpenHashMap<>(),
                          new Object2IntOpenHashMap<String>() {{
                            this.defaultReturnValue(DEFAULT);
                          }},
                          value -> value == DEFAULT
                  )
          )
  );

  @Test
  void testRegister() {
    final IdentifiableRegistry<String, String> identifiable = container.getRegistry();
    final BidiRegistry<String, String> registry = (BidiRegistry<String, String>) identifiable.getRegistry();

    assertNull(registry.get("foo"));
    assertEquals(DEFAULT, container.getRegistry().id("bar").orElse(DEFAULT));
    identifiable.register(32, "foo", "bar");
    assertEquals("bar", registry.get("foo"));
    assertEquals("foo", registry.key("bar"));
    assertEquals(32, identifiable.id("bar").orElse(DEFAULT));

    // default is now in, let's check
    assertEquals(32, container.idOrDefault(UUID.randomUUID().toString()));
    assertEquals("bar", identifiable.byId(ThreadLocalRandom.current().nextInt()));

    // check incremental
    assertNull(registry.get("abc"));
    registry.register("abc", "def");
    assertEquals("def", registry.get("abc"));
    assertEquals("abc", registry.key("def"));
    assertEquals(33, identifiable.id("def").orElse(DEFAULT));
    assertEquals("def", identifiable.byId(33));
  }
}
