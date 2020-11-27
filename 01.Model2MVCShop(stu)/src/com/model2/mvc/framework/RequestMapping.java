package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class RequestMapping {
	
	private static RequestMapping requestMapping;
	private Map<String, Action> map;
	private Properties properties;
	
	private RequestMapping(String resources) {
		map = new HashMap<String, Action>(); //String action으로 해서 값을 넣는듯 xxx.do일떄 xx.action이 행동해라 
		InputStream in = null; //읽어 들이기위한 inputsteam
		try{
			in = getClass().getClassLoader().getResourceAsStream(resources);						//getClass().getClassLoader().getResource()는 항상 절대적인(absolute) 리소스 경로로 리소스를 탐색하게 된다 
																				//getResourceAsStreams는 그냥 겟리소스랑 같은데 inputstream으로 받기위해서 asstrema으로 함
			properties = new Properties(); //key vlaue로 저장하기 위한 properties를 생성함
			properties.load(in);//load라는 메소드를 통해서 프로퍼티 파일일 읽어 와야 하는데 이때 전달되는 인자는 InputStream이다. load하면 알아서 키벨류로 파씽해줌
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties 파일 로딩 실패 :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}
	}
	
	public synchronized static RequestMapping getInstance(String resources){
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
			
		}
		return requestMapping;
	}
	
	
	//getAction은 xx.do 일때 ~~.Acrion을 찾아줌
	public Action getAction(String path){ //action을 얻기 위한 작업
		Action action = map.get(path);//path를 map에 넣어서 나온 value값을 action에 저장 처음에는 없음
		if(action == null){
			String className = properties.getProperty(path);
			System.out.println("prop : " + properties);
			System.out.println("path : " + path);			
			System.out.println("className : " + className);//클래스내임에는 폴더이름이랑 파일이름 까지 저장됨 com.model2.mvc.view.product.ListProductAction 이렇게 
			className = className.trim();// 클래스내임에 여백을 지워줌
			try{
				Class c = Class.forName(className);//물리적인 클래스 파일명을 인자로 넣어주면 이에 해당하는 클래스를 반환해줌클래스를 조사하기 위한 클래스
				Object obj = c.newInstance(); //class c에 인스턴스 생성 그걸 오브젝트에 넣음
				if(obj instanceof Action){ // action이 obj로 형변환이 가능한지 알아보고있음
					map.put(path, (Action)obj);//map에 path랑 (action)obj를 키벨류로 넣고 있음 
					action = (Action)obj;
				}else{
					throw new ClassCastException("Class형변환시 오류 발생  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action정보를 구하는 도중 오류 발생 : " + ex);
			}
		}
		return action; //actionservlet에 34번줄에 acton을 반환함
	}
}