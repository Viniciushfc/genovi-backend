package br.com.genovi.domain.enums;

public interface IEnum<K, V> {
    K getKey();

    V getValue();
}
