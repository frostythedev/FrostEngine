package me.frostythedev.examples;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ExampleGadget {

    private static String[] parts = new String[]{"a", "b", "c", "d"};

    public static void main(String[] args) {
        System.out.println("Amount Completed: " + getAmountCompleted(new String[]{"a", "d", "b", "c"}));

        System.out.println("Is Completed: " + hasCompleted(new String[]{"a", "d", "b", "c"}));

        System.out.println("Parts Uncompleted: " + Arrays.toString(getUncompletedParts(new String[]{"a", "d", "b", "c"})));

        System.out.println(" ");

        List<String> stringList = Lists.newArrayList();
        stringList.add("Paintball:a;b");

        String[] savedParts = searchStringListForCollectable(stringList, "Paintball");
        System.out.println("Stored Parts: " + Arrays.toString(savedParts));

        if (hasCompleted(savedParts)) {
            System.out.println("Trackable Already Completed.");
        }

        String[] unOwned = getUncompletedParts(savedParts);
        String random = unOwned[ThreadLocalRandom.current().nextInt(unOwned.length)];
        System.out.println("Random Part: " + random);

        String newData = "Paintball:";
        if (savedParts.length == 0) {
            newData += random;
        } else {
            newData += random;
            for (String str : savedParts) {
                System.out.println("Part: " + str);
                newData += ";" + str;
            }
        }

        System.out.println(" ");
        System.out.println("========= After ========");
        System.out.println(newData);

        System.out.println("========= NUMBERS ========");
        System.out.println(getBarStatus(20, 100));
    }

    public static int percent(int currentValue, int maxValue){
        float percent = (float) (((double)currentValue/maxValue) *100);
        return (int)percent;
    }


    public static String getBarStatus(int amount, int total) {
        String bar = "";

        float amountColored = (float) (((double)amount/total) *100);
        System.out.println("Amount colored: " + (int)amountColored);
        double unColoredAmount = 100.0 - amountColored;
        System.out.println("Uncolored colored: " + unColoredAmount);

        for (int i = 0; i < amountColored; i++) {
            bar += "$";
        }
        for (int i = 0; i < unColoredAmount; i++) {
            bar += "#";
        }

        return bar;
    }


    private static String[] searchStringListForCollectable(List<String> list, String name) {
        for (String str : list) {
            if (str.contains(name)) {
                list.remove(str);
                String data = str.split(":")[1];
                return data.split(";");
            }
        }
        return new String[0];
    }

    private static int getAmountCompleted(String[] array) {
        int count = 0;
        List<String> listParts = Lists.newArrayList(parts);
        for (String str : array) {
            if (listParts.contains(str)) {
                count++;
            }
        }
        return count;
    }

    private static boolean hasCompleted(String[] array) {
        return getAmountCompleted(array) == parts.length;
    }

    private static String[] getUncompletedParts(String[] array) {
        if (hasCompleted(array)) {
            return new String[0];
        }

        List<String> unowned = Lists.newArrayList();
        List<String> listParts = Lists.newArrayList(array);

        for (String str : parts) {
            if (!listParts.contains(str)) {
                unowned.add(str);
            }
        }

        return unowned.toArray(new String[unowned.size()]);
    }
}
