package com.purejadeite.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2次元リストの抽象クラス
 *
 * @author mitsuhiroseino
 */
abstract public class AbstractTable<E> implements Table<E> {

	/**
	 * ネストされたリスト
	 */
	protected List<List<E>> table;

	/**
	 * オプション情報
	 */
	protected Map<String, Object> options;

	/**
	 * 列数の最大値
	 */
	protected int columnSize = 0;

	/**
	 * コンストラクタ
	 */
	public AbstractTable() {
		super();
		table = new ArrayList<>();
		options = new HashMap<>();
	}

	/**
	 * コンストラクタ
	 */
	public <T extends Collection<R>, R extends Collection<E>> AbstractTable(T table) {
		super();
		List<List<E>> thisTable = new ArrayList<>();
		for (Collection<E> row : table) {
			columnSize = Math.max(columnSize, row.size());
			List<E> thisRow = new ArrayList<>(row);
			thisTable.add(thisRow);
		}
		this.table = thisTable;
		options = new HashMap<>();
	}

	/**
	 * コンストラクタ
	 */
	public AbstractTable(E[][] table) {
		super();
		List<List<E>> thisTable = new ArrayList<>();
		for (E[] row : table) {
			columnSize = Math.max(columnSize, row.length);
			List<E> thisRow = new ArrayList<>();
			for (E value : row) {
				thisRow.add(value);
			}
			thisTable.add(thisRow);
		}
		this.table = thisTable;
		options = new HashMap<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOption(String key, Object value) {
		options.put(key, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public <V extends Object> V getOption(String key) {
		try {
			return (V) options.get(key);
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getOptions() {
		return options;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowSize() {
		return table.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getColumnSize() {
		return columnSize;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<List<E>> getTable() {
		return table;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<List<E>> getAdjustedTable() {
		return getAdjustedTable(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<List<E>> getAdjustedTable(int minColumnSize) {
		return getAdjustedTable(0, minColumnSize);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<List<E>> getAdjustedTable(int minRowSize, int minColumnSize) {
		List<List<E>> adjustedTable = new ArrayList<>();
		int rowSize = Math.max(getRowSize(), minRowSize);
		for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {
			adjustedTable.add(getAdjustedRow(rowIndex, minColumnSize));
		}
		return adjustedTable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<E> getRow(int rowIndex) {
		if (rowIndex < table.size()) {
			return table.get(rowIndex);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<E> getAdjustedRow(int rowIndex) {
		return getAdjustedRow(rowIndex, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<E> getAdjustedRow(int rowIndex, int minColumnSize) {
		int cSize = Math.max(minColumnSize, columnSize);
		List<E> adjustedRow = new ArrayList<>();
		List<E> row = getRow(rowIndex);
		if (row == null) {
			for (int columnIndex = 0; columnIndex < cSize; columnIndex++) {
				adjustedRow.add(null);
			}
		} else {
			for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {
				adjustedRow.add(row.get(columnIndex));
			}
			for (int columnIndex = row.size(); columnIndex < cSize; columnIndex++) {
				adjustedRow.add(null);
			}
		}
		return adjustedRow;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E get(int rowIndex, int columnIndex) {
		List<E> row = getRow(rowIndex);
		if (row != null && columnIndex < row.size()) {
			return row.get(columnIndex);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addValue(E value) {
		List<E> row = table.get(table.size() - 1);
		row.add(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addRow() {
		int rowIndex = table.size();
		table.add(new ArrayList<E>());
		return rowIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + ":[");
		boolean firstRow = true;
		for (List<E> row : table) {
			if (firstRow) {
				firstRow = false;
			} else {
				builder.append(",");
			}
			boolean firstValue = true;
			if (row != null) {
				builder.append("[");
				for (E value : row) {
					if (firstValue) {
						firstValue = false;
					} else {
						builder.append(",");
					}
					builder.append(value);
				}
				builder.append("]");
			} else {
				builder.append("null");
			}
		}
		builder.append("]");
		return builder.toString();
	}

}
