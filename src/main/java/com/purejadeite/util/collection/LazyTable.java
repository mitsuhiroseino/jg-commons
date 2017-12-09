package com.purejadeite.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 行列の範囲を自動的に拡張するtable
 *
 * @author mitsuhiroseino
 */
public class LazyTable<E> extends AbstractTable<E> {

	/**
	 * コンストラクタ
	 */
	public LazyTable() {
		super();
	}

	/**
	 * コンストラクタ
	 */
	public <T extends Collection<R>, R extends Collection<E>> LazyTable(T table) {
		super(table);
	}


	/**
	 * コンストラクタ
	 */
	public LazyTable(E[][] table) {
		super(table);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E set(int rowIndex, int columnIndex, E value) {
		expand(table, rowIndex);
		List<E> row = table.get(rowIndex);
		if (row == null) {
			row = new ArrayList<>();
			table.set(rowIndex, row);
		}
		expand(row, columnIndex);
		if (columnSize <= columnIndex) {
			columnSize = columnIndex + 1;
		}
		return row.set(columnIndex, value);
	}

	/**
	 * 指定のindexが設定できるまでlistを拡張します
	 * @param list リスト
	 * @param index インデックス
	 */
	private void expand(List<?> list, int index) {
		if (index < list.size()) {
			return;
		}
		while (list.size() <= index) {
			list.add(null);
		}
	}

	@Override
	public int size() {
		return table.size();
	}

	@Override
	public boolean isEmpty() {
		return table.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		for (List<E> row : table) {
			if (row.contains(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterator<List<E>> iterator() {
		return table.iterator();
	}

	@Override
	public Object[] toArray() {
		return table.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return table.toArray(a);
	}

	@Override
	public boolean add(List<E> e) {
		return table.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return table.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return table.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends List<E>> c) {
		return table.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends List<E>> c) {
		return table.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return table.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return table.retainAll(c);
	}

	@Override
	public void clear() {
		table.clear();
	}

	@Override
	public List<E> get(int index) {
		return table.get(index);
	}

	@Override
	public List<E> set(int index, List<E> element) {
		return table.set(index, element);
	}

	@Override
	public void add(int index, List<E> element) {
		table.add(index, element);
	}

	@Override
	public List<E> remove(int index) {
		return table.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return table.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return table.lastIndexOf(o);
	}

	@Override
	public ListIterator<List<E>> listIterator() {
		return table.listIterator();
	}

	@Override
	public ListIterator<List<E>> listIterator(int index) {
		return table.listIterator(index);
	}

	@Override
	public List<List<E>> subList(int fromIndex, int toIndex) {
		return table.subList(fromIndex, toIndex);
	}

}
