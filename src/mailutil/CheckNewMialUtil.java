package mailutil;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import utils.CheckNewMail;

public class CheckNewMialUtil {
	private String POP3Host = ""; // POP3������
	private String user = ""; // ��¼POP3���������ʺ�
	private String password = ""; // ��¼POP3������������

	private Session session = null;
	private Folder folder = null;
	private Store store = null;
	private GetMail getMail = GetMail.getMailInstantiate();

	public CheckNewMialUtil() {
		POP3Host = getMail.getPOP3Host();
		user = getMail.getUser();
		password = getMail.getPassword();
	}

	// �����ʼ�������
	public void connect() {
		// ����һ����Ȩ��֤����
		SmtpPop3Auth auth = new SmtpPop3Auth();
		auth.setAccount(user, password);

		// ȡ��һ��Session����
		Properties prop = new Properties();
		prop.put("mail.pop3.host", POP3Host);
		session = Session.getDefaultInstance(prop, auth);

		// ȡ��һ��Store����
		try {
			store = session.getStore("pop3");
			store.connect(POP3Host, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// �ر�����
	public void closeConnect() {
		try {
			if (folder != null)
				folder.close(true);// �ر�����ʱ�Ƿ�ɾ���ʼ���trueɾ���ʼ�
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
			try {
				if (store != null)
					store.close();// �ر��ռ�������
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isCheck = true;

	// ������ʼ�
	public int checkNewMail() {
		int count = 0;
		connect();
		try {
			folder = store.getDefaultFolder().getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			count = folder.getMessageCount();
			if (isCheck) {
				CheckNewMail.setNewMailCount(count);
				isCheck = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnect();
		}
		return count;
	}
}
