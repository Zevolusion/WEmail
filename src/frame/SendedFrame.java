package frame;

import java.util.List;

import utils.EditorUtils;
import utils.SendedMailTable;

/**
 * ��˵�����ѷ����ʼ�����
 * 
 * @author ����: LiuJunGuang
 * @version ����ʱ�䣺2011-2-23 ����07:40:23
 */
public class SendedFrame extends BaseReceiceFrame {
	private SendedMailTable sendedMail = null;// �ѷ����ʼ��б����

	public SendedFrame() {
		super("�ѷ����ʼ�");
		this.setFrameIcon(EditorUtils.createIcon("sended.png"));
		this.setPopupOne("ɾ���ʼ�", "delete.png");
		this.setPopupTwo("���·���", "send.png");
		sendedMail = SendedMailTable.getSendedMailTable();
		tableModel = sendedMail.getMailTableModel();
		sendedMail.setSendedMailTable(table);
		table.setModel(tableModel);
		mailContent.setText("");
	}

	public void doubleClick(int selectRom) {// ˫���¼��Ĵ���
		mailContent.setText(sendedMail.readMail(selectRom));// ��ȡ�ѷ��͵��ʼ�����
	}

	public void delete(int[] selectRoms, int mailState) {
		for (int i = 0; i < selectRoms.length// ѭ��ɾ��ÿ��
				&& selectRoms[i] < tableModel.getRowCount(); i++) {// ���ж�ÿ�е���Ч��
			sendedMail.deleteMail(selectRoms[i]);// ɾ���ʼ��б����е��ʼ�
			for (int j = i + 1; j < selectRoms.length; j++) {// �޸�ѡ���ʼ��Ժ��ÿ���ʼ����к�
				selectRoms[j]--;
			}
		}
	}

	/* �Ҽ��ڶ���ѡ�ѡ�� */
	@Override
	public void popupTwoisSelected(int[] selectRoms, List list) {
		for (int i = 0; i < selectRoms.length// ѭ��ɾ��ÿ��
				&& selectRoms[i] < tableModel.getRowCount(); i++) {// ���ж�ÿ�е���Ч��
			sendedMail.resend(selectRoms[i]);// ���·����ʼ�
			for (int j = i + 1; j < selectRoms.length; j++) {// �޸�ѡ���ʼ��Ժ��ÿ���ʼ����к�
				selectRoms[j]--;
			}
		}
	}
}
