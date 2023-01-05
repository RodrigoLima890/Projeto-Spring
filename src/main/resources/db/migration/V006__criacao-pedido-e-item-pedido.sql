create table pedido(
id bigint not null auto_increment,

sub_total decimal(10,2) not null,
taxa_frete decimal(10,2) not null,
valor_total decimal(10,2) not null,

status_pedido varchar(10) not null,
data_criacao dateTime not null,
data_confirmacao dateTime null,
data_cancelamento dateTime null,
data_entrega dateTime null,

endereco_cidade_id bigint(20) not null,
cep varchar(10) not null,
logradouro varchar(100) not null,
numero varchar(10) not null,
complemento varchar(60) null,
bairro varchar(25) not null,

cliente_id bigint not null,
restaurante_id bigInt not null,
forma_pagamento_id bigint not null,

primary key (id),

constraint fk_pedido1 foreign key (cliente_id) references usuario (id),
constraint fk_pedido2 foreign key (restaurante_id) references restaurante (id),
constraint fk_pedido3 foreign key (forma_pagamento_id) references forma_pagamento (id)

) engine=InnoDB default charset=utf8;

create table item_pedido(
id bigint not null auto_increment,

quantidate smallint(6) not null,
preco_unitario decimal(10,2) not null,
preco_total decimal(10,2) not null,
descricao varchar(255),

produto_id bigint not null,
pedido_id bigint not null,

primary key(id),
unique key uk_item_pedido_produto (pedido_id, produto_id),

constraint fk_item_pedido1 foreign key (produto_id) references produto (id),
constraint fk_item_pedido2 foreign key (pedido_id) references pedido (id)

)engine=InnoDB default charset=utf8;
