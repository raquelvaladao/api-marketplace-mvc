CREATE TABLE tb_amizade(
id BIGINT AUTO_INCREMENT PRIMARY KEY,
status VARCHAR(255) NOT NULL,
data_envio VARCHAR(255) NOT NULL,
remetente_id BIGINT,
destinatario_id BIGINT,
FOREIGN KEY (remetente_id) REFERENCES tb_usuario(id) ON DELETE CASCADE,
FOREIGN KEY (destinatario_id) REFERENCES tb_usuario(id) ON DELETE CASCADE
);
