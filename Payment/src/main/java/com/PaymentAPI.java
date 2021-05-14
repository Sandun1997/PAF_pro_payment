package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/PaymentAPI")
public class PaymentAPI extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	Payment Obj = new Payment();
	
    public PaymentAPI() {
        super();
   
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String output = Obj.readPayment();
		
		response.getWriter().write(output.toString());

	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String output = Obj.insertPayment(request.getParameter("name_on_card"), 
				request.getParameter("card_number"),
				request.getParameter("cvv"),
				request.getParameter("exp_day"),
				request.getParameter("amout"));
		
		System.out.println(output);
		
		response.getWriter().write(output);
		
		
	}


	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ID = request.getParameter("ID");
		String name = request.getParameter("name");
		String card_number = request.getParameter("card_number");
		String cvv = request.getParameter("cvv");
		String exp_day = request.getParameter("exp_day");
		String amount = request.getParameter("amout");
		System.out.println("ID: "+ID);
	
		String output = Obj.updatePayment(ID,name, card_number, cvv, exp_day, amount);
		
		response.getWriter().write(output);
		
	}


	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("pId");

		String output = Obj.deletePayment(id);
		
		System.out.println(output);
		
		response.getWriter().write(output.toString());
	
	}
	
	
	private static Map getParseMap(HttpServletRequest request) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			Scanner sc = new Scanner(request.getInputStream(), "UTF-8");
			String query = sc.hasNext() ? sc.useDelimiter("\\A").next() : "";
			sc.close();
			
			String[] params = query.split("&");
			
			for(String param : params) {
				
				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
