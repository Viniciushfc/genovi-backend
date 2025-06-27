package br.com.genovi.domain.models;

import br.com.genovi.domain.enums.Role;
import br.com.genovi.domain.enums.TypeUsuario;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Boolean ativo;
    private String username;
    private String email;
    private String senha;
    private TypeUsuario perfil;
    //A autenticacao2fa precisa ser "Boolean" devido a possibilidade de ser null
    private Boolean autenticacao2fa;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Usuario() {
    }

    public Usuario(Long id, Boolean ativo, String username, String email, String senha, TypeUsuario perfil, Boolean autenticacao2fa, Set<Role> roles) {
        this.id = id;
        this.ativo = ativo;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.autenticacao2fa = autenticacao2fa;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public TypeUsuario getPerfil() {
        return perfil;
    }

    public void setPerfil(TypeUsuario perfil) {
        this.perfil = perfil;
    }

    public Boolean getAutenticacao2fa() {
        return autenticacao2fa;
    }

    public void setAutenticacao2fa(Boolean autenticacao2fa) {
        this.autenticacao2fa = autenticacao2fa;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
