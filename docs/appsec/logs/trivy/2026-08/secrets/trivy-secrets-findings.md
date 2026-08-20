# Trivy Secret Scanning Findings

## SECRET-TRIVY-001 - Repository Secret Scan Baseline

### Summary

Foi executado secret scanning no repositório `e-signature-app` com Trivy para verificar a presença de credenciais, tokens, chaves privadas ou outros segredos expostos no código-fonte.

O scan foi executado via GitHub Actions como parte do workflow de segurança com Trivy. O resultado não identificou secrets no repositório analisado.

### Tooling

```text
Method: Secret Scanning
Tool: Trivy
Workflow: .github/workflows/security-trivy.yml
Target: repository filesystem
Scanner: secret
Execution date: 2026-08-19
Trivy version: 0.74.0
```

### Evidence

Reports gerados pelo workflow:

```text
docs/appsec/reports/trivy/2026-08/secrets/trivy-secrets.txt
docs/appsec/reports/trivy/2026-08/secrets/trivy-secrets.json
```

Dados técnicos do report JSON:

```text
ReportID: 01a01c7d-a568-7621-963b-59e1d2ec96c2
ArtifactName: .
ArtifactType: repository
Branch: main
Commit: 37158e99c9d458c67fd66ea60fcb2e586942eb59
```

Resultado observado:

```text
No secrets detected
```

### Affected Components

Componente analisado:

```text
Repository filesystem
```

Escopo:

```text
Código-fonte, arquivos de configuração e arquivos versionados do projeto
```

Arquivos ignorados no secret scan:

```text
target/
pom.xml
```

Justificativa:

```text
O diretório target é gerado pelo build Maven e não deve ser versionado.
O arquivo pom.xml foi ignorado no job específico de secrets para evitar
resolução remota de dependências Maven durante o secret scanning.
A análise de dependências continua coberta pelos scans SCA do Trivy.
```

### Risk

Nenhum segredo foi identificado no repositório durante esta execução.

Risco residual:

```text
Baixo, considerando o escopo analisado e a ausência de findings no report.
```

Observação:

```text
Secret scanning deve continuar executando automaticamente no pipeline para
bloquear novos segredos caso sejam introduzidos no repositório.
```

### Root Cause

Não houve vulnerabilidade ou exposição de segredo identificada neste baseline.

Este registro documenta a implementação e validação do controle preventivo de secret scanning no pipeline.

### Remediation Plan

Log ID:

```text
log-01
```

Ação realizada:

```text
Configurar execução explícita do Trivy secret scanning no GitHub Actions.
Gerar reports em formato TXT e JSON.
Adicionar gate para bloquear o pipeline caso secrets sejam detectados.
```

Correção aplicada:

```text
Nenhuma correção de código foi necessária, pois não foram encontrados secrets.
```

### Validation

Validações realizadas:

```text
Workflow Security - Trivy executado no GitHub Actions
Report TXT gerado como evidência humana
Report JSON gerado como evidência técnica
Nenhum secret identificado no resultado do scan
```

Critério de fechamento:

```text
O secret scanning deve concluir sem findings e manter o gate ativo para
bloquear futuras exposições de credenciais.
```

### Status

```text
Closed - No findings
```
