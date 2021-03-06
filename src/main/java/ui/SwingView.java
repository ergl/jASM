/*
 * This file is part of jASM.
 *
 * jASM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jASM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jASM.  If not, see <http://www.gnu.org/licenses/>.
 */

package ui;

import commons.watcherPattern.Watchable;
import commons.watcherPattern.Watcher;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

// TODO: Refactor the subcomponents into new classes
public class SwingView implements Watcher {

    /*
     * TODO: Breakpoint mark disappears when program update happens
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

    private RegisterPanel registerPanel;

    public SwingView(SwingController cont) {
        mainFrame = new JFrame();
        actionPanel = new ActionPanel();
        programPanel = new ProgramPanel();
        stackPanel = new StackPanel();
        memoryPanel = new MemoryPanel();
        inputPanel = new InputPanel();
        outputPanel = new OutputPanel();
        statusPanel = new StatusPanel();
        registerPanel =	new RegisterPanel();

        controller =   cont;

        initUI();
    }

    private void initUI() {

    	JPanel topPanel = mainTopPanel();
        JPanel subPanel = mainSubPanel();

        mainFrame.setTitle("jASM");
        mainFrame.setSize(1000, 700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());

        mainFrame.add(topPanel, BorderLayout.PAGE_START);
        mainFrame.add(this.programPanel, BorderLayout.LINE_START);
        mainFrame.add(subPanel, BorderLayout.CENTER);
        mainFrame.add(statusPanel, BorderLayout.PAGE_END);

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private JPanel mainTopPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(actionPanel);
        mainPanel.add(registerPanel);

        return mainPanel;
    }

    private JPanel mainSubPanel() {

        JPanel mainPanel = 	new JPanel();
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

    // Given that we need a complete View object before beginning the application,
    // We call SwingController.init() after completing all constructor instructions
    public void enable() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            controller.addStrWatcher(inputPanel, outputPanel);
            controller.addCpuWatchers(programPanel, stackPanel, memoryPanel, registerPanel);

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
        if(JOptionPane.showConfirmDialog(null, "Really exit?", "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if(runThread != null && runThread.isAlive())
                runThread.interrupt();
            controller.shutdown();
            System.exit(0);
        }
    }

    private void showHaltedCPU() {
        if(!controller.ready())
            this.statusPanel.showHaltedCPU();
    }

    private void hideHaltedCPU() {
        this.statusPanel.hideHaltedCPU();
    }

    private void updateStatusBar() {
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

        if(runThread != null && runThread.isAlive())
            runThread.interrupt();
    }

    /**
     * Menu bar interface
     *
     * @author Borja
     */
    private class ActionPanel extends JPanel {

        private JButton stepButton,
        runButton,
        pauseButton,
        quitButton,
        resetButton;

        private ImageIcon stepIcon, runIcon, pauseIcon, quitIcon, resetIcon;

        private ActionPanel() {
            initUI();
        }

        private void initUI() {
            JPanel actionPanel = new JPanel(new GridLayout(1,5));

            stepIcon = setupImage("next.png");
            runIcon = setupImage("run.png");
            pauseIcon = setupImage("pause.png");
            quitIcon = setupImage("replay.png");
            resetIcon = setupImage("exit.png");

            stepButton = (stepIcon != null) ? new JButton("Step", stepIcon) : new JButton("STEP");
            runButton = (runIcon != null) ? new JButton("Run", runIcon) : new JButton("RUN");
            pauseButton = (pauseIcon != null) ? new JButton("Pause", pauseIcon) : new JButton("PAUSE");
            quitButton = (quitIcon != null) ? new JButton("Exit", quitIcon) : new JButton("EXIT");
            resetButton = (resetIcon != null) ? new JButton("Reset", resetIcon) : new JButton("RESET");

            pauseButton.setEnabled(false);

            stepButton.addActionListener(e -> {
                SwingView.this.hideHaltedCPU();
                SwingView.this.uncheckStackBox();
                SwingView.this.uncheckMemoryBox();

                resetButton.setEnabled(true);
                controller.stepEvent();
            });

            runButton.addActionListener(e -> {
                pauseButton.setEnabled(true);
                runThread = new Thread() {
                    public void run() {

                        SwingUtilities.invokeLater(() -> {
                            stepButton.setEnabled(false);
                            resetButton.setEnabled(true);
                            runButton.setEnabled(false);
                            resetButton.setEnabled(false);
                        });
                        SwingView.this.stackPanel.disableActions();
                        SwingView.this.memoryPanel.disableActions();

                        controller.stepEvent();

                        while(!Thread.currentThread().isInterrupted() && controller.ready()) {

                            SwingView.this.hideHaltedCPU();
                            SwingView.this.uncheckStackBox();
                            SwingView.this.uncheckMemoryBox();

                            controller.stepEvent();

                            if(Thread.currentThread().isInterrupted()) {
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

                        if(!controller.ready())
                            SwingView.this.disableActions();
                    }
                };

                runThread.start();
            });

            pauseButton.addActionListener(e -> {
                SwingView.this.showHaltedCPU();

                if(runThread != null && runThread.isAlive())
                    runThread.interrupt();

                SwingUtilities.invokeLater(() -> {
                    stepButton.setEnabled(true);
                    pauseButton.setEnabled(false);
                    runButton.setEnabled(true);
                    resetButton.setEnabled(true);
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
                SwingView.this.registerPanel.flushContent();
                SwingView.this.statusPanel.resetExecutedInstructions();

                controller.reset();
            });

            resetButton.setEnabled(false);

            actionPanel.add(stepButton);
            actionPanel.add(runButton);
            actionPanel.add(pauseButton);
            actionPanel.add(resetButton);
            actionPanel.add(quitButton);

            setBorder(new TitledBorder("Actions"));
            add(actionPanel, BorderLayout.CENTER);
        }

        private ImageIcon setupImage(String image) {
            InputStream imageStream = this.getClass().getClassLoader().getResourceAsStream("resources/assets/" + image);
            if (imageStream == null) {
                return null;
            }

            try (InputStream is = new BufferedInputStream(imageStream)) {
                Image result = ImageIO.read(is);
                return new ImageIcon(result);
            } catch (IOException e) {
                return null;
            }
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
     * Register structure interface
     */
	private class RegisterPanel extends JPanel implements Watcher {

        private int REGISTER_N = 4;

        private JLabel[] rLabels;
        private JTextField[] rFields;

    	private RegisterPanel() {
            rLabels = new JLabel[REGISTER_N];
            rFields = new JTextField[REGISTER_N];

    		initUI();
    	}

    	private void initUI() {
    		setBorder(new TitledBorder("Registers"));

            for (int i = 0; i < REGISTER_N; i++) {
                rLabels[i] = new JLabel("R" + i);
                rFields[i] = new JTextField();
                rFields[i].setEditable(false);
                rFields[i].setPreferredSize(new Dimension(60, 20));
                add(rLabels[i]);
                add(rFields[i]);
            }
        }

		@Override
		public void updateDisplays(Watchable o, Object arg) {
			String[] tokens = ((String)arg).split("\\s");

			if(tokens.length != 4)
				return;

            for (int i = 0; i < REGISTER_N; i++)
                rFields[i].setText(tokens[i]);
        }

        private void flushContent() {
            for (JTextField jt : rFields)
                jt.setText(null);
        }

    }

    /**
     * Program visualizer panel
     */
    private class ProgramPanel extends JPanel implements Watcher {

        private char PROGRAM_MARKER = '»';
        private char BREAKPOINT_MARKER = '¬';
        private JTable programTable;
        private DefaultTableModel programTableModel;

        private JScrollPane programListScroll;
        private String[] program;

        private boolean breakpointSet;
        private JRadioButton toggleBreakpointsButton;

        private ProgramPanel() {
            breakpointSet = false;
            initUI();
        }

        private void initUI() {
            setBorder(new TitledBorder("Program"));
            setLayout(new BorderLayout());

            toggleBreakpointsButton = new JRadioButton("Skip all breakpoints");
            toggleBreakpointsButton.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    // foo
                } else {
                    // bar
                }
            });

            programTableModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            programTableModel.setColumnIdentifiers(new Object[] {"Current", "Instruction"});
            programTable = new JTable(programTableModel);
            programTable.getColumnModel().getColumn(1).setPreferredWidth(300);

            programTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        toggleBreakpoint(programTable.getSelectedRow());
                    }
                }
            });

            programListScroll = new JScrollPane(programTable);
            programListScroll.setPreferredSize(new Dimension(150, 250));

            JPanel programDisplayPanel = new JPanel();
            programDisplayPanel.add(programListScroll);

            add(programDisplayPanel, BorderLayout.PAGE_START);
            add(toggleBreakpointsButton, BorderLayout.PAGE_END);
        }

        private void toggleBreakpoint(final int breakpointPosition) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                for (int i = 0; i < program.length; i++) {
                    if (i == breakpointPosition) {
                        programTableModel.setValueAt(((breakpointSet) ? null : BREAKPOINT_MARKER), i, 0);
                        breakpointSet = !breakpointSet;
                    }
                }
            });
        }

        private void addProgram(String[] text) {
            this.program = text;

            for (String inst : program)
                this.programTableModel.addRow(new Object[] {null, inst});

            this.updateProgramDisplay(0);
        }

        @Override
        public void updateDisplays(Watchable o, Object arg) {
            if(arg != null)
                this.updateProgramDisplay((Integer)arg);
        }

        private void updateProgramDisplay(final int nextInst) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                for (int i = 0; i < program.length; ++i) {
                    this.programTableModel.setValueAt(((i != nextInst) ? null : PROGRAM_MARKER), i, 0);
                }
                SwingView.this.updateStatusBar();
            });
        }
    }

    /**
     * Stack visualizer panel
     */
    private class StackPanel extends JPanel implements Watcher {

        private JTextArea stackTextArea;
        private JScrollPane stackScrollable;
        private JLabel pushValueLabel;
        private JTextField pushValueField;
        private JButton pushButton,
                        popButton;

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

            JPanel mainPanel= new JPanel(new BorderLayout());
            JPanel topRowPanel = new JPanel();
            JPanel bottomRowPanel = new JPanel();

            pushValueLabel = new JLabel();
            pushValueLabel.setText("Value: ");
            topRowPanel.add(pushValueLabel);

            pushValueField = new JTextField();
            pushValueField.setPreferredSize(new Dimension(60,20));
            pushValueField.addActionListener(pushAction());
            topRowPanel.add(pushValueField);

            pushButton = new JButton();
            pushButton.setText("Push");
            pushButton.addActionListener(pushAction());

            topRowPanel.add(pushButton);

            popButton =  new JButton();
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
            this.updateStackDisplay((String)arg);
            SwingView.this.checkStackBox();
            if(((String)arg).equalsIgnoreCase("")) {
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

        private ActionListener pushAction() {
            return e -> {
                SwingView.this.uncheckMemoryBox();
                SwingView.this.uncheckStackBox();
                controller.pushEvent(pushValueField.getText().trim());
                pushValueField.setText(null);
            };
        }
    }

    /**
     * Memory visualizer panel
     */
    private class MemoryPanel extends JPanel implements Watcher {

        private JTable memoryTable;
        private DefaultTableModel model;

        private JScrollPane memoryScrollable;
        private JLabel positionLabel,
        valueLabel;

        private JTextField positionField,
        valueField;

        private JButton writeButton;

        private MemoryPanel() {
            initUI();
        }

        private void initUI() {
            setBorder(new TitledBorder("Memory"));
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(250,185));

            JPanel inputSubPanel = buttonSubPanel();

            model = new DefaultTableModel(new Object[]{"ADD", "VAL"}, 0);

            memoryTable = new JTable(model);
            memoryTable.setCellSelectionEnabled(false);
            memoryTable.setEnabled(false);

            memoryScrollable = new JScrollPane(memoryTable);
            memoryScrollable.setPreferredSize(new Dimension(375,185));

            add(memoryScrollable, BorderLayout.PAGE_START);
            add(inputSubPanel, BorderLayout.PAGE_END);
        }

        private JPanel buttonSubPanel() {

            JPanel mainPanel = 	new JPanel(new BorderLayout());
            JPanel topRowPanel = new JPanel();
            JPanel bottomRowPanel = new JPanel();

            positionLabel =  new JLabel();
            positionLabel.setText("Pos: ");
            topRowPanel.add(positionLabel);

            positionField = new JTextField();
            positionField.setPreferredSize(new Dimension(60, 20));
            positionField.addActionListener(submitAction());
            topRowPanel.add(positionField);

            valueLabel = new JLabel();
            valueLabel.setText("Val: ");
            topRowPanel.add(valueLabel);

            valueField = new JTextField();
            valueField.setPreferredSize(new Dimension(60, 20));
            valueField.addActionListener(submitAction());
            topRowPanel.add(valueField);

            writeButton = new JButton();
            writeButton.setText("Write");
            writeButton.addActionListener(submitAction());

            bottomRowPanel.add(writeButton);

            mainPanel.add(topRowPanel, BorderLayout.CENTER);
            mainPanel.add(bottomRowPanel, BorderLayout.PAGE_END);

            return mainPanel;
        }

        @Override
        public void updateDisplays(Watchable o, Object arg) {
            if(((String)arg).equalsIgnoreCase("")) {
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

                if(!memoryContents.equals(""))
                    contents = memoryContents.split(" ");

                if(contents != null) {
                    model.setRowCount(0);
                    for(int i = 0; i < contents.length - 1; i++)
                        if( (i&1) == 0)
                            MemoryPanel.this.model.addRow(new Object[]{contents[i], contents[i+1]});
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

        private ActionListener submitAction() {
            return e -> {
                SwingView.this.uncheckMemoryBox();
                SwingView.this.uncheckStackBox();
                String pos = positionField.getText().trim(),
                        val = valueField.getText().trim();

                positionField.setText(null);
                valueField.setText(null);
                controller.writeEvent(pos, val);
            };
        }
    }

    /**
     * Input panel
     * Displays, if existing, the content of the input file
     * It also displays if any character has been read
     *
     * @author Borja
     */
    private class InputPanel extends JPanel implements Watcher {

        private JTextArea inputTextArea;
        private JScrollPane inputScrollable;

        private InputPanel() {
            initUI();
        }

        private void initUI() {
            setBorder(new TitledBorder("Program Input"));

            inputTextArea = new JTextArea();
            inputTextArea.setLineWrap(true); 
            inputTextArea.setEditable(false);

            inputScrollable = new JScrollPane(inputTextArea);
            inputScrollable.setPreferredSize(new Dimension(625,100));	

            add(inputScrollable);
        }

        // Called when program starts. For each action, we call updateInputText
        private void displayFile(final char[] cs) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                if (cs != null)
                    inputTextArea.setText(String.valueOf(cs));
            });
        }

        private void flushContent() {
            inputTextArea.setText(null);
        }

        @Override
        public void updateDisplays(Watchable o, Object arg) {
            Integer iChar = (Integer) arg;

            if(iChar != null && iChar != -1)
                inputPanel.updateInputDisplay(Character.toChars(iChar));
        }

        // Called whenever an IN instruction is executed. Searches for first non-* character that matches
        // the IN parameter and changes it to *
        // We use char[] because we can't cast int -> char, only int -> char[]
        private void updateInputDisplay(final char[] c) {
            javax.swing.SwingUtilities.invokeLater(() -> {

                char[] writtenText = inputTextArea.getText().toCharArray();
                int j = 0;

                while( j < writtenText.length ) {
                    if(writtenText[j] == c[0] && writtenText[j] != '\n') {
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
     * Output panel
     * Displays the content of the output file
     *
     * @author Borja
     */
    private class OutputPanel extends JPanel implements Watcher {

        private JTextArea outputTextArea;
        private JScrollPane outputScrollable;

        private OutputPanel() {
            initUI();
        }

        private void initUI() {
            setBorder(new TitledBorder("Program Output"));

            outputTextArea = new JTextArea();
            outputTextArea.setLineWrap(true); 
            outputTextArea.setEditable(false);

            outputScrollable = new JScrollPane(outputTextArea);
            outputScrollable.setPreferredSize(new Dimension(625,100));	

            add(outputScrollable);
        }

        private void flushContent() {
            outputTextArea.setText(null);
        }

        @Override
        public void updateDisplays(Watchable o, Object arg) {
            Character oChar = (Character) arg;

            if(oChar != null)
                outputPanel.updateOutputDisplay(oChar);
        }

        private void updateOutputDisplay(final Character arg) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                if(arg != null)
                    OutputPanel.this.outputTextArea.append(String.valueOf(arg));
            });
        }
    }

    /**
     * Status panel
     * Displays how many operations have been executed, and if either the memory, stack, or register have been
     * modified
     */
    private class StatusPanel extends JPanel {

        private JLabel  haltedCPULabel,
                        executedInstructionsLabel,
                        executedInstructionsNumberLabel,
                        modifiedMemoryLabel,
                        modifiedStackLabel;

        private int executedInstructions;

        private JCheckBox modifiedMemoryCheckBox,
                            modifiedStackCheckBox;

        private StatusPanel() {
            initUI();
        }

        private void initUI() {
            haltedCPULabel = new JLabel();
            executedInstructionsLabel = new JLabel("Executed instructions:");
            modifiedMemoryLabel = new JLabel("Changed memory");
            modifiedStackLabel = new JLabel("Changed stack");
            // TODO: Change register list?

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
            javax.swing.SwingUtilities.invokeLater(() -> haltedCPULabel.setText("VM off"));
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
