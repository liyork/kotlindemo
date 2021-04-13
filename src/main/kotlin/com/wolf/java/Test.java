package com.wolf.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description:
 * Created on 2021/3/12 9:13 AM
 *
 * @author 李超
 * @version 0.0.1
 */
public class Test {

    public static void main(String[] args) {
        List<String> objects = new ArrayList<>();
        System.out.println(objects instanceof List);
        System.out.println(objects instanceof ArrayList);

        new Thread(() -> {
            System.out.println(111);
        }).start();

        // kotlin和groovy一样，而java和他们还是不太一样，他们多了一个{}
        HashMap<Integer, String> map = new HashMap<>();
        map.computeIfAbsent(1, t -> t + "111");
        map.computeIfAbsent(1, t -> t + "111");
        System.out.println(map);
    }

    public void setMyInterface(MySamInterface listener) {

    }
}
