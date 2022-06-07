package dsl;

import dsl.builder.Elem;
import dsl.builder.ElemBuilder;

public class Main {
    public static void main(String[] args) {
        Elem elem = new ElemBuilder()
                .startIfBlock()
                    .addIf(1, "one")
                    .addIf(2, "two")
                    .addIf(3, "three")
                    .addIf(4).statement("four")
                    .addIf(5).statement("five")
                    .addIf(6).statement("six")
                .endIfBlock()
                .setElse("default")
                .build();

        System.out.println(elem);

        Elem elem2 = new ElemBuilder()
                .startIfBlock()
                    .addIf(1, "one")
                    .addIf(2, "two")
                    .addIf(3, "three")
                .endIfBlock()
                .build();

        System.out.println(elem2);
    }
}
