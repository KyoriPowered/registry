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
import net.kyori.registry.DefaultValueIdRegistry;
import net.kyori.registry.api.Registry;
import net.kyori.registry.api.map.IncrementalIdMap;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DefaultValueIdBiRegistryTest {
    private static final int DEFAULT = -1000;

    private final DefaultValueIdRegistry<String, String> registry = new DefaultValueIdRegistry<>(
            Registry.create(),
            "_default",
            IncrementalIdMap.create(
                    new Int2ObjectOpenHashMap<>(),
                    new Object2IntOpenHashMap<String>() {{
                        this.defaultReturnValue(DEFAULT);
                    }},
                    value -> value == DEFAULT
            )
    );

    @Test
    void testRegister() {
        assertNull(registry.get("_default"));

        assertEquals(DEFAULT, registry.id("bar").orElse(DEFAULT));

        registry.register(32, "_default", "bar");

        assertEquals("bar", registry.get("_default"));
        assertEquals("_default", registry.key("bar"));
        assertEquals(32, registry.id("bar").orElse(DEFAULT));

        // default is now in, let's check
        assertEquals(32, registry.idOrDefault(UUID.randomUUID().toString()));
        assertEquals("bar", registry.byId(ThreadLocalRandom.current().nextInt()));

        // check incremental
        assertNull(registry.get("abc"));
        registry.register("abc", "def");
        assertEquals("def", registry.get("abc"));
        assertEquals("abc", registry.key("def"));
        assertEquals(33, registry.id("def").orElse(DEFAULT));
        assertEquals("def", registry.byId(33));
    }
}
