package Utilidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static Utilidades.Leitura.ler;

public class ValidarData {
    public LocalDate LerData2() {
        try {
            LocalDate data;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            data = LocalDate.parse(ler.nextLine().trim(), formatter);
            if (data.isAfter(LocalDate.now())) {
                System.out.println("Data invalida, insira uma data anterior a hoje (" + LocalDate.now().format(formatter) + "):");
                return LerData2();
            }
            return data;
        } catch (Exception ex) {
            System.out.println("Data invalida, insira uma data com o formato (dd/MM/aaaa):");
            return LerData2();
        }
    }
}

