package utils;

import frame.AddLinkManFrame;
import frame.ReceiveFrame;
import frame.RecycleFrame;
import frame.SendFrame;
import frame.SendedFrame;

/**
 * ��˵���������������ϵĸ�����
 * 
 * @author ����: LiuJunGuang
 * @version ����ʱ�䣺2011-2-24 ����03:13:30
 */
public class FrameFactory {
	private static FrameFactory factory = new FrameFactory();

	private FrameFactory() {
	}

	public static FrameFactory getFrameFactory() {
		return factory;
	}

	// �ռ������

	public ReceiveFrame getReceiveFrame() {
		return new ReceiveFrame();
	}

	// �ѷ����ʼ�����

	public SendedFrame getSendedFrame() {
		return new SendedFrame();
	}

	// �����ʼ�����

	public SendFrame getSendFrame() {
		return new SendFrame();
	}

	// ����վ����
	public RecycleFrame getRecycleFrame() {
		return new RecycleFrame();
	}

	// ��ϵ���б�

	public AddLinkManFrame getAddLinkManFrame() {
		return new AddLinkManFrame();
	}
}
