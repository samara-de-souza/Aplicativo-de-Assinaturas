# AppSec Portfolio - e-signature-app

Este diretorio documenta as atividades de AppSec realizadas no projeto `e-signature-app`.

O objetivo e registrar, de forma auditavel:

- quais ferramentas de seguranca foram usadas;
- como cada scan foi configurado;
- quais vulnerabilidades ou alertas foram encontrados;
- qual foi a analise tecnica de cada achado;
- quais correcoes foram aplicadas;
- quais evidencias foram preservadas;
- quais riscos foram mitigados, aceitos ou deixados para acompanhamento.

## Estrutura

Os registros de achados ficam separados dos relatorios brutos ou semibrutos das ferramentas.

```text
docs/appsec/
├── appsec-portfolio.md
├── logs/
│   └── <tool>/
│       └── <yyyy-mm>/
│           └── <tool>-findings.md
└── reports/
    └── <tool>/
        └── <yyyy-mm>/
            └── <report-file>
```

## Esteira de AppSec

### 1. Trivy

Tipo:

```text
SCA
Container image scan
Secret scan
Misconfiguration scan
```

Uso no projeto:

- scan do repositorio;
- scan da imagem Docker `e-signature-app`;
- coleta de relatorios como artifacts no GitHub Actions;
- registro dos achados confirmados em `docs/appsec/logs/trivy/2026-08/trivy-findings.md`;
- armazenamento de evidencia em `docs/appsec/reports/trivy/2026-08/`.

### 2. Snyk

Tipo:

```text
SCA
SAST
Container scan
```

### 3. GitHub CodeQL

Tipo:

```text
SAST
```

### 4. OWASP ZAP

Tipo:

```text
DAST
```

## Padrao de IDs

Cada vulnerabilidade recebe um ID no formato:

```text
<METHOD>-<TOOL>-<NUMBER>
```

Exemplos:

```text
SCA-TRIVY-001
SCA-SNYK-001
SAST-CODEQL-001
DAST-ZAP-001
```

Quando uma mesma vulnerabilidade tiver mais de um registro operacional, os registros internos usam:

```text
log-01
log-02
log-03
```