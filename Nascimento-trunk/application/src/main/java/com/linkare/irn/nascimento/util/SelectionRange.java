package com.linkare.irn.nascimento.util;

/**
 * Utility class that provides the ability to specify selection intervals to obtain paginated lists of results from a database query.
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class SelectionRange {

    /**
     * The number of records we want to fetch
     */
    private int count;

    /**
     * The first record from which we want to return our list of <code>count</code> records.
     */
    private int start;

    /**
     * 
     * @param start
     *            the first record from which we want to start our selection
     * @param count
     *            the number of records we want to fecth
     */
    public SelectionRange(final int start, final int count) {
	super();
	this.count = count;
	this.start = start;
    }

    /**
     * @return the count
     */
    public final int getCount() {
	return count;
    }

    /**
     * @param count
     *            the count to set
     */
    public void setCount(int count) {
	this.count = count;
    }

    /**
     * @return the start
     */
    public final int getStart() {
	return start;
    }

    /**
     * @param start
     *            the start to set
     */
    public void setStart(int start) {
	this.start = start;
    }
}