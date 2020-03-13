package utils.bickerTray;

import java.io.InputStream;
import java.net.URL;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * ��˵��������������
 * 
 * @author ����: LiuJunGuang
 * @version ����ʱ�䣺2011-2-26 ����03:41:25
 */
public class MyAudioPlayer {
	private URL url = null;// �����ļ���URl
	private AudioStream as = null;// ������

	public MyAudioPlayer() {
		try {
			url = MyAudioPlayer.class.getResource("/newmail.wav");// ��ȡ�����ļ���url
			InputStream is = url.openStream();// ��������ļ���������
			as = new AudioStream(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��������
	public void play() {
		AudioPlayer.player.start(as);// ��AudioPlayer��̬��Աplayer.start��������
	}
}
