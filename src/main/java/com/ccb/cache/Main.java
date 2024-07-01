package com.ccb.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Main {

    private final static Cache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(20, TimeUnit.SECONDS)
            .removalListener((notification) -> System.out.println("移除掉的缓存值" +   //移除缓存的回调方法
            notification.getCause().name() + ":" +
            notification.getKey() + ":" +
            notification.getValue()))
            .build();

    private static void init() {
        cache.put("IDR", "IDR_INFO");
        cache.put("ZAR", "ZAR_INFO");
        cache.put("DZD", "DZD_INFO");
    }

    /**
     * 缓存过期，size！=0，但asMap是空
     *  因为size是一个近似值，asMap()视图可以遍历缓存中的每个元素，因此可以跳过等待清理的无效条目。
     *  但是asMap()的size还是不可信...
     * @param args
     */
    public static void main(String[] args) {
        Logger logger = Logger.getAnonymousLogger();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String input = sc.nextLine();
            if ("init".equals(input)) {
                init();
                logger.info("init successful");
            } else if (input.startsWith("get")) {
                String[] arr = input.split(":");
                String key = arr[1];
                String value = cache.getIfPresent(key);
                logger.info(value);
            } else if (input.startsWith("put")) {
                String[] arr = input.split(":");
                String key = arr[1];
                String value = arr[2];
                cache.put(key, value);
            } else if (input.startsWith("all")) {
                ConcurrentMap<String, String> map = cache.asMap();
                logger.info("all:" + map.keySet());
                logger.info("all:" + map.values());
                System.out.println("size:"+ map.values().isEmpty());
            } else if (input.startsWith("size")) {
                logger.info(" size: " + cache.size());
            }
        }
    }
}
