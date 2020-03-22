package action;

import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import frame.MainFrame;

import utils.ContactListTabelModel;
import utils.SaveContacts2XML;

/**
 * 处理添加删除联系人事件
 */
public class ContactEventAction {
	Vector<Vector<String>> contactVectors = ContactListTabelModel.getVector();
	private JTextField name = null, nickName = null, emailAdress = null;
	private JTable contactList = null;

	public ContactEventAction(JTextField nameTF, JTextField nickNameTF,
			JTextField emailAdressTF, JTable contactList) {
		name = nameTF;// 名称
		nickName = nickNameTF;// 昵称
		emailAdress = emailAdressTF;// 邮箱地址
		this.contactList = contactList;
	}

	// 添加联系人
	public void addContact() {
		if (!checkRepeatEmail(emailAdress.getText().trim()))// 如果email地址不重复
			add();// 添加
		else {
			JOptionPane.showMessageDialog(null, "你添加的邮箱地址已存在，请勿重复添加！", "警告",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	// 删除联系人
	public void deleteContact(int selectRow) {
		if (selectRow < contactVectors.size() && selectRow != -1) {// 选中一行删除
			contactVectors.remove(selectRow);
			contactList.updateUI();
		} else {
			JOptionPane.showMessageDialog(null, "你没有选中任何一行不能删除！", "警告",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	// 确定修改联系人并将联系人保存为xml格式的文档
	public void ok() {
		SaveContacts2XML saveContactsXML = new SaveContacts2XML();
		saveContactsXML.saveContactXml("contact.xml", contactVectors);
		JOptionPane.showMessageDialog(null, "通讯录修改成功，文件名是 contact.xml", "提示",
				JOptionPane.INFORMATION_MESSAGE);
	}

	// 添加联系人
	private void add() {
		Vector<String> contactVector = new Vector<String>();
		contactVector.add(name.getText().trim());
		contactVector.add(nickName.getText().trim());
		contactVector.add(emailAdress.getText().trim());
		contactVectors.add(contactVector);
		contactList.updateUI();
	}

	// 检测邮箱是否重复
	private boolean checkRepeatEmail(String email) {
		boolean isRepeate = true;
		Vector<String> v = null;
		int count = contactVectors.size();// 得到联系人个数
		if (count > 0) {// 如果有联系人就比较
			for (int i = 0; i < count; i++) {
				v = contactVectors.get(i);// 得到联系人邮箱地址
				if (v.get(2).equals(email))// 新加的和已有的地址是否重复
					isRepeate = true;
				else
					isRepeate = false;
			}
		} else
			// 如果没有就直接添加
			isRepeate = false;
		return isRepeate;
	}
}
