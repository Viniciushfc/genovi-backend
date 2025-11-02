package br.com.genovi.domain.models;

import br.com.genovi.domain.enums.EnumRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "gen_usuario",
            sequenceName = "seq_usuario",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "ativo")
    private boolean ativo;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "autenticacao2fa") //A autenticacao2fa precisa ser "Boolean" devido a possibilidade de ser null
    private Boolean autenticacao2fa;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_roles")
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<EnumRole> enumRoles;

    @OneToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;
}
