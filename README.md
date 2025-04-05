Informações básicas
Projeto desenvolvido em Spring Boot que implementa uma API RESTful para gerenciamento de cadastros de alunos, professores e funcionários. A aplicação utiliza um único controller (CadastroController) para realizar operações CRUD (Create, Read, Update, Delete) sobre essas três entidades, com validação de CPF e restrições específicas, como a permissão de atualização apenas para alunos maiores de 18 anos.

Funcionalidades
Alunos:
Cadastro, listagem, consulta por CPF, atualização (apenas para maiores de 18 anos) e exclusão.
Campos: ID, Nome, Sobrenome, Idade, CPF.
Professores:
Cadastro, listagem, consulta por CPF, atualização e exclusão.
Campos: ID, Nome, Sobrenome, CPF.
Funcionários:
Cadastro, listagem, consulta por CPF, atualização e exclusão.
Campos: ID, Nome, Sobrenome, CPF.
Validação:
CPF com 11 dígitos é obrigatório para todas as entidades.
Não permite duplicidade de CPF dentro de cada categoria.
Tecnologias Utilizadas
Java: Linguagem principal (versão 17 ou superior).
Spring Boot: Framework para criação da API REST (versão 3.2.4).
Maven: Gerenciador de dependências.
Jakarta Validation: Para validação de dados (ex.: @NotBlank, @Min).

Pré-requisitos
Java JDK 17 ou superior
Maven 3.6 ou superior
IDE (ex.: VS Code com extensões Java/Spring Boot) ou terminal para execução
Cliente HTTP para testes (ex.: Insomnia ou Postman)
