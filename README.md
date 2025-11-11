# Gestao-de-veiculos
Sistema de gestão de veículos em Java com arquitetura cliente/servidor e comunicação via sockets.

🚗 Sistema de Gestão de Veículos — Cliente/Servidor (Java + Sockets)
🧩 Descrição Geral

Este projeto foi desenvolvido como parte da disciplina de Computação Paralela e Distribuída do curso de Ciência da Computação da UNIFAL-MG, no 4º período.

O sistema implementa uma aplicação cliente/servidor em Java, utilizando sockets TCP/IP para comunicação entre os módulos.
Seu objetivo é gerenciar informações de veículos de forma distribuída, permitindo a interação simultânea de vários clientes com um servidor central.
Os dados dos veículos são armazenados em arquivo, sem o uso de banco de dados, garantindo persistência simples e direta.

⚙️ Funcionalidades

O sistema oferece as seguintes operações sobre o cadastro de veículos:

🚘 Inserção — adicionar novos veículos ao arquivo de registros;

❌ Remoção — excluir veículos cadastrados;

🔍 Busca — pesquisar veículos por diferentes critérios (placa, marca, modelo, etc.);

✏️ Alteração — atualizar informações de veículos existentes.

Cada registro de veículo contém:

Placa

Marca

Modelo

Ano

Cor

Quilometragem

Valor

🧠 Arquitetura

Servidor:
Responsável por armazenar e gerenciar as informações dos veículos, processando solicitações enviadas pelos clientes.
Implementa controle de acesso concorrente ao arquivo (regiões críticas), garantindo a integridade dos dados.

Cliente:
Fornece a interface de interação com o usuário, permitindo enviar solicitações ao servidor, receber as respostas e exibir os resultados de forma clara.

🚀 Fases de Desenvolvimento

Estabelecimento da comunicação entre cliente e servidor via sockets;

Atendimento simultâneo de múltiplos clientes com controle das regiões críticas;

Testes de desempenho para avaliar a capacidade de atendimento e reduzir o tempo de resposta do servidor.

🧰 Tecnologias Utilizadas

Linguagem: Java

Comunicação: Sockets TCP/IP

Armazenamento: Arquivo texto para persistência dos dados

🎯 Objetivo

O projeto tem como objetivo aplicar, na prática, os conceitos de computação paralela e distribuída, comunicação cliente-servidor, concorrência e persistência de dados, por meio do desenvolvimento de um sistema de gestão de veículos simples, eficiente e funcional.

🏫 Instituição

Universidade Federal de Alfenas (UNIFAL-MG)
Curso: Ciência da Computação — 4º Período
Disciplina: Computação Paralela e Distribuída
