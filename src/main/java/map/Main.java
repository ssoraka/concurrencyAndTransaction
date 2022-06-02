package map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<Test> hashSet = new HashSet<>();

        hashSet.add(new Test("1"));
        hashSet.add(new Test("2"));
        hashSet.add(new Test("3"));
        hashSet.add(new Test("4"));
        hashSet.add(new Test("5"));
        hashSet.add(new Test("6"));
        hashSet.add(new Test("7"));
        hashSet.add(new Test("8"));
        hashSet.add(new Test("9"));
        hashSet.add(new Test("10"));
        hashSet.add(new Test("11"));


        Set<Test> hashSet2 = new HashSet<>();

        hashSet2.add(new Test("5"));
        hashSet2.add(new Test("4"));
        hashSet2.add(new Test("3"));
        hashSet2.add(new Test("2"));
        hashSet2.add(new Test("1"));

        System.out.println("dssa");
    }


}
class Test {
    String name;

    public Test(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return 4;
    }

    @Override
    public String toString() {
        return name;
    }
}