package bank.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bank.dao.RegisterDao;
import bank.dao.RegisterDaoImpl;

/**
 * Servlet implementation class SendtoaccnoController
 */
@WebServlet("/SendtoaccnoController")
public class SendtoaccnoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendtoaccnoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int regNo=Integer.parseInt(request.getParameter("regNo"));
		float bal=Float.parseFloat(request.getParameter("amt"));
		int regNo2=Integer.parseInt(request.getParameter("regNo2"));
	
		RegisterDao rdao=new RegisterDaoImpl();
		int i=rdao.sendtoaccno(regNo, bal, regNo2);
		PrintWriter pw=response.getWriter();
		
		if(i>0) {
			pw.println("Transaction Done!");
		}
		else {
			pw.println("Please try again later!");
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
