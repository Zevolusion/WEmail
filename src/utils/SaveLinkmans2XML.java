package utils;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * ��˵����������ϵ��Ϊxml�ļ�
 * 
 * @author ����: LiuJunGuang
 * @version ����ʱ�䣺2011-1-2 ����06:04:19
 */
public class SaveLinkmans2XML {
	private Document document = null;// xml�ĵ�����
	private Element linkmansElement = null;// ������Ԫ��

	// ������ϵ��Ϊxml�ĵ�
	public boolean saveLinkmanXml(String fileName,
			Vector<Vector<String>> linkmanVector) {
		boolean isSave = false;
		if (linkmanVector.size() > 0) {
			initXml();// ��ʼ��xml�ĵ�
			Iterator<Vector<String>> iterator = linkmanVector.iterator();
			while (iterator.hasNext()) {
				Vector<String> vector = iterator.next();
				String name = vector.get(0);// �õ�����
				String nickname = vector.get(1);// �õ��ǳ�
				String email = vector.get(2);// �õ������ַ
				saveLinkmanInfor(name, nickname, email);
			}
			saveXMLFile(fileName);// ������ϵ��xml�ļ�
			isSave = true;// ����ɹ�
		}
		return isSave;

	}

	// ��ʼ��xml�ĵ�
	private void initXml() {
		// ʹ�� DocumentHelper �ഴ��һ���ĵ�ʵ���� DocumentHelper ������ XML �ĵ��ڵ�� dom4j API
		// �����ࡣ
		document = DocumentHelper.createDocument();
		// ʹ�� addElement() ����������Ԫ�� catalog ��addElement() ������ XML �ĵ�������Ԫ�ء�
		linkmansElement = document.addElement("linkmans");
		// �� linkmans Ԫ����ʹ�� addComment() �������ע�͡�An XML catalog����
		linkmansElement.addComment("�ҵ���ϵ���б�");
	}

	private void saveLinkmanInfor(String name, String nickname,
			String emailadress) {
		// �� linkmans Ԫ����ʹ�� addElement() �������� linkman Ԫ�ء�
		Element linkmanElement = linkmansElement.addElement("linkman");

		// ��ӽڵ�linkman���ӽڵ�name��
		Element nameElement = linkmanElement.addElement("name");
		nameElement.setText(name);

		// ��ӽڵ�linkman���ӽڵ�nickname
		Element nicknameElement = linkmanElement.addElement("nickname");
		nicknameElement.setText(nickname);

		// ��ӽڵ�linkman���ӽڵ�emailadress
		Element emailadressElement = linkmanElement.addElement("emailadress");
		emailadressElement.setText(emailadress);
	}

	// ����ͨѶ¼xml�ļ�
	private void saveXMLFile(String fileName) {
		XMLWriter output;
		try {
			OutputFormat format = new OutputFormat();
			format.setEncoding("gbk");
			String path = System.getProperty("user.dir");
			fileName = path + "/" + fileName;
			output = new XMLWriter(new FileWriter(new File(fileName)), format);
			output.write(document);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
