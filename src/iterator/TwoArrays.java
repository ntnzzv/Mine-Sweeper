package iterator;

import java.util.Iterator;

public class TwoArrays implements Iterable<Integer> {

	int a1[];
	int a2[];

	public TwoArrays(int[] a1, int[] a2) {
		this.a1 = a1;
		this.a2 = a2;

	}

	private class MyIter implements Iterator<Integer> {
		int i = 0, j = 0;

		@Override
		public boolean hasNext() {

			return (i < a1.length || j < a2.length); // checks if indexes still in bounds

		}

		@Override
		public Integer next() {

			if (i < a1.length && j < a2.length) {
				return (i > j) ? a2[j++] : a1[i++]; // returns the values 1 by 1 shuffling between them
			}
			else if (j >= a2.length)return a1[i++];

			return a2[j++]; 

		}
	}

	@Override
	public Iterator<Integer> iterator() {

		return new MyIter();
	}

}
