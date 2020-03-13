package main;

import javax.swing.UIManager;

import frame.LoginFrame;

public class MainMethod {

	/**
	 * @param args
	 *            ����������
	 */
	public static void main(String[] args) {
		// ���ý���Ϊ����ģʽ
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoginFrame().setVisible(true);
			}
		});

	}

}
