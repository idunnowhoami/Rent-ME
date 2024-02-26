package Utilidades;

import java.util.Scanner;

public class Leitura {
    public static Scanner ler = new Scanner(System.in);
    public static int leInt(String mensagem) {
        System.out.println(mensagem);
        return ler.nextInt();
    }
    public static String leStr(String mensagem) {
        System.out.println(mensagem);
        ler = new Scanner(System.in);
        return ler.nextLine();
    }
    public static int leIntPositivo(String mensagem) {
        int valor = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print(mensagem);
                valor = Integer.parseInt(ler.nextLine());

                if (valor <= 0) {
                    System.out.println("O número deve ser positivo!");
                } else {
                    entradaValida = true;
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Insira um número inteiro positivo.");
            }
        }
        return valor;
    }
}
