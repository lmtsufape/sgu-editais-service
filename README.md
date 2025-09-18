# SGU Editais Service

[Swagger Dev (Documentação da API)](http://localhost:8083/swagger-ui/index.html)


Projeto Java para ...

---

## Sobre o Projeto

Este projeto foi desenvolvido...

---

## Tecnologias Utilizadas

* [Java 21+](https://www.oracle.com/br/java/technologies/downloads/#java21) - Linguagem de programação
* [Maven](https://maven.apache.org/) - Gerenciador de dependências e build
* [Docker](https://www.docker.com/) - Plataforma de containerização

---

## Como Começar

Siga estas instruções para colocar o projeto em funcionamento em sua máquina local para fins de desenvolvimento e teste.

### Pré-requisitos

Antes de iniciar, clone os seguintes repositórios:

- [SGU Discovery Service](https://github.com/lmtsufape/sgu-discovery-service)
- [SGU Gateway Service](https://github.com/lmtsufape/sgu-gateway-service)
- [SGU Config Service](https://github.com/lmtsufape/sgu-config-service)
- [SGU Auth Service](https://github.com/lmtsufape/sgu-auth-server)
- [SGU PRAE Service](https://github.com/lmtsufape/sgu-prae-service)

Certifique-se de ter instalado em sua máquina:

* JDK 21
* Maven 3.9.9 ou superior
* Docker (para rodar os outros serviços)

Apos clonar os repositórios, execute o seguinte comando (seguindo a ordem acima) em cada repositório:

```bash
    docker compose up -d --build
```

Execute este projeto tamém via Docker ou em uma IDE de sua preferência.

## Rodando os Testes

Para rodar os testes unitários, execute o seguinte comando na raiz do projeto:

```bash
   mvn clean test
```

Para visualizar a cobertura de testes, execute:

```bash
   mvn clean verify
```

E acesse o seguinte arquivo

```text
target/site/jacoco/index.html
```
