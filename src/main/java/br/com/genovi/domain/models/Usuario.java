package br.com.genovi.domain.models;

import br.com.genovi.domain.enums.Role;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles;

    @OneToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;

    public Usuario() {
    }

    public Usuario(Long id, boolean ativo, String email, String senha, Boolean autenticacao2fa, Set<Role> roles, Funcionario funcionario) {
        this.id = id;
        this.ativo = ativo;
        this.email = email;
        this.senha = senha;
        this.autenticacao2fa = autenticacao2fa;
        this.roles = roles;
        this.funcionario = funcionario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getAutenticacao2fa() {
        return autenticacao2fa;
    }

    public void setAutenticacao2fa(Boolean autenticacao2fa) {
        this.autenticacao2fa = autenticacao2fa;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
