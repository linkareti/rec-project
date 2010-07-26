/*
 * SaveData.java
 *
 * Created on 23 de Fevereiro de 2003, 11:31
 */

package pt.utl.ist.elab.client.webrobot.customizer.Utils;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class SaveData {

	StringBuffer saveToString = null;
	java.io.FileOutputStream fileOutPutStream = null;
	String lineSeparator = System.getProperty("line.separator");
	pt.utl.ist.elab.client.webrobot.customizer.Comps.Block comp = null;
	pt.utl.ist.elab.client.webrobot.customizer.Comps.Block[][] matrix;
	int shift = 0;

	/** Creates a new instance of SaveData */
	public SaveData(java.io.File file, pt.utl.ist.elab.client.webrobot.customizer.Comps.Block[][] matrix,
			pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfInOut confInOut,
			pt.utl.ist.elab.client.webrobot.customizer.Comps.IVPWM ivpwm, int maxRow, int maxColumn) {
		boolean isFirstTime = true;
		this.matrix = matrix;
		int maxAllowedColumn = getMaxAllowedColumn(maxRow, maxColumn);
		pt.utl.ist.elab.client.webrobot.customizer.Models.ModelIVPWM modelIVPWM = (pt.utl.ist.elab.client.webrobot.customizer.Models.ModelIVPWM) ivpwm
				.getDataModel();
		try {
			if (file != null) {
				fileOutPutStream = new java.io.FileOutputStream(file);
			} else {
				saveToString = new StringBuffer();
			}
			for (int iCol = 1; iCol < maxAllowedColumn + 1; iCol++) {
				isFirstTime = true;
				write("--->coluna=" + iCol + lineSeparator);
				for (int iRow = 2; iRow < maxRow + 1; iRow++) {
					comp = matrix[iRow][iCol];
					if (comp == null
							|| comp.getClass().getName().startsWith(
									"pt.utl.ist.elab.client.webrobot.customizer.Comps.HorLine")) {
						continue;
					}
					write("coluna=" + iCol + ", nivel=" + iRow + ", tipo=" + comp.getTipo() + ", d1="
							+ comp.getDataModel().getD1() + " ,d2=" + comp.getDataModel().getD2() + " ,d3="
							+ comp.getDataModel().getD3() + ", valor=" + comp.getDataModel().getValor() + ", valor2="
							+ comp.getDataModel().getValor2() + ", flag=" + comp.getDataModel().getFlag());
					if (comp.isPaintBottom()) {
						write(" baixo=a");
					} else {
						write(" baixo=b");
					}
					shift = -1;// because of matrix[x][-1]
					for (int cCol = iCol; cCol > 0; cCol--) {
						/**
						 * Only start counting horlines, after it finds a
						 * component different from an Horline between two
						 * HorLines
						 */
						if (matrix[iRow][cCol - 1] != null
								&& matrix[iRow][cCol - 1].getClass().getName().startsWith(
										"pt.utl.ist.elab.client.webrobot.customizer.Comps.HorLine") && isFirstTime) {
						} else {
							isFirstTime = false;
							shift++;
						}
					}
					if (shift == 0 || (matrix[iRow][iCol - 1] != null && !matrix[iRow][iCol - 1].isPaintLeft())
							|| matrix[iRow][iCol - 1] == null) {
						write(", esquerda=b" + lineSeparator);
					} else {
						write(", esquerda=a " + shift + "," + lineSeparator);
					}
				}
			}
			write("k" + lineSeparator);
			write("0 255 255 255 0 200 200 255" + lineSeparator);
			write("0 0 255 200 255 200 255 255" + lineSeparator);
			write("150 150 150" + lineSeparator);
			write(confInOut.getModel().getB0() + " " + confInOut.getModel().getB1() + " "
					+ confInOut.getModel().getB2() + " " + confInOut.getModel().getB3() + " "
					+ confInOut.getModel().getB4() + " " + confInOut.getModel().getB5() + " "
					+ confInOut.getModel().getB6() + " " + confInOut.getModel().getB7() + " "
					+ confInOut.getModel().getC0() + " " + confInOut.getModel().getC3() + lineSeparator);
			write(confInOut.getModel().getA1() + " " + confInOut.getModel().getA2() + " "
					+ confInOut.getModel().getA3() + " " + confInOut.getModel().getA4() + " 0" + lineSeparator);
			write(confInOut.getModel().getV1() + " " + confInOut.getModel().getV2() + " "
					+ confInOut.getModel().getV3() + " " + confInOut.getModel().getV4() + " "
					+ confInOut.getModel().getV5() + lineSeparator);

			Object[][] iValues = modelIVPWM.getIValues();
			write("y" + iValues.length + lineSeparator);
			for (int iRow = 0; iRow < iValues.length; iRow++) {
				write((String) iValues[iRow][0] + "\t");
				int firstSize = String.valueOf((Integer) iValues[iRow][1]).length();
				int secondSize = String.valueOf((Integer) iValues[iRow][2]).length();
				write(convertSpaces(firstSize) + String.valueOf((Integer) iValues[iRow][1]) + "\t"
						+ convertSpaces(secondSize) + String.valueOf((Integer) iValues[iRow][2]) + lineSeparator);

			}
			write(modelIVPWM.getI0Value() + " " + modelIVPWM.getI1Value() + " " + modelIVPWM.getI2Value() + " "
					+ modelIVPWM.getI3Value() + " " + modelIVPWM.getI4Value() + " " + modelIVPWM.getI5Value() + " "
					+ modelIVPWM.getI6Value() + " " + modelIVPWM.getI7Value() + lineSeparator);
			write(modelIVPWM.getI0State() + " " + modelIVPWM.getI1State() + " " + modelIVPWM.getI2State() + " "
					+ modelIVPWM.getI3State() + " " + modelIVPWM.getI4State() + " " + modelIVPWM.getI5State() + " "
					+ modelIVPWM.getI6State() + " " + modelIVPWM.getI7State() + lineSeparator);
			write("y");
			if (file != null) {
				fileOutPutStream.close();
			}
		} catch (java.io.IOException ioe) {
			System.out.println("Erro ao tentar escrever no ficheiro..." + ioe);
		}
	}

	private String convertSpaces(int width) {
		if (width == 3) {
			return "";
		} else if (width == 2) {
			return " ";
		} else if (width == 1) {
			return "  ";
		} else {
			return "";
		}
	}

	private int getMaxAllowedColumn(int maxRow, int maxColumn) {
		boolean abort = false;
		int iCol;
		for (iCol = 1; iCol < maxColumn + 1; iCol++) {
			for (int iRow = 1; iRow < maxRow + 1; iRow++) {
				abort = true;
				if (matrix[iRow][iCol] != null) {
					abort = false;
					break;
				}
			}
			if (abort) {
				break;
			}
		}
		return (iCol - 1);
	}

	private void write(String write) {
		if (fileOutPutStream != null) {
			try {
				fileOutPutStream.write(write.getBytes());
			} catch (java.io.IOException ioe) {
				System.out.println("Erro ao tentar escrever no ficheiro..." + ioe);
			}
		} else {
			saveToString.append(write);
		}
	}

	public String getSavedString() {
		if (saveToString != null) {
			return saveToString.toString();
		} else {
			return null;
		}
	}
}
