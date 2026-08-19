# AppSec Findings and Remediation Log - Trivy

Este documento centraliza os achados identificados com Trivy no projeto `e-signature-app`.

## SCA-TRIVY-001 - Outdated Spring Boot Parent

### Summary

O Trivy identificou que a aplicacao empacotava dependencias Java vulneraveis por causa do `spring-boot-starter-parent` desatualizado no `pom.xml`.

O projeto usava Spring Boot `3.1.1`, que gerenciava versoes antigas de dependencias transitivas. A correcao aplicada foi atualizar o parent para Spring Boot `3.5.13`, reduzindo a exposicao causada por bibliotecas herdadas da matriz antiga de dependencias.

### Tooling

```text
Method: SCA
Tool: Trivy 0.74.0
Workflow: .github/workflows/security-trivy.yml
Artifact: trivy-reports
Report: trivy-image.txt / trivy-image.json
```

### Evidence

ID do achado:

```text
SCA-TRIVY-001
```

Dependencia raiz vulneravel:

```text
org.springframework.boot:spring-boot-starter-parent:3.1.1
```

Evidencia observada no scan:

```text
O Trivy reportou vulnerabilidades CRITICAL e HIGH em dependencias Java
transitivas empacotadas no app.jar, incluindo componentes gerenciados pelo
Spring Boot parent antigo.
```

Principais dependencias transitivas afetadas:

```text
org.apache.tomcat.embed:tomcat-embed-core 10.1.10
org.springframework:spring-web 6.0.10
org.springframework:spring-webmvc 6.0.10
com.fasterxml.jackson.core:jackson-databind 2.15.2
ch.qos.logback:logback-core 1.4.8
org.yaml:snakeyaml 1.33
com.h2database:h2 2.1.214
```

Exemplos de vulnerabilidades associadas as dependencias gerenciadas pela matriz antiga:

```text
CVE-2026-43512 - tomcat-embed-core - CRITICAL
CVE-2025-24813 - tomcat-embed-core - CRITICAL
CVE-2026-54513 - jackson-databind - HIGH
CVE-2024-38819 - spring-webmvc - HIGH
CVE-2022-1471  - snakeyaml - HIGH
CVE-2022-45868 - h2 - HIGH
```

### Affected Components

Arquivo afetado:

```text
pom.xml
```

Configuracao anterior:

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.1.1</version>
    <relativePath/>
</parent>
```

### Risk

O uso de uma matriz antiga de dependencias aumenta o risco de exploracao por vulnerabilidades conhecidas em bibliotecas transitivas.

Impactos possiveis, de acordo com os tipos de CVEs observados:

```text
Remote Code Execution
Authentication bypass
Authorization bypass
Path traversal
HTTP request smuggling
Information disclosure
Denial of Service
Unsafe deserialization
```

Severidade tratada:

```text
High
```

### Root Cause

A causa raiz foi o uso de Spring Boot `3.1.1` como parent Maven.

Como o Spring Boot parent controla as versoes das dependencias transitivas, uma unica versao antiga no `pom.xml` fez com que o artefato final herdasse bibliotecas desatualizadas. A correcao por upgrade do parent foi escolhida para manter a matriz de dependencias alinhada, em vez de sobrescrever manualmente versoes individuais.

### Remediation Plan

Log ID:

```text
log-01
```

Correcao aplicada:

```text
Atualizacao do spring-boot-starter-parent de 3.1.1 para 3.5.13.
```

Configuracao atual:

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.13</version>
    <relativePath/>
</parent>
```

### Validation

Validacoes realizadas:

```text
pom.xml atualizado para Spring Boot 3.5.13
```

Criterio de fechamento:

```text
O novo scan Trivy deve mostrar reducao dos achados CRITICAL e HIGH associados
as dependencias Java transitivas gerenciadas pelo Spring Boot 3.1.1.
```

### Status

```text
Fixed
```

## SCA-TRIVY-002 - Unused Vulnerable Thymeleaf Dependency

### Summary

O Trivy identificou vulnerabilidades criticas em dependencias Thymeleaf empacotadas no `app.jar`.

A analise mostrou que o projeto nao utiliza templates Thymeleaf renderizados pelo backend. A interface web e servida como HTML, CSS e JavaScript estaticos em `src/main/resources/static`. Por isso, a dependencia `spring-boot-starter-thymeleaf` aumentava a superficie de ataque sem necessidade funcional.

A correcao aplicada foi remover `spring-boot-starter-thymeleaf` do `pom.xml`.

### Tooling

```text
Method: SCA
Tool: Trivy 0.74.0
Workflow: .github/workflows/security-trivy.yml
Artifact: trivy-reports
Report: trivy-image.txt / trivy-image.json
```

### Evidence

ID do achado:

```text
SCA-TRIVY-002
```

Dependencia vulneravel:

```text
org.springframework.boot:spring-boot-starter-thymeleaf
```

Componentes reportados pelo Trivy:

```text
org.thymeleaf:thymeleaf 3.1.1.RELEASE
org.thymeleaf:thymeleaf-spring6 3.1.1.RELEASE
```

Exemplos de vulnerabilidades reportadas:

```text
CVE-2026-40477 - thymeleaf - CRITICAL
CVE-2026-40478 - thymeleaf - CRITICAL
CVE-2026-41901 - thymeleaf - CRITICAL
```

Evidencia funcional:

```text
Nao ha templates Thymeleaf em src/main/resources/templates.
A interface da aplicacao esta em src/main/resources/static.
```

### Affected Components

Arquivo afetado:

```text
pom.xml
```

Dependencia removida:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### Risk

Thymeleaf vulneravel pode expor a aplicacao a riscos de Server-Side Template Injection quando templates e expressoes sao renderizados no backend.

Mesmo que a aplicacao atual nao use renderizacao Thymeleaf, manter a dependencia vulneravel no artefato final aumenta a superficie de ataque e prejudica a postura de seguranca do projeto.

Impactos possiveis:

```text
Server-Side Template Injection
Expression execution bypass
Remote Code Execution em cenarios exploraveis
```

Severidade tratada:

```text
Critical
```

### Root Cause

A causa raiz foi a presenca de uma dependencia nao utilizada:

```text
spring-boot-starter-thymeleaf
```

O projeto passou a servir a interface como conteudo estatico, mas a dependencia de template engine permaneceu no `pom.xml`. Como resultado, bibliotecas Thymeleaf vulneraveis foram empacotadas no `app.jar` mesmo sem necessidade funcional.

### Remediation Plan

Log ID:

```text
log-01
```

Correcao aplicada:

```text
Remocao do spring-boot-starter-thymeleaf do pom.xml.
```

Justificativa:

```text
A aplicacao nao utiliza templates Thymeleaf. Remover a dependencia elimina os
componentes vulneraveis do artefato final sem impactar a funcionalidade atual.
```

### Validation

Validacoes realizadas:

```text
spring-boot-starter-thymeleaf removido do pom.xml
Arquivos estaticos mantidos em src/main/resources/static
Rota / mantida via HomeController encaminhando para index.html
```

Criterio de fechamento:

```text
O novo scan Trivy nao deve listar org.thymeleaf:thymeleaf nem
org.thymeleaf:thymeleaf-spring6 no app.jar.
```

### Status

```text
Fixed
```

## SCA-TRIVY-003 - Vulnerable Embedded Tomcat

### Summary

O Trivy identificou vulnerabilidades criticas no Tomcat Embedded empacotado no `app.jar`.

O projeto usa Spring Boot com servidor web embutido. Por isso, o Tomcat nao aparece como dependencia declarada diretamente no `pom.xml`; ele e herdado pelos starters do Spring Boot e gerenciado pelo `spring-boot-starter-parent`.

A correcao aplicada foi atualizar o Spring Boot parent de `3.5.13` para `3.5.16`, fazendo a matriz de dependencias gerenciada pelo Spring Boot atualizar o Tomcat Embedded para uma versao corrigida.

### Tooling

```text
Method: SCA
Tool: Trivy 0.74.0
Workflow: .github/workflows/security-trivy.yml
Artifact: trivy-reports
Report: trivy-image.txt / trivy-image.json
```

### Evidence

ID do achado:

```text
SCA-TRIVY-003
```

Componente vulneravel:

```text
org.apache.tomcat.embed:tomcat-embed-core
```

Versao vulneravel observada:

```text
10.1.53
```

Versao corrigida indicada pelo Trivy:

```text
10.1.55
```

Exemplos de vulnerabilidades reportadas:

```text
CVE-2026-41293 - tomcat-embed-core - CRITICAL
CVE-2026-43512 - tomcat-embed-core - CRITICAL
CVE-2026-43515 - tomcat-embed-core - CRITICAL
```

### Affected Components

Arquivo afetado:

```text
pom.xml
```

Configuracao anterior:

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.13</version>
    <relativePath/>
</parent>
```

Configuracao atual:

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.16</version>
    <relativePath/>
</parent>
```

### Risk

Vulnerabilidades no Tomcat Embedded afetam diretamente a camada HTTP da aplicacao, pois o servidor embutido processa as requisicoes recebidas em `localhost:8080` ou no ambiente de deploy.

Impactos possiveis:

```text
Authentication bypass
Authorization bypass
HTTP request handling flaws
Information disclosure
Denial of Service
```

Severidade tratada:

```text
Critical
```

### Root Cause

A causa raiz foi o uso de Spring Boot `3.5.13`, que ainda gerenciava `tomcat-embed-core` em uma versao vulneravel.

Como o Tomcat Embedded e uma dependencia transitiva gerenciada pelo Spring Boot, a correcao mais segura foi atualizar o Spring Boot parent para uma versao que ja inclui a matriz de dependencias corrigida, evitando sobrescrever manualmente a versao do Tomcat no `pom.xml`.

### Remediation Plan

Log ID:

```text
log-01
```

Correcao aplicada:

```text
Atualizacao do spring-boot-starter-parent de 3.5.13 para 3.5.16.
```

Resultado esperado da correcao:

```text
Atualizacao do Tomcat Embedded gerenciado pelo Spring Boot para uma versao
corrigida, removendo os achados criticos associados ao tomcat-embed-core
10.1.53.
```

### Validation

Validacoes realizadas:

```text
pom.xml atualizado para Spring Boot 3.5.16
Build Maven executado com sucesso
Testes executados com sucesso
Aplicacao validada apos a atualizacao
```

Criterio de fechamento:

```text
O novo scan Trivy nao deve listar os CVEs criticos associados ao
tomcat-embed-core 10.1.53.
```

### Status

```text
Fixed
```

## SCA-TRIVY-004 - Vulnerable Jackson Databind

### Summary

O Trivy identificou vulnerabilidades de severidade media no `jackson-databind` empacotado no `app.jar`.

O projeto nao declara `jackson-databind` diretamente no `pom.xml`. A biblioteca e incluida de forma transitiva pelos starters do Spring Boot e sua versao e controlada pela matriz de dependencias do `spring-boot-starter-parent`.

A analise mostrou que o Spring Boot `3.5.16` gerencia `jackson-databind` na versao `2.21.4`, enquanto o Trivy indicou versoes corrigidas a partir de `2.21.5`.

### Tooling

```text
Method: SCA
Tool: Trivy 0.74.0
Workflow: .github/workflows/security-trivy.yml
Artifact: trivy-reports
Report: trivy-image.txt
```

### Evidence

ID do achado:

```text
SCA-TRIVY-004
```

Componente vulneravel:

```text
com.fasterxml.jackson.core:jackson-databind
```

Versao vulneravel observada:

```text
2.21.4
```

Versoes corrigidas indicadas pelo Trivy:

```text
2.21.5
2.22.1
2.18.9
3.1.4
```

Vulnerabilidades reportadas:

```text
CVE-2026-54515 - jackson-databind - MEDIUM
CVE-2026-59889 - jackson-databind - MEDIUM
GHSA-mhm7-754m-9p8w - jackson-databind - MEDIUM
```

### Affected Components

Arquivo afetado:

```text
pom.xml
```

Dependencia transitiva afetada:

```text
com.fasterxml.jackson.core:jackson-databind
```

Origem da versao:

```text
spring-boot-starter-parent 3.5.16
```

### Risk

As vulnerabilidades reportadas afetam o processamento de JSON durante serializacao e desserializacao.

Impactos possiveis:

```text
Bypass de regras de serializacao
Modificacao inesperada de propriedades ignoradas
Exposicao ou manipulacao indevida de dados em cenarios especificos
Comportamento inesperado em objetos com anotacoes Jackson sensiveis
```

Severidade tratada:

```text
Medium
```

### Root Cause

A causa raiz foi a versao vulneravel de `jackson-databind` herdada da matriz de dependencias do Spring Boot `3.5.16`.

Como ainda nao havia uma versao mais nova do Spring Boot 3.5.x gerenciando `jackson-databind` corrigido no momento da analise, a correcao planejada e sobrescrever de forma controlada a propriedade de versao do Jackson BOM no `pom.xml`.

### Remediation Plan

Log ID:

```text
log-01
```

Correcao planejada:

```text
Sobrescrever a propriedade jackson-bom.version para uma versao corrigida
indicada pelo Trivy.
```

Configuracao proposta:

```xml
<properties>
    <java.version>21</java.version>
    <jackson-bom.version>2.21.5</jackson-bom.version>
</properties>
```

Justificativa:

```text
O Trivy indicou jackson-databind 2.21.5 como versao corrigida dentro da mesma
linha 2.21.x. Essa opcao reduz o risco de incompatibilidade em relacao a uma
mudanca maior para 2.22.x ou 3.x.
```

### Validation

Validacoes planejadas:

```text
Executar build Maven
Executar testes automatizados
Recriar a imagem Docker
Executar novo scan Trivy na imagem
Confirmar que jackson-databind 2.21.4 nao aparece mais no app.jar
Confirmar que os achados CVE-2026-54515, CVE-2026-59889 e
GHSA-mhm7-754m-9p8w foram removidos do bloco Java do relatorio
```

### Status

```text
Fixed
```


## SCA-TRIVY-005 - Vulnerable Log4j API

### Summary

O Trivy identificou uma vulnerabilidade de severidade media no `log4j-api` empacotado no `app.jar`.

O projeto nao declara `log4j-api` diretamente no `pom.xml`. A biblioteca e incluida de forma transitiva por dependencias do ecossistema Spring Boot e sua versao e controlada pela matriz de dependencias do `spring-boot-starter-parent`.

A analise mostrou que o artefato empacotava `log4j-api` na versao `2.24.3`, enquanto o Trivy indicou versoes corrigidas a partir de `2.25.5`.

### Tooling

```text
Method: SCA
Tool: Trivy 0.74.0
Workflow: .github/workflows/security-trivy.yml
Artifact: trivy-reports
Report: trivy-image.json
```

### Evidence

ID do achado:

```text
SCA-TRIVY-005
```

Componente vulneravel:

```text
org.apache.logging.log4j:log4j-api
```

Versao vulneravel observada:

```text
2.24.3
```

Versoes corrigidas indicadas pelo Trivy:

```text
2.25.5
2.26.1
```

Vulnerabilidade reportada:

```text
CVE-2026-49844 - log4j-api - MEDIUM
```

### Affected Components

Arquivo afetado:

```text
pom.xml
```

Dependencia transitiva afetada:

```text
org.apache.logging.log4j:log4j-api
```

Origem da versao:

```text
spring-boot-starter-parent 3.5.16
```

### Risk

A vulnerabilidade afeta a biblioteca `log4j-api`, usada por componentes Java para integracao com logging.

Impactos possiveis:

```text
Geracao incorreta de saida JSON
Encoding inadequado em logs estruturados
Risco de interpretacao incorreta de eventos de log por ferramentas downstream
Possivel degradacao de confiabilidade em trilhas de auditoria
```

Severidade tratada:

```text
Medium
```

### Root Cause

A causa raiz foi a versao vulneravel de `log4j-api` herdada da matriz de dependencias do Spring Boot `3.5.16`.

Como o projeto nao declara `log4j-api` diretamente, a correcao aplicada foi sobrescrever de forma controlada a propriedade de versao do Log4j gerenciada pelo Spring Boot.

### Remediation Plan

Log ID:

```text
log-01
```

Correcao aplicada:

```text
Sobrescrita da propriedade log4j2.version para uma versao corrigida indicada
pelo Trivy.
```

Configuracao aplicada:

```xml
<properties>
    <java.version>21</java.version>
    <jackson-bom.version>2.21.5</jackson-bom.version>
    <log4j2.version>2.25.5</log4j2.version>
</properties>
```

Justificativa:

```text
O Trivy indicou log4j-api 2.25.5 como versao corrigida. A atualizacao por
propriedade mantem o controle centralizado de versoes pelo Maven/Spring Boot
e evita declarar manualmente a dependencia transitiva no pom.xml.
```

### Validation

Validacoes realizadas:

```text
pom.xml atualizado com log4j2.version 2.25.5
Build Maven executado com sucesso
Testes automatizados executados com sucesso
Imagem Docker recriada
Novo scan Trivy executado na imagem
```

Criterio de fechamento:

```text
O novo scan Trivy nao deve listar org.apache.logging.log4j:log4j-api 2.24.3
nem a vulnerabilidade CVE-2026-49844 no bloco Java do relatorio.
```

### Status

```text
Fixed
```
