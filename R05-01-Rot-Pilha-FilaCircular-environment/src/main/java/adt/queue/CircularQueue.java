package adt.queue;

public class CircularQueue<T> implements Queue<T> {

	private T[] array;
	private int tail;
	private int head;
	private int elements;

	public CircularQueue(int size) {
		array = (T[]) new Object[size];
		head = -1;
		tail = -1;
		elements = 0;
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (isEmpty()) {
			head = 0;
			tail = 0;
			array[tail] = element;
			elements ++;
		}else if (!isFull()) {
			tail = (tail + 1) % array.length;
			array[tail] = element;
			elements ++;
		}else {
			throw new QueueOverflowException();
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (!isEmpty()) {
			T aux = array[head];
			head = (head + 1) % array.length;
			elements --;
			return aux;
		}
		throw new QueueUnderflowException();
	}

	@Override
	public T head() {
		T aux = null;
		if (!isEmpty()) {
			aux = array[head];
		}
		return aux;
	}

	@Override
	public boolean isEmpty() {
		return elements == 0;
	}

	@Override
	public boolean isFull() {
		return elements == array.length;
	}

}
