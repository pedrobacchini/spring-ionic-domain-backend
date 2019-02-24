package com.github.pedrobacchini.springionicdomain.service.validation;

class Utils {

    // CPF
    private static final int[] weightCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    // CNPJ
    private static final int[] weightCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int calculate(final String str, final int[] weight) {
        int sum = 0;
        for (int i = str.length() - 1, digit; i >= 0; i--) {
            digit = Integer.parseInt(str.substring(i, i + 1));
            sum += digit * weight[weight.length - str.length() + i];
        }
        sum = 11 - sum % 11;
        return sum > 9 ? 0 : sum;
    }

    /**
     * Valida CPF
     *
     * @param cpf representação do cpf a ser validado
     * @return retorna se o cpf e valido ou não
     */
    static boolean isValidCPF(final String cpf) {
        if ((cpf == null) || (cpf.length() != 11) || cpf.matches(cpf.charAt(0) + "{11}")) return false;

        final Integer digit1 = calculate(cpf.substring(0, 9), weightCPF);
        final Integer digit2 = calculate(cpf.substring(0, 9) + digit1, weightCPF);
        return cpf.equals(cpf.substring(0, 9) + digit1.toString() + digit2.toString());
    }

    /**
     * Valida CNPJ
     *
     * @param cnpj representação do cnpj a ser validado
     * @return retorna se o cnpj e valido ou não
     */
    static boolean isValidCNPJ(final String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14) || cnpj.matches(cnpj.charAt(0) + "{14}")) return false;

        final Integer digit1 = calculate(cnpj.substring(0, 12), weightCNPJ);
        final Integer digit2 = calculate(cnpj.substring(0, 12) + digit1, weightCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digit1.toString() + digit2.toString());
    }
}
