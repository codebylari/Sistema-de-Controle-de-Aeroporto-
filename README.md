# 🛫 Sistema de Controle de Aeroporto

## 🧾 Descrição do Projeto
O **Sistema de Controle de Aeroporto** tem como objetivo gerenciar **voos** e **passageiros**, permitindo o **cadastro de reservas**, **check-in** e **visualização de informações** sobre os voos disponíveis.  
O sistema também conta com um **usuário administrador**, responsável por confirmar as reservas e garantir que a quantidade máxima de passageiros de cada voo não seja ultrapassada.

---

## ⚙️ Funcionalidades Principais

### 👤 Para o Passageiro:
- **Reserva de Voos:** permite ao passageiro reservar um voo informando nome, idade, CPF e e-mail válido.  
- **Check-in:** após a confirmação da reserva, o passageiro pode realizar o check-in para o voo.  
- **Visualização de Voos:** o sistema exibe os voos disponíveis, os com reservas pendentes e os voos cheios.  

### 🧑‍💼 Para o Administrador:
- **Processamento de Reservas:** confirma manualmente as reservas pendentes e controla a capacidade máxima dos voos.  
- **Gerenciamento de Passageiros:** acompanha os passageiros que já realizaram o check-in.  

---

## 💾 Estruturas de Dados Utilizadas

| Estrutura | Finalidade | Descrição |
|------------|-------------|-----------|
| **Lista** | Armazenar voos | Cada voo contém número, origem, destino, horários e capacidade máxima. |
| **Fila (Queue)** | Reservas pendentes | As reservas aguardam confirmação do administrador na ordem de chegada (FIFO). |
| **Pilha (Stack)** | Check-in | Organiza passageiros na ordem de check-in (LIFO), simulando o processo de embarque. |

---

## 🔢 Validação do Número do Voo
O número do voo deve conter **exatamente 4 dígitos** (exemplo: `2904`).  
Essa regra pode ser ajustada conforme as necessidades do aeroporto.

---

## 🧳 Exibição de Informações

O sistema permite visualizar:
- **✈️ Voos disponíveis:** ainda possuem vagas.
- **🕒 Voos com reservas pendentes:** aguardam confirmação do administrador.
- **⛔ Voos cheios:** atingiram o número máximo de passageiros.

---

## 🧠 Observações Importantes

- As **reservas pendentes** são gerenciadas através de uma **fila**, garantindo ordem de chegada.
- O **check-in** é organizado com uma **pilha**, simulando o processo de embarque (último a entrar, primeiro a sair).
- A **confirmação de reservas** é manual, feita apenas por administradores para maior controle.
- Todos os códigos devem ser **entregues via GitHub**, com **participação de todos os integrantes** do grupo.

---

## 💻 Tecnologias Utilizadas

- ☕ **Java**
- 🪟 **Java Swing (Interface Gráfica)**
- 🧮 **Estruturas de Dados (List, Queue, Stack)**
- 📦 **Collections Framework**

---



