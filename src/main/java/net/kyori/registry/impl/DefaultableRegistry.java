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
package net.kyori.registry.impl;

import net.kyori.registry.api.Registry;
import net.kyori.registry.api.defaultable.Defaultable;
import net.kyori.registry.impl.nonid.UniRegistry;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A {@link DefaultableRegistry} is an extension of a {@link UniRegistry}. This type of registry provides
 * a default values, as registered, for missing keys.
 *
 * @param <K> the key type
 * @param <V> the value type
 * @param <T> the {@link UniRegistry} type
 */
public class DefaultableRegistry<K, V> implements Defaultable<K, V> {
    private final Registry<K, V> registry;
    private final K defaultKey;

    @MonotonicNonNull
    private V defaultValue;

    public DefaultableRegistry(Registry<K, V> registry, final @NonNull K defaultKey) {
        registry.addRegistrationListener((key, value) -> {
            if (defaultKey.equals(key)) {
                this.defaultValue = value;
            }
        });

        this.registry = registry;
        this.defaultKey = defaultKey;
    }

    public Registry<K, V> getRegistry() {
        return registry;
    }

    @Override
    public @NonNull K defaultKey() {
        return this.defaultKey;
    }

    @Override
    public @NonNull V getOrDefault(final @NonNull K key) {
        final /* @Nullable */ V value = registry.get(key);
        return value != null ? value : this.defaultValue;
    }
}
