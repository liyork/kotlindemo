package com.wolf.kotlin.inaction.java;

import com.wolf.kotlin.inaction.Person1;
import strings.JoinKt;

import java.util.Arrays;

/**
 * Description:
 * Created on 2021/4/20 9:24 AM
 *
 * @author 李超
 * @version 0.0.1
 */
public class ClassForTestInJava {
    public static void main(String[] args) {
        //testUsingClassInKotlin();

        testForceCast();

        testJoinToStringInjava(args);
    }

    private static void testJoinToStringInjava(String[] args) {
        JoinKt.joinToString(Arrays.asList(args), ", ", "", "");
    }

    private static void testForceCast() {
        Object p = new Person1("bob", true);
        Person1 pp = null;
        if (p instanceof Person1) {
            pp = (Person1) p;
        }
    }

    private static void testUsingClassInKotlin() {
        Person1 person = new Person1("bob", true);
        System.out.println(person.getName());
        System.out.println(person.isMarried());
    }
}
