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
		map = new HashMap<String, Action>(); //String action���� �ؼ� ���� �ִµ� xxx.do�ϋ� xx.action�� �ൿ�ض� 
		InputStream in = null; //�о� ���̱����� inputsteam
		try{
			in = getClass().getClassLoader().getResourceAsStream(resources);						//getClass().getClassLoader().getResource()�� �׻� ��������(absolute) ���ҽ� ��η� ���ҽ��� Ž���ϰ� �ȴ� 
																				//getResourceAsStreams�� �׳� �ٸ��ҽ��� ������ inputstream���� �ޱ����ؼ� asstrema���� ��
			properties = new Properties(); //key vlaue�� �����ϱ� ���� properties�� ������
			properties.load(in);//load��� �޼ҵ带 ���ؼ� ������Ƽ ������ �о� �;� �ϴµ� �̶� ���޵Ǵ� ���ڴ� InputStream�̴�. load�ϸ� �˾Ƽ� Ű������ �ľ�����
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties ���� �ε� ���� :"  + ex);
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
	
	
	//getAction�� xx.do �϶� ~~.Acrion�� ã����
	public Action getAction(String path){ //action�� ��� ���� �۾�
		Action action = map.get(path);//path�� map�� �־ ���� value���� action�� ���� ó������ ����
		if(action == null){
			String className = properties.getProperty(path);
			System.out.println("prop : " + properties);
			System.out.println("path : " + path);			
			System.out.println("className : " + className);//Ŭ�������ӿ��� �����̸��̶� �����̸� ���� ����� com.model2.mvc.view.product.ListProductAction �̷��� 
			className = className.trim();// Ŭ�������ӿ� ������ ������
			try{
				Class c = Class.forName(className);//�������� Ŭ���� ���ϸ��� ���ڷ� �־��ָ� �̿� �ش��ϴ� Ŭ������ ��ȯ����Ŭ������ �����ϱ� ���� Ŭ����
				Object obj = c.newInstance(); //class c�� �ν��Ͻ� ���� �װ� ������Ʈ�� ����
				if(obj instanceof Action){ // action�� obj�� ����ȯ�� �������� �˾ƺ�������
					map.put(path, (Action)obj);//map�� path�� (action)obj�� Ű������ �ְ� ���� 
					action = (Action)obj;
				}else{
					throw new ClassCastException("Class����ȯ�� ���� �߻�  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action������ ���ϴ� ���� ���� �߻� : " + ex);
			}
		}
		return action; //actionservlet�� 34���ٿ� acton�� ��ȯ��
	}
}