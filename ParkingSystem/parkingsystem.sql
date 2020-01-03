drop database parkingsystem;

create database parkingsystem;

use parkingsystem;

create table Important(
	password varchar(20) unique,
	timepay int(10),
	seasonpay int(10),
	floor int(10),
	place int(10),
	small1 int(4),
	small2 int(4),
	middle1 int(4),
	middle2 int(4),
	large1 int(4),
	large2 int(4)
);

insert into Important values('root',1500,80000,15,20,11,15,6,10,1,5);

create table maindata(
  inputtime timestamp,
  name varchar(20),
  carsize varchar(20),
  car varchar(20),
  carname varchar(20) unique,
  season varchar(20),
  carfloor varchar(20),
  caradd varchar(20)
);

create table season(
	carname varchar(20) unique,
	last timestamp
);


create table history(
	nowtime timestamp,
	action varchar(20),
	x int(10),
	y int(10),
	income int(10),
	carname varchar(20)
);

create table carlist(
	car varchar(20),
	carsize varchar(20)
);