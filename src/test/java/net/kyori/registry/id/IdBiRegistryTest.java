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
import net.kyori.registry.BiRegistry;
import net.kyori.registry.IdRegistry;
import net.kyori.registry.api.IBiRegistry;
import net.kyori.registry.api.map.IncrementalIdMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IdBiRegistryTest {
    private static final int DEFAULT = -1000;

    private final IdRegistry<String, String> container = new IdRegistry<>(new BiRegistry<>(), IncrementalIdMap.create(new Int2ObjectOpenHashMap<>(), new Object2IntOpenHashMap<String>() {
        {
            this.defaultReturnValue(DEFAULT);
        }
    }, value -> value == DEFAULT));

    @Test
    void testRegister() {
        IBiRegistry registry = (IBiRegistry) container.getRegistry();

        assertNull(registry.get("foo"));
        assertEquals(DEFAULT, container.id("bar").orElse(DEFAULT));
        container.register(32, "foo", "bar");
        assertEquals("bar", registry.get("foo"));
        assertEquals("foo", registry.key("bar"));
        assertEquals(32, container.id("bar").orElse(DEFAULT));

        // check incremental
        assertNull(registry.get("abc"));
        container.register("abc", "def");
        assertEquals("def", registry.get("abc"));
        assertEquals("abc", registry.key("def"));
        assertEquals(33, container.id("def").orElse(DEFAULT));
        assertEquals("def", container.byId(33));
    }
}
