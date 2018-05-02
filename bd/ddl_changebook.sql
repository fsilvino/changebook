CREATE TABLE USUARIO (
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    NOME VARCHAR(200) NOT NULL,
    EMAIL VARCHAR(200) NOT NULL,
    SENHA VARCHAR(50) NOT NULL,
    TELEFONE VARCHAR(20) NOT NULL,
    CONSTRAINT PK_USUARIO PRIMARY KEY (ID)
);

CREATE TABLE LIVRO (
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    IDUSUARIO INTEGER NOT NULL,
    TITULO VARCHAR(255) NOT NULL,
    SINOPSE VARCHAR(3000) NOT NULL,
    AUTOR VARCHAR(255) NOT NULL,
    CONSTRAINT PK_IDLIVRO PRIMARY KEY (ID),
    CONSTRAINT FK_LIVRO_IDUSUARIO FOREIGN KEY (IDUSUARIO) REFERENCES USUARIO
);

CREATE TABLE MENSAGEM (
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    IDLIVRO INTEGER NOT NULL,
    IDUSUARIO_REMETENTE INTEGER NOT NULL,
    IDUSUARIO_DESTINATARIO INTEGER NOT NULL,
    TEXTO VARCHAR(3000) NOT NULL,
    CONSTRAINT PK_MENSAGEM PRIMARY KEY (ID),
    CONSTRAINT FK_MENSAGEM_IDLIVRO FOREIGN KEY (IDLIVRO) REFERENCES LIVRO,
    CONSTRAINT FK_MENSAGEM_IDUSUARIO_REMETENTE FOREIGN KEY (IDUSUARIO_REMETENTE) REFERENCES USUARIO,
    CONSTRAINT FK_MENSAGEM_IDUSUARIO_DESTINATARIO FOREIGN KEY (IDUSUARIO_DESTINATARIO) REFERENCES USUARIO
);