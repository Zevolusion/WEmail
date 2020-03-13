package frame;

import utils.EditorUtils;
import utils.ReceiveMailTable;

public class ReceiveFrame extends BaseReceiceFrame {

	public ReceiveFrame() {
		super("�ռ���");
		this.setFrameIcon(EditorUtils.createIcon("receive.png"));
		mail2Table = ReceiveMailTable.getMail2Table();// �����µ��߳���ʾ�ʼ��б�
		mail2Table.setDeleteMailTable(this.table);
		tableModel = mail2Table.getMailTableModel();
		table.setModel(tableModel);
		this.setPopupOne("ɾ��", "delete.png");
		this.setPopupTwo("����ɾ��", "forverdelete.png");
		this.setPopupThree("ˢ���ռ���", "refresh.jpg", true);
	}

	public void doubleClick(int selectRom) {// ˫���¼��Ĵ���
		mailContent.setText(ReceiveMailTable.readMail(ReceiveMailTable.list,
				selectRom));
	}

	// ɾ���ʼ�
	public void delete(int[] selectRoms, int mailState) {// mailState �ж��ʼ���ɾ��״̬
		for (int i = 0; i < selectRoms.length// ѭ��ɾ��ÿ��
				&& selectRoms[i] < tableModel.getRowCount(); i++) {// ���ж�ÿ�е���Ч��
			if (mailState == 1)
				mail2Table.moveMail2Recycle(selectRoms[i]);// ���ʼ��������վ
			mail2Table.deleteMail(selectRoms[i]);// ɾ���ʼ��б����е��ʼ�
			for (int j = i + 1; j < selectRoms.length; j++) {// �޸�ѡ���ʼ��Ժ��ÿ���ʼ����к�
				selectRoms[j]--;
			}
		}
	}

	// �Ҽ�������ѡ�ѡ��ˢ���ռ���
	private void popupThreeisSelected() {

	}
}
