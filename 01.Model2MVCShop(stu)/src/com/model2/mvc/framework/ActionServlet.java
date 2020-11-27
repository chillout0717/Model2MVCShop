package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;


public class ActionServlet extends HttpServlet {
	
	private RequestMapping mapper;

	@Override
	public void init() throws ServletException {
		super.init();
		String resources=getServletConfig().getInitParameter("resources"); //metadata를 가져옴
																			//web.xml에 있는 resources가 properties로 호출하게 하고 그안에있는 리소스들을 리소스에 저장
		mapper=RequestMapping.getInstance(resources);// 그 리소스를 key value로 나눠서 reqmapping에 저장하는듯 requestMapping으로 넘어가서
		
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		
		String url = request.getRequestURI();// 리퀘스트에서 url을 가져옴  http://localhost:8080/project/list.jsp이면 project/list.jsp까지 가져옴
		System.out.println("이건 url"+url);
		String contextPath = request.getContextPath();//http://localhost:8080/board/list.do 의 경로가 있다면 http://localhost:8080/board가 root에 담김
		System.out.println("이건 contextPath"+contextPath);
		String path = url.substring(contextPath.length());//시작이 contextPath길이가 시작이므로 http://localhost:8080/project/이후로의 list.jsp가 path에 적힘
		System.out.println("이건 path"+path); //주소를 가져옴 무슨 action 클래스로 가야하는지에 사용됨
		
		try{
			Action action = mapper.getAction(path); //getaction으로 path를 넣어서 얻은 action
			action.setServletContext(getServletContext()); //이건 집가서 한번 봐야할듯
			
			String resultPage=action.execute(request, response); //xxx.action의 리턴값이 여기에 담김 
			System.out.println("resultpage 값입니다 "+resultPage); //forward:/product/listProduct.jsp
			String result=resultPage.substring(resultPage.indexOf(":")+1);
			System.out.println("이것은 result값입니다 "+result); ///product/listProduct.jsp
			
			if(resultPage.startsWith("forward:"))//resultPage가 포워드인지 리다이렉트인지 구분해서 httpUtil에 보내줌
				HttpUtil.forward(request, response, result);
			else
				HttpUtil.redirect(response, result);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}