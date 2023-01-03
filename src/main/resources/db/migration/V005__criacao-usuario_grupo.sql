create table usuario_grupo(
usuario_id bigint not null,
grupo_id bigint not null
)engine=innoDB;

alter table usuario_grupo add constraint fk_usuario_grupo1 foreign key (grupo_id) references grupo (id);

alter table usuario_grupo add constraint fk_usuario_grupo2 foreign key (usuario_id) references usuario (id);
