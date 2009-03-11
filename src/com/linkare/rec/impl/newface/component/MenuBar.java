/* 
 * MenuBar.java created on Feb 4, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * ReC MenuBar
 * 
 * @author Henrique Fernandes
 */
public class MenuBar extends JMenuBar {
    
    private static final long serialVersionUID = 4038023664068839031L;

    private JMenu topMenuLaboratory;
    
    private JMenuItem menuItemLaboratoryExit;
    
    private JMenu topMenuView;
    
    private JMenu topMenuLanguage;
    
    private JMenu topMenuHelp;

    public MenuBar() {
	super();
	initComponents();
    }
    
    private void initComponents() {
	// Menu Laboratory
	topMenuLaboratory = new JMenu();
	topMenuLaboratory.setText("Laboratório");

	menuItemLaboratoryExit = new JMenuItem();
	menuItemLaboratoryExit.setText("Sair");

	topMenuLaboratory.add(menuItemLaboratoryExit);
	add(topMenuLaboratory);

	// Menu View
	topMenuView = new JMenu();
	topMenuView.setText("Ver");
	topMenuView.setEnabled(false);
	add(topMenuView);

	// Menu Language
	topMenuLanguage = new JMenu();
	topMenuLanguage.setText("Linguagem");
	topMenuLanguage.setEnabled(false);
	add(topMenuLanguage);

	// Menu Help
	topMenuHelp = new JMenu();
	topMenuHelp.setText("Ajuda");
	add(topMenuHelp);
    }
}
