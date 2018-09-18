package com.imooc.o2o.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping(value="frontend")
public class ShopDetailController {
	
	@Autowired
	private ShopService shopService;
	@Autowired 
	private  ProductCategoryService productCategoryService;
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="listshopdetailpageinfo",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> listShopDetailPageInfo(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		Shop shop = null;
		List<ProductCategory> productCategoryList = null;
		if(shopId > -1L) {
			try {
				shop = shopService.getByShopId(shopId);
				productCategoryList = productCategoryService.getProductCategoryList(shopId);
				modelMap.put("success", true);
				modelMap.put("shop", shop);
				modelMap.put("productCategoryList", productCategoryList);
			}catch(Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少输入一个商店");
		}
		return modelMap;
	}
	
	@RequestMapping(value="listproducts",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> listProducts(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		//获取一页需要显示的数据条数
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		//非空判断
		if((pageIndex>-1)&&(pageSize>-1)) {
			try {
				long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
				long shopId = HttpServletRequestUtil.getLong(request, "shopId");
				String productName = HttpServletRequestUtil.getString(request, "productName");
				Product productCondition = compactProductCondition4Search(shopId,productCategoryId,productName);
				ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
				modelMap.put("success", true);
				modelMap.put("productList", pe.getProductList());
				modelMap.put("count", pe.getCount());
			}catch(Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex");
		}	
		return modelMap;
	}
	
	private Product compactProductCondition4Search(long shopId,long productCategoryId,String productName) {
		Product productCondition = new Product();
		//判断是否有Id
		if(shopId > -1L) {
			Shop shop = new Shop();
			shop.setShopId(shopId);
			//根据shopId查询
			productCondition.setShop(shop);
		}
		if(productCategoryId > -1L) {
			//查询 productId 的商品
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategotyId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		if(productName != null) {
			//查询名称包含productName的商品
			productCondition.setProductName(productName);
		}
		productCondition.setEnableStatus(1);
		return productCondition;
	}
}
