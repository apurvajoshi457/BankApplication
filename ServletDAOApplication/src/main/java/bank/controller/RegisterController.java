package bank.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bank.dao.RegisterDao;
import bank.dao.RegisterDaoImpl;
import bank.model.Register;


@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int regno=Integer.parseInt(request.getParameter("regno"));
		String name=request.getParameter("name");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		float balance=Float.parseFloat(request.getParameter("balance"));
		
		
		List<Register> lstregister=new ArrayList<Register>();
		
		Register reg=new Register(regno,name,username,password,balance);
		lstregister.add(reg);
		
		RegisterDao rdao=new RegisterDaoImpl();
		int i=rdao.createRecord(lstregister);
		if(i>0) {
			response.sendRedirect("login.html");
		}
		else {
			response.sendRedirect("error.html");
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
