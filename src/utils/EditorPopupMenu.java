package utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class EditorPopupMenu {

	/** ��Ӽ��������� */
	private Action cutAction = new DefaultEditorKit.CutAction();
	/** ��Ӹ��������� */
	private Action copyAction = new DefaultEditorKit.CopyAction();
	/** ���ճ�������� */
	private Action pasteAction = new DefaultEditorKit.PasteAction();
	/** ��ӳ��������� */
	protected UndoManager undoMenager = new UndoManager();
	/** ��ӳ��������� */
	private UndoAction undoAction = new UndoAction();
	/** ��ӻָ������� */
	private RedoAction redoAction = new RedoAction();
	/** �����ڵ�ǰ�ĵ��ϵı༭�� */
	protected UndoableEditListener undoHandler = new UndoHandler();
	private JPopupMenu popup = null;
	private JMenuItem copy = null;
	private JMenuItem paste = null;
	private JMenuItem cut = null;
	private JMenuItem undo = null;
	private JMenuItem redo = null;
	private JMenuItem insertIcon = null;

	public EditorPopupMenu(final JTextPane textPane) {
		textPane.getDocument().addUndoableEditListener(undoHandler);
		popup = new JPopupMenu();
		copy = new JMenuItem(copyAction);
		copy.setText("����");
		copy.setIcon(EditorUtils.createIcon("copy.png"));
		paste = new JMenuItem(pasteAction);
		paste.setText("ճ��");
		paste.setIcon(EditorUtils.createIcon("paste.png"));
		cut = new JMenuItem(cutAction);
		cut.setText("����");
		cut.setIcon(EditorUtils.createIcon("cut.png"));
		undo = new JMenuItem(undoAction);
		undo.setText("����");
		undo.setIcon(EditorUtils.createIcon("undo.png"));
		redo = new JMenuItem(redoAction);
		redo.setText("����");
		redo.setIcon(EditorUtils.createIcon("redo.png"));
		insertIcon = new JMenuItem();
		insertIcon.setText("����ͼƬ");
		insertIcon.setIcon(EditorUtils.createIcon("image.png"));
		insertIcon.addActionListener(new ActionListener() {// ����ͼƬ�¼�
					public void actionPerformed(ActionEvent arg0) {
						File f = new File(".");// �õ���ǰĿ¼
						JFileChooser chooser = new JFileChooser(f);// ����һ����ǰ·�����ļ�ѡ����
						if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {// ���ѡ��ȷ����
							File file = chooser.getSelectedFile();
							textPane.insertIcon(EditorUtils.createIcon(file
									.getAbsolutePath()));
						}
					}
				});
		popup.add(copy);
		popup.add(paste);
		popup.add(cut);
		popup.addSeparator();// ����ָ���
		popup.add(undo);
		popup.add(redo);
		popup.addSeparator();// ����ָ���
		popup.add(insertIcon);
	}

	// �༭������Ҽ�����Ӧ
	public void rightMouseButton(MouseEvent e) {
		popup.show(e.getComponent(), e.getX(), e.getY());// ��ʾ�����˵�
	}

	// ����
	class UndoAction extends AbstractAction {
		public UndoAction() {
			super("Undo");
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			try {
				undoMenager.undo();
			} catch (CannotUndoException ex) {
				System.out.println("Unable to undo: " + ex);
				ex.printStackTrace();
			}
			update();
			redoAction.update();
		}

		protected void update() {
			if (undoMenager.canUndo()) {
				setEnabled(true);
				putValue(Action.NAME, undoMenager.getUndoPresentationName());
			} else {
				setEnabled(false);
				putValue(Action.NAME, "����");
			}
		}
	}

	// ����
	class RedoAction extends AbstractAction {

		public RedoAction() {
			super("Redo");
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			try {
				undoMenager.redo();
			} catch (CannotRedoException ex) {
				System.err.println("Unable to redo: " + ex);
				ex.printStackTrace();
			}
			update();
			undoAction.update();
		}

		protected void update() {
			if (undoMenager.canRedo()) {
				setEnabled(true);
				putValue(Action.NAME, undoMenager.getRedoPresentationName());
			} else {
				setEnabled(false);
				putValue(Action.NAME, "����");
			}
		}
	}

	class UndoHandler implements UndoableEditListener {
		public void undoableEditHappened(UndoableEditEvent e) {
			undoMenager.addEdit(e.getEdit());
			undoAction.update();
			redoAction.update();
		}
	}
}
