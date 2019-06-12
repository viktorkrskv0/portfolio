/**
 * BinomialHeap
 *
 * An implementation of binomial heap over non-negative integers. Based on
 * exercise from previous semester.
 */

viktork
205727183
viktor karsakov

asaf amitai 
207688367 
asaf

public class BinomialHeap {
	private boolean empty = true;
	private int size = 0;
	public HeapNode min = null;
	//public static int counter = 0;
	public HeapNode[] HeapTreesArray = new HeapNode[32];// it was told in the
														// class forum, that
														// we could use an
														// array to hold all
														// of the trees. be
														// noted, that for
														// any practical
														// use, array of
														// length 32, is
														// large enough

	/**
	 * public boolean empty()
	 *
	 * precondition: none
	 * 
	 * The method returns true if and only if the heap is empty.
	 * 
	 */
	public boolean empty() {
		return empty;
	}

	/**
	 * public void insert(int value)
	 *
	 * Insert value into the heap
	 *
	 */
	public void insert(int value) {
		if (this.empty) {
			this.empty = false;
			this.size++;
			HeapTreesArray[0] = new HeapNode(value);
			this.min = HeapTreesArray[0];
			return;
		}
		HeapNode hN = new HeapNode(value);
		BinomialHeap bH = new BinomialHeap();
		bH.insert(value);
		this.meld(bH);
		if (this.min.value > value) {
			this.min = hN;
		}
	}

	/**
	 * public void deleteMin()
	 *
	 * Delete the minimum value
	 *
	 */
	public void deleteMin() {
		if (empty())
			return;
		else {
			if (this.size > 0) {
				this.size--;
			}
			if (this.size == 0) {
				this.empty = true;
			}

			HeapNode hN = this.min;
			int rnkCount = 0;
			HeapNode hNIterator = null;
			if (hN != null) {
				hNIterator = hN.theMostLeftChild;
			}
			while (hNIterator != null) {
				rnkCount++;
				hNIterator = hNIterator.rightBrother;
			}
			this.HeapTreesArray[rnkCount] = null;
			if (hN != null) {
				hNIterator = hN.theMostLeftChild;
			}
			rnkCount--;
			BinomialHeap bH = new BinomialHeap();
			if (hNIterator != null) {
				bH.empty = false;
			}
			while (hNIterator != null) {
				bH.HeapTreesArray[rnkCount] = hNIterator;
				hNIterator = hNIterator.rightBrother;
				bH.HeapTreesArray[rnkCount].rightBrother = null;
				if (hNIterator != null) {
					hNIterator.leftBrother = null;
				}
				rnkCount = rnkCount - 1;
			}
			this.min = null;
			for (int i = 0; i < this.HeapTreesArray.length; i++) {
				if (this.min == null) {
					if (this.HeapTreesArray[i] != null) {
						this.min = this.HeapTreesArray[i];
					}
				} else {
					if (this.HeapTreesArray[i] != null) {
						if (this.HeapTreesArray[i].value < this.min.value) {
							this.min = this.HeapTreesArray[i];
						}
					}
				}
			}

			if (!bH.empty()) {
				for (int i = 0; i < bH.HeapTreesArray.length; i++) {
					if (bH.min == null) {
						if (bH.HeapTreesArray[i] != null) {
							bH.min = bH.HeapTreesArray[i];
						}
					} else {
						if (bH.HeapTreesArray[i] != null) {
							if (bH.HeapTreesArray[i].value < bH.min.value) {
								bH.min = bH.HeapTreesArray[i];
							}
						}
					}
				}
				this.meld(bH);
			}

		}
	}

	/**
	 * public int findMin()
	 *
	 * Return the minimum value
	 *
	 */
	public int findMin() {
		return this.min.value;
	}

	/**
	 * public void meld (BinomialHeap heap2)
	 *
	 * Meld the heap with heap2
	 *
	 */
	public void meld(BinomialHeap heap2) {
		this.size = this.size + heap2.size;
		if (heap2.min != null && this.min != null && heap2.min.value < this.min.value) {
			this.min = heap2.min;
		}
		if (heap2.min != null && this.min == null) {
			this.min = heap2.min;
		}
		HeapNode[] heap2Arr = heap2.HeapTreesArray;
		if (this.empty() && !heap2.empty()) {
			this.min = heap2.min;
			this.empty = false;
		}
		for (int i = 0; i < heap2Arr.length; i++) {
			if (heap2Arr[i] != null && this.HeapTreesArray[i] == null) {
				this.HeapTreesArray[i] = heap2Arr[i];
			} else if (heap2Arr[i] != null && this.HeapTreesArray[i] != null) {
				HeapNode hN = merge(heap2Arr[i], this.HeapTreesArray[i]);
				HeapTreesArray[i] = null;
				int j = i + 1;
				while (this.HeapTreesArray[j] != null) {
					hN = merge(hN, this.HeapTreesArray[j]);
					HeapTreesArray[j] = null;
					j++;
				}
				HeapTreesArray[j] = hN;
			}

		}
	}

	public HeapNode merge(HeapNode hN1, HeapNode hN2) {
		//counter++;
		if (hN1.value < hN2.value) {
			hN2.rightBrother = hN1.theMostLeftChild;
			if (hN1.theMostLeftChild != null) {
				hN1.theMostLeftChild.leftBrother = hN2;
			}
			hN1.theMostLeftChild = hN2;
			return hN1;
		} else {
			hN1.rightBrother = hN2.theMostLeftChild;
			if (hN2.theMostLeftChild != null) {
				hN2.theMostLeftChild.leftBrother = hN1;
			}
			hN2.theMostLeftChild = hN1;
			return hN2;
		}
	}

	/**
	 * public int size()
	 *
	 * Return the number of elements in the heap
	 * 
	 */
	public int size() {
		return this.size;
	}

	/**
	 * public int minTreeRank()
	 *
	 * Return the minimum rank of a tree in the heap.
	 * 
	 */
	public int minTreeRank() {
		for (int i = 0; i < this.HeapTreesArray.length; i++) {
			if (this.HeapTreesArray[i] != null) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * public boolean[] binaryRep()
	 *
	 * Return an array containing the binary representation of the heap.
	 * 
	 */
	public boolean[] binaryRep() {
		int length = -1;
		for (int i = 0; i < this.HeapTreesArray.length; i++) {
			if (this.HeapTreesArray[i] != null) {
				length = i;
			}
		}
		boolean[] arr = new boolean[length + 1];
		for (int i = 0; i < arr.length; i++) {
			if (this.HeapTreesArray[i] != null) {
				arr[i] = true;
			} else {
				arr[i] = false;
			}
		}
		return arr;
	}

	/**
	 * public void arrayToHeap()
	 *
	 * Insert the array to the heap. Delete previous elements in the heap.
	 * 
	 */
	public void arrayToHeap(int[] array) {
		HeapTreesArray = new HeapNode[32];
		this.min = null;
		this.size = 0;
		this.empty = true;
		for (int i : array) {
			insert(i);
		}
	}

	/**
	 * public boolean isValid()
	 *
	 * Returns true if and only if the heap is valid.
	 * 
	 */
	public boolean isValid() {
		// checking for ranks & validity & 'heap rule'
		// comment!!! - unmark the comment inside the 'if' to run the other
		// validity checks. be informed that those opertations are expensive,
		// and might cause a stack overflow
		for (int i = 0; i < this.HeapTreesArray.length; i++) {
			if (this.HeapTreesArray[i] != null && (countRank(this.HeapTreesArray[i]) != i
			/*
			 * || !heapRule(this.HeapTreesArray[i]) ||
			 * !checkNumChild(this.HeapTreesArray[i], i)
			 */)) {
				return false;
			}
		}
		return true;
	}

	public int countRank(HeapNode h) {
		// counts how many children h has
		int cnt = 0;
		if (h != null) {
			HeapNode hIt = h.theMostLeftChild;
			while (hIt != null) {
				cnt++;
				hIt = hIt.rightBrother;
			}
		}
		return cnt;
	}

	public boolean heapRule(HeapNode h) {
		// check if the heap rule is satisfied
		if (h.theMostLeftChild == null) {
			return true;
		} else {
			boolean flag = true;
			HeapNode hIt = h.theMostLeftChild;
			while (hIt != null) {
				flag = flag && heapRule(hIt);
				if (hIt.value < h.value) {
					flag = false;
				}
				hIt = hIt.rightBrother;
			}
			return flag;
		}
	}

	public boolean checkNumChild(HeapNode h, int i) {
		// check the number of children validity
		if (countRank(h) == 0 && i == 0) {
			return true;
		}
		if (countRank(h) == 0 && i != 0) {
			return false;
		}
		HeapNode hIt = h;
		boolean flag = true;
		while (hIt != null) {
			flag = countRank(h) == i && checkNumChild(h.theMostLeftChild, i - 1);
			hIt = hIt.rightBrother;
		}
		return flag;
	}

	/**
	 * public class HeapNode
	 * 
	 * If you wish to implement classes other than BinomialHeap (for example
	 * HeapNode), do it in this file, not in another file
	 * 
	 */
	public class HeapNode {

		public int value;
		public HeapNode theMostLeftChild = null;
		public HeapNode rightBrother = null;
		public HeapNode leftBrother = null;

		private HeapNode(int value) {
			this.value = value;
		}

	}

}