package com.linkare.rec.am.experiment;

import java.util.List;

/**
 * 
 * 
 * @author Artur Correia - Linkare TI
 * 
 */
public class ColumnPhysicsValueDTO extends AbstractBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<PhysicsValueDTO> columnValues;

    public ColumnPhysicsValueDTO() {
    }

    public List<PhysicsValueDTO> getColumnValues() {
	return columnValues;
    }

    public void setColumnValues(List<PhysicsValueDTO> column) {
	this.columnValues = column;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("ColumnPhysicsValueDTO [columnValues=");
	builder.append(columnValues);
	builder.append("]");
	return builder.toString();
    }

}
