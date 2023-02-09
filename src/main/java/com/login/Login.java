package com.login;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("uname");
		String pwd = request.getParameter("pwd");
		
		
		String url = "jdbc:mysql://localhost:3306/epsi_db";
		String db_uname = "user1";
		String db_pass="L'EPS1_user1";
		
		String query = "select username from user where username='" + uname + "' and password='" + pwd + "';";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); //Chargement du driver
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("failed to load the driver");
		}
		try {
			Connection con = DriverManager.getConnection(url, db_uname, db_pass); //Connection à la base de donnée
			if (con != null) {
		        System.out.println("Successfully connected to MySQL database epsi_db");
		     }

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query); //Envoi de la requête
			
			/*Lecture du résultat de la requête*/
			if(rs.next() == false) {
				//Le couple identifiant/mdp est faux
				response.sendRedirect("login.jsp");
			}
			else {
				//Connexion autorisée
				HttpSession session = request.getSession();
				session.setAttribute("uname", uname);
				response.sendRedirect("index.jsp");
			}
			st.close();
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("failed to connect to the database or to send the query");
		}
	}

}
