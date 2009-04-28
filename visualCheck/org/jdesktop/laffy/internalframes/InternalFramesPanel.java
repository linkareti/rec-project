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
package org.jdesktop.laffy.internalframes;

import org.jdesktop.laffy.I18nResourceHandler;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * InternalFramesPanel
 *
 * @author Created by Jasper Potts (Dec 13, 2007)
 * @version 1.0
 */
public class InternalFramesPanel extends JPanel {

    private final ImageIcon bugDuke = new ImageIcon(InternalFramesPanel.class.getClassLoader().getResource(
            "org/jdesktop/laffy/icons/big-duke.png"));
    private JDesktopPane desktop;
    private int index = 1;

    /** Creates a new <code>JPanel</code> with a double buffer and a flow layout. */
    public InternalFramesPanel() {
        super(new BorderLayout());
        desktop = new JDesktopPane();
        add(desktop, BorderLayout.CENTER);
        setMinimumSize(new Dimension(700, 450));
        setPreferredSize(new Dimension(700, 450));
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        createFrame(BorderLayout.NORTH);
        createFrame(BorderLayout.SOUTH);
        createFrame(BorderLayout.EAST);
        createFrame(BorderLayout.WEST);

        //JInternalFrame controlsFrame = new JInternalFrame("Controls", false, false, true, true);
        JInternalFrame controlsFrame = new JInternalFrame(I18nResourceHandler.getMessage("Controls"), false, false, true, true);
        desktop.add(controlsFrame);
        controlsFrame.setFrameIcon(new ImageIcon(InternalFramesPanel.class.getClassLoader().getResource(
                "org/jdesktop/laffy/icons/home-48.png")));
        controlsFrame.getContentPane().setLayout(new FlowLayout());
        //JButton create = new JButton("Create New Frame");
        JButton create = new JButton(I18nResourceHandler.getMessage("Create_New_Frame"));
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createFrame(BorderLayout.NORTH);
            }
        });
        controlsFrame.add(create);
        controlsFrame.setLocation(500, 10);
        controlsFrame.pack();
        controlsFrame.setVisible(true);

    }

    private void createFrame(String toolBarSide) {
        //JInternalFrame frame1 = new JInternalFrame("Frame " + index, true, true, true, true);
        JInternalFrame frame1 = new JInternalFrame(I18nResourceHandler.getMessage("Frame_") + index, true, true, true, true);
        desktop.add(frame1);
        frame1.getContentPane().setLayout(new BorderLayout());
        JLabel dukeLabel = new JLabel(bugDuke);
        JScrollPane scrollPane = new JScrollPane(dukeLabel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        frame1.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JToolBar toolBar = new JToolBar((toolBarSide == BorderLayout.EAST || toolBarSide == BorderLayout.WEST) ?
                JToolBar.VERTICAL : JToolBar.HORIZONTAL);
        //toolBar.add(new JButton("A", UIManager.getIcon("FileView.computerIcon")));
        toolBar.add(new JButton(I18nResourceHandler.getMessage("A"), UIManager.getIcon("FileView.computerIcon")));
        //toolBar.add(new JButton("B", UIManager.getIcon("FileView.directoryIcon")));
        toolBar.add(new JButton(I18nResourceHandler.getMessage("B"), UIManager.getIcon("FileView.directoryIcon")));
        toolBar.addSeparator();
        //toolBar.add(new JButton("C", UIManager.getIcon("FileView.fileIcon")));
        toolBar.add(new JButton(I18nResourceHandler.getMessage("C"), UIManager.getIcon("FileView.fileIcon")));
        //toolBar.add(new JButton("D", UIManager.getIcon("FileView.floppyDriveIcon")));
        toolBar.add(new JButton(I18nResourceHandler.getMessage("D"), UIManager.getIcon("FileView.floppyDriveIcon")));
        toolBar.addSeparator();
        toolBar.add(Box.createHorizontalGlue());
        //toolBar.add(new JButton("E", UIManager.getIcon("FileView.hardDriveIcon")));
        toolBar.add(new JButton(I18nResourceHandler.getMessage("E"), UIManager.getIcon("FileView.hardDriveIcon")));
        frame1.getContentPane().add(toolBar, toolBarSide);

        frame1.setLocation(index * 25, index * 25);
        frame1.pack();
        frame1.setSize(200, 200);
        frame1.setVisible(true);
        index++;
    }
}
