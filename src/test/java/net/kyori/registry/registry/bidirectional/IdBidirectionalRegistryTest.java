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
package net.kyori.registry.registry.bidirectional;

import net.kyori.registry.impl.registry.IdRegistryImpl;
import net.kyori.registry.api.registry.Registry;
import net.kyori.registry.api.map.IncrementalIdMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IdBidirectionalRegistryTest {
    private static final int DEFAULT = -1000;

    private final IdRegistryImpl<String, String> registry = new IdRegistryImpl<>(
            Registry.create(),
            IncrementalIdMap.create(DEFAULT)
    );

    @Test
    void testRegister() {
        assertNull(registry.get("foo"));
        assertEquals(DEFAULT, registry.id("bar").orElse(DEFAULT));
        registry.register(32, "foo", "bar");
        assertEquals("bar", registry.get("foo"));
        assertEquals("foo", registry.key("bar"));
        assertEquals(32, registry.id("bar").orElse(DEFAULT));

        // check incremental
        assertNull(registry.get("abc"));
        registry.register("abc", "def");
        assertEquals("def", registry.get("abc"));
        assertEquals("abc", registry.key("def"));
        assertEquals(33, registry.id("def").orElse(DEFAULT));
        assertEquals("def", registry.byId(33));
    }
}
