package br.com.chayotte.common.utils;

public class LogUtils {

    public static String maskDocument(String document) {
        if (document == null || document.isBlank()) {
            return document;
        }

        var cleanDocument = document.replaceAll("\\D", "");

        if (cleanDocument.length() == 11) {
            return maskCpf(cleanDocument);
        } else if (cleanDocument.length() == 14) {
            return maskCnpj(cleanDocument);
        }

        return document;
    }

    private static String maskCpf(String cpf) {
        return cpf.substring(0, 3) + "******" + cpf.substring(9);
    }

    private static String maskCnpj(String cnpj) {
        return cnpj.substring(0, 2) + "********" + cnpj.substring(10);
    }
}
