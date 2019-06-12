package dataStructrehw1;

/**
 *
 * RBTree
 *
 * An implementation of a Red Black Tree with non-negative, distinct integer
 * keys and values
 *
 */

public class RBTree {

	/**
	 * public class RBNode
	 */
	public static class RBNode {
		private boolean red = true;
		private RBNode parent = null;
		private RBNode left = null;
		private RBNode right = null;
		private int key;
		private String value;

		/**
		 * constructor for RBNode with key and value
		 * 
		 * @param key
		 * @param value
		 */
		public RBNode(int key, String value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * constructor for RBNode with key and value
		 */
		public RBNode() {
			this(0, "");
		}

		/**
		 * 
		 * @return true iff the node is red
		 */
		public boolean isRed() {
			return red;
		}

		/**
		 * 
		 * @return node parent
		 */
		public RBNode getParent() {
			return parent;
		}

		/**
		 * 
		 * @return node left child
		 */
		public RBNode getLeft() {
			return left;
		}

		/**
		 * 
		 * @return node right child
		 */
		public RBNode getRight() {
			return right;
		}

		/**
		 * 
		 * @return node key
		 */
		public int getKey() {
			return key;
		}

		/**
		 * 
		 * @return node value
		 */
		public String getValue() {
			return value;
		}

	}

	private RBNode root = null;
	private int size = 0;
	private RBNode min = null;
	private RBNode max = null;
	private static int colorCounterDelete = 0; // static counter for counting
												// color change while deleting

	/**
	 * public RBNode getRoot()
	 *
	 * returns the root of the red black tree
	 *
	 */
	public RBNode getRoot() {
		return root;
	}

	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	public boolean empty() {
		return size == 0;
	}

	/**
	 * public String search(int k)
	 *
	 * returns the value of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k) {
		RBNode current = root;
		while (current != null) {
			if (current.getKey() == k)
				return current.getValue();
			else if (current.getKey() < k)
				current = current.getRight();
			else
				current = current.getLeft();
		}
		return null;
	}

	/**
	 * 
	 * @param node
	 * @return node's uncle (the parent's sibling), if exists and null
	 *         otherwise.
	 */
	private RBNode uncle(RBNode node) {
		if (node == root)
			return null;
		return sibling(node.parent);
	}

	private void leftRotate(RBNode node) {
		RBNode parent = node.parent;
		parent.right = node.left;
		if (parent.right != null)
			parent.right.parent = parent;
		node.left = parent;
		node.parent = parent.parent;
		parent.parent = node;
		if (node.parent == null)
			root = node;
		else if (node.parent.right == parent)
			node.parent.right = node;
		else
			node.parent.left = node;
	}

	private void rightRotate(RBNode node) {
		RBNode parent = node.parent;
		parent.left = node.right;
		if (parent.left != null)
			parent.left.parent = parent;
		node.right = parent;
		node.parent = parent.parent;
		parent.parent = node;
		if (node.parent == null)
			root = node;
		else if (node.parent.right == parent)
			node.parent.right = node;
		else
			node.parent.left = node;
	}

	/**
	 * public int insert(int k, String v)
	 *
	 * inserts an item with key k and value v to the red black tree. the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were necessary. returns -1 if an item
	 * with key k already exists in the tree.
	 */
	public int insert(int k, String v) {
		if (root == null) {
			root = new RBNode();
			root.key = k;
			root.value = v;
			root.red = false;
			min = root;
			max = root;
			size = 1;
			return 1;
		}
		int colorSwitches = 0;
		// insert like binary search tree
		RBNode node = new RBNode();
		node.key = k;
		node.value = v;
		if (node.key == min.key || node.key == max.key)
			return -1;
		if (node.key < min.key) {
			node.parent = min;
			min.left = node;
			min = node;
		} else if (node.key > max.key) {
			node.parent = max;
			max.right = node;
			max = node;
		} else {
			RBNode current = root;
			while (true) {
				if (current.key == k)
					return -1;
				else if (current.key < k)
					if (current.right != null)
						current = current.right;
					else {
						current.right = node;
						node.parent = current;
						break;
					}
				else {
					if (current.left != null)
						current = current.left;
					else {
						current.left = node;
						node.parent = current;
						break;
					}
				}
			}
		}
		// Balancing the tree
		while (node != root && node.parent.red) {
			RBNode uncle = uncle(node);
			RBNode parent = node.parent;
			if (uncle != null && uncle.red) {
				uncle.red = false;
				parent.red = false;
				if (parent.parent != root) {
					parent.parent.red = true;
					colorSwitches += 3;
				} else
					colorSwitches += 2;
				node = parent.parent;

			} else if (parent.key < parent.parent.key) {
				if (node.key > parent.key)
					leftRotate(node);
				else
					node = parent;
				parent = node.parent;
				node.red = false;
				parent.red = true;
				rightRotate(node);
				colorSwitches += 2;

			} else {
				if (node.key < parent.key)
					rightRotate(node);
				else
					node = parent;
				parent = node.parent;
				node.red = false;
				parent.red = true;
				leftRotate(node);
				colorSwitches += 2;
			}

		}
		if (root.red) {
			root.red = false;
			colorSwitches--;
		}
		size++;
		return colorSwitches;
	}

	/**
	 * public int delete(int k)
	 *
	 * deletes an item with key k from the binary tree, if it is there; the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were needed. returns -1 if an item
	 * with key k was not found in the tree.
	 */
	public int delete(int k) {
		RBNode node = findNode(this.root, k);// holder for the node that we want
												// to
												// delete
		if (node == null) {
			return -1;
		} else {
			deleteHelper(node);
		}
		int temp = RBTree.colorCounterDelete;
		RBTree.colorCounterDelete = 0;
		this.size--;
		if (this.size != 0) {
			updateMin(this.root);
			updateMax(this.root);
		}
		return temp;
	}

	public void caseDeleteDecider(RBNode s) {
		// input: node with extra blackness
		// output: activate the function that fixes the tree after deletion
		// (case1 case2 case3 or case4)
		if (s != this.getRoot()) {
			RBNode bro = sibling(s);
			if (bro != null && bro.isRed()) {
				case1(s);
			} else {
				if (bro != null && (bro.right == null || !bro.right.isRed())
						&& (bro.left == null || !bro.left.isRed())) {
					case2(s);
				} else if ((s.parent.right == s && bro != null
						&& (s.parent.left.right != null && s.parent.left.right.isRed())
						&& (s.parent.left.left == null || !s.parent.left.left.isRed()))
						|| (s.parent.left == s && bro != null
								&& (s.parent.right.left != null && s.parent.right.left.isRed())
								&& (s.parent.right.right == null || !s.parent.right.right.isRed()))) {
					case3(s);
				} else {
					if (bro != null) {
						case4(s);
					}
				}
			}
		} else {
			if (s.isRed()) {
				s.red = false;
				RBTree.colorCounterDelete++;
			}
		}
	}

	public RBNode sibling(RBNode n) {
		// input: node with a brother
		// output: his sibling
		boolean nIsLeftChild = n.parent.left == n;
		RBNode sibling;
		if (nIsLeftChild) {
			sibling = n.parent.right;
		} else {
			sibling = n.parent.left;
		}
		return sibling;
	}

	public void case1(RBNode s) {
		// input: node with extra blackness that its brother is red
		// output: change the tree structure according to case 1 as was shown in
		// the class
		boolean SIsLeftChild = s.parent.left == s;
		if (SIsLeftChild) {
			leftRotate(s.parent.right);
		} else {
			rightRotate(s.parent.left);
		}
		caseDeleteDecider(s);
	}

	public void case2(RBNode s) {
		// input: node with extra blackness that its brother is black and both
		// nephews are black
		// output: change the tree structre and color according to case 2 as was
		// shown in the class
		RBNode bro = sibling(s);
		bro.red = true;
		RBTree.colorCounterDelete++; // updating the static counter
		if (s.parent.isRed()) {
			s.parent.red = false;
			RBTree.colorCounterDelete++;
		} else {
			caseDeleteDecider(s.parent);
		}

	}

	public void case3(RBNode s) {
		// input: node with extra blackness that it's brother is black and only
		// left
		// nephew is red (or the symmetrical case)
		// output: change the tree structure and color according to case 3 as
		// was
		// shown in the class
		RBNode bro = sibling(s);
		boolean SIsLeftChild = s.parent.left == s;
		bro.red = true;
		if (SIsLeftChild) {
			bro.left.red = false;
			rightRotate(bro.left);
		} else {
			bro.right.red = false;
			leftRotate(bro.right);
		}
		RBTree.colorCounterDelete++;
		RBTree.colorCounterDelete++;

		caseDeleteDecider(s);
	}

	public void case4(RBNode s) {
		// input: node with extra blackness that its is black and only right
		// nephew is red (or the symmetrical case)
		// output: change the tree structure and color according to case 3 as
		// was
		// shown in the class
		RBNode bro = sibling(s);
		boolean SIsLeftChild = s.parent.left == s;
		if (SIsLeftChild) {
			bro.red = bro.parent.red;
			bro.right.red = false;
			bro.parent.red = false;
			leftRotate(bro);
		} else {
			bro.red = bro.parent.red;
			bro.left.red = false;
			bro.parent.red = false;
			rightRotate(bro);
		}
		RBTree.colorCounterDelete++;
		RBTree.colorCounterDelete++;
		RBTree.colorCounterDelete++;
	}

	public void deleteHelper(RBNode node) {
		// input: node to delete
		// output: delete the node without doing the tree fixing and give back
		// the node that replace the deleted one (null if there weren't)
		RBNode s;// pointer to hold the replacing node

		RBNode rootFather = new RBNode();
		rootFather.right = this.root;
		rootFather.red = false;
		this.root.parent = rootFather;

		if (node.left == null && node.right == null)// if node is a leaf
		{
			s = new RBNode();// creating new Null node
			s.red = false;
			s.parent = node.parent;
			if (node.parent != null && node.parent.left == node) {
				node.parent.left = s;
			} else {
				node.parent.right = s;
			}
			if (!node.isRed()) {
				caseDeleteDecider(s);
			}
			if (s.parent.left == s) {// deleting the null node
				s.parent.left = null;
			} else {
				s.parent.right = null;
			}
			s.parent = null;
		} else if (node.left == null && node.right != null) {// if node has only
																// right child
			s = node.right;
			s.parent = node.parent;
			if (node.parent != null && node.parent.left == node) {
				node.parent.left = s;
			} else {
				node.parent.right = s;
			}
			if (!node.isRed()) {
				if (s.isRed()) {
					s.red = false;
					RBTree.colorCounterDelete++;
				} else {
					caseDeleteDecider(s);
				}
			}
		} else if (node.left != null && node.right == null) {// if node has only
																// left child
			s = node.left;
			s.parent = node.parent;
			if (node.parent != null && node.parent.left == node) {
				node.parent.left = s;
			} else {
				node.parent.right = s;
			}
			if (!node.isRed()) {
				if (s.isRed()) {
					s.red = false;
					RBTree.colorCounterDelete++;
				} else {
					caseDeleteDecider(s);
				}
			}
		} else {// if node has two children
			s = successor(node);
			if (node.right == s) {// if the successor is node's right child
				s.parent = node.parent;
				s.left = node.left;
				node.left.parent = s;
				if (node.parent.left == node) {
					node.parent.left = s;
				} else {
					node.parent.right = s;
				}
				boolean sWasRed = s.red;
				if (s.red != node.red) {
					RBTree.colorCounterDelete++;
				}
				s.red = node.red;
				if (!sWasRed) {
					if (s.right != null) {
						if (s.right.isRed()) {
							s.right.red = false;
							RBTree.colorCounterDelete++;
						} else {
							caseDeleteDecider(s.right);
						}
					} else {
						RBNode sChild = new RBNode();
						s.right = sChild;
						sChild.red = false;
						sChild.parent = s;
						caseDeleteDecider(s.right);
						if (sChild.parent.left == sChild) {
							sChild.parent.left = null;
						} else {
							sChild.parent.right = null;
						}
						sChild.parent = null;
					}
				}
			} else {// if the successor is not the node's child
				s.parent.left = s.right;
				RBNode sOriginalChild = s.right;
				RBNode sOriginalParent = s.parent;
				if (s.right != null) {
					s.right.parent = s.parent;
				}
				s.parent = node.parent;
				if (node.parent.left == node) {
					node.parent.left = s;
				} else {
					node.parent.right = s;
				}
				s.left = node.left;
				node.left.parent = s;
				s.right = node.right;
				node.right.parent = s;

				boolean sWasRed = s.red;
				if (s.red != node.red) {
					RBTree.colorCounterDelete++;
				}
				s.red = node.red;
				if (!sWasRed) {
					if (sOriginalChild != null) {
						if (sOriginalChild.isRed()) {
							sOriginalChild.red = false;
							RBTree.colorCounterDelete++;
						} else {
							caseDeleteDecider(sOriginalChild);
						}
					} else {
						sOriginalChild = new RBNode();
						sOriginalChild.red = false;
						sOriginalChild.parent = sOriginalParent;
						sOriginalParent.left = sOriginalChild;
						caseDeleteDecider(sOriginalChild);
						if (sOriginalChild.parent.left == sOriginalChild) {
							sOriginalChild.parent.left = null;
						} else {
							sOriginalChild.parent.right = null;
						}
						sOriginalChild.parent = null;
					}
				}
			}
		}

		this.root.parent = null;
		this.root = rootFather.right;

	}

	public RBNode findNode(RBNode node, int k) {
		// input: a node and a key
		// output: the node in the tree with the same key. null if we didn't
		// find it
		if (node == null) {
			return node;
		}
		if (node.getKey() == k) {
			return node;
		}
		if (k < node.getKey()) {
			return findNode(node.getLeft(), k);
		} else {
			return findNode(node.getRight(), k);
		}
	}

	public void updateMin(RBNode node) {
		// input: tree root
		// output: update the minimum pointer in the tree
		if (node.left == null) {
			this.min = node;
		} else {
			updateMin(node.left);
		}
	}

	public void updateMax(RBNode node) {
		// input: tree root
		// output: update the maximum pointer in the tree
		if (node.right == null) {
			this.max = node;
		} else {
			updateMax(node.right);
		}
	}

	/**
	 * public String min()
	 *
	 * Returns the value of the item with the smallest key in the tree, or null
	 * if the tree is empty
	 */
	public String min() {
		return min.getValue(); // to be replaced by student code
	}

	/**
	 * public String max()
	 *
	 * Returns the value of the item with the largest key in the tree, or null
	 * if the tree is empty
	 */
	public String max() {
		return max.getValue();
	}

	private RBNode successor(RBNode node) {
		if (node == max)
			return null;
		RBNode successor = null;
		if (node.right != null) {
			successor = node.right;
			while (successor.left != null)
				successor = successor.left;
		} else {
			successor = node;
			while (successor.parent != null && successor.parent.key < successor.key)
				successor = successor.parent;
			successor = successor.parent;
		}
		return successor;
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree, or an empty
	 * array if the tree is empty.
	 */
	public int[] keysToArray() {
		int[] arr = new int[size];
		RBNode node = min;
		if (min == null)
			return arr;
		for (int i = 0; i < size && node != null; i++) {
			arr[i] = node.key;
			node = successor(node);
		}
		return arr;
	}

	/**
	 * public String[] valuesToArray()
	 *
	 * Returns an array which contains all values in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 */
	public String[] valuesToArray() {
		String[] arr = new String[size];
		RBNode node = min;
		if (min == null)
			return arr;
		for (int i = 0; i < size; i++) {
			arr[i] = node.value;
			node = successor(node);
		}
		return arr;
	}

	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 *
	 * precondition: none postcondition: none
	 */
	public int size() {
		return size;
	}

	/**
	 * If you wish to implement classes, other than RBTree and RBNode, do it in
	 * this file, not in another file.
	 */

}
