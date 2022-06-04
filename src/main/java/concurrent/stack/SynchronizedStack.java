package concurrent.stack;

public class SynchronizedStack<T>{
    Node<T> head;
    Object mutex = new Object();

    public void push(T newElem) {
        synchronized (mutex) {
            head = new Node<>(newElem, head);
        }
    }

    public T pop() {
        synchronized (mutex) {
            if (head == null) return null;

            Node<T> answer = head;
            head = head.getNext();
            return head.getValue();
        }
    }
}
