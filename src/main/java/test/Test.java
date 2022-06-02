package test;

public class Test {

    public static void main(String[] args) {
        A a = new C();
        System.out.println(a.random());
    }

    static class A {
        public int random() {
            return 1;
        }

        public int test() {
            return 4;
        }
    }

    static class B extends A implements I2 {
        public int random() {
            return 4;
        }
    }

    static class C extends B implements I {

        @Override
        public int random2() {
            return super.random2();
        }
    }

    interface I {
        int random2();
    }

    interface I2 {
        default int random2() {
            return 5;
        };
    }

}
