package gui;

import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.Menu;

class CloseWindow extends WindowAdapter {

    public void windowClosing(WindowEvent we) {
        System.exit(0);
    }
}

class Cal  implements ActionListener {

    TextField tf;
    Frame f = new Frame();
    TextArea activityArea;
    Button[] buttons;
    Panel mainPanel, calcPanel, infoPanel, p1;
    CardLayout cardLayout;
    String fv, sv, op;
    Double fdv, sdv, tot, memory;
    List<String> history;

    Cal() {
        memory = 0.0;
        history = new ArrayList<>();
        f.addWindowListener(new CloseWindow());
        Color Blue = new Color(51, 153, 255);
        Color DarkBlue = new Color(0, 102, 204);
        f.setBackground(Blue);
        f.setTitle("Calculator");
        f.setResizable(true);
        f.setSize(350, 500);
        f.setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\User\\Downloads\\—Pngtree—vector calculator icon_3782871.png");
        f.setIconImage(icon);

        cardLayout = new CardLayout();
        mainPanel = new Panel(cardLayout);
        f.add(mainPanel);

        Font f1 = new Font("Californian FB", Font.PLAIN, 20);
        Font f3 = new Font("Californian FB", Font.PLAIN, 15);

        p1 = new Panel(new BorderLayout());
        calcPanel = new Panel();
        GridLayout g1 = new GridLayout(6, 5, 2, 2);
        calcPanel.setLayout(g1);
        Color Blue2 = new Color(51, 153, 255);
        calcPanel.setBackground(Blue2);

        activityArea = new TextArea(2, 20);
        activityArea.setFont(f3);
        activityArea.setEditable(false);
        activityArea.setForeground(Color.WHITE);
        activityArea.setBackground(Blue2);

        tf = new TextField(20);
        tf.setFont(f3);
        tf.setEditable(false);
        tf.setForeground(Color.black);
        tf.setBackground(Color.white);
        tf.setText("0");

        p1.add(activityArea, BorderLayout.NORTH);
        p1.add(tf, BorderLayout.SOUTH);

        String[] buttonLabels = {
            "MC", "MR", "M+", "M-", "MS", "CE", "C", "DLT", "H", "%",
            "7", "8", "9", "/", "²√x", "4", "5", "6", "*", "x²",
            "1", "2", "3", "-", "1/x", "+/-", "0", ".", "+", "="
        };

        buttons = new Button[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new Button(buttonLabels[i]);
            buttons[i].setBackground(Color.CYAN);
            buttons[i].setFont(f1);
            buttons[i].addActionListener(this);
            calcPanel.add(buttons[i]);
        }

        // Set "=" button to DarkBlue and remove hover effect
        for (Button button : buttons) {
            if (button.getLabel().equals("=")) {
                button.setBackground(DarkBlue);
            }
        }

        Panel calcContainer = new Panel(new BorderLayout());
        calcContainer.add(p1, BorderLayout.NORTH);
        calcContainer.add(calcPanel, BorderLayout.CENTER);

        mainPanel.add(calcContainer, "Calculator");

        MenuBar mb = new MenuBar();

        Menu viewMenu = new Menu("View");
        MenuItem standardItem = new MenuItem("Standard");
        standardItem.addActionListener(this);
        viewMenu.add(standardItem);

        Menu editMenu = new Menu("Edit");
        MenuItem copyItem = new MenuItem("Copy");
        copyItem.addActionListener(this);
        MenuItem pasteItem = new MenuItem("Paste");
        pasteItem.addActionListener(this);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        Menu helpMenu = new Menu("Help");
        MenuItem viewHelpItem = new MenuItem("View Help");
        viewHelpItem.addActionListener(this);
        MenuItem aboutItem = new MenuItem("About Calculator");
        aboutItem.addActionListener(this);
        helpMenu.add(viewHelpItem);
        helpMenu.add(aboutItem);

        Menu historyMenu = new Menu("History");
        MenuItem viewHistoryItem = new MenuItem("View History");
        viewHistoryItem.addActionListener(this);
        MenuItem clearHistoryItem = new MenuItem("Clear History");
        clearHistoryItem.addActionListener(this);
        MenuItem printHistoryItem = new MenuItem("Print History as PDF");
        printHistoryItem.addActionListener(this);
        historyMenu.add(viewHistoryItem);
        historyMenu.add(clearHistoryItem);
        historyMenu.add(printHistoryItem);

        mb.add(viewMenu);
        mb.add(editMenu);
        mb.add(historyMenu);
        mb.add(helpMenu);

        f.setMenuBar(mb);

        infoPanel = new Panel(new BorderLayout());
        infoPanel.setBackground(Blue2);
        mainPanel.add(infoPanel, "Info");

        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent d) {
        String command = d.getActionCommand();

        if (command.equals("View Help")) {
            showInfo("Help", "Welcome to the Calculator Help!\n"
                    + "\n"
                    + "- MC -: Clears the memory.\n"
                    + "- MR -: Recalls the memory.\n"
                    + "- M+ -: Adds the current value to memory.\n"
                    + "- M- -: Subtracts the current value from memory.\n"
                    + "- MS -: Stores the current value in memory.\n"
                    + "- CE -: Clears the current entry.\n"
                    + "- C -: Clears all entries.\n"
                    + "- DLT -: Deletes the last digit.\n"
                    + "- % -: Calculates the percentage.\n"
                    + "- H -: Opens the calculation history.\n"
                    + "- +/- -: Toggles the sign of the current number.\n"
                    + "- ²√x -: Calculates the square root.\n"
                    + "- x² -: Squares the current number.\n"
                    + "- 1/x -: Calculates the reciprocal.\n"
                    + "- + -: Addition operation.\n"
                    + "- - -: Subtraction operation.\n"
                    + "- × -: Multiplication operation.\n"
                    + "- ÷ -: Division operation.\n"
                    + "- = -: Computes the result.\n"
                    + "\n"
                    + "For more assistance, please or contact support.\n"
                    + "Email - dhanikagimhana22@gmail.com");
        } else if (command.equals("About Calculator")) {
            showInfo("About", "Calculator Version 1.0\n"
                    + "\n"
                    + "Developed by: Dhanika Jagodaarachchi.\n"
                    + "\n"
                    + "This calculator application provides basic arithmetic operations, memory functions,\n"
                    + "and additional features such as percentage calculation, square root, \n"
                    + "square, reciprocal, and a toggle for positive/negative values. It also includes \n"
                    + "a history view and the ability to print the history to a PDF.\n"
                    + "\n"
                    + "2024 Dhanika. All rights reserved.\n"
                    + "\n"
                    + "Enjoy using the Calculator!");
        } else if (command.equals("Standard")) {
            hideInfo();
        } else if (command.equals("Copy")) {
            StringSelection stringSelection = new StringSelection(tf.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        } else if (command.equals("Paste")) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            try {
                tf.setText((String) clipboard.getData(DataFlavor.stringFlavor));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (command.equals("H")) {
            showHistory();
        } else if (command.equals("View History")) {
            showHistory();
        } else if (command.equals("Clear History")) {
            clearHistory();
        } else if (command.equals("Print History as PDF")) {
            printHistoryAsPDF();
        } else {
            handleCalculatorInput(command);
        }
    }

    private void handleCalculatorInput(String command) {
        try {
            if (tf.getText().equals("0")) {
                tf.setText("");
            }

            if (command.equals("MC")) {
                memory = 0.0;
            } else if (command.equals("MR")) {
                tf.setText(memory.toString());
            } else if (command.equals("M+")) {
                memory += Double.parseDouble(tf.getText());
            } else if (command.equals("M-")) {
                memory -= Double.parseDouble(tf.getText());
            } else if (command.equals("MS")) {
                memory = Double.parseDouble(tf.getText());
            } else if (command.equals("x²")) {
                fv = tf.getText();
                fdv = Double.parseDouble(fv);
                tot = fdv * fdv;
                tf.setText(tot + "");
                addToHistory(fv + " ² = " + tot);
                updateActivity();
            } else if (command.equals("²√x")) {
                fv = tf.getText();
                fdv = Double.parseDouble(fv);
                if (fdv < 0) {
                    showError("Cannot calculate square root of a negative number!");
                } else {
                    tot = Math.sqrt(fdv);
                    tf.setText(tot + "");
                    addToHistory("√" + fv + " = " + tot);
                }
            } else if (command.equals("1/x")) {
                fv = tf.getText();
                fdv = Double.parseDouble(fv);
                if (fdv == 0) {
                    showError("Cannot divide by zero!");
                } else {
                    tot = 1 / fdv;
                    tf.setText(tot + "");
                    addToHistory("1/" + fv + " = " + tot);
                }
            } else if (command.equals("%")) {
                fv = tf.getText();
                fdv = Double.parseDouble(fv);
                tot = fdv / 100;
                tf.setText(tot + "");
                addToHistory(fv + " % = " + tot);
            } else if (command.equals("7")) {
                tf.setText(tf.getText() + "7");
            } else if (command.equals("8")) {
                tf.setText(tf.getText() + "8");
            } else if (command.equals("9")) {
                tf.setText(tf.getText() + "9");
            } else if (command.equals("/")) {
                fv = tf.getText();
                op = "/";
                tf.setText("");
                updateActivity();
            } else if (command.equals("C")) {
                tf.setText("0");
                fv = sv = op = null;
                updateActivity();
            } else if (command.equals("4")) {
                tf.setText(tf.getText() + "4");
            } else if (command.equals("5")) {
                tf.setText(tf.getText() + "5");
            } else if (command.equals("6")) {
                tf.setText(tf.getText() + "6");
            } else if (command.equals("*")) {
                fv = tf.getText();
                op = "*";
                tf.setText("");
                updateActivity();
            } else if (command.equals("CE")) {
                tf.setText("0");
            } else if (command.equals("1")) {
                tf.setText(tf.getText() + "1");
            } else if (command.equals("2")) {
                tf.setText(tf.getText() + "2");
            } else if (command.equals("3")) {
                tf.setText(tf.getText() + "3");
            } else if (command.equals("-")) {
                fv = tf.getText();
                op = "-";
                tf.setText("");
                updateActivity();
            } else if (command.equals("DLT")) {
                String text = tf.getText();
                if (text.length() > 0) {
                    tf.setText(text.substring(0, text.length() - 1));
                }
                if (tf.getText().isEmpty()) {
                    tf.setText("0");
                }
                updateActivity();
            } else if (command.equals("+/-")) {
                if (tf.getText().length() > 0 && !tf.getText().equals("0")) {
                    tf.setText(tf.getText().startsWith("-") ? tf.getText().substring(1) : "-" + tf.getText());
                }
                updateActivity();
            } else if (command.equals("0")) {
                tf.setText(tf.getText() + "0");
            } else if (command.equals(".")) {
                if (!tf.getText().contains(".")) {
                    tf.setText(tf.getText() + ".");
                }
            } else if (command.equals("+")) {
                fv = tf.getText();
                op = "+";
                tf.setText("");
                updateActivity();
            } else if (command.equals("=")) {
                sv = tf.getText();
                fdv = Double.parseDouble(fv);
                sdv = Double.parseDouble(sv);
                if (op.equals("+")) {
                    tot = fdv + sdv;
                } else if (op.equals("-")) {
                    tot = fdv - sdv;
                } else if (op.equals("*")) {
                    tot = fdv * sdv;
                } else if (op.equals("/")) {
                    if (sdv == 0) {
                        showError("Cannot divide by zero!");
                        return;
                    } else {
                        tot = fdv / sdv;
                    }
                }
                tf.setText(tot + "");
                addToHistory(fv + " " + op + " " + sv + " = " + tot);
                updateActivity();
            }
        } catch (NumberFormatException e) {
            showError("Invalid input");
        }
    }

    private void updateActivity() {
        String displayFV = (fv != null && !fv.isEmpty()) ? fv : "0";
        String displayOp = (op != null) ? op : "";
        String displaySV = (sv != null && !sv.isEmpty()) ? sv : "";
        activityArea.setText(displayFV + " " + displayOp + " " + displaySV);
    }

    private void showError(String message) {
        tf.setText("Error: " + message);
        fv = sv = op = null;
    }

    private void addToHistory(String entry) {
        history.add(entry);
    }

    private void showInfo(String title, String info) {
        Label titleLabel = new Label(title, Label.CENTER);
        TextArea infoLabel = new TextArea(info, 10, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 20));
        infoLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        infoLabel.setEditable(false);

        infoPanel.removeAll();
        infoPanel.add(titleLabel, BorderLayout.NORTH);
        infoPanel.add(infoLabel, BorderLayout.CENTER);

        cardLayout.show(mainPanel, "Info");
        mainPanel.validate();
        mainPanel.repaint();
    }

    private void showHistory() {
        TextArea historyTextArea = new TextArea();
        for (String entry : history) {
            historyTextArea.append(entry + "\n");
        }
        historyTextArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        historyTextArea.setEditable(false);

        infoPanel.removeAll();
        infoPanel.add(historyTextArea, BorderLayout.CENTER);

        cardLayout.show(mainPanel, "Info");
        mainPanel.validate();
        mainPanel.repaint();
    }

    private void clearHistory() {
        history.clear();
        showHistory();
    }

    //Need to be added the "itextpdf-5.5.13.2.jar" file to the library.
    private void printHistoryAsPDF() {
        if (history.isEmpty()) {
            showError("History is empty, nothing to print!");
            return;
        }

        Document document = new Document();
        String fileName = "CalculatorHistory.pdf";
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            for (String entry : history) {
                document.add(new Paragraph(entry));
            }
            document.close();

            // Open the PDF file after creation
            File file = new File(fileName);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
                showInfo("Success", "History has been printed to " + fileName);
            } else {
                showError("Failed to create PDF file!");
            }
        } catch (DocumentException | IOException e) {
            showError("Error printing history: " + e.getMessage());
        }

    }

    private void hideInfo() {
        cardLayout.show(mainPanel, "Calculator");
        mainPanel.validate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        new Cal();
    }

    private void add(Menu viewMenu) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void mbadd(Menu viewMenu) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
