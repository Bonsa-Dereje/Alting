create database collegeInfo;
use collegeInfo;

create table collegeData
	(collegeName varchar(250),
	country varchar(15),
	stateLocation varchar(50),
	privateCollege varchar(3),
	acceptanceRate decimal(3,2),
	satReq varchar(30),
	commonAppReq varchar(30),
	questBridge varchar(30) NULL,
	proprieteryApp varchar(30) NULL,
	proprieteryAppLink varchar(100) NOT NULL,
	toeflReq varchar(100) NOT NULL,
	cssProfileCode int PRIMARY KEY,
	avgSAT int,
	avgACT int,
	appFee int,
	tuition int,
	appFeeWaiver varchar(20),
	intlEligiblityReq varchar(50),
	fafsaForm varbinary(MAX),
	lastUpdated datetime DEFAULT getdate());

select *From collegeData;
drop table collegeData;
alter table collegeData 
	add lastUpdated datetime DEFAULT getdate();
alter table collegeData
	drop column finAidOfficeEmail;
ALTER TABLE collegeData
ADD  finAidOfficeEmail VARCHAR(20);


create table financialAid
	(cssProfileCode int PRIMARY KEY,
	grantsAV varchar(3) NOT NULL,
		grantsFullInfo TEXT,
	scholarshipsAV varchar(3) NOT NULL,
		scholarshipFullInfo TEXT,
	meritScholarshipsAV varchar(3) NOT NULL,
		meritScholarshipFullInfo TEXT,
	workStudyAV varchar(3),
		workStudyFullInfo TEXT,
	intlFinancialAidAV varchar(3),
		intlFinancialAidFullInfo TEXT,
	loansAV varchar(3),
		loansFullInfo TEXT,
	finAidOfficeEmail varchar(50),
	CONSTRAINT cssCodeReference FOREIGN KEY (cssProfileCode) 
		REFERENCES collegeData(cssProfileCode));


	drop table financialAid;


create table deadlines
	(cssProfileCode int PRIMARY KEY,
	regularDecision varchar(15),
	earlyDecision varchar(15),
	earlyAction varchar(15),
	restrictiveEarlyAction varchar(15),
	rollingAdmission varchar(15),
	CONSTRAINT cssReference FOREIGN KEY (cssProfileCode) 
		REFERENCES collegeData(cssProfileCode));

drop table deadlines;





