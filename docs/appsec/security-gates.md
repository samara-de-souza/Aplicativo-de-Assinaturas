# AppSec Security Gates

## Objective

Definir quais resultados de segurança bloqueiam o pipeline do projeto `e-signature-app` e quais resultados devem ser registrados como evidência, backlog ou risco aceito.

Esta política representa a primeira versão dos security gates do projeto, criada após a execução e revalidação dos controles de SCA, SAST, DAST e secret scanning.

## Current Gate Policy

| Controle | Ferramenta | Bloqueia pipeline | Critério |
| --- | --- | --- | --- |
| Secret scanning | Trivy | Sim | Qualquer secret detectado no repositório |
| SCA Critical | Trivy | Planejado | Vulnerabilidade `CRITICAL` confirmada e com correção disponível |
| SCA High | Trivy | Não inicialmente | Gerar artifact, analisar impacto e priorizar correção |
| SCA Medium/Low | Trivy | Não | Registrar artifact e tratar por prioridade |
| SAST High | GitHub CodeQL | Planejado | Alerta `High` confirmado no código da aplicação |
| DAST High/Medium | OWASP ZAP | Planejado | Novo finding `High` ou `Medium` após baseline limpa |
| DAST Low | OWASP ZAP | Não | Documentar, analisar contexto e priorizar quando fizer sentido |
| Informational | OWASP ZAP | Não | Registrar como contexto técnico, sem bloqueio |

## Current Implementation Status

```text
Trivy secret scanning gate: enabled
Trivy SCA vulnerability gate: planned
GitHub CodeQL gate: planned
OWASP ZAP DAST gate: planned
Branch protection rules: planned
```

## Gate Rationale

A política começa bloqueando apenas riscos de alto impacto e baixa ambiguidade.

Secrets expostos devem bloquear o pipeline porque podem representar credenciais reais, tokens de acesso ou chaves privadas. Nesses casos, a ação correta é impedir o merge, remover o segredo, revogar a credencial e registrar a correção.

Vulnerabilidades críticas em dependências também devem evoluir para bloqueio, mas somente após a baseline inicial estar documentada. Essa abordagem evita falhas constantes no pipeline enquanto os findings existentes ainda estão sendo triados.

Findings de severidade média, baixa ou informativa não bloqueiam inicialmente. Eles devem gerar evidência, análise técnica e priorização no backlog de segurança.

## Tool-Specific Policy

### Trivy - Secret Scanning

Status:

```text
Enabled
```

Critério de bloqueio:

```text
Qualquer secret detectado pelo Trivy no repositório.
```

Ação esperada:

```text
Falhar o pipeline
Remover o segredo do código
Revogar ou rotacionar a credencial caso seja real
Documentar o finding e a remediação
Executar novo scan de validação
```

### Trivy - SCA

Status:

```text
Observation mode
```

Critério atual:

```text
Gerar reports e artifacts sem bloquear o pipeline.
```

Evolução planejada:

```text
Bloquear vulnerabilidades CRITICAL confirmadas e com correção disponível.
Avaliar bloqueio de vulnerabilidades HIGH após estabilização da baseline.
```

### GitHub CodeQL - SAST

Status:

```text
Observation mode
```

Critério atual:

```text
Gerar alertas em Security and quality > Code scanning.
```

Evolução planejada:

```text
Bloquear pull requests com alertas High confirmados.
Registrar falso positivo somente com justificativa técnica.
```

### OWASP ZAP - DAST

Status:

```text
Observation mode
```

Critério atual:

```text
Gerar artifacts e documentar findings confirmados.
Não bloquear por alertas Informational.
```

Evolução planejada:

```text
Bloquear novos findings High ou Medium após baseline limpa.
Manter findings Low como backlog priorizado.
```

## Exception Handling

Exceções só devem ser aceitas quando houver justificativa técnica clara.

Cada exceção deve registrar:

```text
ID do finding
Ferramenta
Severidade
Justificativa técnica
Evidência analisada
Responsável pela decisão
Data de revisão
Condição para reabrir o risco
```

## Decision Table

| Resultado | Ação |
| --- | --- |
| Secret confirmado | Bloquear pipeline, remover e revogar credencial |
| Vulnerabilidade CRITICAL confirmada | Bloquear após baseline e correção disponível |
| Vulnerabilidade HIGH com correção disponível | Analisar e evoluir para bloqueio gradual |
| Vulnerabilidade HIGH sem correção disponível | Registrar risco, acompanhar atualização e criar backlog |
| Vulnerabilidade Medium/Low | Registrar artifact e priorizar |
| CodeQL High confirmado | Corrigir antes do merge |
| ZAP High/Medium após baseline limpa | Corrigir antes do merge |
| ZAP Informational | Não bloquear |
| Falso positivo | Documentar evidência e data de revisão |

## Validation Evidence

Controles já executados no projeto:

```text
SCA com Trivy executado e findings corrigidos
Secret scanning com Trivy executado sem secrets detectados
SAST com GitHub CodeQL executado e finding corrigido
DAST com OWASP ZAP executado e findings corrigidos
OWASP ZAP revalidado com 0 High, 0 Medium e 0 Low
```

## Next Steps

```text
Configurar branch protection rules no GitHub
Exigir checks obrigatórios antes de merge na branch main
Evoluir Trivy SCA para bloquear vulnerabilidades CRITICAL confirmadas
Evoluir CodeQL para gate de alertas High confirmados
Evoluir OWASP ZAP para gate de novos findings High ou Medium
```
