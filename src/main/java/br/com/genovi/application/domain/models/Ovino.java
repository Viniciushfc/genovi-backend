package br.com.genovi.application.domain.models;

import br.com.genovi.application.domain.enums.TypeGrauPureza;
import br.com.genovi.application.domain.enums.TypeSexo;
import br.com.genovi.application.domain.enums.TypeStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "Ovino")
public class Ovino {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private Long rfid;
    private boolean ativo;
    private String nome;
    private String raca;
    private String fbb;
    private LocalDateTime dataNascimento;
    @ManyToOne(optional = true)
    private Criador criador;
    private int tempoFazendo;
    private TypeGrauPureza typeGrauPureza;
    private TypeSexo sexo;
    private Float peso;
    private String comportamento;
    @ManyToOne(optional = true)
    private Ascendencia ascendencia;
    private TypeStatus status;

    public Ovino() {

    }

    public Ovino(Long id, Long rfid, boolean ativo, String nome, String raca, String fbb, LocalDateTime dataNascimento, Criador criador, int tempoFazendo, TypeGrauPureza typeGrauPureza, TypeSexo sexo, Float peso, String comportamento, Ascendencia ascendencia, TypeStatus status) {
        this.id = id;
        this.rfid = rfid;
        this.ativo = ativo;
        this.nome = nome;
        this.raca = raca;
        this.fbb = fbb;
        this.dataNascimento = dataNascimento;
        this.criador = criador;
        this.tempoFazendo = tempoFazendo;
        this.typeGrauPureza = typeGrauPureza;
        this.sexo = sexo;
        this.peso = peso;
        this.comportamento = comportamento;
        this.ascendencia = ascendencia;
        this.status = status;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRfid() {
        return rfid;
    }

    public void setRfid(Long rfid) {
        this.rfid = rfid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getFbb() {
        return fbb;
    }

    public void setFbb(String fbb) {
        this.fbb = fbb;
    }

    public LocalDateTime getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDateTime dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Criador getCriador() {
        return criador;
    }

    public void setCriador(Criador criador) {
        this.criador = criador;
    }

    public int getTempoFazendo() {
        return tempoFazendo;
    }

    public void setTempoFazendo(int tempoFazendo) {
        this.tempoFazendo = tempoFazendo;
    }

    public TypeGrauPureza getTypeGrauPureza() {
        return typeGrauPureza;
    }

    public void setTypeGrauPureza(TypeGrauPureza typeGrauPureza) {
        this.typeGrauPureza = typeGrauPureza;
    }

    public TypeSexo getSexo() {
        return sexo;
    }

    public void setSexo(TypeSexo sexo) {
        this.sexo = sexo;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public String getComportamento() {
        return comportamento;
    }

    public void setComportamento(String comportamento) {
        this.comportamento = comportamento;
    }

    public Ascendencia getAscendencia() {
        return ascendencia;
    }

    public void setAscendencia(Ascendencia ascendencia) {
        this.ascendencia = ascendencia;
    }

    public TypeStatus getStatus() {
        return status;
    }

    public void setStatus(TypeStatus status) {
        this.status = status;
    }
}
