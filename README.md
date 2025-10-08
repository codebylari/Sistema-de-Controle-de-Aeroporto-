# Sistema de Controle de Aeroporto ✈️

Um aeroporto deseja desenvolver um sistema de controle que gerencie **voos e passageiros**.  

O sistema permite:  
- Para passageiros: reserva de voos, check-in e exibição de informações sobre os voos.  
- Para administradores: processamento das reservas e controle do número máximo de passageiros.  

---

## Funcionalidades do Sistema

Ao acessar o sistema, o usuário terá acesso a opções para:  
- Reservar voos  
- Realizar check-in  
- Visualizar informações sobre os voos disponíveis  

Os voos são armazenados em uma lista com as seguintes informações:  
- Número do voo  
- Origem  
- Destino  
- Horário de partida  
- Horário de chegada  
- Quantidade máxima de passageiros  

---

## Validação do Número do Voo

O número do voo deve conter **4 dígitos** (exemplo: `2904`).  
Essa restrição pode ser ajustada conforme a necessidade, mas neste sistema o padrão é 4 dígitos.

---

## Reservas de Voos

- Os passageiros podem reservar um voo específico.  
- Para reservar, o sistema solicita:  
  - Nome  
  - Idade  
  - CPF  
  - E-mail válido  
- As reservas são armazenadas em uma **fila** (FIFO), para gerenciar as reservas pendentes que ainda não foram confirmadas pelo administrador.

---

## Processamento de Reservas (Administrador)

- O administrador confirma as reservas pendentes.  
- Garante que o número de passageiros não ultrapasse a **capacidade máxima do voo**.  
- Esse processo é **manual**, permitindo maior controle sobre o gerenciamento das reservas.

---

## Check-In

- Uma vez confirmada a reserva, o passageiro pode realizar o **check-in**.  
- Os passageiros que já fizeram o check-in são armazenados em uma **pilha** (LIFO – último a entrar, primeiro a sair).  
- A pilha facilita o gerenciamento da ordem de embarque.

---

## Exibição de Informações

O sistema deve fornecer ao usuário:  
- **Voos disponíveis:** Voos que ainda possuem vagas  
- **Voos com reservas pendentes:** Voos que têm reservas realizadas, mas ainda não confirmadas  
- **Voos cheios:** Voos que atingiram a capacidade máxima  

Essas informações são exibidas de forma clara, permitindo que os passageiros saibam:  
- Quais voos estão disponíveis  
- Quais possuem reservas pendentes  
- Quais já estão cheios  

---

## Observações sobre Estruturas de Dados

- **Fila para reservas pendentes:** Armazena as reservas em ordem de chegada, permitindo que o administrador processe conforme a prioridade.  
- **Pilha para check-in:** Organiza os passageiros que realizaram check-in. Embora a ordem LIFO seja usada, simplifica a simulação do processo de embarque.
