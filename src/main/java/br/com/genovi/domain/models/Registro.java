package br.com.genovi.domain.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registro")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reproducao", nullable = true)
    private Reproducao reproducao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gestacao", nullable = true)
    private Gestacao gestacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parto", nullable = true)
    private Parto parto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aplicacao", nullable = true)
    private Aplicacao aplicacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ocorrencia_doenca", nullable = true)
    private OcorrenciaDoenca ocorrenciaDoenca;

    public Registro() {
    }

    public Registro(Long id, LocalDateTime dataRegistro, Funcionario funcionario, Reproducao reproducao, Gestacao gestacao, Parto parto, Aplicacao aplicacao, OcorrenciaDoenca ocorrenciaDoenca) {
        this.id = id;
        this.dataRegistro = dataRegistro;
        this.funcionario = funcionario;
        this.reproducao = reproducao;
        this.gestacao = gestacao;
        this.parto = parto;
        this.aplicacao = aplicacao;
        this.ocorrenciaDoenca = ocorrenciaDoenca;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Reproducao getReproducao() {
        return reproducao;
    }

    public void setReproducao(Reproducao reproducao) {
        this.reproducao = reproducao;
    }

    public Gestacao getGestacao() {
        return gestacao;
    }

    public void setGestacao(Gestacao gestacao) {
        this.gestacao = gestacao;
    }

    public Parto getParto() {
        return parto;
    }

    public void setParto(Parto parto) {
        this.parto = parto;
    }

    public Aplicacao getAplicacao() {
        return aplicacao;
    }

    public void setAplicacao(Aplicacao aplicacao) {
        this.aplicacao = aplicacao;
    }

    public OcorrenciaDoenca getOcorrenciaDoenca() {
        return ocorrenciaDoenca;
    }

    public void setOcorrenciaDoenca(OcorrenciaDoenca ocorrenciaDoenca) {
        this.ocorrenciaDoenca = ocorrenciaDoenca;
    }
}
