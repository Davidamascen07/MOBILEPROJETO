# Relatório do Projeto - Aplicativo Acadêmico Móvel

## 1. Visão Geral do Projeto

### Objetivo
Desenvolver um aplicativo móvel Android para consulta de notas, faltas, horários de aulas e histórico acadêmico, facilitando o acompanhamento do desempenho dos alunos de forma centralizada.

### Nome do Projeto
**MobilIA P3** - Mobile Institutional Academic Portal

## 2. Requisitos Funcionais

### RF01 - Autenticação do Usuário
- Login com credenciais institucionais
- Armazenamento seguro de sessão

### RF02 - Consulta de Notas
- Visualização de notas por período letivo
- Exibição de notas por etapas (NT1, NT2, NT3, Média Final)
- Filtro por disciplinas
- Status da disciplina (Cursando, Aprovado, etc.)

### RF03 - Consulta de Faltas
- Visualização de faltas por período
- Controle de faltas por mês
- Indicadores visuais (dentro do limite, próximo ao limite, acima do limite)
- Cálculo automático de percentual de faltas

### RF04 - Histórico Acadêmico
- Consulta de disciplinas cursadas
- Histórico completo com situação, notas e créditos
- Carga horária integralizada
- Agrupamento por período letivo

### RF05 - Horários de Aulas
- Visualização da grade de horários semanal
- Informações de disciplinas, professores e salas

## 3. Requisitos Não Funcionais (Obrigatórios)

### RNF01 - Uso de Intents
- **Navegação entre Activities**: Transição entre telas principais
- **Intent Implícita**: Compartilhamento de boletim via WhatsApp/Email
- **Intent com Extras**: Passagem de dados entre activities

### RNF02 - Banco de Dados SQLite
- **Tabelas**:
  - `usuarios` (id, nome, matricula, curso)
  - `disciplinas` (id, codigo, nome, creditos, ch)
  - `notas` (id, disciplina_id, periodo, nt1, nt2, nt3, media_final)
  - `faltas` (id, disciplina_id, periodo, janeiro, fevereiro, marco, abril, maio, junho, total)
  - `historico` (id, disciplina_id, periodo, situacao, nota_final, ch_integralizada)

### RNF03 - SharedPreferences
- **Preferências do usuário**: Tema, período selecionado
- **Cache de login**: Manter sessão ativa
- **Configurações**: Notificações, filtros padrão

### RNF04 - Testes Automatizados
- **Testes Unitários**: Validação de cálculos e lógica de negócio
- **Testes de Integração**: Banco de dados e operações CRUD
- **Testes de Interface**: Navegação e interação do usuário

## 4. Arquitetura do Sistema

### Padrão Arquitetural: MVVM + Repository (Android Architecture Components)

#### Model (Camada de Dados)
- **Database**: SQLite com DatabaseHelper
- **Models/Entities**: Usuario, Disciplina, Nota, Falta, Historico
- **Repository**: Camada de abstração para acesso aos dados
- **SharedPreferences**: PreferencesManager para configurações

#### View (Camada de Apresentação)
- **Activities**: LoginActivity, MainActivity, NotasActivity, FaltasActivity, HistoricoActivity
- **Fragments**: Para navegação modular dentro das Activities
- **Adapters**: RecyclerView adapters para listas
- **Layouts**: XML layouts com Material Design

#### ViewModel (Camada de Lógica)
- **ViewModels**: Gerenciam estado da UI e lógica de negócio
- **Controllers/Managers**: Classes auxiliares para operações específicas
- **Utils**: Helpers para cálculos, formatação e validações

### Estrutura de Pacotes
```
com.example.mobiliap3/
├── activities/          # Activities principais
├── fragments/           # Fragments modulares
├── adapters/           # RecyclerView adapters
├── models/             # Modelos de dados
├── database/           # SQLite e Repository
├── utils/              # Utilitários e helpers
├── managers/           # Gerenciadores de funcionalidades
└── viewmodels/         # ViewModels (se necessário)
```

## 5. Estrutura de Dados

### Dados de Exemplo (Baseados nas Imagens)

#### Histórico Acadêmico
```
6º PERÍODO:
- ANÁLISE E PROJETOS DE SISTEMAS (2024.1) - APROVADO - 5,20 - 14 faltas
- COMPUTAÇÃO GRÁFICA (2024.1) - APROVADO - 5,21 - 12 faltas
- LABORATÓRIO DE PROGRAMAÇÃO I (2024.2) - APROVADO - 5,67 - 6 faltas
- INTELIGÊNCIA ARTIFICIAL (2024.2) - REPROVADO - 4,75 - 14 faltas

7º PERÍODO:
- ESTÁGIO SUPERVISIONADO I (2024.2) - APROVADO - 10,00 - 0 faltas
- TRABALHO DE CONCLUSÃO DE CURSO I (2024.2) - APROVADO - 8,90 - 0 faltas
- LABORATÓRIO DE PROGRAMAÇÃO II (2025.1) - CURSANDO - 16 faltas
- TÓPICOS EM DESENVOLVIMENTO MOBILE (2025.1) - CURSANDO - 18 faltas
```

#### Notas Atuais (2025.1)
```
- TRABALHO DE CONCLUSÃO DE CURSO II: CURSANDO
- TÓPICOS EM DESENVOLVIMENTO MOBILE: NT1: 6,00
- LABORATÓRIO DE PROGRAMAÇÃO II: NT1: 10,00, NT2: 10,00
- TÓPICOS ESPECIAIS EM ENGENHARIA DE SOFTWARE: NT1: 8,00
```

#### Faltas Atuais (2025.1)
```
- TRABALHO DE CONCLUSÃO DE CURSO II: 2 faltas (2.67%)
- TÓPICOS EM DESENVOLVIMENTO MOBILE: 18 faltas (24%)
- LABORATÓRIO DE PROGRAMAÇÃO II: 16 faltas (21.33%)
- TÓPICOS ESPECIAIS EM ENGENHARIA DE SOFTWARE: 10 faltas (22.22%)
```

## 6. Interface do Usuário

### Design System
- **Paleta de Cores**: Azul principal (#2196F3), Material Design
- **Tipografia**: Roboto (padrão Android)
- **Componentes**: Material Design Components
- **Navegação**: Bottom Navigation com Fragments

### Telas Principais
1. **Login**: Formulário de autenticação
2. **Dashboard**: Resumo geral com cards informativos (Fragment)
3. **Notas**: Lista expandível por período/disciplina (Fragment)
4. **Faltas**: Tabela com indicadores visuais de status (Fragment)
5. **Histórico**: Timeline das disciplinas cursadas (Fragment)
6. **Perfil**: Configurações e logout (Fragment)

### Fluxo de Navegação
- **LoginActivity** → **MainActivity** (com Bottom Navigation)
- **MainActivity** contém 5 Fragments principais
- **Intents explícitas** para navegação entre Activities
- **Intents implícitas** para compartilhamento

## 7. Casos de Uso dos Requisitos Obrigatórios

### Intents
- **Navegação**: Login → MainActivity → Detalhes via Intent com extras
- **Compartilhamento**: Boletim → Apps externos (WhatsApp, Email)
- **Detalhes**: Fragment → Activity de detalhes com dados específicos

### SQLite
- **Repository Pattern**: Abstração do acesso aos dados
- **CRUD Operations**: Create, Read, Update, Delete para todas as entidades
- **Data Population**: Inserção de dados de exemplo no onCreate

### SharedPreferences
- **Sessão**: Manter usuário logado
- **Filtros**: Lembrar período selecionado, preferências de exibição
- **Tema**: Modo claro/escuro (futuro)
- **Cache**: Dados frequentemente acessados

### Testes
- **Testes Unitários**: Validar cálculos de médias e percentuais
- **Testes de Integração**: Operações de banco de dados
- **Testes de UI**: Navegação e interação com Espresso

## 8. Cronograma de Desenvolvimento

### Fase 1: Estrutura Base (Semana 1-2) ✅
- Configuração do projeto
- Implementação do banco de dados
- Tela de login e autenticação

### Fase 2: Funcionalidades Core (Semana 3-4) ✅
- MainActivity com Bottom Navigation
- Fragments principais (Dashboard, Notas, Faltas)
- Repository e Adapters

### Fase 3: Histórico e Extras (Semana 5-6) ✅
- Fragment de Histórico
- Funcionalidades de compartilhamento
- Detalhamento de disciplinas

### Fase 4: Testes e Refinamento (Semana 7-8) 🔄
- Implementação de testes automatizados
- Melhorias de UX/UI
- Documentação final

## Status de Implementação

### ✅ Funcionalidades Implementadas
- **Autenticação**: Login com SQLite e SharedPreferences
- **Navegação**: Bottom Navigation com 5 Fragments
- **Dashboard**: Resumo acadêmico com cards informativos
- **Notas**: Visualização de notas por disciplina com detalhes
- **Faltas**: Controle mensal com indicadores visuais
- **Histórico**: Timeline de disciplinas por período
- **Perfil**: Informações do usuário e logout
- **Intents**: Navegação entre activities e compartilhamento
- **Repository**: Camada de abstração para dados
- **Material Design**: Interface moderna e responsiva

### 🔄 Em Desenvolvimento
- **Testes Automatizados**: Unitários e de integração
- **Validações**: Entrada de dados e tratamento de erros
- **Performance**: Otimizações e melhorias

### 📱 Telas Implementadas
1. **LoginActivity**: Formulário de autenticação
2. **MainActivity**: Container com Bottom Navigation
3. **DashboardFragment**: Resumo acadêmico
4. **NotasFragment**: Lista de notas com RecyclerView
5. **FaltasFragment**: Controle de faltas mensal
6. **HistoricoFragment**: Timeline de disciplinas
7. **PerfilFragment**: Perfil do usuário
8. **DetalhesNotaActivity**: Detalhes de disciplina

### 🗄️ Banco de Dados
- **Tabelas**: usuarios, disciplinas, notas, faltas
- **Repository**: Padrão de abstração implementado
- **Dados**: População inicial com dados de exemplo
- **Operações**: CRUD completo para todas as entidades

### 🎨 Interface
- **Material Design**: Componentes modernos
- **Paleta**: Azul principal com acentos
- **Layouts**: Responsivos e acessíveis
- **Navegação**: Intuitiva com Bottom Navigation

---

**Desenvolvedor**: David Damásceño da Frota  
**Disciplina**: Tópicos em Desenvolvimento Mobile  
**Período**: 2025.1  
**Instituição**: Sistema de Informação - Bacharelado
