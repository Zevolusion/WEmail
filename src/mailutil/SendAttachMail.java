package mailutil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class SendAttachMail {

	private String SMTPHost = ""; // SMTP������
	private String user = ""; // ��¼SMTP���������ʺ�
	private String password = ""; // ��¼SMTP������������

	private String from = ""; // ����������
	private Address[] to = null; // �ռ�������
	private String subject = ""; // �ʼ�����
	private String content = ""; // �ʼ�����
	private Address[] copy_to = null;// �����ʼ���
	private Session mailSession = null;
	private Transport transport = null;
	private ArrayList<String> filename = new ArrayList<String>(); // �����ļ���
	private static SendAttachMail sendMail = new SendAttachMail();

	// �޲������췽��
	private SendAttachMail() {
	}

	// ���ر�������ʵ��
	public static SendAttachMail getSendMailInstantiate() {
		return sendMail;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		try {
			// ������ݵ���������
			content = new String(content.getBytes("ISO8859-1"), "gbk");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.content = content;
	}

	public ArrayList<String> getFilename() {
		return filename;
	}

	public void setFilename(ArrayList<String> filename) {
		Iterator<String> iterator = filename.iterator();
		ArrayList<String> attachArrayList = new ArrayList<String>();
		while (iterator.hasNext()) {
			String attachment = iterator.next();
			try {
				// ����ļ�������������
				attachment = MimeUtility.decodeText(attachment);
				// ���ļ�·���е�'\'�滻��'/'
				attachment = attachment.replaceAll("\\\\", "/");
				attachArrayList.add(attachment);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		this.filename = attachArrayList;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSMTPHost() {
		return SMTPHost;
	}

	public void setSMTPHost(String host) {
		SMTPHost = host;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		try {
			// ����������������
			subject = MimeUtility.encodeText(subject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.subject = subject;
	}

	public Address[] getTo() {
		return to;
	}

	public void setTo(String toto) {
		int i = 0;
		StringTokenizer tokenizer = new StringTokenizer(toto, ";");
		to = new Address[tokenizer.countTokens()];// ��̬�ľ�������ĳ���
		while (tokenizer.hasMoreTokens()) {
			String d = tokenizer.nextToken();
			try {
				d = MimeUtility.encodeText(d);
				to[i] = new InternetAddress(d);// ���ַ���ת��Ϊ����
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Address[] getCopy_to() {
		return copy_to;
	}

	// ���ó���
	public void setCopy_to(String copyTo) {
		int i = 0;
		StringTokenizer tokenizer = new StringTokenizer(copyTo, ";");
		copy_to = new Address[tokenizer.countTokens()];// ��̬�ľ�������ĳ���
		while (tokenizer.hasMoreTokens()) {
			String d = tokenizer.nextToken();
			try {
				d = MimeUtility.encodeText(d);
				copy_to[i] = new InternetAddress(d);// ���ַ���ת��Ϊ����
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
	}

	public void connect() throws Exception {

		// ����һ�����Զ���
		Properties props = new Properties();
		// ָ��SMTP������
		props.put("mail.smtp.host", SMTPHost);
		// ָ���Ƿ���ҪSMTP��֤
		props.put("mail.smtp.auth", "true");
		// ����һ����Ȩ��֤����
		SmtpPop3Auth auth = new SmtpPop3Auth();
		auth.setAccount(user, password);
		// ����һ��Session����
		mailSession = Session.getDefaultInstance(props, auth);
		// �����Ƿ����
		mailSession.setDebug(false);
		if (transport != null)
			transport.close();// �ر�����
		// ����һ��Transport����
		transport = mailSession.getTransport("smtp");
		// ����SMTP������
		transport.connect(SMTPHost, user, password);
	}

	// �����ʼ�
	public String send() {
		String issend = "";
		try {// ����smtp������
			connect();
			// ����һ��MimeMessage ����
			MimeMessage message = new MimeMessage(mailSession);

			// ָ������������
			message.setFrom(new InternetAddress(from));
			// ָ���ռ�������
			message.addRecipients(Message.RecipientType.TO, to);
			if (!"".equals(copy_to))
				// ָ������������
				message.addRecipients(Message.RecipientType.CC, copy_to);
			// ָ���ʼ�����
			message.setSubject(subject);
			// ָ���ʼ���������
			message.setSentDate(new Date());
			// ָ���ʼ����ȼ� 1������ 3����ͨ 5������
			message.setHeader("X-Priority", "1");
			message.saveChanges();
			// �жϸ����Ƿ�Ϊ��
			if (!filename.isEmpty()) {
				// �½�һ��MimeMultipart����������Ŷ��BodyPart����
				Multipart container = new MimeMultipart();
				// �½�һ������ż����ݵ�BodyPart����
				BodyPart textBodyPart = new MimeBodyPart();
				// ��BodyPart�����������ݺ͸�ʽ/���뷽ʽ
				textBodyPart.setContent(content, "text/html;charset=gbk");
				// �������ż����ݵ�BodyPart���뵽MimeMultipart������
				container.addBodyPart(textBodyPart);
				Iterator<String> fileIterator = filename.iterator();
				while (fileIterator.hasNext()) {// �������и���
					String attachmentString = fileIterator.next();
					// �½�һ������ż�������BodyPart����
					BodyPart fileBodyPart = new MimeBodyPart();
					// �������ļ���Ϊ����
					FileDataSource fds = new FileDataSource(attachmentString);
					fileBodyPart.setDataHandler(new DataHandler(fds));
					// �����ʼ��и����ļ�������������
					String attachName = fds.getName();
					attachName = MimeUtility.encodeText(attachName);
					// �趨�����ļ���
					fileBodyPart.setFileName(attachName);
					// ��������BodyPart������뵽container��
					container.addBodyPart(fileBodyPart);
				}
				// ��container��Ϊ��Ϣ���������
				message.setContent(container);
			} else {// û�и��������
				message.setContent(content, "text/html;charset=gbk");
			}
			// �����ʼ�
			Transport.send(message, message.getAllRecipients());
			if (transport != null)
				transport.close();
		} catch (Exception ex) {
			issend = ex.getMessage();
		}
		return issend;
	}
}
