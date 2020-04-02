package me.frostythedev.achievementmaker.gui;

import me.frostythedev.achievementmaker.db.Database;
import me.frostythedev.achievementmaker.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatabaseGUI extends JFrame {

    private Database database;

    private JTextField field1;
    private JTextField field2;
    private JTextField field3;
    private JTextField field4;

    private JButton button1;

    private boolean connected;

    public DatabaseGUI() {
        super(Main.TITLE);

        setLayout(new FlowLayout());

        this.field1 = new JTextField("MySQL Host", 16);
        this.field1.setVisible(true);

        this.field2 = new JTextField("MySQL Username", 16);
        this.field2.setVisible(true);

        this.field3 = new JTextField("MySQL Password", 16);
        this.field3.setVisible(true);

        this.field4 = new JTextField("MySQL Database", 16);
        this.field4.setVisible(true);

        this.button1 = new JButton("Connect");
        this.button1.setVisible(true);
        this.button1.addActionListener(new ACHandler());
    }

    public Database getDatabase() {
        return database;
    }

    public JTextField getField1() {
        return field1;
    }

    public JTextField getField2() {
        return field2;
    }

    public JTextField getField3() {
        return field3;
    }

    public JTextField getField4() {
        return field4;
    }

    public JButton getButton1() {
        return button1;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public class ACHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button1) {
                database = new Database(field1.getText(), field4.getText(), field2.getText(), field3.getText());
                if (database.hasConnection()) {
                    setConnected(true);
                }
            }
        }
    }
}
