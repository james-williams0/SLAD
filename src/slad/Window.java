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
                boolean invalidInput = true;
                int leapYearModifier = 4;
                while(true) {
                    String name;
                    byte day;
                    byte month = 0;
                    short year = 0;
                    byte hour;
                    byte minute;
                    short table = 0;
                    short numOfPeople = 0;
                    String details;

                    String dayWithZero = null;
                    String monthWithZero = null;

                    String hourWithZero = null;
                    String minuteWithZero = null;

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
                        details = detailsField.getText();
                        try {
                            year = parseShort(yearField.getText());
                        } catch(NumberFormatException exc) {
                            messageTextField.append("Year is invalid, please type only the current year.");
                            invalidInput = true;
                        }
                        try {
                            month = parseByte(monthField.getText());
                            if(month <= 12 && month >= 1) {
                                if(month <= 9) {
                                    monthWithZero = "0" + month;
                                } else {
                                    monthWithZero = Byte.toString(month);
                                }
                            } else {
                                messageTextField.append("Month is invalid, please type only one number in the range 1-12 inclusive.");
                                invalidInput = true;
                            }
                        } catch(NumberFormatException exc) {
                            messageTextField.append("Month is invalid, please type only one number in the range 1-12 inclusive.");
                            invalidInput = true;
                        }
                        try {
                            day = parseByte(dayField.getText());
                            if((day > 0 && (day <= 31 && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)) || (day <= 30 && (month == 4 || month == 6 || month == 9 || month == 11)) || (day <= 28 && (month == 2 && year%leapYearModifier != 0)) || (day <= 29 && (month == 2 && year%leapYearModifier == 0)))) {
                                if(day <= 9) {
                                    dayWithZero = "0" + day;
                                } else {
                                    dayWithZero = Byte.toString(day);
                                }
                            } else {
                                messageTextField.append("Day is invalid, please type only one number in the range 1-31 inclusive.");
                                invalidInput = true;
                            }
                        } catch(NumberFormatException exc) {
                            messageTextField.append("Day is invalid, please type only one number in the range 1-31 inclusive.");
                            invalidInput = true;
                        }
                        try {
                            hour = parseByte(hourField.getText());
                            if(hour <= 23 && hour >= 0) {
                                if(hour <= 9) {
                                    hourWithZero = "0" + hour;
                                } else {
                                    hourWithZero = Byte.toString(hour);
                                }
                            } else {
                                messageTextField.append("Hour is invalid, please type only one number in the range 0-23.");
                                invalidInput = true;
                            }
                        } catch(NumberFormatException exc) {
                            messageTextField.append("Hour is invalid, please type only one number in the range 0-23.");
                            invalidInput = true;
                        }
                        try {
                            minute = parseByte(minuteField.getText());
                            if(minute <= 59 && minute >= 0) {
                                if(minute <= 9) {
                                    minuteWithZero = "0" + minute;
                                } else {
                                    minuteWithZero = Byte.toString(minute);
                                }
                            } else {
                                messageTextField.append("Minute is invalid, please type only one number in the range 0-59.");
                                invalidInput = true;
                            }
                        } catch(NumberFormatException exc) {
                            messageTextField.append("Minute is invalid, please type only one number in the range 0-59.");
                            invalidInput = true;
                        }
                        try {
                            table = parseShort(tableField.getText());
                        } catch(NumberFormatException exc) {
                            messageTextField.append("Table number is invalid, please type only the number of the table you wish to book for this group.");
                            invalidInput = true;
                        }
                        try {
                            numOfPeople = parseShort(numOfPeopleField.getText());
                        } catch(NumberFormatException exc) {
                            messageTextField.append("Number of people is invalid, please type only the number of people in the group.");
                            invalidInput = true;
                        }
                        if(!invalidInput) {
                            String date = dayWithZero + "/" + monthWithZero + "/" + year;
                            String time = hourWithZero + ":" + minuteWithZero;
                            drawableArray.add(new Booking(name, date, time, table, details, numOfPeople));
                            canvas.repaint();
                            removeButton.setEnabled(true);
                            emptyButton.setEnabled(true);
                            break;
                        }
                    }
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
