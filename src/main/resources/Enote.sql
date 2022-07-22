use Enote
go
create table Account (
	username varchar(20) NOT NULL primary key,
	passwd nvarchar(1000),
)

go 

create table enote(
	username varchar(20),
	id_note varchar(10) ,
	files_path nvarchar(200),
	files_type varchar(10),
	primary key (username, id_note)

)

alter table enote add constraint fk_usrn
	foreign key (username)
	references Account(username)

go 