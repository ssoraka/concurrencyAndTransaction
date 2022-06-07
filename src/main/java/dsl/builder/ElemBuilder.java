package dsl.builder;


import java.util.ArrayList;
import java.util.List;

/*
<?xml version="1.0" ?>
<elem>
    <if value="1">
        <statement>text1<\statement>
    <\if>
    <if value="2">
        <statement>text2<\statement>
    <\if>
    <else>
        <statement>default<\statement>
    <\else>
<\elem>

 */


public class ElemBuilder {
    Elem elem;

    public ElemBuilder() {
        this.elem = new Elem();
    }

    public ElemBuilder.IfBuilder startIfBlock() {
        return new IfBuilder(this);
    }

    private ElemBuilder setList(List<If> list) {
        elem.setList(list);
        return this;
    }

    private ElemBuilder setElse(Else elseBlock) {
        elem.setBlock(elseBlock);
        return this;
    }

    private Elem build() {
        return elem;
    }

    public static class IfBuilder {
        ElemBuilder from;
        List<If> list = new ArrayList<>();

        public IfBuilder(ElemBuilder from) {
            this.from = from;
        }

        public ElemBuilder.IfBuilder addIf(int value, String statement) {
            list.add(new If(value, statement));
            return this;
        }

        public ElemBuilder.ElseBuilder endIfBlock() {
            return new ElseBuilder(from.setList(list));
        }


        public ElemBuilder.IfBuilder2 addIf(int value) {
            return new IfBuilder2(this, value);
        }

        ElemBuilder.IfBuilder addIf(If ifValue) {
            list.add(ifValue);
            return this;
        }
    }

    public static class IfBuilder2 {
        IfBuilder from;
        int value;

        public IfBuilder2(IfBuilder from, int value) {
            this.from = from;
            this.value = value;
        }

        public ElemBuilder.IfBuilder statement(String str) {
            from.addIf(new If(value, str));
            return from;
        }
    }

    public static class ElseBuilder {
        ElemBuilder from;

        public ElseBuilder(ElemBuilder from) {
            this.from = from;
        }

        public ElemBuilder.Builder setElse(String statement) {
            return new Builder(from.setElse(new Else(statement)));
        }

        public Elem build() {
            return from.build();
        }
    }

    public static class Builder {
        ElemBuilder from;

        public Builder(ElemBuilder from) {
            this.from = from;
        }

        public Elem build() {
            return from.build();
        }
    }
}
