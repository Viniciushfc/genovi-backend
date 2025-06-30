package br.com.genovi.domain.utils;

public class CpfCnpjUtils {

    //Validação CPF
    public static boolean isCpf(String cpf) {
        if (cpf == null) return false;

        cpf = cpf.replaceAll("[^\\d]", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int dig1 = 11 - (soma % 11);
            dig1 = (dig1 >= 10) ? 0 : dig1;

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int dig2 = 11 - (soma % 11);
            dig2 = (dig2 >= 10) ? 0 : dig2;

            return dig1 == Character.getNumericValue(cpf.charAt(9)) &&
                    dig2 == Character.getNumericValue(cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }

    //Validação CNPJ
    public static boolean isCnpj(String cnpj) {
        if (cnpj == null) return false;

        cnpj = cnpj.replaceAll("[^\\d]", "");

        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * pesos1[i];
            }
            int dig1 = soma % 11;
            dig1 = (dig1 < 2) ? 0 : 11 - dig1;

            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
            }
            int dig2 = soma % 11;
            dig2 = (dig2 < 2) ? 0 : 11 - dig2;

            return dig1 == Character.getNumericValue(cnpj.charAt(12)) &&
                    dig2 == Character.getNumericValue(cnpj.charAt(13));
        } catch (Exception e) {
            return false;
        }
    }

    //Utilização para as duas logicas
    public static boolean isCpfOrCnpjValido(String cpfCnpj) {
        return isCpf(cpfCnpj) || isCnpj(cpfCnpj);
    }
}

