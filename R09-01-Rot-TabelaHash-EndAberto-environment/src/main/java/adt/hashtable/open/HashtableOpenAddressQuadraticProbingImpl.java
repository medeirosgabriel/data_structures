package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionOpenAddress;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable>
		extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressQuadraticProbingImpl(int size,
			HashFunctionClosedAddressMethod method, int c1, int c2) {
		super(size);
		hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
		if (!this.isFull()) {
			int i = 0, key = 0;
			boolean stop = false;
			while (!stop && i < this.table.length) {
				key = this.getKey(element, i);
				if (this.table[key] == null || this.table[key].equals(this.deletedElement) || this.table[key].equals(element)) {
					this.table[key] = element;
					this.elements ++;
					stop = true;
				}else {
					this.COLLISIONS ++;
				}
				i++;
			}
		}else {
			throw new HashtableOverflowException();
		}
	}

	@Override
	public void remove(T element) {
		if (!this.isEmpty()) {
			int i = 0, key = 0;
			boolean stop = false;
			while (!stop && i < this.table.length) {
				key = this.getKey(element, i);
				if (this.table[key] == null) {
					stop = true;
				}else if (this.table[key].equals(element)) {
					this.table[key] = this.deletedElement;
					this.elements --;
					stop = true;
				}
				i++;
			}
		}
	}

	@Override
	public T search(T element) {
		T ret = null;
		if (this.indexOf(element) != -1) {
			ret = (T) this.table[this.indexOf(element)];
		}
		return ret;
	}

	@Override
	public int indexOf(T element) {
		int ret = -1;
		if (!this.isEmpty()) {
			int i = 0, key = 0;
			boolean stop = false;
			while (!stop && i < this.table.length) {
				key = this.getKey(element, i);
				if (this.table[key] == null) {
					stop = true;
				}else if (this.table[key].equals(element)) {
					ret = key;
					stop = true;
				}
				i++;
			}
		}
		return ret;
	}
	
	public int getKey(T element, int probe) {
		 return Math.abs(((HashFunctionOpenAddress<T>) this.hashFunction).hash(element, probe));
	}
}
