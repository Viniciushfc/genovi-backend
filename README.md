# 🐑 Genovi - Backend

Sistema de Gestão e Monitoramento de Ovinos desenvolvido como parte de um Trabalho de Conclusão de Curso (TCC). O objetivo é fornecer uma solução tecnológica eficiente para auxiliar criadores na gestão, rastreio e melhoria genética do rebanho ovino.

---
# 📊 Status

🚧 Em desenvolvimento 🚧

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Spring DevTools
  - Autenticação JWT
    
- **Banco de Dados**
  - H2 Database (ambiente de desenvolvimento e testes)
  - PostgreSQL (produção)
- **Arquitetura Limpa (Clean Architecture)**
- **Princípios SOLID e boas práticas de POO**

---

## 🐏 Sobre o Projeto

O **Genovi** é um sistema de monitoramento e gestão de ovinos que integra tecnologia para otimizar o controle do rebanho. Seu principal diferencial é o foco específico nas necessidades dos criadores de ovinos, oferecendo funcionalidades geralmente encontradas apenas em sistemas para bovinos.

---

## 🗂️ Arquitetura

O projeto segue os princípios da **Arquitetura Limpa**, promovendo:

- Alta coesão e baixo acoplamento
- Separação clara de responsabilidades
- Facilidade de manutenção, testes e escalabilidade

---

## 🔒 Segurança

- Implementação de autenticação e autorização utilizando **JWT (JSON Web Token)**.
- Controle de acesso baseado em roles (papéis), garantindo segurança nas operações sensíveis do sistema.
