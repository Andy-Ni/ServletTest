package mypackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet
{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		/*if(username.equals("niyunsheng") &&password.equals("niyunsheng"))
		{
			resp.getOutputStream().println("Login Sucess");
		}
		else
		{
			resp.getOutputStream().println("Login Failed");
		}*/
		Connection connection = null;
		try
		{  
			Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动  
			connection = DriverManager.getConnection(  
	                    "jdbc:mysql://localhost:3306/test", "root", "mysqlnystsn");// 创建数据连接  
	              
		} catch (Exception e) {  
	            resp.getOutputStream().println(":<Sorry, db connection failed");
	        }  
		String sql = "select * from user where username = \'"
	        + username 
	        +"\' and password = sha1(\'"+password+"\')"; 
		Statement st;
		ResultSet rs;
		try
		{
			if(sql.equals(""))
			{
				resp.getOutputStream().println("SQL query is empty!");
			}
			st = (Statement) connection.createStatement();
			rs= st.executeQuery(sql);
			if(rs.next())
			{
				resp.getOutputStream().println("Login success");
				Cookie cookie = new Cookie("username", username);
				cookie.setPath("/");
				resp.addCookie(cookie);
			}
			else
			{
				resp.getOutputStream().println("Login failed");
			}
		} catch (SQLException e)
		{
			resp.getOutputStream().println("Sorry, query execution failed");
		}
		
	}
}
