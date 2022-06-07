package dsl.builder;

class If {
    int value;
    String statement;

    public If(int value, String statement) {
        this.value = value;
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "<if value="+ value + ">" + statement + "</if>";
    }
}