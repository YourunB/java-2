USE [TestJavaDB]
GO
/****** Object:  Table [dbo].[user]    Script Date: 19.06.2025 21:57:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user](
	[id] [int] NULL,
	[name] [char](10) NULL,
	[age] [int] NULL,
	[education] [char](10) NULL,
	[works] [char](10) NULL
) ON [PRIMARY]
GO
