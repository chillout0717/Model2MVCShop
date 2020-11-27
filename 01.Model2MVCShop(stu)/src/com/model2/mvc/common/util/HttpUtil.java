package com.model2.mvc.common.util;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HttpUtil { //xxxx.Action에서 받은 URI을 리턴하는것 확인하는 일을 함
	
	public static void forward(HttpServletRequest request, HttpServletResponse response, String path){
		try{
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}catch(Exception ex){
			System.out.println("forward 오류 : " + ex);
			throw new RuntimeException("forward 오류 : " + ex);
		}
	}
	
	public static void redirect(HttpServletResponse response, String path){
		try{
			response.sendRedirect(path);
		}catch(Exception ex){
			System.out.println("redirect 오류 : " + ex);
			throw new RuntimeException("redirect 오류  : " + ex);
		}
	}
}