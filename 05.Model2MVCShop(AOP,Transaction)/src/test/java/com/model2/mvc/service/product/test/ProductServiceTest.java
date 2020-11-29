package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-common.xml",
											"classpath:config/context-aspect.xml",
													"classpath:config/context-mybatis.xml",
														"classpath:config/context-transaction.xml" })
public class ProductServiceTest {

	
	@Autowired
	@Qualifier("productServiceImpl")
	
	private ProductService productService;
	
	//@Test
	public void testAddProduct() throws Exception{
		
		Product product = new Product();
		product.setProdNo(10073);
		product.setProdName("1111");
		product.setProdDetail("111");
		product.setManuDate("2020-11-04");
		product.setPrice(Integer.parseInt("111"));
		product.setFileName("11");
	
		productService.addProduct(product);
		
		product = productService.getProduct(10073);
		
		Assert.assertEquals(10073, product.getProdNo());
		Assert.assertEquals("1111", product.getProdName());
		Assert.assertEquals("111", product.getProdDetail());
		Assert.assertEquals("2020-11-04", product.getManuDate());
		Assert.assertEquals(111, product.getPrice());
		Assert.assertEquals("11", product.getFileName());		
	}
	
	//@Test
	public void testGetProduct() throws Exception{
		Product product = new Product();
		
		product = productService.getProduct(10082);
		
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("1988-07-17", product.getManuDate());
		Assert.assertEquals(Integer.parseInt("20000"), product.getPrice());
		Assert.assertEquals("testFileName", product.getFileName());	
		
		Assert.assertNotNull(productService.getProduct(Integer.parseInt("10000")));
		
	}
		//@Test
		public void testUpdateProduct() throws Exception{
			
		Product product = productService.getProduct(10073);
		Assert.assertNotNull(product);	
			
		Assert.assertEquals("1111", product.getProdName());
		Assert.assertEquals("111", product.getProdDetail());
		Assert.assertEquals("2020-11-04", product.getManuDate());
		Assert.assertEquals(Integer.parseInt("111"), product.getPrice());
		Assert.assertEquals("11", product.getFileName());	
		
		product.setProdName("change");
		product.setProdDetail("Detail");
		product.setManuDate("2020-11-26");
		product.setPrice(Integer.parseInt("10000"));
		product.setFileName("fileName");
		
		productService.updateProduct(product);
		
		product = productService.getProduct(10073);
		Assert.assertNotNull(product);
		
		Assert.assertEquals("change", product.getProdName());
		Assert.assertEquals("Detail", product.getProdDetail());
		Assert.assertEquals("2020-11-26", product.getManuDate());
		Assert.assertEquals(Integer.parseInt("10000"), product.getPrice());
		Assert.assertEquals("fileName", product.getFileName());

		}
		
		//@Test
		public void TestGetProductListAll() throws Exception{
			Search search = new Search();
			search.setCurrentPage(1);
			search.setPageSize(3);
			
			Map<String, Object> map = productService.getProductList(search);
			
			List<Object> list = (List<Object>)map.get("list");
			Assert.assertEquals(3, list.size());
			
			Integer totalCount = (Integer)map.get("totalCount");
			System.out.println(totalCount);
			
			System.out.println("=======================================");
			
			search.setCurrentPage(1);
			search.setPageSize(3);
			search.setSearchCondition("0");
			search.setSearchKeyword("");
			map=productService.getProductList(search);
			
			list = (List<Object>)map.get("list");
			Assert.assertEquals(3, list.size());
			
			totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
						
		}
		//@Test
		 public void testGetUserListByProdNo() throws Exception{
			 
			 	Search search = new Search();
			 	search.setCurrentPage(1);
			 	search.setPageSize(3);
			 	search.setSearchCondition("0");
			 	search.setSearchKeyword("10082");
			 	Map<String,Object> map = productService.getProductList(search);
			 	
			 	List<Object> list = (List<Object>)map.get("list");
			 	Assert.assertEquals(1, list.size());
			 	
				//==> console 확인
			 	//System.out.println(list);
			 	
			 	Integer totalCount = (Integer)map.get("totalCount");
			 	System.out.println(totalCount);
			 	
			 	System.out.println("=======================================");
			 	
			 	search.setSearchCondition("0");
			 	search.setSearchKeyword(""+System.currentTimeMillis());
			 	map = productService.getProductList(search);
			 	
			 	list = (List<Object>)map.get("list");
			 	Assert.assertEquals(0, list.size());
			 	
				//==> console 확인
			 	//System.out.println(list);
			 	
			 	totalCount = (Integer)map.get("totalCount");
			 	System.out.println(totalCount);
			 }
		
	@Test
		public void testGetUserListByProductName() throws Exception{
			Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword("자전거");
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(1, list.size());
		 	
		 	System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword(""+System.currentTimeMillis());
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(0, list.size());
		 	
			//==> console 확인
		 	System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		}
}


