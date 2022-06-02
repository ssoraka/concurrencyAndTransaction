package filo;

public interface RunnableWithException {
    static void silent(RunnableWithException runner) {
        try {
            runner.run();
        } catch (Exception e) {;}
    }

    void run() throws Exception;
}
