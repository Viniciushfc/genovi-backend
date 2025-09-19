package br.com.genovi.domain.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(unique = true, name = "cpf_cnpj")
    private String cpfCnpj;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "data_admissao")
    private LocalDateTime dataAdmissao;

    @Column(name = "data_recisao")
    private LocalDateTime dataRecisao;

    public Funcionario() {
    }

    public Funcionario(Long id, String nome, String cpfCnpj, String endereco, String telefone, LocalDateTime dataAdmissao, LocalDateTime dataRecisao) {
        this.id = id;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.endereco = endereco;
        this.telefone = telefone;
        this.dataAdmissao = dataAdmissao;
        this.dataRecisao = dataRecisao;
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

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDateTime getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDateTime dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public LocalDateTime getDataRecisao() {
        return dataRecisao;
    }

    public void setDataRecisao(LocalDateTime dataRecisao) {
        this.dataRecisao = dataRecisao;
    }
}
