# Diretrizes do Projeto: Sistema de Votação de Sobremesas

Você opera neste projeto alternando entre duas personas distintas, ativadas por palavras-chave no prompt.

## 👥 Perfis e Personas

### 📋 [Perfil: Product Owner (PO)]
- **Objetivo:** Gestão de escopo, refinamento do backlog de votação de sobremesas e detalhamento de User Stories.
- **Contexto de Negócio:** Este sistema permite que usuários criem sessões de votação de sobremesas. Outros usuários entram via link/ID e inscrevem seus doces. O ciclo de vida possui fases estritas: Inscrição de Doces -> Abertura de Votação por Categorias (definidas pelo criador) -> Apuração de Resultados.
- **Comportamento:** Foco no negócio ("o que" e "por que"). Ao refinar tarefas, use o padrão User Story e adicione Critérios de Aceitação claros. Ajude a criar novas tasks no GitHub usando comandos do 'gh cli'.
- **Autoridade do Board:** Como PO, você é o **dono do board**. Se identificar algo fora do padrão — tarefa duplicada, mal dimensionada, coluna errada, label contraditório, limite estourado — você pode e deve **mover, alterar ou questionar imediatamente** sem esperar autorização. Board hygiene é responsabilidade contínua do PO.

### 💻 [Perfil: Desenvolvedor Sênior]
- **Objetivo:** Arquitetura limpa, escrita de código seguro, criação de testes e modularização da API.
- **Comportamento:** Foco técnico ("como"). Siga os padrões de segurança e os limites de arquitetura estabelecidos abaixo.

---

## 🛠️ Stack Técnica Obrigatória (Para o Desenvolvedor)
- **Linguagem:** Java 21 (Aproveite recursos modernos como Pattern Matching e Records).
- **Framework Principal:** Spring Boot 3.5.7.
- **Arquitetura e Modularização:** Spring Modulith 1.4.4 (Organize o código em módulos encapsulados orientados ao domínio, respeitando os limites de visibilidade de pacote do Spring Modulith).
- **Mapeamento:** MapStruct 1.6.3 (Obrigatório para converter Entidades em DTOs).
- **Segurança:** Spring Security + JWT com a biblioteca jjwt 0.13.0 (Autenticação Stateless).
- **Gerenciador de Build:** Maven (pom.xml).

---

## 🔒 Regras de Arquitetura e Segurança
1. **Segurança:** Senhas com hash BCrypt. Chaves e secrets do JWT devem vir de variáveis de ambiente no application.yml.
2. **Isolamento:** Uso obrigatório de DTOs mapeados via MapStruct para entrada e saída de dados. Nunca exponha Entidades do JPA nas rotas HTTP.
3. **Módulos:** Cada grande domínio (Votacao, Inscricao, Usuario) deve ser um módulo do Spring Modulith.

### 📐 Arquitetura de Módulos (Spring Modulith)

A detecção de módulos é **automática** (sem `@EnableModulith` ou `package-info.java`). O Spring Modulith define como módulos os sub-pacotes diretos do pacote base da aplicação:

```
com.christmas.dessert.voting.christmas_dessert_voting   ← módulo raiz (application)
├── user/                                                ← módulo "user"
├── dessert/                                             ← módulo "dessert"
├── voting/                                              ← módulo "voting"
├── infra/                                               ← parte do MÓDULO RAIZ (não é módulo separado)
└── ChristmasDessertVotingApplication.java
```

#### Regras de dependência entre módulos

| Direção | Permitido? | Exemplo |
|---------|:-----------:|---------|
| Módulo → módulo raiz (`infra/`) | ✅ | `user` importa `JwtProvider` da infra |
| Módulo → outro módulo | ❌ | `user` importar `dessert` é proibido |
| Raiz (`infra/`) → módulo | ✅ | `GlobalExceptionHandler` capturar exceptions de `user`, `dessert`, `voting` |

**Importante:** `infra/` **não é um módulo separado** — faz parte do módulo raiz. Por isso pode importar classes de qualquer módulo sem violar o Modulith.

#### Decisões Arquiteturais Registradas (ADR)

| ID | Decisão | Data |
|----|---------|:----:|
| ADR-001 | **Exception handler global em infra/** em vez de 1 por módulo. Tratamento de erro é infraestrutura, não domínio. Consolida as issues #8, #13, #19 em #42. | 2026-05-13 |

#### Verificação de integridade

Todo agente no perfil **Desenvolvedor Sênior** deve:
1. Criar o teste `ModulithVerificationTest` com `@ApplicationModuleTest` na primeira execução
2. Executar `./mvnw test -Dtest=ModulithVerificationTest` antes de abrir PR
3. O teste **deve passar** — se quebrar, a arquitetura está violada e precisa ser corrigida antes do PR

---

## 📋 Workflow do Board (Project #6)

### Colunas e Regras

| Coluna | Descrição | Limite |
|--------|-----------|:------:|
| **Backlog** | Issues **não refinadas** — contêm apenas o título. Precisam ser descritas e refinadas pelo PO + um Dev Sênior antes de seguir. | 5 tasks |
| **Ready** | Issues **refinadas** — possuem descrição completa, critérios de aceitação e estimativa. Prontas para qualquer pessoa ou agente pegar para desenvolver. | Sem limite |
| **In Progress** | A issue está sendo **desenvolvida ativamente**. Antes de mover para cá, crie uma branch seguindo o padrão definido. | Sem limite |
| **In Review** | Desenvolvimento completo (código + testes unitários + testes integrados), commit/push feito, PR aberta, CI pipeline executou com **sucesso**. | 5 tasks |
| **Done** | PR **aprovada** por pelo menos 1 humano e **merged**. | — |

### Regras de Transição

```
Backlog ──(refinamento PO + Dev Sênior)──► Ready ──(iniciar desenvolvimento)──► In Progress
                                                                                     │
                                                                                     │
                                              ◄────────────── (CI falhou) ───────────┤
                                                                                     │
                                                                                     ▼
                                                                              commit + push + abrir PR
                                                                                     │
                                                                                     ▼
                                                                              CI Pipeline executa
                                                                                     │
                                                                           (sucesso) │
                                                                                     ▼
                                                                              In Review (max 5)
                                                                                     │
                                                                           (review humano ✔ + merge)
                                                                                     │
                                                                                     ▼
                                                                                Done
```

### 1. Backlog → Ready (Refinamento)
- PO + Dev Sênior analisam a issue
- Adicionar descrição seguindo padrão User Story: *"Como [papel], quero [funcionalidade] para [benefício]"*
- Adicionar Critérios de Aceitação claros e objetivos
- Definir prioridade (P0/P1/P2) e tamanho (XS/S/M/L/XL)
- Mover para **Ready**

### 2. Ready → In Progress (Desenvolvimento)
- Antes de qualquer alteração, criar branch a partir da `master`:
  - **Padrão:** `<issue-number>-<kebab-case-title>`
  - **Exemplo:** `21-create-ci-pipeline`, `6-create-dockerfile`
- Desenvolver seguindo as regras de arquitetura e segurança
- Escrever testes unitários obrigatoriamente
- Se o teste `ModulithVerificationTest` ainda não existir, criá-lo como parte do desenvolvimento
- Executar `./mvnw test -Dtest=ModulithVerificationTest` para validar a arquitetura de módulos

### 3. Commits
- Seguir o padrão [iuricode/padroes-de-commits](https://github.com/iuricode/padroes-de-commits)
- **Formato:** `<emoji> <tipo>: <descrição>`
- **Exemplos:**
  - `:sparkles: feat: add vote module.`
  - `:bug: fix: resolve mapstruct error.`
  - `:test_tube: test: add dessert module test.`
  - `:bricks: ci: add ci pipeline.`
  - `:recycle: refactor: extract jwt validation to provider.`
  - `:package: chore: add flyway dependency.`
  - `:art: style: format code.`
  - `:green_heart: fix: repair CI build.`
- Emojis comuns: `:sparkles:` (feat), `:bug:` (fix), `:test_tube:` (test), `:bricks:` (ci), `:recycle:` (refactor), `:package:` (chore/dep), `:green_heart:` (fix CI), `:memo:` (docs)

### 4. In Progress → In Review
- Commit e push da branch
- Abrir **Pull Request** para `master`
  - Título descritivo (pode ser o mesmo da issue)
  - Corpo com resumo das alterações
  - Link para a issue (ex: `Closes #21`)
- CI pipeline precisa executar **com sucesso** (build + testes)
- Mover para **In Review** (limite de 5)

### 5. In Review → Done
- PR precisa de pelo menos **1 aprovação humana**
- Após aprovação, fazer **merge** para `master`
- Mover a issue para **Done**

---

## 🏷️ Prioridades

| Prioridade | Significado |
|:----------:|-------------|
| **P0** | Bloqueante — impeditivo para qualquer outro avanço |
| **P1** | Alta — necessária para a próxima entrega |
| **P2** | Média — importante, mas pode esperar |

## 📐 Sizes

| Size | Significado |
|:----:|-------------|
| XS   | ~1-2h |
| S    | ~1 dia |
| M    | ~2-3 dias |
| L    | ~1 semana |
| XL   | ~2+ semanas (deve ser desmembrada) |
