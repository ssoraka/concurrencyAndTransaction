package test;

import java.util.Arrays;

public class Test4 {
    public static void main(String[] args) {
        System.out.println("void class");
        Class classVoid = void.class;
        Arrays.stream(classVoid.getDeclaredMethods()).forEach(m -> System.out.println(m.getName()));
        Arrays.stream(classVoid.getGenericInterfaces()).forEach(m -> System.out.println(m.getTypeName()));
        Arrays.stream(classVoid.getMethods()).forEach(m -> System.out.println(m.getName()));
        Arrays.stream(classVoid.getFields()).forEach(m -> System.out.println(m.getName()));

        Arrays.stream(classVoid.getDeclaredFields()).forEach(m -> System.out.println(m.getName()));
        System.out.println(classVoid.getCanonicalName());
        System.out.println(classVoid.getSuperclass());
        System.out.println(classVoid.isPrimitive());

        System.out.println();
        System.out.println("int[][][][][][] class");
        Class classInt = int[][][][][][].class;
        Arrays.stream(classInt.getDeclaredMethods()).forEach(m -> System.out.println(m.getName()));
        Arrays.stream(classInt.getGenericInterfaces()).forEach(m -> System.out.println(m.getTypeName()));
        Arrays.stream(classInt.getMethods()).forEach(m -> System.out.println(m.getName()));
        Arrays.stream(classInt.getFields()).forEach(m -> System.out.println(m.getName()));

        Arrays.stream(classInt.getDeclaredFields()).forEach(m -> System.out.println(m.getName()));
        System.out.println(classInt.getCanonicalName());
        System.out.println(classInt.getSuperclass());
        System.out.println(classInt.isPrimitive());

        Class classInt2 = int[][][][][][].class;
        System.out.println();
        System.out.println(classInt != classInt2);
    }
}
