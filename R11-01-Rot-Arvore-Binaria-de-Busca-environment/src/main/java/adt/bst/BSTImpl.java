package adt.bst;

import adt.bt.BTNode;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}

	private int height(BTNode<T> node) {
		int height = -1;
		if (!node.isEmpty()) {
			height = 1 + Math.max(height(node.getLeft()), height(node.getRight()));
		}
		return height;
	}

	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> node = new BSTNode<T>();
		if (element != null) {
			if (!this.isEmpty()) {
				node = search(this.root, element);
			}
		}
		return node;
	}
	
	private BSTNode<T> search(BSTNode<T> node, T element) {
		BSTNode<T> aux = new BSTNode<T>();
		if (!node.isEmpty()) {
			if (element.compareTo(node.getData()) < 0) {
				aux = search((BSTNode<T>) node.getLeft(), element);
			}else if (element.compareTo(node.getData()) > 0) {
				aux = search((BSTNode<T>) node.getRight(), element);
			}else{
				aux = node;
			}
		}
		return aux;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			insert(this.root, element);
		}
	}

	private void insert(BTNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode.Builder<T>().data(null).left(null).right(null).parent(node).build());
			node.setRight(new BSTNode.Builder<T>().data(null).left(null).right(null).parent(node).build());
		}else if (element.compareTo(node.getData()) > 0) {
			insert(node.getRight(), element);
		}else {
			insert(node.getLeft(), element);
		}
	}

	@Override
	public BSTNode<T> maximum() {
		BSTNode<T> max = null;
		if (!this.isEmpty()) {
			max = maximum(this.root);
		}
		return max;
	}

	private BSTNode<T> maximum(BSTNode<T> node) {
		BSTNode<T> aux = node;
		if (!node.getRight().isEmpty()) {
			aux = maximum((BSTNode<T>) node.getRight());
		}
		return aux;
	}

	@Override
	public BSTNode<T> minimum() {
		BSTNode<T> min = null;
		if (!this.isEmpty()) {
			min = minimum(this.root);
		}
		return min;
	}

	private BSTNode<T> minimum(BSTNode<T> node) {
		BSTNode<T> aux = node;
		if (!node.getLeft().isEmpty()) {
			aux = minimum((BSTNode<T>) node.getLeft());
		}
		return aux;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = this.search(element);
		if (element != null && !this.isEmpty() && !node.isEmpty()) {
			if (!node.getRight().isEmpty()) {
				node = minimum((BSTNode<T>) node.getRight());
			} else {
				node = sucessor(node);
			}
		}else {
			node = null;
		}
		return node;
	}

	private BSTNode<T> sucessor(BSTNode<T> node) {
		BSTNode<T> sucessor = (BSTNode<T>) node.getParent();
		if (node.getParent() != null) {
			if (!sucessor.isEmpty() && sucessor.getRight().equals(node)) {
				sucessor = sucessor((BSTNode<T>) node.getParent());
			}
		}
		return sucessor;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = this.search(element);
		if (element != null && !this.isEmpty() && !node.isEmpty()) {
			if (!node.getLeft().isEmpty()) {
				node = maximum((BSTNode<T>) node.getLeft());
			}else {
				node = predecessor(node);					
			}
		}else {
			node = null;
		}
		return node;
	}

	private BSTNode<T> predecessor(BSTNode<T> node) {
		BSTNode<T> predecessor = (BSTNode<T>) node.getParent();
		if (node.getParent() != null) {
			if (!predecessor.isEmpty() && predecessor.getLeft().equals(node)) {
				predecessor = predecessor((BSTNode<T>) node.getParent());
			}
		}
		return predecessor;
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);
		if (element != null && !node.isEmpty()) {
			 if (node.isLeaf()) {
				 node.setData(null);
				 node.setLeft(null);
				 node.setRight(null);
			 }else if(node.getLeft().isEmpty() || node.getRight().isEmpty()) {
				 BSTNode<T> parent = (BSTNode<T>) node.getParent();
				 if (parent != null) {
					 if (parent.getLeft().equals(node)) {
						 if (!node.getLeft().isEmpty()) {
							 parent.setLeft(node.getLeft());
							 node.getLeft().setParent(parent);
						 }else {
							 parent.setLeft(node.getRight());
							 node.getRight().setParent(parent);
						 }
					 }else {
						 if (!node.getLeft().isEmpty()) {
							 parent.setRight(node.getLeft());
							 node.getLeft().setParent(parent);
						 }else {
							 parent.setRight(node.getRight());
							 node.getRight().setParent(parent);
						 }
					 }
				 }else {
					 if (!node.getLeft().isEmpty()) {
						 this.root = (BSTNode<T>) node.getLeft();
					 }else{
						 this.root = (BSTNode<T>) node.getRight();
					 }
					 this.root.setParent(null);
				 }
			 }else {
				 T sucessor = sucessor(node.getData()).getData();
				 remove(sucessor);
				 node.setData(sucessor);
			 }
		}
	}

	@Override
	public T[] preOrder() {
		T[] array = (T[]) new Comparable[this.size()];
		preOrder(this.root, 0, array);
		return array;
	}

	private void preOrder(BSTNode<T> node, int i, T[] array) {
		if (!node.isEmpty()) {
			array[i] = node.getData();
			preOrder((BSTNode<T>) node.getLeft(), i + 1, array);
			int sizeLeft = size((BSTNode<T>) node.getLeft());
			preOrder((BSTNode<T>) node.getRight(), sizeLeft + i + 1, array);
		}
	}

	@Override
	public T[] order() {
		T[] array = (T[]) new Comparable[this.size()];
		order(this.root, 0, array);
		return array;
	}

	private int order(BSTNode<T> node, int i, T[] array) {
		if (!node.isEmpty()) {
			i = order((BSTNode<T>) node.getLeft(), i, array);
			array[i++] = node.getData();
			i = order((BSTNode<T>) node.getRight(), i, array);
		}
		return i;
	}

	@Override
	public T[] postOrder() {
		T[] array = (T[]) new Comparable[this.size()];
		postOrder(this.root, 0, array);
		return array;
	}

	private int postOrder(BSTNode<T> node, int i, T[] array) {
		if (!node.isEmpty()) {
			i = postOrder((BSTNode<T>) node.getLeft(), i, array);
			i = postOrder((BSTNode<T>) node.getRight(), i, array);
			array[i++] = node.getData();
		}
		return i;
	}

	/**
	 * This method is already implemented using recursion. You must understand how
	 * it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}
}
