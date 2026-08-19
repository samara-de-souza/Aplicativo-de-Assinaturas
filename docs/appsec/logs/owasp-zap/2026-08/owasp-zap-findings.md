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

Validacoes realizadas:

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

## DAST-ZAP-003 - X-Content-Type-Options Header Missing

### Summary

O OWASP ZAP Baseline identificou que a aplicacao nao define o header HTTP `X-Content-Type-Options` nas respostas analisadas.

Esse header instrui o navegador a respeitar o `Content-Type` declarado pela aplicacao e evita MIME sniffing. Sem ele, navegadores podem tentar interpretar o conteudo da resposta como um tipo diferente do informado, aumentando o risco em cenarios de injecao ou arquivos servidos com tipo incorreto.

### Tooling

```text
Method: DAST
Tool: OWASP ZAP Baseline
Workflow: .github/workflows/security-zap.yml
Artifact: zap-baseline-report
Report: report_html.html / report_json.json / report_md.md
Alert: X-Content-Type-Options Header Missing
Risk: Low
Confidence: Medium
```

### Evidence

ID do achado:

```text
DAST-ZAP-003
```

Alerta reportado:

```text
X-Content-Type-Options Header Missing
```

URLs afetadas:

```text
http://localhost:8080
http://localhost:8080/scripts.js
http://localhost:8080/styles.css
```

Metodo:

```text
GET
```

Parametro:

```text
x-content-type-options
```

Severidade:

```text
Low
```

Confianca:

```text
Medium
```

CWE:

```text
CWE-693 - Protection Mechanism Failure
```

Evidencia observada no relatorio:

```text
O ZAP reportou ausencia do header X-Content-Type-Options nas respostas da
pagina principal e dos arquivos estaticos scripts.js e styles.css.
```

### Affected Components

Componente afetado:

```text
Respostas HTTP da interface web e arquivos estaticos
```

Arquivo relacionado:

```text
src/main/java/application/SecurityHeadersFilter.java
```

Fluxos afetados:

```text
GET /
GET /scripts.js
GET /styles.css
```

### Risk

A ausencia do header `X-Content-Type-Options` permite que alguns navegadores tentem inferir o tipo do conteudo retornado, em vez de respeitar estritamente o `Content-Type` enviado pela aplicacao.

Impactos possiveis:

```text
MIME sniffing pelo navegador
Interpretacao incorreta de arquivos estaticos
Maior risco em cenarios de upload, injecao ou resposta com Content-Type incorreto
Reducao da postura de seguranca dos headers HTTP
```

Severidade tratada:

```text
Low
```

### Root Cause

A causa raiz foi a ausencia do header `X-Content-Type-Options` na configuracao centralizada de headers HTTP de seguranca.

Embora a aplicacao ja envie `Content-Security-Policy`, ainda nao havia configuracao para instruir o navegador a desabilitar MIME sniffing.

### Remediation Plan

Log ID:

```text
log-01
```

Correcao planejada:

```text
Adicionar o header X-Content-Type-Options com valor nosniff nas respostas HTTP.
```

Configuracao proposta:

```java
httpResponse.setHeader("X-Content-Type-Options", "nosniff");
```

Justificativa:

```text
O valor nosniff instrui navegadores compativeis a nao tentar interpretar o
conteudo da resposta como um tipo diferente do declarado pelo Content-Type.
```

### Validation

Validacoes realizadas:

```text
Executar testes automatizados
Executar a aplicacao localmente
Validar o header com curl.exe -I http://localhost:8080
Executar novamente o OWASP ZAP Baseline
Confirmar que o alerta X-Content-Type-Options Header Missing foi removido
```

### Status

```text
Fixed
```


## DAST-ZAP-004 - CSP style-src unsafe-inline

### Summary

O OWASP ZAP Baseline identificou que a politica `Content-Security-Policy` permitia estilos inline por meio da diretiva `style-src 'unsafe-inline'`.

O alerta surgiu apos a aplicacao passar a definir o header CSP. A politica estava funcional, mas ainda permitia CSS inline. Como o `index.html` utiliza o arquivo externo `styles.css` e nao depende de atributos `style` ou blocos `<style>`, a permissao `'unsafe-inline'` nao era necessaria.

### Tooling

```text
Method: DAST
Tool: OWASP ZAP Baseline
Workflow: .github/workflows/security-zap.yml
Artifact: zap-baseline-report
Report: report_html.html / report_json.json / report_md.md
Alert: CSP: style-src unsafe-inline
Risk: Medium
Confidence: High
```

### Evidence

ID do achado:

```text
DAST-ZAP-004
```

Alerta reportado:

```text
CSP: style-src unsafe-inline
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
style-src includes unsafe-inline.
```

Politica CSP anterior:

```text
default-src 'self'; script-src 'self'; style-src 'self' 'unsafe-inline'; img-src 'self' data:; object-src 'none'; base-uri 'self'; form-action 'self'; frame-ancestors 'none'
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

Arquivo revisado:

```text
src/main/resources/static/index.html
```

Fluxo afetado:

```text
GET /
```

### Risk

A diretiva `'unsafe-inline'` enfraquece a politica CSP porque permite a execucao de estilos inline na pagina.

Impactos possiveis:

```text
Politica CSP menos restritiva
Maior superficie para injecao de conteudo em cenarios exploraveis
Permissao desnecessaria para estilos inline
Reducao da efetividade da CSP como controle defensivo
```

Severidade tratada:

```text
Medium
```

### Root Cause

A causa raiz foi a inclusao permissiva de `'unsafe-inline'` em `style-src` durante a primeira configuracao da CSP.

Essa permissao foi adicionada de forma conservadora para evitar quebra visual da interface, mas a revisao do `index.html` confirmou que os estilos sao carregados por `styles.css`, sem dependencia de estilo inline.

### Remediation Plan

Log ID:

```text
log-01
```

Correcao aplicada:

```text
Remocao de 'unsafe-inline' da diretiva style-src na politica
Content-Security-Policy.
```

Politica CSP atual:

```text
default-src 'self'; script-src 'self'; style-src 'self'; img-src 'self' data:; object-src 'none'; base-uri 'self'; form-action 'self'; frame-ancestors 'none'
```

Justificativa:

```text
A interface usa styles.css como arquivo externo local. Como nao ha necessidade
de CSS inline, a politica pode restringir style-src para 'self', reduzindo a
superficie de ataque sem impactar a funcionalidade.
```

### Validation

Validacoes realizadas:

```text
index.html revisado sem dependencia de style inline
Build da aplicacao executado com sucesso
Aplicacao executada localmente
Header Content-Security-Policy validado com curl.exe -I http://localhost:8080
```

Evidencia local:

```text
Content-Security-Policy: default-src 'self'; script-src 'self'; style-src 'self'; img-src 'self' data:; object-src 'none'; base-uri 'self'; form-action 'self'; frame-ancestors 'none'
```

Criterio de fechamento:

```text
O novo scan OWASP ZAP Baseline nao deve listar o alerta
CSP: style-src unsafe-inline.
```

### Status

```text
Fixed
```

## DAST-ZAP-005 - Permissions Policy Header Not Set

### Summary

O OWASP ZAP Baseline identificou que a aplicacao nao define o header HTTP `Permissions-Policy` nas respostas analisadas.

Esse header permite restringir o uso de recursos sensiveis do navegador, como camera, microfone, geolocalizacao, pagamentos e acesso USB. Como a aplicacao nao precisa desses recursos para o fluxo atual de administracao de assinaturas, a politica foi configurada para negar explicitamente essas permissoes.

### Tooling

```text
Method: DAST
Tool: OWASP ZAP Baseline
Workflow: .github/workflows/security-zap.yml
Artifact: zap-baseline-report
Report: report_html.html / report_json.json / report_md.md
Alert: Permissions Policy Header Not Set
Risk: Low
Confidence: Medium
```

### Evidence

ID do achado:

```text
DAST-ZAP-005
```

Alerta reportado:

```text
Permissions Policy Header Not Set
```

URLs afetadas:

```text
http://localhost:8080
http://localhost:8080/scripts.js
```

Metodo:

```text
GET
```

Severidade:

```text
Low
```

Confianca:

```text
Medium
```

CWE:

```text
CWE-693 - Protection Mechanism Failure
```

Evidencia observada no relatorio:

```text
O ZAP reportou ausencia do header Permissions-Policy nas respostas da pagina
principal e do arquivo scripts.js.
```

### Affected Components

Componente afetado:

```text
Respostas HTTP da interface web e arquivos estaticos
```

Arquivo relacionado:

```text
src/main/java/application/SecurityHeadersFilter.java
```

Fluxos afetados:

```text
GET /
GET /scripts.js
```

### Risk

A ausencia de `Permissions-Policy` deixa o navegador sem uma politica explicita para limitar APIs sensiveis que a pagina pode usar.

Impactos possiveis:

```text
Ausencia de restricao explicita para recursos sensiveis do navegador
Maior superficie para abuso de APIs como camera, microfone ou geolocalizacao
Politica de seguranca de navegador incompleta
```

Severidade tratada:

```text
Low
```

### Root Cause

A causa raiz foi a ausencia do header `Permissions-Policy` na configuracao centralizada de headers HTTP de seguranca.

Embora a aplicacao nao utilize camera, microfone, geolocalizacao, pagamentos ou USB, esses recursos nao estavam explicitamente bloqueados por header.

### Remediation Plan

Log ID:

```text
log-01
```

Correcao aplicada:

```text
Adicionar o header Permissions-Policy negando recursos sensiveis nao usados
pela aplicacao.
```

Configuracao aplicada:

```java
httpResponse.setHeader("Permissions-Policy", "camera=(), microphone=(), geolocation=(), payment=(), usb=()");
```

Justificativa:

```text
A aplicacao nao depende dessas APIs do navegador. Negar esses recursos reduz
a superficie de ataque e melhora a postura de seguranca dos headers HTTP.
```

### Validation

Validacoes realizadas:

```text
Build da aplicacao executado com sucesso
Aplicacao executada localmente
Header Permissions-Policy validado com curl.exe -I http://localhost:8080
```

Evidencia local:

```text
Permissions-Policy: camera=(), microphone=(), geolocation=(), payment=(), usb=()
```

Criterio de fechamento:

```text
O novo scan OWASP ZAP Baseline nao deve listar o alerta
Permissions Policy Header Not Set.
```

### Status

```text
Fixed
```

## DAST-ZAP-006 - Cross-Origin-Opener-Policy Header Missing or Invalid

### Summary

O OWASP ZAP Baseline identificou que a aplicacao nao definia o header HTTP `Cross-Origin-Opener-Policy` nas respostas analisadas.

Esse header ajuda a isolar o contexto de navegacao da pagina em relacao a outras origens, reduzindo riscos associados a interacoes cross-origin e melhorando a postura de seguranca do navegador.

### Tooling

```text
Method: DAST
Tool: OWASP ZAP Baseline
Workflow: .github/workflows/security-zap.yml
Artifact: zap-baseline-report
Report: report_html.html / report_json.json / report_md.md
Alert: Cross-Origin-Opener-Policy Header Missing or Invalid
Risk: Low
Confidence: Medium
```

### Evidence

ID do achado:

```text
DAST-ZAP-006
```

Alerta reportado:

```text
Cross-Origin-Opener-Policy Header Missing or Invalid
```

URLs afetadas:

```text
http://localhost:8080
```

Metodo:

```text
GET
```

Severidade:

```text
Low
```

Confianca:

```text
Medium
```

CWE:

```text
CWE-693 - Protection Mechanism Failure
```

Evidencia observada no relatorio:

```text
O ZAP reportou ausencia ou configuracao invalida do header
Cross-Origin-Opener-Policy na resposta da aplicacao.
```

### Affected Components

Componente afetado:

```text
Respostas HTTP da interface web
```

Arquivo relacionado:

```text
src/main/java/application/SecurityHeadersFilter.java
```

Fluxos afetados:

```text
GET /
```

### Risk

A ausencia do header `Cross-Origin-Opener-Policy` pode permitir que a pagina compartilhe contexto de navegacao com documentos de outras origens.

Impactos possiveis:

```text
Isolamento insuficiente entre contextos de navegacao
Maior exposicao a interacoes cross-origin indesejadas
Politica de seguranca de navegador incompleta
```

Severidade tratada:

```text
Low
```

### Root Cause

A causa raiz foi a ausencia do header `Cross-Origin-Opener-Policy` na configuracao centralizada de headers HTTP de seguranca.

A aplicacao ja possuia outros headers defensivos, mas ainda nao definia uma politica explicita para isolamento de contexto de navegacao.

### Remediation Plan

Log ID:

```text
log-01
```

Correcao aplicada:

```text
Adicionar o header Cross-Origin-Opener-Policy com o valor same-origin.
```

Configuracao aplicada:

```java
httpResponse.setHeader("Cross-Origin-Opener-Policy", "same-origin");
```

Justificativa:

```text
O valor same-origin restringe o compartilhamento do contexto de navegacao
com documentos de outras origens, fortalecendo o isolamento da aplicacao.
```

### Validation

Validacoes realizadas:

```text
Build da aplicacao executado com sucesso
Aplicacao executada localmente
Header Cross-Origin-Opener-Policy validado com curl.exe -I http://localhost:8080
```

Evidencia local:

```text
Cross-Origin-Opener-Policy: same-origin
```

Criterio de fechamento:

```text
O novo scan OWASP ZAP Baseline nao deve listar o alerta
Cross-Origin-Opener-Policy Header Missing or Invalid.
```

### Status

```text
Fixed
```

## DAST-ZAP-007 - Cross-Origin-Resource-Policy Header Missing or Invalid

### Summary

O OWASP ZAP Baseline identificou que a aplicacao nao definia o header HTTP `Cross-Origin-Resource-Policy` nas respostas analisadas.

Esse header orienta o navegador sobre quais origens podem carregar recursos da aplicacao, reduzindo a exposicao de arquivos e respostas a leituras cross-origin indevidas.

### Tooling

```text
Method: DAST
Tool: OWASP ZAP Baseline
Workflow: .github/workflows/security-zap.yml
Artifact: zap-baseline-report
Report: report_html.html / report_json.json / report_md.md
Alert: Cross-Origin-Resource-Policy Header Missing or Invalid
Risk: Low
Confidence: Medium
```

### Evidence

ID do achado:

```text
DAST-ZAP-007
```

Alerta reportado:

```text
Cross-Origin-Resource-Policy Header Missing or Invalid
```

URLs afetadas:

```text
http://localhost:8080
```

Metodo:

```text
GET
```

Severidade:

```text
Low
```

Confianca:

```text
Medium
```

CWE:

```text
CWE-693 - Protection Mechanism Failure
```

Evidencia observada no relatorio:

```text
O ZAP reportou ausencia ou configuracao invalida do header
Cross-Origin-Resource-Policy na resposta da aplicacao.
```

### Affected Components

Componente afetado:

```text
Respostas HTTP da interface web e arquivos estaticos
```

Arquivo relacionado:

```text
src/main/java/application/SecurityHeadersFilter.java
```

Fluxos afetados:

```text
GET /
```

### Risk

A ausencia do header `Cross-Origin-Resource-Policy` deixa a aplicacao sem uma politica explicita para restringir o carregamento de seus recursos por outras origens.

Impactos possiveis:

```text
Ausencia de restricao explicita para consumo cross-origin de recursos
Maior exposicao de arquivos estaticos a contextos externos
Politica de seguranca de navegador incompleta
```

Severidade tratada:

```text
Low
```

### Root Cause

A causa raiz foi a ausencia do header `Cross-Origin-Resource-Policy` na configuracao centralizada de headers HTTP de seguranca.

A aplicacao ja definia outros headers defensivos, mas ainda nao restringia explicitamente o carregamento de recursos por outras origens.

### Remediation Plan

Log ID:

```text
log-01
```

Correcao aplicada:

```text
Adicionar o header Cross-Origin-Resource-Policy com o valor same-origin.
```

Configuracao aplicada:

```java
httpResponse.setHeader("Cross-Origin-Resource-Policy", "same-origin");
```

Justificativa:

```text
O valor same-origin limita o carregamento de recursos da aplicacao a paginas
da mesma origem, reduzindo exposicao cross-origin desnecessaria.
```

### Validation

Validacoes realizadas:

```text
Build da aplicacao executado com sucesso
Aplicacao executada localmente
Header Cross-Origin-Resource-Policy validado com curl.exe -I http://localhost:8080
```

Evidencia local:

```text
Cross-Origin-Resource-Policy: same-origin
```

Criterio de fechamento:

```text
O novo scan OWASP ZAP Baseline nao deve listar o alerta
Cross-Origin-Resource-Policy Header Missing or Invalid.
```

### Status

```text
Fixed
```

## DAST-ZAP-008 - Cross-Origin-Embedder-Policy Header Missing or Invalid

### Summary

O OWASP ZAP Baseline identificou que a aplicacao nao definia o header HTTP `Cross-Origin-Embedder-Policy` nas respostas analisadas.

Esse header ajuda a controlar como recursos cross-origin podem ser carregados pela pagina, exigindo politicas explicitas para recursos incorporados e fortalecendo o isolamento do contexto web.

### Tooling

```text
Method: DAST
Tool: OWASP ZAP Baseline
Workflow: .github/workflows/security-zap.yml
Artifact: zap-baseline-report
Report: report_html.html / report_json.json / report_md.md
Alert: Cross-Origin-Embedder-Policy Header Missing or Invalid
Risk: Low
Confidence: Medium
```

### Evidence

ID do achado:

```text
DAST-ZAP-008
```

Alerta reportado:

```text
Cross-Origin-Embedder-Policy Header Missing or Invalid
```

URLs afetadas:

```text
http://localhost:8080
```

Metodo:

```text
GET
```

Severidade:

```text
Low
```

Confianca:

```text
Medium
```

CWE:

```text
CWE-693 - Protection Mechanism Failure
```

Evidencia observada no relatorio:

```text
O ZAP reportou ausencia ou configuracao invalida do header
Cross-Origin-Embedder-Policy na resposta da aplicacao.
```

### Affected Components

Componente afetado:

```text
Respostas HTTP da interface web e arquivos estaticos
```

Arquivo relacionado:

```text
src/main/java/application/SecurityHeadersFilter.java
```

Fluxos afetados:

```text
GET /
```

### Risk

A ausencia do header `Cross-Origin-Embedder-Policy` deixa a aplicacao sem uma politica explicita para controlar recursos incorporados de outras origens.

Impactos possiveis:

```text
Isolamento cross-origin incompleto
Carregamento de recursos incorporados sem politica explicita
Politica de seguranca de navegador incompleta
```

Severidade tratada:

```text
Low
```

### Root Cause

A causa raiz foi a ausencia do header `Cross-Origin-Embedder-Policy` na configuracao centralizada de headers HTTP de seguranca.

A aplicacao ja definia headers relacionados a CSP, COOP e CORP, mas ainda nao declarava uma politica explicita para recursos incorporados.

### Remediation Plan

Log ID:

```text
log-01
```

Correcao aplicada:

```text
Adicionar o header Cross-Origin-Embedder-Policy com o valor require-corp.
```

Configuracao aplicada:

```java
httpResponse.setHeader("Cross-Origin-Embedder-Policy", "require-corp");
```

Justificativa:

```text
O valor require-corp exige politicas explicitas para recursos cross-origin
incorporados pela pagina, fortalecendo o isolamento do navegador.
```

### Validation

Validacoes realizadas:

```text
Build da aplicacao executado com sucesso
Aplicacao executada localmente
Header Cross-Origin-Embedder-Policy validado com curl.exe -I http://localhost:8080
```

Evidencia local:

```text
Cross-Origin-Embedder-Policy: require-corp
```

Criterio de fechamento:

```text
O novo scan OWASP ZAP Baseline nao deve listar o alerta
Cross-Origin-Embedder-Policy Header Missing or Invalid.
```

### Status

```text
Fixed
```
