package com.company;

import java.util.Scanner;

import static com.company.Column.*;
import static com.company.RailFence.*;
import static com.company.RotatingLattice.*;
import static com.company.Vigener.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строку:");
        String text = scanner.nextLine();
        railFenceChiper(text);
        columnChiper(text);
        rotatingLatticeChiper(text);
        viginerChiper(text);
    }
}
