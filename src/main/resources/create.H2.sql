drop table if exists RESPONSABLE;

drop table if exists GROUP_ETUDIANT;

drop table if exists ABSENCE;

drop table if exists ETUDIANT;

drop table if exists CRENEAUX;

drop table if exists SALLE;

drop table if exists GROUP_COURS;

drop table if exists FILIERE;

drop table if exists COURS;

drop table if exists ENSEIGNANT;

drop table if exists UTILISATEUR;

drop table if exists GROUPS;




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
                                    BATIMENT CHAR(15) not null,
                                    VIDEO_P BOOLEAN,
                                    constraint SALLE_PK
                                        primary key (NUM,BATIMENT)
);

CREATE TABLE if not exists COURS (
                                     ID_C INT NOT NULL AUTO_INCREMENT,
                                     NATURE CHAR(15),
                                     NOM CHAR(50),
                                     LOGIN CHAR(15) not null,
                                     constraint COURS_PK
                                         primary key (NOM,LOGIN,NATURE),
                                     constraint COURS_ENSEIGNANT
                                         foreign key (LOGIN) references ENSEIGNANT(LOGIN),
                                     CONSTRAINT COURS_U unique (ID_C)
);

CREATE TABLE IF NOT EXISTS GROUPS(
    ID_G INT NOT NULL AUTO_INCREMENT,
    NOM CHAR(30) NOT NULL unique
);

CREATE TABLE if not exists GROUP_ETUDIANT (
                                      ID_G INT not null ,
                                      LOGIN char(15) not null ,
                                      constraint GROUP_PK
                                          primary key  (ID_G,LOGIN),
                                      constraint GROUP_FK
                                          foreign key (LOGIN) references ETUDIANT(LOGIN),
                                      constraint GROUP_FK1
                                          foreign key (ID_G) references GROUPS(ID_G)
);


CREATE TABLE if not exists GROUP_COURS (
                                           ID_G INT not null,
                                           ID_C INT,
                                           ID_F INT,
                                           TAUX_H NUMERIC (6,2),
                                           constraint GROUP_COURS_PK
                                               primary key (ID_G,ID_C),
                                            CONSTRAINT fk_f foreign key (ID_F)
                                                references FILIERE(ID_F),
                                           constraint GROUP_COURS_FK1
                                               foreign key  (ID_G) references GROUPS(ID_G),
                                           constraint GROUP_COURS_FK2
                                               foreign key  (ID_C) references COURS(ID_C)
);



create table if not exists CRENEAUX
(
    DATE_D DATETIME not null,
    DATE_F DATETIME,
    ID_S   int not null,
    ID_G INT      not null,
    ID_C INT NOT NULL ,
    constraint CRENAUX_PK
        primary key (DATE_D, ID_S, ID_G),
    constraint CRENAUX_GROUPE_ID_G_FK
        foreign key (ID_G,ID_C) references GROUP_COURS (ID_G,ID_C),
    constraint CRENAUX_SALLE_ID_FK
        foreign key (ID_S) references SALLE(ID_S),
    constraint unique_cr unique (date_d,id_g)
);

create table if not exists absence(
                                      date_d datetime,
                                      ID_S   int not null,
                                      ID_G INT      not null,
                                      login char(15),
                                      justified bool default false,
                                      constraint abs_pk primary key (login,date_d),
                                      constraint fk foreign key (date_d ,ID_S ,ID_G)references CRENEAUX(date_d ,ID_S ,ID_G),
                                      constraint fk_1 foreign key (login) references ETUDIANT(login)
);


INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Tailor','Anthony','bZe','eTNwi','Anthony_Tailor6705@tonsy.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Morris','Eduardo','exQ','iAMxk','Eduardo_Morris6409@fuliss.net');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Morgan','Tom','Xvb','2PMba','Tom_Morgan3007@deons.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Penn','Roger','HI5','1jJQ0','Roger_Penn9930@nickia.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Glass','Oliver','SVW','xYQhW','Oliver_Glass8356@fuliss.net');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Slater','Eduardo','Ese','LrKK4','Eduardo_Slater805@muall.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Stone','Chad','EpG','XF4yo','Chad_Stone8914@deons.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Morris','Johnathan','zzs','RIyEn','Johnathan_Morris876@grannar.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Pond','Maggie','W3q','NrsB7','Maggie_Pond2070@zorer.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Bradshaw','Jade','M8S','LmaqA','Jade_Bradshaw3303@ubusive.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Neville','Joy','zry','8HukH','Joy_Neville5509@bulaffy.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Lane','Alma','Vdt','m7b0M','Alma_Lane6090@sveldo.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Waterson','Carter','5G7','CieaK','Carter_Waterson8829@ubusive.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Plant','Joseph','g1x','oDVfN','Joseph_Plant796@gembat.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Rose','Sylvia','u6T','NkcEL','Sylvia_Rose8228@irrepsy.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Addison','Denny','cON','cIkYN','Denny_Addison9246@infotech44.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Downing','Deborah','AYr','03qUL','Deborah_Downing7507@corti.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Greenwood','Dakota','qJi','rYnIr','Dakota_Greenwood8839@jiman.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Irving','Leilani','pSK','y85Qh','Leilani_Irving4553@nickia.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Clark','Logan','dLK','ruWyY','Logan_Clark9119@eirey.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Ventura','Logan','vyW','HIrOy','Logan_Ventura78@yahoo.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Brooks','Rufus','6XM','OeEsx','Rufus_Brooks1557@ovock.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Shaw','Clint','nxp','vDflX','Clint_Shaw1606@kideod.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Pratt','Bob','wuv','dJJwP','Bob_Pratt4275@elnee.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Stewart','Angelina','jfw','l1z6m','Angelina_Stewart738@elnee.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Little','Doug','fUH','w5B37','Doug_Little2947@infotech44.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Groves','Drew','XZQ','MXdET','Drew_Groves9388@deons.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Radley','Logan','VY5','rTmOL','Logan_Radley6424@kideod.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Wellington','Chris','Kwx','Rgu5E','Chris_Wellington4599@womeona.net');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Kerr','Bart','pKo','jOgNV','Bart_Kerr6775@cispeto.com');

INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Simpson','Jack','gVa','hV23S','Jack_Simpson3987@bauros.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Sherry','Rick','7Tm','mZgjO','Rick_Sherry9673@ovock.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Addis','Gabriel','1qg','N4kfi','Gabriel_Addis7781@twipet.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Franks','Marissa','6rw','7BYyW','Marissa_Franks4413@qater.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Bradley','Alessia','NYM','iYcpU','Alessia_Bradley158@gembat.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Nicholls','Ema','qHD','KBXO7','Ema_Nicholls5821@zorer.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Chester','Candice','jDo','ZzTmc','Candice_Chester3829@sveldo.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Donovan','Cedrick','wrG','5eAwL','Cedrick_Donovan6988@nimogy.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Pope','Rocco','zWe','ngtid','Rocco_Pope8822@vetan.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Evans','Jacob','djY','SAr7H','Jacob_Evans6076@womeona.net');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Wellington','Eryn','cNt','2WVJA','Eryn_Wellington4783@jiman.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Dale','Christy','moa','3FbUH','Christy_Dale5327@gembat.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('James','Georgia','lTr','Nzm8J','Georgia_James2794@ubusive.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Chester','Zara','TbW','9TZia','Zara_Chester691@bretoux.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Vane','Marissa','ZTP','5B6eq','Marissa_Vane7399@qater.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Torres','Jack','Vva','VTq3z','Jack_Torres1274@guentu.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Khan','Cedrick','X0P','hAAfY','Cedrick_Khan2138@nickia.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Murray','Jaylene','wfj','jlDt2','Jaylene_Murray731@sheye.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Maxwell','Aleksandra','Azl','RaeXH','Aleksandra_Maxwell5722@muall.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Norman','Denis','RuC','IGh99','Denis_Norman9025@gmail.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('James','Josephine','ryB','SDQyB','Josephine_James76@nimogy.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Callan','Alan','Y8M','N1JXv','Alan_Callan7735@extex.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Dobson','Carmella','ct9','GbVtI','Carmella_Dobson7925@gompie.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Marshall','Logan','KXz','REPM2','Logan_Marshall1424@fuliss.net');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Cork','Rufus','GKg','5zLWY','Rufus_Cork8089@deons.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Grey','Sonya','bLF','lYF6R','Sonya_Grey9930@fuliss.net');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Palmer','Celia','jtx','TsLZ6','Celia_Palmer2420@gompie.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Radcliffe','Raquel','54L','VSxXz','Raquel_Radcliffe8111@fuliss.net');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Butler','Tiffany','suN','GKka4','Tiffany_Butler6080@iatim.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Morris','Chadwick','0eR','Mndto','Chadwick_Morris1684@yahoo.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Wood','Olivia','IHE','vtBvG','Olivia_Wood4685@nickia.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Owens','Tania','Ba0','TSk05','Tania_Owens9454@guentu.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Cadman','Jacob','Sx8','hsHdc','Jacob_Cadman4435@bretoux.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Dempsey','Mabel','ksQ','XyjEP','Mabel_Dempsey4536@bretoux.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Yarwood','Matthew','fww','MOgJV','Matthew_Yarwood7203@infotech44.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Ward','Doug','kp5','kHAdY','Doug_Ward26@twace.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Fox','Gil','8hI','0eKq8','Gil_Fox6134@nimogy.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Collis','Aurelia','Sqh','1uIc0','Aurelia_Collis9746@joiniaa.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Ballard','Camden','xXV','foInK','Camden_Ballard5485@vetan.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Thomas','Shannon','7O9','88PVC','Shannon_Thomas7040@naiker.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Tailor','Nathan','fGN','AIy8S','Nathan_Tailor3655@bretoux.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Gilmour','Gabriel','gl5','uEU9P','Gabriel_Gilmour1768@liret.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Nielson','Havana','DgJ','FleWR','Havana_Nielson6332@womeona.net');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Amstead','Chris','Y4M','l5X27','Chris_Amstead7239@brety.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Miller','Anne','fJI','U2WAs','Anne_Miller7597@kideod.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Adams','Mayleen','Htu','3F8Nk','Mayleen_Adams9439@deons.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Saunders','Adalind','eHc','UxCJ2','Adalind_Saunders9846@gembat.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Swan','Martin','khv','ncEUN','Martin_Swan2484@atink.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Gordon','Martin','ItI','jEEuk','Martin_Gordon8682@guentu.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Overson','Martin','FHz','Iglkc','Martin_Overson3801@nickia.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Richardson','Rick','8PE','O8QfK','Rick_Richardson9070@joiniaa.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Gordon','Marvin','DiD','sngMz','Marvin_Gordon9827@gembat.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Terry','Danielle','lOL','vDUPc','Danielle_Terry990@cispeto.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Jeffery','Candice','84d','LsEsX','Candice_Jeffery5422@famism.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Lewin','Bart','RGx','9CqIV','Bart_Lewin6616@jiman.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Nobbs','Denny','aJq','7ChFs','Denny_Nobbs5511@twipet.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('May','Jamie','TXP','DLwGA','Jamie_May464@atink.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Campbell','Julianna','zej','TneBW','Julianna_Campbell6517@gompie.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Norton','Julia','1iT','tybMi','Julia_Norton3595@bauros.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Hancock','Barney','MCU','XAC1k','Barney_Hancock7092@mafthy.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Tennant','Carla','1vH','pyjdL','Carla_Tennant9117@grannar.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Talbot','Callie','N43','ducsh','Callie_Talbot2789@extex.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Wright','George','nsZ','J7KeJ','George_Wright5683@elnee.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Walter','Emma','gH4','SfnDp','Emma_Walter8135@ovock.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Nayler','Alexander','qgH','kwGYQ','Alexander_Nayler9262@sheye.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Kennedy','Ryan','gzM','oecwz','Ryan_Kennedy9354@mafthy.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Saunders','Chris','XN3','zut4H','Chris_Saunders4818@infotech44.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Cassidy','Martin','USo','lWIh5','Martin_Cassidy1158@liret.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Morris','Peter','OcA','u5elS','Peter_Morris7175@nanoff.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Dowson','Matt','Czb','C0Kp0','Matt_Dowson1216@corti.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Henderson','Fiona','dbi','Q8apR','Fiona_Henderson5005@kideod.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Benfield','Tyler','gWV','mHSfV','Tyler_Benfield1065@deavo.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Glass','Lara','Sem','G8zZq','Lara_Glass179@nanoff.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Darcy','Camden','6at','hXUTx','Camden_Darcy6500@bretoux.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Young','Chadwick','2z3','XuFMl','Chadwick_Young1335@tonsy.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('James','Claire','RTW','fMlK5','Claire_James3485@deons.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Andrews','Anais','Juk','nLHll','Anais_Andrews9555@mafthy.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Griffiths','Luke','oPk','yOEI3','Luke_Griffiths435@fuliss.net');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Rainford','Charlotte','4Ub','gtRxZ','Charlotte_Rainford9310@elnee.tech');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Roberts','Julia','hw2','Usi1w','Julia_Roberts9472@bulaffy.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Oswald','Denis','haa','GuJeF','Denis_Oswald2130@jiman.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Overson','Marigold','gzA','sykTO','Marigold_Overson5454@acrit.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Vane','Adela','2RT','7aH93','Adela_Vane7441@supunk.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Harper','Georgia','194','Tfs41','Georgia_Harper333@yahoo.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Ventura','Barney','tNl','9EZYc','Barney_Ventura2708@gembat.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Lee','Juliet','Ca7','wcA3I','Juliet_Lee7236@ubusive.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Ventura','Denis','Sya','0PgtU','Denis_Ventura3794@sheye.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Stone','Janice','kaN','kvIEo','Janice_Stone6804@irrepsy.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Hudson','Destiny','wU7','B3RvO','Destiny_Hudson1092@mafthy.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Overson','Davina','Qgg','oopBI','Davina_Overson4282@irrepsy.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Owens','Scarlett','LJL','mUhkq','Scarlett_Owens7419@joiniaa.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Wheeler','Liam','A9F','d0LKy','Liam_Wheeler8747@grannar.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Chadwick','Logan','OOa','V0C2V','Logan_Chadwick902@vetan.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Vaughan','Elisabeth','fzQ','gGJSw','Elisabeth_Vaughan6542@yahoo.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Wilkinson','Benjamin','t8t','TlClg','Benjamin_Wilkinson3040@gmail.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('James','George','Udh','5YC8l','George_James6731@ubusive.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Cox','Jack','8sC','jMWYO','Jack_Cox2981@bulaffy.com');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Gonzales','Aileen','A3s','McAHi','Aileen_Gonzales8282@typill.biz');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Benfield','Owen','B8B','EnM7K','Owen_Benfield702@zorer.org');
INSERT INTO UTILISATEUR (NOM, PRENOM, PASSWORD, LOGIN,EMAIL) VALUES('Reese','Gwenyth','39U','Bi6JY','Gwenyth_Reese279@deons.tech');


insert into RESPONSABLE (login)values ( 'eTNwi' ),('2PMba'),('iAMxk');
insert into ENSEIGNANT(login)values('1jJQ0'),('xYQhW'),('LrKK4'),('XF4yo'),('RIyEn'),('NrsB7'),('LmaqA');
insert into FILIERE(NOM) values ( 'NONE' );
insert into FILIERE(NOM) values ( 'snone' );
insert into FILIERE(NOM) values ( 'DID' );
insert into FILIERE(NOM) values ( 'MIR' );
insert into FILIERE(NOM) values ( 'INFO' );
insert into FILIERE(NOM) values ( 'ECO' );
insert into FILIERE(NOM) values ( 'MIAGE' );


insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'hV23S',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'mZgjO',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'N4kfi',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'7BYyW',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'iYcpU',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'KBXO7',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'ZzTmc',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'5eAwL',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'ngtid',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'SAr7H',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'2WVJA',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'3FbUH',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'Nzm8J',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'9TZia',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'5B6eq',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'VTq3z',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'hAAfY',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'jlDt2',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'RaeXH',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'IGh99',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'SDQyB',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'N1JXv',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'GbVtI',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'REPM2',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'5zLWY',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'lYF6R',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'TsLZ6',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'VSxXz',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'GKka4',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'Mndto',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'vtBvG',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'TSk05',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'hsHdc',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'XyjEP',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'MOgJV',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'kHAdY',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'0eKq8',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'1uIc0',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'foInK',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'88PVC',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'AIy8S',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'uEU9P',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'FleWR',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'l5X27',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'U2WAs',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'3F8Nk',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'UxCJ2',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'ncEUN',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'jEEuk',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'Iglkc',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'O8QfK',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'sngMz',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'vDUPc',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'LsEsX',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'9CqIV',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'7ChFs',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'DLwGA',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'TneBW',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'tybMi',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'XAC1k',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'pyjdL',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'ducsh',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'J7KeJ',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'SfnDp',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'kwGYQ',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'oecwz',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'zut4H',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'lWIh5',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'u5elS',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'C0Kp0',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'Q8apR',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'mHSfV',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'G8zZq',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'hXUTx',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'XuFMl',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'fMlK5',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'nLHll',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'yOEI3',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'gtRxZ',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'Usi1w',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'GuJeF',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'sykTO',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'7aH93',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'Tfs41',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'9EZYc',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'wcA3I',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'0PgtU',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'kvIEo',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'B3RvO',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'oopBI',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'mUhkq',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'd0LKy',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'V0C2V',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'gGJSw',	2);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'TlClg',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 1',	'2021/2022',	'5YC8l',	5);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'master 2',	'2021/2022',	'jMWYO',	3);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 1',	'2021/2022',	'McAHi',	1);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 2',	'2021/2022',	'EnM7K',	4);
insert into ETUDIANT (nvx_etude, promo, login, ID_F) values ( 'licence 3',	'2021/2022',	'Bi6JY',	3);


insert into salle (NUM,BATIMENT, video_p) values ( '001','U',true );
insert into salle (NUM,BATIMENT, video_p) values ( '003','F',true );
insert into salle (NUM,BATIMENT, video_p) values ( '002','W',true );
insert into salle (NUM,BATIMENT, video_p) values ( '001','F',FALSE );
insert into salle (NUM,BATIMENT, video_p) values ( '003','U',FALSE );
insert into salle (NUM,BATIMENT, video_p) values ( '001','W',FALSE );

insert into salle (NUM,BATIMENT, video_p) values ( '008','U',true );
insert into salle (NUM,BATIMENT, video_p) values ( '003','B',true );
insert into salle (NUM,BATIMENT, video_p) values ( '002','A',true );
insert into salle (NUM,BATIMENT, video_p) values ( '005','F',FALSE );
insert into salle (NUM,BATIMENT, video_p) values ( '013','U',FALSE );
insert into salle (NUM,BATIMENT, video_p) values ( '001','X',FALSE );


insert into COURS(LOGIN, nature, nom) values ( '1jJQ0','CM','Anglais 1' );
insert into COURS(LOGIN , NATURE ,nom) values ('LmaqA' , 'TP' , 'JAVA');
insert into COURS(LOGIN , NATURE,nom) values ('NrsB7' , 'CM' , 'UML');
insert into COURS(LOGIN, NATURE,nom) values ('LrKK4' , 'CM' , 'M_learning');
insert into COURS(LOGIN, NATURE,nom) values ('LrKK4' , 'TD' , 'M_learning');
insert into COURS(LOGIN, NATURE,nom) values ('LrKK4' , 'TD' , 'Traitement_db_1');
insert into COURS(LOGIN , NATURE ,nom) values ('XF4yo' , 'TP' , 'JAVA');
insert into COURS(LOGIN , NATURE ,nom) values ('xYQhW' , 'TD' , 'JAVA');
insert into COURS(LOGIN , NATURE ,nom) values ('xYQhW' , 'TP' , 'Analyse_conception_II');



INSERT into GROUPS(NOM) values ( 'anglais L1' ),('DID master1'),('DID L1'),('MIR L1'),('INFO L1'),('ECO L1'),('MIAGE L1');

insert into GROUP_ETUDIANT(ID_G,LOGIN) values (1,'88PVC'),(1,'U2WAs'),(1,'7aH93'),(1,'SDQyB'),(1,'lYF6R'),(1,'MOgJV'),(1,'VTq3z'),(1,'tybMi'),(1,'SfnDp'),(1,'u5elS'),(1,'hXUTx'),(1,'Iglkc'),(1,'kvIEo'),(1,'V0C2V'),(1,'McAHi'),(1,'hV23S'),(1,'2WVJA'),(1,'KBXO7');
insert into GROUP_ETUDIANT(ID_G,LOGIN) select 2,login from ETUDIANT where NVX_ETUDE='master 1' LIMIT 14;
insert into GROUP_ETUDIANT(ID_G,LOGIN) select 3,login from ETUDIANT where NVX_ETUDE='licence 1' LIMIT 14;
insert into GROUP_ETUDIANT(ID_G,LOGIN) select 5,login from ETUDIANT where NVX_ETUDE='licence 1' and login not in (select login from GROUP_ETUDIANT where ID_G=3) LIMIT 14;
insert into GROUP_COURS(ID_G, ID_C) values ( 1,1 ) ;
insert into GROUP_COURS(ID_G, ID_C,ID_F,TAUX_H) values ( 2,2,1,23.5) ;
insert into GROUP_COURS(ID_G, ID_C,ID_F,TAUX_H) values ( 2,9,1,9) ;
insert into GROUP_COURS(ID_G, ID_C,ID_F,TAUX_H) values ( 3,9,1,13) ;
insert into GROUP_COURS(ID_G, ID_C,ID_F,TAUX_H) values ( 5,6,3,10.7) ;

INSERT INTO CRENEAUX (DATE_D, DATE_F, ID_S, ID_G,ID_C) VALUES ( '2021-12-15 09:45:23.000000
','2021-12-15 12:30:00.000000',1,1,1);

INSERT INTO CRENEAUX (DATE_D, DATE_F, ID_S, ID_G, ID_C) VALUES ( '2021-12-16 16:00:00','2021-12-16 18:00:00',1,1,1 );
INSERT INTO CRENEAUX(DATE_D, DATE_F, ID_S, ID_G, ID_C) VALUES ( '2021-12-14 14:00:00','2021-12-14 18:00:00',3,1,1 );
INSERT INTO CRENEAUX(DATE_D, DATE_F, ID_S, ID_G, ID_C) VALUES ( '2021-12-18 09:00:00','2021-12-18 11:00:00',2,1,1);
INSERT INTO CRENEAUX(DATE_D, DATE_F, ID_S, ID_G, ID_C) VALUES ( '2021-12-17 08:00:00','2021-12-17 11:30:00',7,5,6);
INSERT INTO CRENEAUX(DATE_D, DATE_F, ID_S, ID_G, ID_C) VALUES ( '2021-12-18 16:00:00','2021-12-18 18:30:00',9,3,9);


UPDATE UTILISATEUR SET PASSWORD = HASH('SHA256', PASSWORD);

//select distinct batiment from salle where ID_S not in ( select ID_S FROM CRENEAUX WHERE(DATE_D <= ? and date_f >= ?)and ((date_d between ? and ?)or (date_f between ? and ?)))

//select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,U.NOM,U.PRENOM,U.EMAIL,C2.NOM,C2.NATURE from SALLE join CRENEAUX C on SALLE.ID_S = C.ID_S join GROUP_COURS GC on C.ID_G = GC.ID_G and C.ID_C = GC.ID_C JOIN GROUP_ETUDIANT GE on GC.ID_G=GE.ID_G JOIN COURS C2 on GC.ID_C = C2.ID_C JOIN UTILISATEUR U on C2.LOGIN = U.LOGIN WHERE GE.LOGIN='U2WAs';


//select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,C2.NOM,C2.NATURE,G.nom from SALLE join CRENEAUX C on SALLE.ID_S = C.ID_S join GROUP_COURS GC on C.ID_G = GC.ID_G and C.ID_C = GC.ID_C join COURS C2 on GC.ID_C = C2.ID_C join GROUPS G on GC.ID_G = G.ID_G where C2.LOGIN='1jJQ0';


//Select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,C2.NOM,C2.NATURE,G.nom from SALLE join CRENEAUX C on SALLE.ID_S = C.ID_S join GROUP_COURS GC on C.ID_G = GC.ID_G and C.ID_C = GC.ID_C join COURS C2 on GC.ID_C = C2.ID_C join GROUPS G on GC.ID_G = G.ID_G where C2.LOGIN ='1jJQ0' AND DATE_D;

select LOGIN from ENSEIGNANT ;

insert into creneaux values( '2021-12-20 16:00:00','2021-12-20 16:00:00',(select id_s from salle where num='001' and batiment='U'),(select id_g from groups where nom='DID master1' ),(select id_c from cours where nom='JAVA' and nature ='TP' and login in(select login from utilisateur where CONCAT(nom, ' ', prenom)='Bradshaw Jade')));



select DATE_D, DATE_F, BATIMENT,NUM,VIDEO_P,cours.NOM,NATURE from SALLE join CRENEAUX ON(SALLE.ID_S=CRENEAUX.ID_S) join GROUP_COURS ON (CRENEAUX.ID_G=GROUP_COURS.ID_G)join COURS ON (GROUP_COURS.ID_C = COURS.ID_C) join GROUPS on GROUPS.ID_G=GROUP_COURS.ID_G where GROUPS.NOM='anglais L1';
//update creneaux set DATE_D = '2021-12-23 08:00:00' , DATE_F = '2021-12-23 11:00:00' , ID_S = (select ID_S from SALLE where NUM = '001' and BATIMENT = 'F') where DATE_D = '2021-12-17 08:00:00'
select * from absence;
//select cr.date_d ,nom ,nature from absence a join CRENEAUX cr on a.date_d=cr.DATE_D and a.id_s=cr.ID_S and a.ID_G=cr.ID_G join cours c on c.ID_C=cr.ID_C where login=? ;
//select distinct u.login,u.nom,u.prenom,nbabs, from UTILISATEUR u join (SELECT abs.login,count(abs.login) as nbabs from absence abs join groups g on abs.id_g=g.ID_G where nom=? group by abs.login)  absn on u.LOGIN=absn.login ;

select abs.login from absence abs join GROUPs g on abs.ID_G=g.ID_G;

select nom from UTILISATEUR where login = 'zoom'