package com.company;

import java.util.Locale;
import java.util.Scanner;

public class RotatingLattice {

    private static char [][] charMatrix;

    public static void rotatingLatticeChiper(String text){
        System.out.println("\n\tМетод поворачивающейся решетки\nВведите размер решетки (четное число): ");
        Scanner scanner = new Scanner(System.in);
        int n;
        boolean flag = true;
        while (flag)
        {
            n = scanner.nextInt();
            if (n % 2 != 0 || n*n < text.length())
            {
                System.out.println("Данные введены неверно!\nПовторите попытку: ");
            }
            else
            {
                flag = false;
                String upperText = text.toUpperCase(Locale.ROOT);
                upperText = upperText.replace(" ", "");
                int[][] lattice = latticeInput(n);

                String encryption = rotatingLatticeEncrypt(upperText, n, lattice);
                System.out.println("Шифрование: " + encryption);
                System.out.println("Дешифрование: " + rotatingLatticeDecrypt(encryption, n, lattice));
            }
        }
    }

    private static int[][] rotateMatrix (int[][] matrix) {
        int SIDE = matrix.length;
        int[][] resMatrix = new int[SIDE][SIDE];

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                resMatrix[i][j] = matrix[SIDE - j - 1][i];
            }
        }
        return resMatrix;
    }

    private static char[][] rotateMatrix (char[][] matrix) {
        int SIDE = matrix.length;
        char[][] resMatrix = new char[SIDE][SIDE];

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                resMatrix[i][j] = matrix[SIDE - j - 1][i];
            }
        }
        return resMatrix;
    }

    private static int[][] latticeInput(int n) {
        int boolLattice[][] = new int[n][n];
        int numbLattice[][] = new int[n][n];
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < n; i++)                 //Инициализация матриц
            for (int j = 0; j < n; j++)
            {
                boolLattice[i][j] = 0 ;
                numbLattice[i][j] = 0 ;
            }

        for (int turnCount = 0; turnCount < 4 ; turnCount++) {    //Заполнение матрицы цифрами
            int number = 1 ;
            for (int i = 0; i < n / 2; i++)
                for (int j = 0; j < n / 2; j++) {
                    numbLattice[i][j] = number;
                    number++ ;
                }
            numbLattice = rotateMatrix(numbLattice);
        }

        for (int i = 0; i < n; i++) {                           //Вывод цифровой матрицы
            for (int j = 0; j < n; j++) {
                System.out.print(numbLattice[i][j] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < (n/2)*(n/2); i++) {                 //"Вырезание отверстий" в логической матрице
            boolean flag = true;
            while (flag)
            {
                System.out.println("Введите координаты отверстия для " + (i+1) + " (0-3):");
                System.out.print("x: ");
                int x = scanner.nextInt();
                System.out.print("y: ");
                int y = scanner.nextInt() ;
                if (numbLattice[x][y] == (i + 1))
                {
                    boolLattice[x][y] = 1;
                    flag = false;
                }
                else {
                    System.out.println("Данные введены неверно!");
                }
            }
        }
        return boolLattice;
    }

    private static String rotatingLatticeEncrypt(String text, int n, int[][] lattice){

        charMatrix = new char[n][n];

        for (int i = 0; i < n ; i++)                            //Заполнение матрицы рандомными символами
            for(int j = 0 ; j < n ; j++)
                charMatrix[i][j] = (char) ((int)(Math.random() * 26) + 'A') ;

        int index = 0;

        for (int turnCount = 0; turnCount < 4; turnCount++){        //Заполнение матрицы символами исходной строки
            if (index <= text.length())
            {
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++)
                    {
                        if (lattice[i][j] == 1)
                        {
                            if (index < text.length()) {
                                charMatrix[i][j] = text.charAt(index);
                            }
                            if (index == text.length()) {
                                charMatrix[i][j] = '0';
                            }
                            index++ ;
                        }
                    }
            }
            lattice = rotateMatrix(lattice);                //Вращение решетки
        }

        String resultText = "";                             //Чтение матрицы по строкам
        for (int i = 0 ; i < n; i++)
            for (int j = 0 ; j < n; j++)
                resultText = resultText + charMatrix[i][j];

        return resultText;
    }


    private static String rotatingLatticeDecrypt(String text, int n, int[][] lattice){

        charMatrix = new char[n][n];

        int index = 0;
        for (int i = 0; i < n; i++)                         //Заполнение матрицы по строкам
            for (int j = 0; j < n; j++)
            {
                charMatrix[i][j] = text.charAt(index);
                index++;
            }

        String resultText = "";

        for (int turnCount = 0; turnCount < 4; turnCount++)     //Чтение исходной строки с помощью решетки
        {
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                {
                    if (lattice[i][j] == 1)
                    {
                        resultText = resultText + charMatrix[i][j];
                    }
                }
            lattice = rotateMatrix(lattice);                //Вращение решетки
        }

        return resultText.substring(0,resultText.indexOf('0'));
    }
}