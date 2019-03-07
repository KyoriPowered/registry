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
package net.kyori.registry.impl.id;

import net.kyori.registry.api.IdentifiableRegistry;
import net.kyori.registry.api.Registry;
import net.kyori.registry.api.defaultable.DefaultableIdentifier;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.OptionalInt;

/**
 * A {@link DefaultableIdentifiableRegistry} is a container for a {@link IdentifiableRegistry} which itself is a
 * container for a {@link Registry} which permits IDs with each entry.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public final class DefaultableIdentifiableRegistry<K, V> implements DefaultableIdentifier<V> {
    private IdentifiableRegistry<K, V> registry;
    private int defaultId;

    public DefaultableIdentifiableRegistry(IdentifiableRegistry<K, V> registry) {
        registry.getRegistry().addRegistrationListener((key, value) ->
                this.defaultId = registry.id(value).orElseThrow(() ->
                        new IllegalStateException("This shouldn't happen!")
                )
        );

        this.registry = registry;
    }

    public final IdentifiableRegistry<K, V> getRegistry() {
        return registry;
    }

    @Override
    public int idOrDefault(@NonNull V value) {
        final OptionalInt id = registry.id(value);
        return id.isPresent() ? id.getAsInt() : this.defaultId;
    }
}
