create table if not exists  UTILISATEUR
(
    LOGIN    CHAR(15) not null,
    PASSWORD char not null,
    NOM      CHAR(30),
    PRENOM   CHAR(30),
    EMAIL    CHAR(50),
    constraint UTILISATEUR_PK
        primary key (LOGIN)
);

create table if not exists RESPONSABLE
(
    LOGIN CHAR(15) not null,
    constraint RESPONSABLE_PK
        primary key (LOGIN),
    constraint RESPONSABLE_UTILISATEUR_LOGIN_FK
        foreign key (LOGIN) references UTILISATEUR (LOGIN)
            on update cascade on delete cascade
);

create table if not exists ENSEIGNANT
(
    LOGIN CHAR(15) not null,
    constraint ENSEIGNANT_PK
        primary key (LOGIN),
    constraint ENSEIGNANT_UTILISATEUR_LOGIN_FK
        foreign key (LOGIN) references UTILISATEUR (LOGIN)
            on update cascade on delete cascade
);


CREATE TABLE if not exists FILIERE
(
    ID_F INT auto_increment,
    NOM CHAR(30),
    constraint FILIERE_PK
        primary key (ID_F)
);

create table ETUDIANT
(
    LOGIN     CHAR(15) not null unique ,
    NVX_ETUDE CHAR(15),
    PROMO     CHAR(15),
    ID_F   int,
    constraint ETUDIANT_PK
        primary key (LOGIN),
    constraint ETUDIANT_FILIERE_FK
        foreign key (ID_F) references FILIERE(ID_F),
    constraint ETUDIANT_UTILISATEUR_FK
        foreign key (LOGIN) references UTILISATEUR
            on update cascade on delete cascade
);





CREATE TABLE if not exists SALLE(
                                    ID_S int not null auto_increment,
                                    NUM CHAR(15) not null ,
                                    BATIMENT CHAR(15),
                                    VIDEO_P BOOLEAN,
                                    constraint SALLE_PK
                                        primary key (NUM),
                                    constraint salle_u unique(BATIMENT,NUM)
);

CREATE TABLE if not exists COURS (
                                     ID_C INT NOT NULL AUTO_INCREMENT,
                                     NATURE CHAR(15),
                                     NOM CHAR(15),
                                     LOGIN CHAR(15) not null,
                                     constraint COURS_PK
                                         primary key (NOM,LOGIN,NATURE),
                                     constraint COURS_ENSEIGNANT
                                         foreign key (LOGIN) references ENSEIGNANT(LOGIN),
                                     CONSTRAINT COURS_U unique (ID_C)
);

CREATE TABLE if not exists COURS_FILIERE(
                                            ID_C CHAR(15) not null ,
                                            ID_F INT not null ,
                                            TAUX_H NUMERIC(6,2),
                                            constraint COURS_FILIERE_PK
                                                primary key (ID_C,ID_F),
                                            constraint COURS_FILIERE_FK1
                                                foreign key (ID_C) references COURS(ID_C),
                                            constraint COURS_FILIERE_FK2
                                                foreign key (ID_F) references FILIERE(ID_F)
);

CREATE TABLE if not exists GROUPE (
                                      ID_G INT not null ,
                                      LOGIN char(15) not null ,
                                      constraint GROUP_PK
                                          primary key  (ID_G,LOGIN),
                                      constraint GROUP_FK
                                          foreign key (LOGIN) references ETUDIANT(LOGIN)
);

CREATE TABLE if not exists GROUP_COURS (
                                           ID_G INT not null,
                                           ID_C INT,
                                           constraint GROUP_COURS_PK
                                               primary key (ID_G),
                                           constraint GROUP_COURS_FK1
                                               foreign key  (ID_G) references GROUPE(ID_G),
                                           constraint GROUP_COURS_FK2
                                               foreign key  (ID_C) references COURS(ID_C)
);

CREATE TABLE if not exists GROUP_FILIERE(
                                            ID_G INT,
                                            ID_F INT,
                                            constraint GROUP_FILIERE_PK
                                                primary key (ID_G),
                                            constraint GROUP_FILIERE_FK1
                                                foreign key  (ID_G) references GROUPE(ID_G),
                                            constraint GROUP_FILIERE_FK2
                                                foreign key  (ID_F) references FILIERE(ID_F)
);

create table if not exists CRENEAUX
(
    DATE_D DATETIME not null,
    DATE_F DATETIME,
    ID_S   int not null,
    ID_G INT      not null,
    constraint CRENAUX_PK
        primary key (DATE_D, ID_S, ID_G),
    constraint CRENAUX_GROUPE_ID_G_FK
        foreign key (ID_G) references GROUPE (ID_G),
    constraint CRENAUX_SALLE_ID_FK
        foreign key (ID_S) references SALLE(ID_S)
);


insert INTO UTILISATEUR (NOM, PRENOM, LOGIN, PASSWORD) VALUES ( 'NEO' ,'ANDERSON','MATRIX','ABCD');
insert INTO UTILISATEUR (NOM, PRENOM, LOGIN, PASSWORD) VALUES ( 'UHG' ,'DDD','FFF','VDS' );


insert INTO UTILISATEUR (NOM, PRENOM, LOGIN, PASSWORD) VALUES ( 'abc' ,'bbbb','cccc','dddd' );
insert into RESPONSABLE (login)values ( 'cccc' );
insert into ENSEIGNANT(login)values('FFF');
insert into FILIERE(NOM) values ( 'DID' );
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1','2021/2022','MATRIX',1 );
insert into salle (NUM,BATIMENT, video_p) values ( '001','U',true );
insert into salle (NUM,BATIMENT, video_p) values ( '003','F',true );
insert into salle (NUM,BATIMENT, video_p) values ( '002','W',true );
insert into COURS(LOGIN, nature, nom) values ( 'FFF','CM','java' );
insert into COURS(LOGIN, nature, nom) values ( 'FFF','TP','MATH' );
insert into COURS(LOGIN, nature, nom) values ( 'FFF','TD','LINUX' );
insert into COURS_FILIERE (id_c, id_f, taux_h) values ( 1,1,22.5 );
insert into GROUPE(ID_G,LOGIN) values (1,'MATRIX');
insert into GROUP_COURS(ID_G, ID_C) values ( 1,1 ) ;
INSERT INTO CRENEAUX (DATE_D, DATE_F, ID_S, ID_G) VALUES ( '2021-12-15 09:45:23.000000
','2021-12-15 12:30:00.000000',1,1);
INSERT INTO CRENEAUX (DATE_D, DATE_F, ID_S, ID_G) VALUES ( '2021-12-16 09:00:00.000000
','2021-12-16 10:15:00.000000',1,1);
INSERT INTO CRENEAUX (DATE_D, DATE_F, ID_S, ID_G) VALUES ( '2021-12-15 08:45:23.000000
','2021-12-15 11:30:00.000000',2,1);
INSERT INTO CRENEAUX (DATE_D, DATE_F, ID_S, ID_G) VALUES ( '2021-12-16 12:00:00.000000
','2021-12-16 16:15:00.000000',3,1);


UPDATE UTILISATEUR SET PASSWORD = HASH('SHA256', PASSWORD, 1000);


select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G) join COURS ON (GROUP_COURS.ID_C = COURS.ID_C)
where LOGIN ='FFF' AND DATE_D>='2021-12-15 09:45:23.000000' AND DATE_F <='2021-12-15 17:30:00.000000';

select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G)join COURS ON (GROUP_COURS.ID_C = COURS.ID_C) join GROUPE on CRENEAUX.ID_G = GROUPE.ID_G
where GROUPE.LOGIN ='cccc' AND DATE_D>='2021-12-15 09:45:23.000000' AND DATE_F <='2021-12-15 09:45:23.000000';/*GET SYSTEM DATE calender get monday =<0*/

//ET SYSTEM DATE calender get monday =<0
SELECT  count(1) from UTILISATEUR join RESPONSABLE on (UTILISATEUR.LOGIN = RESPONSABLE.LOGIN) where LOGIN= '"MATRIX"' AND PASSWORD = HASH('SHA256','"ABCD"',1000)