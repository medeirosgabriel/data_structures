package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.RecursiveDoubleLinkedListImpl;

public class StackRecursiveDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> top;
	protected int size;

	public StackRecursiveDoubleLinkedListImpl(int size) {
		this.size = size;
		this.top = new RecursiveDoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (!this.isFull()) {
			((RecursiveDoubleLinkedListImpl<T>) this.top).insert(element);
		}else {
			throw new StackOverflowException();
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (!this.isEmpty()) {
			T top = this.top();
			((RecursiveDoubleLinkedListImpl<T>) this.top).removeLast();
			return top;
		}else {
			throw new StackUnderflowException();
		}
	}

	@Override
	public T top() {
		T[] array = this.top.toArray();
		T answer = null;
		if (array.length > 0) {
			answer = array[array.length - 1];
		}
		return answer;
	}

	@Override
	public boolean isEmpty() {
		return this.top.size() == 0;
	}

	@Override
	public boolean isFull() {
		return this.top.size() == this.size;
	}

}
