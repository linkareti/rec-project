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

package org.jdesktop.laffy.table;

import org.jdesktop.laffy.I18nResourceHandler;
import org.jdesktop.laffy.OptionsSettablePanel;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;

/** @author Richard Bair */
public class TablePanel extends OptionsSettablePanel {

    private final Color DEFAULT_BACKGROUND;
    private final boolean DEFAULT_OPAQUE;

    /** Creates new form TablePanel */
    public TablePanel() {
        initComponents();
        DEFAULT_BACKGROUND = jTable1.getBackground();
        DEFAULT_OPAQUE = jTable1.isOpaque();

        jTable1.setRowSorter(new TableRowSorter(jTable1.getModel()));
        jTable2.setRowSorter(new TableRowSorter(jTable2.getModel()));
        jTable4.setRowSorter(new TableRowSorter(jTable4.getModel()));
        jTable5.setRowSorter(new TableRowSorter(jTable5.getModel()));
        jTable6.setRowSorter(new TableRowSorter(jTable6.getModel()));
        jTable8.setRowSorter(new TableRowSorter(jTable8.getModel()));
        jTable9.setRowSorter(new TableRowSorter(jTable9.getModel()));
        jTable10.setRowSorter(new TableRowSorter(jTable10.getModel()));
        jTable12.setRowSorter(new TableRowSorter(jTable12.getModel()));
        jTable18.setRowSorter(new TableRowSorter(jTable18.getModel()));
        normalCustomRenderer.setRowSorter(new TableRowSorter(normalCustomRenderer.getModel()));
        disabledCustomRenderer.setRowSorter(new TableRowSorter(disabledCustomRenderer.getModel()));

        normalCustomRenderer.getColumnModel().getColumn(2).setCellRenderer(new MoneyCellRenderer());
        disabledCustomRenderer.getColumnModel().getColumn(2).setCellRenderer(new MoneyCellRenderer());

        jTable4.getSelectionModel().setSelectionInterval(2, 2);
        jTable5.getSelectionModel().setSelectionInterval(2, 2);
        jTable18.getSelectionModel().setSelectionInterval(2, 2);
        jTable9.getSelectionModel().setSelectionInterval(2, 2);
        jTable10.getSelectionModel().setSelectionInterval(2, 2);
        disabledCustomRenderer.getSelectionModel().setSelectionInterval(2, 2);
    }

    // =================================================================================================================
    // OptionsSettablePanel Methods

    @Override public void setForceComponentsBackgroundColor(boolean force) {
        Color c = force ? FORCED_BACKGROUND : DEFAULT_BACKGROUND;
        // apply to all
        for (Component component : getComponents()) {
            if (component instanceof JScrollPane){
                component = ((JScrollPane)component).getViewport().getView();
            }
            if (component instanceof JTable) {
                component.setBackground(c);
            }
        }
    }

    @Override public void setForceComponentsNonOpaque(boolean force) {
        setBackground(force ? Color.GREEN : DEFAULT_BACKGROUND);
        boolean o = force ? false : DEFAULT_OPAQUE;
        // apply to all
        for (Component component : getComponents()) {
            if (component instanceof JScrollPane){
                component = ((JScrollPane)component).getViewport().getView();
            }
            if (component instanceof JTable) {
                ((JTable)component).setOpaque(o);
            }
        }
    }

    @Override public void setForceComponentsToBasicUI(boolean force) {
        System.out.println("TablePanel.setForceComponentsToBasicUI "+force );
        // apply to all
        for (Component component : getComponents()) {
            if (component instanceof JScrollPane){
                component = ((JScrollPane)component).getViewport().getView();
            }
            if (component instanceof JTable) {
                if (force){
                    ((JTable)component).setUI(new BasicTableUI());
                } else {
                    ((JTable)component).updateUI();
                }
            }
        }
        revalidate();
    }

    // =================================================================================================================
    // Netbeans Code

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            @Override protected void setUI(ComponentUI newUI) {
                System.out.println("TablePanel.setUI("+newUI+")");
                (new Exception()).printStackTrace(System.out);
                super.setUI(newUI);
            }

            @Override public void updateUI() {
                System.out.println("TablePanel.updateUI");
                super.updateUI();
            }
        };
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable5 = new FocusedTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable6 = new FocusedTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable12 = new javax.swing.JTable();
        jScrollPane12 = new javax.swing.JScrollPane();
        normalCustomRenderer = new javax.swing.JTable();
        jScrollPane14 = new javax.swing.JScrollPane();
        disabledCustomRenderer = new javax.swing.JTable();
        jTable18 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        jLabel8.setFont(jLabel8.getFont().deriveFont(jLabel8.getFont().getStyle() | java.awt.Font.BOLD, jLabel8.getFont().getSize()-2));
        jLabel8.setForeground(java.awt.Color.darkGray);
        //jLabel8.setText("Normal");
        jLabel8.setText(I18nResourceHandler.getMessage("Normal"));
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel8, gridBagConstraints);

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getStyle() | java.awt.Font.BOLD, jLabel2.getFont().getSize()-2));
        jLabel2.setForeground(java.awt.Color.darkGray);
        //jLabel2.setText("Focused");
        jLabel2.setText(I18nResourceHandler.getMessage("Focused"));
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel2, gridBagConstraints);

        jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getStyle() | java.awt.Font.BOLD, jLabel3.getFont().getSize()-2));
        jLabel3.setForeground(java.awt.Color.darkGray);
        //jLabel3.setText("Without ScrollPane");
        jLabel3.setText(I18nResourceHandler.getMessage("Without_ScrollPane"));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel3, gridBagConstraints);

        jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getStyle() | java.awt.Font.BOLD, jLabel4.getFont().getSize()-2));
        jLabel4.setForeground(java.awt.Color.darkGray);
        //jLabel4.setText("Custom Cell Spacing/Colors");
        jLabel4.setText(I18nResourceHandler.getMessage("Custom_Cell_Spacing_Colors"));
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel4, gridBagConstraints);

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD, jLabel1.getFont().getSize()-2));
        jLabel1.setForeground(java.awt.Color.darkGray);
        //jLabel1.setText("Normal");
        jLabel1.setText(I18nResourceHandler.getMessage("Normal"));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
        add(jLabel1, gridBagConstraints);

        jLabel6.setFont(jLabel6.getFont().deriveFont(jLabel6.getFont().getStyle() | java.awt.Font.BOLD, jLabel6.getFont().getSize()-2));
        jLabel6.setForeground(java.awt.Color.darkGray);
        //jLabel6.setText("Disabled");
        jLabel6.setText(I18nResourceHandler.getMessage("Disabled"));
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 4, 2);
        add(jLabel6, gridBagConstraints);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(250, 100));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                /*
                {new Integer(234523), "Rupert", new Double(253.23), null},
                        {new Integer(392923), "Clara", new Double(-98.78), new Boolean(true)},
                        {new Integer(811233), "Nick", new Double(-88.45), new Boolean(true)}
                 */
                {new Integer(234523), I18nResourceHandler.getMessage("Rupert"), new Double(253.23), null},
                {new Integer(392923), I18nResourceHandler.getMessage("Clara"), new Double(-98.78), new Boolean(true)},
                {new Integer(811233), I18nResourceHandler.getMessage("Nick"), new Double(-88.45), new Boolean(true)}
            },
            new String [] {
                //"ID", "Name", "Balance", "Boolean"
                I18nResourceHandler.getMessage("ID"), I18nResourceHandler.getMessage("Name"), I18nResourceHandler.getMessage("Balance"), I18nResourceHandler.getMessage("Boolean")
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setPreferredSize(new java.awt.Dimension(250, 75));
        jScrollPane1.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jScrollPane1, gridBagConstraints);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                /*
                {new Integer(234523), "Rupert", new Double(253.23)},
                {new Integer(392923), "Clara", new Double(-98.78)},
                {new Integer(811233), "Nick", new Double(-88.45)}
                 */
                {new Integer(234523), I18nResourceHandler.getMessage("Rupert"), new Double(253.23)},
                {new Integer(392923), I18nResourceHandler.getMessage("Clara"), new Double(-98.78)},
                {new Integer(811233), I18nResourceHandler.getMessage("Nick"), new Double(-88.45)}
            },
            new String [] {
                //"ID", "Name", "Balance"
                I18nResourceHandler.getMessage("ID"), I18nResourceHandler.getMessage("Name"), I18nResourceHandler.getMessage("Balance")
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setPreferredSize(new java.awt.Dimension(250, 100));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTable2, gridBagConstraints);

        jScrollPane3.setPreferredSize(new java.awt.Dimension(250, 100));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                /*
                {new Integer(234523), "Rupert", new Double(253.23), null},
                {new Integer(392923), "Clara", new Double(-98.78), new Boolean(true)},
                {new Integer(811233), "Nick", new Double(-88.45), new Boolean(true)}
                 */
                {new Integer(234523), I18nResourceHandler.getMessage("Rupert"), new Double(253.23), null},
                {new Integer(392923), I18nResourceHandler.getMessage("Clara"), new Double(-98.78), new Boolean(true)},
                {new Integer(811233), I18nResourceHandler.getMessage("Nick"), new Double(-88.45), new Boolean(true)}
            },
            new String [] {
                //"ID", "Name", "Balance", "Boolean"
                I18nResourceHandler.getMessage("ID"), I18nResourceHandler.getMessage("Name"), I18nResourceHandler.getMessage("Balance"), I18nResourceHandler.getMessage("Boolean")
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable4.setEnabled(false);
        jTable4.setPreferredSize(new java.awt.Dimension(250, 75));
        jScrollPane3.setViewportView(jTable4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jScrollPane3, gridBagConstraints);

        jScrollPane4.setPreferredSize(new java.awt.Dimension(250, 100));

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                /*
                {new Integer(234523), "Rupert", new Double(253.23)},
                {new Integer(392923), "Clara", new Double(-98.78)},
                {new Integer(811233), "Nick", new Double(-88.45)}
                 */
                {new Integer(234523), I18nResourceHandler.getMessage("Rupert"), new Double(253.23)},
                {new Integer(392923), I18nResourceHandler.getMessage("Clara"), new Double(-98.78)},
                {new Integer(811233), I18nResourceHandler.getMessage("Nick"), new Double(-88.45)}
            },
            new String [] {
                //"ID", "Name", "Balance"
                I18nResourceHandler.getMessage("ID"), I18nResourceHandler.getMessage("Name"), I18nResourceHandler.getMessage("Balance")
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable5.setEnabled(false);
        jTable5.setPreferredSize(new java.awt.Dimension(250, 75));
        jScrollPane4.setViewportView(jTable5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jScrollPane4, gridBagConstraints);

        jScrollPane5.setPreferredSize(new java.awt.Dimension(250, 100));

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                /*
                {new Integer(234523), "Rupert", new Double(253.23)},
                {new Integer(392923), "Clara", new Double(-98.78)},
                {new Integer(811233), "Nick", new Double(-88.45)}
                 */
                {new Integer(234523), I18nResourceHandler.getMessage("Rupert"), new Double(253.23)},
                {new Integer(392923), I18nResourceHandler.getMessage("Clara"), new Double(-98.78)},
                {new Integer(811233), I18nResourceHandler.getMessage("Nick"), new Double(-88.45)}
            },
            new String [] {
                //"ID", "Name", "Balance"
                I18nResourceHandler.getMessage("ID"), I18nResourceHandler.getMessage("Name"), I18nResourceHandler.getMessage("Balance")
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable6.setPreferredSize(new java.awt.Dimension(250, 75));
        jScrollPane5.setViewportView(jTable6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jScrollPane5, gridBagConstraints);

        jScrollPane7.setPreferredSize(new java.awt.Dimension(250, 100));

        jTable8.setBackground(java.awt.Color.lightGray);
        jTable8.setFont(jTable8.getFont().deriveFont((jTable8.getFont().getStyle() | java.awt.Font.ITALIC)));
        jTable8.setForeground(java.awt.Color.red);
        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                /*
                {new Integer(234523), "Rupert", new Double(253.23)},
                {new Integer(392923), "Clara", new Double(-98.78)},
                {new Integer(811233), "Nick", new Double(-88.45)}
                 */
                {new Integer(234523), I18nResourceHandler.getMessage("Rupert"), new Double(253.23)},
                {new Integer(392923), I18nResourceHandler.getMessage("Clara"), new Double(-98.78)},
                {new Integer(811233), I18nResourceHandler.getMessage("Nick"), new Double(-88.45)}
            },
            new String [] {
                //"ID", "Name", "Balance"
                I18nResourceHandler.getMessage("ID"), I18nResourceHandler.getMessage("Name"), I18nResourceHandler.getMessage("Balance")
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable8.setPreferredSize(new java.awt.Dimension(250, 75));
        jTable8.setSelectionBackground(java.awt.Color.black);
        jScrollPane7.setViewportView(jTable8);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jScrollPane7, gridBagConstraints);

        jScrollPane8.setPreferredSize(new java.awt.Dimension(250, 100));

        jTable9.setBackground(java.awt.Color.lightGray);
        jTable9.setFont(jTable9.getFont().deriveFont((jTable9.getFont().getStyle() | java.awt.Font.ITALIC)));
        jTable9.setForeground(java.awt.Color.red);
        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                /*
                {new Integer(234523), "Rupert", new Double(253.23)},
                {new Integer(392923), "Clara", new Double(-98.78)},
                {new Integer(811233), "Nick", new Double(-88.45)}
                 */
                {new Integer(234523), I18nResourceHandler.getMessage("Rupert"), new Double(253.23)},
                {new Integer(392923), I18nResourceHandler.getMessage("Clara"), new Double(-98.78)},
                {new Integer(811233), I18nResourceHandler.getMessage("Nick"), new Double(-88.45)}
            },
            new String [] {
                //"ID", "Name", "Balance"
                I18nResourceHandler.getMessage("ID"), I18nResourceHandler.getMessage("Name"), I18nResourceHandler.getMessage("Balance")
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable9.setEnabled(false);
        jTable9.setPreferredSize(new java.awt.Dimension(250, 75));
        jTable9.setSelectionBackground(java.awt.Color.black);
        jScrollPane8.setViewportView(jTable9);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jScrollPane8, gridBagConstraints);

        jScrollPane9.setPreferredSize(new java.awt.Dimension(250, 100));

        jTable10.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                /*
                {new Integer(234523), "Rupert", new Double(253.23)},
                {new Integer(392923), "Clara", new Double(-98.78)},
                {new Integer(811233), "Nick", new Double(-88.45)}
                 */
                {new Integer(234523), I18nResourceHandler.getMessage("Rupert"), new Double(253.23)},
                {new Integer(392923), I18nResourceHandler.getMessage("Clara"), new Double(-98.78)},
                {new Integer(811233), I18nResourceHandler.getMessage("Nick"), new Double(-88.45)}
            },
            new String [] {
                //"ID", "Name", "Balance"
                I18nResourceHandler.getMessage("ID"), I18nResourceHandler.getMessage("Name"), I18nResourceHandler.getMessage("Balance")
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable10.setEnabled(false);
        jTable10.setGridColor(java.awt.Color.red);
        jTable10.setIntercellSpacing(new java.awt.Dimension(5, 5));
        jTable10.setPreferredSize(new java.awt.Dimension(250, 75));
        jScrollPane9.setViewportView(jTable10);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jScrollPane9, gridBagConstraints);

        jScrollPane11.setPreferredSize(new java.awt.Dimension(250, 100));

        jTable12.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                /*
                {new Integer(234523), "Rupert", new Double(253.23)},
                {new Integer(392923), "Clara", new Double(-98.78)},
                {new Integer(811233), "Nick", new Double(-88.45)}
                 */
                {new Integer(234523), I18nResourceHandler.getMessage("Rupert"), new Double(253.23)},
                {new Integer(392923), I18nResourceHandler.getMessage("Clara"), new Double(-98.78)},
                {new Integer(811233), I18nResourceHandler.getMessage("Nick"), new Double(-88.45)}
            },
            new String [] {
                //"ID", "Name", "Balance"
                I18nResourceHandler.getMessage("ID"), I18nResourceHandler.getMessage("Name"), I18nResourceHandler.getMessage("Balance")
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable12.setGridColor(java.awt.Color.red);
        jTable12.setIntercellSpacing(new java.awt.Dimension(5, 5));
        jTable12.setPreferredSize(new java.awt.Dimension(250, 75));
        jScrollPane11.setViewportView(jTable12);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jScrollPane11, gridBagConstraints);

        jScrollPane12.setPreferredSize(new java.awt.Dimension(250, 100));

        normalCustomRenderer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                /*
                {new Integer(234523), "Rupert", new Double(253.23)},
                {new Integer(392923), "Clara", new Double(-98.78)},
                {new Integer(811233), "Nick", new Double(-88.45)}
                 */
                {new Integer(234523), I18nResourceHandler.getMessage("Rupert"), new Double(253.23)},
                {new Integer(392923), I18nResourceHandler.getMessage("Clara"), new Double(-98.78)},
                {new Integer(811233), I18nResourceHandler.getMessage("Nick"), new Double(-88.45)}
            },
            new String [] {
                //"ID", "Name", "Balance"
                I18nResourceHandler.getMessage("ID"), I18nResourceHandler.getMessage("Name"), I18nResourceHandler.getMessage("Balance")
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        normalCustomRenderer.setPreferredSize(new java.awt.Dimension(250, 75));
        jScrollPane12.setViewportView(normalCustomRenderer);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jScrollPane12, gridBagConstraints);

        jScrollPane14.setPreferredSize(new java.awt.Dimension(250, 100));

        disabledCustomRenderer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                /*
                {new Integer(234523), "Rupert", new Double(253.23)},
                {new Integer(392923), "Clara", new Double(-98.78)},
                {new Integer(811233), "Nick", new Double(-88.45)}
                 */
                {new Integer(234523), I18nResourceHandler.getMessage("Rupert"), new Double(253.23)},
                {new Integer(392923), I18nResourceHandler.getMessage("Clara"), new Double(-98.78)},
                {new Integer(811233), I18nResourceHandler.getMessage("Nick"), new Double(-88.45)}
            },
            new String [] {
                //"ID", "Name", "Balance"
                I18nResourceHandler.getMessage("ID"), I18nResourceHandler.getMessage("Name"), I18nResourceHandler.getMessage("Balance")
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        disabledCustomRenderer.setEnabled(false);
        disabledCustomRenderer.setPreferredSize(new java.awt.Dimension(250, 75));
        jScrollPane14.setViewportView(disabledCustomRenderer);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jScrollPane14, gridBagConstraints);

        jTable18.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                /*
                {new Integer(234523), "Rupert", new Double(253.23)},
                {new Integer(392923), "Clara", new Double(-98.78)},
                {new Integer(811233), "Nick", new Double(-88.45)}
                 */
                {new Integer(234523), I18nResourceHandler.getMessage("Rupert"), new Double(253.23)},
                {new Integer(392923), I18nResourceHandler.getMessage("Clara"), new Double(-98.78)},
                {new Integer(811233), I18nResourceHandler.getMessage("Nick"), new Double(-88.45)}
            },
            new String [] {
                //"ID", "Name", "Balance"
                I18nResourceHandler.getMessage("ID"), I18nResourceHandler.getMessage("Name"), I18nResourceHandler.getMessage("Balance")
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable18.setEnabled(false);
        jTable18.setPreferredSize(new java.awt.Dimension(250, 100));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jTable18, gridBagConstraints);

        jLabel7.setFont(jLabel7.getFont().deriveFont(jLabel7.getFont().getStyle() | java.awt.Font.BOLD, jLabel7.getFont().getSize()-2));
        jLabel7.setForeground(java.awt.Color.darkGray);
        //jLabel7.setText("Custom Cell Renderer");
        jLabel7.setText(I18nResourceHandler.getMessage("Custom_Cell_Renderer"));
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel7, gridBagConstraints);

        jLabel9.setFont(jLabel9.getFont().deriveFont(jLabel9.getFont().getStyle() | java.awt.Font.BOLD, jLabel9.getFont().getSize()-2));
        jLabel9.setForeground(java.awt.Color.darkGray);
        //jLabel9.setText("Custom Colors");
        jLabel9.setText(I18nResourceHandler.getMessage("Custom_Colors"));
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        add(jLabel9, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable disabledCustomRenderer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable10;
    private javax.swing.JTable jTable12;
    private javax.swing.JTable jTable18;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable8;
    private javax.swing.JTable jTable9;
    private javax.swing.JTable normalCustomRenderer;
    // End of variables declaration//GEN-END:variables

    public static final class FocusedTable extends JTable {
        @Override public boolean hasFocus() { return true; }

        @Override public boolean isFocusOwner() { return true; }
    }

    public static final class MoneyCellRenderer extends DefaultTableCellRenderer {
        private NumberFormat format = NumberFormat.getCurrencyInstance();

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 2 && value instanceof Number) {
                //this is the money column
                Number n = (Number) value;
                if (n.doubleValue() < 0) {
                    setForeground(Color.RED);
                }

                setText(format.format(n.doubleValue()));
            }
            return this;
        }
    }
}
