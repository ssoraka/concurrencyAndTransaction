package dsl.builder;

class Else {
    String statement;

    public Else(String statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "<else>" + statement + "</else>";
    }
}
