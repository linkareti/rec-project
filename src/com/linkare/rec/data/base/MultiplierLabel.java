/*
 * MultiplierBean.java
 *
 * Created on 27 de Junho de 2002, 11:57
 */

package com.linkare.rec.data.base;
import javax.swing.JLabel;

import com.linkare.rec.data.Multiplier;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class MultiplierLabel extends JLabel
{

	/** Holds value of property multiplier. */
	private com.linkare.rec.data.Multiplier multiplier;

	/** Creates a new instance of MultiplierBean */
	public MultiplierLabel()
	{
	}

	/** Creates a new instance of MultiplierBean */
	public MultiplierLabel(Multiplier multiplier)
	{
		setMultiplier(multiplier);
	}
	/** Getter for property multiplier.
	 * @return Value of property multiplier.
	 */
	public com.linkare.rec.data.Multiplier getMultiplier()
	{
		return this.multiplier;
	}

	/** Setter for property multiplier.
	 * @param multiplier New value of property multiplier.
	 */
	public synchronized void setMultiplier(com.linkare.rec.data.Multiplier multiplier)
	{
		this.multiplier = multiplier;
		repaint();
	}


	public void setText(String text)
	{}//cannot set Text Property

	public String getText()
	{
		if(multiplier!=null)
			return multiplier.toString();
		else
			return "";
	}


}
