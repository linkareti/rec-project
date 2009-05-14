/** Painel de defini��es para o projecto final de semestre
 *  sobre jun��es P-N. Vai apresentar um conjunto de mecanismos
 *  de input para a defini��o de todos os par�metros necess�rios.
 */

package pt.utl.ist.elab.virtual.client.semiconductor;

/** Imports v�rios*/
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.beans.*;
import java.lang.*;

/** Import ReC*/
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.customizer.*;
import com.linkare.rec.data.synch.*;

/** Classe que vai conter o painel de defini��es da simula��o
 *  a correr. Esta classe apresenta uma colec��o de JSliders
 *  e JFormattedTextFields que possibilitam o input dos v�rios
 *  par�metros a considerar para a execu��o da simula��o, que
 *  de seguida ser�o enviados para o motor de dados onde ser�o
 *  processados os v�rios c�lculos necess�rios � simula��o.
 **/
public class SCustomizer extends JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer
{
    
    // Vari�veis necess�rias � defini��o dos valores a considerar
    // JSliders (valores iniciais)
    private JSlider sliderConcDadores;
    private JSlider sliderConcAceitadores;
    private JSlider sliderTemperatura;
    private JSlider sliderTempoAmostras;
    private JSlider sliderNumAmostras;
    private JSlider sliderCampoElectrico;
    
    // JFormattedTextFields (valores iniciais)
    private JFormattedTextField txtConcDadores;
    private JFormattedTextField txtConcAceitadores;
    private JFormattedTextField txtTemperatura;
    private JFormattedTextField txtTempoAmostras;
    private JFormattedTextField txtNumAmostras;
    private JFormattedTextField txtCampoElectrico;
    
    // JPanels (valores iniciais)
    private JPanel panelConcDadores;
    private JPanel panelConcAceitadores;
    private JPanel panelTemperatura;
    private JPanel panelTempoAmostras;
    private JPanel panelNumAmostras;
    private JPanel panelCampoElectrico;
    
    // JSliders (valores finais)
    private JSlider sliderConcDadoresFinal;
    private JSlider sliderConcAceitadoresFinal;
    private JSlider sliderTemperaturaFinal;
    private JSlider sliderCampoElectricoFinal;
    private JSlider sliderGradienteImpurezas;
    
    // JFormattedTextFields (valores finais)
    private JFormattedTextField txtConcDadoresFinal;
    private JFormattedTextField txtConcAceitadoresFinal;
    private JFormattedTextField txtTemperaturaFinal;
    private JFormattedTextField txtCampoElectricoFinal;
    private JFormattedTextField txtGradienteImpurezas;
    
    // JPanels (valores finais)
    private JPanel panelConcDadoresFinal;
    private JPanel panelConcAceitadoresFinal;
    private JPanel panelTemperaturaFinal;
    private JPanel panelCampoElectricoFinal;
    private JPanel panelGradienteImpurezas;
    
    // JCheckBoxes wCentro - pWidth)(para se escolher o que vai variar)
    private JCheckBox concDadoresCheck;
    private JCheckBox concAceitadoresCheck;
    private JCheckBox temperaturaCheck;
    private JCheckBox campoElectricoCheck;
    
    // Vari�veis necess�rias � escolha da l�ngua
    private JRadioButton portuguesButton;
    private JRadioButton englishButton;
    private JPanel panelPortugues;
    private JPanel panelEnglish;
    private JPanel panelLinguas;
    
    // Vari�veis necess�rias � escolha do material
    private JRadioButton silicioButton;
    private JRadioButton gaAsButton;
    private JPanel panelSilicio;
    private JPanel panelGaAs;
    private JPanel panelMaterial;
    
    // Vari�veis necess�rias � escolha do tipo de juncao
    private JRadioButton abruptaButton;
    private JRadioButton linearButton;
    private JPanel panelAbrupta;
    private JPanel panelLinear;
    private JPanel panelJuncao;
    
    // Painel para conter alguns conjuntos JSlider/JFormattedTextField, para efeitos de organiza��o
    private JPanel esquerda;
    private JPanel direita;
    
    //Bot�es para correr a simula��o ou limpar os valores introduzidos
    private JButton correrButton;
    private JButton limparButton;
    private JButton sairButton;
    private JPanel panelBotoes;
    
    // Labels para indicar certos valores ao utilizador
    private JLabel deltaDadoresLabel;
    private JLabel deltaAceitadoresLabel;
    private JLabel deltaTemperaturaLabel;
    private JLabel deltaPotencialLabel;
    private JPanel panelLabels;
    
    // Vari�veis v�rias. As dimens�es servir�o para desenhar o Graphical User Interface,
    // e a TitledBorder para identificar os diferentes par�metros.
    private Dimension pDimensao = new Dimension(450, 75);
    private Dimension txtFieldDim = new Dimension(50, 30);
    private Dimension checkBoxDim = new Dimension(60, 30);
    private Dimension buttonDim = new Dimension(150, 30);
    private TitledBorder title;
    
    // Frame para apresentar o painel (provis�rio)
    //static private JFrame frame;
    
    /** Construtor do painel, onde se desenham os v�rios elementos e se definem as ac��es
     *  que cada elemento vai desempenhar.
     */
    public SCustomizer()
    {
        
        /** DefiniwCentro - pWidth)��o dos valores iniciais - a menos que variem ao longo da experiencia,
         *  obviamente, ser�o tamb�m os definitivos.
         */
        // Cria��o do Slider
        sliderConcDadores = new JSlider(JSlider.HORIZONTAL, 1300, 1900, 1500);
        
        // Hashtable para o Slider
        Hashtable concsLabelTable = new Hashtable();
        concsLabelTable.put(new Integer(1300), new JLabel("<html>10<sup>13</sup>"));
        concsLabelTable.put(new Integer(1400), new JLabel("<html>10<sup>14</sup>"));
        concsLabelTable.put(new Integer(1500), new JLabel("<html>10<sup>15</sup>"));
        concsLabelTable.put(new Integer(1600), new JLabel("<html>10<sup>16</sup>"));
        concsLabelTable.put(new Integer(1700), new JLabel("<html>10<sup>17</sup>"));
        concsLabelTable.put(new Integer(1800), new JLabel("<html>10<sup>18</sup>"));
        concsLabelTable.put(new Integer(1900), new JLabel("<html>10<sup>19</sup>"));
        sliderConcDadores.setLabelTable(concsLabelTable);
        sliderConcDadores.setPaintLabels(true);
        
        // Ticks para o Slider
        sliderConcDadores.setMajorTickSpacing(40);
        sliderConcDadores.setMinorTickSpacing(20);
        sliderConcDadores.setPaintTicks(true);
        
        // Change Listener para o Slider, definido de forma a que ele interaja com a Formatted Text Field
        sliderConcDadores.addChangeListener( new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider)e.getSource();				// Vai buscar o Slider que disparou o Event
                double valor = (double)source.getValue();				// E recolhe-se o novo valor (ap�s a mudan�a)
                if (!source.getValueIsAdjusting())
                { 					// Se o utilizador acabou de ajustar o valor
                    txtConcDadores.setValue(new Double(valor/100)); 	// Actualiza-se o valor na Formatted Text Field
                } else
                {
                    txtConcDadores.setText(String.valueOf(valor/100));	// Se nao, apresenta-se apenas o dito valor
                }
                actualizarLabels();
            }
        });
        
        /** Cria��o do formato de n�mero desejado para os valores introduzidos.
         *  Este formato vai servir de "plano geral" para os valores a serem
         *  considerados, servindo mais tarde para o formatador verificar a
         *  validade dos valores introduzidos.
         */
        NumberFormat concsFormat = NumberFormat.getNumberInstance(new Locale("en", "US"));
        concsFormat.setMaximumFractionDigits(2);
        concsFormat.setMinimumFractionDigits(0);
        
        /** Formatador que vai avaliar os valores introduzidos de acordo com o
         *  formato criado, bem como outros limites impostos (a definir de acordo
         *  com cada caso individual).
         */
        NumberFormatter concsFormatador = new NumberFormatter(concsFormat);
        concsFormatador.setMinimum(new Double(13));		// Valor m�nimo aceite pelo formatador
        concsFormatador.setMaximum(new Double(19));		// Valor m�ximo aceite pelo formatador
        
        /** Cria��o da JFormattedTextField que vai fazer uso do formatador.
         *  Esta ir� interagir com o JSlider de forma a permitir a introdu��o
         *  de um valor espec�fico de forma mais simples e directa que o
         *  slider permite, utilizando para controlo de valores o formatador
         *  criado anteriormente.
         */
        txtConcDadores = new JFormattedTextField(concsFormatador);
        txtConcDadores.setValue(new Double(15));				// Valor inicial da caixa de texto.
        txtConcDadores.setColumns(5);							// N�mero de colunas da caixa (para ganhar algum espa�o).
        txtConcDadores.setMaximumSize(txtFieldDim);				// Tamanho m�ximo da caixa.
        
        /** Defini��o do InputMap da Text Field, de forma a que esta
         *  reaja ao pressionar da tecla enter al�m da perda de focus.
         */
        txtConcDadores.getInputMap().put(KeyStroke.getKeyStroke(
        KeyEvent.VK_ENTER, 0),
        "check");
        
        /** Defini��o do ActionMap da Text Field. Este mapa ir� conter
         *  as ac��es que a Text Field executa para cada input definido
         *  no input map.
         */
        txtConcDadores.getActionMap().put("check", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {		// ActionEvent (para alguma ac��o executada)
                if (!txtConcDadores.isEditValid())
                { 			// Se o texto introduzido for inv�lido,
                    Toolkit.getDefaultToolkit().beep();			// avisa-se o utilizador com um beep
                    txtConcDadores.selectAll();					// e selecciona-se o texto introduzido.
                } else try
                {                    				// Se o texto for v�lido,
                    txtConcDadores.commitEdit();     			// aprova-se a edi��o do valor da Text Field.
                } catch (java.text.ParseException exc)
                { }
            }
        });
        
        /** Property Change Listener para a Text Field. Este bloco de
         *  c�digo vai conter as instru��es necess�rias para a defini��o
         *  dos valores da simula��o de acordo com o escolhido pelo utilizador.
         */
        txtConcDadores.addPropertyChangeListener(new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                if ("value".equals(e.getPropertyName()))
                {				// Se a propriedade alterada for a do valor (diferente do texto!)
                    Number value = (Number)e.getNewValue();				// vai-se buscar o novo valor introduzido.
                    if (sliderConcDadores != null && value != null)
                    {	// Por uma quest�o de seguran�a verifica-se a exist�ncia destes dois objectos
                        sliderConcDadores.setValue((int)(value.doubleValue()*100));// e altera-se o valor condignamente.
                        actualizarLabels();
                        if(!concDadoresCheck.isSelected()) sliderConcDadoresFinal.setValue(sliderConcDadores.getValue());
                    }
                }
            }
        });
        
        /** Cria��o do JPanel que vai conter o Slider e a Text Field, al�m
         *  de uma border identificadora do par�metro que vai ser definido
         *  nesses componentes.
         */
        panelConcDadores = new JPanel();
        title = BorderFactory.createTitledBorder("Concentra\u00e7\u00e3o de Dadores Inicial (10^x) (cm^-3)");// Cria��o da border e do t�tulo
        title.setTitleJustification(TitledBorder.CENTER);									// Posicionamento do t�tulo
        title.setTitlePosition(TitledBorder.TOP);
        panelConcDadores.setBorder(title);				// Defini��o do painel; border, dimens�o, layout e componentes
        panelConcDadores.setPreferredSize(pDimensao);
        panelConcDadores.setLayout(new BoxLayout(panelConcDadores, BoxLayout.X_AXIS));
        panelConcDadores.add(sliderConcDadores);
        panelConcDadores.add(txtConcDadores);
        
        /** Dado que o que se segue � apenas uma repeti��o para cada par�metro
         *  do que j� foi feito at� agora, julga-se desnecess�rio a repeti��o
         *  dos coment�rios e explica��es oferecidas dado que ser� sempre uma
         *  simples quest�o de procurar o segmento correspondente do j� oferecido.
         *  Assim sendo comentaremos apenas aquelas passagens que forem novas
         *  e que ainda n�o tenham sido explicadas/comentadas ao longo do c�digo.
         */
        
        // Novo conjunto JSlider/JFormattedTextField/JRadioButton/etc
        sliderConcAceitadores = new JSlider(JSlider.HORIZONTAL, 1300, 1900, 1500);
        
        sliderConcAceitadores.setLabelTable(concsLabelTable);
        sliderConcAceitadores.setPaintLabels(true);
        
        sliderConcAceitadores.setMajorTickSpacing(40);
        sliderConcAceitadores.setMinorTickSpacing(20);
        sliderConcAceitadores.setPaintTicks(true);
        
        sliderConcAceitadores.addChangeListener( new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider)e.getSource();
                double valor = (double)source.getValue();
                if (!source.getValueIsAdjusting())
                {
                    txtConcAceitadores.setValue(new Double(valor/100));
                } else
                {
                    txtConcAceitadores.setText(String.valueOf(valor/100));
                }
                actualizarLabels();
            }
        });
        
        txtConcAceitadores = new JFormattedTextField(concsFormatador);
        txtConcAceitadores.setValue(new Double(15));
        txtConcAceitadores.setColumns(5);
        txtConcAceitadores.setMaximumSize(txtFieldDim);
        
        txtConcAceitadores.getInputMap().put(KeyStroke.getKeyStroke(
        KeyEvent.VK_ENTER, 0),
        "check");
        txtConcAceitadores.getActionMap().put("check", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (!txtConcAceitadores.isEditValid())
                {
                    Toolkit.getDefaultToolkit().beep();
                    txtConcAceitadores.selectAll();
                } else try
                {
                    txtConcAceitadores.commitEdit();
                } catch (java.text.ParseException exc)
                { }
            }
        });
        
        txtConcAceitadores.addPropertyChangeListener(new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                if ("value".equals(e.getPropertyName()))
                {
                    Number value = (Number)e.getNewValue();
                    if (sliderConcAceitadores != null && value != null)
                    {
                        sliderConcAceitadores.setValue((int)(value.doubleValue()*100));
                        actualizarLabels();
                        if(!concAceitadoresCheck.isSelected()) sliderConcAceitadoresFinal.setValue(sliderConcAceitadores.getValue());
                    }
                }
            }
        });
        
        panelConcAceitadores = new JPanel();
        title = BorderFactory.createTitledBorder("Concentra\u00e7\u00e3o de Aceitadores Inicial (10^x) (cm^-3)");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.TOP);
        panelConcAceitadores.setBorder(title);
        panelConcAceitadores.setPreferredSize(pDimensao);
        panelConcAceitadores.setLayout(new BoxLayout(panelConcAceitadores, BoxLayout.X_AXIS));
        panelConcAceitadores.add(sliderConcAceitadores);
        panelConcAceitadores.add(txtConcAceitadores);
        
        // Novo conjunto JSlider/JFormattedTextField/JRadioButton/etc
        sliderTemperatura = new JSlider(JSlider.HORIZONTAL, 10000, 60000, 30000);
        
        Hashtable temperaturaLabelTable = new Hashtable();
        temperaturaLabelTable.put(new Integer(10000), new JLabel("100"));
        temperaturaLabelTable.put(new Integer(20000), new JLabel("200"));
        temperaturaLabelTable.put(new Integer(30000), new JLabel("300"));
        temperaturaLabelTable.put(new Integer(40000), new JLabel("400"));
        temperaturaLabelTable.put(new Integer(50000), new JLabel("500"));
        temperaturaLabelTable.put(new Integer(60000), new JLabel("600"));
        sliderTemperatura.setLabelTable(temperaturaLabelTable);
        sliderTemperatura.setPaintLabels(true);
        
        sliderTemperatura.setMajorTickSpacing(5000);
        sliderTemperatura.setMinorTickSpacing(2500);
        sliderTemperatura.setPaintTicks(true);
        
        sliderTemperatura.addChangeListener( new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider)e.getSource();
                double valor = (double)source.getValue();
                if (!source.getValueIsAdjusting())
                {
                    txtTemperatura.setValue(new Double(valor/100.0));
                } else
                {
                    txtTemperatura.setText(String.valueOf(valor/100.0));
                }
                actualizarLabels();
            }
        });
        
        NumberFormat temperaturaFormat = NumberFormat.getNumberInstance(new Locale("en", "US"));
        temperaturaFormat.setMaximumFractionDigits(2);
        temperaturaFormat.setMinimumFractionDigits(0);
        NumberFormatter temperaturaFormatador = new NumberFormatter(temperaturaFormat);
        temperaturaFormatador.setMinimum(new Double(100));
        temperaturaFormatador.setMaximum(new Double(600));
        txtTemperatura = new JFormattedTextField(temperaturaFormatador);
        txtTemperatura.setValue(new Double(300));
        txtTemperatura.setColumns(5);
        txtTemperatura.setMaximumSize(txtFieldDim);
        
        txtTemperatura.getInputMap().put(KeyStroke.getKeyStroke(
        KeyEvent.VK_ENTER, 0),
        "check");
        txtTemperatura.getActionMap().put("check", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (!txtTemperatura.isEditValid())
                {
                    Toolkit.getDefaultToolkit().beep();
                    txtTemperatura.selectAll();
                } else try
                {
                    txtTemperatura.commitEdit();
                } catch (java.text.ParseException exc)
                { }
            }
        });
        
        txtTemperatura.addPropertyChangeListener(new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                if ("value".equals(e.getPropertyName()))
                {
                    Number value = (Number)e.getNewValue();
                    if (sliderTemperatura != null && value != null)
                    {
                        sliderTemperatura.setValue((int)(value.doubleValue()*100.0));
                        actualizarLabels();
                        if(!temperaturaCheck.isSelected()) sliderTemperaturaFinal.setValue(sliderTemperatura.getValue());
                    }
                }
            }
        });
        
        panelTemperatura = new JPanel();
        title = BorderFactory.createTitledBorder("Temperatura Inicial (K)");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.TOP);
        panelTemperatura.setBorder(title);
        panelTemperatura.setPreferredSize(pDimensao);
        panelTemperatura.setLayout(new BoxLayout(panelTemperatura, BoxLayout.X_AXIS));
        panelTemperatura.add(sliderTemperatura);
        panelTemperatura.add(txtTemperatura);
        
        // Novo conjunto JSlider/JFormattedTextField/etc
        sliderTempoAmostras = new JSlider(JSlider.HORIZONTAL, 100, 500, 200);
        
        Hashtable tempoAmostrasLabelTable = new Hashtable();
        tempoAmostrasLabelTable.put(new Integer(100), new JLabel("100"));
        tempoAmostrasLabelTable.put(new Integer(200), new JLabel("200"));
        tempoAmostrasLabelTable.put(new Integer(300), new JLabel("300"));
        tempoAmostrasLabelTable.put(new Integer(400), new JLabel("400"));
        tempoAmostrasLabelTable.put(new Integer(500), new JLabel("500"));
        sliderTempoAmostras.setLabelTable(tempoAmostrasLabelTable);
        sliderTempoAmostras.setPaintLabels(true);
        
        sliderTempoAmostras.setMajorTickSpacing(40);
        sliderTempoAmostras.setMinorTickSpacing(20);
        sliderTempoAmostras.setPaintTicks(true);
        
        sliderTempoAmostras.addChangeListener( new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider)e.getSource();
                int valor = (int)source.getValue();
                if (!source.getValueIsAdjusting())
                {
                    txtTempoAmostras.setValue(new Integer(valor));
                } else
                {
                    txtTempoAmostras.setText(String.valueOf(valor));
                }
                actualizarLabels();
            }
        });
        
        NumberFormat tempoAmostrasFormat = NumberFormat.getNumberInstance(new Locale("en", "US"));
        tempoAmostrasFormat.setMaximumFractionDigits(0);
        tempoAmostrasFormat.setMinimumFractionDigits(0);
        NumberFormatter tempoAmostrasFormatador = new NumberFormatter(tempoAmostrasFormat);
        tempoAmostrasFormatador.setMinimum(new Integer(100));
        tempoAmostrasFormatador.setMaximum(new Integer(500));
        txtTempoAmostras = new JFormattedTextField(tempoAmostrasFormatador);
        txtTempoAmostras.setValue(new Double(200));
        txtTempoAmostras.setColumns(5);
        txtTempoAmostras.setMaximumSize(txtFieldDim);
        
        txtTempoAmostras.getInputMap().put(KeyStroke.getKeyStroke(
        KeyEvent.VK_ENTER, 0),
        "check");
        txtTempoAmostras.getActionMap().put("check", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (!txtTempoAmostras.isEditValid())
                {
                    Toolkit.getDefaultToolkit().beep();
                    txtTempoAmostras.selectAll();
                } else try
                {
                    txtTempoAmostras.commitEdit();
                } catch (java.text.ParseException exc)
                { }
            }
        });
        
        txtTempoAmostras.addPropertyChangeListener(new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                if ("value".equals(e.getPropertyName()))
                {
                    Number value = (Number)e.getNewValue();
                    if (sliderTempoAmostras != null && value != null)
                    {
                        sliderTempoAmostras.setValue((value.intValue()));
                        actualizarLabels();
                    }
                }
            }
        });
        
        panelTempoAmostras = new JPanel();
        title = BorderFactory.createTitledBorder("Tempo entre Amostras (ms)");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.TOP);
        panelTempoAmostras.setBorder(title);
        panelTempoAmostras.setPreferredSize(pDimensao);
        panelTempoAmostras.setLayout(new BoxLayout(panelTempoAmostras, BoxLayout.X_AXIS));
        panelTempoAmostras.add(sliderTempoAmostras);
        panelTempoAmostras.add(txtTempoAmostras);
        
        // Novo conjunto JSlider/JFormattedTextField/etc
        sliderNumAmostras = new JSlider(JSlider.HORIZONTAL, 0, 500, 10);
        sliderNumAmostras.setLabelTable(tempoAmostrasLabelTable);
        sliderNumAmostras.setPaintLabels(true);
        
        sliderNumAmostras.setMajorTickSpacing(40);
        sliderNumAmostras.setMinorTickSpacing(20);
        sliderNumAmostras.setPaintTicks(true);
        
        sliderNumAmostras.addChangeListener( new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider)e.getSource();
                double valor = (double)source.getValue();
                if (!source.getValueIsAdjusting())
                {
                    txtNumAmostras.setValue(new Integer((int)valor));
                } else
                {
                    txtNumAmostras.setText(String.valueOf(valor));
                }
                actualizarLabels();
            }
        });
        
        txtNumAmostras = new JFormattedTextField(tempoAmostrasFormatador);
        txtNumAmostras.setValue(new Integer(10));
        txtNumAmostras.setColumns(5);
        txtNumAmostras.setMaximumSize(txtFieldDim);
        
        txtNumAmostras.getInputMap().put(KeyStroke.getKeyStroke(
        KeyEvent.VK_ENTER, 0),
        "check");
        txtNumAmostras.getActionMap().put("check", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (!txtNumAmostras.isEditValid())
                {
                    Toolkit.getDefaultToolkit().beep();
                    txtNumAmostras.selectAll();
                } else try
                {
                    txtNumAmostras.commitEdit();
                } catch (java.text.ParseException exc)
                { }
            }
        });
        
        txtNumAmostras.addPropertyChangeListener(new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                if ("value".equals(e.getPropertyName()))
                {
                    Number value = (Number)e.getNewValue();
                    if (sliderNumAmostras != null && value != null)
                    {
                        sliderNumAmostras.setValue((value.intValue()));
                        actualizarLabels();
                    }
                }
            }
        });
        
        panelNumAmostras = new JPanel();
        title = BorderFactory.createTitledBorder("N\u00famero de Amostras");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.TOP);
        panelNumAmostras.setBorder(title);
        panelNumAmostras.setPreferredSize(pDimensao);
        panelNumAmostras.setLayout(new BoxLayout(panelNumAmostras, BoxLayout.X_AXIS));
        panelNumAmostras.add(sliderNumAmostras);
        panelNumAmostras.add(txtNumAmostras);
        
        // Novo conjunto JSlider/JFormattedTextField/JRadioButton/etc
        sliderCampoElectrico = new JSlider(JSlider.HORIZONTAL, -1000, 1000, -100);
        
        Hashtable campoElectricoLabelTable = new Hashtable();
        campoElectricoLabelTable.put(new Integer(-1000), new JLabel("-10"));
        campoElectricoLabelTable.put(new Integer(-500), new JLabel("-5"));
        campoElectricoLabelTable.put(new Integer(0), new JLabel("0"));
        campoElectricoLabelTable.put(new Integer(500), new JLabel("+5"));
        campoElectricoLabelTable.put(new Integer(1000), new JLabel("+10"));
        sliderCampoElectrico.setLabelTable(campoElectricoLabelTable);
        sliderCampoElectrico.setPaintLabels(true);
        
        sliderCampoElectrico.setMajorTickSpacing(200);
        sliderCampoElectrico.setMinorTickSpacing(100);
        sliderCampoElectrico.setPaintTicks(true);
        
        sliderCampoElectrico.addChangeListener( new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider)e.getSource();
                double valor = (double)source.getValue();
                if (!source.getValueIsAdjusting())
                {
                    txtCampoElectrico.setValue(new Double(valor/100));
                } else
                {
                    txtCampoElectrico.setText(String.valueOf(valor/100));
                }
                actualizarLabels();
            }
        });
        
        NumberFormat campoElectricoFormat = NumberFormat.getNumberInstance(new Locale("en", "US"));
        campoElectricoFormat.setMaximumFractionDigits(2);
        campoElectricoFormat.setMinimumFractionDigits(0);
        NumberFormatter campoElectricoFormatador = new NumberFormatter(campoElectricoFormat);
        campoElectricoFormatador.setMinimum(new Double(-10));
        campoElectricoFormatador.setMaximum(new Double(10));
        txtCampoElectrico = new JFormattedTextField(campoElectricoFormatador);
        txtCampoElectrico.setValue(new Double(-1));
        txtCampoElectrico.setColumns(5);
        txtCampoElectrico.setMaximumSize(txtFieldDim);
        
        txtCampoElectrico.getInputMap().put(KeyStroke.getKeyStroke(
        KeyEvent.VK_ENTER, 0),
        "check");
        txtCampoElectrico.getActionMap().put("check", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (!txtCampoElectrico.isEditValid())
                {
                    Toolkit.getDefaultToolkit().beep();
                    txtCampoElectrico.selectAll();
                } else try
                {
                    txtCampoElectrico.commitEdit();
                } catch (java.text.ParseException exc)
                { }
            }
        });
        
        txtCampoElectrico.addPropertyChangeListener(new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                if ("value".equals(e.getPropertyName()))
                {
                    Number value = (Number)e.getNewValue();
                    if (sliderCampoElectrico != null && value != null)
                    {
                        sliderCampoElectrico.setValue((int)(value.doubleValue()*100.0));
                        actualizarLabels();
                        if(!campoElectricoCheck.isSelected()) sliderCampoElectricoFinal.setValue(sliderCampoElectrico.getValue());
                    }
                }
            }
        });
        
        panelCampoElectrico = new JPanel();
        title = BorderFactory.createTitledBorder("Potencial Externo Inicial (V)");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.TOP);
        panelCampoElectrico.setBorder(title);
        panelCampoElectrico.setPreferredSize(pDimensao);
        panelCampoElectrico.setLayout(new BoxLayout(panelCampoElectrico, BoxLayout.X_AXIS));
        panelCampoElectrico.add(sliderCampoElectrico);
        panelCampoElectrico.add(txtCampoElectrico);
        // Fim da cria��o das combina��es de painel/slider/text field.
        
        /** Defini��o dos valores
         * finais - apenas ser�o definidos se as checkboxes correspondentes
         *  estiverem activadas. Nesse caso ser� definido n�o s� o valor inicial como tamb�m o final,
         *  e de acordo com esses limites e o n�mero de amostras ser� ent�o calculado o n�mero e o
         *  tamanho dos intervalos em que variam as respectivas grandezas.
         */
        // Cria��o do Slider
        sliderConcDadoresFinal = new JSlider(JSlider.HORIZONTAL, 1300, 1900, 1700);
        sliderConcDadoresFinal.setLabelTable(concsLabelTable);
        sliderConcDadoresFinal.setPaintLabels(true);
        
        // Ticks para o Slider
        sliderConcDadoresFinal.setMajorTickSpacing(40);
        sliderConcDadoresFinal.setMinorTickSpacing(20);
        sliderConcDadoresFinal.setPaintTicks(true);
        
        // Change Listener para o Slider, definido de forma a que ele interaja com a Formatted Text Field
        sliderConcDadoresFinal.addChangeListener( new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider)e.getSource();			// Vai buscar o Slider que disparou o Event
                double valor = (double)source.getValue();			// E recolhe-se o novo valor (ap�s a mudan�a)
                if (!source.getValueIsAdjusting())
                { 				// Se o utilizador acabou de ajustar o valor
                    txtConcDadoresFinal.setValue(new Double(valor/100));// Actualiza-se o valor na Formatted Text Field
                } else
                {
                    txtConcDadoresFinal.setText(String.valueOf(valor/100));	// Se nao, apresenta-se apenas o dito valor
                }
                actualizarLabels();
            }
        });
        
        /** Cria��o da JFormattedTextField que vai fazer uso do formatador.
         *  Esta ir� interagir com o JSlider de forma a permitir a introdu��o
         *  de um valor espec�fico de forma mais simples e directa que o
         *  slider permite, utilizando para controlo de valores o formatador
         *  criado anteriormente.
         */
        txtConcDadoresFinal = new JFormattedTextField(concsFormatador);
        txtConcDadoresFinal.setValue(new Double(17));				// Valor inicial da caixa de texto.
        txtConcDadoresFinal.setColumns(5);							// N�mero de colunas da caixa (para ganhar algum espa�o).
        txtConcDadoresFinal.setMaximumSize(txtFieldDim);			// Tamanho m�ximo da caixa.
        
        /** Defini��o do InputMap da Text Field, de forma a que esta
         *  reaja ao pressionar da tecla enter al�m da perda de focus.
         */
        txtConcDadoresFinal.getInputMap().put(KeyStroke.getKeyStroke(
        KeyEvent.VK_ENTER, 0),
        "check");
        
        /** Defini��o do ActionMap da Text Field. Este mapa ir� conter
         *  as ac��es que a Text Field executa para cada input definido
         *  no input map.
         */
        txtConcDadoresFinal.getActionMap().put("check", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {		// ActionEvent (para alguma ac��o executada)
                if (!txtConcDadoresFinal.isEditValid())
                { 		// Se o texto introduzido for inv�lido,
                    Toolkit.getDefaultToolkit().beep();			// avisa-se o utilizador com um beep
                    txtConcDadoresFinal.selectAll();			// e selecciona-se o texto introduzido.
                } else try
                {                    				// Se o texto for v�lido,
                    txtConcDadoresFinal.commitEdit();     		// aprova-se a edi��o do valor da Text Field.
                } catch (java.text.ParseException exc)
                { }
            }
        });
        
        /** Property Change Listener para a Text Field. Este bloco de
         *  c�digo vai conter as instru��es necess�rias para a defini��o
         *  dos valores da simula��o de acordo com o escolhido pelo utilizador.
         */
        txtConcDadoresFinal.addPropertyChangeListener(new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                if ("value".equals(e.getPropertyName()))
                {				// Se a propriedade alterada for a do valor (diferente do texto!)
                    Number value = (Number)e.getNewValue();				// vai-se buscar o novo valor introduzido.
                    if (sliderConcDadoresFinal != null && value != null)
                    {	// Por uma quest�o de seguran�a verifica-se a exist�ncia destes dois objectos
                        sliderConcDadoresFinal.setValue((int)(value.doubleValue()*100.0));	// e altera-se o valor condignamente
                        actualizarLabels();
                    }
                }
            }
        });
        
        // Check box para definir quais os par�metros a variar
        concDadoresCheck = new JCheckBox();
        
        concDadoresCheck.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent evt)
            {
                if(concDadoresCheck.isSelected())
                {
                    sliderConcDadoresFinal.setEnabled(true);
                    txtConcDadoresFinal.setEnabled(true);
                } else
                {
                    txtConcDadoresFinal.setValue(txtConcDadores.getValue());
                    sliderConcDadoresFinal.setEnabled(false);
                    txtConcDadoresFinal.setEnabled(false);
                }
                actualizarLabels();
            }
        });
        
        JPanel panelConcDadoresCheck = new JPanel();
        panelConcDadoresCheck.setMaximumSize(checkBoxDim);
        panelConcDadoresCheck.setBorder(BorderFactory.createLineBorder(Color.gray));
        
        panelConcDadoresCheck.add(concDadoresCheck);
        
        /** Cria��o do JPanel que vai conter o Slider e a Text Field, al�m
         *  de uma border identificadora do par�metro que vai ser definido
         *  nesses componentes.
         */
        panelConcDadoresFinal = new JPanel();
        title = BorderFactory.createTitledBorder("Concentra\u00e7\u00e3o de Dadores Final (10^x) (cm^-3)");// Cria��o da border e do t�tulo
        title.setTitleJustification(TitledBorder.CENTER);							// Posicionamento do t�tulo
        title.setTitlePosition(TitledBorder.TOP);
        panelConcDadoresFinal.setBorder(title);				// Defini��o do painel; border, dimens�o, layout e componentes
        panelConcDadoresFinal.setPreferredSize(pDimensao);
        panelConcDadoresFinal.setLayout(new BoxLayout(panelConcDadoresFinal, BoxLayout.X_AXIS));
        panelConcDadoresFinal.add(panelConcDadoresCheck);
        panelConcDadoresFinal.add(sliderConcDadoresFinal);
        panelConcDadoresFinal.add(txtConcDadoresFinal);
        
        /** Dado que o que se segue � apenas uma repeti��o para cada par�metro
         *  do que j� foi feito at� agora, julga-se desnecess�rio a repeti��o
         *  dos coment�rios e explica��es oferecidas dado que ser� sempre uma
         *  simples quest�o de procurar o segmento correspondente do j� oferecido.
         *  Assim sendo comentaremos apenas aquelas passagens que forem novas
         *  e que ainda n�o tenham sido explicadas/comentadas ao longo do c�digo.
         */
        
        // Novo conjunto JSlider/JFormattedTextField/JRadioButton/etc
        sliderConcAceitadoresFinal = new JSlider(JSlider.HORIZONTAL, 1300, 1900, 1700);
        sliderConcAceitadoresFinal.setLabelTable(concsLabelTable);
        sliderConcAceitadoresFinal.setPaintLabels(true);
        
        sliderConcAceitadoresFinal.setMajorTickSpacing(40);
        sliderConcAceitadoresFinal.setMinorTickSpacing(20);
        sliderConcAceitadoresFinal.setPaintTicks(true);
        
        sliderConcAceitadoresFinal.addChangeListener( new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider)e.getSource();
                double valor = (double)source.getValue();
                if (!source.getValueIsAdjusting())
                {
                    txtConcAceitadoresFinal.setValue(new Double(valor/100));
                } else
                {
                    txtConcAceitadoresFinal.setText(String.valueOf(valor/100));
                }
                actualizarLabels();
            }
        });
        
        txtConcAceitadoresFinal = new JFormattedTextField(concsFormatador);
        txtConcAceitadoresFinal.setValue(new Double(17));
        txtConcAceitadoresFinal.setColumns(5);
        txtConcAceitadoresFinal.setMaximumSize(txtFieldDim);
        
        txtConcAceitadoresFinal.getInputMap().put(KeyStroke.getKeyStroke(
        KeyEvent.VK_ENTER, 0),
        "check");
        txtConcAceitadoresFinal.getActionMap().put("check", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (!txtConcAceitadoresFinal.isEditValid())
                {
                    Toolkit.getDefaultToolkit().beep();
                    txtConcAceitadoresFinal.selectAll();
                } else try
                {
                    txtConcAceitadoresFinal.commitEdit();
                } catch (java.text.ParseException exc)
                { }
            }
        });
        
        txtConcAceitadoresFinal.addPropertyChangeListener(new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                if ("value".equals(e.getPropertyName()))
                {
                    Number value = (Number)e.getNewValue();
                    if (sliderConcAceitadoresFinal != null && value != null)
                    {
                        sliderConcAceitadoresFinal.setValue((int)(value.doubleValue()*100.0));
                        actualizarLabels();
                    }
                }
            }
        });
        
        concAceitadoresCheck = new JCheckBox();
        
        concAceitadoresCheck.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent evt)
            {
                if(concAceitadoresCheck.isSelected())
                {
                    sliderConcAceitadoresFinal.setEnabled(true);
                    txtConcAceitadoresFinal.setEnabled(true);
                } else
                {
                    txtConcAceitadoresFinal.setValue(txtConcAceitadores.getValue());
                    sliderConcAceitadoresFinal.setEnabled(false);
                    txtConcAceitadoresFinal.setEnabled(false);
                }
                actualizarLabels();
            }
        });
        
        JPanel panelConcAceitadoresCheck = new JPanel();
        panelConcAceitadoresCheck.setMaximumSize(checkBoxDim);
        panelConcAceitadoresCheck.setBorder(BorderFactory.createLineBorder(Color.gray));
        
        panelConcAceitadoresCheck.add(concAceitadoresCheck);
        
        panelConcAceitadoresFinal = new JPanel();
        title = BorderFactory.createTitledBorder("Concentra\u00e7\u00e3o de Aceitadores Final (10^x) (cm^-3)");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.TOP);
        panelConcAceitadoresFinal.setBorder(title);
        panelConcAceitadoresFinal.setPreferredSize(pDimensao);
        panelConcAceitadoresFinal.setLayout(new BoxLayout(panelConcAceitadoresFinal, BoxLayout.X_AXIS));
        panelConcAceitadoresFinal.add(panelConcAceitadoresCheck);
        panelConcAceitadoresFinal.add(sliderConcAceitadoresFinal);
        panelConcAceitadoresFinal.add(txtConcAceitadoresFinal);
        
        
        // Novo conjunto JSlider/JFormattedTextField/JRadioButton/etc
        sliderTemperaturaFinal = new JSlider(JSlider.HORIZONTAL, 10000, 60000, 40000);
        sliderTemperaturaFinal.setLabelTable(temperaturaLabelTable);
        sliderTemperaturaFinal.setPaintLabels(true);
        
        sliderTemperaturaFinal.setMajorTickSpacing(5000);
        sliderTemperaturaFinal.setMinorTickSpacing(2500);
        sliderTemperaturaFinal.setPaintTicks(true);
        
        sliderTemperaturaFinal.addChangeListener( new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider)e.getSource();
                double valor = (double)source.getValue();
                if (!source.getValueIsAdjusting())
                {
                    txtTemperaturaFinal.setValue(new Double(valor/100.0));
                } else
                {
                    txtTemperaturaFinal.setText(String.valueOf(valor/100.0));
                }
                actualizarLabels();
            }
        });
        
        txtTemperaturaFinal = new JFormattedTextField(temperaturaFormatador);
        txtTemperaturaFinal.setValue(new Double(400));
        txtTemperaturaFinal.setColumns(5);
        txtTemperaturaFinal.setMaximumSize(txtFieldDim);
        
        txtTemperaturaFinal.getInputMap().put(KeyStroke.getKeyStroke(
        KeyEvent.VK_ENTER, 0),
        "check");
        txtTemperaturaFinal.getActionMap().put("check", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (!txtTemperaturaFinal.isEditValid())
                {
                    Toolkit.getDefaultToolkit().beep();
                    txtTemperaturaFinal.selectAll();
                } else try
                {
                    txtTemperaturaFinal.commitEdit();
                } catch (java.text.ParseException exc)
                { }
            }
        });
        
        txtTemperaturaFinal.addPropertyChangeListener(new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                if ("value".equals(e.getPropertyName()))
                {
                    Number value = (Number)e.getNewValue();
                    if (sliderTemperaturaFinal != null && value != null)
                    {
                        sliderTemperaturaFinal.setValue((int)(value.doubleValue()*100.0));
                        actualizarLabels();
                    }
                }
            }
        });
        
        temperaturaCheck = new JCheckBox();
        
        temperaturaCheck.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent evt)
            {
                if(temperaturaCheck.isSelected())
                {
                    sliderTemperaturaFinal.setEnabled(true);
                    txtTemperaturaFinal.setEnabled(true);
                } else
                {
                    txtTemperaturaFinal.setValue(txtTemperatura.getValue());
                    sliderTemperaturaFinal.setEnabled(false);
                    txtTemperaturaFinal.setEnabled(false);
                }
                actualizarLabels();
            }
        });
        
        JPanel panelTemperaturaCheck = new JPanel();
        panelTemperaturaCheck.setMaximumSize(checkBoxDim);
        panelTemperaturaCheck.setBorder(BorderFactory.createLineBorder(Color.gray));
        
        panelTemperaturaCheck.add(temperaturaCheck);
        
        panelTemperaturaFinal = new JPanel();
        title = BorderFactory.createTitledBorder("Temperatura Final (K)");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.TOP);
        panelTemperaturaFinal.setBorder(title);
        panelTemperaturaFinal.setPreferredSize(pDimensao);
        panelTemperaturaFinal.setLayout(new BoxLayout(panelTemperaturaFinal, BoxLayout.X_AXIS));
        panelTemperaturaFinal.add(panelTemperaturaCheck);
        panelTemperaturaFinal.add(sliderTemperaturaFinal);
        panelTemperaturaFinal.add(txtTemperaturaFinal);
        
        // Novo conjunto JSlider/JFormattedTextField/JRadioButton/etc
        sliderCampoElectricoFinal = new JSlider(JSlider.HORIZONTAL, -1000, 1000, 100);
        sliderCampoElectricoFinal.setLabelTable(campoElectricoLabelTable);
        sliderCampoElectricoFinal.setPaintLabels(true);
        
        sliderCampoElectricoFinal.setMajorTickSpacing(200);
        sliderCampoElectricoFinal.setMinorTickSpacing(100);
        sliderCampoElectricoFinal.setPaintTicks(true);
        
        sliderCampoElectricoFinal.addChangeListener( new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider)e.getSource();
                double valor = (double)source.getValue();
                if (!source.getValueIsAdjusting())
                {
                    txtCampoElectricoFinal.setValue(new Double(valor/100));
                } else
                {
                    txtCampoElectricoFinal.setText(String.valueOf(valor/100));
                }
                actualizarLabels();
            }
        });
        
        txtCampoElectricoFinal = new JFormattedTextField(campoElectricoFormatador);
        txtCampoElectricoFinal.setValue(new Double(1));
        txtCampoElectricoFinal.setColumns(5);
        txtCampoElectricoFinal.setMaximumSize(txtFieldDim);
        
        txtCampoElectricoFinal.getInputMap().put(KeyStroke.getKeyStroke(
        KeyEvent.VK_ENTER, 0),
        "check");
        txtCampoElectricoFinal.getActionMap().put("check", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (!txtCampoElectricoFinal.isEditValid())
                {
                    Toolkit.getDefaultToolkit().beep();
                    txtCampoElectricoFinal.selectAll();
                } else try
                {
                    txtCampoElectricoFinal.commitEdit();
                } catch (java.text.ParseException exc)
                { }
            }
        });
        
        txtCampoElectricoFinal.addPropertyChangeListener(new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                if ("value".equals(e.getPropertyName()))
                {
                    Number value = (Number)e.getNewValue();
                    if (sliderCampoElectricoFinal != null && value != null)
                    {
                        sliderCampoElectricoFinal.setValue((int)(value.doubleValue()*100.0));
                        actualizarLabels();
                    }
                }
            }
        });
        
        campoElectricoCheck = new JCheckBox();
        
        campoElectricoCheck.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent evt)
            {
                if(campoElectricoCheck.isSelected())
                {
                    sliderCampoElectricoFinal.setEnabled(true);
                    txtCampoElectricoFinal.setEnabled(true);
                } else
                {
                    txtCampoElectricoFinal.setValue(txtCampoElectrico.getValue());
                    sliderCampoElectricoFinal.setEnabled(false);
                    txtCampoElectricoFinal.setEnabled(false);
                }
                actualizarLabels();
            }
        });
        
        JPanel panelCampoElectricoCheck = new JPanel();
        panelCampoElectricoCheck.setMaximumSize(checkBoxDim);
        panelCampoElectricoCheck.setBorder(BorderFactory.createLineBorder(Color.gray));
        
        panelCampoElectricoCheck.add(campoElectricoCheck);
        
        panelCampoElectricoFinal = new JPanel();
        title = BorderFactory.createTitledBorder("Potencial Externo Final (V)");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.TOP);
        panelCampoElectricoFinal.setBorder(title);
        panelCampoElectricoFinal.setPreferredSize(pDimensao);
        panelCampoElectricoFinal.setLayout(new BoxLayout(panelCampoElectricoFinal, BoxLayout.X_AXIS));
        panelCampoElectricoFinal.add(panelCampoElectricoCheck);
        panelCampoElectricoFinal.add(sliderCampoElectricoFinal);
        panelCampoElectricoFinal.add(txtCampoElectricoFinal);
        
        // Cria��o do Slider
        sliderGradienteImpurezas = new JSlider(JSlider.HORIZONTAL, 1900, 2300, 2100);
        
        // Hashtable para o Slider
        Hashtable gradienteLabelTable = new Hashtable();
        gradienteLabelTable.put(new Integer(1900), new JLabel("<html>10<sup>19</sup>"));
        gradienteLabelTable.put(new Integer(2000), new JLabel("<html>10<sup>20</sup>"));
        gradienteLabelTable.put(new Integer(2100), new JLabel("<html>10<sup>21</sup>"));
        gradienteLabelTable.put(new Integer(2200), new JLabel("<html>10<sup>22</sup>"));
        gradienteLabelTable.put(new Integer(2300), new JLabel("<html>10<sup>23</sup>"));
        sliderGradienteImpurezas.setLabelTable(gradienteLabelTable);
        sliderGradienteImpurezas.setPaintLabels(true);
        
        // Ticks para o Slider
        sliderGradienteImpurezas.setMajorTickSpacing(40);
        sliderGradienteImpurezas.setMinorTickSpacing(20);
        sliderGradienteImpurezas.setPaintTicks(true);
        
        // Change Listener para o Slider, definido de forma a que ele interaja com a Formatted Text Field
        sliderGradienteImpurezas.addChangeListener( new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider)e.getSource();				// Vai buscar o Slider que disparou o Event
                double valor = (double)source.getValue();				// E recolhe-se o novo valor (ap�s a mudan�a)
                if (!source.getValueIsAdjusting())
                { 					// Se o utilizador acabou de ajustar o valor
                    txtGradienteImpurezas.setValue(new Double(valor/100)); // Actualiza-se o valor na Formatted Text Field
                } else
                {
                    txtGradienteImpurezas.setText(String.valueOf(valor/100));// Se nao, apresenta-se apenas o dito valor
                }
                actualizarLabels();
            }
        });
        
        /** Cria��o do formato de n�mero desejado para os valores introduzidos.
         *  Este formato vai servir de "plano geral" para os valores a serem
         *  considerados, servindo mais tarde para o formatador verificar a
         *  validade dos valores introduzidos.
         */
        NumberFormat gradienteFormat = NumberFormat.getNumberInstance(new Locale("en", "US"));
        gradienteFormat.setMaximumFractionDigits(2);
        gradienteFormat.setMinimumFractionDigits(0);
        
        /** Formatador que vai avaliar os valores introduzidos de acordo com o
         *  formato criado, bem como outros limites impostos (a definir de acordo
         *  com cada caso individual).
         */
        NumberFormatter gradienteFormatador = new NumberFormatter(gradienteFormat);
        gradienteFormatador.setMinimum(new Double(19));		// Valor m�nimo aceite pelo formatador
        gradienteFormatador.setMaximum(new Double(23));		// Valor m�ximo aceite pelo formatador
        
        /** Cria��o da JFormattedTextField que vai fazer uso do formatador.
         *  Esta ir� interagir com o JSlider de forma a permitir a introdu��o
         *  de um valor espec�fico de forma mais simples e directa que o
         *  slider permite, utilizando para controlo de valores o formatador
         *  criado anteriormente.
         */
        txtGradienteImpurezas = new JFormattedTextField(gradienteFormatador);
        txtGradienteImpurezas.setValue(new Double(21));				// Valor inicial da caixa de texto.
        txtGradienteImpurezas.setColumns(5);							// N�mero de colunas da caixa (para ganhar algum espa�o).
        txtGradienteImpurezas.setMaximumSize(txtFieldDim);				// Tamanho m�ximo da caixa.
        
        /** Defini��o do InputMap da Text Field, de forma a que esta
         *  reaja ao pressionar da tecla enter al�m da perda de focus.
         */
        txtGradienteImpurezas.getInputMap().put(KeyStroke.getKeyStroke(
        KeyEvent.VK_ENTER, 0),
        "check");
        
        /** Defini��o do ActionMap da Text Field. Este mapa ir� conter
         *  as ac��es que a Text Field executa para cada input definido
         *  no input map.
         */
        txtGradienteImpurezas.getActionMap().put("check", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {		// ActionEvent (para alguma ac��o executada)
                if (!txtGradienteImpurezas.isEditValid())
                { 			// Se o texto introduzido for inv�lido,
                    Toolkit.getDefaultToolkit().beep();			// avisa-se o utilizador com um beep
                    txtGradienteImpurezas.selectAll();					// e selecciona-se o texto introduzido.
                } else try
                {                    				// Se o texto for v�lido,
                    txtGradienteImpurezas.commitEdit();     			// aprova-se a edi��o do valor da Text Field.
                } catch (java.text.ParseException exc)
                { }
            }
        });
        
        /** Property Change Listener para a Text Field. Este bloco de
         *  c�digo vai conter as instru��es necess�rias para a defini��o
         *  dos valores da simula��o de acordo com o escolhido pelo utilizador.
         */
        txtGradienteImpurezas.addPropertyChangeListener(new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                if ("value".equals(e.getPropertyName()))
                {				// Se a propriedade alterada for a do valor (diferente do texto!)
                    Number value = (Number)e.getNewValue();				// vai-se buscar o novo valor introduzido.
                    if (sliderGradienteImpurezas != null && value != null)
                    {	// Por uma quest�o de seguran�a verifica-se a exist�ncia destes dois objectos
                        sliderGradienteImpurezas.setValue((int)(value.doubleValue()*100));// e altera-se o valor condignamente.
                    }
                }
            }
        });
        
        /** Cria��o do JPanel que vai conter o Slider e a Text Field, al�m
         *  de uma border identificadora do par�metro que vai ser definido
         *  nesses componentes.
         */
        panelGradienteImpurezas = new JPanel();
        title = BorderFactory.createTitledBorder("Gradiente de Impurezas (10^x) (cm^-4)");// Cria��o da border e do t�tulo
        title.setTitleJustification(TitledBorder.CENTER);									// Posicionamento do t�tulo
        title.setTitlePosition(TitledBorder.TOP);
        panelGradienteImpurezas.setBorder(title);				// Defini��o do painel; border, dimens�o, layout e componentes
        panelGradienteImpurezas.setPreferredSize(pDimensao);
        panelGradienteImpurezas.setLayout(new BoxLayout(panelGradienteImpurezas, BoxLayout.X_AXIS));
        panelGradienteImpurezas.add(sliderGradienteImpurezas);
        panelGradienteImpurezas.add(txtGradienteImpurezas);
        
        // Fim da cria��o das combina��es de painel/slider/text field.
        
        // Radio Buttons para a selec��o da l�ngua do painel de defini��es
        portuguesButton = new JRadioButton("Portugu\u00eas");
        englishButton = new JRadioButton("English");
        
        // Grupo de bot�es para os bot�es da l�ngua
        ButtonGroup grupoLinguas = new ButtonGroup();
        grupoLinguas.add(portuguesButton);
        grupoLinguas.add(englishButton);
        
        // M�todos Action Performed para os dois bot�es; chamam ambos o mesmo m�todo por uma quest�o de simplicidade.
        portuguesButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                mudarLingua();
            }
        });
        
        englishButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                mudarLingua();
            }
        });
        
        // Pain�is para os bot�es das l�nguas (para centrar os ditos bot�es)
        panelPortugues = new JPanel();
        panelPortugues.add(portuguesButton, BorderLayout.CENTER);
        
        panelEnglish = new JPanel();
        panelEnglish.add(englishButton, BorderLayout.CENTER);
        
        // Painel para conter os pain�is dos bot�es das l�nguas, com o t�tulo, dimens�o e layout apropriados
        panelLinguas = new JPanel();
        title = BorderFactory.createTitledBorder("L\u00ednguas");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.TOP);
        panelLinguas.setBorder(title);
        panelLinguas.setLayout(new GridLayout(1,0));
        panelLinguas.add(panelPortugues);
        panelLinguas.add(panelEnglish);
        
        // Bot�es para a escolha do material a considerar
        silicioButton = new JRadioButton("Sil\u00edcio");
        gaAsButton = new JRadioButton("Arseneto de G\u00e1lio");
        
        // Grupo para esses bot�es
        ButtonGroup grupoMateriais = new ButtonGroup();
        grupoMateriais.add(silicioButton);
        grupoMateriais.add(gaAsButton);
        
        // Pain�is individuais para centrar os bot�es
        panelSilicio = new JPanel();
        panelSilicio.add(silicioButton, BorderLayout.CENTER);
        
        panelGaAs = new JPanel();
        panelGaAs.add(gaAsButton, BorderLayout.CENTER);
        
        // Painel para conter os pain�is individuais do material, com border, layout e componentes
        panelMaterial = new JPanel();
        title = BorderFactory.createTitledBorder("Material Semi-Condutor");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.TOP);
        panelMaterial.setBorder(title);
        panelMaterial.setLayout(new GridLayout(1,0));
        panelMaterial.add(panelSilicio);
        panelMaterial.add(panelGaAs);
        
        JPanel panelMaterialELinguas = new JPanel();
        panelMaterialELinguas.setLayout(new GridLayout(1, 0));
        panelMaterialELinguas.add(panelMaterial);
        panelMaterialELinguas.add(panelLinguas);
        
        // Bot�es para a escolha do material a considerar
        abruptaButton = new JRadioButton("Abrupta");
        linearButton = new JRadioButton("Linear");
        
        // Grupo para esses bot�es
        ButtonGroup grupoJuncao = new ButtonGroup();
        grupoJuncao.add(abruptaButton);
        grupoJuncao.add(linearButton);
        
        abruptaButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                sliderGradienteImpurezas.setEnabled(linearButton.isSelected());
                txtGradienteImpurezas.setEnabled(linearButton.isSelected());
            }
        });
        
        linearButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                sliderGradienteImpurezas.setEnabled(linearButton.isSelected());
                txtGradienteImpurezas.setEnabled(linearButton.isSelected());
            }
        });
        
        // Pain�is individuais para centrar os bot�es
        panelAbrupta = new JPanel();
        panelAbrupta.add(abruptaButton, BorderLayout.CENTER);
        
        panelLinear = new JPanel();
        panelLinear.add(linearButton, BorderLayout.CENTER);
        
        // Painel para conter os pain�is individuais do material, com border, layout e componentes
        panelJuncao = new JPanel();
        title = BorderFactory.createTitledBorder("Tipo de Jun\u00e7\u00e3o");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.TOP);
        panelJuncao.setBorder(title);
        panelJuncao.setLayout(new GridLayout(1,0));
        panelJuncao.add(panelAbrupta);
        panelJuncao.add(panelLinear);
        
        // Cria��o dos bot�es para correr a simula��o e para limpar os valores
        correrButton = new JButton("Correr");
        limparButton = new JButton("Restaurar");
        sairButton = new JButton("Cancelar");
        
        correrButton.setMaximumSize(buttonDim);
        limparButton.setMaximumSize(buttonDim);
        sairButton.setMaximumSize(buttonDim);
        
        // ActionPerformed para o bot�o de come�o da simula��o
        correrButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                
                int numAmostras = 1;
                
                try
                {
                    numAmostras = Integer.parseInt(txtNumAmostras.getText());
                } catch (NumberFormatException exc)
                {
                    if (txtNumAmostras.getText().equals("1,000"))
                    {
                        numAmostras = 1000;
                    }
                }
                acqConfig.setTotalSamples(numAmostras);
                
                acqConfig.setSelectedFrequency(new Frequency((double)sliderTempoAmostras.getValue(),hardwareInfo.getHardwareFrequencies(0).getMinimumFrequency().getMultiplier(),hardwareInfo.getHardwareFrequencies(0).getMinimumFrequency().getFrequencyDefType()));
                
                acqConfig.getSelectedHardwareParameter("N Dadores INI").setParameterValue("" + Double.parseDouble(txtConcDadores.getText()));
                acqConfig.getSelectedHardwareParameter("N Dadores END").setParameterValue("" + Double.parseDouble(txtConcDadoresFinal.getText()));
                acqConfig.getSelectedHardwareParameter("N Aceitadores INI").setParameterValue("" + Double.parseDouble(txtConcAceitadores.getText()));
                acqConfig.getSelectedHardwareParameter("N Aceitadores END").setParameterValue("" + Double.parseDouble(txtConcAceitadoresFinal.getText()));
                acqConfig.getSelectedHardwareParameter("Temp INI").setParameterValue("" + Double.parseDouble(txtTemperatura.getText()));
                acqConfig.getSelectedHardwareParameter("Temp END").setParameterValue("" + Double.parseDouble(txtTemperaturaFinal.getText()));
                acqConfig.getSelectedHardwareParameter("V ext INI").setParameterValue("" + Double.parseDouble(txtCampoElectrico.getText()));
                acqConfig.getSelectedHardwareParameter("V ext END").setParameterValue("" + Double.parseDouble(txtCampoElectricoFinal.getText()));
                acqConfig.getSelectedHardwareParameter("Imp Gradient").setParameterValue("" + Double.parseDouble(txtGradienteImpurezas.getText()));
                /*
                if(portuguesButton.isSelected()) {
                        motor.setLingua("portugues");
                } else if(englishButton.isSelected()) {
                        motor.setLingua("english");
                }*/
                
                String material = "";
                if(silicioButton.isSelected())
                {
                    material = "Silicio";
                } else if(gaAsButton.isSelected())
                {
                    material = "GaAs";
                }
                
                acqConfig.getSelectedHardwareParameter("Material").setParameterValue(material);
                
                String juncao = "";
                if(abruptaButton.isSelected())
                {
                    juncao = "Abrupta";
                } else if(linearButton.isSelected())
                {
                    juncao = "Linear";
                }
                acqConfig.getSelectedHardwareParameter("Juncao").setParameterValue(juncao);
                
                fireICustomizerListenerDone();
                
            }
        });
        
        // ActionPerformed para o bot�o de limpeza
        limparButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                txtConcDadores.setValue(new Integer(15));
                txtConcDadoresFinal.setValue(new Integer(17));
                txtConcAceitadores.setValue(new Integer(15));
                txtConcAceitadoresFinal.setValue(new Integer(17));
                txtTemperatura.setValue(new Double(300));
                txtTemperaturaFinal.setValue(new Double(400));
                txtCampoElectrico.setValue(new Double(-1));
                txtCampoElectricoFinal.setValue(new Double(1));
                txtNumAmostras.setValue(new Integer(200));
                txtTempoAmostras.setValue(new Integer(200));
                txtGradienteImpurezas.setValue(new Double(21));
                portuguesButton.doClick();
                silicioButton.doClick();
                abruptaButton.doClick();
                concDadoresCheck.setSelected(true);
                concAceitadoresCheck.setSelected(true);
                temperaturaCheck.setSelected(true);
                campoElectricoCheck.setSelected(true);
            }
        });
        
        // ActionPerformed para o bot�o de sa�da
        sairButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                fireICustomizerListenerCanceled();
            }
        });
        
        deltaDadoresLabel = new JLabel("Dadores = 0");
        deltaAceitadoresLabel = new JLabel("Aceitadores = 0");
        deltaTemperaturaLabel = new JLabel("Temperatura = 0");
        deltaPotencialLabel = new JLabel("Potencial = 0");
        
        JPanel panelLabelsEsq = new JPanel();
        panelLabelsEsq.setLayout(new GridLayout(2, 0));
        panelLabelsEsq.add(new JPanel().add(deltaDadoresLabel));
        panelLabelsEsq.add(new JPanel().add(deltaAceitadoresLabel));
        
        JPanel panelLabelsDta = new JPanel();
        panelLabelsDta.setLayout(new GridLayout(2, 0));
        panelLabelsDta.add(new JPanel().add(deltaTemperaturaLabel));;
        panelLabelsDta.add(new JPanel().add(deltaPotencialLabel));
        
        panelLabels = new JPanel();
        title = BorderFactory.createTitledBorder("Taxas de Varia\u00e7\u00e3o Temporal");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.TOP);
        panelLabels.setBorder(title);
        panelLabels.setLayout(new GridLayout(1, 0));
        panelLabels.add(panelLabelsEsq);
        panelLabels.add(panelLabelsDta);
        
        // Cria��o do painel
        esquerda = new JPanel();
        
        // Defini��o da border a utilizar no painel (com o t�tulo correspondente)
        esquerda.setLayout(new GridLayout(0,1));	// Defini��o do layout e adi��o dos componentes ao painel
        esquerda.add(panelMaterialELinguas);
        esquerda.add(panelConcDadores);
        esquerda.add(panelConcAceitadores);
        esquerda.add(panelTemperatura);
        esquerda.add(panelCampoElectrico);
        esquerda.add(panelTempoAmostras);
        esquerda.add(panelGradienteImpurezas);
        
        panelBotoes = new JPanel();
        panelBotoes.setLayout(new GridLayout(1,0));
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
        panelBotoes.add(new JPanel().add(correrButton));
        panelBotoes.add(new JPanel().add(limparButton));
        panelBotoes.add(new JPanel().add(sairButton));
        
        JPanel baixoDireita = new JPanel();
        baixoDireita.setLayout(new GridLayout(1, 0));
        baixoDireita.add(panelJuncao);
        baixoDireita.add(panelBotoes);
        
        // Cria��o do painel
        direita = new JPanel();
        
        // Defini��o da border a utilizar no painel (com o t�tulo correspondente)
        direita.setLayout(new GridLayout(0,1));	// Defini��o do layout e adi��o dos componentes ao painel
        direita.add(panelLabels);
        direita.add(panelConcDadoresFinal);
        direita.add(panelConcAceitadoresFinal);
        direita.add(panelTemperaturaFinal);
        direita.add(panelCampoElectricoFinal);
        direita.add(panelNumAmostras);
        direita.add(baixoDireita);
        
        // Defini��o da border a utilizar no painel (com o t�tulo correspondente)
        title = BorderFactory.createTitledBorder("Defini\u00e7\u00f5es");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitlePosition(TitledBorder.TOP);
        this.setBorder(title);
        // Defini��o do layout e adi��o dos componentes ao painel
        this.setLayout(new GridLayout(1,0));
        this.add(esquerda);
        this.add(direita);
        
        // Escolha dos bot�es que est�o accionados ao princ�pio
        portuguesButton.setSelected(true);
        silicioButton.setSelected(true);
        abruptaButton.doClick();
        concDadoresCheck.setSelected(true);
        concAceitadoresCheck.setSelected(true);
        temperaturaCheck.setSelected(true);
        campoElectricoCheck.setSelected(true);
        limparButton.doClick();
    }
    
    /** M�todo de actualiza��o dos valores das taxas de varia��o por itera��o dos v�rios
     *  par�metros. Actualiza todos os valores por uma quest�o de simplicidade de c�digo.
     */
    private void actualizarLabels()
    {
        double numAmostras = Double.parseDouble(txtNumAmostras.getText());
        
        double deltaDadores = (Math.pow(10, Double.parseDouble(txtConcDadoresFinal.getText())) - Math.pow(10, Double.parseDouble(txtConcDadores.getText())));
        if(concDadoresCheck.isSelected()) deltaDadores /= numAmostras*(Double.parseDouble(txtTempoAmostras.getText())/1000);
        else deltaDadores = 0;
        
        DecimalFormatSymbols formatoDeltas = new DecimalFormatSymbols(new Locale("en", "US"));
        DecimalFormat formatadorDeltas = new DecimalFormat("#0.####E00", formatoDeltas);
        
        double deltaAceitadores = (Math.pow(10, Double.parseDouble(txtConcAceitadoresFinal.getText())) - Math.pow(10, Double.parseDouble(txtConcAceitadores.getText())));
        if(concAceitadoresCheck.isSelected()) deltaAceitadores /= numAmostras*(Double.parseDouble(txtTempoAmostras.getText())/1000);
        else deltaAceitadores = 0;
        
        double deltaTemperatura = (Double.parseDouble(txtTemperaturaFinal.getText()) - Double.parseDouble(txtTemperatura.getText()));
        if(temperaturaCheck.isSelected()) deltaTemperatura /= numAmostras*(Double.parseDouble(txtTempoAmostras.getText())/1000);
        else deltaTemperatura = 0;
        
        double deltaPotencial = (Double.parseDouble(txtCampoElectricoFinal.getText()) - Double.parseDouble(txtCampoElectrico.getText()));
        if(campoElectricoCheck.isSelected()) deltaPotencial /= numAmostras*(Double.parseDouble(txtTempoAmostras.getText())/1000);
        else deltaPotencial = 0;
        
        if(portuguesButton.isSelected())
        {
            deltaDadoresLabel.setText("Dadores = " + formatadorDeltas.format(deltaDadores));
            deltaAceitadoresLabel.setText("Aceitadores = " + formatadorDeltas.format(deltaAceitadores));
            deltaTemperaturaLabel.setText("Temperatura = " + arredondar(deltaTemperatura, 4));
            deltaPotencialLabel.setText("Potencial = " + arredondar(deltaPotencial, 4));
        } else if(englishButton.isSelected())
        {
            deltaDadoresLabel.setText("Donors = " + formatadorDeltas.format(deltaDadores));
            deltaAceitadoresLabel.setText("Acceptors = " + formatadorDeltas.format(deltaAceitadores));
            deltaTemperaturaLabel.setText("Temperature = " + arredondar(deltaTemperatura, 4));
            deltaPotencialLabel.setText("Potential = " + arredondar(deltaPotencial, 4));
        }
    }
    
    /** M�todo para mudar al�ngua em que � apresentado o painel de defini��es. Este m�todo
     *  limita-se a alterar as borders nos v�rios pain�is, dado que seria um desperd�cio de
     *  recursos criar uma nova colec��o de objectos s� para alterar a l�ngua do painel, j�
     *  que o funcionamento do programa se mant�m.
     */
    private void mudarLingua()
    {
        if(portuguesButton.isSelected())
        {			// Se a l�ngua escolhida for o Portugu�s, muda-se tudo para Portugu�s
            //rframe.setTitle("Painel de Defini��es");
            
            title = (TitledBorder)this.getBorder();
            title.setTitle("Defini\u00e7\u00f5es");
            this.setBorder(title);
            this.repaint();
            
            title = (TitledBorder)panelConcDadores.getBorder();
            title.setTitle("Concentra\u00e7\u00e3o de Dadores Inicial (10^x) (cm^-3)");
            panelConcDadores.setBorder(title);
            panelConcDadores.repaint();
            
            title = (TitledBorder)panelConcAceitadores.getBorder();
            title.setTitle("Concentra\u00e7\u00e3o de Aceitadores Inicial (10^x) (cm^-3)");
            panelConcAceitadores.setBorder(title);
            panelConcAceitadores.repaint();
            
            title = (TitledBorder)panelTemperatura.getBorder();
            title.setTitle("Temperatura Inicial (K)");
            panelTemperatura.setBorder(title);
            panelTemperatura.repaint();
            
            title = (TitledBorder)panelTempoAmostras.getBorder();
            title.setTitle("Tempo entre Amostras (ms)");
            panelTempoAmostras.setBorder(title);
            panelTempoAmostras.repaint();
            
            title = (TitledBorder)panelNumAmostras.getBorder();
            title.setTitle("N\u00famero de Amostras");
            panelNumAmostras.setBorder(title);
            panelNumAmostras.repaint();
            
            title = (TitledBorder)panelCampoElectrico.getBorder();
            title.setTitle("Potencial Externo Inicial (V)");
            panelCampoElectrico.setBorder(title);
            panelCampoElectrico.repaint();
            
            title = (TitledBorder)panelLinguas.getBorder();
            title.setTitle("L\u00ednguas");
            panelLinguas.setBorder(title);
            panelLinguas.repaint();
            
            title = (TitledBorder)panelMaterial.getBorder();
            title.setTitle("Material Semi-Condutor");
            panelMaterial.setBorder(title);
            panelMaterial.repaint();
            
            silicioButton.setText("Sil\u00edcio");
            gaAsButton.setText("Arseneto de G\u00e1lio");
            
            title = (TitledBorder)panelJuncao.getBorder();
            title.setTitle("Tipo de Jun\u00e7\u00e3o");
            panelJuncao.setBorder(title);
            panelJuncao.repaint();
            
            abruptaButton.setText("Abrupta");
            
            title = (TitledBorder)panelConcDadoresFinal.getBorder();
            title.setTitle("Concentra\u00e7\u00e3o de Dadores Final (10^x) (cm^-3)");
            panelConcDadoresFinal.setBorder(title);
            panelConcDadoresFinal.repaint();
            
            title = (TitledBorder)panelConcAceitadoresFinal.getBorder();
            title.setTitle("Concentra\u00e7\u00e3o de Aceitadores Final (10^x) (cm^-3)");
            panelConcAceitadoresFinal.setBorder(title);
            panelConcAceitadoresFinal.repaint();
            
            title = (TitledBorder)panelTemperaturaFinal.getBorder();
            title.setTitle("Temperatura Final (K)");
            panelTemperaturaFinal.setBorder(title);
            panelTemperaturaFinal.repaint();
            
            title = (TitledBorder)panelCampoElectricoFinal.getBorder();
            title.setTitle("Potencial Externo Final (V)");
            panelCampoElectricoFinal.setBorder(title);
            panelCampoElectricoFinal.repaint();
            
            correrButton.setText("Correr");
            limparButton.setText("Restaurar");
            sairButton.setText("Cancelar");
            
            title = (TitledBorder)panelLabels.getBorder();
            title.setTitle("Taxas de Varia\u00e7\u00e3o Temporal");
            panelLabels.setBorder(title);
            panelLabels.repaint();
            
            title = (TitledBorder)panelGradienteImpurezas.getBorder();
            title.setTitle("Gradiente de Impurezas (10^x) (cm^-4)");
            panelGradienteImpurezas.setBorder(title);
            panelGradienteImpurezas.repaint();
        } else
        {									// Se a l�ngua for outra - Ingl�s, exclusivamente - muda-se apropriadamente
            //frame.setTitle("Settings Panel");
            
            title = (TitledBorder)this.getBorder();
            title.setTitle("Settings");
            this.setBorder(title);
            this.repaint();
            
            title = (TitledBorder)panelConcDadores.getBorder();
            title.setTitle("Initial Donor Concentration (10^x) (cm^-3)");
            panelConcDadores.setBorder(title);
            panelConcDadores.repaint();
            
            title = (TitledBorder)panelConcAceitadores.getBorder();
            title.setTitle("Inicial Acceptor Concentration (10^x) (cm^-3)");
            panelConcAceitadores.setBorder(title);
            panelConcAceitadores.repaint();
            
            title = (TitledBorder)panelTemperatura.getBorder();
            title.setTitle("Initial Temperature (K)");
            panelTemperatura.setBorder(title);
            panelTemperatura.repaint();
            
            title = (TitledBorder)panelTempoAmostras.getBorder();
            title.setTitle("Time Between Samples (ms)");
            panelTempoAmostras.setBorder(title);
            panelTempoAmostras.repaint();
            
            title = (TitledBorder)panelNumAmostras.getBorder();
            title.setTitle("Number of Samples");
            panelNumAmostras.setBorder(title);
            panelNumAmostras.repaint();
            
            title = (TitledBorder)panelCampoElectrico.getBorder();
            title.setTitle("Initial External Potential (V)");
            panelCampoElectrico.setBorder(title);
            panelCampoElectrico.repaint();
            
            title = (TitledBorder)panelLinguas.getBorder();
            title.setTitle("Languages");
            panelLinguas.setBorder(title);
            panelLinguas.repaint();
            
            title = (TitledBorder)panelMaterial.getBorder();
            title.setTitle("Semi-Conductor Material");
            panelMaterial.setBorder(title);
            panelMaterial.repaint();
            
            silicioButton.setText("Silicon");
            gaAsButton.setText("Gallium Arsenide");
            
            title = (TitledBorder)panelJuncao.getBorder();
            title.setTitle("Junction Type");
            panelJuncao.setBorder(title);
            panelJuncao.repaint();
            
            abruptaButton.setText("Abrupt");
            
            title = (TitledBorder)panelConcDadoresFinal.getBorder();
            title.setTitle("Final Donor Concentration (10^x) (cm^-3)");
            panelConcDadoresFinal.setBorder(title);
            panelConcDadoresFinal.repaint();
            
            title = (TitledBorder)panelConcAceitadoresFinal.getBorder();
            title.setTitle("Final Acceptor Concentration (10^x) (cm^-3)");
            panelConcAceitadoresFinal.setBorder(title);
            panelConcAceitadoresFinal.repaint();
            
            title = (TitledBorder)panelTemperaturaFinal.getBorder();
            title.setTitle("Final Temperature (K)");
            panelTemperaturaFinal.setBorder(title);
            panelTemperaturaFinal.repaint();
            
            title = (TitledBorder)panelCampoElectricoFinal.getBorder();
            title.setTitle("Final External Potential (V)");
            panelCampoElectricoFinal.setBorder(title);
            panelCampoElectricoFinal.repaint();
            
            correrButton.setText("Run");
            limparButton.setText("Reset");
            sairButton.setText("Cancel");
            
            title = (TitledBorder)panelLabels.getBorder();
            title.setTitle("Temporal Rates");
            panelLabels.setBorder(title);
            panelLabels.repaint();
            
            title = (TitledBorder)panelGradienteImpurezas.getBorder();
            title.setTitle("Impurity Gradient (10^x) (cm^-4)");
            panelGradienteImpurezas.setBorder(title);
            panelGradienteImpurezas.repaint();
        }
        actualizarLabels();
    }
    
    /**REC*/
    /** Utility field used by event firing mechanism. */
    private javax.swing.event.EventListenerList listenerList =  null;
    
    /** Registers ICustomizerListener to receive events.
     * @param listener The listener to register.
     */
    public synchronized void addICustomizerListener(ICustomizerListener listener)
    {
        if (listenerList == null )
        {
            listenerList = new javax.swing.event.EventListenerList();
        }
        listenerList.add(ICustomizerListener.class, listener);
    }
    
    /** Removes ICustomizerListener from the list of listeners.
     * @param listener The listener to remove.
     */
    public synchronized void removeICustomizerListener(ICustomizerListener listener)
    {
        listenerList.remove(ICustomizerListener.class, listener);
    }
    
    /** Notifies all registered listeners about the event.
     *
     * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
     */
    private void fireICustomizerListenerCanceled()
    {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i]==ICustomizerListener.class)
            {
                ((ICustomizerListener)listeners[i+1]).canceled();
            }
        }
    }
    
    /** Notifies all registered listeners about the event.
     *
     * @param param1 ParameterDPendulum.zip #1 of the <CODE>EventObject<CODE> constructor.
     */
    private void fireICustomizerListenerDone()
    {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i]==ICustomizerListener.class)
            {
                
                ((ICustomizerListener)listeners[i+1]).done();
            }
        }
    }
    
    
    private HardwareInfo hardwareInfo=null;
    private HardwareAcquisitionConfig acqConfig=null;
    
    public HardwareAcquisitionConfig getAcquisitionConfig()
    {
        return acqConfig;
    }
    
    public void setHardwareAcquisitionConfig(HardwareAcquisitionConfig acqConfig)
    {
        this.acqConfig=acqConfig;
        /*    if(acqConfig!=null)
            {
                jSliderNumSamples.setValue(acqConfig.getTotalSamples());
                jTextFieldSamples.setText(""+acqConfig.getTotalSamples());
         
                int freq = (int)acqConfig.getSelectedFrequency().getFrequency();
                jSliderTBS.setValue(freq);
                jTextFieldTBS.setText("" + freq);
         
                int a1 = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("P1 ini angle"));
                jSliderAng1.setValue(a1);
                jTextFieldAng1.setText("" + a1);
         
                int a2 = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("P2 ini angle"));
                jSliderAng2.setValue(a2);
                jTextFieldAng2.setText("" + a2);
         
                int l1 = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("P1 length"));
                jSliderL1.setValue(l1);
                jTextFieldL1.setText("" + l1);
         
                int l2 = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("P2 length"));
                jSliderL2.setValue(l2);
                jTextFieldL2.setText("" + l2);
         
                int m1 = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("P1 Mass"));
                jSliderM1.setValue(m1);
                jTextFieldM1.setText("" + m1);
         
                int m2 = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("P2 Mass"));
                jSliderM2.setValue(m2);
                jTextFieldM2.setText("" + m2);
         
                int w1 = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("P1 vel ang ini"));
                jSliderW1.setValue(w1);
                jTextFieldW1.setText("" + w1);
         
                int w2 = Integer.parseInt(acqConfig.getSelectedHardwareParameterValue("P2 vel ang ini"));
                jSliderW2.setValue(w2);
                jTextFieldW2.setText("" + w2);
            }                                */
    }
    
    public void setHardwareInfo(HardwareInfo hardwareInfo)
    {
        this.hardwareInfo=hardwareInfo;
    }
    
    protected HardwareInfo getHardwareInfo()
    {
        return this.hardwareInfo;
    }
    
    public javax.swing.JComponent getCustomizerComponent()
    {
        return this;
    }
    
    public javax.swing.ImageIcon getCustomizerIcon()
    {
        return new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/virtual/client/semiconductor/resources/diode.gif"));
    }
    
    public String getCustomizerTitle()
    {
        return "Semicondutores";
    }
    
    public javax.swing.JMenuBar getMenuBar()
    {
        return null;
    }
    
    /** Arredonda um dado numero com as casas decimais desejadas.
     * O numero e' arredondado 'a x-esima casa.
     * <pre> arredondar(double a, int x); </pre>
     * Devolve um double que e' o double <i>a</i> fornecido arredondado a <i>x</i> casas.
     * @param a numero a arredondar.
     * @param x numero de casas desejadas.
     * @return O numero a arredondado as casas desejadas.
     */
    public static double arredondar(double a, int x)
    {
        return (Math.rint(Math.pow(10, x)*a)/Math.pow(10, x));
    }
    
    /** M�todo main. Este m�todo vai criar uma frame provis�ria para apresentar o painel
     *  e vai-lhe adicionar o dito painel, apenas para uma quest�o de testes.
     */
    public static void main(String args[])
    {
        
        //UIManager.getSystemLookAndFeelClassName()
        //UIManager.getCrossPlatformLookAndFeelClassName()
        
        /*try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch (Exception exc) { }
         
                /*frame = new JFrame("Painel de Defini��es");
                frame.getContentPane().add(new SCustomizer());
                frame.setResizable(false);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.show();*/
        System.out.println(com.linkare.rec.impl.utils.MathUtil.isValueInScale(1, 5, 0.01, 2.3));
    }
}