package utils;

import frame.AddContactFrame;
import frame.ReceiveFrame;
import frame.RecycleFrame;
import frame.SendFrame;
import frame.SendedFrame;

/**
 * 产生主界面上的各种类
 */
public class FrameFactory {
	private static FrameFactory factory = new FrameFactory();

	private FrameFactory() {
	}

	public static FrameFactory getFrameFactory() {
		return factory;
	}

	// 收件箱对象

	public ReceiveFrame getReceiveFrame() {
		return new ReceiveFrame();
	}

	// 已发送邮件对象

	public SendedFrame getSendedFrame() {
		return new SendedFrame();
	}

	// 发送邮件对象

	public SendFrame getSendFrame() {
		return new SendFrame();
	}

	// 回收站对象
	public RecycleFrame getRecycleFrame() {
		return new RecycleFrame();
	}

	// 联系人列表

	public AddContactFrame getAddContactFrame() {
		return new AddContactFrame();
	}
}
