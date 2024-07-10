package com.ccb.fastjson;

import com.alibaba.fastjson.JSON;

public class Main {
    public static void main(String[] args) {
        Cat catOne = Cat.builder().id(1L).build();
        System.out.println(JSON.toJSONString(catOne));
        Cat catTwo = Cat.builder().id(1L).name("catTest").build();
        System.out.println(JSON.toJSONString(catTwo));
    }
}
