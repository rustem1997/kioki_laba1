package com.company;

import java.util.Locale;
import java.util.Scanner;

public class Vigener {

    private static final String ABC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void viginerChiper(String text){
        System.out.println("\n\tШифр Вижинера: \nВведите ключ(строку): ");
        Scanner scanner = new Scanner(System.in);
        String key = scanner.nextLine();
        String upperText = text.toUpperCase(Locale.ROOT);
        String upperKey = key.toUpperCase();
        upperText = upperText.replace(" ", "");

        String encryption = viginerEncrypt(upperText, upperKey);
        System.out.println("Шифрование: " + encryption);
        System.out.println("Дешифрование: " + viginerDecrypt(encryption, upperKey));
    }

    private static String GenerateSecretKeyRelativeToInputString(String text, String key) //получение самогенерирующегося ключа
    {
        StringBuilder result = new StringBuilder();

        String inputStringWithoutSpaces = text.replace(" ", "");
        for (int i = 0; i < text.length(); i++)
        {
            if (i >= key.length())
            {
                int index = (i - key.length()) % inputStringWithoutSpaces.length();
                result.append(inputStringWithoutSpaces.charAt(index));
            }
            else
            {
                result.append(key.charAt(i));
            }

        }
        return result.toString();
    }

    private static int GetIndexRelativeToABC(char character){  //Получение индекса символа исходного текста
        return ABC.indexOf(character);
    }

    private static char GetCharFromABCByIndex(int index){   //Получение индекса символа сгенерированного ключа
        return ABC.charAt(index);
    }

    private static String viginerEncrypt(String text, String key){

        StringBuilder resultText = new StringBuilder();

        int abcLength = ABC.length();

        String newKey = GenerateSecretKeyRelativeToInputString(text, key);  //получение самогенерирующегося ключа

        for (int i = 0; i < text.length(); i++)
        {
            int p = GetIndexRelativeToABC(text.charAt(i));                 //Получение индекса символа исходного текста
            int k = GetIndexRelativeToABC(newKey.charAt(i));                    //Получение индекса символа сгенерированного ключа
            int charIndex = (p + k) % abcLength;
            resultText.append(GetCharFromABCByIndex(charIndex));                    //Получение символа шифротекста
        }

        return resultText.toString();
    }

    private static String viginerDecrypt(String text, String key)
    {
        StringBuilder resultText = new StringBuilder();
        int abcLength = ABC.length();
        String upperText = text.toUpperCase(Locale.ROOT);
        String upperKey = key.toUpperCase();
        String _key = "";
        for (int i = 0; i < upperText.length(); i++)
        {
            int c = GetIndexRelativeToABC(upperText.charAt(i));   //Получение индекса символа шифротекста
            int k;
            if (i >= upperKey.length())
            {
                k = GetIndexRelativeToABC(resultText.charAt((i - upperKey.length())));
            }
            else
            {
                k = GetIndexRelativeToABC(upperKey.charAt(i));
            }
            _key+= GetCharFromABCByIndex(k);
            int charIndex = (c - k + abcLength) % abcLength;
            resultText.append(GetCharFromABCByIndex(charIndex));
        }
        return resultText.toString();
    }
}
