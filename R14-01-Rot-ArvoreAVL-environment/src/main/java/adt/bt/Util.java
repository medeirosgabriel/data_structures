package adt.bt;

import adt.bst.BSTNode;

public class Util {


	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		BSTNode<T> pivot = (BSTNode<T>) node.getRight();
		BSTNode<T> lPivot = (BSTNode<T>) pivot.getLeft();
		BSTNode<T> rootParent = (BSTNode<T>) node.getParent();
		if (rootParent != null) {
			if (rootParent.getLeft().equals(node) ) {
				rootParent.setLeft(pivot);
			}else {
				rootParent.setRight(pivot);
			}
		}
		pivot.setParent(rootParent);
		node.setParent(pivot);
		node.setRight(lPivot);
		pivot.setLeft(node);
		lPivot.setParent(node);
		return pivot;
	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		BSTNode<T> pivot = (BSTNode<T>) node.getLeft();
		BSTNode<T> rPivot = (BSTNode<T>) pivot.getRight();
		BSTNode<T> rootParent = (BSTNode<T>) node.getParent();
		if (rootParent != null) {
			if (rootParent.getLeft().equals(node) ) {
				rootParent.setLeft(pivot);
			}else {
				rootParent.setRight(pivot);
			}
		}
		pivot.setParent(rootParent);
		node.setParent(pivot);
		node.setLeft(rPivot);
		pivot.setRight(node);
		rPivot.setParent(node);
		return pivot;
	}

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}
