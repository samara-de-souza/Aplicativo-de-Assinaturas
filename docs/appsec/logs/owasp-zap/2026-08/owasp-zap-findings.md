# AppSec Findings and Remediation Log - OWASP ZAP

Este documento centraliza os achados identificados com OWASP ZAP no projeto `e-signature-app`.

## DAST-ZAP-001 - Content Security Policy Header Not Set

### Summary

O OWASP ZAP Baseline identificou que a aplicacao nao define o header HTTP `Content-Security-Policy` na resposta da pagina principal.

Content Security Policy e um controle de seguranca do navegador usado para restringir as origens permitidas para scripts, estilos, imagens, frames e outros recursos carregados pela pagina. Sem esse header, a aplicacao fica com menor protecao contra ataques como Cross-Site Scripting e injecao de conteudo.

### Tooling

```text
Method: DAST
Tool: OWASP ZAP Baseline
Workflow: .github/workflows/security-zap.yml
Artifact: zap-baseline-report
Report: report_html.html / report_json.json / report_md.md
Alert: Content Security Policy (CSP) Header Not Set
Risk: Medium
Confidence: High
```

### Evidence

ID do achado:

```text
DAST-ZAP-001
```

Alerta reportado:

```text
Content Security Policy (CSP) Header Not Set
```

URL afetada:

```text
http://localhost:8080
```

Metodo:

```text
GET
```

Severidade:

```text
Medium
```

Confianca:

```text
High
```

CWE:

```text
CWE-693 - Protection Mechanism Failure
```

Evidencia do relatorio:

```text
O ZAP reportou ausencia do header Content-Security-Policy na resposta de
http://localhost:8080.
```

### Affected Components

Componente afetado:

```text
Resposta HTTP da interface web
```

Arquivos relacionados:

```text
src/main/resources/static/index.html
src/main/resources/static/scripts.js
src/main/resources/static/styles.css
```

Fluxo afetado:

```text
GET /
```

### Risk

A ausencia de CSP reduz a capacidade do navegador de bloquear carregamento ou execucao de conteudo nao autorizado.

Impactos possiveis:

```text
Maior exposicao a Cross-Site Scripting
Maior exposicao a injecao de conteudo
Execucao indevida de scripts caso exista outra falha exploravel
Ausencia de controle explicito sobre origens permitidas para recursos da pagina
```

Severidade tratada:

```text
Medium
```

### Root Cause

A causa raiz foi a ausencia de configuracao centralizada de headers HTTP de seguranca na aplicacao.

Como a interface web e servida pelo Spring Boot como conteudo estatico, as respostas HTTP da pagina principal e dos assets estaticos foram entregues sem o header `Content-Security-Policy`.

### Remediation Plan

Log ID:

```text
log-01
```

Correcao planejada:

```text
Adicionar configuracao de headers HTTP de seguranca para incluir
Content-Security-Policy nas respostas da aplicacao.
```

Politica CSP inicial proposta:

```text
default-src 'self'; script-src 'self'; style-src 'self' 'unsafe-inline'; img-src 'self' data:; object-src 'none'; base-uri 'self'; frame-ancestors 'none'
```

Justificativa:

```text
A aplicacao carrega recursos locais a partir de src/main/resources/static.
Por isso, uma politica baseada em 'self' reduz a superficie de ataque sem
exigir origens externas. A diretiva frame-ancestors 'none' tambem ajuda a
mitigar o finding de clickjacking reportado pelo ZAP.
```

### Validation

Validacoes planejadas:

```text
Executar testes automatizados
Recriar a imagem Docker
Executar novamente o OWASP ZAP Baseline
Confirmar que o alerta Content Security Policy (CSP) Header Not Set foi removido
Confirmar que a aplicacao continua carregando index.html, scripts.js e styles.css
```

### Status

```text
Fixed
```

## DAST-ZAP-002 - CSP Failure to Define Directive with No Fallback

### Summary

O OWASP ZAP Baseline identificou que a politica `Content-Security-Policy` configurada na aplicacao nao definia uma diretiva que nao possui fallback para `default-src`.

O alerta foi gerado apos a correcao inicial do header CSP. A aplicacao passou a enviar `Content-Security-Policy`, mas a politica ainda nao declarava explicitamente a diretiva `form-action`. Segundo o ZAP, diretivas desse tipo nao herdam automaticamente a politica de `default-src`, entao precisam ser configuradas de forma explicita.

### Tooling

```text
Method: DAST
Tool: OWASP ZAP Baseline
Workflow: .github/workflows/security-zap.yml
Artifact: zap-baseline-report
Report: report_html.html / report_json.json / report_md.md
Alert: CSP: Failure to Define Directive with No Fallback
Risk: Medium
Confidence: High
```

### Evidence

ID do achado:

```text
DAST-ZAP-002
```

Alerta reportado:

```text
CSP: Failure to Define Directive with No Fallback
```

URL afetada:

```text
http://localhost:8080
```

Metodo:

```text
GET
```

Parametro:

```text
Content-Security-Policy
```

Severidade:

```text
Medium
```

Confianca:

```text
High
```

CWE:

```text
CWE-693 - Protection Mechanism Failure
```

Evidencia observada no relatorio:

```text
The directive(s): form-action is/are among the directives that do not fallback
to default-src.
```

Politica CSP anterior:

```text
default-src 'self'; script-src 'self'; style-src 'self' 'unsafe-inline'; img-src 'self' data:; object-src 'none'; base-uri 'self'; frame-ancestors 'none'
```

### Affected Components

Componente afetado:

```text
Resposta HTTP da interface web
```

Arquivo relacionado:

```text
src/main/java/application/SecurityHeadersFilter.java
```

Fluxo afetado:

```text
GET /
```

### Risk

Uma politica CSP sem `form-action` explicito deixa o comportamento de submissao de formularios menos restritivo do que o esperado.

Impactos possiveis:

```text
Ausencia de controle explicito sobre destinos permitidos para formularios
Maior exposicao em cenarios de injecao de HTML ou manipulacao de formulario
Politica CSP incompleta para controles que nao usam fallback de default-src
```

Severidade tratada:

```text
Medium
```

### Root Cause

A causa raiz foi uma politica CSP inicial incompleta.

A primeira correcao adicionou o header `Content-Security-Policy`, mas nao incluiu `form-action`. Como essa diretiva nao utiliza `default-src` como fallback, o ZAP continuou identificando uma lacuna na politica de seguranca.

### Remediation Plan

Log ID:

```text
log-01
```

Correcao aplicada:

```text
Adicionar form-action 'self' a politica Content-Security-Policy.
```

Politica CSP atual:

```text
default-src 'self'; script-src 'self'; style-src 'self' 'unsafe-inline'; img-src 'self' data:; object-src 'none'; base-uri 'self'; form-action 'self'; frame-ancestors 'none'
```

Justificativa:

```text
A aplicacao nao precisa submeter formularios para origens externas. A diretiva
form-action 'self' restringe o envio de formularios para a propria origem da
aplicacao e fecha a lacuna reportada pelo ZAP.
```

### Validation

Validacoes realizadas:

```text
Build da aplicacao executado com sucesso
Aplicacao executada localmente
Header Content-Security-Policy validado com curl.exe -I http://localhost:8080
```

Evidencia local:

```text
Content-Security-Policy: default-src 'self'; script-src 'self'; style-src 'self' 'unsafe-inline'; img-src 'self' data:; object-src 'none'; base-uri 'self'; form-action 'self'; frame-ancestors 'none'
```

Criterio de fechamento:

```text
O novo scan OWASP ZAP Baseline nao deve listar o alerta
CSP: Failure to Define Directive with No Fallback.
```

### Status

```text
Fixed
```
