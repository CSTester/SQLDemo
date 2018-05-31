package SQL注入;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import commons.IO;

/* 描述
 * 缺陷类型：SQL注入
 * 缺陷描述：SQL注入是一种数据库攻击手段。攻击者通过向应用程序提交恶意代码来改变原SQL语句的含义，进而执行任意SQL命令，达到入侵数据库乃至操作系统的目的。
*/

public class SQL注入 {
		
	public static void Bad(HttpServletRequest request, HttpServletResponse response)
	{
	  String name;
	  
	  name=request.getParameter("NAME");
	  Connection connection=null;
	  Statement statement=null;
	  ResultSet resultSet=null;
	  try
	  {
		  connection = IO.getDBConnection();
		  statement = connection.createStatement();
		  name = name + "+ '1'";
	      resultSet = statement.executeQuery("select * from users where name='"+name+"'");//缺陷爆发行，由于动态构造并执行sql语句
	  }
	  catch (SQLException exceptSql)
	  {
	     System.out.println("Error getting database connection");
	  }
	  finally
	  {
	      try
	      {
	          if (resultSet != null)
	          {
	              resultSet.close();
	          }
	      }
	      catch (SQLException exceptSql)
	      {
	          System.out.println("Error closing ResultSet");
	      }

	      try
	      {
	          if (statement != null)
	          {
	        	  statement.close();
	          }
	      }
	      catch (SQLException exceptSql)
	      {
	          System.out.println("Error closing Statement");
	      }

	      try
	      {
	          if (connection != null)
	          {
	        	  connection.close();
	          }
	      }
	      catch (SQLException exceptSql)
	      {
	          System.out.println("Error closing Connection");
	      }
	  }
	}
	
	public static void Good(HttpServletRequest request, HttpServletResponse response)
	{
		String name;
		name=request.getParameter("NAME");
		Connection connection=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		String sql=null;
		  try
		  {
			  connection = IO.getDBConnection();
			  statement = (PreparedStatement) connection.createStatement();
			  sql="select * from users where name=?";
			  statement = connection.prepareStatement(sql);
			  statement.setString(1,name);  //缺陷修复,使用参数化API进行SQL查询
	          ResultSet rs = statement.executeQuery();
		  }
		  catch (SQLException exceptSql)
		  {
		      System.out.println("Error getting database connection");
		  }
		  finally
		  {
		      try
		      {
		          if (resultSet != null)
		          {
		              resultSet.close();
		          }
		      }
		      catch (SQLException exceptSql)
		      {
		          System.out.println("Error closing ResultSet");
		      }

		      try
		      {
		          if (statement != null)
		          {
		        	  statement.close();
		          }
		      }
		      catch (SQLException exceptSql)
		      {
		          System.out.println("Error closing Statement");
		      }

		      try
		      {
		          if (connection != null)
		          {
		        	  connection.close();
		          }
		      }
		      catch (SQLException exceptSql)
		      {
		          System.out.println("Error closing Connection");
		      }
		  }
	}
}
