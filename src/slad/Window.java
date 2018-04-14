package slad;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Window extends JFrame {

    //  Extension of the JPanel class to Canvas class to allow the drawing graphics elements as specified
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
            if(e.getSource() == pushButton) {               //  For pushing values I initialise a JOptionPane to take input, store that input in a string, test the input for validity in a try-catch structure (required for
                JOptionPane pushPane = new JOptionPane();   //  the parseInt method anyway due to it potentially throwing a NumberFormatException), then only if it is valid to push the value and append it to the message area.
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
            } else if(e.getSource() == popButton) { //  The process for popping values is less extensive than pushing them, and involves another try-catch structure to catch my custom OutOfBoundsException that may be thrown
                try {                               //  by attempting to pop a value from an empty stack. Whatever value is popped is appended to the message area.
                    messageTextField.append("Popped: " + Integer.toString(drawableStack.pop()) + "\n");
                    onStack = true;
                    canvas.repaint();
                } catch(OutOfBoundsException exception) {
                    messageTextField.append(exception + "\n");
                    messageTextField.append("Your stack is empty, there are no more values to pop.\n");
                }
            } else if(e.getSource() == enqueueButton) {         //  Enqueuing and dequeuing are both very similar to pushing and popping except that it is for the queue and it draws the queue afterwards instead of the stack
                JOptionPane enqueuePane = new JOptionPane();    //  and has no interaction with the stack.
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
            } else if(e.getSource() == peekButton) {    //  Attempts to append the current value of the head or front of the structure to the message area, catches a NullPointerException if the value
                try {                                   //  of either these is null (when the structures are empty).
                    if (!queueStackSelector.isSelected()) {
                        messageTextField.append("Current head value: " + Integer.toString(drawableStack.head.getVal()) + "\n");
                    } else {
                        messageTextField.append("Current front value: " + Integer.toString(drawableQueue.front.getVal()) + "\n");
                    }
                } catch(NullPointerException exception) {
                    messageTextField.append(exception + "\n");
                    messageTextField.append("Your stack is empty, there are no values to peek at.");
                }
            } else if(e.getSource() == sizeReportButton) {      //  Uses the method in my stack class to retrieve the size of the stack or queue and report this size to the message area.
                try {                                           //  Catches a NullPointerException which occurs if the size is 0 (as in there are no values in the structure selected).
                    if (!queueStackSelector.isSelected()) {
                        messageTextField.append("Current stack size: " + Integer.toString(drawableStack.size()) + "\n");
                    } else {
                        messageTextField.append("Current queue size: " + Integer.toString(drawableQueue.size()) + "\n");
                    }
                } catch(NullPointerException exception) {
                    messageTextField.append(exception + "\n");
                    messageTextField.append("Your queue is empty, size is 0.\n");
                }
            } else if(e.getSource() == emptyButton) {       //  Uses a method to pop/dequeue every element in the currently selected structure.
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
            } else if(e.getSource() == reverseStackButton) {    //  Pops/dequeues every value in the currently selected ADT and appends them to a string, this string is then
                int currentSize = drawableStack.size();         //  appended to the message area, printing the structure to the area in reverse order.
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

    private class MenuListener implements ActionListener {      //  This listener is for both menu buttons, file and load. Upon load being clicked, it uses a JFileChooser customised to
        File file;                                              //  my needs and stores the selected file in a File variable to be used by my FileLoad method. It also appends the file
        JFileChooser fileChooser;                               //  path to the message area.
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
            if(e.getSource() == exitMenu) {     //  If, instead, the exit button is clicked, it exits the application gracefully.
                System.exit(0);
            }
        }
    }

    private class ADTStateChangedListener implements ActionListener {   //  This action listener is attached to the button controlling whether I am currently operating on the
        @Override
        public void actionPerformed(ActionEvent e) {                   //  stack or queue and calls the method below.
            ToggleStackQueueText(queueStackSelector, selector, messageTextField);
        }
    }

    private void ToggleStackQueueText(JToggleButton i, JPanel j, JTextArea k) {     //  When the JToggleButton is deselected it switches operation to the stack and disables buttons
        if(!i.isSelected()) {                                                       //  that are only relevant to the queue, while doing the opposite when it is selected. It
            i.setText("Change to: Queue");                                          //  adjusts border and button text in the toggle button's containing JPanel.
            BorderSet(j, "Currently selected: Stack");
            k.append("Changed from operating on the queue to the stack\n");
            drawableStack = new DrawStack();
            onStack = true;
            enqueueButton.setEnabled(false);
            dequeueButton.setEnabled(false);
            reverseStackButton.setEnabled(true);
            popButton.setEnabled(true);
            pushButton.setEnabled(true);
            loadMenu.setEnabled(true);
            canvas.repaint();
        }
        else {
            i.setText("Change to: Stack");
            BorderSet(j, "Currently selected: Queue");
            k.append("Changed from operating on the stack to the queue\n");
            drawableQueue = new DrawQueue();
            onQueue = true;
            popButton.setEnabled(false);
            pushButton.setEnabled(false);
            reverseStackButton.setEnabled(false);
            enqueueButton.setEnabled(true);
            dequeueButton.setEnabled(true);
            loadMenu.setEnabled(false);
            canvas.repaint();
        }
    }

    private void emptyStack() {                     //  These emptyStack and emptyQueue methods just empty their respective structures, also appending the emptied values to
        int currentSize = drawableStack.size();     //  the message area as it runs.
        for(int i = 0; i < currentSize; i++) {
            messageTextField.append("Popped :" + Integer.toString(drawableStack.pop()) + "\n");
        }
    }

    private void emptyQueue() {
        int currentSize = drawableQueue.size();
        for(int i = 0; i < currentSize; i++) {
            messageTextField.append("Dequeued: " + Integer.toString(drawableQueue.deQueue()) + "\n");
        }
    }

    private void BorderSet(JComponent uiItem, String title) {               //  These BorderSet and MediumButton methods are to save typing later on by setting
        uiItem.setBorder(new TitledBorder(new EtchedBorder(), title));      //  my standard etched+titled borders and button size.
    }

    private void MediumButton(JButton aButton) {
        aButton.setPreferredSize(new Dimension(160, 40));
    }

    private FileReader reader;
    private BufferedReader readerBuffered;
    void FileLoad(String filePath, File file, boolean custom) { //  File loading attempts to read a line in the file, considering the specification that the text file will have
        emptyStack();                                           //  an integer separated by a line each, then parse the string representation of an integer read into an integer
        try {                                                   //  to be pushed to the stack. There are two use cases for this method, the initial, automatic loading of "stack.txt"
            if (!custom) {                                      //  and then a custom file loaded through GUI, this accomodates both of these through the custom boolean flag.
                reader = new FileReader(filePath);              //  A variety of exceptions might be thrown depending on if it is a custom file or not, file formatting, if the file
                readerBuffered = new BufferedReader(reader);    //  contains solely string representations of integers, and whether the file is corrupted. All of these are handled
            } else {                                            //  via the catching of NumberFormatException (thrown if the file does not contain solely string representations of an
                reader = new FileReader(file);                  //  integer separated by a line each), IOException (if the file is there but there are other restrictions on reading it
                readerBuffered = new BufferedReader(reader);    //  such as corruption, and FileNotFoundException (in the case that the file is simply not where specified).
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
    }

    private Canvas canvas;      //  All GUI elements that need to be accessed by any of the above are declared here. Others are declared within Window.
    private JPanel selector;
    private JMenuItem loadMenu, exitMenu;
    private JTextArea messageTextField;
    private JToggleButton queueStackSelector;
    private JButton emptyButton, popButton, pushButton, enqueueButton, dequeueButton, peekButton, sizeReportButton, reverseStackButton;

    private JFrame appWindow;

    public Window() {

        //Initialisation of the JFrame with its title, layout, and operation upon it being closed.
        appWindow = new JFrame("ADT GUI Example program");
        appWindow.setLayout(new BorderLayout());
        appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Drawing area (Canvas) which graphic representation of the ADT is drawn to.
        canvas = new Canvas();
        appWindow.add(canvas, BorderLayout.CENTER);
        BorderSet(canvas, "ADT");
        canvas.setPreferredSize(new Dimension(500, 500));

        //Tools panel which will later contain many buttons for operating on the ADT selected.
        JPanel tools = new JPanel();
        appWindow.add(tools, BorderLayout.LINE_START);
        BorderSet(tools, "Tools");
        tools.setPreferredSize(new Dimension(200, 500));

        //GUI for selecting between working with the stack and queue ADTs, includes a JPanel, JToggleButton, and a listener for actions on this button.
        selector = new JPanel();
        tools.add(selector, BorderLayout.LINE_START);
        BorderSet(selector, "Currently selected: Stack");
        selector.setPreferredSize(new Dimension(190, 100));
        queueStackSelector = new JToggleButton("Change to Queue");
        selector.add(queueStackSelector, BorderLayout.LINE_START);
        queueStackSelector.setPreferredSize(new Dimension(160, 60));
        ADTStateChangedListener stateListenerObj = new ADTStateChangedListener();
        queueStackSelector.addActionListener(stateListenerObj);

        //Messages panel with a scrollable text area.
        JPanel messages = new JPanel();
        appWindow.add(messages, BorderLayout.PAGE_END);
        BorderSet(messages, "Messages");
        messages.setPreferredSize(new Dimension(700, 120));
        messageTextField = new JTextArea();
        JScrollPane scroller = new JScrollPane(messageTextField);
        scroller.setPreferredSize(new Dimension(650, 80));
        messages.add(scroller);

        //Menu bar with "Exit" and "Load" options under a "File" submenu, Load option becomes disabled when the queue is selected.
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

        //For stack and queue, GUI for the push, pop, enqueue, dequeue, size report, value peek, ADT empty and stack reverse functionality.
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

        //Addition of all the buttons specified above to the tools panel
        tools.add(functions, BorderLayout.AFTER_LAST_LINE);

        //Sets the application to launch maximised and I recommend it to be kept this way for maximum visbility of nodes, especially when operating on the queue.
        appWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //Pack ensures sizing is correct, then all GUI is set to be visible.
        appWindow.pack();
        appWindow.setVisible(true);
    }
}
