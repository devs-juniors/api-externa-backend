# FEF Invest

Sistema de gestão de corretoras, ações financeiras e carteiras de investimento desenvolvido em Java com Spring Boot, com integração a APIs públicas externas para validação e enriquecimento de dados.

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
  ├── domains/          → entidades, DTOs e enums
  ├── infra/
  │   ├── adapter/      → Adapters de cotação (Strategy + Adapter)
  │   ├── client/       → interfaces OpenFeign das APIs externas
  │   ├── converters/   → conversores JPA de enum para banco
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
| DELETE | `/corretoras/{id}` | Exclui corretora |

### Ações

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/acoes` | Cadastra ação pelo ticker e mercado |
| GET | `/acoes` | Lista todas as ações |
| GET | `/acoes/{id}` | Busca ação por ID |
| GET | `/acoes/ticker/{ticker}` | Busca ação por ticker |
| PUT | `/acoes/{id}/atualizar-cotacao` | Atualiza cotação da ação |
| DELETE | `/acoes/{id}` | Exclui ação |

### Carteiras

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/carteiras` | Cria uma nova carteira |
| GET | `/carteiras` | Lista todas as carteiras |
| GET | `/carteiras/{id}` | Busca carteira com posições e lucro/prejuízo |
| POST | `/carteiras/{id}/comprar` | Registra compra de ação |
| POST | `/carteiras/{id}/vender` | Registra venda de ação |
| GET | `/carteiras/{id}/operacoes` | Lista histórico completo da carteira |
| DELETE | `/carteiras/{id}` | Exclui carteira |

### Operações

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/operacoes/carteira-acao/{id}` | Histórico de uma posição específica |
| GET | `/operacoes/carteira-acao/{id}/compras` | Filtra só as compras |
| GET | `/operacoes/carteira-acao/{id}/vendas` | Filtra só as vendas |

### Exclusão de recursos

| Método | Endpoint | Descrição | Validação |
|---|---|---|---|
| DELETE | `/corretoras/{id}` | Exclui corretora | Não pode ter carteiras vinculadas |
| DELETE | `/carteiras/{id}` | Exclui carteira | Não pode ter posições ativas |
| DELETE | `/acoes/{id}` | Exclui ação | Não pode estar em nenhuma carteira |

---

## 🧪 Exemplos de uso

### 1. Cadastrar corretora

```json
POST /corretoras
{
    "cnpj": "02332886000104"
}
```

### 2. Cadastrar ações

```json
POST /acoes
{
    "ticker": "PETR4",
    "mercado": "BR"
}
```

```json
POST /acoes
{
    "ticker": "VALE3",
    "mercado": "BR"
}
```

```json
POST /acoes
{
    "ticker": "AAPL",
    "mercado": "EUA"
}
```

### 3. Criar carteira

```json
POST /carteiras
{
    "nome": "Carteira Principal",
    "corretoraId": 1
}
```

### 4. Registrar compras

```json
POST /carteiras/1/comprar
{
    "ticker": "PETR4",
    "quantidade": 10,
    "precoUnitario": 49.08
}
```

```json
POST /carteiras/1/comprar
{
    "ticker": "PETR4",
    "quantidade": 5,
    "precoUnitario": 52.00
}
```

```json
POST /carteiras/1/comprar
{
    "ticker": "VALE3",
    "quantidade": 8,
    "precoUnitario": 68.50
}
```

```json
POST /carteiras/1/comprar
{
    "ticker": "AAPL",
    "quantidade": 3,
    "precoUnitario": 276.83
}
```

### 5. Registrar venda

```json
POST /carteiras/1/vender
{
    "ticker": "PETR4",
    "quantidade": 5,
    "precoUnitario": 55.00
}
```

### 6. Atualizar cotação

```
PUT /acoes/1/atualizar-cotacao
```

### 7. Ver carteira com lucro/prejuízo

```
GET /carteiras/1
```

### 8. Ver histórico de operações

```
GET /carteiras/1/operacoes
GET /operacoes/carteira-acao/1/compras
GET /operacoes/carteira-acao/1/vendas
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

Carteira
├── id (Long)
├── nome (String)
├── corretora (Corretora)
├── posicoes (List<CarteiraAcao>)
└── dataCriacao (LocalDateTime)

CarteiraAcao
├── id (Long)
├── carteira (Carteira)
├── acao (Acao)
├── quantidadeAtual (Integer)
├── precoMedioCompra (BigDecimal)
└── valorTotalInvestido (BigDecimal)

Operacao
├── id (Long)
├── carteiraAcao (CarteiraAcao)
├── tipo (TipoOperacao: COMPRA | VENDA)
├── quantidade (Integer)
├── precoUnitario (BigDecimal)
├── valorTotal (BigDecimal)
└── dataOperacao (LocalDateTime)
```

---

## 💰 Cálculo de Lucro/Prejuízo

### Preço Médio de Compra

O sistema calcula automaticamente o preço médio de compra a cada nova compra:

```
Exemplo:
  Compra 10 ações a R$ 49,08 → total investido: R$ 490,80
  Compra 5 ações a R$ 52,00  → total investido: R$ 260,00

  Total de ações: 15
  Total investido: R$ 750,80
  Preço médio: 750,80 ÷ 15 = R$ 50,05
```

### Lucro Realizado vs. Não-Realizado

O campo `lucroOuPrejuizo` retornado pela API combina dois componentes:

```
lucroOuPrejuizo = lucroRealizado + lucroNaoRealizado

lucroRealizado    = Σ (precoVenda - precoMedioCompra) × qtdVendida  (acumulado a cada venda)
lucroNaoRealizado = (cotacaoAtual - precoMedioCompra) × quantidadeAtual
```

**Exemplo:**
```
Compra 10 ações a R$ 20 → total investido: R$ 200
Vende  10 ações a R$ 30 → lucroRealizado = (30-20) × 10 = R$ 100
Compra  5 ações a R$ 25 → novo preço médio = R$ 25
Cotação atual = R$ 30   → lucroNaoRealizado = (30-25) × 5 = R$ 25

lucroOuPrejuizo = 100 + 25 = R$ 125
```

Ao zerar uma posição (`quantidadeAtual = 0`), o `lucroRealizado` é preservado no registro. Uma nova compra reutiliza o registro existente, resetando apenas os dados de compra (preço médio, valor investido) sem apagar o histórico de vendas.

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
- Carteira duplicada com mesmo nome na mesma corretora
- Venda com quantidade maior do que a disponível na carteira
- Ação não cadastrada no sistema antes de comprar
- API externa fora do ar
- Limite de requisições excedido
