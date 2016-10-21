/*
 * Undo.java
 *
 * Created on 16 de Fevereiro de 2003, 12:13
 */

package pt.utl.ist.elab.client.webrobot.customizer.Utils;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class Undo {
	int index = 0;
	java.util.List<Object> undoList;
	Object undo = null;
	Object redo = null;

	/** Holds value of property undoable. */
	private boolean undoable = false;

	/** Holds value of property redoable. */
	private boolean redoable = false;

	/** Creates a new instance of Undo */
	public Undo() {
		undoList = new java.util.ArrayList<Object>(0);
	}

	public void addElement(final Object element) {
		undoList.add(element);
		setUndoable(true);
		if (undoList.size() == 30) {
			undoList.remove(0);
		}
	}

	public Object undoElement() {
		try {
			undo = undoList.get(undoList.size() - 1 - (index + 1));
		} catch (final java.lang.ArrayIndexOutOfBoundsException aioobe) {
			setUndoable(false);
		}
		index++;
		setRedoable(true);
		if (undoList.size() < (index + 2)) {
			setUndoable(false);
		}
		return undo;
	}

	public Object redoElement() {
		try {
			redo = undoList.get(undoList.size() - index);
		} catch (final java.lang.ArrayIndexOutOfBoundsException aioobe) {
			setRedoable(false);
		}
		index--;
		setUndoable(true);
		return redo;
	}

	/**
	 * Getter for property undoable.
	 * 
	 * @return Value of property undoable.
	 */
	public boolean isUndoable() {
		return undoable;
	}

	/**
	 * Setter for property undoable.
	 * 
	 * @param undoable New value of property undoable.
	 */
	public void setUndoable(final boolean undoable) {
		this.undoable = undoable;
	}

	/**
	 * Getter for property redoable.
	 * 
	 * @return Value of property redoable.
	 */
	public boolean isRedoable() {
		return redoable;
	}

	/**
	 * Setter for property redoable.
	 * 
	 * @param redoable New value of property redoable.
	 */
	public void setRedoable(final boolean redoable) {
		this.redoable = redoable;
	}
}
