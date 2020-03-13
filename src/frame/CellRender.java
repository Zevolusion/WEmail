package frame;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

//�Զ����齨��Ⱦ��ʽ(���븽����ʱ���õ�)
class CellRender extends JLabel implements ListCellRenderer {
	private Icon icon = null;

	public CellRender(Icon icon) {
		this.icon = icon;
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		setText(String.valueOf(value));
		setIcon(icon);
		setOpaque(true);
		setBackground(isSelected ? list.getSelectionBackground() : list
				.getBackground());
		setForeground(isSelected ? list.getSelectionForeground() : list
				.getForeground());
		return this;
	}
}
