package frame;

import java.util.List;

import utils.EditorUtils;
import utils.ReceiveMailTable;
import utils.RecycleMailTable;

/**
 * ��˵��������վ ɾ���ʼ��Ļָ��ͳ���ɾ��
 * 
 * @author ����: user
 * @version ����ʱ�䣺2011-2-22 ����06:08:58
 */
public class RecycleFrame extends BaseReceiceFrame {
	private RecycleMailTable recycleMail = null;

	public RecycleFrame() {
		super("����վ");
		this.setFrameIcon(EditorUtils.createIcon("deleted.png"));
		recycleMail = RecycleMailTable.getRecycleMail();
		tableModel = recycleMail.getMailTableModel();
		recycleMail.setRecycleMailTable(table);
		table.setModel(tableModel);
		this.setPopupOne("�ָ��ʼ�", "undo.png");
		this.setPopupTwo("����ɾ��", "forverdelete.png");
	}

	public void doubleClick(int selectRom) {// ˫���¼��Ĵ���
		mailContent.setText(ReceiveMailTable.readMail(
				RecycleMailTable.listCopy, selectRom));
	}

	/**
	 * �Ҽ��ڶ���ѡ�ѡ��
	 * 
	 * @param selectRoms
	 */
	public void popupTwoisSelected(int[] selectRoms, List list) {
		super.popupTwoisSelected(selectRoms, RecycleMailTable.listCopy);
	}

	// �ָ��ʼ�
	public void delete(int[] selectRoms, int mailState) {
		for (int i = 0; i < selectRoms.length// ѭ��ɾ��ÿ��
				&& selectRoms[i] < tableModel.getRowCount(); i++) {// ���ж�ÿ�е���Ч��
			if (mailState == 1)
				recycleMail.recycleMail(selectRoms[i]);
			recycleMail.deleteMail(selectRoms[i]);// ɾ���ʼ��б����е��ʼ�
			for (int j = i + 1; j < selectRoms.length; j++) {// �޸�ѡ���ʼ��Ժ��ÿ���ʼ����к�
				selectRoms[j]--;
			}
		}
	}
}
