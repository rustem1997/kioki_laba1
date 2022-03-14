package com.company;


import java.util.Locale;
import java.util.Scanner;

public class Column {
    private static char [][] charMatrix;

    public static void columnChiper(String text){
        System.out.println("\n\tСтолбцовый метод\nВведите ключ(строку): ");
        Scanner scanner = new Scanner(System.in);
        String key = scanner.nextLine();
        String upperText = text.toUpperCase(Locale.ROOT);
        String upperKey = key.toUpperCase();
        upperText = upperText.replace(" ", "");

        String encryption = columnEncrypt(upperText, upperKey);
        System.out.println("Шифрование: " + encryption.replace("*", ""));
        System.out.println("Дешифрование: " + columnDecrypt(encryption, upperKey));
    }

    private static int[] GetKeyNumEncrypt(String key) //определение кодов символов в ключе
    {
        char c = (char)2000;
        int[] keyNumArr = new int[key.length()];

        StringBuilder _key = new StringBuilder(key);
        for (int i = 0; i < _key.length(); i++)
        {
            int min = i;
            for (int j = 0; j < _key.length(); j++)
            {
                if (_key.charAt(j) != c)
                {
                    min = j;
                    break;
                }
            }

            for (int j = 0; j < _key.length(); j++)   //определяем символ с минимальным кодом
            {
                if ((int)_key.charAt(j) < (int)_key.charAt(min))
                {
                    min = j;
                }
            }
            keyNumArr[i] = min;
            _key.setCharAt(min, c);
        }
        return keyNumArr;
    }

    private static int[] GetKeyNumDecrypt(String key) //номера символов в ключе относительно алфавита
    {
        int[] keyNumArr = new int[key.length()];
        StringBuilder _key = new StringBuilder(key);

        for (int i = 0; i < _key.length(); i++)
        {
            int min = 0;
            for (int j = 0; j < _key.length(); j++)
            {
                if ((int)_key.charAt(i) > (int)_key.charAt(j))
                {
                    min++;
                }
                else if (((int)_key.charAt(i) == (int)_key.charAt(j)) && (i > j))
                {
                    min++;
                }
            }
            keyNumArr[i] = min;
        }
        return keyNumArr;
    }

    private static String columnEncrypt(String text, String key){

        int col = key.length();
        int row = 0;
        String tempr = "";

        while (tempr.length() < text.length())  //определяем кол-во строк в матрице
        {
            tempr += key;
            row++;
        }

        charMatrix = new char[row][col];   //создаем матрицу

        while (text.length() % col != 0)   //дополняем исходный текст * для полного покрытия матрицы
        {
            text += '*';
        }

        int index = 0;
        int m = 0;
        while (index < text.length())    //заполняем матрицу построчно
        {
            int n = 0;
            while (n < col && index < text.length())
            {
                charMatrix[m][n] = text.charAt(index);
                n++;
                index++;
            }
            m++;
        }

        String resultText = "";
        int[] keyNumArr = GetKeyNumEncrypt(key); //массив номеров символов в ключе
        for (int i = 0; i < keyNumArr.length; i++)  // выводим столбцы по порядку относительно keyNumArr[]
        {
            int number = keyNumArr[i];
            for (int j = 0; j < row; j++)
                resultText += charMatrix[j][number];
        }
        return resultText;
    }

    private static String columnDecrypt(String text, String key){

        int col = key.length();

        int[] keyNumArr = GetKeyNumDecrypt(key); // номера символов в ключе относительно алфавита

        int row = text.length() / col;
        charMatrix = new char[row][col];
        int m = 0;
        int index = 0;
        while (index < text.length())           //заполняем матрицу по столбцам
        {
            int n = 0;
            while (n < text.length() / col)
            {
                charMatrix[n][m] = text.charAt(index);
                n++;
                index++;
            }
            m++;
        }

        char[][] charMatrix2 = new char[row][col];

        for (int i = 0; i < col; i++)  // переставляем столбцы местами
        {
            int number = keyNumArr[i];
            for (int j = 0; j < row; j++)
            {
                charMatrix2[j][i] = charMatrix[j][number];
            }
        }

        String resultText = "";
        for (int i = 0; i < row; i++) //выводим, читая построчно
        {
            for (int j = 0; j < col; j++)
            {
                resultText += charMatrix2[i][j];
            }
        }
        return resultText.replaceAll("\\*", "");
    }
}
