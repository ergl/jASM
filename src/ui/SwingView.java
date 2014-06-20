package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import commons.watcherPattern.Watchable;
import commons.watcherPattern.Watcher;

/**
 * Vista de la interfaz gráfica Swing.
 * Implementa todos sus subcomponentes como clases internas.
 * Esto es debido a que todas necesitan acceder al atributo SwingController, que sólo pertenece a SwingView
 * <p>
 * Accede directamente al controlador a través de los Listeners.
 * Observa a SwingController y a la CPU. (El método update es llamado desde CPU o SwingController)
 *
 * @author Borja
 * @author Chaymae
 */
public class SwingView implements Watcher {

    /*
     * TODO: Breakpoint mark dissappears when program update happens
     * Change + with another characters when "Skip all breakpoints" is enabled
     */

    public static int timeout = 100;

    private SwingController controller;
    private JFrame mainFrame;
    private Thread runThread = null;

    private ActionPanel actionPanel;
    private ProgramPanel programPanel;
    private StackPanel stackPanel;
    private MemoryPanel memoryPanel;
    private InputPanel inputPanel;
    private OutputPanel outputPanel;
    private StatusPanel statusPanel;

    public SwingView(SwingController _cont) {
        mainFrame = new JFrame();
        actionPanel = new ActionPanel();
        programPanel = new ProgramPanel();
        stackPanel = new StackPanel();
        memoryPanel = new MemoryPanel();
        inputPanel = new InputPanel();
        outputPanel = new OutputPanel();
        statusPanel = new StatusPanel();
        controller = _cont;

        initUI();
    }

    private void initUI() {

        JPanel subPanel = mainSubPanel();

        mainFrame.setTitle("Máquina Virtual de TP");
        mainFrame.setSize(1000, 700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());

        mainFrame.add(this.actionPanel, BorderLayout.PAGE_START);
        mainFrame.add(this.programPanel, BorderLayout.LINE_START);
        mainFrame.add(subPanel, BorderLayout.CENTER);
        mainFrame.add(statusPanel, BorderLayout.PAGE_END);

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private JPanel mainSubPanel() {

        JPanel mainPanel = new JPanel();
        JPanel topSubPanel = topSubPanel();
        JPanel bottomPanel = new JPanel();

        mainPanel.setLayout(new BorderLayout());
        bottomPanel.setLayout(new GridLayout(2, 1));

        bottomPanel.add(this.inputPanel);
        bottomPanel.add(this.outputPanel);

        mainPanel.add(topSubPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);

        return mainPanel;
    }

    private JPanel topSubPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        panel.add(this.stackPanel);
        panel.add(this.memoryPanel);

        return panel;
    }

    /*
     * Debido a que necesitamos un objeto View completo antes de empezar la aplicación,
     * llamamos a init() del controlador después de haber acabo todas las tareas del constructor.
     */
    public void enable() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            controller.addStrWatcher(inputPanel, outputPanel);
            controller.addCpuWatchers(programPanel, stackPanel, memoryPanel);

            controller.init(SwingView.this);
            mainFrame.pack();
            mainFrame.setVisible(true);
        });
    }

    void init(char[] inFileContents, String[] programContents) {
        programPanel.addProgram(programContents);
        inputPanel.displayFile(inFileContents);
    }

    private void show(final String message) {
        javax.swing.SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(mainFrame, message));
    }

    void disableActions() {
        SwingView.this.actionPanel.disableActions();
        SwingView.this.stackPanel.disableActions();
        SwingView.this.memoryPanel.disableActions();
        SwingView.this.statusPanel.showHaltedCPU();
    }

    void quit() {
        if (JOptionPane.showConfirmDialog(null, "¿Estás seguro de querer salir?", "Salir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (runThread != null && runThread.isAlive()) {
                runThread.interrupt();
            }
            controller.shutdown();
            System.exit(0);
        }
    }

    private void showHaltedCPU() {
        if (!controller.ready()) {
            this.statusPanel.showHaltedCPU();
        }
    }

    private void hideHaltedCPU() {
        this.statusPanel.hideHaltedCPU();
    }

    private void updateStatusBar(int nextInst) {
        this.statusPanel.updateExecutedInstructions();
    }

    private void checkMemoryBox() {
        this.statusPanel.checkMemoryBox();
    }

    private void uncheckMemoryBox() {
        this.statusPanel.uncheckMemoryBox();
    }

    private void checkStackBox() {
        this.statusPanel.checkStackBox();
    }

    private void uncheckStackBox() {
        this.statusPanel.uncheckStackBox();
    }

    @Override
    public void updateDisplays(Watchable o, Object arg) {
        show((String) arg);

        if (runThread != null && runThread.isAlive()) {
            runThread.interrupt();
        }
    }

    /**
     * Panel de acciones de la interfaz.
     * Contiene los botones principales de la aplicación: Step, Run y Quit.
     *
     * @author Borja
     * @author Chaymae
     */
    private class ActionPanel extends JPanel {

        private JButton stepButton, runButton, pauseButton, quitButton, resetButton;

        private URL stepIconURL, runIconURL, pauseIconURL, quitIconURL, resetIconURL;

        private ActionPanel() {
            initUI();
        }

        private void initUI() {
            JPanel actionPanel = new JPanel(new GridLayout(1,5));

            stepIconURL = SwingView.class.getResource("res/step.png");
            runIconURL = SwingView.class.getResource("res/run.png");
            pauseIconURL = SwingView.class.getResource("res/pause.png");
            quitIconURL = SwingView.class.getResource("res/exit.png");
            resetIconURL = SwingView.class.getResource("res/reset.png");

            stepButton = (stepIconURL != null) ? new JButton("Step", new ImageIcon(stepIconURL)) : new JButton("STEP");
            runButton = (runIconURL != null) ? new JButton("Run", new ImageIcon(runIconURL)) : new JButton("RUN");
            pauseButton = (pauseIconURL != null) ? new JButton("Pause", new ImageIcon(pauseIconURL)) : new JButton("PAUSE");
            quitButton = (quitIconURL != null) ? new JButton("Exit", new ImageIcon(quitIconURL)) : new JButton("EXIT");
            resetButton = (resetIconURL != null)  ?  new JButton("Reset", new ImageIcon(resetIconURL)) :  new JButton("RESET");

            resetButton = new JButton("RESET");

            pauseButton.setEnabled(false);

            stepButton.addActionListener(e -> {
                SwingView.this.hideHaltedCPU();
                SwingView.this.uncheckStackBox();
                SwingView.this.uncheckMemoryBox();

                controller.stepEvent();
            });

            runButton.addActionListener(e -> {
                pauseButton.setEnabled(true);
                runThread = new Thread() {

                    public void run() {

                        SwingUtilities.invokeLater(() -> stepButton.setEnabled(false));
                        SwingView.this.stackPanel.disableActions();
                        SwingView.this.memoryPanel.disableActions();


                        while (!Thread.currentThread().isInterrupted() && controller.ready()) {

                            SwingView.this.hideHaltedCPU();
                            SwingView.this.uncheckStackBox();
                            SwingView.this.uncheckMemoryBox();

                            controller.stepEvent();

                            if (Thread.currentThread().isInterrupted()) {
                                SwingUtilities.invokeLater(() -> stepButton.setEnabled(true));

                                SwingView.this.stackPanel.enableActions();
                                SwingView.this.memoryPanel.enableActions();
                            }

                            try {
                                Thread.sleep(timeout);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }

                        if (!controller.ready()) {
                            SwingView.this.disableActions();
                        }
                    }
                };

                runThread.start();
            });

            pauseButton.addActionListener(e -> {
                SwingView.this.showHaltedCPU();

                if (runThread != null && runThread.isAlive()) {
                    runThread.interrupt();
                }

                SwingUtilities.invokeLater(() -> {
                    stepButton.setEnabled(true);
                    pauseButton.setEnabled(false);
                });
                SwingView.this.stackPanel.enableActions();
                SwingView.this.memoryPanel.enableActions();

            });

            quitButton.addActionListener(e -> controller.quitEvent());

            resetButton.addActionListener(e -> {
                stepButton.setEnabled(true);
                runButton.setEnabled(true);
                resetButton.setEnabled(false);

                SwingView.this.hideHaltedCPU();
                SwingView.this.inputPanel.flushContent();
                SwingView.this.outputPanel.flushContent();
                SwingView.this.statusPanel.resetExecutedInstructions();

                controller.reset();
            });

            resetButton.setEnabled(false);

            actionPanel.add(stepButton);
            actionPanel.add(runButton);
            actionPanel.add(pauseButton);
            actionPanel.add(quitButton);
            actionPanel.add(resetButton);

            setBorder(new TitledBorder("Actions"));
            add(actionPanel, BorderLayout.CENTER);
        }

        private void disableActions() {
            javax.swing.SwingUtilities.invokeLater(() -> {
                stepButton.setEnabled(false);
                pauseButton.setEnabled(false);
                runButton.setEnabled(false);
            });
        }
    }

    /**
     * Panel de programa.
     * Representa el programa en ejecución, con un indicador de la instrucción a ejecutar en la siguiente acción.
     *
     * @author Borja
     * @author Chaymae
     */
    private class ProgramPanel extends JPanel implements Watcher {

        private JList<String> programList;
        private DefaultListModel<String> programListModel;
        private DefaultListCellRenderer programListRender;
        private JScrollPane programListScroll;

        private boolean breakpointSet;
        private JRadioButton breakpointButton;

        private String[] program;

        private ProgramPanel() {
            initUI();
        }

        private void initUI() {
            setBorder(new TitledBorder("Program"));
            setLayout(new BorderLayout());

            programList = new JList<String>();
            programListModel = new DefaultListModel<String>();
            programListRender = new DefaultListCellRenderer();

            //programListRender.setHorizontalAlignment(JLabel.CENTER);

            programList.setModel(programListModel);
            programList.setCellRenderer(programListRender);

            programList.addMouseListener(selectBreakpoint());

            programListScroll = new JScrollPane(programList);
            programListScroll.setPreferredSize(new Dimension(150, 250));

            breakpointButton = new JRadioButton("Skip All Breakpoints");

            breakpointButton.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    controller.enableBreakpoints();
                } else if (e.getStateChange() == ItemEvent.SELECTED) {
                    controller.disableBreakpoints();
                }
            });

            breakpointSet = false;

            JPanel programDisplayPanel = new JPanel();
            programDisplayPanel.add(programListScroll);

            add(programDisplayPanel, BorderLayout.PAGE_START);
            add(breakpointButton, BorderLayout.PAGE_END);
        }

        private void addProgram(String[] text) {
            this.program = text;
            for(String inst : program)
                programListModel.addElement(inst);
            this.updateProgramDisplay(0);
        }

        private MouseAdapter selectBreakpoint() {
            return new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    int index;
                    @SuppressWarnings("unchecked")
                    JList<String> list = (JList<String>)evt.getSource();
                    if (evt.getClickCount() == 2) {
                        index = list.locationToIndex(evt.getPoint());
                        if (!breakpointSet) {
                            controller.addBreakpointAt(index);
                            showBreakpoint(index);
                            breakpointSet = true;
                        } else {
                            controller.deleteBreakpointAt(index);
                            hideBreakpoint(index);
                            breakpointSet = false;
                        }
                    }
                }
            };
        }

        private void showBreakpoint(final int breakpointInst) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                for (int i = 0; i < program.length; i++) {
                    if (i == breakpointInst)
                        programListModel.set(i, "+" + programListModel.getElementAt(i));
                }
            });
        }

        private void hideBreakpoint(final int breakpointInst) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                for (int i = 0; i < program.length; i++) {
                    if (i == breakpointInst)
                        programListModel.set(i, program[i]);
                }
            });
        }

        @Override
        public void updateDisplays(Watchable o, Object arg) {
            if (arg != null) {
                this.updateProgramDisplay((int) arg);
            }
        }

        private void updateProgramDisplay(final int nextInst) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                for (int i = 0; i < program.length; i++) {
                    if (i == nextInst) {
                        programListModel.set(i, "*" + programListModel.getElementAt(i));
                    } else {
                        programListModel.set(i, program[i]);
                    }
                }
                SwingView.this.updateStatusBar(nextInst);
            });
        }
    }

    /**
     * Panel de la pila.
     * Representa la pila en el momento presente de ejecución.
     * Permite ejecutar las instrucciones debug Push y Pop.
     * Se actualiza automáticamente con cada acción sobre ella.
     *
     * @author Borja
     * @author Chaymae
     */
    private class StackPanel extends JPanel implements Watcher {

        private JTextArea stackTextArea;
        private JScrollPane stackScrollable;
        private JLabel pushValueLabel;
        private JTextField pushValueField;
        private JButton pushButton, popButton;

        private StackPanel() {
            initUI();
        }

        private void initUI() {
            setBorder(new TitledBorder("Operand Stack"));
            setLayout(new BorderLayout());

            JPanel inputSubPanel = buttonSubPanel();

            stackTextArea = new JTextArea();
            stackTextArea.setEditable(false);

            stackScrollable = new JScrollPane(stackTextArea);
            stackScrollable.setPreferredSize(new Dimension(200, 185));

            add(stackScrollable, BorderLayout.PAGE_START);
            add(inputSubPanel, BorderLayout.PAGE_END);
        }

        private JPanel buttonSubPanel() {

            JPanel mainPanel = new JPanel(new BorderLayout());
            JPanel topRowPanel = new JPanel();
            JPanel bottomRowPanel = new JPanel();

            pushValueLabel = new JLabel();
            pushValueLabel.setText("Valor: ");
            topRowPanel.add(pushValueLabel);

            pushValueField = new JTextField();
            pushValueField.setPreferredSize(new Dimension(60, 20));
            topRowPanel.add(pushValueField);

            pushButton = new JButton();
            pushButton.setText("Push");
            pushButton.addActionListener(e -> {
                SwingView.this.uncheckMemoryBox();
                SwingView.this.uncheckStackBox();
                controller.pushEvent(pushValueField.getText().trim());
                pushValueField.setText(null);
            });

            topRowPanel.add(pushButton);

            popButton = new JButton();
            popButton.setText("Pop");
            popButton.addActionListener(e -> {
                SwingView.this.uncheckMemoryBox();
                SwingView.this.uncheckStackBox();
                controller.popEvent();
            });

            bottomRowPanel.add(popButton);

            mainPanel.add(topRowPanel, BorderLayout.CENTER);
            mainPanel.add(bottomRowPanel, BorderLayout.PAGE_END);

            return mainPanel;
        }

        @Override
        public void updateDisplays(Watchable o, Object arg) {
            this.updateStackDisplay((String) arg);
            SwingView.this.checkStackBox();
            if (((String)arg).equalsIgnoreCase("")) {
                SwingView.this.uncheckStackBox();
            }

        }

        private void updateStackDisplay(final String stackContents) {
            javax.swing.SwingUtilities.invokeLater(() -> stackTextArea.setText(stackContents));
        }

        private void enableActions() {
            javax.swing.SwingUtilities.invokeLater(() -> {
                StackPanel.this.pushValueField.setText(null);
                StackPanel.this.pushValueField.setEditable(true);
                StackPanel.this.popButton.setEnabled(true);
                StackPanel.this.pushButton.setEnabled(true);
            });
        }

        private void disableActions() {
            javax.swing.SwingUtilities.invokeLater(() -> {
                StackPanel.this.pushValueField.setText(null);
                StackPanel.this.pushValueField.setEditable(false);
                StackPanel.this.popButton.setEnabled(false);
                StackPanel.this.pushButton.setEnabled(false);
            });
        }
    }

    /**
     * Panel de la memoria.
     * Representa el estado de la memoria, ordenador por posición.
     * Permite ejecutar la instrucción debug Write.
     * Se actualiza automáticamente con cada acción sobre ella.
     *
     * @author Borja
     * @author Chaymae
     */
    private class MemoryPanel extends JPanel implements Watcher {

        private JTable memoryTable;
        private DefaultTableModel model;

        private JScrollPane memoryScrollable;
        private JLabel positionLabel, valueLabel;

        private JTextField positionField, valueField;

        private JButton writeButton;

        private MemoryPanel() {
            initUI();
        }

        private void initUI() {
            setBorder(new TitledBorder("Memory"));
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(250, 185));

            JPanel inputSubPanel = buttonSubPanel();

            model = new DefaultTableModel(new Object[] {"DIR", "VAL"}, 0);

            memoryTable = new JTable(model);
            memoryTable.setCellSelectionEnabled(false);
            memoryTable.setEnabled(false);

            memoryScrollable = new JScrollPane(memoryTable);
            memoryScrollable.setPreferredSize(new Dimension(375, 185));

            add(memoryScrollable, BorderLayout.PAGE_START);
            add(inputSubPanel, BorderLayout.PAGE_END);
        }

        private JPanel buttonSubPanel() {

            JPanel mainPanel = new JPanel(new BorderLayout());
            JPanel topRowPanel = new JPanel();
            JPanel bottomRowPanel = new JPanel();

            positionLabel = new JLabel();
            positionLabel.setText("Pos: ");
            topRowPanel.add(positionLabel);

            positionField = new JTextField();
            positionField.setPreferredSize(new Dimension(60, 20));
            topRowPanel.add(positionField);

            valueLabel = new JLabel();
            valueLabel.setText("Val: ");
            topRowPanel.add(valueLabel);


            valueField = new JTextField();
            valueField.setPreferredSize(new Dimension(60, 20));
            topRowPanel.add(valueField);

            writeButton = new JButton();
            writeButton.setText("Write");
            writeButton.addActionListener(e -> {
                SwingView.this.uncheckMemoryBox();
                SwingView.this.uncheckStackBox();
                String pos = positionField.getText().trim();
                String val = valueField.getText().trim();

                positionField.setText(null);
                valueField.setText(null);
                controller.writeEvent(pos, val);
            });

            bottomRowPanel.add(writeButton);

            mainPanel.add(topRowPanel, BorderLayout.CENTER);
            mainPanel.add(bottomRowPanel, BorderLayout.PAGE_END);

            return mainPanel;
        }


        @Override
        public void updateDisplays(Watchable o, Object arg) {
            if (((String)arg).equalsIgnoreCase("")) {
                model.setRowCount(0);
                SwingView.this.uncheckMemoryBox();
                return;
            }
            this.updateMemoryTable((String) arg);
            SwingView.this.checkMemoryBox();
        }

        private void updateMemoryTable(final String memoryContents) {
            javax.swing.SwingUtilities.invokeLater(() -> {

                String[] contents = null;

                if (!memoryContents.equals("")) {
                    contents = memoryContents.split(" ");
                }

                if (contents != null) {
                    model.setRowCount(0);
                    for (int i = 0; i < contents.length - 1; i++)
                        if ((i & 1) == 0) {
                            MemoryPanel.this.model.addRow(new Object[] {contents[i], contents[i + 1]});
                        }
                }
            });
        }

        private void enableActions() {
            javax.swing.SwingUtilities.invokeLater(() -> {
                MemoryPanel.this.positionField.setText(null);
                MemoryPanel.this.valueField.setText(null);
                MemoryPanel.this.positionField.setEditable(true);
                MemoryPanel.this.valueField.setEditable(true);
                MemoryPanel.this.writeButton.setEnabled(true);
            });
        }

        private void disableActions() {
            javax.swing.SwingUtilities.invokeLater(() -> {
                MemoryPanel.this.positionField.setText(null);
                MemoryPanel.this.valueField.setText(null);
                MemoryPanel.this.positionField.setEditable(false);
                MemoryPanel.this.valueField.setEditable(false);
                MemoryPanel.this.writeButton.setEnabled(false);
            });
        }
    }

    /**
     * Panel de entrada.
     * Representa, en caso de que haya, el contenido del archivo .IN
     * Además, indica qué caracteres se han leído de él.
     *
     * @author Borja
     * @author Chaymae
     */
    private class InputPanel extends JPanel implements Watcher {

        private JTextArea inputTextArea;
        private JScrollPane inputScrollable;

        private InputPanel() {
            initUI();
        }

        private void initUI() {
            setBorder(new TitledBorder("Program-p Input"));

            inputTextArea = new JTextArea();
            inputTextArea.setLineWrap(true);
            inputTextArea.setEditable(false);

            inputScrollable = new JScrollPane(inputTextArea);
            inputScrollable.setPreferredSize(new Dimension(625, 100));

            add(inputScrollable);
        }

        /* Llamado al inicio de la aplicación. Para cada acción, se llama updayeInputText */
        private void displayFile(final char[] cs) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                if (cs != null) {
                    inputTextArea.setText(String.valueOf(cs));
                }
            });
        }

        private void flushContent() {
            inputTextArea.setText(null);
        }

        @Override
        public void updateDisplays(Watchable o, Object arg) {
            Integer iChar = (int) arg;

            if (iChar != -1) {
                inputPanel.updateInputDisplay(Character.toChars(iChar));
            }
        }

        /* Llamado al ejecutarse una instrucción IN. Busca el carácter parámetro y lo sustituye por '*'
         * Usamos char[] porque no se puede convertir de int a char, sólo a char[] 
         */
        private void updateInputDisplay(final char[] c) {
            javax.swing.SwingUtilities.invokeLater(() -> {

                char[] writtenText = inputTextArea.getText().toCharArray();
                int j = 0;

                while (j < writtenText.length) {
                    if (writtenText[j] == c[0] && writtenText[j] != '\n') {
                        writtenText[j] = '*';
                        break;
                    }
                    j++;
                }

                inputTextArea.setText(String.valueOf(writtenText));
            });
        }
    }

    /**
     * Panel de salida.
     * Representa, en caso de que haya, el contenido del archivo .OUT
     *
     * @author Borja
     * @author Chaymae
     */
    private class OutputPanel extends JPanel implements Watcher {

        private JTextArea outputTextArea;
        private JScrollPane outputScrollable;

        private OutputPanel() {
            initUI();
        }

        private void initUI() {
            setBorder(new TitledBorder("Program-p Output"));

            outputTextArea = new JTextArea();
            outputTextArea.setLineWrap(true);
            outputTextArea.setEditable(false);

            outputScrollable = new JScrollPane(outputTextArea);
            outputScrollable.setPreferredSize(new Dimension(625, 100));

            add(outputScrollable);
        }

        private void flushContent() {
            outputTextArea.setText(null);
        }

        @Override
        public void updateDisplays(Watchable o, Object arg) {
            Character oChar = (char) arg;
            outputPanel.updateOutputDisplay(oChar);
        }

        private void updateOutputDisplay(final Character arg) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                if (arg != null) {
                    OutputPanel.this.outputTextArea.append(String.valueOf(arg));
                }
            });
        }
    }

    private class StatusPanel extends JPanel {

        private JLabel haltedCPULabel, executedInstructionsLabel, executedInstructionsNumberLabel, modifiedMemoryLabel, modifiedStackLabel;

        private int executedInstructions;

        private JCheckBox modifiedMemoryCheckBox, modifiedStackCheckBox;

        private StatusPanel() {
            initUI();
        }

        private void initUI() {
            haltedCPULabel = new JLabel();
            executedInstructionsLabel = new JLabel("Num. Instrucciones ejecutadas:");
            modifiedMemoryLabel = new JLabel("Memoria modificada");
            modifiedStackLabel = new JLabel("Pila modificada");

            haltedCPULabel.setForeground(Color.red);

            executedInstructions = -1;
            executedInstructionsNumberLabel = new JLabel(String.valueOf(executedInstructions));

            modifiedMemoryCheckBox = new JCheckBox();
            modifiedStackCheckBox = new JCheckBox();

            modifiedMemoryCheckBox.setEnabled(false);
            modifiedStackCheckBox.setEnabled(false);

            haltedCPULabel.setFont(haltedCPULabel.getFont().deriveFont(11.0f));
            executedInstructionsLabel.setFont(executedInstructionsLabel.getFont().deriveFont(11.0f));
            modifiedMemoryLabel.setFont(modifiedMemoryLabel.getFont().deriveFont(11.0f));
            modifiedStackLabel.setFont(modifiedStackLabel.getFont().deriveFont(11.0f));

            add(haltedCPULabel);
            add(executedInstructionsLabel);
            add(executedInstructionsNumberLabel);
            add(modifiedMemoryCheckBox);
            add(modifiedMemoryLabel);
            add(modifiedStackCheckBox);
            add(modifiedStackLabel);
        }

        private void showHaltedCPU() {
            javax.swing.SwingUtilities.invokeLater(() -> haltedCPULabel.setText("Máquina Parada"));
        }

        private void hideHaltedCPU() {
            javax.swing.SwingUtilities.invokeLater(() -> haltedCPULabel.setText(null));
        }

        private void updateExecutedInstructions() {
            javax.swing.SwingUtilities.invokeLater(() -> {
                executedInstructions++;
                executedInstructionsNumberLabel.setText(String.valueOf(executedInstructions));
            });
        }

        private void resetExecutedInstructions() {
            javax.swing.SwingUtilities.invokeLater(() -> {
                executedInstructions = -1;
                executedInstructionsNumberLabel.setText(String.valueOf(executedInstructions));
            });
        }

        private void checkMemoryBox() {
            javax.swing.SwingUtilities.invokeLater(() -> modifiedMemoryCheckBox.setSelected(true));
        }

        private void uncheckMemoryBox() {
            javax.swing.SwingUtilities.invokeLater(() -> modifiedMemoryCheckBox.setSelected(false));
        }

        private void checkStackBox() {
            javax.swing.SwingUtilities.invokeLater(() -> modifiedStackCheckBox.setSelected(true));
        }

        private void uncheckStackBox() {
            javax.swing.SwingUtilities.invokeLater(() -> modifiedStackCheckBox.setSelected(false));
        }
    }
}
