package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratÃ©gia de Counting Sort vista em sala. Procure
 * evitar desperdicio de memoria alocando o array de contadores com o tamanho
 * sendo o mÃ¡ximo inteiro presente no array a ser ordenado.
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		if (array != null && leftIndex >= 0 && rightIndex >= 0 && leftIndex < rightIndex && leftIndex < array.length
				&& rightIndex < array.length && array.length != 0) {
			int max = procuraMaior(array, leftIndex, rightIndex);
			int[] count = new int[max + 1];
			int[] output = new int[rightIndex - leftIndex + 1];
			int i;
			for (i = leftIndex; i <= rightIndex; i++) {
				count[array[i]]++;
			}
			for (i = 1; i < count.length; i++) {
				count[i] += count[i - 1];
			}
			for (i = rightIndex; i >= leftIndex; i--) {
				output[count[array[i]] - 1] = array[i];
				count[array[i]]--;
			}
			for (i = leftIndex; i <= rightIndex; i++) {
				array[i] = output[i - leftIndex];
			}
		}
	}

	public int procuraMaior(Integer[] array, int left, int right) {
		int maior = array[left];
		for (int i = left + 1; i <= right; i++) {
			if (array[i] > maior) {
				maior = array[i];
			}
		}
		return maior;
	}
}
