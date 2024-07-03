package com.ccb.cache.self;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 一次性全量缓存使用
 */
@Slf4j
public class Cache<K, V> {

    private final Map<K, V> cacheMap;

    private final long cacheExpireTime;

    private final TimeUnit unit;

    private final LoadStrategy<K, V> loadStrategy;

    private long lastLoadTime;

    public Cache() {
        this(30L, TimeUnit.MINUTES, () -> {
            return null;
        });
    }

    public Cache(Long expireTime, TimeUnit unit, LoadStrategy<K, V> loadStrategy) {
        this.cacheMap = new ConcurrentHashMap<>();
        this.cacheExpireTime = expireTime;
        this.unit = unit;
        this.loadStrategy = loadStrategy;
    }

    /**
     * 缓存所有键值
     */
    public void put(Map<K, V> map) {
        cacheMap.putAll(map);
        // 更新最后更新时间
        lastLoadTime = System.nanoTime();
        log.debug("Cache load success");
    }

    /**
     * 获取单个键
     */
    public V get(K key) {
        reloadIfExpire();
        log.debug("Cache get key:{}", key);
        return cacheMap.get(key);
    }

    /**
     * 获取所有的键
     */
    public Collection<V> getAll() {
        reloadIfExpire();
        log.debug("Cache getAll");
        return cacheMap.values();
    }

    /**
     * 超时重新加载
     */
    public void reloadIfExpire() {
        long current = System.nanoTime();
        if (current - lastLoadTime >= unit.toNanos(cacheExpireTime)) {
            this.cacheMap.clear();
            this.put(loadStrategy.loadAll());
            log.debug("Cache reload success");
        }
    }
}
