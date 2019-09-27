package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements
		DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;
	
	public DoubleLinkedListImpl () {
		this.setHead(new DoubleLinkedListNode<>());
		this.setLast((DoubleLinkedListNode<T>) this.head);
	}
	
	@Override
	public void insert(T element) {
		DoubleLinkedListNode<T> newNode = new DoubleLinkedListNode<T>(element, new DoubleLinkedListNode<T>(), new DoubleLinkedListNode<T>());
		if (this.head.isNIL()) {
			newNode.setNext(this.last);
			this.setHead(newNode);
			this.last.setNext(new SingleLinkedListNode<T>());
		}else {
			newNode.setPrevious(this.last);
			this.getLast().setNext(newNode);
		}
		this.setLast(newNode);
	}
	
	@Override
	public void remove(T element) {
		if (this.head.getData().equals(element)) {
			this.removeFirst();
		}else {
			DoubleLinkedListNode<T> atual = (DoubleLinkedListNode<T>) this.head;
			while (!atual.isNIL() && !atual.getData().equals(element)) {
				atual = (DoubleLinkedListNode<T>) atual.getNext();
			}
			if (!atual.isNIL()) {
				atual.getPrevious().setNext(atual.getNext());
				((DoubleLinkedListNode<T>) atual.getNext()).setPrevious(atual.getPrevious());
			}
		}
	}

	@Override
	public void insertFirst(T element) {
		DoubleLinkedListNode<T> newHead = new DoubleLinkedListNode<T>(element, (DoubleLinkedListNode<T>) this.head, new DoubleLinkedListNode<T>());
		((DoubleLinkedListNode<T>) this.head).setPrevious(newHead);
		if (this.isEmpty()) {
			this.setLast(newHead);
		}
		this.setHead(newHead);
	}

	@Override
	public void removeFirst() {
		if (!this.head.isNIL()) {
			this.head = this.head.getNext();
			if (this.head.isNIL()) {
				this.last = (DoubleLinkedListNode<T>) this.head;
			}
			((DoubleLinkedListNode<T>) this.head).setPrevious(new DoubleLinkedListNode<>());
		}
	}

	@Override
	public void removeLast() {
		if (!isEmpty()) {
			this.last = this.last.getPrevious();
			if (this.last.isNIL()) {
				this.head = this.last;
			}
			this.last.setNext(new DoubleLinkedListNode<T>());
		}
	}

	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}
}
