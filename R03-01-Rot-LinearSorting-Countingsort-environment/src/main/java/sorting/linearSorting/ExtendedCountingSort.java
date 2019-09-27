package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. Desta vez este
 * algoritmo deve satisfazer os seguitnes requisitos: - Alocar o tamanho minimo
 * possivel para o array de contadores (C) - Ser capaz de ordenar arrays
 * contendo numeros negativos
 */
public class ExtendedCountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		if (array != null && leftIndex >= 0 && rightIndex >= 0 && leftIndex < rightIndex && leftIndex < array.length
				&& rightIndex < array.length && array.length != 0) {
			int max = procuraMaior(array, leftIndex, rightIndex);
			int min = procuraMenor(array, leftIndex, rightIndex);
			int[] count = new int[max - min + 1];
			int[] output = new int[array.length];
			int i;
			for (i = leftIndex; i <= rightIndex; i++) {
				count[array[i] - min]++;
			}
			for (i = 1; i < count.length; i++) {
				count[i] += count[i - 1];
			}
			for (i = rightIndex; i >= leftIndex; i--) {
				output[count[array[i] - min] - 1] = array[i];
				count[array[i] - min]--;
			}
			for (i = leftIndex; i <= rightIndex; i++) {
				array[i] = output[i - leftIndex];
			}
		}
	}

	private int procuraMaior(Integer[] array, int left, int right) {
		int maior = array[left];
		for (int i = left + 1; i <= right; i++) {
			if (array[i] > maior) {
				maior = array[i];
			}
		}
		return maior;
	}

	private int procuraMenor(Integer[] array, int left, int right) {
		int menor = array[left];
		for (int i = left + 1; i <= right; i++) {
			if (array[i] < menor) {
				menor = array[i];
			}
		}
		return menor;
	}

}
