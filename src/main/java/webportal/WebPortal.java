package webportal;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebPortal extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fname=req.getParameter("fn");
		String lname=req.getParameter("ln");
		String email=req.getParameter("em");
		String pass=req.getParameter("pw");
		
		String qry1="insert into demo.signup values(?,?,?,?)";
		Connection con=null;
		PreparedStatement ps=null;
		boolean b=true;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","12345");
			ps=con.prepareStatement(qry1);
			ps.setString(1, fname);
			ps.setString(2, lname);
			ps.setString(3, email);
			ps.setString(4, pass);
			ps.executeUpdate();
			if(b) {
				PrintWriter pw=resp.getWriter();
				pw.println("You successfully registered");
				pw.println("<a href=\"signin.html\"><button>Signin</button></a>");
//				RequestDispatcher rd=req.getRequestDispatcher("index.html");
//				rd.forward(req, resp);
			}
			else {
				RequestDispatcher rd=req.getRequestDispatcher("signup.html");
				rd.forward(req, resp);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
