package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.BTNode;
import adt.bt.Util;

/**
 * 
 * Implementacao de uma arvore AVL
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements
		AVLTree<T> {

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.
	
	@Override
	public void insert(T element) {
		this.insert(this.root, element);
	}

	private void insert(BTNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode.Builder<T>().data(null).left(null).right(null).parent(null).parent(node).build());
			node.setRight(new BSTNode.Builder<T>().data(null).left(null).right(null).parent(null).parent(node).build());
		} else if (element.compareTo(node.getData()) < 0) {
			insert(node.getLeft(), element);
		} else {
			insert(node.getRight(), element);
		}
		rebalance((BSTNode<T>) node);
	}
	
	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);
		super.remove(element);
		this.rebalanceUp((BSTNode<T>) node.getParent());
	}

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		return this.height(node.getLeft()) - this.height(node.getRight());
	}

	// AUXILIARY
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
		}else {
			Util.rightRotation((BSTNode<T>) node.getRight());
			aux = Util.leftRotation(node);
		}
		if (aux.getParent() == null) {
			this.root = aux;
		}
	}
	
	private void hLeft(BSTNode<T> node) {
		BSTNode<T> aux;
		if (this.calculateBalance((BSTNode<T>) node.getLeft()) > 0) {
			aux = Util.rightRotation(node);
		}else {
			Util.leftRotation((BSTNode<T>) node.getLeft());
			aux = Util.rightRotation(node);
		}
		if (aux.getParent() == null) {
			this.root = aux;
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		if (node != null) {
			this.rebalance(node);
			this.rebalanceUp((BSTNode<T>) node.getParent());
		}
	}
}
