package problems;

/**
 * Calcula o floor e ceil de um numero em um array usando a estrategia de busca
 * binaria.
 * 
 * Restricoes: 
 * - Algoritmo in-place (nao pode usar memoria extra a nao ser variaveis locais) 
 * - O tempo de seu algoritmo deve ser O(log n).
 * 
 * @author Adalberto
 *
 */
public class FloorCeilBinarySearch implements FloorCeil {

	@Override
	public Integer floor(Integer[] array, Integer x) {
		return floor(array, x, 0, array.length - 1);
	}

	private Integer floor(Integer[] array, Integer x, int start, int f) {
		Integer middle = (start + f)/2;
		Integer retorno;
		if (start > f) {
			if (f < 0) {
				retorno = null;
			}else {
				retorno = array[f];
			}
		}else if (array[middle] == x){
			retorno = x;
		}else if (x < array[middle]) {
			retorno = floor(array, x, start, middle - 1);
		}else {
			retorno = floor(array, x, middle + 1, f);
		}
		return retorno;
	}

	@Override
	public Integer ceil(Integer[] array, Integer x) {
		return ceil(array, x, 0, array.length - 1);
	}

	private Integer ceil(Integer[] array, Integer x, int start, int f) {
		Integer middle = (start + f)/2;
		Integer retorno;
		if (start > f) {
			if (start > array.length - 1) {
				retorno = null;
			}else {
				retorno = array[start];
			}
		}else if (array[middle] == x){
			retorno = x;
		}else if (x < array[middle]) {
			retorno = ceil(array, x, start, middle - 1);
		}else {
			retorno = ceil(array, x, middle + 1, f);
		}
		return retorno;
	}
}
