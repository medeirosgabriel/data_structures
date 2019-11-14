package adt.rbtree;

import java.util.ArrayList;
import java.util.List;

import adt.bst.BSTImpl;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

   public RBTreeImpl() {
      this.root = new RBNode<T>();
   }

   protected int blackHeight() {
      return this.blackHeight((RBNode<T>) this.root);
   }

   private int blackHeight(RBNode<T> node) {
      int height = 0;
      if (!node.isEmpty()) {
         if (node.getColour().equals(Colour.BLACK)) {
            height = 1 + Math.max(this.blackHeight((RBNode<T>) node.getLeft()),
                  this.blackHeight((RBNode<T>) node.getRight()));
         } else {
            height = Math.max(this.blackHeight((RBNode<T>) node.getLeft()),
                  this.blackHeight((RBNode<T>) node.getRight()));
         }
      }
      return height;
   }

   protected boolean verifyProperties() {
      boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
            && verifyBlackHeight();

      return resp;
   }

   /**
    * The colour of each node of a RB tree is black or red. This is guaranteed
    * by the type Colour.
    */
   private boolean verifyNodesColour() {
      return true; // already implemented
   }

   /**
    * The colour of the root must be black.
    */
   private boolean verifyRootColour() {
      return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
      // implemented
   }

   /**
    * This is guaranteed by the constructor.
    */
   private boolean verifyNILNodeColour() {
      return true; // already implemented
   }

   /**
    * Verifies the property for all RED nodes: the children of a red node must
    * be BLACK.
    */
   private boolean verifyChildrenOfRedNodes() {
      return this.verifyChildrenOfRedNodes((RBNode<T>) this.root);
   }

   private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
      boolean resp = true;
      if (!node.isEmpty()) {
         if (node.getColour().equals(Colour.RED)) {
            RBNode<T> left = (RBNode<T>) node.getLeft();
            RBNode<T> right = (RBNode<T>) node.getRight();
            if (left.getColour().equals(Colour.RED) || right.getColour().equals(Colour.RED)) {
               return false;
            }
         }
         resp = this.verifyChildrenOfRedNodes((RBNode<T>) node.getLeft())
               && this.verifyChildrenOfRedNodes((RBNode<T>) node.getLeft());
      }
      return resp;
   }

   /**
    * Verifies the black-height property from the root.
    */
   private boolean verifyBlackHeight() {
      return blackHeight((RBNode<T>) this.root.getLeft()) == blackHeight((RBNode<T>) this.root.getRight());
   }

   @Override
   public void insert(T value) {
      this.insert((RBNode<T>) this.root, value);
   }

   private void insert(RBNode<T> node, T element) {
      if (node.isEmpty()) {
         node.setData(element);
         node.setColour(Colour.RED);
         node.setLeft(new RBNode<T>());
         node.setRight(new RBNode<T>());
         node.getLeft().setParent(node);
         node.getRight().setParent(node);
         this.fixUpCase1(node);
      } else {
         if (element.compareTo(node.getData()) < 0) {
            this.insert((RBNode<T>) node.getLeft(), element);
         } else {
            this.insert((RBNode<T>) node.getRight(), element);
         }
      }
   }

   @Override
   public RBNode<T>[] rbPreOrder() {
      List<RBNode<T>> list = new ArrayList<RBNode<T>>();
      RBNode<T>[] array = new RBNode[this.size()];
      return this.rbPreOrder((RBNode<T>) this.root, list).toArray(array);
   }

   private List<RBNode<T>> rbPreOrder(RBNode<T> node, List<RBNode<T>> list) {
      if (!node.isEmpty()) {
         list.add(node);
         this.rbPreOrder((RBNode<T>) node.getLeft(), list);
         this.rbPreOrder((RBNode<T>) node.getRight(), list);
      }
      return list;
   }

   // FIXUP methods
   protected void fixUpCase1(RBNode<T> node) {
      if (node.getParent() == null) {
         node.setColour(Colour.BLACK);
      } else {
         this.fixUpCase2(node);
      }
   }

   protected void fixUpCase2(RBNode<T> node) {
      RBNode<T> parent = (RBNode<T>) node.getParent();
      if (parent.getColour().equals(Colour.RED)) {
         this.fixUpCase3(node);
      }
   }

   protected void fixUpCase3(RBNode<T> node) {
      RBNode<T> parent = (RBNode<T>) node.getParent();
      RBNode<T> grandParent = (RBNode<T>) parent.getParent();
      RBNode<T> uncle;
      if (grandParent.getLeft().equals(parent)) {
         uncle = (RBNode<T>) grandParent.getRight();
      } else {
         uncle = (RBNode<T>) grandParent.getLeft();
      }
      if (uncle.getColour().equals(Colour.RED)) {
         parent.setColour(Colour.BLACK);
         grandParent.setColour(Colour.RED);
         uncle.setColour(Colour.BLACK);
         this.fixUpCase1(grandParent);
      } else {
         this.fixUpCase4(node);
      }
   }

   protected void fixUpCase4(RBNode<T> node) {
      RBNode<T> parent = (RBNode<T>) node.getParent();
      RBNode<T> grandParent = (RBNode<T>) parent.getParent();
      RBNode<T> next = node;
      if (grandParent.getLeft().equals(parent) && parent.getRight().equals(node)) {
         Util.leftRotation(parent);
         next = (RBNode<T>) node.getLeft();
      } else if (grandParent.getRight().equals(parent) && parent.getLeft().equals(node)) {
         Util.rightRotation(parent);
         next = (RBNode<T>) node.getRight();
      }
      this.fixUpCase5(next);
   }

   protected void fixUpCase5(RBNode<T> node) {
      RBNode<T> parent = (RBNode<T>) node.getParent();
      RBNode<T> grandParent = (RBNode<T>) parent.getParent();
      RBNode<T> aux;
      parent.setColour(Colour.BLACK);
      grandParent.setColour(Colour.RED);
      if (parent.getLeft().equals(node)) {
         aux = (RBNode<T>) Util.rightRotation(grandParent);
      } else {
         aux = (RBNode<T>) Util.leftRotation(grandParent);
      }
      if (aux.getParent() == null) {
         this.root = aux;
      }
   }
}
