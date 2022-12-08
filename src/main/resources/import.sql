insert into cozinha (id, nome) values (1,'Tailandesa');
insert into cozinha (id, nome) values (2,'Indiana');
insert into cozinha (id, nome) values (3,'Japonesa');

insert into estado(id,nome) values(1, 'Ceará');
insert into estado(id,nome) values(2, 'São Paulo');
insert into estado(id,nome) values(3, 'Minas Gerais');

insert into cidade(id,nome,estado_id) values(1,'São José dos campos',2);
insert into cidade(id,nome,estado_id) values(2,'Fortaleza',1);
insert into cidade (id, nome, estado_id) values (3, 'Uberlândia', 3);
insert into cidade (id, nome, estado_id) values (4, 'Belo Horizonte', 3);
insert into cidade (id, nome, estado_id) values (5, 'São Paulo', 2);
insert into cidade (id, nome, estado_id) values (6, 'Campinas', 2);
insert into cidade (id, nome, estado_id) values (7, 'Crato', 1);

insert into restaurante (id, nome, taxa_frete, cozinha_id,endereco_cidade_id,endereco_cep,endereco_logradouro,endereco_numero,endereco_bairro,data_cadastro, data_atualizacao) 
values (1, 'Tay_Cearense', 10, 1,2,'60212-321','21','1002','Mesejana', utc_timestamp, utc_timestamp);
insert into restaurante (id,nome, taxa_frete, cozinha_id,data_cadastro, data_atualizacao) values (2,'Thay_Gourmet', 10.00, 1, utc_timestamp, utc_timestamp);
insert into restaurante (id,nome, taxa_frete, cozinha_id,data_cadastro, data_atualizacao) values (3,'Indie_Restaurante', 12.50, 2, utc_timestamp, utc_timestamp);

insert into forma_de_pagamento(id, descricao) values(1, "Cartão de credito");
insert into forma_de_pagamento(id, descricao) values(2, "Cartão de debito");
insert into forma_de_pagamento(id, descricao) values(3, "Dinheiro");
insert into forma_de_pagamento(id, descricao) values(4, "Pix");

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_forma_pagamento(restaurante_id, forma_pagamento_id) values(1,1),(1,2),(1,3),(2,1),(2,2),(2,3),(2,4),(3,1),(3,2),(3,3);
