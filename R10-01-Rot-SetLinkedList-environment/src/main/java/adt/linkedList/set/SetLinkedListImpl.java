package adt.linkedList.set;

import adt.linkedList.SingleLinkedListImpl;
import adt.linkedList.SingleLinkedListNode;

public class SetLinkedListImpl<T> extends SingleLinkedListImpl<T> implements SetLinkedList<T> {

	@Override
	public void removeDuplicates() {
		SingleLinkedListNode<T> atual = this.head;
		while (!atual.isNIL()) {
			SingleLinkedListNode<T> aux = atual.getNext();
			while (!aux.isNIL()) {
				boolean changed = false;
				if (aux.getData().equals(atual.getData())) {
					aux.setData(aux.getNext().getData());
					aux.setNext(aux.getNext().getNext());
					if (aux.getNext() == null) {
						aux.setNext(new SingleLinkedListNode<>());
					}
					changed = true;
				}
				if (!changed) {
					aux = aux.getNext();
				}
			}
			atual = atual.getNext();
		}
	}

	@Override
	public SetLinkedList<T> union(SetLinkedList<T> otherSet) {
		SetLinkedListImpl<T> output = new SetLinkedListImpl();
		SingleLinkedListNode<T> aux = this.getHead();
		while (!aux.isNIL()) {
			output.insert(aux.getData());
			aux = aux.getNext();
		}
		aux = ((SingleLinkedListImpl<T>) otherSet).getHead();
		while (!aux.isNIL()) {
			output.insert(aux.getData());
			aux = aux.getNext();
		}
		output.removeDuplicates();
		return output;
	}

	@Override
	public SetLinkedList<T> intersection(SetLinkedList<T> otherSet) {
		SetLinkedListImpl<T> output = new SetLinkedListImpl<T>();
		SingleLinkedListNode<T> aux = this.getHead();
		while (!aux.isNIL()) {
			if (otherSet.search(aux.getData()) != null) {
				output.insert(aux.getData());
			}
			aux = aux.getNext();
		}
		output.removeDuplicates();
		return output;
	}

	@Override
	public void concatenate(SetLinkedList<T> otherSet) {
		SingleLinkedListNode<T> atual = this.getHead();
		while (!atual.isNIL()) {
			atual = atual.getNext();
		}
		atual.setData(((SingleLinkedListImpl<T>) otherSet).getHead().getData());
		atual.setNext(((SingleLinkedListImpl<T>) otherSet).getHead().getNext());
		this.removeDuplicates();
	}
}
