package utils;

/**
 * ���ģ��
 */
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class MailTableModel extends AbstractTableModel {
	private String[] columns = new String[] { "������", "����", "����ʱ��", "����" };

	private Vector<Vector<String>> v = new Vector<Vector<String>>();

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return v.size();
	}

	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public Object getValueAt(int row, int column) {
		return ((Vector) (v.get(row))).get(column);
	}

	// �õ�Vector<Vector<String>> ����
	public Vector<Vector<String>> getVector() {
		return v;
	}

	// �����е���ʾ����
	public void setColumens(String[] value) {
		columns = value;
	}
}