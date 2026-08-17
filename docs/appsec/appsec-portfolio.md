# AppSec Portfolio - e-signature-app

Este diretorio documenta as atividades de AppSec realizadas no projeto `e-signature-app`.

O objetivo e registrar, de forma auditavel:

- quais ferramentas de segurança foram usadas;
- como cada scan foi configurado;
- quais vulnerabilidades ou alertas foram encontrados;
- qual foi a análise técnica de cada achado;
- quais correções foram aplicadas;
- quais riscos foram aceitos, mitigados ou deixados para acompanhamento.

## Ordem dos scans

### 1. SCA - Software Composition Analysis

Objetivo: identificar vulnerabilidades em dependencias Maven declaradas no `pom.xml`.

Ferramenta inicial recomendada:

- OWASP Dependency-Check

Por que comecar por SCA:

- e direto para projetos Maven;
- gera relatorio objetivo com CVEs;
- ajuda a demonstrar gestao de dependencias vulneraveis;
- e um bom primeiro passo de AppSec em CI/CD.

Evidencia esperada:

- workflow do GitHub Actions executado;
- relatorio HTML/XML/JSON publicado como artifact;
- resumo dos achados registrado em `docs/appsec/security-scan-log.md`.

### 2. SAST - Static Application Security Testing

Objetivo: analisar o codigo-fonte em busca de padroes inseguros.

Ferramenta inicial recomendada:

- GitHub CodeQL

Por que usar CodeQL:

- integra nativamente com GitHub;
- publica achados na aba Security;
- e adequado para Java/Spring Boot;
- ajuda a demonstrar uso de code scanning em pipeline.

Evidencia esperada:

- workflow de CodeQL configurado;
- alertas publicados no GitHub Security;
- achados documentados no log de AppSec.

### 3. DAST - Dynamic Application Security Testing

Objetivo: testar a aplicacao rodando em HTTP.

Ferramenta inicial recomendada:

- OWASP ZAP Baseline Scan

Por que usar ZAP:

- e uma ferramenta OWASP conhecida;
- consegue testar endpoints HTTP;
- funciona bem contra aplicacoes rodando em container;
- gera relatorio util para portfolio.

Evidencia esperada:

- aplicacao iniciada no CI;
- scan contra `http://localhost:8080`;
- relatorio publicado como artifact;
- achados documentados no log de AppSec.

## Politica de documentacao

Cada scan relevante deve gerar uma entrada em:

```text
docs/appsec/security-scan-log.md
```

Cada entrada deve conter:

- data;
- tipo de scan;
- ferramenta;
- comando ou workflow usado;
- escopo;
- resultado;
- achados;
- severidade;
- acao tomada;
- status final.

## Status resumido

- [x] CI basico configurado no GitHub Actions.
- [ ] SCA configurado.
- [ ] SAST configurado.
- [ ] DAST configurado.
- [ ] Correcoes documentadas a partir dos scans.
