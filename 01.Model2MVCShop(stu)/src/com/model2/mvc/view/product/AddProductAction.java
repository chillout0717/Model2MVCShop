package com.model2.mvc.view.product;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public class AddProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(FileUpload.isMultipartContent(request)) {
			//String temDir = 
					
	DiskFileUpload fileUpload = new DiskFileUpload();
	fileUpload.setRepositoryPath(temDir);
	
	fileUpload.setSizeMax(1024*1024*10);
	
	fileUpload.setSizeThreshold(1024*100);
	
	if(request.getContentLength() < fileUpload.getSizeMax()) {
		ProductVO productVO = new ProductVO();
		
		StringTokenizer token = null; 
		
		List fileItemList = fileUpload.parseRequest(request);
		int Size = fileItemList.size();
		for(int i=0; i<Size; i++) {
			FileItem fileItem = (FileItem) fileItemList.get(i);
			
			if(fileItem.isFormField()) {
				if(fileItem.getFieldName().equals("manuDate")) {
					token = new StringTokenizer(fileItem.getString("euc-kr"),"-");
					String manuDate = token.nextToken() + token.nextToken() + token.nextToken();
					productVO.setManuDate(manuDate);
				}
				else if(fileItem.getFieldName().equals("prodName"))
					productVO.setFileName(fileItem.getString("euc-kr"));
				else if(fileItem.getFieldName().equals("prodDetail"))
					productVO.setProdDetail(fileItem.getString());
			}
		}
	}
	
	
	
			
	}	
}
