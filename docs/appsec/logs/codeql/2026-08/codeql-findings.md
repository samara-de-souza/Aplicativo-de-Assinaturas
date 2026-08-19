# AppSec Findings and Remediation Log - CodeQL

Este documento centraliza os achados identificados com GitHub CodeQL no projeto `e-signature-app`.

## SAST-CODEQL-001 - User-Controlled Data in Arithmetic Expression

### Summary

O CodeQL identificou que a aplicacao usa um valor controlado pelo usuario em uma expressao aritmetica no fluxo de registro de pagamento.

O valor `mes` e recebido a partir da requisicao e usado na expressao `mes - 1` antes de ser aplicado ao `Calendar`. Sem validacao previa, valores fora do intervalo esperado podem gerar datas incorretas ou comportamento de negocio inesperado.

### Tooling

```text
Method: SAST
Tool: GitHub CodeQL
Workflow: .github/workflows/codeql.yml
Alert: User-controlled data in arithmetic expression
Rule ID: java/tainted-arithmetic
Severity: High
```

### Evidence

ID do achado:

```text
SAST-CODEQL-001
```

Arquivo afetado:

```text
src/main/java/domain/service/PagamentoService.java
```

Linha reportada:

```text
26
```

Trecho vulneravel:

```java
cal.set(Calendar.MONTH, mes - 1);
```

Entrada controlada pelo usuario:

```text
mes
```

Mensagem do CodeQL:

```text
This arithmetic expression depends on a user-provided value, potentially causing an underflow.
```

### Affected Components

Componente afetado:

```text
domain.service.PagamentoService
```

Metodo afetado:

```text
registrarPagamento(Long codAssinatura, int dia, int mes, int ano, Double valorPago)
```

Fluxo afetado:

```text
POST /registrarpagamento
```

### Risk

O uso de valores de data sem validacao pode permitir que o usuario envie meses invalidos, como `0`, numeros negativos ou valores acima de `12`.

Impactos possiveis:

```text
Registro de pagamento com data incorreta
Alteracao indevida da logica de vigencia
Comportamento inesperado na normalizacao de datas pelo Calendar
Falha de integridade em dados de pagamento
```

Severidade tratada:

```text
High
```

### Root Cause

A causa raiz foi a ausencia de validacao de entrada antes do uso de `dia`, `mes` e `ano` em operacoes de data.

O metodo aceitava diretamente valores vindos da requisicao e usava `mes - 1` para adaptar o mes ao indice usado por `Calendar`, sem garantir que `mes` estivesse no intervalo valido de `1` a `12`.

### Remediation Plan

Log ID:

```text
log-01
```

Correcao planejada:

```text
Validar os campos de data antes de construir o Calendar.
```

Criterios de validacao:

```text
dia deve estar entre 1 e 31
mes deve estar entre 1 e 12
ano deve estar dentro de um intervalo aceitavel para o dominio da aplicacao
```

### Validation

Validacoes planejadas:

```text
Executar testes automatizados
Executar CodeQL novamente
Confirmar que o alerta SAST-CODEQL-001 foi removido ou justificado
Validar que entradas invalidas sao rejeitadas antes do calculo de data
```

### Status

```text
Fixed
```
