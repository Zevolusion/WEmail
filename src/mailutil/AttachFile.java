package mailutil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * ��˵���������Ĳ�����
 * 
 * @author ����: LiuJunGuang
 * @version ����ʱ�䣺2011-4-22 ����06:21:05
 */
public class AttachFile {

	public AttachFile() {
	}

	// �ʼ���������·����ѡ��
	public void choicePath(final String filename, final InputStream in) {
		File f = new File(".");// �õ���ǰuser����Ŀ¼
		JFileChooser chooser = new JFileChooser(f);// ����һ����ǰ·�����ļ�ѡ����
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {// ���ѡ��ȷ����
			final File f1 = chooser.getSelectedFile();// �õ�ѡ����ļ�
			new Thread() {// �������߳����ظ���
				public void run() {
					downloadFile(f1.getPath() + "/" + filename, in);// ���ظ���
				}
			}.start();
		}
	}

	// �ʼ���������ȷ��
	public boolean isDownload(String filename) {
		boolean download = false;
		int n = JOptionPane.showConfirmDialog(null, "���ʼ����и���  \"" + filename
				+ "\" �Ƿ����أ�", "ѯ��", JOptionPane.YES_NO_OPTION);
		if (n == 0)
			download = true;
		return download;
	}

	// �ʼ�����������
	public void downloadFile(String filename, InputStream in) {// filename
																// �ļ�����in ������
		FileOutputStream out = null;// ���������
		try {
			out = new FileOutputStream(new File(filename));
			byte[] content = new byte[1024];
			int read = 0;
			while ((read = in.read(content)) != -1) {
				out.write(content);
			}
			JOptionPane.showMessageDialog(null, "����   \"" + filename
					+ "\"���سɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			System.out.println("GetMail����downloadFile����  �����ļ�����");
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();// �ر������
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (in != null)
						in.close();// �ر�������
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
