package com.example;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;
import javax.swing.*;
import javax.swing.table.*;

public class PageOne extends JFrame{
    private JPanel leftPanel;
    private JTable table, topPrioritiesTable;;
    private DefaultTableModel taskTable;
    private CurvedTextField taskText;
    private JTextField taskInputField, lastColumnOutput;
    private int taskCounter = 0;

    public PageOne(FrameController controller){
        setTitle("Spark Sched");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon logo = new ImageIcon(getClass().getResource("SparkSchedLogo.png"));
        setIconImage(logo.getImage());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // LEFT SIDE
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(425, 720));
        leftPanel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);

        // HEADER
        JLabel label2 = new JLabel("SPARK SCHED");
        label2.setFont(new Font("SERIF", Font.PLAIN, 35));
        label2.setForeground(new Color(84, 84, 84));

        Font customFont = null;
        try {
            InputStream fontStream = getClass().getResourceAsStream("HeyGotcha-Regular.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(55f);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        if (customFont != null) {
            label2.setFont(customFont);
        } else {
            // Fallback to default font if custom font loading fails
            label2.setFont(new Font("SERIF", Font.PLAIN, 35));
        }

        // Add the label to the topPanel with GridBagConstraints
        GridBagConstraints gbcLabel2 = new GridBagConstraints();
        gbcLabel2.gridx = 0;
        gbcLabel2.gridy = 0;
        gbcLabel2.anchor = GridBagConstraints.WEST;
        gbcLabel2.insets = new Insets(50, 22, 0, 100);
        topPanel.add(label2, gbcLabel2);

        JPanel underlinePanel = new JPanel();
        underlinePanel.setPreferredSize(new Dimension(165, 5));
        underlinePanel.setBackground(new Color(84, 84, 84));

        GridBagConstraints gbcUnderline = new GridBagConstraints();
        gbcUnderline.gridx = 0;
        gbcUnderline.gridy = 1;
        gbcUnderline.anchor = GridBagConstraints.WEST;
        gbcUnderline.insets = new Insets(0, 22, 35, 0);
        topPanel.add(underlinePanel, gbcUnderline);

        JLabel textLabel1 = new JLabel("<html> Turning plans into sparks<br>of success.");
        textLabel1.setFont(new Font("Canva Sans", Font.PLAIN, 12));
        textLabel1.setForeground(new Color(84, 84, 84));

        GridBagConstraints gbcTextLabel1 = new GridBagConstraints();
        gbcTextLabel1.gridx = 0;
        gbcTextLabel1.gridy = 1;
        gbcTextLabel1.anchor = GridBagConstraints.WEST;
        gbcTextLabel1.insets = new Insets(-3, 195, 15, 10);
        topPanel.add(textLabel1, gbcTextLabel1);

        // TABLE
        HighlightPanel head = new HighlightPanel();
        head.setBounds(50, 145, 340, 33);

        JLabel label1 = new JLabel("T A S K  S U M M A R Y");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Canva Sans", Font.BOLD, 14));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setBounds(10, 20, 280, 20);
        head.add(label1);

        taskTable = new DefaultTableModel();
        taskTable.addColumn("DATE");
        taskTable.addColumn("TIME");
        taskTable.addColumn("TASK");

        table = new JTable(taskTable);
        table.setBounds(50, 190, 400, 200);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(190, 182, 168));
        header.setForeground(Color.WHITE);

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        TableColumnModel columnModel = table.getColumnModel();
        TableColumn columnDate = columnModel.getColumn(0);
        TableColumn columnTime = columnModel.getColumn(1);
        TableColumn columnTask = columnModel.getColumn(2);

        columnDate.setPreferredWidth(50);
        columnTime.setPreferredWidth(50);
        columnTask.setPreferredWidth(150);

        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 30));
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 195, 340, 400);


        // FOOTER
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(Color.WHITE);

        RoundedButtonPanel clearButton = new RoundedButtonPanel("Clear");
        clearButton.setFont(new Font("Canva Sans", Font.PLAIN, 14));
        clearButton.setForeground(Color.BLACK);
        clearButton.setPreferredSize(new Dimension(90, 30));
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear all inputs?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION){
                    taskTable.setRowCount(0);
                    taskInputField.setText("");
                }
            }
        });


        GridBagConstraints gbcClearButton = new GridBagConstraints();
        gbcClearButton.gridx = 0;
        gbcClearButton.gridy = 0;
        gbcClearButton.anchor = GridBagConstraints.CENTER;
        gbcClearButton.insets = new Insets(0, 75, 45, -185);
        bottomPanel.add(clearButton, gbcClearButton);

        JLabel tableDesc = new JLabel("<html><div style='text-align:left;'>" +
                "<p>* The table above displays the summary of your<br>" +
                "&nbsp;&nbsp; tasks sorted by the most recent additions.</p></div></html>");
        tableDesc.setForeground(new Color(84, 84, 84));
        tableDesc.setFont(new Font("Arial", Font.PLAIN, 11));

        GridBagConstraints gbcTableDesc = new GridBagConstraints();
        gbcTableDesc.gridx = 0;
        gbcTableDesc.gridy = 0;
        gbcTableDesc.anchor = GridBagConstraints.WEST;
        gbcTableDesc.insets = new Insets(0, -90, 52, 0);
        bottomPanel.add(tableDesc, gbcTableDesc);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        leftPanel.add(head, BorderLayout.CENTER);
        leftPanel.add(scrollPane, BorderLayout.CENTER);
        leftPanel.add(topPanel, BorderLayout.NORTH);
        leftPanel.add(bottomPanel, BorderLayout.SOUTH);


        // RIGHT SIDE
        JPanel taskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        taskPanel.setBackground(Color.WHITE);
        taskPanel.setBounds(426, 50, 350, 65);

        JLabel taskLabel = new JLabel("Enter number of task  ");
        taskLabel.setForeground(Color.BLACK);
        taskLabel.setFont(new Font("Canva Sans", Font.PLAIN, 20));
        taskLabel.setHorizontalAlignment(SwingConstants.CENTER);
        taskPanel.add(taskLabel);

        taskText = new CurvedTextField();
        taskText.setPreferredSize(new Dimension(140,30));
        taskText.setText("1-10");
        taskText.setForeground(Color.GRAY);
        taskText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (taskText.getText().equals("1-10")) {
                    taskText.setText("");
                    taskText.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (taskText.getText().isEmpty()) {
                    taskText.setForeground(Color.GRAY);
                    taskText.setText("1-10");
                }
            }
        });
        taskPanel.add(taskText);
        JLabel taskLabel2 = new JLabel("<html><div style='text-align: left;'><table><tr><td style='vertical-align: top;'>&#8226;</td><td>Indicate the number of tasks you wish to complete.</td></tr></table></div></html>");
        taskLabel2.setForeground(Color.GRAY);
        taskLabel2.setFont(new Font("Arial", Font.PLAIN, 12));
        taskPanel.add(taskLabel2);
        add(taskPanel);

        JPanel button1Panel = new JPanel();
        button1Panel.setBackground(Color.WHITE);
        button1Panel.setBounds(800, 50, 120, 50);

        RoundedButtonPanel button1 = new RoundedButtonPanel("Set");
        button1.setFont(new Font("Canva Sans", Font.PLAIN, 12));
        button1.setPreferredSize(new Dimension(80, 30));
        button1.addActionListener(e -> {
            String numTasks = taskText.getText();

            try {
                int taskCount = Integer.parseInt(numTasks);
                if (taskCount >= 1 && taskCount <= 10) {
                    taskTable.setRowCount(taskCount);
                    JOptionPane.showMessageDialog(null, "Number of tasks set to " + taskCount, "Number of Tasks Set", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Calculate and display the result in taskInputField
                    double halvedTaskCount = (double) taskCount / 2;
                    int result = (int) Math.ceil(halvedTaskCount);
                    taskInputField.setText(String.valueOf(result));
                } else {
                    JOptionPane.showMessageDialog(null, "You are allowed to enter up to 10 tasks only.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });


        button1Panel.add(button1);
        add(button1Panel);

        JPanel actPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actPanel.setBackground(Color.WHITE);
        actPanel.setBounds(426, 125, 790, 40);

        JLabel actLabel = new JLabel("Name of the activity  ");
        actLabel.setForeground(Color.BLACK);
        actLabel.setFont(new Font("Canva Sans", Font.PLAIN, 18));
        actPanel.add(actLabel);

        CurvedTextField actText = new CurvedTextField();
        actText.setPreferredSize(new Dimension(590,30));
        actText.setText("Type task name here");
        actText.setForeground(Color.GRAY);
        actText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (actText.getText().equals("Type task name here")) {
                    actText.setText("");
                    actText.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (actText.getText().isEmpty()) {
                    actText.setForeground(Color.GRAY);
                    actText.setText("Type name here");
                }
            }
        });
        actPanel.add(actText);
        add(actPanel);

        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timePanel.setBackground(Color.WHITE);
        timePanel.setBounds(426, 175, 250, 40);

        JLabel timeLabel = new JLabel("Time ");
        timeLabel.setForeground(Color.BLACK);
        timeLabel.setFont(new Font("Canva Sans", Font.PLAIN, 18));
        timeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        timePanel.add(timeLabel);

        CurvedTextField timeText = new CurvedTextField();
        timeText.setPreferredSize(new Dimension(110,30));
        timeText.setText("hh:mm");
        timeText.setForeground(Color.GRAY);
        timeText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (timeText.getText().equals("hh:mm")) {
                    timeText.setText("");
                    timeText.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (timeText.getText().isEmpty()) {
                    timeText.setForeground(Color.GRAY);
                    timeText.setText("hh:mm");
                }
            }
        });
        timePanel.add(timeText);
        String[] amPmOptions = {"AM", "PM"};
        JComboBox<String> timeComboBox = new JComboBox<>(amPmOptions);
        timeComboBox.setFont(new Font("Canva Sans", Font.PLAIN, 12));
        timeComboBox.setPreferredSize(new Dimension(60, 30));
        timeComboBox.setBackground(Color.WHITE);
        timePanel.add(timeComboBox);
        add(timePanel);

        JPanel datePanel = new JPanel();
        datePanel.setBackground(Color.WHITE);
        datePanel.setBounds(750, 175, 350, 50);

        JLabel dateLabel = new JLabel("Date ");
        dateLabel.setForeground(Color.BLACK);
        dateLabel.setFont(new Font("Canva Sans", Font.PLAIN, 18));
        dateLabel.setHorizontalAlignment(SwingConstants.LEFT);
        datePanel.add(dateLabel);

        CurvedTextField dateText = new CurvedTextField();
        dateText.setPreferredSize(new Dimension(95,30));
        dateText.setText("YYYY-MM-DD");
        dateText.setForeground(Color.GRAY);
        dateText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (dateText.getText().equals("YYYY-MM-DD")) {
                    dateText.setText("");
                    dateText.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (dateText.getText().isEmpty()) {
                    dateText.setForeground(Color.GRAY);
                    dateText.setText("YYYY-MM-DD");
                }
            }
        });
        datePanel.add(dateText);

        JPanel extraPanel = new JPanel();
        extraPanel.setBackground(Color.WHITE);
        extraPanel.setPreferredSize(new Dimension(20, 0));
        datePanel.add(extraPanel);

        RoundedButtonPanel dateButton = new RoundedButtonPanel("Add task");
        dateButton.setFont(new Font("Canva Sans", Font.PLAIN, 12));
        dateButton.setPreferredSize(new Dimension(80, 30));
        datePanel.add(dateButton);
        add(datePanel);

        dateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = dateText.getText();
                String time = timeText.getText() + " " + timeComboBox.getSelectedItem();
                String task = actText.getText();

                if (date.equals("YYYY-MM-DD") || time.equals("hh:mm") || task.equals("Type task name here")){
                    JOptionPane.showMessageDialog(null, "Please fill in all fields before adding a task.", "Incomplete Information", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (taskCounter < getMaxTaskCount()) {
                    int rowIndex = 0;
                    for (int i = 0; i < taskTable.getRowCount(); i++) {
                        if (taskTable.getValueAt(i, 0) == null || taskTable.getValueAt(i, 0).toString().isEmpty()) {
                            rowIndex = i;
                            break;
                        }
                    }
                    taskTable.setValueAt(date, rowIndex, 0);
                    taskTable.setValueAt(time, rowIndex, 1);
                    taskTable.setValueAt(task, rowIndex, 2);

                    if (taskCounter == getMaxTaskCount() - 1) {
                        int tasksToPrint = Math.min(taskCounter, Integer.parseInt(taskInputField.getText()));

                        // Add rows from taskTable to topPrioritiesTable up to the taskInputField count
                        DefaultTableModel topPrioritiesTableModel = (DefaultTableModel) topPrioritiesTable.getModel();
                        for (int i = 0; i < tasksToPrint; i++) {
                            Object[] rowData = new Object[taskTable.getColumnCount()];
                            for (int j = 0; j < taskTable.getColumnCount(); j++) {
                                rowData[j] = taskTable.getValueAt(i, j);
                            }
                            topPrioritiesTableModel.addRow(rowData);
                        }
                        sortTable(topPrioritiesTableModel);

                        int lastRowIndex = topPrioritiesTableModel.getRowCount() - 1;
                        String lastDate = (String) topPrioritiesTableModel.getValueAt(lastRowIndex, 0);
                        String lastTime = (String) topPrioritiesTableModel.getValueAt(lastRowIndex, 1);

                        String outputText = lastDate + " and " + lastTime;

                        lastColumnOutput.setText(outputText);
                    }
                    taskCounter++;
                } else {
                    JOptionPane.showMessageDialog(null, "You have reached the maximum number of tasks allowed.", "Maximum Task Limit Reached", JOptionPane.ERROR_MESSAGE); 
                }
            }
        });


        JPanel bulletPanel = new JPanel();
        bulletPanel.setBackground(Color.WHITE);
        bulletPanel.setBounds(426, 215, 400, 30);

        JLabel bulletLabel = new JLabel("<html><div style='text-align: left;'><table><tr><td style='vertical-align: top;'>&#8226;</td><td>Specify the activity name, task time, and date of the task you wish to add.</td></tr></table></div></html>");
        bulletLabel.setForeground(Color.GRAY);
        bulletLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        bulletPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bulletPanel.add(bulletLabel);
        add(bulletPanel);

        JPanel outputPanel = new JPanel();
        outputPanel.setBackground(new Color(209, 203, 188));
        outputPanel.setForeground(Color.WHITE);
        outputPanel.setBounds(426, 255, 790, 70);

        JPanel outputPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        outputPanel2.setBackground(new Color(209, 203, 188));
        outputPanel2.setForeground(Color.WHITE);
        outputPanel2.setBounds(426, 255, 340, 70);

        HighlightPanel medPanel = new HighlightPanel();
        medPanel.setBounds(672, 260, 37, 26);

        taskInputField = new JTextField(3);
        taskInputField.setOpaque(false);
        taskInputField.setBorder(BorderFactory.createEmptyBorder());
        taskInputField.setHorizontalAlignment(SwingConstants.CENTER);
        taskInputField.setFont(new Font("Arial", Font.PLAIN, 12));
        taskInputField.setForeground(Color.WHITE);
        taskInputField.setEditable(false);
        medPanel.add(taskInputField);

        HighlightPanel pivotPanel = new HighlightPanel();
        pivotPanel.setBounds(685, 293, 380, 26);

        lastColumnOutput = new JTextField(28);

        lastColumnOutput.setEditable(false);
        lastColumnOutput.setOpaque(false);
        lastColumnOutput.setBorder(BorderFactory.createEmptyBorder());
        lastColumnOutput.setHorizontalAlignment(SwingConstants.LEFT);
        lastColumnOutput.setFont(new Font("Arial", Font.PLAIN, 15));
        lastColumnOutput.setForeground(Color.WHITE);
        pivotPanel.add(lastColumnOutput);

        JLabel outputLabel = new JLabel("<html><div style='text-align: left;'><table><tr><td style='vertical-align: top;'>&#8226;</td><td>Your lists of priorities consists of  ..l...... tasks.</td></tr></table></div></html>");
        outputLabel.setForeground(Color.BLACK);
        outputLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        outputPanel2.add(outputLabel);
        JLabel outputLabel2 = new JLabel("<html><div style='text-align: left;'><table><tr><td style='vertical-align: top;'>&#8226;</td><td>The top task should be finished by </td></tr></table></div></html>");
        outputLabel2.setForeground(Color.BLACK);
        outputLabel2.setFont(new Font("Arial", Font.PLAIN, 15));
        outputPanel2.add(outputLabel2);
        add(medPanel);
        add(pivotPanel);
        add(outputPanel2);
        add(outputPanel);

        HighlightPanel topPrioritiesPanel = new HighlightPanel();
        topPrioritiesPanel.setBounds(426, 341, 790, 33);
        add(topPrioritiesPanel);

        // Top Priorities label
        JLabel topPrioritiesLabel = new JLabel("S C H E D U L E   R O U T I N E");
        topPrioritiesLabel.setForeground(Color.WHITE);
        topPrioritiesLabel.setFont(new Font("Canva Sans", Font.BOLD, 14));
        topPrioritiesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPrioritiesLabel.setBounds(10, 20, 280, 20);
        topPrioritiesPanel.add(topPrioritiesLabel);

        // Top Priorities Table / TPT
        DefaultTableModel topPrioritiesTableModel = new DefaultTableModel();
        topPrioritiesTableModel.addColumn("DATE");
        topPrioritiesTableModel.addColumn("TIME");
        topPrioritiesTableModel.addColumn("TASK");

        topPrioritiesTable = new JTable(topPrioritiesTableModel);
        topPrioritiesTable.setBounds(426, topPrioritiesPanel.getY() + topPrioritiesPanel.getHeight() + 20, 790, 210);

        // TPT: Set column header attributes
        JTableHeader topPrioritiesHeader = topPrioritiesTable.getTableHeader();
        topPrioritiesHeader.setBackground(new Color(190, 182, 168));
        topPrioritiesHeader.setForeground(Color.WHITE);
        topPrioritiesHeader.setReorderingAllowed(false);
        topPrioritiesHeader.setResizingAllowed(false);

        // TPT: Set preferred widths for columns
        TableColumnModel topPrioritiesColumnModel = topPrioritiesTable.getColumnModel();
        TableColumn topPrioritiesColumnDate = topPrioritiesColumnModel.getColumn(0);
        TableColumn topPrioritiesColumnTime = topPrioritiesColumnModel.getColumn(1);
        TableColumn topPrioritiesColumnName = topPrioritiesColumnModel.getColumn(2);

        topPrioritiesColumnDate.setPreferredWidth(30);
        topPrioritiesColumnTime.setPreferredWidth(30);
        topPrioritiesColumnName.setPreferredWidth(180);

        topPrioritiesTable.getTableHeader().setPreferredSize(new Dimension(topPrioritiesTable.getTableHeader().getWidth(), 30));
        topPrioritiesTable.setRowHeight(25);

        // TPT: Add the Top Priorities table to the frame
        JScrollPane topPrioritiesScrollPane = new JScrollPane(topPrioritiesTable);
        topPrioritiesScrollPane.setBounds(426, topPrioritiesPanel.getY() + topPrioritiesPanel.getHeight() + 15, 790, 206);
        add(topPrioritiesScrollPane);

        JPanel proceedPanel = new JPanel();
        proceedPanel.setBackground(Color.WHITE);
        proceedPanel.setBounds(426, 595, 790, 50);
        proceedPanel.setLayout(null);
        add(proceedPanel);

        JLabel bottomPanelLabel = new JLabel("Would you like to rearrange your schedule efficiently according to your chosen task?");
        bottomPanelLabel.setFont(new Font("Canva Sans", Font.PLAIN, 14));
        bottomPanelLabel.setBounds(5, 5, 600, 30);
        proceedPanel.add(bottomPanelLabel);

        RoundedButtonPanel routineButton = new RoundedButtonPanel("Yes");
        routineButton.setBounds(bottomPanelLabel.getWidth() + 90, 8, 90, 30);
        proceedPanel.add(routineButton);
        routineButton.addActionListener(e -> controller.switchToPrioPanel());

        add(scrollPane);
        add(mainPanel);

        setVisible(true);
    }

    private int getMaxTaskCount(){
        String numTasks = taskText.getText();

        try {
            return Integer.parseInt(numTasks);
        }
        catch (NumberFormatException e){
            return 10;
        }
    }

    public Stack<String[]> getTaskStack(){
        Stack<String[]> taskStack = new Stack<>();

        for (int i = 0; i < taskTable.getRowCount(); i++){
            String date = (String) taskTable.getValueAt(i, 0);
            String time = (String) taskTable.getValueAt(i, 1);
            String task = (String) taskTable.getValueAt(i, 2);

            if (date != null && time != null && task != null){
                String[] taskData = {date, time, task};
                taskStack.push(taskData);
            }
        }
        return taskStack;
    }

    private void quicksort(DefaultTableModel model, int low, int high) {
        if (low < high) {
            int pi = partition(model, low, high);
            quicksort(model, low, pi - 1);
            quicksort(model, pi + 1, high);
        }
    }

    private void sortTable(DefaultTableModel model) {
        int n = model.getRowCount();
        quicksort(model, 0, n - 1);
    }

    private int partition(DefaultTableModel model, int low, int high) {
        String pivotDate = (String) model.getValueAt(high, 0);
        String pivotTime = (String) model.getValueAt(high, 1);
        String pivotAmPm = pivotTime.substring(pivotTime.length() - 2);
        String pivotDateTime = pivotDate + " " + pivotTime;

        int i = low - 1;
        for (int j = low; j < high; j++) {
            String currentDate = (String) model.getValueAt(j, 0);
            String currentTime = (String) model.getValueAt(j, 1);
            String currentAmPm = currentTime.substring(currentTime.length() - 2); 
            String currentDateTime = currentDate + " " + currentTime;

            if (currentDateTime.compareTo(pivotDateTime) < 0 ||
                    (currentDateTime.compareTo(pivotDateTime) == 0 && currentAmPm.equals("AM") && pivotAmPm.equals("PM"))) {
                i++;
                for (int k = 0; k < model.getColumnCount(); k++) {
                    Object temp = model.getValueAt(i, k);
                    model.setValueAt(model.getValueAt(j, k), i, k);
                    model.setValueAt(temp, j, k);
                }
            }
        }

        for (int k = 0; k < model.getColumnCount(); k++) {
            Object temp = model.getValueAt(i + 1, k);
            model.setValueAt(model.getValueAt(high, k), i + 1, k);
            model.setValueAt(temp, high, k);
        }
        return i + 1;
    }

    //public static void main(String[] args){
    //    SwingUtilities.invokeLater(() -> new PageOne());
    //}

}

