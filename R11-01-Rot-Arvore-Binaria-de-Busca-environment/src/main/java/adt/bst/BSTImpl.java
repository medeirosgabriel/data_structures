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
		int height = -1;
		if (!this.root.isEmpty()) {
			height += height(this.root);
		}
		return height;
	}

	private int height(BTNode<T> node) {
		if (!node.isEmpty()) {
			return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
		} else {
			return 0;
		}
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
			} else if (element.compareTo(node.getData()) < 0) {
				ret = search((BSTNode<T>) node.getLeft(), element);
			} else {
				ret = search((BSTNode<T>) node.getRight(), element);
			}
		}
		return ret;
	}

	@Override
	public void insert(T element) {
		insert(this.root, element);
	}

	private void insert(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode.Builder<T>().data(null).left(null).right(null).parent(node).build());
			node.setRight(new BSTNode.Builder<T>().data(null).left(null).right(null).parent(node).build());
		} else if (element.compareTo(node.getData()) <= 0) {
			insert((BSTNode<T>) node.getLeft(), element);
		} else {
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
		} else {
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
		} else {
			ret = minimum((BSTNode<T>) node.getLeft());
		}
		return ret;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> sucessor = search(element);
		if (!sucessor.isEmpty()) {
			if (!sucessor.getRight().isEmpty()) {
				sucessor = minimum((BSTNode<T>) sucessor.getRight());
			} else {
				sucessor = sucessor(sucessor);
			}
		} else {
			sucessor = null;
		}
		return sucessor;
	}

	private BSTNode<T> sucessor(BSTNode<T> node) {
		BSTNode<T> sucessor = (BSTNode<T>) node.getParent();
		if (sucessor != null) {
			if (!sucessor.isEmpty() && sucessor.getRight().equals(node)) {
				sucessor = sucessor((BSTNode<T>) node.getParent());
			}
		}
		return sucessor;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> predecessor = search(element);
		if (!predecessor.isEmpty()) {
			if (!predecessor.getLeft().isEmpty()) {
				predecessor = maximum((BSTNode<T>) predecessor.getLeft());
			} else {
				predecessor = predecessor(predecessor);
			}
		} else {
			predecessor = null;
		}
		return predecessor;
	}

	private BSTNode<T> predecessor(BSTNode<T> node) {
		BSTNode<T> predecessor = (BSTNode<T>) node.getParent();
		if (predecessor != null) {
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
				node.setLeft(null);
				node.setRight(null);
			}else if (node.getRight().isEmpty() || node.getLeft().isEmpty()) {
				BSTNode<T> parent = (BSTNode<T>) node.getParent();
				if (parent != null) {
					if (parent.getLeft().equals(node)) {
						if (!node.getLeft().isEmpty()) {
							parent.setLeft(node.getLeft());
							node.getLeft().setParent(parent);
						} else {
							parent.setLeft(node.getRight());
							node.getRight().setParent(parent);
						}
					}else {
						if (!node.getLeft().isEmpty()) {
							parent.setRight(node.getLeft());
							node.getLeft().setParent(parent);
						} else {
							parent.setRight(node.getRight());
							node.getRight().setParent(parent);
						}
					}
				}else{
					if (!node.getRight().isEmpty()) {
						this.root = (BSTNode<T>) node.getRight();
					} else {
						this.root = (BSTNode<T>) node.getLeft();
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
		return (T[]) preOrder(this.getRoot(), new ArrayList<T>()).toArray(array);
	}

	private ArrayList<T> preOrder(BSTNode<T> node, ArrayList<T> list) {
		if (!node.isEmpty()) {
			list.add((T) node.getData());
			this.preOrder((BSTNode<T>) node.getLeft(), list);
			this.preOrder((BSTNode<T>) node.getRight(), list);
		}
		return list;
	}

	@Override
	public T[] order() {
		T[] array = (T[]) new Comparable[this.size()];
		return (T[]) order(this.getRoot(), new ArrayList<T>()).toArray(array);
	}

	private ArrayList<T> order(BSTNode<T> node, ArrayList<T> list) {
		if (!node.isEmpty()) {
			this.order((BSTNode<T>) node.getLeft(), list);
			list.add((T) node.getData());
			this.order((BSTNode<T>) node.getRight(), list);
		}
		return list;
	}

	@Override
	public T[] postOrder() {
		T[] array = (T[]) new Comparable[this.size()];
		return (T[]) postOrder(this.getRoot(), new ArrayList<T>()).toArray(array);
	}

	private ArrayList<T> postOrder(BSTNode<T> node, ArrayList<T> list) {
		if (!node.isEmpty()) {
			this.preOrder((BSTNode<T>) node.getLeft(), list);
			this.preOrder((BSTNode<T>) node.getRight(), list);
			list.add((T) node.getData());
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
