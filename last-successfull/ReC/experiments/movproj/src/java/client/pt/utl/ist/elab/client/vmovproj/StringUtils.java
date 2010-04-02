/*
 * StringUtils.java
 *
 * Created on March 23, 2005, 4:38 PM
 */

package pt.utl.ist.elab.client.vmovproj;

/**
 * 
 * @author andre
 */
public abstract class StringUtils {
	public static String[] splitArroundPoint(String tosplit) {
		java.util.Vector v = new java.util.Vector();
		java.util.StringTokenizer token = new java.util.StringTokenizer(tosplit, ".");
		while (token.hasMoreTokens())
			v.add(token.nextToken());
		return (String[]) v.toArray(new String[0]);
	}
}
