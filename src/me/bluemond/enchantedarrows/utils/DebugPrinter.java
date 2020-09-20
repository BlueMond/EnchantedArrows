package me.bluemond.enchantedarrows.utils;

public class DebugPrinter {

    private static final boolean isDebugging = true;

    public static void print(String output){
        if(isDebugging){
            System.out.println("[EnchantedArrows.DEBUG] " + output);
        }
    }
}
