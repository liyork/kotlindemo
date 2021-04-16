package com.wolf.kotlin.shizhan.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created on 2021/4/15 8:59 AM
 *
 * @author 李超
 * @version 0.0.1
 */
public class GenericTest {

    public static void main(String[] args) {
        //testCovariation();

        //testContravariant();

        // PECS（Producer-Extends,Consumer Super），对于生产数据用extends，对于消费用super
        // 在java.util.Collections的copy()方法中（JDK1.7）诠释了PECS
        // 对于dest，ListIterator<? super T> di=dest.listIterator(),用<? super T?，in T，写入dest数据
        // 对于src，ListIterator<? extends T> si=src.listIterator(),用<? extends T>，out T,读取src数据
    }

    private static void testContravariant() {
        // 2.逆变
        // 子类型C是“? super Number”，父类型F是Number的父类型（如Object类）
        List<? super Number> list = new ArrayList<>();
        List<? super Number> list3 = new ArrayList<Number>();
        List<? super Number> list4 = new ArrayList<Object>();
        list3.add(new Integer(3));// 可以添加Integer类型元素
        list4.add(new Integer(4));
        // 在逆变类型中， 我们可以向其中添加元素
    }

    private static void testCovariation() {
        // 1.协变
        // 子类型C是Number类及其子类，表示Number类或其子类。父类F就是上界通配符?extends Number
        List<? extends Number> list = new ArrayList<>();
        List<? extends Number> list1 = new ArrayList<Integer>();
        List<? extends Number> list2 = new ArrayList<Float>();

        // List<Integer>可以添加Integer及其子类；
        // List<Float>可以添加Float及其子类；
        // List<Integer>、 List<Float>等都是List<?extends Number>的子类型
        // list1/list2不能添加除null外的对象
        list1.add(null);
        list2.add(null);
        //list1.add(new Integer(1));
        //list1.add(new Float(1.1f));

        // 因为，如果能将Float的子类添加到List<?extends Number>中，也能将Integer的子类添加到List<?extends Number>中，
        // 那么List<? extends Number>里面将会持有各种Number子类型的对象（如Byte、Integer、 Float、 Double等）
        // 当再使用这个List的时候， 元素的类型就会混乱， 我们不知道哪个元素是Integer或者Float。
        //Java为了保护其类型一致， 禁止向List<? extends Number>添加任意Number子类型的对象，不过可以添加空对象null
    }
}
