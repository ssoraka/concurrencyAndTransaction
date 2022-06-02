package filo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    final static int FILO_COUNT = 10;

    public static void main(String[] args) {
        List<Object> keys = Stream.generate(() -> new Object())
                .limit(FILO_COUNT)
                .collect(Collectors.toList());
        keys.add(keys.get(0));

        IntStream.iterate(0, i -> i + 1)
                .limit(FILO_COUNT)
                .mapToObj(i -> start(new Filo("f" + i, keys.get(i), keys.get(i + 1))))
                .collect(Collectors.toList())
                .forEach(elem -> RunnableWithException.silent(elem::join));
    }

    static Thread start(Filo f) {
        Thread thread = new Thread(f);
        thread.setName(f.getName());
        thread.setDaemon(true);
        thread.start();
        return thread;
    }
}
