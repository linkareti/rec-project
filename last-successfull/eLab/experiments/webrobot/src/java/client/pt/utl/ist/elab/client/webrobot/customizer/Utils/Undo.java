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
	java.util.Vector vectorUndo;
	Object undo = null;
	Object redo = null;

	/** Holds value of property undoable. */
	private boolean undoable = false;

	/** Holds value of property redoable. */
	private boolean redoable = false;

	/** Creates a new instance of Undo */
	public Undo() {
		vectorUndo = new java.util.Vector(0);
	}

	public void addElement(Object element) {
		vectorUndo.addElement(element);
		setUndoable(true);
		if (vectorUndo.size() == 30) {
			vectorUndo.remove(0);
		}
	}

	public Object undoElement() {
		try {
			undo = vectorUndo.get(vectorUndo.size() - 1 - (index + 1));
		} catch (java.lang.ArrayIndexOutOfBoundsException aioobe) {
			setUndoable(false);
		}
		index++;
		setRedoable(true);
		if (vectorUndo.size() < (index + 2)) {
			setUndoable(false);
		}
		return undo;
	}

	public Object redoElement() {
		try {
			redo = vectorUndo.get(vectorUndo.size() - index);
		} catch (java.lang.ArrayIndexOutOfBoundsException aioobe) {
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
		return this.undoable;
	}

	/**
	 * Setter for property undoable.
	 * 
	 * @param undoable New value of property undoable.
	 */
	public void setUndoable(boolean undoable) {
		this.undoable = undoable;
	}

	/**
	 * Getter for property redoable.
	 * 
	 * @return Value of property redoable.
	 */
	public boolean isRedoable() {
		return this.redoable;
	}

	/**
	 * Setter for property redoable.
	 * 
	 * @param redoable New value of property redoable.
	 */
	public void setRedoable(boolean redoable) {
		this.redoable = redoable;
	}
}
