package adt.btree;

import java.util.ArrayList;
import java.util.List;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

   protected BNode<T> root;
   protected int order;

   public BTreeImpl(int order) {
      this.order = order;
      this.root = new BNode<T>(order);
   }

   @Override
   public BNode<T> getRoot() {
      return this.root;
   }

   @Override
   public boolean isEmpty() {
      return this.root.isEmpty();
   }

   @Override
   public int height() {
      return height(this.root);
   }

   private int height(BNode<T> node) {
      int height = -1;
      if (!node.isEmpty()) {
         if (node.isLeaf()) {
            height += 1;
         } else {
            height = 1 + this.height(node.getChildren().get(0));
         }
      }
      return height;
   }

   @Override
   public BNode<T>[] depthLeftOrder() {
      List<BNode<T>> list = new ArrayList<>();
      this.depthLeftOrder(this.root, list);
      return list.toArray(new BNode[list.size()]);
   }

   private List<BNode<T>> depthLeftOrder(BNode<T> node, List<BNode<T>> list) {
		if (!node.isEmpty()) {
			list.add(node);
			node.getChildren().forEach(e -> this.depthLeftOrder(e, list));
		}
		return list;
	}

   @Override
   public int size() {
      return this.size(this.root);
   }

   private int size(BNode<T> node) {
      int quant = 0;
      if (!node.isEmpty()) {
         quant += node.size();
         for (int i = 0; i < node.getChildren().size(); i++) {
            quant += this.size(node.getChildren().get(i));
         }
      }
      return quant;
   }

   @Override
   public BNodePosition<T> search(T element) {
      return this.search(this.root, element);
   }

   private BNodePosition<T> search(BNode<T> node, T element) {
      int i = 0;
      while (i < node.size() && element.compareTo(node.getElementAt(i)) > 0) {
         i++;
      }
      BNodePosition<T> ret = null;
      if (i < node.getElements().size() && node.getElementAt(i).equals(element)) {
         ret = new BNodePosition<T>(node, i);
      } else if (node.isLeaf()) {
         ret = new BNodePosition<T>();
      } else {
         ret = this.search(node.getChildren().get(i), element);
      }
      return ret;
   }

   @Override
   public void insert(T element) {
      this.insert(this.root, element);
   }

   private void insert(BNode<T> node, T element) {
      int i = 0;
      while (i < node.size() && element.compareTo(node.getElementAt(i)) > 0) {
         i++;
      }
      if (i >= node.size() || !node.getElementAt(i).equals(element)) {
         if (node.isLeaf()) {
            node.addElement(element);
         } else {
            this.insert(node.getChildren().get(i), element);
         }
      }
      if (node.size() > node.getMaxKeys()) {
         this.split(node);
      }
   }

   private void split(BNode<T> node) {
		int medianIndex = node.getElements().size() / 2;
		BNode<T> rightNode = new BNode<>(this.order);
		BNode<T> leftNode = new BNode<>(this.order);
		for (int i = 0; i < node.size(); i++) {
			if (i < medianIndex) {
				leftNode.addElement(node.getElementAt(i));
			} else if (i > medianIndex) {
				rightNode.addElement(node.getElementAt(i));
			}
		}
		T medianValue = node.getElementAt(medianIndex);
		if (!node.isLeaf()) {
			int leftIndex = 0;
			int rightIndex = 0;
			for (int i = 0; i < node.size(); i++) {
				if (i <= medianIndex) {
					leftNode.addChild(leftIndex++, node.getChildren().get(i));
				} else {
					rightNode.addChild(rightIndex++, node.getChildren().get(i));
				}
			}
		}
		node.getChildren().clear();
		if (node.equals(this.getRoot())) {
			BNode<T> newRoot = new BNode<T>(this.order);
			newRoot.addElement(medianValue);
			newRoot.addChild(0, leftNode);
			newRoot.addChild(1, rightNode);
			newRoot.getChildren().get(0).setParent(newRoot);
			newRoot.getChildren().get(1).setParent(newRoot);
			this.root = newRoot;
		} else {
			node.addChild(0, leftNode);
			node.addChild(1, rightNode);
			promote(node);
		}

	}

	private void promote(BNode<T> node) {
		T medianValue = node.getElementAt(node.getElements().size() / 2);
		node.getElements().clear();
		node.addElement(medianValue);
		BNode<T> parent = node.getParent();
		if (parent != null) {
			node.getChildren().get(0).setParent(parent);
			node.getChildren().get(1).setParent(parent);
			int index = parent.getChildren().indexOf(node);
			parent.addElement(medianValue);
			parent.addChild(index++, node.getChildren().get(0));
			parent.addChild(index++, node.getChildren().get(1));
			parent.getChildren().remove(node);
		}
	}

   // NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
   @Override
   public BNode<T> maximum(BNode<T> node) {
      // NAO PRECISA IMPLEMENTAR
      throw new UnsupportedOperationException("Not Implemented yet!");
   }

   @Override
   public BNode<T> minimum(BNode<T> node) {
      // NAO PRECISA IMPLEMENTAR
      throw new UnsupportedOperationException("Not Implemented yet!");
   }

   @Override
   public void remove(T element) {
      // NAO PRECISA IMPLEMENTAR
      throw new UnsupportedOperationException("Not Implemented yet!");
   }

}
