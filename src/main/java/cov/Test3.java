package cov;

import java.util.Arrays;
import java.util.List;

public class Test3 {
    public static void main(String[] args) {

        List<C2> listC2 = Arrays.asList(new C2(){}, new D1(){}, new D2(){});

        List<? extends C2> listExC2 = listC2; //присваивать можно только наследников
//        listExC2.add(new C2(){}); //нельзя добавлять


        List<Object> listC22 = Arrays.asList(new C2(){}, new B2(){}, new B3(){}, new Object());

        List<? super C2> listSuC2 = listC22; //присвоить можно родителей класса

        listSuC2.add(new C2(){}); //добавлять наследников
        listSuC2.add(new D1(){});
        listSuC2.add(new D2(){});
        listSuC2.add(new E1(){});
        listSuC2.add(new E2(){});
        listSuC2.add(new E3(){});
        listSuC2.add(new E4(){});
    }
}

interface A1{}
interface A2{}
interface A3{}
interface A4{}

interface B1 extends A1{}
interface B2 extends A1,A2{}
interface B3 extends A3,A4{}
interface B4 extends A4{}

interface C1 extends B2{}
interface C2 extends B2,B3{}
interface C3 extends B3{}

interface D1 extends C1,C2{}
interface D2 extends C2{}

interface E1 extends D1{}
interface E2 extends D1{}
interface E3 extends D2{}
interface E4 extends D2{}