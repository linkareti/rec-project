/* 
 * MenuBar.java created on Feb 4, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.security.KeyFactory;
import javax.swing.BorderFactory;
import javax.swing.JComboBox.KeySelectionManager;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

/**
 * ReC MenuBar
 * 
 * @author Henrique Fernandes
 */
public class MenuBar extends JMenuBar {
    
    private static final long serialVersionUID = 4038023664068839031L;

    private JMenu topMenuLaboratory;
    
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

	topMenuLaboratory.add(new JMenuItem("Menu Item 3"));
    topMenuLaboratory.add(new JMenuItem("Menu Item 2"));
    topMenuLaboratory.add(new JMenuItem("Menu Item 1"));
    topMenuLaboratory.add(new JSeparator());
    topMenuLaboratory.add(new JMenuItem("Menu Item 3"));
    topMenuLaboratory.add(new JMenuItem("Menu Item 2"));
    topMenuLaboratory.add(new JSeparator());
    topMenuLaboratory.add(new JMenuItem("Menu Item 1"));
    topMenuLaboratory.add(new JMenuItem("Sair"));
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
	topMenuHelp.add(new JMenuItem("Indice"));
    topMenuHelp.add(new JMenuItem("Acerca"));
    topMenuHelp.add(new JSeparator());
    topMenuHelp.add(new JMenuItem("Contens"));

	add(topMenuHelp);
    }
}
