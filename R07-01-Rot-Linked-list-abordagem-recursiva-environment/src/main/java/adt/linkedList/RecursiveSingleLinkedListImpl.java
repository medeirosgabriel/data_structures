package adt.linkedList;

import java.util.ArrayList;
import java.util.List;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {

	protected T data;
	protected RecursiveSingleLinkedListImpl<T> next;

	public RecursiveSingleLinkedListImpl() {
		
	}

	@Override
	public boolean isEmpty() {
		return this.data == null;
	}

	@Override
	public int size() {
		int ret;
		if (this.isEmpty()) {
			ret = 0;
		}else {
			ret = 1 + this.getNext().size();
		}
		return ret;
	}

	@Override
	public T search(T element) {
		if (this.isEmpty() || this.getData().equals(element)) {
			return this.getData();
		}else{
			return this.getNext().search(element);
		}
	}

	@Override
	public void insert(T element) {
		if (this.isEmpty()) {
			this.setData(element);
			this.setNext(new RecursiveSingleLinkedListImpl<>());
		}else {
			this.getNext().insert(element);
		}
	}

	@Override
	public void remove(T element) {
		if (!this.isEmpty()) {
			if (this.getData().equals(element)) {
				this.setData(this.getNext().getData());
				this.setNext(this.getNext().getNext());
				if (this.getNext() == null) {
					this.setNext(new RecursiveSingleLinkedListImpl<>());
				}
			}else {
				this.getNext().remove(element);
			}
		}
	}

	@Override
	public T[] toArray() {
		List<T> list = new ArrayList<>();
		this.auxToArray(list);
		return (T[]) list.toArray();
	}
	
	public void auxToArray(List<T> list) {
		if (!this.isEmpty()) {
			list.add(this.getData());
			this.getNext().auxToArray(list);
		}
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RecursiveSingleLinkedListImpl<T> getNext() {
		return next;
	}

	public void setNext(RecursiveSingleLinkedListImpl<T> next) {
		this.next = next;
	}

}
