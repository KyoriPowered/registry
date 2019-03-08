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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultedRegistryImplTest {
  @Test
  void testDefaultKeyValue() {
    final DefaultedRegistryImpl<String, String> registry = new DefaultedRegistryImpl<>("_default");
    assertEquals("_default", registry.defaultKey());

    assertEquals("The default value for key '_default' has not been registered yet!", assertThrows(IllegalStateException.class, () -> registry.get("aaa")).getMessage());
    assertEquals("The default value for key '_default' has not been registered yet!", assertThrows(IllegalStateException.class, () -> registry.get("ccc")).getMessage());

    registry.register("_default", "bbb");
    assertEquals("bbb", registry.get("aaa"));

    assertNotNull(registry.get("ccc"));
    assertEquals("bbb", registry.get("ccc"));
  }
}
