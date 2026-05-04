# API Externa Backend — Gestão de Ações

Sistema de gestão de corretoras e ações financeiras desenvolvido em Java com Spring Boot, com integração a APIs públicas externas para validação e enriquecimento de dados.

## 👥 Integrantes

- Lucas Mendes
- Rhiady Vieira
- Jhonatan Zago
- João Augusto

---

## 🛠️ Tecnologias

- Java 17
- Spring Boot 3.4.5
- Spring Cloud OpenFeign 4.2.1
- Spring Data JPA
- H2 Database (ambiente de teste)
- PostgreSQL (produção)
- Lombok
- Maven

---

## 📐 Arquitetura

O projeto segue arquitetura em camadas com os seguintes pacotes:

```
com/
  ├── config/           → configurações globais (FeignConfig)
  ├── domains/          → entidades e DTOs
  ├── infra/
  │   ├── adapter/      → Adapters de cotação (Strategy + Adapter)
  │   ├── client/       → interfaces OpenFeign das APIs externas
  │   └── facade/       → Facades de isolamento das APIs
  ├── mappers/          → conversão Entity ↔ DTO
  ├── repositories/     → interfaces JPA
  ├── resources/        → controllers e exceptions
  └── services/         → regras de negócio
```

### Padrões de Projeto utilizados

| Padrão | Onde | Por quê |
|---|---|---|
| **Facade** | `CepFacade`, `CnpjFacade` | Isola as APIs externas do Service |
| **Strategy** | `CotacaoAdapter` | Escolhe qual API de cotação usar sem if/else |
| **Adapter** | `BrapiAdapter`, `AlphaVantageAdapter` | Traduz resposta de cada API pro formato interno |

---

## 🌐 APIs Externas Utilizadas

### BrasilAPI — CNPJ
- **URL:** https://brasilapi.com.br/api
- **Endpoint:** `GET /cnpj/v1/{cnpj}`
- **Uso:** Busca dados cadastrais da corretora na Receita Federal
- **Autenticação:** Não requer token
- **Limitação:** Sem limite documentado

### ViaCep — CEP
- **URL:** https://viacep.com.br
- **Endpoint:** `GET /ws/{cep}/json/`
- **Uso:** Busca endereço completo a partir do CEP
- **Autenticação:** Não requer token
- **Limitação:** Sem limite documentado

### Brapi — Ações Brasileiras
- **URL:** https://brapi.dev
- **Endpoint:** `GET /api/quote/{ticker}?token={token}`
- **Uso:** Busca cotação de ações da bolsa brasileira (B3)
- **Autenticação:** Requer token gratuito
- **Limitação:** Plano gratuito com limite de requisições
- **Cadastro:** https://brapi.dev

### AlphaVantage — Ações Americanas
- **URL:** https://www.alphavantage.co
- **Endpoint:** `GET /query?function=GLOBAL_QUOTE&symbol={ticker}&apikey={key}`
- **Uso:** Busca cotação de ações da bolsa americana (NYSE/NASDAQ)
- **Autenticação:** Requer chave de API gratuita
- **Limitação:** 25 requisições por dia no plano gratuito
- **Cadastro:** https://www.alphavantage.co/support/#api-key

---

## ⚙️ Como configurar e rodar

### Pré-requisitos
- Java 17
- Maven 3.8+
- IntelliJ IDEA ou VSCode

### 1. Clone o repositório

```bash
git clone https://github.com/devs-juniors/api-externa-backend.git
cd api-externa-backend
```

### 2. Configure o application.properties

Abra o arquivo `src/main/resources/application.properties` e preencha os tokens:

```properties
# URLs das APIs
api.cep.url=https://viacep.com.br
api.cnpj.url=https://brasilapi.com.br/api
api.brapi.url=https://brapi.dev
api.alphavantage.url=https://www.alphavantage.co

# Tokens — cada integrante deve gerar o próprio
api.brapi.token=SEU_TOKEN_BRAPI
api.alphavantage.key=SUA_CHAVE_ALPHAVANTAGE

# Banco H2 (ambiente de teste)
spring.datasource.url=jdbc:h2:mem:cursodb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

### 3. Rode o projeto

```bash
mvn spring-boot:run
```

O projeto sobe em `http://localhost:8080`

---

## 📋 Endpoints

### Corretoras

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/corretoras` | Cadastra corretora pelo CNPJ |
| GET | `/corretoras` | Lista todas as corretoras |
| GET | `/corretoras/{id}` | Busca corretora por ID |
| GET | `/corretoras/cnpj/{cnpj}` | Busca corretora por CNPJ |
| GET | `/corretoras/cep/{cep}` | Consulta endereço pelo CEP |

### Ações

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/acoes` | Cadastra ação pelo ticker e mercado |
| GET | `/acoes` | Lista todas as ações |
| GET | `/acoes/{id}` | Busca ação por ID |
| GET | `/acoes/ticker/{ticker}` | Busca ação por ticker |
| PUT | `/acoes/{id}/atualizar-cotacao` | Atualiza cotação da ação |

---

## 🧪 Exemplos de uso

### Cadastrar corretora

```json
POST /corretoras
{
    "cnpj": "11.222.333/0001-81"
}
```

### Cadastrar ação brasileira

```json
POST /acoes
{
    "ticker": "PETR4",
    "mercado": "BR"
}
```

### Cadastrar ação americana

```json
POST /acoes
{
    "ticker": "AAPL",
    "mercado": "EUA"
}
```

### Atualizar cotação

```
PUT /acoes/1/atualizar-cotacao
```

---

## 🗄️ Diagrama das Entidades

```
Corretora
├── id (Long)
├── cnpj (String)
├── razaoSocial (String)
├── nomeFantasia (String)
├── email (String)
├── telefone (String)
├── cep (String)
├── logradouro (String)
├── numero (String)
├── complemento (String)
├── bairro (String)
├── cidade (String)
├── uf (String)
├── situacaoCadastral (String)
├── validadaNaCvm (Boolean)
└── dataCadastro (LocalDateTime)

Acao
├── id (Long)
├── ticker (String)
├── nomeEmpresa (String)
├── mercado (String)
├── moeda (String)
├── cotacaoAtual (BigDecimal)
└── dataHoraCotacao (LocalDateTime)
```

---

## 📁 Banco de dados

O projeto usa **H2 em memória** para testes. O console H2 está disponível em:

```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:cursodb
Username: sa
Password: (vazio)
```

---

## ⚠️ Tratamento de erros

O sistema trata os seguintes cenários de falha:

- CNPJ inválido no formato ou inexistente na Receita Federal
- CEP inexistente ou inválido
- Ticker inexistente na API de cotação
- Cadastro duplicado de corretora por CNPJ
- Cadastro duplicado de ação por ticker
- Mercado não suportado
- API externa fora do ar
- Limite de requisições excedido
