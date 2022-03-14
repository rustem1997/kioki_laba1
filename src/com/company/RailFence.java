package com.company;

import java.util.Locale;
import java.util.Scanner;

public class RailFence {

    private static char [][] charMatrix;

    public static void railFenceChiper(String text){
        System.out.println("\n\tМетод \"железнодорожной изгороди\"\nВведите ключ(число): ");
        Scanner scanner = new Scanner(System.in);
        int key = scanner.nextInt();
        scanner.nextLine();
        String upperText = text.toUpperCase(Locale.ROOT);
        upperText = upperText.replace(" ", "");

        String encryption = encryptRailFence(upperText, key);
        System.out.println("Шифрование: " + encryption);
        System.out.println("Дешифрование: " + decryptRailFence(encryption, key));
    }

    private static String encryptRailFence(String text, int key){
        if (key < 1) return null;
        if (key == 1) return text;
        charMatrix = new char[key][text.length()];

        boolean dir_down = false;
        int row = 0, col = 0;

        for (int i = 0; i < key; i++)               //Инициализация матрицы
        {
            for (int j = 0; j < text.length(); j++) {
                charMatrix[i][j] = '\n';
            }
        }

        for (int i = 0; i < text.length(); i++){        //Построение железнодорожной изгороди
            if (row == 0 || row == key-1)
                dir_down = !dir_down;
            charMatrix[row][col++] = text.charAt(i);
            if (dir_down)
                row++;
            else
                row--;
        }

        String resultText = "";                         //Чтение матрицы по строкам
        for (int i = 0; i < key; i++)
            for (int j = 0; j < text.length(); j++){
                if (charMatrix[i][j] != '\n')
                    resultText += charMatrix[i][j];
            }
        return resultText;
    }

    private static String decryptRailFence(String text, int key){
        if (key < 1) return null;
        if (key == 1) return text;
        charMatrix = new char[key][text.length()];

        boolean dir_down = false;
        int row = 0, col = 0;

        for (int i = 0; i < text.length(); i++)      //Построение железнодорожной изгороди
        {
            if (row == 0 || row == key-1)
                dir_down = !dir_down;
            charMatrix[row][col++] = '*';
            if (dir_down)
                row++;
            else
                row--;
        }

        int ind = 0;
        for (int i = 0; i < key; i++)               //Заполнение железнодорожной изгороди символами шифротекста по строкам
        {
            for (int j = 0; j < text.length(); j++) {
                if (charMatrix[i][j] == '*')
                    charMatrix[i][j] = text.charAt(ind++);
            }
        }

        String resultText = "";
            row = 0;
            col = 0;
        for (int i = 0; i < text.length(); i++)     //Чтение матрицы
        {
            if (row == 0)
                dir_down = true;
            if (row == key - 1)
                dir_down = false;
            resultText += charMatrix[row][col++];
            if (dir_down)
                row++;
            else
                row--;
        }
        return resultText;
    }
}
