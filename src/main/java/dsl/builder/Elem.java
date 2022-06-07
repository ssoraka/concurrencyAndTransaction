package dsl.builder;

import java.util.List;

public class Elem {
    List<If> list;
    Else block;

    public void setList(List<If> list) {
        this.list = list;
    }

    public void add(If block) {
        list.add(block);
    }

    public void setBlock(Else block) {
        this.block = block;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("<elem>").append("\n");
        list.forEach(elem -> sb.append("   ").append(elem.toString()).append("\n"));
        if (block != null) {
            sb.append("   ").append(block.toString()).append("\n");
        }
        return sb.append("</elem>").toString();
    }
}
