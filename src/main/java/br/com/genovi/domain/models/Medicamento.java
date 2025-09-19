package br.com.genovi.domain.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "fabricante")
    private String fabricante;

    @ManyToMany
    @JoinTable(
            name = "medicamento_doenca",
            joinColumns = @JoinColumn(name = "medicamento_id"),
            inverseJoinColumns = @JoinColumn(name = "doenca_id")
    )
    private List<Doenca> doencas;

    @Column(name = "doce_unica")
    private boolean doceUnica;

    @Column(name = "quantidade_doses")
    private Integer quantidadeDoses;

    @Column(name = "is_vacina")
    private boolean isVacina;

    public Medicamento() {
    }

    public Medicamento(Long id, String nome, String fabricante, List<Doenca> doencas, boolean doceUnica, Integer quantidadeDoses, boolean isVacina) {
        this.id = id;
        this.nome = nome;
        this.fabricante = fabricante;
        this.doencas = doencas;
        this.doceUnica = doceUnica;
        this.quantidadeDoses = quantidadeDoses;
        this.isVacina = isVacina;
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

    public boolean isDoceUnica() {
        return doceUnica;
    }

    public void setDoceUnica(boolean doceUnica) {
        this.doceUnica = doceUnica;
    }

    public Integer getQuantidadeDoses() {
        return quantidadeDoses;
    }

    public void setQuantidadeDoses(Integer quantidadeDoses) {
        this.quantidadeDoses = quantidadeDoses;
    }

    public boolean isVacina() {
        return isVacina;
    }

    public void setVacina(boolean vacina) {
        isVacina = vacina;
    }
}
