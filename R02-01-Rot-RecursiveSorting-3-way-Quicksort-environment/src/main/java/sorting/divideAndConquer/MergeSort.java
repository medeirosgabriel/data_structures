package sorting.divideAndConquer;

import sorting.AbstractSorting;

/**
 * Merge sort is based on the divide-and-conquer paradigm. The algorithm
 * consists of recursively dividing the unsorted list in the middle, sorting
 * each sublist, and then merging them into one single sorted list. Notice that
 * if the list has length == 1, it is already sorted.
 */
public class MergeSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (array != null && leftIndex >= 0 && rightIndex >= 0 && rightIndex >= leftIndex) {
			if (leftIndex < rightIndex) {
				int middle = (leftIndex + rightIndex)/2;
				sort(array, leftIndex, middle);
				sort(array, middle + 1, rightIndex);
				merge(array, leftIndex, middle, rightIndex);
			}
		}
	}

	private void merge(T[] array, int leftIndex, int middle, int rightIndex) {
		T[] aux = (T[]) new Comparable[rightIndex + 1];
		int i = leftIndex;
		int p1 = leftIndex;
		int p2 = middle + 1;
		while (p1 <= middle && p2 <= rightIndex) {
			if (array[p1].compareTo(array[p2]) <= 0) {
				aux[i++] = array[p1++];
			}else {
				aux[i++] = array[p2++];
			}
		}
		while (p1 <= middle) {
			aux[i++] = array[p1++];
		}
		while (p2 <= rightIndex) {
			aux[i++] = array[p2++];
		}
		for (i = leftIndex; i <= rightIndex; i++) {
			array[i] = aux[i];
		}
	}
}
