# TodoList
Projeto final do Bootcamp Santander Mobile Developer da DIO.

A proposta deste Lab é montar um app To Do List do zero com Kotlin. Além disto, desafiar a evolução do App e entregar uma solução mais robusta pensando sempre na melhor experiência do usuário.

Criado o layout e o código base do app, como desafio o instrutor propôs o aluno a incrementar o app com um solução que os dados persistissem e não fossem perdidos ao fechar o app. Como exemplo foram apresentados Room e DataStore.

Como solução apliquei o Room Database Android, que são representadas por três componentes:

Entity: Entidades responsáveis por mapear as tabelas.

DAO (Data Access Object): são as interfaces utilizadas para acessar os dados armazenados no banco.

Database: Representação da classe abstrata do Banco de Dados.

Apliquei também como melhoria um pequeno ajuste no ícone de retorno na toolbar da Activity de adicionar tarefa.
