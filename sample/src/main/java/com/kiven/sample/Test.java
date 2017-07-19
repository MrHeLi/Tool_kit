package com.kiven.sample;

import java.util.LinkedHashMap;

/**
 * Created by Kiven on 2017/7/10.
 * Details:
 */

public class Test {
    public static void sort() {
        LinkedHashMap<Integer, String> hashMap = new LinkedHashMap<>();
        for (int i = 0; i < 5; i++) {
            hashMap.put(1, "sort : " + i);
        }

        System.out.println(hashMap.toString());


    }
}
