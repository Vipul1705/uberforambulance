USE [master]
GO
/****** Object:  Database [ambulance_tracking_lr_db]    Script Date: 03/16/2022 12:32:40 ******/
CREATE DATABASE [ambulance_tracking_lr_db] ON  PRIMARY 
( NAME = N'ambulance_tracking_db', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL10_50.MSSQLSERVER\MSSQL\DATA\ambulance_tracking_db.mdf' , SIZE = 2048KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'ambulance_tracking_db_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL10_50.MSSQLSERVER\MSSQL\DATA\ambulance_tracking_db_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [ambulance_tracking_lr_db].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET ANSI_NULLS OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET ANSI_PADDING OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET ARITHABORT OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET AUTO_CLOSE OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET AUTO_CREATE_STATISTICS ON
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET  DISABLE_BROKER
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET  READ_WRITE
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET RECOVERY FULL
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET  MULTI_USER
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [ambulance_tracking_lr_db] SET DB_CHAINING OFF
GO
EXEC sys.sp_db_vardecimal_storage_format N'ambulance_tracking_lr_db', N'ON'
GO
USE [ambulance_tracking_lr_db]
GO
/****** Object:  Table [dbo].[hospital_master]    Script Date: 03/16/2018 12:32:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[hospital_master](
	[hid] [int] IDENTITY(1,1) NOT NULL,
	[hname] [varchar](max) NOT NULL,
	[location] [varchar](max) NOT NULL,
	[hno] [numeric](18, 0) NULL,
	[email] [varchar](max) NULL,
	[address] [varchar](max) NULL,
	[type] [varchar](max) NULL,
	[lat] [numeric](18, 12) NULL,
	[lon] [numeric](18, 12) NULL,
	[geo_loc] [geography] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[hospital_master] ON
INSERT [dbo].[hospital_master] ([hid], [hname], [location], [hno], [email], [address], [type], [lat], [lon], [geo_loc]) VALUES (2, N'abc', N'Mumbai', CAST(9819652176 AS Numeric(18, 0)), N'test3@mail.com', N'KANDICVALI', N'qwertyu', CAST(19.211896300000 AS Numeric(18, 12)), CAST(72.842072700000 AS Numeric(18, 12)), 0xE6100000010CB6A4FED53E363340AFD6E484E4355240)
INSERT [dbo].[hospital_master] ([hid], [hname], [location], [hno], [email], [address], [type], [lat], [lon], [geo_loc]) VALUES (3, N'Shatabdi Hospital', N'Borivali East', CAST(2228647003 AS Numeric(18, 0)), N'abcd@mail.com', N'Kasturba Cross Road No. 2, Borivali East, Mumbai, Maharashtra 400066', N'general', CAST(19.208349200000 AS Numeric(18, 12)), CAST(72.781289800000 AS Numeric(18, 12)), 0xE6100000010CD425885F56353340B2ECEEA600325240)
SET IDENTITY_INSERT [dbo].[hospital_master] OFF
/****** Object:  Table [dbo].[Charges_master]    Script Date: 03/16/2018 12:32:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Charges_master](
	[chargeid] [int] IDENTITY(1,1) NOT NULL,
	[bookingid] [int] NOT NULL,
	[ambuid] [int] NULL,
	[charges] [numeric](18, 3) NULL,
	[dtme] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[booking_master]    Script Date: 03/16/2018 12:32:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[booking_master](
	[bid] [int] IDENTITY(1,1) NOT NULL,
	[uname] [varchar](max) NOT NULL,
	[drivername] [varchar](max) NOT NULL,
	[hname] [varchar](max) NULL,
	[userid] [int] NULL,
	[ambuid] [int] NULL,
	[hospid] [int] NULL,
	[date] [datetime] NOT NULL,
	[status] [int] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[booking_master] ON
INSERT [dbo].[booking_master] ([bid], [uname], [drivername], [hname], [userid], [ambuid], [hospid], [date], [status]) VALUES (1, N'tejas', N'abc', N'abc', 1, 2, 1, CAST(0x0000A89300DBEACC AS DateTime), 1)
SET IDENTITY_INSERT [dbo].[booking_master] OFF
/****** Object:  Table [dbo].[ambulance_master]    Script Date: 03/16/2018 12:32:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ambulance_master](
	[aid] [int] IDENTITY(1,1) NOT NULL,
	[oname] [varchar](50) NULL,
	[dname] [varchar](max) NOT NULL,
	[contact_no] [varchar](50) NULL,
	[email] [varchar](max) NOT NULL,
	[age] [int] NOT NULL,
	[password] [varchar](max) NOT NULL,
	[status] [bit] NULL,
	[hid] [int] NULL,
	[lat] [numeric](18, 12) NULL,
	[lon] [numeric](18, 12) NULL,
	[curr_loc] [geography] NULL,
	[dt] [datetime] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[ambulance_master] ON
INSERT [dbo].[ambulance_master] ([aid], [oname], [dname], [contact_no], [email], [age], [password], [status], [hid], [lat], [lon], [curr_loc], [dt]) VALUES (1, N'qwe', N'abc', NULL, N'test@mail.com', 21, N'12345', 0, 1, CAST(19.213471800000 AS Numeric(18, 12)), CAST(72.841774200000 AS Numeric(18, 12)), 0xE6100000010C469E7F16A636334076E7E4A0DF355240, NULL)
INSERT [dbo].[ambulance_master] ([aid], [oname], [dname], [contact_no], [email], [age], [password], [status], [hid], [lat], [lon], [curr_loc], [dt]) VALUES (2, N'abc', N'qwe', NULL, N'test1@mail.com', 21, N'12345', 1, 1, CAST(19.213458500000 AS Numeric(18, 12)), CAST(72.841635700000 AS Numeric(18, 12)), 0xE6100000010CA08D5C37A536334059A9FB5BDD355240, NULL)
SET IDENTITY_INSERT [dbo].[ambulance_master] OFF
/****** Object:  Table [dbo].[admin_master]    Script Date: 03/16/2018 12:32:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[admin_master](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[username] [varchar](max) NOT NULL,
	[password] [varchar](max) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[admin_master] ON
INSERT [dbo].[admin_master] ([id], [username], [password]) VALUES (1, N'admin', N'admin')
SET IDENTITY_INSERT [dbo].[admin_master] OFF
/****** Object:  Table [dbo].[status_master]    Script Date: 03/16/2018 12:32:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[status_master](
	[status_id] [int] IDENTITY(1,1) NOT NULL,
	[status_value] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[status_master] ON
INSERT [dbo].[status_master] ([status_id], [status_value]) VALUES (1, N'new')
INSERT [dbo].[status_master] ([status_id], [status_value]) VALUES (2, N'accepted')
INSERT [dbo].[status_master] ([status_id], [status_value]) VALUES (3, N'closed')
INSERT [dbo].[status_master] ([status_id], [status_value]) VALUES (4, N'rejected')
SET IDENTITY_INSERT [dbo].[status_master] OFF
/****** Object:  Table [dbo].[user_master]    Script Date: 03/16/2018 12:32:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[user_master](
	[uid] [int] IDENTITY(1,1) NOT NULL,
	[uname] [varchar](max) NOT NULL,
	[uno] [numeric](18, 0) NOT NULL,
	[email] [varchar](max) NOT NULL,
	[password] [varchar](max) NOT NULL,
	[address] [varchar](max) NOT NULL,
	[hid] [int] NULL,
	[aid] [int] NULL,
	[lat] [numeric](18, 12) NULL,
	[lon] [numeric](18, 12) NULL,
	[curr_loc] [geography] NULL,
	[dt] [datetime] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[user_master] ON
INSERT [dbo].[user_master] ([uid], [uname], [uno], [email], [password], [address], [hid], [aid], [lat], [lon], [curr_loc], [dt]) VALUES (1, N'tejas', CAST(9819652176 AS Numeric(18, 0)), N'test@mail.com', N'12345', N'borivali', 1, 1, CAST(19.214419000000 AS Numeric(18, 12)), CAST(72.836837000000 AS Numeric(18, 12)), NULL, NULL)
INSERT [dbo].[user_master] ([uid], [uname], [uno], [email], [password], [address], [hid], [aid], [lat], [lon], [curr_loc], [dt]) VALUES (2, N'yuvraj', CAST(1234567890 AS Numeric(18, 0)), N'test1@mail.com', N'12345', N'kandivali', 1, 1, NULL, NULL, NULL, NULL)
INSERT [dbo].[user_master] ([uid], [uname], [uno], [email], [password], [address], [hid], [aid], [lat], [lon], [curr_loc], [dt]) VALUES (3, N'abc', CAST(1234567890 AS Numeric(18, 0)), N'test2@mail.com', N'12345', N'malad', 1, 1, NULL, NULL, NULL, NULL)
SET IDENTITY_INSERT [dbo].[user_master] OFF
/****** Object:  StoredProcedure [dbo].[uploadAmbulocation]    Script Date: 03/16/2018 12:32:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		sy
-- =============================================
CREATE PROCEDURE [dbo].[uploadAmbulocation]
	-- Add the parameters for the stored procedure here
@lat numeric(18,12),
@lon numeric(18,12),
@amb_id int 
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	declare @longitude decimal(15, 12) =@lon,@latitude decimal(14, 12) = @lat;
    declare @p geography = geography::Point(@latitude, @longitude, 4326);
    	
	update ambulance_master set lat=@lat ,lon=@lon,curr_loc=@p where aid=@amb_id
	
END
GO
/****** Object:  StoredProcedure [dbo].[updateHospital]    Script Date: 03/16/2018 12:32:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		sy
-- =============================================
CREATE PROCEDURE [dbo].[updateHospital]
	-- Add the parameters for the stored procedure here
	@hid int,
	@hname varchar(max),
	@hno numeric(18,0),
	@location varchar(max),
	@address varchar(max),
	@type varchar(max),
	@email varchar(50),
	@lat numeric(18,12),
	@lon numeric(18,12)
	
AS
BEGIN

	SET NOCOUNT ON;
	
	declare @longitude decimal(15, 12) =@lon,@latitude decimal(14, 12) = @lat;
    declare @p geography = geography::Point(@latitude, @longitude, 4326);
    
	update hospital_master set @hname=@hname,location=@location,hno=@hno,address=@address,type=@type,lat=@lat,lon=@lon,geo_loc=@p where hid=@hid

END
GO
/****** Object:  StoredProcedure [dbo].[spInsertCharges]    Script Date: 03/16/2018 12:32:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		sy
-- =============================================
CREATE PROCEDURE [dbo].[spInsertCharges]
	@bookId int,
	@ambId int,
	@charge int
AS
BEGIN
	SET NOCOUNT ON;

	if exists(select * from Charges_master where bookingid=@bookId)
	begin
		select * from Charges_master where bookingid=@bookId
	end
	else
	begin
		insert into Charges_master( bookingid, ambuid, charges, dtme) values (@bookId,@ambId,@charge,GETDATE())
	end

END
GO
/****** Object:  StoredProcedure [dbo].[spCloseBooking]    Script Date: 03/16/2018 12:32:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		sy
-- =============================================
CREATE PROCEDURE [dbo].[spCloseBooking]
@bookingid int,
@ambId int
AS
BEGIN
	SET NOCOUNT ON;

	update booking_master set status=3 where bid=@bookingid
	update ambulance_master set status=0 where aid=@ambid;
	
END
GO
/****** Object:  StoredProcedure [dbo].[spAcceptBooking]    Script Date: 03/16/2018 12:32:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		sy
-- =============================================
CREATE PROCEDURE [dbo].[spAcceptBooking]

@ambid int,
@bookingid int

AS
BEGIN

	SET NOCOUNT ON;
	
	update booking_master set status=2 where bid=@bookingid;
	
	update booking_master set status=4 where ambuid=@ambid and status=1;
	
	update ambulance_master set status=1 where aid=@ambid;

	select bid,userid,u.uname,u.lat as userlat,u.lon as userlon,hospid,h.hname,h.lat as hosplat,h.lon as hosplon from booking_master left join user_master as u on userid=uid left join hospital_master as h on hospid=h.hid where bid=@bookingid
END
GO
/****** Object:  StoredProcedure [dbo].[nearest_hospitals]    Script Date: 03/16/2018 12:32:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		sy
-- =============================================
CREATE PROCEDURE [dbo].[nearest_hospitals]

@lat numeric(18,12),
@lon numeric(18,12)

AS
BEGIN

	SET NOCOUNT ON;

	declare @distanceKM int='10';
	declare @longitude decimal(15, 12) =@lon,@latitude decimal(14, 12) = @lat;
    declare @p geography = geography::Point(@latitude, @longitude, 4326);
    -- Insert statements for procedure here
	declare @distanceM int = @distanceKM * 1000;
	
    select hid,hname,hno,email,address,lat,lon,hno,@p.STDistance(geo_loc) as [DistanceInM] from hospital_master
    where @p.STDistance(geo_loc) <= @distanceM 
    order by @p.STDistance(geo_loc);
	
END
GO
/****** Object:  StoredProcedure [dbo].[nearest_ambulance]    Script Date: 03/16/2018 12:32:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		sy
-- =============================================
CREATE PROCEDURE [dbo].[nearest_ambulance]

@lat numeric(18,12),
@lon numeric(18,12)

AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	declare @distanceKM int='5';
	declare @longitude decimal(15, 12) =@lon,@latitude decimal(14, 12) = @lat;
    declare @p geography = geography::Point(@latitude, @longitude, 4326);
    -- Insert statements for procedure here
	declare @distanceM int = @distanceKM * 1000;
	
    select aid,oname,lat,lon,contact_no,@p.STDistance(curr_loc) as [DistanceInM] from ambulance_master
    where @p.STDistance(curr_loc) <= @distanceM and status=0
    order by @p.STDistance(curr_loc);
	
	
END
GO
/****** Object:  StoredProcedure [dbo].[insertHospital]    Script Date: 03/16/2018 12:32:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		sy
-- =============================================
CREATE PROCEDURE [dbo].[insertHospital] 
	-- Add the parameters for the stored procedure here
	@hname varchar(max),
	@location varchar(max),
	@hno numeric(18,0),
	@email varchar(max),
	@address varchar(max),
	@type varchar(max),
	@lat numeric(18,12),
	@lon numeric(18,12)
AS
BEGIN
	SET NOCOUNT ON;
	declare @longitude decimal(15, 12) =@lon,@latitude decimal(14, 12) = @lat;
    declare @p geography = geography::Point(@latitude, @longitude, 4326);
    
	insert into hospital_master(hname,location,hno,email,address,type,lat,lon,geo_loc) 
			values(@hname,@location,@hno,@email,@address,@type,@latitude,@longitude,@p)
END
GO
/****** Object:  StoredProcedure [dbo].[bookAmbulance]    Script Date: 03/16/2018 12:32:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		sy
-- =============================================
CREATE PROCEDURE [dbo].[bookAmbulance]
	-- Add the parameters for the stored procedure here
@userid int,
@ambid int,
@hospid int,
@ulat numeric(18,12),
@ulon numeric(18,12)
AS
BEGIN
	SET NOCOUNT ON;

	insert into booking_master(userid,uname,drivername, ambuid, hospid, date,status) values(@userid,'','',@ambid,@hospid,GETDATE(),1)
	
	update user_master set lat=@ulat, lon=@ulon where uid=@userid

END
GO
