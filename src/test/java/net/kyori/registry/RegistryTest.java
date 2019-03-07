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
package net.kyori.registry;

import com.google.common.collect.ImmutableList;
import net.kyori.registry.api.IRegistry;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth8.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RegistryTest {
    @Test
    void testGetPut() {
        final Registry<String, String> registry = new Registry<>();
        assertNull(registry.get("aaa"));
        registry.register("aaa", "bbb");
        assertEquals("bbb", registry.get("aaa"));
    }

    @Test
    void testKeySet() {
        final Registry<String, String> registryImpl = new Registry<>();
        assertThat(registryImpl.keySet()).isEmpty();
        registryImpl.register("eee", "fff");
        registryImpl.register("aaa", "bbb");
        registryImpl.register("ccc", "ddd");
        assertThat(registryImpl.keySet()).containsExactly("aaa", "ccc", "eee");
    }

    @Test
    void testIterator() {
        final Registry<String, String> registryImpl = new Registry<>();
        assertThat(ImmutableList.copyOf(registryImpl.iterator())).isEmpty();
        registryImpl.register("eee", "fff");
        registryImpl.register("aaa", "bbb");
        registryImpl.register("ccc", "ddd");
        assertThat(ImmutableList.copyOf(registryImpl.iterator())).containsExactly("bbb", "ddd", "fff");
    }

    @Test
    void testIteratorRemove() {
        final Registry<String, String> registryImpl = new Registry<>();
        registryImpl.register("aaa", "bbb");
        final Iterator<String> it = registryImpl.iterator();
        assertTrue(it.hasNext());
        assertEquals("bbb", it.next());
        assertThrows(UnsupportedOperationException.class, it::remove);
    }

    @Test
    void testStream() {
        final Registry<String, String> registryImpl = new Registry<>();
        assertThat(registryImpl.stream()).isEmpty();
        registryImpl.register("eee", "fff");
        registryImpl.register("aaa", "bbb");
        registryImpl.register("ccc", "ddd");
        assertThat(registryImpl.stream()).containsExactly("bbb", "ddd", "fff");
    }
}
