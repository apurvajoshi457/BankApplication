package bank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bank.dao.RegisterDao;
import bank.dao.RegisterDaoImpl;
import bank.model.Register;

/**
 * Servlet implementation class DisplayController
 */
@WebServlet("/DisplayController")
public class DisplayController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		RegisterDao rdao=new RegisterDaoImpl();
		List<Register> lst=rdao.displayAll();
		
		PrintWriter pw=response.getWriter();
		
			for(Register robj: lst) {
			pw.println("<table><tr>");
			pw.println("<td>" + robj.getRegNO() + "</td>");
			pw.println("<td>" + robj.getName() + "</td>");
			pw.println("<td>" + robj.getUsername() + "</td>");
			pw.println("<td>" + robj.getPassword() + "</td>");
			pw.println("<td>" + robj.getBalance() + "</td>");
			pw.println("</tr></table>");
			}
			
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
