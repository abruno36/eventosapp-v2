insert into usuario(login, nome_completo, senha) values('bruno','Antonio Bruno','$2a$10$7ZwPIHYNiAgOWWh1fTHnmu9avEWOyo/XcaSVQM8hgXZUhE4qW1qEe');
insert into usuario(login, nome_completo, senha) values('marcio','Marcio Bento Silva','$2a$10$HTvYqjqUqgHFirMfi9Akp..W/r5RaNLSIrtGq4Gfzcuy3U/SRWEBi');
insert into role(nome_role) values('ROLE_ADMIN');
insert into role(nome_role) values('ROLE_USER');
insert into usuarios_roles(usuario_id, role_id) values('bruno','ROLE_ADMIN');
insert into usuarios_roles(usuario_id, role_id) values('marcio','ROLE_USER');