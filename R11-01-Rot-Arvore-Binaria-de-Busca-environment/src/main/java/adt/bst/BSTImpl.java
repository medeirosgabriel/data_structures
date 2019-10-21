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
		int ret = 0;
		if (!this.root.isEmpty()) {
			return height(this.root);
		}
		return ret;
	}
	
	private int height(BTNode<T> node) {
		int ret = 0;
		if (!node.isEmpty()) {
			ret = 1 + Math.max(height(node.getLeft()), height(node.getRight()));
		}
		return ret;
	}

	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> node = new BSTNode<>();
		if (!this.root.isEmpty()) {
			node = this.search(this.root, element);
		}
		return node;
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {
		BSTNode<T> ret = new BSTNode<T>();
		if (!node.isEmpty()) {
			if (element.compareTo(node.getData()) == 0) {
				ret = node;
			}else if (element.compareTo(node.getData()) < 0) {
				ret = search((BSTNode<T>) node.getLeft(), element);
			}else {
				ret = search((BSTNode<T>) node.getRight(), element); 
			}
		}
		return ret;
	}

	@Override
	public void insert(T element) {
		if (!this.root.isEmpty()) {
			insert(this.root, element);
		}else {
			this.root.setData(element);
		}
	}

	private void insert(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<>());
			node.setRight(new BSTNode<>());
		}else if (element.compareTo(node.getData()) <= 0) {
			insert((BSTNode<T>) node.getLeft(), element);
		}else {
			insert((BSTNode<T>) node.getRight(), element);
		}
	}

	@Override
	public BSTNode<T> maximum() {
		BSTNode<T> ret = null;
		if (!this.root.isEmpty()) {
			ret = maximum(this.root);
		}
		return ret;
	}
	
	private BSTNode<T> maximum(BSTNode<T> node) {
		BSTNode<T> ret = null;
		if (node.isLeaf() || node.getRight().isEmpty()) {
			ret = node;
		}else {
			ret = maximum((BSTNode<T>) node.getRight());
		}
		return ret;
	}

	@Override
	public BSTNode<T> minimum() {
		BSTNode<T> ret = null;
		if (!this.root.isEmpty()) {
			ret = minimum(this.root);
		}
		return ret;
	}

	private BSTNode<T> minimum(BSTNode<T> node) {
		BSTNode<T> ret = null;
		if (node.isLeaf() || node.getLeft().isEmpty()) {
			ret = node;
		}else {
			ret = maximum((BSTNode<T>) node.getLeft());
		}
		return ret;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	private BSTNode<T> sucessor(BSTNode<T> node) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}
	
	private BSTNode<T> predecessor(BSTNode<T> node) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public void remove(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] preOrder() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] order() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] postOrder() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
