CREATE TABLE tb_perfil(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(255) NOT NULL);

CREATE TABLE tb_usuario(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
senha VARCHAR(255) NOT NULL,
saldo DECIMAL(19,2) NOT NULL,
quantidade_amigos INT NOT NULL,
perfil_id BIGINT NOT NULL,
FOREIGN KEY (perfil_id) REFERENCES tb_perfil(id)
);

CREATE TABLE tb_carteira(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
codigo VARCHAR(255) NOT NULL,
orcamento DECIMAL(19,2) NOT NULL,
bandeira VARCHAR(255) NOT NULL,
moeda VARCHAR(255) NOT NULL,
usuario_id BIGINT,
FOREIGN KEY (usuario_id) REFERENCES tb_usuario(id)
);

CREATE TABLE tb_produto(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(255) NOT NULL,
valor DECIMAL(19,2) NOT NULL,
usuario_id BIGINT,
FOREIGN KEY (usuario_id) REFERENCES tb_usuario(id)
);


CREATE TABLE tb_transacao(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
token VARCHAR(255) NOT NULL,
data VARCHAR(255) NOT NULL,
valor DECIMAL(19,2) NOT NULL,
origem_id BIGINT NOT NULL,
destino_id BIGINT NOT NULL,
produto_id BIGINT,
FOREIGN KEY (origem_id) REFERENCES tb_usuario(id),
FOREIGN KEY (destino_id) REFERENCES tb_usuario(id),
FOREIGN KEY (produto_id) REFERENCES tb_produto(id)
);


INSERT INTO `tb_perfil` (`id`, `nome`) VALUES (1, 'USUARIO');
INSERT INTO `tb_perfil` (`id`, `nome`) VALUES (2, 'ADMIN');

--INSERT INTO `tb_usuario` (`id`, `email`, `senha`, `perfil_id`) VALUES (1, 'admin@gft.com', '$2a$10$UYsFPyLCVXzxPJLFKVMV0uMNCszuGWYeXDRhyaOc1xxVMQiHhETzi', 2);
--INSERT INTO `tb_usuario` (`id`, `email`, `senha`, `perfil_id`) VALUES (2, 'usu@gft.com', '$2a$10$UYsFPyLCVXzxPJLFKVMV0uMNCszuGWYeXDRhyaOc1xxVMQiHhETzi', 1);
--

