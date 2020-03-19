package utils;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/**
 * ��ϵ���б���ģ��
 */
public class LinkmanListTabelModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private final String[] COLUMNS = new String[] { "����", "�ǳ�", "���������ַ" };
	private static Vector<Vector<String>> v = new Vector<Vector<String>>();

	public LinkmanListTabelModel() {
	}

	@Override
	public int getColumnCount() {// �õ�������
		return COLUMNS.length;
	}

	@Override
	public int getRowCount() {// �õ�����
		return v.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {// �õ�ĳ���е�ֵ
		return ((Vector<String>) (v.get(rowIndex))).get(columnIndex);
	}

	public String getColumnName(int column) {// �õ�����
		return COLUMNS[column];
	}

	// �õ�Vector<Vector<String>> ����
	public static Vector<Vector<String>> getVector() {
		return v;
	}

}
