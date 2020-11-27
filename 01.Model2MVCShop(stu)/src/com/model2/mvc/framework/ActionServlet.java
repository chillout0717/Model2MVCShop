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
		String resources=getServletConfig().getInitParameter("resources"); //metadata�� ������
																			//web.xml�� �ִ� resources�� properties�� ȣ���ϰ� �ϰ� �׾ȿ��ִ� ���ҽ����� ���ҽ��� ����
		mapper=RequestMapping.getInstance(resources);// �� ���ҽ��� key value�� ������ reqmapping�� �����ϴµ� requestMapping���� �Ѿ��
		
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		
		String url = request.getRequestURI();// ������Ʈ���� url�� ������  http://localhost:8080/project/list.jsp�̸� project/list.jsp���� ������
		System.out.println("�̰� url"+url);
		String contextPath = request.getContextPath();//http://localhost:8080/board/list.do �� ��ΰ� �ִٸ� http://localhost:8080/board�� root�� ���
		System.out.println("�̰� contextPath"+contextPath);
		String path = url.substring(contextPath.length());//������ contextPath���̰� �����̹Ƿ� http://localhost:8080/project/���ķ��� list.jsp�� path�� ����
		System.out.println("�̰� path"+path); //�ּҸ� ������ ���� action Ŭ������ �����ϴ����� ����
		
		try{
			Action action = mapper.getAction(path); //getaction���� path�� �־ ���� action
			action.setServletContext(getServletContext()); //�̰� ������ �ѹ� �����ҵ�
			
			String resultPage=action.execute(request, response); //xxx.action�� ���ϰ��� ���⿡ ��� 
			System.out.println("resultpage ���Դϴ� "+resultPage); //forward:/product/listProduct.jsp
			String result=resultPage.substring(resultPage.indexOf(":")+1);
			System.out.println("�̰��� result���Դϴ� "+result); ///product/listProduct.jsp
			
			if(resultPage.startsWith("forward:"))//resultPage�� ���������� �����̷�Ʈ���� �����ؼ� httpUtil�� ������
				HttpUtil.forward(request, response, result);
			else
				HttpUtil.redirect(response, result);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}