create table forma_pagamento (id bigint not null auto_increment, descricao varchar(255), primary key (id)) engine=InnoDB;

create table grupo (id bigint not null auto_increment, nome varchar(80) not null, primary key (id)) engine=InnoDB;

create table grupo_permissao (grupo_id bigint not null, permissao_id bigint not null) engine=InnoDB;

create table hibernate_sequence (next_val bigint) engine=InnoDB;
insert into hibernate_sequence values ( 1 );

create table permissao (id bigint not null auto_increment, descricao varchar(255), nome varchar(80), primary key (id)) engine=InnoDB;

create table produto (id bigint not null auto_increment, ativo bit not null, descricao varchar(255) not null, nome varchar(80) not null,
 preco decimal(9,2) not null, restaurante_id bigint not null, primary key (id)) engine=InnoDB;

create table restaurante (id bigint not null auto_increment, data_atualizacao DateTime not null, 
data_cadastro DateTime not null, endereco_bairro varchar(80), endereco_cep varchar(11), 
endereco_complemento varchar(80), endereco_logradouro varchar(80), endereco_numero varchar(11),
 nome varchar(80) not null, taxa_frete decimal(9,2) not null, cozinha_id bigint, endereco_cidade_id bigint, primary key (id)) engine=InnoDB;

create table restaurante_forma_pagamento (restaurante_id bigint not null, forma_pagamento_id bigint not null) engine=InnoDB;

create table usuario (id bigint not null auto_increment, data_cadastro DateTime not null, email varchar(80) not null, 
nome varchar(80) not null, senha varchar(80) not null, primary key (id)) engine=InnoDB;

alter table grupo_permissao add constraint fk_grupo_permissao1 foreign key (permissao_id) references permissao (id);

alter table grupo_permissao add constraint fk_grupo_permissao2 foreign key (grupo_id) references grupo (id);

alter table produto add constraint fk_produto1 foreign key (restaurante_id) references restaurante (id);

alter table restaurante add constraint fk_restaurante1 foreign key (cozinha_id) references cozinha (id);

alter table restaurante add constraint fk_restaurante2 foreign key (endereco_cidade_id) references cidade (id);

alter table restaurante_forma_pagamento add constraint fk_restaurante_forma_pagamento1 foreign key (forma_pagamento_id) 
references forma_pagamento (id);

alter table restaurante_forma_pagamento add constraint fk_restaurante_forma_pagamento2 foreign key 
(restaurante_id) references restaurante (id);


