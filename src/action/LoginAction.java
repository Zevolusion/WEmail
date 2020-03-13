/**
 * ��֤��½�Ƿ�ɹ�
 */
package action;

import mailutil.GetMail;
import mailutil.SendAttachMail;
import utils.EditorUtils;

public class LoginAction {
	private String POP3Host = ""; // POP3������
	private String SMTPHost = ""; // SMTP������
	private String user = ""; // ��¼���������ʺ�
	private String password = ""; // ��¼������������
	private GetMail getMail = null;
	private SendAttachMail sendMail = null;

	// ���������Ĺ��췽��
	public LoginAction(String smtpHost, String pop3Host, String user,
			String password) {
		super();
		POP3Host = pop3Host;
		SMTPHost = smtpHost;
		this.user = user;
		this.password = password;
		// ʵ�������ʼ�����
		getMail = GetMail.getMailInstantiate();
		getMail.setPOP3Host(POP3Host);
		getMail.setUser(user);
		getMail.setPassword(password);
		// ʵ�������ʼ�������
		sendMail = SendAttachMail.getSendMailInstantiate();
		sendMail.setSMTPHost(SMTPHost);
		sendMail.setUser(user);
		sendMail.setPassword(password);
	}

	// �жϵ�½�Ƿ�ɹ�
	public boolean isLogin() {

		boolean isLogin = false;
		// �ж��û����Ƿ�Ϊ��
		if (checkUser()) {
			try {
				sendMail.connect();// ���ӷ�����������ֻ��ͨ�����ӷ�������֤��ݣ����߷���ʱ�����쳣��
				isLogin = true;
			} catch (Exception e) {
				isLogin = false;
				e.printStackTrace();
			}
		}
		return isLogin;
	}

	// ��֤�û��������ݵ���Ч��
	public boolean checkUser() {
		boolean check = false;
		boolean checkSMTP = SMTPHost.toLowerCase().startsWith("smtp");// ��֤smtp������
		boolean checkPOP = POP3Host.toLowerCase().startsWith("pop");// ��֤pop������
		boolean checkPassword = !"".equals(password) && password.length() >= 1;
		boolean checkUser = EditorUtils.checkEmailAdress(user);// ��֤�������Ч��
		if (checkSMTP && checkPOP && checkPassword && checkUser) {
			check = true;// ��֤ͨ��
		}
		return check;
	}
}
