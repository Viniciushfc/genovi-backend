package br.com.genovi.domain.utils;

public class StringUtils {

    /**
     * Retorna o toString() de um objeto, ou uma string vazia caso seja null.
     */
    public static String safeToString(Object obj) {
        return obj != null ? obj.toString() : "";
    }
}