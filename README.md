# desafio-analise-credito

O sistema se encontra hospedado no endereço: "https://analise-credito.herokuapp.com".

Há dois usuários no sistema:
 - usuario: analista@cdt.com.br | senha: abcd1234;
 - usuario: captacao@cdt.com.br | senha: abcd1234;

O sistema hospedado está com banco de dados povoado. O dump do mesmo se encontra na raíz do projeto com o nome "dump.backup";

Para executar o sistema localmente é necessário possuir o banco de dados Postgresql instalado com uma base de dados criada com o nome "credito".
Para rodar o sistema deve-se apenas executar o comando "gradle bootrun" na raíz do projeto, após o servidor iniciar pode-se acessar a aplicação web através do endereço "localhost:8080".

O backend do sistema foi construído utilizando o framework SpringBoot, em sua versão 1.5.7.RELEASE, e fornece APIs REST.
O frontend do sistema foi construído utilizando o framework Angular, em sua versão 5.0.
O banco de dados utilizado é o PostgreSql 9.6.8.
O JDK utilizado foi o 1.8.
O sistema de automatização de builds utilizado foi o Gradle, em sua versão 4.3.1.
O SpringBoot utiliza o "spring-boot-starter-test", que faz uso do JUnit 4.12.

O sistema possui autenticação através de usuário e senha, e token para acessar as APIs. Na tela de login o usuário deve inserir suas credenciais (email e senha), assim como também preencher um campo de captcha para ter acesso ao sistema.

O usuário de captação tem acesso ao cadastro dos portadores de crédito que serão analisados e acesso a listagem e consulta dos portadores de crédito apenas que já foram analisados.

O usuário de análise tem acesso à listagem dos portadores de crédito cadastrados e que estão à espera da análise de crédito, e também acesso à funcionalidade de análise de crédito dos portadores.

Foi utilizado o Swagger para gerar a documentação das APIs. Esse serviço pode ser encontrado em "http://analise-credito.herokuapp.com/swagger-ui.html#/" e também rodando localmente por "localhost:8080/swagger-ui.html#/".


 
