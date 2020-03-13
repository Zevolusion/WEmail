package action;

import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import frame.MainFrame;

import utils.LinkmanListTabelModel;
import utils.SaveLinkmans2XML;

/**
 * ��˵�����������ɾ����ϵ���¼�
 * 
 * @author ����: LiuJunGuang
 * @version ����ʱ�䣺2011-1-1 ����05:11:31
 */
public class LinkmanEventAction {
	Vector<Vector<String>> linkmanVectors = LinkmanListTabelModel.getVector();
	private JTextField name = null, nickName = null, emailAdress = null;
	private JTable linkmanList = null;

	public LinkmanEventAction(JTextField nameTF, JTextField nickNameTF,
			JTextField emailAdressTF, JTable linkmanList) {
		name = nameTF;// ����
		nickName = nickNameTF;// �ǳ�
		emailAdress = emailAdressTF;// �����ַ
		this.linkmanList = linkmanList;
	}

	// �����ϵ��
	public void addLinkman() {
		if (!checkRepeatEmail(emailAdress.getText().trim()))// ���email��ַ���ظ�
			add();// ���
		else {
			JOptionPane.showMessageDialog(null, "����ӵ������ַ�Ѵ��ڣ������ظ���ӣ�", "����",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	// ɾ����ϵ��
	public void deleteLinkman(int selectRow) {
		if (selectRow < linkmanVectors.size() && selectRow != -1) {// ѡ��һ��ɾ��
			linkmanVectors.remove(selectRow);
			linkmanList.updateUI();
		} else {
			JOptionPane.showMessageDialog(null, "��û��ѡ���κ�һ�в���ɾ����", "����",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	// ȷ���޸���ϵ�˲�����ϵ�˱���Ϊxml��ʽ���ĵ�
	public void ok() {
		SaveLinkmans2XML saveLinkmansXML = new SaveLinkmans2XML();
		saveLinkmansXML.saveLinkmanXml("linkman.xml", linkmanVectors);
		JOptionPane.showMessageDialog(null, "ͨѶ¼�޸ĳɹ����ļ����� linkman.xml", "��ʾ",
				JOptionPane.INFORMATION_MESSAGE);
	}

	// �����ϵ��
	private void add() {
		Vector<String> linkmanVector = new Vector<String>();
		linkmanVector.add(name.getText().trim());
		linkmanVector.add(nickName.getText().trim());
		linkmanVector.add(emailAdress.getText().trim());
		linkmanVectors.add(linkmanVector);
		linkmanList.updateUI();
	}

	// ��������Ƿ��ظ�
	private boolean checkRepeatEmail(String email) {
		boolean isRepeate = true;
		Vector<String> v = null;
		int count = linkmanVectors.size();// �õ���ϵ�˸���
		if (count > 0) {// �������ϵ�˾ͱȽ�
			for (int i = 0; i < count; i++) {
				v = linkmanVectors.get(i);// �õ���ϵ�������ַ
				if (v.get(2).equals(email))// �¼ӵĺ����еĵ�ַ�Ƿ��ظ�
					isRepeate = true;
				else
					isRepeate = false;
			}
		} else
			// ���û�о�ֱ�����
			isRepeate = false;
		return isRepeate;
	}
}
