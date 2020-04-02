package me.frostythedev.achievementmaker.gui;

import me.frostythedev.achievementmaker.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MakerGUI extends JFrame {

    private JTextField name;
    private JCheckBox enabled;
    private JTextField description;
    private JTextField output;
    private JComboBox types;

   private String[] ach_types = new String[]{"GENERAL", "LOBBY", "PVP"};

    private JButton create;

    public MakerGUI() {
        super(Main.TITLE);

        setLayout(new FlowLayout());
        setSize(640, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.name = new JTextField("Achievement Name", 16);
        this.name.setVisible(true);
        this.add(name);

        this.enabled = new JCheckBox("Achievement Enabled");
        this.enabled.setVisible(true);
        this.add(enabled);

        this.description = new JTextField("Achievement Description", 16);
        this.description.setVisible(true);
        this.add(description);

        this.output = new JTextField("Resulting MySQL code", 16);
        this.output.setVisible(true);
        this.output.setEditable(false);
        this.add(output);

        this.types = new JComboBox(ach_types);
        this.add(types);

        this.create = new JButton("Parse");
        this.create.setVisible(true);
        this.add(create);
        this.create.addActionListener(new ACHandler());
    }

    public static int higest(int[] array){
        int highest = 0;
        for(int i = 0; i < array.length; i++){
            if(array[i] > highest){
                highest = array[i];
            }
        }

      //  Math.

        return highest;
    }

    public static int lowest(int[] array){
        int lowest = 0;
        for(int i = 0; i < array.length; i++){
            if(array[i] < lowest){
                lowest = array[i];
            }
        }

        return lowest;
    }

    class ACHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == create) {
                MakerGUI.this.output.setText(MakerGUI.this.name.getText() + " this is the name");
            }
        }
    }
}
