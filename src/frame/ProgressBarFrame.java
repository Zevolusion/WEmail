package frame;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * ��˵���������������� �����С���ģʽ���ֽ�����
 * 
 * @author ����: user
 * @version ����ʱ�䣺2011-2-25 ����04:26:52
 */
public class ProgressBarFrame extends JDialog {
	private JLabel progressBarLabel = null;// �������Ϸ�����ʾ��ǩ
	private JProgressBar progressBar = null;// ����������
	private JPanel progressBarPanel = null;

	public ProgressBarFrame() {
	}

	/**
	 * ���������췽��
	 * 
	 * @param title
	 *            ����������
	 * @param state
	 *            ������״̬ ��1 ����ģʽ�Ľ�������2����ģʽ�Ľ�����
	 */
	public ProgressBarFrame(JFrame frame, String title, String message) {
		super(frame, title);
		init(frame, title, message);
	}

	// ����ĳ�ʼ��
	private void initFrame() {
		this.setAlwaysOnTop(true);
		Toolkit tk = getToolkit();// �����Ļ�Ŀ��͸�
		Dimension dim = tk.getScreenSize();
		this.setResizable(false);// ���ô��ڲ��ɵ�����С
		this.setBounds(dim.width / 2 - 110, dim.height / 2 - 40, 258, 142);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		validate();
	}

	/**
	 * @param message
	 *            ������Ҫ��ʾ����Ϣ
	 * @param state
	 *            ��������ģʽ
	 */
	private void init(JFrame frame, String title, String message) {
		this.setLayout(null);// ���ò���Ϊ������
		
		progressBarLabel = new JLabel();
		progressBarLabel.setText(message);
		progressBarLabel.setBounds(2, 2, 200, 25);
		this.add(progressBarLabel);

		progressBar = new JProgressBar();
		progressBar.setBounds(2, 30, 200, 25);
		progressBar.setIndeterminate(true);
		this.add(progressBar);
		
		progressBarPanel = new JPanel();
		progressBarPanel.setBounds(0, 0, 258, 142);
		this.add(progressBarPanel);
		initFrame();
	}

}