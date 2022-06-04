package concurrent.stack;

public class SimpleStack<T> {
    Node<T> head;

    public void push(T newElem) {
        head = new Node<>(newElem, head);
    }

    public T pop() {
        if (head == null) return null;

        Node<T> answer = head;
        head = head.getNext();
        return head.getValue();
    }
}