package br.com.genovi.domain.models;

import br.com.genovi.domain.enums.TypeGrauPureza;
import br.com.genovi.domain.enums.TypeRaca;
import br.com.genovi.domain.enums.TypeSexo;
import br.com.genovi.domain.enums.TypeStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "raca")
    private TypeRaca raca;

    @Column(name = "fbb")
    private String fbb;

    @Column(name = "data_nascimento", nullable = true)
    private LocalDateTime dataNascimento;

    //Se tiver compra pega da compra se nao pega do parto se nao manualmente
    @Column(name = "data_cadastro", nullable = true)
    private LocalDateTime dataCadastro;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "grau_pureza")
    private TypeGrauPureza typeGrauPureza;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "sexo")
    private TypeSexo sexo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ovino_mae", nullable = true)
    private Ovino ovinoMae;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ovino_pai", nullable = true)
    private Ovino ovinoPai;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private TypeStatus status;

    @Column(name = "foto_ovino", nullable = true)
    private String fotoOvino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compra", nullable = true)
    private Compra compra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parto", nullable = true)
    private Parto parto;

    @OneToMany(mappedBy = "ovino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pesagem> pesagens = new ArrayList<>();

    public Ovino() {
    }

    public Ovino(Long id, Long rfid, String nome, TypeRaca raca, String fbb, LocalDateTime dataNascimento, LocalDateTime dataCadastro, TypeGrauPureza typeGrauPureza, TypeSexo sexo, Ovino ovinoMae, Ovino ovinoPai, TypeStatus status, String fotoOvino, Compra compra, Parto parto, List<Pesagem> pesagens) {
        this.id = id;
        this.rfid = rfid;
        this.nome = nome;
        this.raca = raca;
        this.fbb = fbb;
        this.dataNascimento = dataNascimento;
        this.dataCadastro = dataCadastro;
        this.typeGrauPureza = typeGrauPureza;
        this.sexo = sexo;
        this.ovinoMae = ovinoMae;
        this.ovinoPai = ovinoPai;
        this.status = status;
        this.fotoOvino = fotoOvino;
        this.compra = compra;
        this.parto = parto;
        this.pesagens = pesagens;
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

    public TypeRaca getRaca() {
        return raca;
    }

    public void setRaca(TypeRaca raca) {
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

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
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

    public Ovino getOvinoMae() {
        return ovinoMae;
    }

    public void setOvinoMae(Ovino ovinoMae) {
        this.ovinoMae = ovinoMae;
    }

    public Ovino getOvinoPai() {
        return ovinoPai;
    }

    public void setOvinoPai(Ovino ovinoPai) {
        this.ovinoPai = ovinoPai;
    }

    public TypeStatus getStatus() {
        return status;
    }

    public void setStatus(TypeStatus status) {
        this.status = status;
    }

    public String getFotoOvino() {
        return fotoOvino;
    }

    public void setFotoOvino(String fotoOvino) {
        this.fotoOvino = fotoOvino;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Parto getParto() {
        return parto;
    }

    public void setParto(Parto parto) {
        this.parto = parto;
    }

    public List<Pesagem> getPesagens() {
        return pesagens;
    }

    public void setPesagens(List<Pesagem> pesagens) {
        this.pesagens = pesagens;
    }
}
