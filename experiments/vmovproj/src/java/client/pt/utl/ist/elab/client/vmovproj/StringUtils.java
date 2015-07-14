/*
 * StringUtils.java
 *
 * Created on March 23, 2005, 4:38 PM
 */

package pt.utl.ist.elab.client.vmovproj;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author andre
 */
public abstract class StringUtils {
	public static String[] splitArroundPoint(final String tosplit) {
		final List<String> v = new ArrayList<String>();
		final StringTokenizer token = new StringTokenizer(tosplit, ".");
		while (token.hasMoreTokens()) {
			v.add(token.nextToken());
		}
		return (String[]) v.toArray(new String[0]);
	}
}
