-- ============================================================================
-- Pito de Pango - schema unico do banco (coffeeshop_db)
-- ============================================================================
-- Este script eh a fonte da verdade da estrutura do banco. Ele reflete
-- exatamente as colunas que o codigo Java (desktop e web) ja espera nas
-- consultas SQL. Rode este arquivo uma vez em um banco novo:
--
--   mysql -u root -p < schema.sql
--
-- ou, dentro do MySQL Workbench, abra o arquivo e execute (Ctrl+Shift+Enter).
-- ============================================================================

CREATE DATABASE IF NOT EXISTS coffeeshop_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE coffeeshop_db;

-- ----------------------------------------------------------------------------
-- usuario: login e perfil de acesso do painel administrativo
-- (perfil espelha o enum Usuario.Perfil: GERENTE, FINANCEIRO, ATENDENTE)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS usuario (
    nome   VARCHAR(100) NOT NULL,
    senha  VARCHAR(255) NOT NULL, -- guarda o HASH da senha (ex: BCrypt), nunca texto puro
    perfil ENUM('GERENTE', 'FINANCEIRO', 'ATENDENTE') NOT NULL,
    PRIMARY KEY (nome)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- fornecedor
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS fornecedor (
    nome      VARCHAR(150) NOT NULL,
    cnpj      VARCHAR(20)  NOT NULL,
    telefone  VARCHAR(20),
    email     VARCHAR(150),
    PRIMARY KEY (cnpj)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- cliente
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS cliente (
    id_cliente INT AUTO_INCREMENT,
    nome       VARCHAR(150) NOT NULL,
    cpf        VARCHAR(14)  NOT NULL,
    telefone   VARCHAR(20),
    email      VARCHAR(150),
    endereco   VARCHAR(255),
    PRIMARY KEY (id_cliente),
    UNIQUE KEY uk_cliente_cpf (cpf)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- produto
-- Observacao: "fornecedor" aqui guarda o NOME do fornecedor em texto livre
-- (nao eh FK para fornecedor.cnpj). Isso ja existe assim no codigo atual dos
-- dois projetos; fica registrado como melhoria futura (Fase 1) transformar
-- em uma FK de verdade.
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS produto (
    codigo        INT NOT NULL,
    nome          VARCHAR(150) NOT NULL,
    fornecedor    VARCHAR(150),
    quantidade    INT NOT NULL DEFAULT 0,
    valor         DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    data_cadastro VARCHAR(20),
    descricao     TEXT,
    categoria     VARCHAR(100),
    ativo         BOOLEAN NOT NULL DEFAULT TRUE, -- usado pelo model novo do emporio; repository ainda precisa ler/gravar essa coluna (Fase 2)
    PRIMARY KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- pedido
-- 
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS pedido (
    numero_pedido  INT AUTO_INCREMENT,
    id_cliente     INT NULL,
    data_pedido    VARCHAR(20),
    total          DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    PRIMARY KEY (numero_pedido),
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (id_cliente)
        REFERENCES cliente (id_cliente)
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- pedido_item
-- 
-- ----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS pedido_item (
    id_item        INT AUTO_INCREMENT,
    numero_pedido  INT NOT NULL,
    produto        VARCHAR(150) NOT NULL,
    valor_unitario DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    quantidade     INT NOT NULL DEFAULT 1,
    subtotal       DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    PRIMARY KEY (id_item),
    CONSTRAINT fk_item_pedido FOREIGN KEY (numero_pedido)
        REFERENCES pedido (numero_pedido)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
-- ----------------------------------------------------------------------------
-- caixa
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS caixa (
    id_caixa      INT AUTO_INCREMENT,
    data          VARCHAR(20),
    operador      VARCHAR(150),
    saldo_inicial DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    saldo_atual   DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    descricao     TEXT,
    PRIMARY KEY (id_caixa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
