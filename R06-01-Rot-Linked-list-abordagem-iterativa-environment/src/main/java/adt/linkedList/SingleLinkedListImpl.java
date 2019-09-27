package adt.linkedList;

import java.util.ArrayList;
import java.util.List;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return this.head.isNIL();
	}

	@Override
	public int size() {
		SingleLinkedListNode<T> atual = this.head;
		int size = 0;
		while (!atual.isNIL()) {
			size ++;
			atual = atual.getNext();
		}
		return size;
	}

	@Override
	public T search(T element) {
		SingleLinkedListNode<T> atual = this.head;
		T ret = null;
		while (!atual.isNIL() && ret == null) {
			if (atual.getData().equals(element)) {
				ret = atual.getData();
			}
			atual = atual.getNext();
		}
		return ret;
	}

	@Override
	public void insert(T element) {
		SingleLinkedListNode<T> atual = this.head;
		while (!atual.isNIL()) {
			atual = atual.getNext();
		}
		atual.setData(element);
		atual.setNext(new SingleLinkedListNode<T>(null, new SingleLinkedListNode<T>()));
	}

	@Override
	public void remove(T element) {
		SingleLinkedListNode<T> previous = null;
		SingleLinkedListNode<T> atual = this.head;
		boolean flag = true;
		while (!atual.isNIL() && flag) {
			if (atual.getData().equals(element)) {
				if (previous != null) {
					previous.setNext(atual.getNext());
				}else{
					this.setHead(atual.getNext());
				}
				flag = false;
			}else {
				previous = atual;
				atual = atual.getNext();
			}
		}
	}

	@Override
	public T[] toArray() {
		List<T> list = new ArrayList<>();
		SingleLinkedListNode<T> atual = this.head;
		while (!atual.isNIL()) {
			list.add(atual.getData());
			atual = atual.getNext();
		}
		return (T[]) list.toArray();
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}
}
