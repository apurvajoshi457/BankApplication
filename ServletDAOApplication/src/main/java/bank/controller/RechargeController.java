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
 * Servlet implementation class RechargeController
 */
@WebServlet("/RechargeController")
public class RechargeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RechargeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int regNo=Integer.parseInt(request.getParameter("regNo"));
		float bal=0;
		
		String baltype=request.getParameter("baltype");
		
		if(baltype.equalsIgnoreCase("first")) {
			System.out.println("test1");
			 bal=499;
		}
		else if(baltype.equalsIgnoreCase("second")) {
			System.out.println("test2");
			 bal=699;
		}
		else if(baltype.equalsIgnoreCase("third")) {
			 bal=799;
		}

		RegisterDao rdao=new RegisterDaoImpl();
		int i=rdao.recharge(regNo, bal);
		PrintWriter pw=response.getWriter();
		
		if(i>0) {
			pw.println("Recharged successfully !");
		}
		else {
			pw.println("Cannot recharge !");
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
