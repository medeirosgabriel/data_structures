package sorting.divideAndConquer;

import sorting.AbstractSorting;

/**
 * Quicksort is based on the divide-and-conquer paradigm. The algorithm chooses
 * a pivot element and rearranges the elements of the interval in such a way
 * that all elements lesser than the pivot go to the left part of the array and
 * all elements greater than the pivot, go to the right part of the array. Then
 * it recursively sorts the left and the right parts. Notice that if the list
 * has length == 1, it is already sorted.
 */
public class QuickSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (array != null && leftIndex >= 0 && rightIndex >= 0 && rightIndex >= leftIndex) {
			if (leftIndex < rightIndex) {
				int pivotPosition = partition(array, leftIndex, rightIndex);
				sort(array, leftIndex, pivotPosition - 1);
				sort(array, pivotPosition + 1, rightIndex);
			}
		}
	}

	private int partition(T[] array, int leftIndex, int rightIndex) {
		T pivot = array[leftIndex];
		int left = leftIndex;
		int right = rightIndex;
		while (left <= right) {
			if (array[left].compareTo(pivot) <= 0) {
				left ++;
			}else if (array[right].compareTo(pivot) > 0) {
				right --;
			}else{
				T temp = array[left];
				array[left] = array[right];
				array[right] = temp;
			}
		}
		array[leftIndex] = array[right];
		array[right] = pivot;
		return right;
	}
}
