package adt.avltree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import adt.bst.BSTNode;
import adt.bt.Util;

public class AVLCountAndFillImpl<T extends Comparable<T>> extends
		AVLTreeImpl<T> implements AVLCountAndFill<T> {

	private int LLcounter;
	private int LRcounter;
	private int RRcounter;
	private int RLcounter;

	public AVLCountAndFillImpl() {
		
	}

	@Override
	public int LLcount() {
		return LLcounter;
	}

	@Override
	public int LRcount() {
		return LRcounter;
	}

	@Override
	public int RRcount() {
		return RRcounter;
	}

	@Override
	public int RLcount() {
		return RLcounter;
	}
	
	@Override
	protected void rebalance(BSTNode<T> node) {
		if (this.calculateBalance(node) > 1) {
			this.hLeft(node);
		}else if(this.calculateBalance(node) < -1) {
			this.hRight(node);
		}
	}
	
	private void hRight(BSTNode<T> node) {
		BSTNode<T> aux;
		if (this.calculateBalance((BSTNode<T>) node.getRight()) < 0) {
			aux = Util.leftRotation(node);
			this.RRcounter++;
		}else {
			Util.rightRotation((BSTNode<T>) node.getRight());
			aux = Util.leftRotation(node);
			this.RLcounter++;
		}
		if (aux.getParent() == null) {
			this.root = aux;
		}
	}
	
	private void hLeft(BSTNode<T> node) {
		BSTNode<T> aux;
		if (this.calculateBalance((BSTNode<T>) node.getLeft()) > 0) {
			aux = Util.rightRotation(node);
			this.LLcounter++;
		}else {
			Util.leftRotation((BSTNode<T>) node.getLeft());
			aux = Util.rightRotation(node);
			this.LRcounter++;
		}
		if (aux.getParent() == null) {
			this.root = aux;
		}
	}

	@Override
	public void fillWithoutRebalance(T[] array) {
		Arrays.sort(array);
		Map<Integer, ArrayList<T>> levels = new HashMap<>();
		this.binarySearch(0, array.length - 1, 0, array, levels);
		for (ArrayList<T> l : levels.values()) {
			l.forEach(n -> this.insert(n));
		}
	}

	private void binarySearch(int left, int right, int l, T[] array, Map<Integer, ArrayList<T>> levels) {
		if (left <= right) {
			int mid = (left + right)/2;
			if (levels.containsKey(l)) {
				levels.get(l).add(array[mid]);
			}else{
				levels.put(l, new ArrayList<>());
				levels.get(l).add(array[mid]);
			}
			this.binarySearch(left, mid - 1, l + 1, array, levels);
			this.binarySearch(mid + 1, right, l + 1, array, levels);
		}
	}
}
