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
package org.jdesktop.laffy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.jdesktop.beans.AbstractBean;
import org.jdesktop.laffy.Tabs.TabsPanel;
import org.jdesktop.laffy.buttons.ButtonPanel;
import org.jdesktop.laffy.buttons.CheckPanel;
import org.jdesktop.laffy.buttons.RadioPanel;
import org.jdesktop.laffy.buttons.TogglePanel;
import org.jdesktop.laffy.combospinner.ComboPanel;
import org.jdesktop.laffy.combospinner.EditableComboPanel;
import org.jdesktop.laffy.combospinner.SpinnerPanel;
import org.jdesktop.laffy.dialogs.OptionPanePageFactory;
import org.jdesktop.laffy.internalframes.InternalFramesPanel;
import org.jdesktop.laffy.list.ListPanel;
import org.jdesktop.laffy.preview.PagePreviewListCellRenderer;
import org.jdesktop.laffy.preview.PagePreviewListModel;
import org.jdesktop.laffy.progress.ProgressPanel;
import org.jdesktop.laffy.progress.VerticalProgressPanel;
import org.jdesktop.laffy.sizevariants.SizeVariants;
import org.jdesktop.laffy.sliders.SliderPanel;
import org.jdesktop.laffy.sliders.VerticalSliderPanel;
import org.jdesktop.laffy.splitter.SplittersPanel;
import org.jdesktop.laffy.table.TablePanel;
import org.jdesktop.laffy.texts.LabelPanel;
import org.jdesktop.laffy.texts.TextAreaPanel;
import org.jdesktop.laffy.texts.TextFieldPanel;
import org.jdesktop.laffy.tree.TreePanel;
import org.jdesktop.swingx.border.DropShadowBorder;

import com.linkare.rec.impl.newface.laf.flat.FlatLookAndFeel;

/**
 * Laffy
 *
 * @author Created by Jasper Potts (Aug 23, 2007)
 * @version 1.0
 */
public class Laffy extends AbstractBean {
    private static Laffy instance = null;
    private JFrame frame;
    private JScrollPane pageScrollPane = new JScrollPane();
    private JList pagePreviewList = new JList();
    private PagePreviewListModel pagePreviewModel = new PagePreviewListModel(pagePreviewList);
    private JSplitPane splitPane = new JSplitPane();
    private SplashWindow splashWindow;

    private boolean isForceNonOpaque = false;
    private boolean isForceBackgroundColor = false;
    private boolean isForceCyanPanels = false;
    private boolean isForceComponentsToBasicUI = false;
    private boolean isRightToLeft = false;

    private final List<Page> pages = new ArrayList<Page>();

    private Laffy() {}

    // called from main thread to load laffy
    public void load(String ... args) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                splashWindow = new SplashWindow();
                splashWindow.setVisible(true);
            }
        });
        // give UI thread chance to paint splash screen
        synchronized (this) {
            wait(500);
        }
        // create all pages
        createPages();
        // check command line options
        for (String arg : args) {
            if ("-forceNonOpaque".equalsIgnoreCase(arg)){
                isForceNonOpaque = true;
            } else if ("-forceBackgroundColor".equalsIgnoreCase(arg)){
                isForceBackgroundColor = true;
            } else if ("-forceComponentsToBasicUI".equalsIgnoreCase(arg)){
                isForceComponentsToBasicUI = true;
            }
        }
        firePropertyChange("forceNonOpaque", false, isForceNonOpaque);
        firePropertyChange("forceBackgroundColor", false, isForceBackgroundColor);
        firePropertyChange("forceCyanPanels", false, isForceCyanPanels);
        firePropertyChange("forceComponentsToBasicUI", false, isForceComponentsToBasicUI);
        // render previews for all pages
        new PageRender().loadPages(pages, splashWindow);
        for (Page page : pages) {
            pagePreviewModel.addPage(page);
        }
        // show splash for little longer
        synchronized (this) {
            wait(500);
        }
        // build and show main frame then hide splash
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                // build main frame
                buildMainFrame();
                // show main frame
                frame.setSize(1000, 600);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                // hide splash
                splashWindow.setVisible(false);
                splashWindow.dispose();
                // switch to first page
                if (pagePreviewModel.getSize() > 0) {
                    pagePreviewModel.setSelectedItem(pagePreviewModel.getElementAt(0));
                } else {
                    // System.err.println("Error: No Pages Loaded");
                    System.err.println(I18nResourceHandler.getMessage("Error_No_Pages_Loaded"));
                }
            }
        });
    }

    /**
     * This is more complicated that it realy should be, because each page needs to be created in EDT and then we need
     * to let the EDT have a chance to repaint the splash screen. That is why each page creation is in its own
     * invokeAndWait block.
     * <p/>
     * To add a new page, just copy a existing block and ammend. Don't forget to update <code>numOfPages</code> for the
     * total number of pages so the progress can be currectly updated.
     */
    private void createPages() throws Exception {
        final int numOfPages = 12;
        // ADD PAGES
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                pages.add(new Page(I18nResourceHandler.getMessage("Buttons"),
                        new Section(I18nResourceHandler.getMessage("Button"), ButtonPanel.class),
                        new Section(I18nResourceHandler.getMessage("Toggle_Button"), TogglePanel.class),
                        new Section(I18nResourceHandler.getMessage("Radio_Button"), RadioPanel.class),
                        new Section(I18nResourceHandler.getMessage("Check_Boxes"), CheckPanel.class)));
                splashWindow.setPercentageComplete((0.5f / numOfPages) * pages.size());
            }
        });
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                pages.add(new Page(I18nResourceHandler.getMessage("Text_Components"),
                        new Section(I18nResourceHandler.getMessage("Label"), LabelPanel.class),
                        new Section(I18nResourceHandler.getMessage("Text_Field"), TextFieldPanel.class),
                        new Section(I18nResourceHandler.getMessage("Text_Area"), TextAreaPanel.class)));
                splashWindow.setPercentageComplete((0.5f / numOfPages) * pages.size());
            }
        });
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                pages.add(new Page(I18nResourceHandler.getMessage("Combos_&_Spinners"),
                        new Section(I18nResourceHandler.getMessage("ComboBox"), ComboPanel.class),
                        new Section(I18nResourceHandler.getMessage("Editable_ComboBox"), EditableComboPanel.class),
                        new Section(I18nResourceHandler.getMessage("Spinner"), SpinnerPanel.class)));
                splashWindow.setPercentageComplete((0.5f / numOfPages) * pages.size());
            }
        });
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                pages.add(new Page(I18nResourceHandler.getMessage("Progress"),
                        new Section(I18nResourceHandler.getMessage("Progress_Bar"), ProgressPanel.class),
                        new Section(I18nResourceHandler.getMessage("Vertical_Progress_Bar"), VerticalProgressPanel.class)));
                splashWindow.setPercentageComplete((0.5f / numOfPages) * pages.size());
            }
        });
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                pages.add(new Page(I18nResourceHandler.getMessage("Sliders"),
                        new Section(I18nResourceHandler.getMessage("Slider"), SliderPanel.class),
                        new Section(I18nResourceHandler.getMessage("Vertical_Slider"), VerticalSliderPanel.class)));
                splashWindow.setPercentageComplete((0.5f / numOfPages) * pages.size());
            }
        });
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                pages.add(new Page(I18nResourceHandler.getMessage("Trees_&_Tables_&_Lists"),
                        new Section(I18nResourceHandler.getMessage("Tree"), TreePanel.class),
                        new Section(I18nResourceHandler.getMessage("Table"), TablePanel.class),
                        new Section(I18nResourceHandler.getMessage("List"), ListPanel.class)));
                splashWindow.setPercentageComplete((0.5f / numOfPages) * pages.size());
            }
        });
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                pages.add(new Page(I18nResourceHandler.getMessage("Tabs"),
                        new Section(I18nResourceHandler.getMessage("Tabs"), TabsPanel.class)));
                splashWindow.setPercentageComplete((0.5f / numOfPages) * pages.size());
            }
        });
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                pages.add(new Page(I18nResourceHandler.getMessage("Size_Variants"),
                        new Section(I18nResourceHandler.getMessage("Size_Variants"), SizeVariants.class)));
                splashWindow.setPercentageComplete((0.5f / numOfPages) * pages.size());
            }
        });
        
        // FIXME Run the File Chooser Dialogs Test
        
//        SwingUtilities.invokeAndWait(new Runnable() {
//            public void run() {
//                pages.add(ChooserPageFactory.createPage());
//                splashWindow.setPercentageComplete((0.5f / numOfPages) * pages.size());
//            }
//        });
        
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                pages.add(OptionPanePageFactory.createPage());
                splashWindow.setPercentageComplete((0.5f / numOfPages) * pages.size());
            }
        });
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                pages.add(new Page(I18nResourceHandler.getMessage("Internal_Frames"),
                        new Section(I18nResourceHandler.getMessage("Desktop"), InternalFramesPanel.class)));
                splashWindow.setPercentageComplete((0.5f / numOfPages) * pages.size());
            }
        });
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                pages.add(new Page(I18nResourceHandler.getMessage("SplitPanes"),
                        new Section(I18nResourceHandler.getMessage("SplitPanes"), SplittersPanel.class)));
                splashWindow.setPercentageComplete((0.5f / numOfPages) * pages.size());
            }
        });
    }

    private void buildMainFrame() {
        frame = new JFrame(I18nResourceHandler.getMessage("Laffy"));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        // BUILD
        buildToolBar();
        buildMenuBar();
        buildCenterArea();
        buildPreviewArea();
    }

    public static Laffy getInstance() {
        if (instance == null) {
            instance = new Laffy();
        }
        return instance;
    }

    // Bean methods

    public JFrame getFrame() {
        return frame;
    }

    // CREATE MENUS

    public void buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        // FILE ---------------------------------------------
        //JMenu fileMenu = new JMenu("File");
        JMenu fileMenu = new JMenu(I18nResourceHandler.getMessage("M_File"));
        I18nResourceHandler.setMnemonic(fileMenu, "M_File");
        menuBar.add(fileMenu);
        //JMenuItem print = new JMenuItem("Print Page...");
        JMenuItem print = new JMenuItem(I18nResourceHandler.getMessage("MI_Print_Page..."));
        I18nResourceHandler.setMnemonic(print, "MI_Print_Page...");
        print.setAccelerator(KeyStroke.getKeyStroke("control P"));
        fileMenu.add(print);
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PagePrinter.printPage((Page) pagePreviewModel.getSelectedItem());
            }
        });
        //JMenuItem printOld = new JMenuItem("Print Page(1.1 API)...");
        JMenuItem printOld = new JMenuItem(I18nResourceHandler.getMessage("MI_Print_Page(1.1_API)..."));
        I18nResourceHandler.setMnemonic(printOld, "MI_Print_Page(1.1_API)...");
        printOld.setAccelerator(KeyStroke.getKeyStroke("control shift P"));
        fileMenu.add(printOld);
        printOld.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PagePrinter.printPageOld((Page) pagePreviewModel.getSelectedItem());
            }
        });
        fileMenu.addSeparator();
        //JMenuItem quit = new JMenuItem("Quit");
        JMenuItem quit = new JMenuItem(I18nResourceHandler.getMessage("MI_Quit"));
        I18nResourceHandler.setMnemonic(quit, "MI_Quit");
        quit.setAccelerator(KeyStroke.getKeyStroke("control Q"));
        fileMenu.add(quit);
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                System.exit(0);
            }
        });
        // LAF ---------------------------------------------
        //JMenu lafMenu = new JMenu("Look & Feel");
        JMenu lafMenu = new JMenu(I18nResourceHandler.getMessage("M_Look_&_Feel"));
        I18nResourceHandler.setMnemonic(lafMenu, "M_Look_&_Feel");
        menuBar.add(lafMenu);
        ButtonGroup lafButtonGroup = new ButtonGroup();
        // add available lafs
        for (UIManager.LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()) {
            createLafMenuItem(frame, lafMenu, lafButtonGroup, lafInfo.getName(), lafInfo.getClassName());
        }
        // OPTIONS ---------------------------------------------
        //JMenu optionsMenu = new JMenu("Options");
        JMenu optionsMenu = new JMenu(I18nResourceHandler.getMessage("M_Options"));
        I18nResourceHandler.setMnemonic(optionsMenu, "M_Options");
        buildOptionsMenu(optionsMenu);
        menuBar.add(optionsMenu);
        // TEST ---------------------------------------------
        //JMenu testMenu = new JMenu("Test Menu");
        JMenu testMenu = new JMenu(I18nResourceHandler.getMessage("M_Test_Menu"));
        I18nResourceHandler.setMnemonic(testMenu, "M_Test_Menu");
        menuBar.add(testMenu);

        //JMenuItem item = new JMenuItem("Stop");
        JMenuItem item = new JMenuItem(I18nResourceHandler.getMessage("MI_Stop"));
        I18nResourceHandler.setMnemonic(item, "MI_Stop");
        item.setEnabled(false);
        testMenu.add(item);

        //item = new JMenuItem("Reload");
        item = new JMenuItem(I18nResourceHandler.getMessage("MI_Reload"));
        I18nResourceHandler.setMnemonic(item, "MI_Reload");
        item.setAccelerator(KeyStroke.getKeyStroke("control R"));
        testMenu.add(item);

        testMenu.addSeparator();

        //JCheckBoxMenuItem cbitem = new JCheckBoxMenuItem("Laffy Toolbar");
        JCheckBoxMenuItem cbitem = new JCheckBoxMenuItem(I18nResourceHandler.getMessage("MI_Laffy_Toolbar"));
        I18nResourceHandler.setMnemonic(cbitem, "MI_Laffy_Toolbar");
        testMenu.add(cbitem);

        //cbitem = new JCheckBoxMenuItem("Side Pane");
        cbitem = new JCheckBoxMenuItem(I18nResourceHandler.getMessage("MI_Side_Pane"));
        I18nResourceHandler.setMnemonic(cbitem, "MI_Side_Pane");
        cbitem.setAccelerator(KeyStroke.getKeyStroke("F9"));
        testMenu.add(cbitem);

        //cbitem = new JCheckBoxMenuItem("Location Bar");
        cbitem = new JCheckBoxMenuItem(I18nResourceHandler.getMessage("MI_Location_Bar"));
        I18nResourceHandler.setMnemonic(cbitem, "MI_Location_Bar");
        testMenu.add(cbitem);

        //cbitem = new JCheckBoxMenuItem("Status Bar");
        cbitem = new JCheckBoxMenuItem(I18nResourceHandler.getMessage("MI_Status_Bar"));
        I18nResourceHandler.setMnemonic(cbitem, "MI_Status_Bar");
        testMenu.add(cbitem);

        testMenu.addSeparator();

        //JMenu m = new JMenu("Reset View to Defaults");
        JMenu m = new JMenu(I18nResourceHandler.getMessage("MI_Reset_View_to_Defaults"));
        I18nResourceHandler.setMnemonic(m, "MI_Reset_View_to_Defaults");
        testMenu.add(m);

        //cbitem = new JCheckBoxMenuItem("Show Hidden Files");
        cbitem = new JCheckBoxMenuItem(I18nResourceHandler.getMessage("MI_Show_Hidden_Files"));
        I18nResourceHandler.setMnemonic(cbitem, "MI_Show_Hidden_Files");
        cbitem.setAccelerator(KeyStroke.getKeyStroke("control H"));
        testMenu.add(cbitem);

        testMenu.addSeparator();

        //m = new JMenu("Visible Columns...");
        m = new JMenu(I18nResourceHandler.getMessage("MI_Visible_Columns..."));
        I18nResourceHandler.setMnemonic(m, "MI_Visible_Columns...");
        testMenu.add(m);

        testMenu.addSeparator();

        //item = new JMenuItem("Zoom In");
        item = new JMenuItem(I18nResourceHandler.getMessage("MI_Zoom_In"));
        I18nResourceHandler.setMnemonic(item, "MI_Zoom_In");
        item.setAccelerator(KeyStroke.getKeyStroke("control +"));
        testMenu.add(item);

        //item = new JMenuItem("Zoom Out");
        item = new JMenuItem(I18nResourceHandler.getMessage("MI_Zoom_Out"));
        I18nResourceHandler.setMnemonic(item, "MI_Zoom_Out");
        item.setAccelerator(KeyStroke.getKeyStroke("control -"));
        testMenu.add(item);

        //item = new JMenuItem("Normal Size");
        item = new JMenuItem(I18nResourceHandler.getMessage("MI_Normal_Size"));
        I18nResourceHandler.setMnemonic(item, "MI_Normal_Size");
        item.setAccelerator(KeyStroke.getKeyStroke("control 0"));
        testMenu.add(item);

        //JRadioButtonMenuItem ritem = new JRadioButtonMenuItem("View as Icons");
        JRadioButtonMenuItem ritem = new JRadioButtonMenuItem(I18nResourceHandler.getMessage("MI_View_as_Icons"));
        I18nResourceHandler.setMnemonic(ritem, "MI_View_as_Icons");
        ritem.setAccelerator(KeyStroke.getKeyStroke("control 1"));
        ritem.setEnabled(false);
        testMenu.add(ritem);

        //ritem = new JRadioButtonMenuItem("View as List");
        ritem = new JRadioButtonMenuItem(I18nResourceHandler.getMessage("MI_View_as_List"));
        I18nResourceHandler.setMnemonic(ritem, "MI_View_as_List");
        ritem.setAccelerator(KeyStroke.getKeyStroke("control 2"));
        testMenu.add(ritem);
    }

    public void buildOptionsMenu(JMenu options) {
        // Opaque and Background Color
        //final JRadioButtonMenuItem defaultMI = new JRadioButtonMenuItem("Default");
        final JRadioButtonMenuItem defaultMI = new JRadioButtonMenuItem(I18nResourceHandler.getMessage("MI_Default"));
        I18nResourceHandler.setMnemonic(defaultMI, "MI_Default");
        //final JRadioButtonMenuItem nonOpaqueMI = new JRadioButtonMenuItem("Non-Opaque");
        final JRadioButtonMenuItem nonOpaqueMI = new JRadioButtonMenuItem(I18nResourceHandler.getMessage("MI_Non-Opaque"));
        I18nResourceHandler.setMnemonic(nonOpaqueMI, "MI_Non-Opaque");
        //final JRadioButtonMenuItem backgroundRedMI = new JRadioButtonMenuItem("Set Background Color");
        final JRadioButtonMenuItem backgroundRedMI = new JRadioButtonMenuItem(I18nResourceHandler.getMessage("MI_Set_Background_Color"));
        I18nResourceHandler.setMnemonic(backgroundRedMI, "MI_Set_Background_Color");
        //final JRadioButtonMenuItem cyanPanelsMI = new JRadioButtonMenuItem("Cyan Panels");
        final JRadioButtonMenuItem cyanPanelsMI = new JRadioButtonMenuItem(I18nResourceHandler.getMessage("MI_Cyan_Panels"));
        I18nResourceHandler.setMnemonic(cyanPanelsMI, "MI_Cyan_Panels");
        //final JRadioButtonMenuItem basicUIMI = new JRadioButtonMenuItem("Use BasicUI Delegates");
        final JRadioButtonMenuItem basicUIMI = new JRadioButtonMenuItem(I18nResourceHandler.getMessage("MI_Use_BasicUI_Delegates"));
        I18nResourceHandler.setMnemonic(basicUIMI, "MI_Use_BasicUI_Delegates");
        if (isForceNonOpaque){
            nonOpaqueMI.setSelected(true);
        } else if (isForceBackgroundColor){
            backgroundRedMI.setSelected(true);
        } else  if (isForceCyanPanels){
            cyanPanelsMI.setSelected(true);
        } else  if (isForceComponentsToBasicUI){
            basicUIMI.setSelected(true);
        } else {
            defaultMI.setSelected(true);
        }
        ActionListener obActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean oldOp = isForceNonOpaque, oldBack = isForceBackgroundColor,
                        oldCyan = isForceCyanPanels, oldBasic = isForceComponentsToBasicUI;
                if (defaultMI.isSelected()) {
                    isForceNonOpaque = false;
                    isForceBackgroundColor = false;
                    isForceCyanPanels = false;
                    isForceComponentsToBasicUI = false;
                } else if (nonOpaqueMI.isSelected()) {
                    isForceNonOpaque = true;
                    isForceBackgroundColor = false;
                    isForceCyanPanels = false;
                    isForceComponentsToBasicUI = false;
                } else if (backgroundRedMI.isSelected()) {
                    isForceNonOpaque = false;
                    isForceBackgroundColor = true;
                    isForceCyanPanels = false;
                    isForceComponentsToBasicUI = false;
                } else if (cyanPanelsMI.isSelected()) {
                    isForceNonOpaque = false;
                    isForceBackgroundColor = false;
                    isForceCyanPanels = true;
                    isForceComponentsToBasicUI = false;
                } else if (basicUIMI.isSelected()) {
                    isForceNonOpaque = false;
                    isForceBackgroundColor = false;
                    isForceCyanPanels = false;
                    isForceComponentsToBasicUI = true;
                }
                firePropertyChange("forceNonOpaque", oldOp, isForceNonOpaque);
                firePropertyChange("forceBackgroundColor", oldBack, isForceBackgroundColor);
                firePropertyChange("forceCyanPanels", oldCyan, isForceCyanPanels);
                firePropertyChange("forceComponentsToBasicUI", oldBasic, isForceComponentsToBasicUI);
                getFrame().repaint();
            }
        };
        defaultMI.addActionListener(obActionListener);
        nonOpaqueMI.addActionListener(obActionListener);
        backgroundRedMI.addActionListener(obActionListener);
        cyanPanelsMI.addActionListener(obActionListener);
        basicUIMI.addActionListener(obActionListener);
        ButtonGroup obGroup = new ButtonGroup();
        obGroup.add(defaultMI);
        obGroup.add(nonOpaqueMI);
        obGroup.add(backgroundRedMI);
        obGroup.add(cyanPanelsMI);
        obGroup.add(basicUIMI);
        options.add(defaultMI);
        options.add(nonOpaqueMI);
        options.add(backgroundRedMI);
        options.add(cyanPanelsMI);
        options.add(basicUIMI);
        // add component orientation
        options.addSeparator();
        //final JRadioButtonMenuItem LTR = new JRadioButtonMenuItem("Left to Right");
        final JRadioButtonMenuItem LTR = new JRadioButtonMenuItem(I18nResourceHandler.getMessage("MI_Left_to_Right"));
        I18nResourceHandler.setMnemonic(LTR, "MI_Left_to_Right");
        //final JRadioButtonMenuItem RTL = new JRadioButtonMenuItem("Right to Left");
        final JRadioButtonMenuItem RTL = new JRadioButtonMenuItem(I18nResourceHandler.getMessage("MI_Right_to_Left"));
        I18nResourceHandler.setMnemonic(RTL, "MI_Right_to_Left");
        options.add(LTR);
        options.add(RTL);
        ButtonGroup rtlBg = new ButtonGroup();
        rtlBg.add(LTR);
        rtlBg.add(RTL);
        LTR.setSelected(true);
        ActionListener rtlListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (LTR.isSelected()) {
                    isRightToLeft = false;
                    getFrame().applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                } else {
                    isRightToLeft = true;
                    getFrame().applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                }
            }
        };
        LTR.addActionListener(rtlListener);
        RTL.addActionListener(rtlListener);
    }

    public JRadioButtonMenuItem createLafMenuItem(final JFrame frame, JMenu menu, ButtonGroup lafButtonGroup,
                                                  String text, final String className) {
        JRadioButtonMenuItem mi = (JRadioButtonMenuItem) menu.add(new JRadioButtonMenuItem(text));
        lafButtonGroup.add(mi);
        mi.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!className.equals(UIManager.getLookAndFeel().getClass().getName())) {
                    try {
                        UIManager.setLookAndFeel(className);
                        SwingUtilities.updateComponentTreeUI(frame);
                        // update laf for all pages in case they are not part of component hierachy
                        for (Page page : pages) {
                            SwingUtilities.updateComponentTreeUI(page);
                        }
                    } catch (Exception e1) {
                        // System.err.println("Error setting LAF to [" + className + "]");
                        System.err.println(java.text.MessageFormat.format(I18nResourceHandler.getMessage("Error_setting_LAF_to"), className));
                        e1.printStackTrace();
                    }
                }
            }
        });
        return mi;
    }

    // CERATE TOOLBAR

    public void buildToolBar() {
        JToolBar toolBar = new JToolBar();
        frame.getContentPane().add(toolBar, BorderLayout.NORTH);
        toolBar.setFloatable(true);
        toolBar.add(new JButton(loadIcon("save-24.png")));
        toolBar.addSeparator();
        JButton b = new JButton(loadIcon("prev-24.png"));
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int current = pagePreviewList.getSelectedIndex() - 1;
                if (current < 0) current = pagePreviewModel.getSize() - 1;
                pagePreviewModel.setSelectedItem(pagePreviewModel.getElementAt(current));
            }
        });
        toolBar.add(b);
        b = new JButton(loadIcon("next-24.png"));
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int current = pagePreviewList.getSelectedIndex() + 1;
                if (current == pagePreviewModel.getSize()) current = 0;
                pagePreviewModel.setSelectedItem(pagePreviewModel.getElementAt(current));
            }
        });
        toolBar.add(b);
        toolBar.addSeparator();
        //JLabel pageLabel = new JLabel("Pages");
        JLabel pageLabel = new JLabel(I18nResourceHandler.getMessage("Pages"));
        I18nResourceHandler.setMnemonic(pageLabel, "Pages");
        pageLabel.setFont(pageLabel.getFont().deriveFont(12f));
        toolBar.add(pageLabel);
        JComboBox pagesComboBox = new JComboBox(pagePreviewModel);
        toolBar.add(pagesComboBox);
        pageLabel.setLabelFor(pagesComboBox);
    }

    // CREATE PAGES

    public void buildCenterArea() {
        pageScrollPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setRightComponent(pageScrollPane);
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);
    }

    // Create preview area

    public void buildPreviewArea() {
        pagePreviewList.setModel(pagePreviewModel);
        pagePreviewList.setBackground(Color.GRAY);
        pagePreviewList.setCellRenderer(new PagePreviewListCellRenderer());
        pagePreviewList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pagePreviewList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // CHANGE PAGE
                    Page p = (Page) pagePreviewList.getSelectedValue();
                    if (p != null) {
                        JPanel pageHolder = new JPanel(new BorderLayout());
                        pageHolder.setBackground(Color.WHITE);
                        JPanel pageBorder = new JPanel(new BorderLayout());
                        pageBorder.setBackground(Color.GRAY);
                        pageBorder.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                                new DropShadowBorder(Color.DARK_GRAY, 5)
                        ));
                        pageHolder.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
                        pageBorder.add(pageHolder, BorderLayout.CENTER);
                        pageHolder.add(p, BorderLayout.CENTER);
                        pageScrollPane.setViewportView(pageBorder);
                        SwingUtilities.updateComponentTreeUI(pageScrollPane);
                        pageScrollPane.repaint();
                        if (isRightToLeft) {
                            // reapply RTL
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    getFrame().applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                                }
                            });
                        }
                    }
                }
            }
        });
//        pagePreviewList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(pagePreviewList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setLeftComponent(scrollPane);
        splitPane.setOneTouchExpandable(true);
    }

    // Helper Methods

    public static Icon loadIcon(String fileName) {
        return new ImageIcon(Laffy.class.getResource("icons/" + fileName));
    }

    public static BufferedImage loadImage(String fileName) {
        try {
            return ImageIO.read(Laffy.class.getResource("icons/" + fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // MAIN

    public static void main(String[] args) throws Exception {
        // Set look and feel
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                // set numbus laf if available
                try {
                	
                	Locale.setDefault(Locale.ENGLISH);
                	// TODO check anti-aliasing on windows
					System.setProperty("swing.aatext", "true");
					// Anti-aliasing for mac
					System.setProperty("apple.awt.textantialiasing", "on");
					
                	UIManager.installLookAndFeel("FlatLAF", FlatLookAndFeel.class.getName());
                    
                	//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//                	UIManager.setLookAndFeel("org.jdesktop.swingx.plaf.nimbus.NimbusLookAndFeel");
                	UIManager.setLookAndFeel(FlatLookAndFeel.class.getName());
                	
                } catch (Exception e) {
                    // System.err.println("Nimbus LAF not available using Ocean.");
                    System.err.println(I18nResourceHandler.getMessage("Nimbus_LAF_not_available_using_Ocean."));
                    try {
                        UIManager.setLookAndFeel(new MetalLookAndFeel());
                    } catch (Exception e2) {
                        // System.err.println("Unable to use Ocean LAF using default.");
                        System.err.println(I18nResourceHandler.getMessage("Unable_to_use_Ocean_LAF_using_default."));
                    }
//                    System.err.println("Nimbus LAF not available using platform LAF.");
//                    try {
//                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//                    } catch (Exception e2) {
//                        System.err.println("Unable to use platform LAF using default.");
//                    }
                }
            }
        });
        // create laffy and load
        getInstance().load(args);
    }
    
}


