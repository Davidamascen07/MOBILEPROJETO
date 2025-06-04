# Mobília P3 - Sistema Acadêmico Mobile

Sistema móvel para gerenciamento acadêmico desenvolvido em Android, permitindo aos estudantes acompanhar notas, faltas e histórico acadêmico.

## 📱 Funcionalidades

- **Login Seguro**: Autenticação com matrícula e senha
- **Dashboard**: Visão geral do desempenho acadêmico
- **Gestão de Notas**: Acompanhamento de NT1, NT2, NT3 e situação das disciplinas
- **Controle de Faltas**: Monitoramento mensal e percentual de faltas
- **Histórico Acadêmico**: Visualização de disciplinas cursadas por período
- **Perfil do Usuário**: Informações pessoais e acadêmicas

## 🛠️ Tecnologias

- **Linguagem**: Java
- **Plataforma**: Android (API 24+)
- **Banco de Dados**: SQLite
- **Design**: Material Design 3
- **Arquitetura**: MVC com Repository Pattern

## 🏗️ Estrutura do Projeto

```
app/
├── src/main/java/com/example/mobiliap3/
│   ├── activities/          # Atividades principais
│   ├── fragments/           # Fragmentos da interface
│   ├── adapters/           # Adaptadores para listas
│   ├── models/             # Modelos de dados
│   ├── database/           # Camada de banco de dados
│   └── utils/              # Utilitários e helpers
├── res/
│   ├── layout/             # Layouts XML
│   ├── drawable/           # Recursos gráficos
│   ├── values/             # Cores, strings, estilos
│   └── menu/               # Menus da aplicação
```

## 🎨 Design System

- **Cores Primárias**: Azul moderno (#1976D2)
- **Cores Secundárias**: Verde (#4CAF50), Vermelho (#F44336)
- **Tipografia**: Roboto (Material Design)
- **Componentes**: Material Design 3

## 🚀 Como Executar

1. **Clone o repositório**
   ```bash
   git clone https://github.com/seu-usuario/mobiliap3.git
   ```

2. **Abra no Android Studio**
   - File → Open → Selecione a pasta do projeto

3. **Sincronize as dependências**
   - O Android Studio irá baixar automaticamente

4. **Execute o projeto**
   - Conecte um dispositivo Android ou use o emulador
   - Clique em "Run" ou pressione Shift+F10

## 🔐 Credenciais de Teste

- **Matrícula**: `2810000317`
- **Senha**: `123456`

## 📊 Banco de Dados

O app utiliza SQLite com as seguintes tabelas:

- **usuarios**: Dados de login e perfil
- **disciplinas**: Informações das matérias
- **notas**: Registros de avaliações
- **faltas**: Controle de presenças

## 🎯 Funcionalidades Implementadas

- ✅ Sistema de autenticação
- ✅ Dashboard com resumo acadêmico
- ✅ Listagem de notas com status
- ✅ Controle detalhado de faltas
- ✅ Histórico acadêmico por período
- ✅ Perfil do usuário
- ✅ Interface responsiva
- ✅ Tema claro/escuro
- ✅ **Testes automatizados completos**

## 🧪 Testes Automatizados

O projeto inclui uma suíte completa de testes automatizados:

### Testes Unitários (JUnit + Mockito)
- **NotaTest**: Validação dos cálculos de notas e situações
- **FaltaTest**: Teste dos cálculos de faltas e percentuais
- **ModelTest**: Validação das regras de negócio dos modelos

### Testes de Instrumentação (Espresso)
- **LoginActivityTest**: Fluxo completo de autenticação
- **MainActivityTest**: Navegação e funcionalidades principais
- **DashboardFragmentTest**: Exibição de dados no dashboard
- **DatabaseTest**: Validação das operações de banco de dados

### Cobertura de Testes
- ✅ **Autenticação**: Login válido/inválido, validações
- ✅ **Navegação**: Bottom Navigation, transições entre fragments
- ✅ **Dados**: CRUD do SQLite, Repository Pattern
- ✅ **Cálculos**: Notas, faltas, percentuais, situações
- ✅ **Interface**: Elementos visíveis, responsividade

### Como Executar os Testes

1. **Testes Unitários**:
   ```bash
   ./gradlew test
   ```

2. **Testes de Instrumentação**:
   ```bash
   ./gradlew connectedAndroidTest
   ```

3. **Todos os Testes**:
   ```bash
   ./gradlew check connectedAndroidTest
   ```

4. **Relatório de Cobertura**:
   ```bash
   ./gradlew jacocoTestReport
   ```

## 📊 Requisitos Atendidos

| Requisito | Status | Implementação |
|-----------|--------|---------------|
| **Intents** | ✅ | Navegação entre activities e compartilhamento |
| **SQLite** | ✅ | DatabaseHelper + Repository Pattern |
| **SharedPreferences** | ✅ | PreferencesManager completo |
| **Testes Automatizados** | ✅ | **JUnit + Mockito + Espresso (COMPLETO)** |

## 👨‍💻 Desenvolvedor

**David Damásceño da Frota**
- Matrícula: 2810000317
- Curso: Sistema de Informação - Bacharelado

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos.

---

*Desenvolvido com 💙 para a disciplina de Desenvolvimento Mobile*
