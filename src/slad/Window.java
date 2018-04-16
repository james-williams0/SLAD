package slad;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import static java.lang.Byte.parseByte;
import static java.lang.Short.parseShort;

public class Window extends JFrame {
    private DrawableArray drawableArray = new DrawableArray();
    private class Canvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawableArray.draw(g, canvas);
        }
    }

    private class ADTActionListener implements ActionListener {
        int integerValue;
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == addButton) {
                String name;
                byte day;
                byte month;
                short year;
                byte hour;
                byte minute;
                short table;
                short numOfPeople;
                String details;

                JTextField nameField = new JTextField();
                JTextField dayField = new JTextField();
                JTextField monthField = new JTextField();
                JTextField yearField = new JTextField();
                JTextField hourField = new JTextField();
                JTextField minuteField = new JTextField();
                JTextField numOfPeopleField = new JTextField();
                JTextField tableField = new JTextField();
                JTextField detailsField = new JTextField();

                Object[] options = {
                        "Name:", nameField,
                        "Day:", dayField,
                        "Month:", monthField,
                        "Year:", yearField,
                        "Hour:", hourField,
                        "Minute:", minuteField,
                        "Number of people:", numOfPeopleField,
                        "Table number:", tableField,
                        "Details:", detailsField,
                };

                int option = JOptionPane.showConfirmDialog(appWindow, options, "Add booking", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
                if (option == JOptionPane.OK_OPTION) {
                    name = nameField.getText();
                    day = parseByte(dayField.getText());
                    month = parseByte(monthField.getText());
                    year = parseShort(yearField.getText());
                    hour = parseByte(hourField.getText());
                    minute = parseByte(minuteField.getText());
                    table = parseShort(tableField.getText());
                    numOfPeople = parseShort(numOfPeopleField.getText());
                    details = detailsField.getText();
                    String date = day + "/" + month +"/" + year;
                    String time = hour + ":" + minute;
                    drawableArray.add(new Booking(name, date, time, table, details, numOfPeople));
                    canvas.repaint();
                    removeButton.setEnabled(true);
                    emptyButton.setEnabled(true);
                }

            } else if(e.getSource() == removeButton) {
                String name;
                byte day;
                byte month;
                short year;
                ArrayList<Booking> toBeRemoved = new ArrayList<>();

                JTextField nameField = new JTextField();
                JTextField dayField = new JTextField();
                JTextField monthField = new JTextField();
                JTextField yearField = new JTextField();

                Object[] options = {
                        "Name:", nameField,
                        "Day:", dayField,
                        "Month:", monthField,
                        "Year:", yearField,
                };

                int option = JOptionPane.showConfirmDialog(appWindow, options, "Delete booking", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
                if(option == JOptionPane.OK_OPTION) {
                    name = nameField.getText();
                    day = parseByte(dayField.getText());
                    month = parseByte(monthField.getText());
                    year = parseShort(yearField.getText());
                    String date = day + "/" + month + "/" + year;
                    for(Booking booking : drawableArray) {
                        if(booking.getName().equals(name) && booking.getDate().equals(date)) {
                            toBeRemoved.add(booking);
                        }
                    }
                    drawableArray.removeAll(toBeRemoved);
                    canvas.repaint();
                    if(drawableArray.size() == 0) {
                        removeButton.setEnabled(false);
                        emptyButton.setEnabled(false);
                    }
                }
            } else if(e.getSource() == sizeReportButton) {
                int numOfBookings = drawableArray.size();
                if(numOfBookings == 1) {
                    messageTextField.append("Currently " + numOfBookings + " booking.\n");
                } else {
                    messageTextField.append("Currently " + numOfBookings + " bookings.\n");
                }
            } else if(e.getSource() == emptyButton) {
                int option = JOptionPane.showConfirmDialog(appWindow, "Are you sure you want to delete all bookings from the system?", "Delete booking", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
                if (option == JOptionPane.OK_OPTION) {
                    drawableArray.removeAll(drawableArray);
                    canvas.repaint();
                    removeButton.setEnabled(false);
                    emptyButton.setEnabled(false);
                }
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

    void FileLoad(String filePath, File file, boolean custom) {
        FileReader reader;
        BufferedReader readerBuffered;
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
                            //drawableStack.push(Integer.parseInt(toPush));
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
            //onStack = true;
            canvas.repaint();
        }
    }

    private Canvas canvas;
    private JPanel selector;
    private JMenuItem loadMenu, exitMenu;
    private JTextArea messageTextField;
    private JToggleButton queueStackSelector;
    private JButton emptyButton, addButton, sizeReportButton, removeButton;

    private JFrame appWindow;

    public Window() {

        appWindow = new JFrame("Restaurant bookings");
        appWindow.setLayout(new BorderLayout());
        appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new Canvas();
        appWindow.add(canvas, BorderLayout.CENTER);
        BorderSet(canvas, "Bookings");
        canvas.setPreferredSize(new Dimension(500, 500));

        JPanel tools = new JPanel();
        appWindow.add(tools, BorderLayout.LINE_START);
        BorderSet(tools, "Tools");
        tools.setPreferredSize(new Dimension(200, 500));

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
        functions.setPreferredSize(new Dimension(190, 300));

        addButton = new JButton("Add");
        ADTActionListener adtListenerObj = new ADTActionListener();
        addButton.addActionListener(adtListenerObj);
        MediumButton(addButton);
        functions.add(addButton, BorderLayout.LINE_START);

        removeButton = new JButton("Delete one");
        removeButton.addActionListener(adtListenerObj);
        MediumButton(removeButton);
        removeButton.setEnabled(false);
        functions.add(removeButton, BorderLayout.AFTER_LAST_LINE);

        emptyButton = new JButton("Delete all");
        emptyButton.addActionListener(adtListenerObj);
        MediumButton(emptyButton);
        emptyButton.setEnabled(false);
        functions.add(emptyButton, BorderLayout.AFTER_LAST_LINE);

        sizeReportButton = new JButton("Current number of bookings");
        sizeReportButton.addActionListener(adtListenerObj);
        MediumButton(sizeReportButton);
        functions.add(sizeReportButton, BorderLayout.AFTER_LAST_LINE);

        tools.add(functions, BorderLayout.AFTER_LAST_LINE);

        appWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);

        appWindow.pack();
        appWindow.setVisible(true);
    }
}
