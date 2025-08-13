# Genovi - Backend

Sistema de Gestão e Monitoramento de Ovinos desenvolvido como parte de um Trabalho de Conclusão de Curso (TCC).  
O objetivo é fornecer uma solução tecnológica eficiente para auxiliar criadores na gestão, rastreio e melhoria genética do rebanho ovino.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Spring DevTools
  - Autenticação JWT
- **Banco de Dados**
  - H2 Database (desenvolvimento e testes)
  - PostgreSQL (produção)
- **Arquitetura Limpa (Clean Architecture)**
- **Princípios SOLID e boas práticas de POO**
- **Integração com Gemini** *(API gratuita do Google AI Studio para responder perguntas e auxiliar usuários com inteligência artificial)*

## Sobre o Projeto

O **Genovi** é um sistema de monitoramento e gestão de ovinos que integra tecnologia para otimizar o controle do rebanho.  
Seu principal diferencial é o foco específico nas necessidades dos criadores, oferecendo funcionalidades que geralmente só existem em sistemas voltados para bovinos.  

Além disso, o sistema conta com integração à **API Gemini**, permitindo que criadores façam perguntas e obtenham respostas automatizadas utilizando inteligência artificial.

## Arquitetura

O projeto segue os princípios da **Arquitetura Limpa**, promovendo:

- Alta coesão e baixo acoplamento
- Separação clara de responsabilidades
- Facilidade de manutenção, testes e escalabilidade

## Segurança

- Autenticação e autorização com **JWT (JSON Web Token)**.
- Controle de acesso baseado em **roles** (papéis), garantindo segurança nas operações sensíveis.

## Collection do Postman

Para facilitar os testes da API, disponibilizamos uma collection completa no Postman com todos os endpoints documentados.  
Basta acessar o link abaixo e importar para o seu Postman:

🔗 **[Acessar Collection no Postman](https://bold-sunset-813684.postman.co/workspace/My-Workspace~c4643ed2-a217-494e-82a8-5d0977dddbd8/collection/22777596-e9b3dfc5-feca-4830-afe0-58218b69b40f?action=share&creator=22777596)**
