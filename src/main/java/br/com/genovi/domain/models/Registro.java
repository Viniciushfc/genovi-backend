package br.com.genovi.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registro")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_registro")
    @SequenceGenerator(name = "gen_registro", sequenceName = "gen_id_registro", allocationSize = 1)
    private Long id;

    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;

    @Column(name = "is_sugestao")
    private Boolean isSugestao;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionario;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reproducao", nullable = true)
    private Reproducao reproducao;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_gestacao", nullable = true)
    private Gestacao gestacao;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_parto", nullable = true)
    private Parto parto;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_aplicacao", nullable = true)
    private Aplicacao aplicacao;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ocorrencia_doenca", nullable = true)
    private OcorrenciaDoenca ocorrenciaDoenca;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pesagem", nullable = true)
    private Pesagem pesagem;

    @Column(columnDefinition = "TEXT", name = "descricao_registro")
    private String descricaoRegistro;

    @Override
    public String toString() {
        return "id: " + id +
                ", dataRegistro: " + dataRegistro +
                ", isSugestao: " + isSugestao +
                ", funcionario: " + funcionario +
                ", reproducao: " + reproducao +
                ", gestacao: " + gestacao +
                ", parto: " + parto +
                ", aplicacao: " + aplicacao +
                ", ocorrenciaDoenca: " + ocorrenciaDoenca +
                ", pesagem: " + pesagem +
                ", descricaoRegistro: " + descricaoRegistro;
    }
}
