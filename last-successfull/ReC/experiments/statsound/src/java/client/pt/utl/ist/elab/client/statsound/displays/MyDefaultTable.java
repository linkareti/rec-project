package pt.utl.ist.elab.client.statsound.displays;

/**
 * 
 * @author José Pedro Pereira - Linkare TI & Andr�
 */
public class MyDefaultTable extends com.linkare.rec.impl.baseUI.table.MultSeriesTable {

	/** Creates new form DefaultExperimentDataTable */
	public MyDefaultTable() {
		super(new MyTableModelProxy());

		setColArray(new int[] { 0, 1 });
	}

}
