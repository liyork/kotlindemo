package com.wolf.kotlin.consttest;

public class JavaTest {

    public static void main(String[] args) {
        //top-level属性，单利对象的属性，类的伴随对象的属性，在没有加const修饰时应该如此调用
        System.out.println(ConstTest.getAge1());
        System.out.println(test1.INSTANCE.getName1());
        System.out.println(Person.Companion.getSex1());

        //加了const修饰之后的调用
        System.out.println(ConstTest.age);
        System.out.println(test1.name);
        System.out.println(Person.sex);

    }

}