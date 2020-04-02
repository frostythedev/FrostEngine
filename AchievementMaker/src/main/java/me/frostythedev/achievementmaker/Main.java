package me.frostythedev.achievementmaker;

import me.frostythedev.achievementmaker.gui.MainMenuGUI;

import javax.swing.*;

public class Main {

    public static String TITLE = "FrostEngine Achievement Maker v1.0.0";

    public static void main(String[] args){
        MainMenuGUI gui = new MainMenuGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(640, 240);
        gui.setVisible(true);

    }
}
