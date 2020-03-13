package mailutil;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeUtility;

public class GetMail {

	private String POP3Host = ""; // POP3������
	private String user = ""; // ��¼POP3���������ʺ�
	private String password = ""; // ��¼POP3������������

	private Session session = null;
	private Folder folder = null;
	private Store store = null;
	private Message[] msg = null;// �ʼ���Ϣ
	private static final GetMail getMail = new GetMail();
	private AttachFile attachFile = new AttachFile();

	// �޲����Ĺ��캯��
	private GetMail() {
	}

	// ����GetMail�Ķ���
	public static GetMail getMailInstantiate() {
		return getMail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPOP3Host() {
		return POP3Host;
	}

	public void setPOP3Host(String host) {
		POP3Host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	// �����ʼ�������
	public void connect() throws Exception {
		// ����һ����Ȩ��֤����
		SmtpPop3Auth auth = new SmtpPop3Auth();
		auth.setAccount(user, password);

		// ȡ��һ��Session����
		Properties prop = new Properties();
		prop.put("mail.pop3.host", POP3Host);
		session = Session.getDefaultInstance(prop, auth);

		// ȡ��һ��Store����
		store = session.getStore("pop3");
		store.connect(POP3Host, user, password);
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

	// ��������ʼ����б�
	public Message[] getAllMail() throws Exception {
		// ����POP3����
		connect();// �����ʼ�������

		// ȡ��һ��Folder����
		folder = store.getDefaultFolder().getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		// ȡ�����е�Message����
		msg = folder.getMessages();
		FetchProfile profile = new FetchProfile();
		profile.add(FetchProfile.Item.ENVELOPE);
		folder.fetch(msg, profile);
		closeConnect();// �ر������ʼ�������
		return msg;
	}

	// ȡ���ʼ��б����Ϣ
	@SuppressWarnings("unchecked")
	public List getMailInfo(Message[] msg) throws Exception {
		List result = new ArrayList();
		Map map = null;
		Multipart mp = null;
		BodyPart part = null;
		String disp = null;
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy��MM��dd�� hh:mm-ss");
		Enumeration enumMail = null;
		// ȡ��ÿ���ʼ�����Ϣ
		for (int i = 0; i < msg.length; i++) {
			map = new HashMap();
			// ��ȡ�ʼ�ID
			enumMail = msg[i].getAllHeaders();
			Header h = null;
			while (enumMail.hasMoreElements()) {
				h = (Header) enumMail.nextElement();
				if (h.getName().equals("Message-ID")
						|| h.getName().equals("Message-Id")) {
					map.put("ID", h.getValue());
				}
			}
			// ��ȡ�ʼ�����
			map.put("subject", msg[i].getSubject());
			// ��ȡ������
			map.put("sender",
					MimeUtility.decodeText(msg[i].getFrom()[0].toString()));
			// ��ȡ�ʼ���������
			map.put("senddate", fmt.format(msg[i].getSentDate()));
			// ��ȡ�ʼ���С
			map.put("size", new Integer(msg[i].getSize()));
			map.put("hasAttach", "&nbsp;");
			// �ж��Ƿ��и���
			if (msg[i].isMimeType("multipart/*")) {
				mp = (Multipart) msg[i].getContent();
				// ����ÿ��Miltipart����
				for (int j = 0; j < mp.getCount(); j++) {
					part = mp.getBodyPart(j);
					disp = part.getDisposition();
					// ����и���
					if (disp != null
							&& (disp.equals(Part.ATTACHMENT) || disp
									.equals(Part.INLINE))) {
						// �����и���������ֵ
						map.put("hasAttach", "��");
					}
				}
			}
			result.add(map);
		}
		return result;
	}

	// ����ָ���ʼ�
	public Message findMail(Message[] msg, String id) throws Exception {
		Enumeration enumMail = null;
		Header h = null;
		for (int i = 0; i < msg.length; i++) {
			enumMail = msg[i].getAllHeaders();
			// �����ʼ�ͷ�е�Message-ID��
			while (enumMail.hasMoreElements()) {
				h = (Header) enumMail.nextElement();
				// ���ݴ����message-id������Ŀ���ʼ�
				boolean messageId = (h.getName().equals("Message-ID"))
						|| (h.getName().equals("Message-Id"));
				if (messageId && (h.getValue().equals(id))) {
					return msg[i];
				}
			}
		}
		return null;
	}

	// ɾ���ʼ�
	public boolean deleteMail(String[] id) {
		boolean isDelete = false;
		try {
			connect();// �����ʼ�������
			// ȡ��һ��Folder����
			folder = store.getDefaultFolder().getFolder("INBOX");
			folder.open(Folder.READ_WRITE);
			Message[] deletemsg = folder.getMessages();
			Message mes = null;
			for (int i = 0; i < id.length; i++) {
				mes = findMail(deletemsg, id[i]);// ����ָ���ʼ�
				mes.setFlag(Flags.Flag.DELETED, true);// ���ʼ����Ϊɾ��
			}
			closeConnect();// �ر��ʼ������������Ӳ�ɾ���ʼ�
			CheckNewMialUtil.isCheck = true;// �������ʼ����
			isDelete = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDelete;
	}

	// ��ȡ�ʼ�����
	@SuppressWarnings("unchecked")
	public Map readMail(String id) throws Exception {
		Map map = new HashMap();
		// �ҵ�Ŀ���ʼ�
		Message readmsg = findMail(msg, id);
		// ��ȡ�ʼ�����
		map.put("subject", readmsg.getSubject());
		// ��ȡ������
		map.put("sender",
				MimeUtility.decodeText(readmsg.getFrom()[0].toString()));
		map.put("attach", "");
		// ȡ���ʼ�����
		if (readmsg.isMimeType("text/*")) {
			map.put("content", readmsg.getContent().toString());
		} else {
			Multipart mp = (Multipart) readmsg.getContent();
			BodyPart part = null;
			String disp = null;
			StringBuffer result = new StringBuffer();
			// ����ÿ��Miltipart����
			for (int j = 0; j < mp.getCount(); j++) {
				part = mp.getBodyPart(j);
				disp = part.getDisposition();
				// ����и���
				if (disp != null
						&& (disp.equals(Part.ATTACHMENT) || disp
								.equals(Part.INLINE))) {
					// ȡ�ø����ļ���
					String filename = MimeUtility
							.decodeText(part.getFileName());// ������ĸ�����������
					map.put("attach", filename);
					// ���ظ���
					InputStream in = part.getInputStream();// ����������
					if (attachFile.isDownload(filename))
						attachFile.choicePath(filename, in);// // ���ظ���
				} else {
					// ��ʾ�����ʼ���������
					result.append(getPart(part, j, 1));
				}
			}
			map.put("content", result.toString());
		}
		return map;
	}

	// x������ȷ������html 1 ��ʽ��ʾ������plain 2
	// ����ʱgetPart��part��i��1��;
	// ��ʾ�����ʼ�����������
	public String getPart(Part part, int partNum, int x) throws

	MessagingException, IOException {
		String s = "";
		String s1 = "";
		String s2 = "";
		String s5 = "";
		String sct = part.getContentType();
		if (sct == null) {
			s = "part ��Ч";
			return s;
		}
		ContentType ct = new ContentType(sct);
		if (ct.match("text/html") || ct.match("text/plain")) {
			// display text/plain inline
			s1 = "" + (String) part.getContent() + "";
		} else if (partNum != 0) {
			String temp = "";
			if ((temp = part.getFileName()) != null) {
				s2 = "Filename: " + temp + "";
			}
		}
		if (part.isMimeType("multipart/alternative")) {
			String s6 = "";
			String s7 = "";
			Multipart mp = (Multipart) part.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; i++) {
				if (mp.getBodyPart(i).isMimeType("text/plain"))
					s7 = getPart(mp.getBodyPart(i), i, 2);
				else if (mp.getBodyPart(i).isMimeType("text/html"))
					s6 = getPart(mp.getBodyPart(i), i, 1);
			}
			if (x == 1) {// html��ʽ���ַ���
				s5 = s6;
			}
			if (x == 2) {// paint���͵��ַ���
				s5 = s7;
			}
			return s5;
		}
		s = s1 + s2;
		return s;
	}

}
