# PizzariaSoap

**Desenvolvido por:**  
**Erick Daniel Teixeira Vier** – RA: 235908-1

### Descrição
Este projeto tem como objetivo criar um **Web Service SOAP** para uma pizzaria, proporcionando uma experiência interativa para os clientes. O sistema permitirá que os usuários se cadastrem, façam pedidos de pizzas de forma prática e acompanhem o progresso de seus pedidos em tempo real. Além disso, o sistema calculará automaticamente o valor total do pedido, garantindo que as informações sejam entregues de maneira precisa e acessível a cada cliente.

### Funcionalidades
- **Cadastro de Clientes:** Os clientes podem se cadastrar informando nome, CPF, telefone, endereço e data de nascimento.
- **Fazer Pedido:** O cliente pode selecionar os sabores de pizza, borda, e quantidade de pizzas. O valor total do pedido é calculado automaticamente.
- **Consultar Status do Pedido:** Os clientes podem consultar o status atual de seu pedido.
- **Cálculo Automático do Valor:** O valor total do pedido é calculado com base no tipo de pizza, borda e quantidade.

### Tecnologias Utilizadas
- **Java 17**
- **Spring Boot** (para configuração do SOAP e integração)
- **Hibernate** (para persistência de dados)
- **PostgreSQL** (para banco de dados)
- **JPA** (Java Persistence API) 
- **Maven** (gerenciador de dependências)
- **SOAP Web Services** (para comunicação via XML)

### Como Rodar o Projeto

Para rodar este projeto em seu ambiente local, siga os passos abaixo.

#### 1. **Clone o repositório**
Primeiramente, clone o repositório para o seu computador.

```bash
git clone https://github.com/ErickDaniel7/PizzariaSoap.git
```

### Configurar o Banco de Dados

Crie um banco de dados chamado pizzaria em seu PostgreSQL

```bash
CREATE DATABASE pizzaria;
```

A configuração do banco de dados está no arquivo src/main/resources/application.properties, onde você pode ajustar o usuário, senha e URL do banco de dados, se necessário.

### Instalar Dependências

Se você não tiver o Maven instalado, faça isso agora. Caso contrário, use o Maven para baixar as dependências necessárias.

Execute o seguinte comando na raiz do projeto para instalar as dependências:

```bash
mvn clean install
```

### Executar o Projeto

Após instalar as dependências, você pode rodar o projeto com o seguinte comando:

```bash
mvn spring-boot:run
```
