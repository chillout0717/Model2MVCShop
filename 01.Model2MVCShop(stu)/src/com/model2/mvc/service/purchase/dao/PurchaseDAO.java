package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;


public class PurchaseDAO {

	public PurchaseDAO(){	
	}
	
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception{
	
	ProductVO productVO = new ProductVO();
	UserVO userVO = new UserVO();
		
		
	Connection con = DBUtil.getConnection();
	
	String sql = "insert into transaction values"
			+ "(seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,to_char(sysdate, 'YYYYMMDD',?)";
	
	PreparedStatement stmt = con.prepareStatement(sql);
	
	stmt.setInt(1,productVO.getProdNo());
	stmt.setString(2, userVO.getUserId());
	stmt.setString(3, purchaseVO.getPaymentOption());
	stmt.setString(4, purchaseVO.getReceiverName());
	stmt.setString(5, purchaseVO.getReceiverPhone());
	stmt.setString(6, purchaseVO.getDivyAddr());
	stmt.setString(7, purchaseVO.getDivyRequest());
	stmt.setNString(8, purchaseVO.getTranCode());
	stmt.setString(10,purchaseVO.getDivyDate());
	
	stmt.executeUpdate();
	
	con.close();
	
}
	
	
	
	
}
