package com.ccb.cache.self;

import java.util.Map;

public interface LoadStrategy<K, V> {
    Map<K, V> loadAll();
}
