package mailutil;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 类说明：SMTP/POP3授权验证类
 * 
 * @author 作者: LiuJunGuang
 * @version 创建时间：2011-4-22 下午06:42:08
 */

class SmtpPop3Auth extends Authenticator {
	String user, password;

	// 设置帐号信息
	void setAccount(String user, String password) {
		this.user = user;
		this.password = password;
	}

	// 取得PasswordAuthentication对象
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, password);
	}
}