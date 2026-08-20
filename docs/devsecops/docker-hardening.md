# Docker Hardening Baseline

## Summary

Foi realizada a primeira etapa de Docker hardening do projeto `e-signature-app`, com foco em reduzir privilégios da aplicação em runtime, validar a imagem Docker após a alteração e analisar o resultado do Trivy image scan.

O objetivo desta etapa foi iniciar a fase DevSecOps do projeto sem alterar o comportamento funcional da aplicação.

## Implemented Changes

### Non-root Runtime User

A imagem final passou a criar e utilizar um usuário de sistema dedicado para executar a aplicação.

Configuração aplicada:

```dockerfile
RUN groupadd --system app && useradd --system --gid app app
RUN chown -R app:app /app
USER app
```

Resultado esperado:

```text
A aplicação não roda mais como root dentro do container.
```

## Functional Validation

A aplicação foi rebuildada, executada em container e validada localmente via `curl`.

Comando de validação:

```powershell
curl.exe -I http://localhost:8080
```

Resultado observado:

```text
HTTP/1.1 200
```

Headers de segurança preservados:

```text
Content-Security-Policy
X-Content-Type-Options
Permissions-Policy
Cross-Origin-Opener-Policy
Cross-Origin-Resource-Policy
Cross-Origin-Embedder-Policy
```

Conclusão:

```text
A alteração de runtime user não quebrou a aplicação e os headers de segurança continuaram presentes nas respostas HTTP.
```

## Trivy Image Scan Validation

Foi executado Trivy image scan após o hardening inicial.

Resultado da aplicação Java:

```text
app/app.jar: 0 vulnerabilities
```

Comparação da imagem base:

```text
Imagem base fixada em eclipse-temurin:21.0.8_9-jre:
Ubuntu 24.04: 247 vulnerabilities
HIGH: 11
CRITICAL: 0

Imagem base atual em eclipse-temurin:21-jre:
Ubuntu 26.04: 49 vulnerabilities
HIGH: 0
CRITICAL: 0
```

Resultado:

```text
A troca para a tag atual do Eclipse Temurin 21 reduziu a quantidade de
vulnerabilidades da base OS de 247 para 49.

Os findings HIGH da base OS foram reduzidos de 11 para 0.

O artefato da aplicação Java permaneceu sem vulnerabilidades detectadas.
```

## Residual Findings

O Trivy ainda reportou findings fora da aplicação Java.

Resumo:

```text
e-signature-app (ubuntu 26.04): 49 vulnerabilities
app/app.jar: 0 vulnerabilities
usr/bin/pebble: 8 HIGH
```

Análise:

```text
As 49 vulnerabilidades restantes pertencem à imagem base Ubuntu utilizada pelo Eclipse Temurin.

Os findings HIGH restantes foram identificados em usr/bin/pebble, um binário presente na imagem base upstream, não no código da aplicação.
```

Decisão:

```text
Os findings residuais foram classificados como risco de imagem base upstream e serão tratados em etapa futura de seleção, atualização e pinning de imagem base.
```

## Current Status

```text
Docker multi-stage build: implemented
Runtime image with JRE only: implemented
Application running as non-root user: implemented
Application functional validation: passed
Security headers validation: passed
Java application vulnerabilities: 0
Base OS critical vulnerabilities: 0
Base OS high vulnerabilities: 0
Residual upstream findings: documented
```


