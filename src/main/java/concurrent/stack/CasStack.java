package concurrent.stack;

import java.util.concurrent.atomic.AtomicReference;

public class CasStack<T> {
    AtomicReference<Node<T>> head;

    public void push(T value) {
        Node<T> newHead = new Node<>(value, null);

        while (true) {
            Node<T> node = head.get();
            newHead.setNext(node);
            if (head.compareAndSet(node, newHead)) {
                return;
            }
        }
    }

    public T pop() {
        while (true) {
            Node<T> node = head.get();
            if (node == null) return null;

            if (head.compareAndSet(node, node.getNext())) {
                return node.getValue();
            }
        }
    }
}
