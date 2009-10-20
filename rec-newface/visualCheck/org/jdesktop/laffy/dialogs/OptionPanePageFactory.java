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

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.jdesktop.laffy.DialogSection;
import org.jdesktop.laffy.I18nResourceHandler;
import org.jdesktop.laffy.Laffy;
import org.jdesktop.laffy.Page;

/**
 * OptionPanePageFactory
 * 
 * @author Created by Jasper Potts (Sep 6, 2007)
 * @version 1.0
 */
public class OptionPanePageFactory {
	//private static final String message = " This is a long message that you coild put in a option pane dialog.";
	private static final String message = I18nResourceHandler
			.getMessage("_This_is_a_long_message_that_you_coild_put_in_a_option_pane_dialog.");
	/*
	 * private static final String htmlMessage =
	 * I18nResourceHandler.getMessage("HTML_H2_Html_Header_H2_This_is_a_long_message_that_you_") +
	 * I18nResourceHandler.getMessage
	 * ("coild_put_in_a_option_pane_dialog._BR_It_is_written_using_HTML_so_it_can_have_mutiple_lines_and_") +
	 * I18nResourceHandler.getMessage("formating_BR__such_as__B_Bold_or_FONT_COLOR_Colored_Text.");
	 */
	private static final String htmlMessage = I18nResourceHandler.getMessage("HTML_Message");

	//private static final String waitMessage = "Its a slow day this may takee a while....";
	private static final String waitMessage = I18nResourceHandler.getMessage("Its_a_slow_day_this_may_takee_a_while....");

	public static Page createPage() {
		//DialogSection info = new DialogSection("Info Message") {
		DialogSection info = new DialogSection(I18nResourceHandler.getMessage("Info_Message")) {
			@Override
			protected void showDialog() {
				JOptionPane.showMessageDialog(Laffy.getInstance().getFrame(), message, getName(), JOptionPane.INFORMATION_MESSAGE);
			}

			@Override
			protected JComponent getDialogContent() {
				JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_OPTION);
				return optionPane;
			}
		};
		//DialogSection confirm = new DialogSection("Confirm") {
		DialogSection confirm = new DialogSection(I18nResourceHandler.getMessage("Confirm")) {
			@Override
			protected void showDialog() {
				JOptionPane.showConfirmDialog(Laffy.getInstance().getFrame(), message);
			}

			@Override
			protected JComponent getDialogContent() {
				JOptionPane optionPane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);
				return optionPane;
			}
		};
		//DialogSection input = new DialogSection("Input") {
		DialogSection input = new DialogSection(I18nResourceHandler.getMessage("Input")) {
			@Override
			protected void showDialog() {
				JOptionPane.showInputDialog(Laffy.getInstance().getFrame(), message);
			}

			@Override
			protected JComponent getDialogContent() {
				JOptionPane pane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				pane.setWantsInput(true);
				return pane;
			}
		};
		//DialogSection choiceInput = new DialogSection("Choice Input") {
		DialogSection choiceInput = new DialogSection(I18nResourceHandler.getMessage("Choice_Input")) {
			@Override
			protected void showDialog() {
				JOptionPane.showInputDialog(Laffy.getInstance().getFrame(), message, getName(), JOptionPane.QUESTION_MESSAGE, null,
				/*
				 * new Object[]{ "Option A", "Option B", "Option C", "Option D", "Option E", "Option F"
				 */
				new Object[] { I18nResourceHandler.getMessage("Option_A"), I18nResourceHandler.getMessage("Option_B"),
						I18nResourceHandler.getMessage("Option_C"), I18nResourceHandler.getMessage("Option_D"),
						I18nResourceHandler.getMessage("Option_E"), I18nResourceHandler.getMessage("Option_F") }, null);
			}

			@Override
			protected JComponent getDialogContent() {
				JOptionPane pane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				pane.setWantsInput(true);
				pane.setSelectionValues(new Object[] {
				/*
				 * "Option A", "Option B", "Option C", "Option D", "Option E", "Option F"});
				 */
				I18nResourceHandler.getMessage("Option_A"), I18nResourceHandler.getMessage("Option_B"),
						I18nResourceHandler.getMessage("Option_C"), I18nResourceHandler.getMessage("Option_D"),
						I18nResourceHandler.getMessage("Option_E"), I18nResourceHandler.getMessage("Option_F") });
				return pane;
			}
		};
		//DialogSection warning = new DialogSection("Warning Message") {
		DialogSection warning = new DialogSection(I18nResourceHandler.getMessage("Warning_Message")) {
			@Override
			protected void showDialog() {
				JOptionPane.showMessageDialog(Laffy.getInstance().getFrame(), htmlMessage, getName(), JOptionPane.WARNING_MESSAGE);
			}

			@Override
			protected JComponent getDialogContent() {
				JOptionPane optionPane = new JOptionPane(htmlMessage, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION);
				return optionPane;
			}
		};
		//DialogSection error = new DialogSection("Error Message with custom content") {
		DialogSection error = new DialogSection(I18nResourceHandler.getMessage("Error_Message_with_custom_content")) {
			@Override
			protected void showDialog() {
				JPanel panel = new JPanel(new BorderLayout(0, 10));
				panel.add(new JLabel(waitMessage), BorderLayout.CENTER);
				JProgressBar progressBar = new JProgressBar();
				progressBar.setIndeterminate(true);
				panel.add(progressBar, BorderLayout.SOUTH);
				JOptionPane.showOptionDialog(Laffy.getInstance().getFrame(), panel, getName(), JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE,
						//null, new Object[]{"Cancel"}, "Cancel");
						null, new Object[] { I18nResourceHandler.getMessage("Cancel") }, I18nResourceHandler.getMessage("Cancel"));
			}

			@Override
			protected JComponent getDialogContent() {
				JPanel panel = new JPanel(new BorderLayout(0, 10));
				panel.add(new JLabel(waitMessage), BorderLayout.CENTER);
				JProgressBar progressBar = new JProgressBar();
				progressBar.setIndeterminate(true);
				panel.add(progressBar, BorderLayout.SOUTH);
				JOptionPane optionPane = new JOptionPane(panel, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION,
				//null, new Object[]{"Cancel"}, "Cancel");
						null, new Object[] { I18nResourceHandler.getMessage("Cancel") }, I18nResourceHandler.getMessage("Cancel"));
				return optionPane;
			}
		};
		//return new Page("Option Panes", info, confirm, input, choiceInput, warning, error);
		return new Page(I18nResourceHandler.getMessage("Option_Panes"), info, confirm, input, choiceInput, warning, error);
	}

}
