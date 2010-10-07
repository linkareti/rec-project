INSERT INTO LOGIN_DOMAIN VALUES (1, 1, 'Localhost Moodle', 'http://localhost/moodle/wspp/service_pp.php');
INSERT INTO LOGIN_DOMAIN VALUES (2, 1, 'IP Moodle', 'http://192.168.1.52/moodle/wspp/service_pp.php');
INSERT INTO LOGIN_DOMAIN VALUES (3, 1, 'Invalid Moodle', 'http://localhost-invalid/moodle/wspp/service_pp.php');
INSERT INTO LOGIN_DOMAIN VALUES (4, 1, 'Internal', null);

INSERT INTO PRINCIPAL (ID, INSTANCE_TYPE, VERSION, NAME) VALUES (1, 'Role', 1, 'ADMIN');
INSERT INTO PRINCIPAL (ID, INSTANCE_TYPE, VERSION, NAME) VALUES (2, 'Role', 1, 'TEACHER');
INSERT INTO PRINCIPAL (ID, INSTANCE_TYPE, VERSION, NAME) VALUES (3, 'Role', 1, 'STUDENT');

INSERT INTO PRINCIPAL (ID, INSTANCE_TYPE, VERSION, USERNAME) VALUES (4, 'User', 1, 'rec.am');
INSERT INTO IDENTIFICATION (ID, INSTANCE_TYPE, VERSION, PASSWORD, KEY_USER) VALUES (1, 'Login', 1, MD5('pass'), 4);

INSERT INTO PRINCIPALS_ASSOCIATION (KEY_PARENT, KEY_CHILD) VALUES (1, 4);

INSERT INTO LABORATORY (ID, VERSION, NAME, DESCRIPTION) VALUES (1, 1, 'LAB_ME', 'Laboratório do Ministério da Educação');
INSERT INTO LABORATORY (ID, VERSION, NAME, DESCRIPTION) VALUES (2, 1, 'LAB_ME_BASIC', 'Laboratório do Ministério da Educação - Básico');
INSERT INTO LABORATORY (ID, VERSION, NAME, DESCRIPTION) VALUES (3, 1, 'LAB_ME_INTERMEDIATE', 'Laboratório do Ministério da Educação - Intermédio');
INSERT INTO LABORATORY (ID, VERSION, NAME, DESCRIPTION) VALUES (4, 1, 'LAB_ME_ADVANCED', 'Laboratório do Ministério da Educação - Avançado');

INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (1, 1, 'ELAB_ALEATORIO_V02', 'Estatística de Dados', 'Name = Aleatorio, version = 1, manufacturer = Pedro, driverVersion = v1', 2, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (2, 1, 'BIOT_SAVART_V1.0', 'Biot-Savart', 'Name = Biot-Savart, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (3, 1, 'CARGAS3D_V1.0', 'Cargas3D', 'Name = Cargas3D, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (4, 1, 'CART_POLE_V1.0', 'Cart Pole', 'Name = Cart Pole, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (5, 1, 'CAVENDISH_G_V1.0', 'Cavendish-G', 'Name = Cavendish-G, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (6, 1, 'COLISAO_V1.0', 'Colisao', 'Name = Colisao, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (7, 1, 'ELAB_CC_STAMP_V02', 'Condensador Cilíndrico', 'Name = Basic STAMP, version = BS2, manufacturer = Parallax, driverVersion = v2', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (8, 1, 'ELAB_CONDCALOR_STAMP_V1.0', 'Condução do calor', 'Name = Basic STAMP, version = BS2, manufacturer = Parallax, driverVersion = v2', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (9, 1, 'DISCOS_INERCIA_V1.0', 'Discos-Inercia', 'Name = Discos-Inercia, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (10, 1, 'FERMI_MAP_V1.0', 'Fermi-Map', 'Name = Fermi-Map, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (11, 1, 'ELAB_G_STAMP_V02', 'g', 'Name = Basic STAMP, version = BS2, manufacturer = Parallax, driverVersion = v2', 2, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (12, 1, 'ELAB_GAMMA_V1.0', 'Gamma', 'Name = Basic STAMP, version = BS2, manufacturer = Parallax, driverVersion = v2', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (13, 1, 'LOOPING_V1.0', 'Looping', 'Name = Looping, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (14, 1, 'MOLAS3_V1.0', 'Molas-3', 'Name = Molas-3, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (15, 1, 'ELAB_METEO_V01', 'Meteorologia', 'Name = Basic STAMP, version = BS2, manufacturer = Parallax, driverVersion = v2', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (16, 1, 'MASSA_MOLA_V1.0', 'Massa-Mola', 'Name = Massa-Mola, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (17, 1, 'EXP_ANGULAR_STAMP_V1.0', 'Momento Inércia', 'Name = Basic STAMP, version = BS2, manufacturer = Parallax, driverVersion = v2', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (18, 1, 'MOVIMENTO_DE_PROJECTEIS_V1.0', 'Movimento de Projecteis', 'Name = Movimento de Projecteis, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (19, 1, 'MOVIMENTO_BROWNIANO_V1.0', 'Movimento Browniano', 'Name = Movimento Browniano, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (20, 1, 'OSCILADOR_V1.0', 'Oscilador', 'Name = Oscilador, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (21, 1, 'PENDULO_DUPLO_MOTORIZADO_V1.0', 'Pendulo-Duplo Motorizado', 'Name = Pendulo-Duplo Motorizado, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (22, 1, 'ELAB_PENDULO_STAMP_V02', 'Pêndulo vertical amortecido', 'Name = Basic STAMP, version = BS2, manufacturer = Parallax, driverVersion = v2', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (23, 1, 'ELAB_PENDULO_GRAV_STAMP_V01', 'Pendulo gravitico amortecido', 'Name = Basic STAMP, version = BS2, manufacturer = Parallax, driverVersion = v2', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (24, 1, 'EXP_PLANCK_STAMP_V1.1', 'Planck', 'Name = Basic STAMP, version = BS2, manufacturer = Parallax, driverVersion = v2', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (25, 1, 'POISSON_V1.0', 'Poisson', 'Name = Poisson, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (26, 1, 'ELAB_POLAROID_V1.3', 'Polaroid', 'Name = Basic STAMP, version = BS2, manufacturer = Parallax, driverVersion = v2', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (27, 1, 'ELAB_PV_STAMP_V02', 'Boyle Mariotte', 'Name = Basic STAMP, version = BS2, manufacturer = Parallax, driverVersion = v2', 2, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (28, 1, 'MECANICA_QUANTICA_V1.0', 'Mecanica Quantica', 'Name = Mecanica Quantica, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (29, 1, 'ELAB_RAD_STAMP_V02', 'Exp de Radioactividade', 'Name = Basic STAMP, version = BS2, manufacturer = Parallax, driverVersion = v2', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (30, 1, 'ELAB_SCUBA_STAMP_V02', 'Scuba', 'Name = Basic STAMP, version = BS2, manufacturer = Parallax, driverVersion = v2', 2, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (31, 1, 'ELAB_STATSOUND_STAMP_V01', 'Ondas estacionarias e velocidade do som', 'Name = Basic STAMP, version = BS2, manufacturer = Parallax, driverVersion = v2', 4, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (32, 1, 'STANDARD_MAP_V1.0', 'Standard-Map', 'Name = Standard-Map, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (33, 1, 'ELAB_TELESCOPIO_V01', 'Telescopio', 'Name = Basic STAMP, version = BS2, manufacturer = Parallax, driverVersion = v2', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (34, 1, 'ELAB_THOMSON_STAMP_V01', 'Thomson', 'Name = Basic STAMP, version = BS2, manufacturer = Parallax, driverVersion = v2', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (35, 1, 'TIRO_V1.0', 'Tiro ao Grave', 'Name = Tiro ao Grave, version = 01, manufacturer = Elab - UTL, driverVersion = v2', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (36, 1, 'ELAB_WEBROBOT_V01', 'Web-Robot', 'Name = none, version = none, manufacturer = none, driverVersion = 1.0', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (37, 1, 'INTERFERENCIA_YOUNG_V1.0', 'Interferencia-Young', 'Name = Interferencia-Young, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 1, 1);
INSERT INTO EXPERIMENT (ID, VERSION, EXTERNAL_ID, NAME, DESCRIPTION, KEY_LABORATORY, IS_ACTIVE) VALUES (38, 1, 'OPTICA', 'Determinação do índice de refracção do pexiglass e verificar a lei de Snell', 'Name = Determinação do índice de refracção do pexiglass e verificar a lei de Snell, version = 01, manufacturer = Elab - UTL, driverVersion = v1', 3, 1);