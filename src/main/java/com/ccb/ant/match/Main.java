package com.ccb.ant.match;

import org.springframework.util.AntPathMatcher;

public class Main {
    public static void main(String[] args) {
        AntPathMatcher matcher = new AntPathMatcher();
        String path = "/?productName=apigatewaynext#/apis/list**";
        String url = "https://one.console.tbres.telkomsigma.co.id/?productName=apigatewaynext#/apis/list?searchBy=ApiName";
        System.out.println(matcher.match("**" + path, url));
    }


}
