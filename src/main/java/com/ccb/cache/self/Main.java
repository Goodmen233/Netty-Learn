package com.ccb.cache.self;

import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Main {

    private final static Cache<String, String> cache = new Cache<>(10L, TimeUnit.SECONDS, () -> {
        Map<String, String> map = new HashMap<>();
        if (System.currentTimeMillis() % 2 == 0) {
            map.put("IDR", "IDR_INFO");
        }
        map.put("ZAR", "ZAR_INFO");
        map.put("DZD", "DZD_INFO");
        return map;
    });

    private static void init() {
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
                String value = cache.get(key);
                logger.info(value);
            }
//            else if (input.startsWith("all")) {
//                ConcurrentMap<String, String> map = cache.asMap();
//                logger.info("all:" + map.keySet());
//                logger.info("all:" + map.values());
//                System.out.println("size:"+ map.values().isEmpty());
//            }
            else if (input.startsWith("size")) {
                logger.info("size: " + cache.getAll());
            }
        }
    }
}
