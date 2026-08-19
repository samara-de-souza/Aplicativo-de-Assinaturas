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
