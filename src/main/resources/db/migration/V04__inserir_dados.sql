INSERT INTO `tb_carteira` (`id`, `codigo`, `orcamento`, `bandeira`, `moeda`, `usuario_id`) VALUES (1, '423423-234234-23423-23423', 9000.00, 'TRUST', 'BITCOIN', 1);
INSERT INTO `tb_carteira` (`id`, `codigo`, `orcamento`, `bandeira`, `moeda`, `usuario_id`) VALUES (2, '423423-234234-23423-23423', 15000.00, 'TRUST', 'ETHEREUM', 2);

INSERT INTO `tb_usuario` (`id`, `nome`, `email`, `senha`, `saldo`, `quantidade_amigos`, `perfil_id`) VALUES (3, 'Ab guy', 'ab@gft.com', '$2a$10$yJuXeiSZ2hslHOaCA6sB0e7jWU.Kj7uUbqVGAhsHb021VC2NeKwoy', 0.00, 0, 1);

INSERT INTO `tb_carteira` (`id`, `codigo`, `orcamento`, `bandeira`, `moeda`, `usuario_id`) VALUES (3, '4234-234234-23423-234', 14300.00, 'COINBASE', 'BITCOIN', 3);
INSERT INTO `tb_carteira` (`id`, `codigo`, `orcamento`, `bandeira`, `moeda`, `usuario_id`) VALUES (4, '12341-234234-2342-3432', 89300.00, 'TRUST', 'ETHEREUM', 3);

INSERT INTO `tb_produto` (`id`, `nome`, `valor`, `usuario_id`) VALUES (1, 'Produto do Admin', 9000.99, 1);
INSERT INTO `tb_produto` (`id`, `nome`, `valor`, `usuario_id`) VALUES (2, 'Carta do Admin', 9000.99, 1);
INSERT INTO `tb_produto` (`id`, `nome`, `valor`, `usuario_id`) VALUES (3, 'Carta do Usuario', 3899.99, 2);

