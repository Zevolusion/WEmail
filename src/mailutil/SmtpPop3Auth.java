package mailutil;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * SMTP/POP3��Ȩ��֤��
 */

class SmtpPop3Auth extends Authenticator {
	String user, password;

	// �����ʺ���Ϣ
	void setAccount(String user, String password) {
		this.user = user;
		this.password = password;
	}

	// ȡ��PasswordAuthentication����
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, password);
	}
}