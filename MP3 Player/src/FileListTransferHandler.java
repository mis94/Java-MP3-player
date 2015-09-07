import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

class FileListTransferHandler extends TransferHandler {
	private JList list;
	private DefaultListModel listModel;
	private Vector<File> storage;

	public FileListTransferHandler(JList list, DefaultListModel model, Vector<File> v) {
		this.list = list;
		this.listModel = model;
		storage = v;
	}

	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}

	public boolean canImport(TransferSupport ts) {
		return ts.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
	}

	public boolean importData(TransferSupport ts) {
		try {
			@SuppressWarnings("rawtypes")
			List data = (List) ts.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
			if (data.size() < 1) {
				return false;
			}

			for (Object item : data) {
				File file = (File) item;

				storage.add(file);
				listModel.addElement(storage.lastElement().getName().substring(0,
						(int) storage.lastElement().getName().length() - 4));

			}

			list.setModel(listModel);
			return true;

		} catch (UnsupportedFlavorException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
	
}