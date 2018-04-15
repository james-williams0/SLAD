package slad;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Window extends JFrame {

    private class Canvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
        }
    }

    private class ADTActionListener implements ActionListener {
        int integerValue;
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == pushButton) {
                JOptionPane pushPane = new JOptionPane();
                String value;
                boolean validInput = false;
                value = (String) (pushPane.showInputDialog(appWindow, "Type value to be pushed: ", "Input", pushPane.PLAIN_MESSAGE, null, null, null));
                try {
                    integerValue = Integer.parseInt(value);
                    messageTextField.append("Pushed: " + value + "\n");
                    validInput = true;
                } catch (NumberFormatException exception) {
                    messageTextField.append(exception + "\n");
                    messageTextField.append("Your number contains values that are not numerical digits, \nor no values at all, please attempt to input your value again.");
                }
                if (validInput) {
                    drawableStack.push(integerValue);
                }
                onStack = true;
                canvas.repaint();
            } else if(e.getSource() == popButton) {
                try {
                    messageTextField.append("Popped: " + Integer.toString(drawableStack.pop()) + "\n");
                    onStack = true;
                    canvas.repaint();
                } catch(OutOfBoundsException exception) {
                    messageTextField.append(exception + "\n");
                    messageTextField.append("Your stack is empty, there are no more values to pop.\n");
                }
            } else if(e.getSource() == enqueueButton) {
                JOptionPane enqueuePane = new JOptionPane();
                String value;
                boolean validInput = false;
                value = (String) (enqueuePane.showInputDialog(appWindow, "Type value to be queued: ", "Input", enqueuePane.PLAIN_MESSAGE, null, null, null));
                try {
                    integerValue = Integer.parseInt(value);
                    messageTextField.append("Enqueued: " + value + "\n");
                    validInput = true;
                } catch (NumberFormatException exception) {
                    messageTextField.append(exception + "\n");
                    messageTextField.append("Your number contains values that are not numerical digits, \nor no values at all, please attempt to input your value again.");
                }
                if (validInput) {
                    drawableQueue.enQueue(integerValue);
                }
                onQueue = true;
                canvas.repaint();
            } else if(e.getSource() == dequeueButton) {
                try {
                    messageTextField.append("Dequeued: " + Integer.toString(drawableQueue.deQueue()) + "\n");
                    onQueue = true;
                    canvas.repaint();
                } catch(OutOfBoundsException exception) {
                    messageTextField.append(exception + "\n");
                    messageTextField.append("Your queue is empty, there are no more values to pop.\n");
                }
            } else if(e.getSource() == peekButton) {
                try {
                    if (!queueStackSelector.isSelected()) {
                        messageTextField.append("Current head value: " + Integer.toString(drawableStack.head.getVal()) + "\n");
                    } else {
                        messageTextField.append("Current front value: " + Integer.toString(drawableQueue.front.getVal()) + "\n");
                    }
                } catch(NullPointerException exception) {
                    messageTextField.append(exception + "\n");
                    messageTextField.append("Your stack is empty, there are no values to peek at.");
                }
            } else if(e.getSource() == sizeReportButton) {
                try {
                    if (!queueStackSelector.isSelected()) {
                        messageTextField.append("Current stack size: " + Integer.toString(drawableStack.size()) + "\n");
                    } else {
                        messageTextField.append("Current queue size: " + Integer.toString(drawableQueue.size()) + "\n");
                    }
                } catch(NullPointerException exception) {
                    messageTextField.append(exception + "\n");
                    messageTextField.append("Your queue is empty, size is 0.\n");
                }
            } else if(e.getSource() == emptyButton) {
                if(!queueStackSelector.isSelected()) {
                    emptyStack();
                    onStack = true;
                    canvas.repaint();
                    messageTextField.append("Stack emptied\n");
                } else {
                    emptyQueue();
                    onQueue = true;
                    canvas.repaint();
                    messageTextField.append("Queue emptied\n");
                }
            } else if(e.getSource() == reverseStackButton) {
                int currentSize = drawableStack.size();
                String reversedStack = "";
                for(int i = 0; i < currentSize; i++) {
                    reversedStack = reversedStack + " " + drawableStack.pop() + ", ";
                }
                messageTextField.append("Stack: " + reversedStack + "\n");
                onStack = true;
                canvas.repaint();
            }
        }
    }

    private class MenuListener implements ActionListener {
        File file;
        JFileChooser fileChooser;
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == loadMenu) {
                fileChooser = new JFileChooser();
                int returnVal = fileChooser.showOpenDialog(appWindow);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                    messageTextField.append("Opening file at: " + file.getAbsolutePath() + "\n");
                }
                FileLoad(null, file, true);
            }
            if(e.getSource() == exitMenu) {
                System.exit(0);
            }
        }
    }

    private void emptyBookings() {

    }

    private void BorderSet(JComponent uiItem, String title) {
        uiItem.setBorder(new TitledBorder(new EtchedBorder(), title));
    }

    private void MediumButton(JButton aButton) {
        aButton.setPreferredSize(new Dimension(160, 40));
    }

    private FileReader reader;
    private BufferedReader readerBuffered;
    void FileLoad(String filePath, File file, boolean custom) {
        /*
        try {
            if (!custom) {
                reader = new FileReader(filePath);
                readerBuffered = new BufferedReader(reader);
            } else {
                reader = new FileReader(file);
                readerBuffered = new BufferedReader(reader);
            }
            try {
                try {
                    while (true) {
                        String toPush = readerBuffered.readLine();
                        if (toPush == null) {
                            break;
                        } else {
                            drawableStack.push(Integer.parseInt(toPush));
                        }
                    }
                } catch (NumberFormatException e) {
                    messageTextField.append(e + "\n");
                    messageTextField.append("Check formatting of numbers in input file.\nValues have been pushed to stack up until formatting becomes incorrect.\n");
                }
            } catch (IOException e) {
                messageTextField.append(e + "\n");
                messageTextField.append("File read error, please restart the application and verify value file location and contents.\n");
            }
        } catch (FileNotFoundException e) {
            messageTextField.append(e + "\n");
            messageTextField.append("Please ensure your file of values to be pushed onto the stack is named 'stack.txt' and is in the root directory of the application.\n");
        }
        if(custom) {
            onStack = true;
            canvas.repaint();
        }
        */
    }

    private Canvas canvas;
    private JPanel selector;
    private JMenuItem loadMenu, exitMenu;
    private JTextArea messageTextField;
    private JToggleButton queueStackSelector;
    private JButton emptyButton, popButton, pushButton, enqueueButton, dequeueButton, peekButton, sizeReportButton, reverseStackButton;

    private JFrame appWindow;

    public Window() {


        appWindow = new JFrame("ADT GUI Example program");
        appWindow.setLayout(new BorderLayout());
        appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        canvas = new Canvas();
        appWindow.add(canvas, BorderLayout.CENTER);
        BorderSet(canvas, "ADT");
        canvas.setPreferredSize(new Dimension(500, 500));


        JPanel tools = new JPanel();
        appWindow.add(tools, BorderLayout.LINE_START);
        BorderSet(tools, "Tools");
        tools.setPreferredSize(new Dimension(200, 500));


        selector = new JPanel();
        tools.add(selector, BorderLayout.LINE_START);
        BorderSet(selector, "Currently selected: Stack");
        selector.setPreferredSize(new Dimension(190, 100));
        queueStackSelector = new JToggleButton("Change to Queue");
        selector.add(queueStackSelector, BorderLayout.LINE_START);
        queueStackSelector.setPreferredSize(new Dimension(160, 60));
        ADTStateChangedListener stateListenerObj = new ADTStateChangedListener();
        queueStackSelector.addActionListener(stateListenerObj);


        JPanel messages = new JPanel();
        appWindow.add(messages, BorderLayout.PAGE_END);
        BorderSet(messages, "Messages");
        messages.setPreferredSize(new Dimension(700, 120));
        messageTextField = new JTextArea();
        JScrollPane scroller = new JScrollPane(messageTextField);
        scroller.setPreferredSize(new Dimension(650, 80));
        messages.add(scroller);


        JMenuBar menu = new JMenuBar();
        appWindow.add(menu, BorderLayout.PAGE_START);
        JMenu fileMenu = new JMenu("File");
        loadMenu = new JMenuItem("Load");
        exitMenu = new JMenuItem("Exit");
        menu.add(fileMenu);
        fileMenu.add(loadMenu);
        fileMenu.add(exitMenu);
        MenuListener MenuListenerObj = new MenuListener();
        loadMenu.addActionListener(MenuListenerObj);
        exitMenu.addActionListener(MenuListenerObj);


        JPanel functions = new JPanel();
        BorderSet(functions, "Functions");
        functions.setPreferredSize(new Dimension(190, 400));

        pushButton = new JButton("Push");
        ADTActionListener adtListenerObj = new ADTActionListener();
        pushButton.addActionListener(adtListenerObj);
        MediumButton(pushButton);
        functions.add(pushButton, BorderLayout.LINE_START);

        enqueueButton = new JButton("Enqueue");
        enqueueButton.addActionListener(adtListenerObj);
        MediumButton(enqueueButton);
        enqueueButton.setEnabled(false);
        functions.add(enqueueButton, BorderLayout.AFTER_LAST_LINE);

        popButton = new JButton("Pop");
        popButton.addActionListener(adtListenerObj);
        MediumButton(popButton);
        functions.add(popButton, BorderLayout.AFTER_LAST_LINE);

        dequeueButton = new JButton("Dequeue");
        dequeueButton.addActionListener(adtListenerObj);
        MediumButton(dequeueButton);
        dequeueButton.setEnabled(false);
        functions.add(dequeueButton, BorderLayout.AFTER_LAST_LINE);

        emptyButton = new JButton("Empty selected ADT");
        emptyButton.addActionListener(adtListenerObj);
        MediumButton(emptyButton);
        functions.add(emptyButton, BorderLayout.AFTER_LAST_LINE);

        sizeReportButton = new JButton("Report ADT size");
        sizeReportButton.addActionListener(adtListenerObj);
        MediumButton(sizeReportButton);
        functions.add(sizeReportButton, BorderLayout.AFTER_LAST_LINE);

        peekButton = new JButton("Peek");
        peekButton.addActionListener(adtListenerObj);
        MediumButton(peekButton);
        functions.add(peekButton, BorderLayout.AFTER_LAST_LINE);

        reverseStackButton = new JButton("Display emptied stack");
        reverseStackButton.addActionListener(adtListenerObj);
        MediumButton(reverseStackButton);
        functions.add(reverseStackButton, BorderLayout.AFTER_LAST_LINE);


        tools.add(functions, BorderLayout.AFTER_LAST_LINE);


        appWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);


        appWindow.pack();
        appWindow.setVisible(true);
    }
}
