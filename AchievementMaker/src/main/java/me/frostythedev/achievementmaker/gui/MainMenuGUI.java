package me.frostythedev.achievementmaker.gui;

import me.frostythedev.achievementmaker.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuGUI extends JFrame {

    private JButton databaseSelect;
    private JButton achievementMaker;

    public MainMenuGUI() {
        super(Main.TITLE);

        setLayout(new FlowLayout());

        this.databaseSelect = new JButton("Connect to Database");
        this.databaseSelect.setVisible(true);
        this.databaseSelect.addActionListener(new ACListener());
        this.add(this.databaseSelect);

        this.achievementMaker = new JButton("Go to AchievementMaker");
        this.achievementMaker.setVisible(true);
        this.achievementMaker.addActionListener(new ACListener());
        this.add(this.achievementMaker);
    }

    class ACListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource().equals(MainMenuGUI.this.databaseSelect)) {
                //MainMenuGUI.this.setVisible(false);

                JOptionPane.showMessageDialog(null, "Coming soon!");
               /* DatabaseGUI dcg = new DatabaseGUI();
                dcg.setVisible(true);*/

            } else if (e.getSource().equals(MainMenuGUI.this.achievementMaker)) {
                MainMenuGUI.this.setVisible(false);

                MakerGUI mgui = new MakerGUI();
                mgui.setVisible(true);

            }
        }
    }
}
