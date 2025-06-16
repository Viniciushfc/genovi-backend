package br.com.genovi.application.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Vacina")
public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private String fabricante;
    @OneToMany
    private List<Doenca> doencas;
    private boolean doseUnica;
    private String descricao;

    public Vacina() {
    }

    public Vacina(Long id, String nome, String fabricante, List<Doenca> doencas, boolean doseUnica, String descricao) {
        this.id = id;
        this.nome = nome;
        this.fabricante = fabricante;
        this.doencas = doencas;
        this.doseUnica = doseUnica;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public List<Doenca> getDoencas() {
        return doencas;
    }

    public void setDoencas(List<Doenca> doencas) {
        this.doencas = doencas;
    }

    public boolean isDoseUnica() {
        return doseUnica;
    }

    public void setDoseUnica(boolean doseUnica) {
        this.doseUnica = doseUnica;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
