package pt.utl.ist.elab.client.statsound.displays;

/**
 * 
 * @author José Pedro Pereira - Linkare TI & Andr�
 */
public class MyDefaultTable extends com.linkare.rec.impl.baseUI.table.MultSeriesTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8343569383516675622L;

	/** Creates new form DefaultExperimentDataTable */
	public MyDefaultTable() {
		super(new MyTableModelProxy());

		setColArray(new int[] { 0, 1 });
	}

}
