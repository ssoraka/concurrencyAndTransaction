package cov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test2 {
    public static void main(String[] args) {
        List<A0> a1 = new ArrayList<>(Arrays.asList(new A0(), new B0(), new C0(), new B02(), new C02()));
        a1.add(new A0());
        List<? extends A0> a2 = new ArrayList<>(Arrays.asList(new A0(), new B0(), new C0(), new B02(), new C02()));
        //a2.add(new A0()); //нельзя
        List<? super A0> a3 = new ArrayList<>(Arrays.asList(new A0(), new B0(), new C0(), new B02(), new C02()));
        a3.add(new A0());

        //Consumer Super
        List<A0> a41 = new ArrayList<>(Arrays.asList(new A0(), new B0(), new C0(), new B02(), new C02()));
        List<? super B0> a42 = a41;

        List<? super B0> a4 = new ArrayList<>(Arrays.asList(new A0(), new B0(), new C0(), new B02(), new C02())); //?
//        a4.add(new A0()); //нельзя
        a4.add(new B0());
        a4.add(new C0());
        a4.add(new C02());

        //Producer Extends
        List<? extends B0> a5 = new ArrayList<>(Arrays.asList(/*new A0(),*/ new B0(), new C0(), /*new B02(),*/ new C02()));
//        a5.add(new C0()); //нельзя
//        a5.add(new B0()); //нельзя

    }
}

/*
A0 <- B0 <- C0
^     ^
B02    C02
 */

class A0 {}
class B0 extends A0 {}
class C0 extends B0 {}
class C02 extends B0 {}
class B02 extends A0 {}
