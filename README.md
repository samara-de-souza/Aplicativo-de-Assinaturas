# e-signature-app

O `e-signature-app` é uma aplicação Java com Spring Boot para administração de assinaturas, clientes, aplicativos e pagamentos. O projeto foi iniciado como trabalho acadêmico de fundamentos de desenvolvimento de software e está sendo evoluído como portfólio prático de Application Security.

A aplicação combina uma API REST com uma interface web estática servida pelo próprio Spring Boot. O objetivo funcional é representar um fluxo simples de gestão de assinaturas, enquanto o objetivo técnico atual é demonstrar práticas de AppSec aplicadas a um projeto real: análise de dependências, identificação de vulnerabilidades, documentação de evidências, correção, revalidação e rastreabilidade por commits.

Como evolução futura, o projeto também será expandido para uma trilha DevSecOps, incluindo security gates, hardening de Docker, publicação de imagem no GitHub Container Registry, artifacts, Dependabot, SBOM e deploy controlado.

## Domínio da aplicação

O sistema trabalha com entidades relacionadas ao ciclo de vida de assinaturas:

- `Cliente`: pessoa ou organização que consome um aplicativo.
- `Aplicativo`: produto contratado pelo cliente.
- `Assinatura`: vínculo entre cliente e aplicativo, com período de vigência.
- `Pagamento`: registro financeiro associado a uma assinatura.
- `Usuário`: entidade usada para validação de acesso no fluxo da aplicação.

Essas entidades são expostas por serviços e endpoints REST, permitindo consultar clientes, aplicativos, assinaturas, pagamentos e validações de negócio.

## Arquitetura

O projeto está organizado em camadas para separar responsabilidades de aplicação, entrada HTTP, domínio, persistência e infraestrutura.

```text
src/main/java/application
src/main/java/controller
src/main/java/domain/entity
src/main/java/domain/repository
src/main/java/domain/service
src/main/java/infrastructure
src/main/resources/static
src/test/java
```

Responsabilidades principais:

- `application`: inicialização da aplicação Spring Boot.
- `controller`: endpoints REST e roteamento da interface web.
- `domain/entity`: entidades persistidas com JPA.
- `domain/repository`: acesso a dados com Spring Data JPA.
- `domain/service`: regras de negócio da aplicação.
- `infrastructure`: configuração e inicialização de dados.
- `src/main/resources/static`: interface web estática.
- `src/test/java`: testes automatizados.

## Tecnologias do projeto

- Java 21.
- Spring Boot.
- Spring Data JPA.
- Spring Data REST.
- Spring Boot Actuator.
- H2 Database.
- Maven Wrapper.
- Docker.
- GitHub Actions.

## Trilha AppSec

Este projeto está sendo usado como laboratório de AppSec com foco em evidência prática. A proposta é documentar não apenas quais ferramentas foram executadas, mas também o que foi encontrado, qual foi o impacto analisado, qual correção foi aplicada e como a correção foi validada.

Controles contemplados no trabalho:

- SCA para análise de dependências.
- Secret scanning para identificação de segredos expostos.
- Container scanning para análise da imagem Docker.
- SAST para análise estática de código.
- DAST para análise dinâmica da aplicação em execução.
- Remediação documentada com evidências antes e depois.

Ferramentas utilizadas ou planejadas nesta fase:

- Trivy.
- GitHub CodeQL.
- OWASP ZAP.

## Documentação AppSec

A documentação de segurança fica separada do README para evitar que este arquivo precise ser alterado a cada novo scan, finding ou ferramenta adicionada.

Estrutura base:

```text
docs/appsec/
```

Ferramentas:

```text
Trivy:

CodeQL:

OWASP ZAP:
```

Os caminhos específicos de relatórios, logs e findings serão preenchidos conforme cada ferramenta for utilizada e documentada no projeto.

## CI/CD

O projeto possui automação com GitHub Actions para validar build, testes e verificações de segurança. A esteira foi pensada para evoluir gradualmente: primeiro gerando visibilidade sobre os achados, depois aplicando critérios de bloqueio conforme os findings forem analisados e tratados.

Workflows:

```text
CI:

Trivy:

CodeQL:

OWASP ZAP:
```

## Docker

A aplicação possui suporte a execução em container. O Docker é usado tanto para facilitar a execução local quanto para permitir análise de segurança da imagem durante a trilha AppSec.

O hardening da imagem faz parte da evolução futura do projeto dentro da trilha DevSecOps.

## Status do portfólio

- Projeto reorganizado para estrutura Maven.
- Aplicação Spring Boot funcional.
- Interface web servida pelo Spring Boot.
- Build e testes automatizados.
- Docker configurado.
- CI básico configurado.
- Trilha AppSec iniciada com análise de vulnerabilidades e registro de remediações.