package adt.bst;

import java.util.ArrayList;

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
		return heightNode(this.root);
	}

	private int heightNode(BTNode<T> node) {
		int height = -1;
		if (!node.isEmpty()) {
			height = 1 + Math.max(heightNode(node.getLeft()), heightNode(node.getRight()));
		}

		return height;
	}

	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> result = new BSTNode<>();
		if (!this.isEmpty()) {
			result = searchNode(this.root, element);
		}
		return result;
	}

	private BSTNode<T> searchNode(BSTNode<T> node, T element) {
		BSTNode<T> aux = new BSTNode<>();
		if (!node.isEmpty()) {
			if (node.getData().equals(element)) {
				aux = node;
			} else if (element.compareTo(node.getData()) < 0) {
				aux = searchNode((BSTNode<T>) node.getLeft(), element);
			} else {
				aux = searchNode((BSTNode<T>) node.getRight(), element);
			}
		}
		return aux;
	}

	@Override
	public void insert(T element) {
		insertNode(this.root, element);
	}

	private void insertNode(BTNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode.Builder<T>().data(null).left(null).right(null).parent(node).build());
			node.setRight(new BSTNode.Builder<T>().data(null).left(null).right(null).parent(node).build());
		} else if (element.compareTo(node.getData()) < 0) {
			insertNode(node.getLeft(), element);
		} else {
			insertNode(node.getRight(), element);
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
		BSTNode<T> max = node;
		if (!node.getRight().isEmpty()) {
			max = maximum((BSTNode<T>) node.getRight());
		}
		return max;
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
		BSTNode<T> min = node;
		if (!node.getLeft().isEmpty()) {
			min = minimum((BSTNode<T>) node.getLeft());
		}
		return min;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = this.search(element);
		if (!node.isEmpty()) {
			if (!node.getRight().isEmpty()) {
				node = minimum((BSTNode<T>) node.getRight());
			} else {
				node = sucessor(node);
			}
		} else {
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
		if (!node.isEmpty()) {
			if (!node.getLeft().isEmpty()) {
				node = maximum((BSTNode<T>) node.getLeft());
			} else {
				node = predecessor(node);
			}
		} else {
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
		if (!node.isEmpty()) {
			if (node.isLeaf()) {
				node.setData(null);
				node.setRight(null);
				node.setLeft(null);
			} else if (node.getRight().isEmpty() || node.getLeft().isEmpty()) {
				BSTNode<T> parent = (BSTNode<T>) node.getParent();
				if (parent != null) {
					if (parent.getRight().equals(node)) {
						if (!node.getLeft().isEmpty()) {
							parent.setRight(node.getLeft());
							node.getLeft().setParent(parent);
						} else {
							parent.setRight(node.getRight());
							node.getRight().setParent(parent);
						}
					} else {
						if (!node.getLeft().isEmpty()) {
							parent.setLeft(node.getLeft());
							node.getLeft().setParent(parent);
						} else {
							parent.setLeft(node.getRight());
							node.getRight().setParent(parent);
						}
					}
				} else {
					if (node.getLeft().isEmpty()) {
						this.root = (BSTNode<T>) node.getRight();
					} else {
						this.root = (BSTNode<T>) node.getLeft();
					}
					this.root.setParent(null);
				}
			} else {
				T sucessor = sucessor(node.getData()).getData();
				remove(sucessor);
				node.setData(sucessor);
			}
		}
	}

	@Override
	public T[] preOrder() {
		T[] array = (T[]) new Comparable[this.size()];
		return (T[]) preOrder(this.getRoot(), new ArrayList<T>()).toArray(array);
	}

	private ArrayList<T> preOrder(BSTNode<T> node, ArrayList<T> list) {
		if (!node.isEmpty()) {
			list.add(node.getData());
			preOrder((BSTNode<T>) node.getLeft(), list);
			preOrder((BSTNode<T>) node.getRight(), list);
		}
		return list;
	}

	@Override
	public T[] order() {
		T[] array = (T[]) new Comparable[this.size()];
		return (T[]) order(this.root, new ArrayList<T>()).toArray(array);
	}

	private ArrayList<T> order(BSTNode<T> node, ArrayList<T> list) {
		if (!node.isEmpty()) {
			order((BSTNode<T>) node.getLeft(), list);
			list.add(node.getData());
			order((BSTNode<T>) node.getRight(), list);
		}
		return list;
	}
	
	@Override
	public T[] postOrder() {
		T[] array = (T[]) new Comparable[this.size()];
		return (T[]) postOrder(this.root, new ArrayList<T>()).toArray(array);
	}

	private ArrayList<T> postOrder(BSTNode<T> node, ArrayList<T> list) {
		if (!node.isEmpty()) {
			order((BSTNode<T>) node.getLeft(), list);
			order((BSTNode<T>) node.getRight(), list);
			list.add(node.getData());
		}
		return list;
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
