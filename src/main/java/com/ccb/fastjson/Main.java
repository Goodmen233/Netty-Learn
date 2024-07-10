package com.ccb.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Cat catOne = Cat.builder().id(1L).build();
        String catOneString = JSON.toJSONString(catOne);
        System.out.println(catOneString);
        Cat catTwo = Cat.builder().id(1L).name("catTest").build();
        System.out.println(JSON.toJSONString(catTwo));
        Wrap<Cat> wrapCatOne = Wrap.<Cat>builder().cat(catOne).content("test").build();
        String jsonString = JSON.toJSONString(wrapCatOne);
        System.out.println(jsonString);

        Wrap<Object> wrapCat = JSON.parseObject(jsonString, Wrap.class);
        System.out.println(JSON.toJSONString(wrapCat, SerializerFeature.WriteMapNullValue));

        JSONObject jsonObject = JSON.parseObject(catOneString);
        String jsonString1 = JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
        System.out.println(jsonString1);
    }
}
