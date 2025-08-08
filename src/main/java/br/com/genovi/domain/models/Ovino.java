package br.com.genovi.domain.models;

import br.com.genovi.domain.enums.TypeGrauPureza;
import br.com.genovi.domain.enums.TypeSexo;
import br.com.genovi.domain.enums.TypeStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "ovino")
public class Ovino {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, name = "RFID")
    private Long rfid;

    @Column(name = "nome")
    private String nome;

    @Column(name = "raca")
    private String raca;

    @Column(name = "fbb")
    private String fbb;

    @Column(name = "data_nascimento")
    private LocalDateTime dataNascimento;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_criador")
    private Criador criador;

    @Column(name = "tempo_fazenda")
    private int tempoFazenda;

    @Column(name = "grau_pureza")
    private TypeGrauPureza typeGrauPureza;

    @Column(name = "sexo")
    private TypeSexo sexo;

    @Column(name = "peso")
    private Float peso;

    @Column(name = "comportamento")
    private String comportamento;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_ascendencia")
    private Ascendencia ascendencia;

    @Column(name = "status")
    private TypeStatus status;

    public Ovino() {

    }

    public Ovino(Long id, Long rfid, String nome, String raca, String fbb, LocalDateTime dataNascimento, Criador criador, int tempoFazenda, TypeGrauPureza typeGrauPureza, TypeSexo sexo, Float peso, String comportamento, Ascendencia ascendencia, TypeStatus status) {
        this.id = id;
        this.rfid = rfid;
        this.nome = nome;
        this.raca = raca;
        this.fbb = fbb;
        this.dataNascimento = dataNascimento;
        this.criador = criador;
        this.tempoFazenda = tempoFazenda;
        this.typeGrauPureza = typeGrauPureza;
        this.sexo = sexo;
        this.peso = peso;
        this.comportamento = comportamento;
        this.ascendencia = ascendencia;
        this.status = status;
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

    public int getTempoFazenda() {
        return tempoFazenda;
    }

    public void setTempoFazenda(int tempoFazendo) {
        this.tempoFazenda = tempoFazendo;
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
