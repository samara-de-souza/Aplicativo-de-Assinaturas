# Docker Hardening Findings and Remediation Log

## CONTAINER-TRIVY-001 - Actionable Base Image curl Vulnerability

### Summary

O Trivy image scan executado no workflow `Security - Trivy` identificou vulnerabilidades corrigíveis em pacotes da imagem base utilizada pelo container da aplicação.

O artefato Java da aplicação permaneceu sem vulnerabilidades detectadas, mas a base Ubuntu presente na imagem `eclipse-temurin:21-jre` reportou findings em `curl` e `libcurl4t64`.

### Tooling

```text
Method: Container Image Scanning
Tool: Trivy
Workflow: .github/workflows/security-trivy.yml
Target: e-signature-app
Image base: eclipse-temurin:21-jre
Scanner: vuln
Report: trivy-image.txt
```

### Evidence

ID do achado:

```text
CONTAINER-TRIVY-001
```

Vulnerabilidade reportada:

```text
CVE-2026-11856
```

Pacotes afetados:

```text
curl
libcurl4t64
```

Severidade:

```text
MEDIUM
```

Status no Trivy:

```text
fixed
```

Versão instalada:

```text
8.18.0-1ubuntu2.3
```

Versão corrigida:

```text
8.18.0-1ubuntu2.4
```

Resumo do report:

```text
e-signature-app (ubuntu 26.04): 2 vulnerabilities
app/app.jar: 0 vulnerabilities
usr/bin/pebble: 8 HIGH
```

### Affected Components

Componente afetado:

```text
Imagem base do container
```

Pacotes afetados:

```text
curl
libcurl4t64
```

Componente não afetado:

```text
app/app.jar
```

Resultado da aplicação:

```text
0 vulnerabilities
```

### Risk

A vulnerabilidade está associada ao pacote `curl/libcurl` da imagem base e não ao código Java da aplicação.

Impacto potencial:

```text
Exposição a falha de informação em cenários que utilizem Digest authentication
via curl/libcurl dentro da imagem base.
```

Contexto do projeto:

```text
A aplicação e-signature-app não utiliza curl/libcurl diretamente em runtime.
O pacote vulnerável está presente como componente da imagem base upstream.
```

Severidade tratada:

```text
MEDIUM
```

### Root Cause

A causa raiz é a presença de pacotes da distribuição Ubuntu na imagem base `eclipse-temurin:21-jre` com versão corrigida disponível no repositório da distribuição.

O finding não foi introduzido por dependência Maven ou código da aplicação.

### Remediation Plan

Log ID:

```text
log-01
```

Decisão atual:

```text
Não aplicar apt upgrade manual no Dockerfile nesta etapa.
```

Justificativa:

```text
A correção pertence à camada da imagem base upstream. Aplicar upgrade manual
de pacotes no Dockerfile pode reduzir rastreabilidade e tornar a imagem menos
previsível.
```

Tratamento planejado:

```text
Habilitar Dependabot para monitorar atualizações de imagem base.
Rebuildar a imagem quando houver nova versão segura da base Eclipse Temurin.
Reexecutar Trivy image scan após atualização da imagem base.
Avaliar pinning por digest em etapa futura.
```

### Validation

Validações realizadas:

```text
Aplicação executada localmente em container
Endpoint principal validado com HTTP 200
Headers de segurança preservados após Docker hardening
Trivy image scan executado no GitHub Actions
app/app.jar validado com 0 vulnerabilidades
```

Evidência funcional:

```text
HTTP/1.1 200
```

Evidência de segurança:

```text
app/app.jar: 0 vulnerabilities
```

Critério de fechamento futuro:

```text
Novo scan Trivy image sem CVE-2026-11856 em curl/libcurl4t64 após atualização
da imagem base ou dos pacotes upstream.
```

### Status

```text
Accepted residual risk - Base image update required
```
