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

### RF03 - Consulta de Faltas ⚠️ **ATUALIZADO**
- Visualização de faltas por período
- Controle de faltas por mês
- Indicadores visuais (dentro do limite <20%, próximo ao limite 20-24%, acima do limite ≥25%)
- Cálculo automático de percentual de faltas baseado na carga horária
- **Nova regra**: ≥25% de faltas = reprovação automática

### RF04 - Histórico Acadêmico
- Consulta de disciplinas cursadas
- Histórico completo com situação, notas e créditos
- Carga horária integralizada
- Agrupamento por período letivo
- Indicação de reprovação por falta vs. nota

### RF05 - Horários de Aulas
- Visualização da grade de horários semanal
- Informações de disciplinas, professores e salas

## 3. Regras de Negócio Atualizadas

### RN01 - Cálculo de Faltas
- **Limite máximo**: 25% da carga horária total
- **Status dos indicadores**:
  - 🟢 **Dentro do limite**: < 20%
  - 🟡 **Próximo ao limite**: 20% ≤ percentual < 25%
  - 🔴 **Acima do limite**: ≥ 25% (reprovação automática)

### RN02 - Cálculo de Notas
- **Média final**: (NT1 + NT2 + NT3) / 3
- **Aprovação direta**: Média ≥ 7,0
- **NAF necessária**: 4,0 ≤ Média < 7,0
- **Nota final com NAF**: (Média + NAF) / 2
- **Aprovação com NAF**: Nota final ≥ 5,0

### RN03 - Situação Final
- **Aprovado**: Nota final ≥ 5,0 E faltas < 25%
- **Reprovado por nota**: Nota final < 5,0
- **Reprovado por falta**: Faltas ≥ 25%
- **Cursando**: Disciplina em andamento

## Status de Implementação - **ATUALIZADO** 

### ✅ Funcionalidades Corrigidas
- **Regra de Faltas**: Corrigida para ≥25% como limite máximo
- **Cálculo de Percentual**: Melhorado considerando carga horária real
- **Validações**: Adicionadas validações nos modelos
- **Indicadores Visuais**: Atualizados com as novas regras

### 🔄 Melhorias Implementadas
- **Modelos**: Métodos de cálculo mais robustos
- **Repository**: Correção nos dados de exemplo
- **Interface**: Indicadores visuais melhorados
- **Documentação**: Atualizada com as novas regras

### 📊 Dados de Exemplo Corrigidos
```
Faltas Atuais (2025.1) - COM NOVA REGRA:
- TRABALHO DE CONCLUSÃO DE CURSO II: 2 faltas (2.67%) 🟢
- TÓPICOS EM DESENVOLVIMENTO MOBILE: 18 faltas (24%) 🟡
- LABORATÓRIO DE PROGRAMAÇÃO II: 16 faltas (21.33%) 🟡  
- TÓPICOS ESPECIAIS EM ENGENHARIA DE SOFTWARE: 19 faltas (25.3%) 🔴 ⚠️
```

---

**Desenvolvedor**: David Damásceño da Frota  
**Disciplina**: Tópicos em Desenvolvimento Mobile  
**Período**: 2025.1  
**Instituição**: Sistema de Informação - Bacharelado

**Última Atualização**: Correção das regras de faltas (≥25% = reprovação)
