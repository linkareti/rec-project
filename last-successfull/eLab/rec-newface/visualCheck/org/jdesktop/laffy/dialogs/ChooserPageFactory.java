/*
 * Laffy - Swing Look and Feel Sampler
 * Copyright (C) Sun Microsystems
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 */
package org.jdesktop.laffy.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.jdesktop.laffy.DialogSection;
import org.jdesktop.laffy.I18nResourceHandler;
import org.jdesktop.laffy.Laffy;
import org.jdesktop.laffy.Page;

import sun.swing.SwingUtilities2;

/**
 * ChooserPageFactory
 * 
 * @author Created by Jasper Potts (Sep 6, 2007)
 * @version 1.0
 */
public class ChooserPageFactory {

	public static Page createPage() {
		//DialogSection open = new DialogSection("FileChooser: Open") {
		DialogSection open = new DialogSection(I18nResourceHandler.getMessage("FileChooser_Open")) {
			@Override
			protected void showDialog() {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
				int res = fileChooser.showOpenDialog(Laffy.getInstance().getFrame());
				System.out.println("res_" + (res == JFileChooser.CANCEL_OPTION ? "canceled" : "approved"));
				System.out.println(Arrays.asList(fileChooser.getSelectedFiles()));
				System.out.println(fileChooser.getSelectedFile());
			}

			@Override
			protected JComponent getDialogContent() {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
				return fileChooser;
			}
		};
		//DialogSection openFilesOnly = new DialogSection("FileChooser: Open Files Only") {
		DialogSection openFilesOnly = new DialogSection(I18nResourceHandler.getMessage("FileChooser_Open_Files_Only")) {
			@Override
			protected void showDialog() {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int res = fileChooser.showOpenDialog(Laffy.getInstance().getFrame());
				System.out.println("res_" + (res == JFileChooser.CANCEL_OPTION ? "canceled" : "approved"));
				System.out.println(Arrays.asList(fileChooser.getSelectedFiles()));
				System.out.println(fileChooser.getSelectedFile());
			}

			@Override
			protected JComponent getDialogContent() {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				return fileChooser;
			}
		};
		//DialogSection save = new DialogSection("FileChooser: Save") {
		DialogSection save = new DialogSection(I18nResourceHandler.getMessage("FileChooser_Save")) {
			@Override
			protected void showDialog() {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
				int res = fileChooser.showSaveDialog(Laffy.getInstance().getFrame());
				System.out.println("res_" + (res == JFileChooser.CANCEL_OPTION ? "canceled" : "approved"));
				System.out.println(Arrays.asList(fileChooser.getSelectedFiles()));
				System.out.println(fileChooser.getSelectedFile());
			}

			@Override
			protected JComponent getDialogContent() {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
				return fileChooser;
			}
		};
		//DialogSection color = new DialogSection("ColorChooser") {
		DialogSection color = new DialogSection(I18nResourceHandler.getMessage("ColorChooser")) {
			@Override
			protected void showDialog() {
				//JColorChooser.showDialog(Laffy.getInstance().getFrame(), "ColorChooser", Color.CYAN);
				JColorChooser.showDialog(Laffy.getInstance().getFrame(), I18nResourceHandler.getMessage("ColorChooser"), Color.CYAN);
			}

			@Override
			protected JComponent getDialogContent() {
				JPanel panel = new JPanel(new BorderLayout());
				JColorChooser colorChooser = new JColorChooser();
				panel.add(colorChooser, BorderLayout.CENTER);
				String okString = UIManager.getString("ColorChooser.okText");
				String cancelString = UIManager.getString("ColorChooser.cancelText");
				String resetString = UIManager.getString("ColorChooser.resetText");
				JPanel buttonPane = new JPanel();
				buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
				JButton okButton = new JButton(okString);
				buttonPane.add(okButton);
				JButton cancelButton = new JButton(cancelString);
				buttonPane.add(cancelButton);
				JButton resetButton = new JButton(resetString);
				int mnemonic = SwingUtilities2.getUIDefaultsInt("ColorChooser.resetMnemonic", -1);
				if (mnemonic != -1) {
					resetButton.setMnemonic(mnemonic);
				}
				buttonPane.add(resetButton);
				panel.add(buttonPane, BorderLayout.SOUTH);
				return panel;
			}
		};
		//return new Page("File & Color Choosers", open, openFilesOnly, save, color);
		return new Page(I18nResourceHandler.getMessage("File_&_Color_Choosers"), open, openFilesOnly, save, color);
	}

}
