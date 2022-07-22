create database Enote
go

use Enote
go
create table Account (
	username varchar(255) NOT NULL primary key,
	pwd varchar(255),
)

go 

create table enote(
	username varchar(255),
	id_note int identity(1,1),
	files_path varchar(255),
	files_type varchar(255),
	primary key (id_note)

)

alter table enote add constraint fk_usrn
	foreign key (username)
	references Account(username)

go 

create proc usp_signin @username varchar(255), @pwd varchar(255), @result varchar(255) output
as 
begin
	if exists 
		(select * from Account where username = @username and pwd = @pwd) 
		set @result = 'success'
	else if exists 
		(select * from Account where username = @username) 
		set @result = 'wrong password'
	else
		set @result = 'username does not exist'

end
go


create proc usp_signup @username varchar(255), @pwd varchar(255), @result varchar(255) output
as 
begin
		if exists 
		(select * from Account where username = @username) 
		set @result = 'username already taken'
	else
		begin
			insert into Account values(@username,@pwd)
			set @result = 'success'
		end

end
go




insert into Account values
	('hoan3232','123anhhoan32'),
	('phanvy','phanvy'),
	('ptd0301','ptd0301')
go

declare @output_ varchar(255)
--exec usp_signin 'hoan3232', '123anhhoan3', @output_ output
exec usp_signup 'hoan323233', '12345', @output_ output
select @output_




--drop database Enote