package adt.skipList;

import java.util.ArrayList;
import java.util.List;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int maxHeight;

	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		for (int i = 0; i < maxHeight; i++) {
			root.forward[i] = NIL;
		}
	}

	
	@Override
	public void insert(int key, T newValue, int height) {
		SkipListNode<T> node = this.root;
		SkipListNode<T>[] aux = new SkipListNode[this.maxHeight];
		for (int i = this.maxHeight - 1; i > -1; i--) {
			while (node.getForward(i).getValue() != null && node.getForward(i).getKey() < key) { 
				node = node.getForward(i);
			}
			aux[i] = node;
		}
		node = node.getForward(0);
		if (node.getKey() == key) {
			node.setValue(newValue);
		}else{
			if (height > this.maxHeight) {
				throw new RuntimeException();
			}else{
				SkipListNode<T> newNode = new SkipListNode<T>(key, height, newValue);
				for (int i = 0; i < height; i++) {
					newNode.getForward()[i] = aux[i].getForward(i);
					aux[i].getForward()[i] = newNode;
				}
			}
		}
		
	}

	@Override
	public void remove(int key) {
		SkipListNode<T> node = this.root;
		SkipListNode<T>[] aux = new SkipListNode[this.maxHeight];
		for (int i = this.maxHeight - 1; i > -1; i--) {
			while (node.getForward(i).getValue() != null && node.getForward(i).getKey() < key) { 
				node = node.getForward(i);
			}
			aux[i] = node;
		}
		node = node.getForward(0);
		if (node.getKey() == key) {
			for (int i = 0; i < this.maxHeight; i++) {
				if (aux[i].getForward(i).equals(node)) {
					aux[i].getForward()[i] = node.getForward(i);
				}
			}
		}
	}

	@Override
	public int height() {
		return this.maxHeight;
	}

	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> node = this.root;
		SkipListNode<T>[] aux = new SkipListNode[this.maxHeight];
		for (int i = this.maxHeight - 1; i > -1; i--) {
			while (node.getForward(i).getValue() != null && node.getForward(i).getKey() < key) { 
				node = node.getForward(i);
			}
			aux[i] = node;
		}
		node = node.getForward(0);
		if (node.getKey() != key) {
			node = null;
		}
		return node;
	}

	@Override
	public int size() {
		SkipListNode<T> node = this.root.getForward(0);
		int size = 0;
		while (node.getValue() != null) {
			node = node.getForward(0);
			size++;
		}
		return size;
	}

	@Override
	public SkipListNode<T>[] toArray() {
		List<SkipListNode<T>> list = new ArrayList<>();
		SkipListNode<T> node = this.root;
		while (node.getKey() < Integer.MAX_VALUE) {
			list.add(node);
			node = node.getForward(0);
		}
		list.add(node);
		SkipListNode<T>[] array = new SkipListNode[list.size()];
		return list.toArray(array);
	}
}
