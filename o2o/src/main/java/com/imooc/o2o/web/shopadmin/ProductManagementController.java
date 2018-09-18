package com.imooc.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	
	//支持上传商品详情图的最大数量
	private static final int IMAGEMAXCOUNT = 6;
	
	@RequestMapping(value="/getproductbyid",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getProductById(@RequestParam Long productId){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Product product = new Product();
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		if(productId >-1) {
			try {
				product = productService.getProductById(productId);
				productCategoryList = productCategoryService.getProductCategoryList(product.getShop().getShopId());
				modelMap.put("success", true);
				modelMap.put("product", product);
				modelMap.put("productCategoryList", productCategoryList);
			}catch(Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "商品为空");
		}
		return modelMap;		
	}
	
	@RequestMapping(value="/addproduct",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> addProduct(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//验证码校验
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		try {
			//若请求中存在文件流，则取出相关的文件(包括缩略图和详情图)
			if(multipartResolver.isMultipart(request)) {
				thumbnail = handleImage(request, thumbnail, productImgList);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
				return modelMap;
			}
		}catch(ProductOperationException | IOException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			//尝试获取前端传过来的表单string刘并将其转换成product实体类
			product = mapper.readValue(productStr, Product.class);
		}catch(Exception e){
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		//若Product信息，缩略图以及详情图列表为空，则开始进行商品添加操作
		if(product!=null&&thumbnail!=null&&productImgList.size() > 0) {
			try {
				//从session中获取当前店铺的Id并赋值给product，减少对前端数据的依赖
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				//执行添加操作
				ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
				if(pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品完整信息");
		}
		return modelMap;
		
	}

	private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws IOException {
		MultipartHttpServletRequest multipartRequest;
		multipartRequest = (MultipartHttpServletRequest)request;
		//取出缩略图并构建ImageHolder对象
		CommonsMultipartFile thumbnailFile = (CommonsMultipartFile)multipartRequest.getFile("thumbnail");
		if(thumbnailFile!=null) {
			thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
		}
		//取出详情图列表并构建List<ImageHolder>列表对象,最多支持六张图片上传
		for(int i = 0;i < IMAGEMAXCOUNT;i++) {
			CommonsMultipartFile productImgFile = (CommonsMultipartFile)multipartRequest.getFile("productImg"+ i);
			if(productImgFile!=null) {
				//若取出的第i个详情图片文件流不为空，则将其加入详情图片列表
				ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),productImgFile.getInputStream());
				productImgList.add(productImg);
			}else {
				//若取出的第i个详情图文件流为空，则终止循环
				break;
			}
		}
		return thumbnail;
	}
	
	
	@RequestMapping(value="/modifyproduct",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> modifyProduct(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//是商品编辑时候调用还是上下架操作的时候调用
		//若为前者进行验证码判断，后者则跳过验证码判断
		boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
		//验证码校验
		if(!statusChange&&!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		try {
			//若请求中存在文件流，则取出相关的文件(包括缩略图和详情图)
			if(multipartResolver.isMultipart(request)) {
				thumbnail = handleImage(request, thumbnail, productImgList);
			}
//			else {
//				modelMap.put("success", false);
//				modelMap.put("errMsg", "上传图片不能为空");
//				return modelMap;
//			}
		}catch(ProductOperationException | IOException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			//尝试获取前端传过来的表单string刘并将其转换成product实体类
			product = mapper.readValue(productStr, Product.class);
		}catch(Exception e){
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		//若Product信息，缩略图以及详情图列表为空，则开始进行商品添加操作
		if(product!=null) {
			try {
				//从session中获取当前店铺的Id并赋值给product，减少对前端数据的依赖
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				product.setShop(currentShop);
				//执行添加操作
				ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
				if(pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;
	}
	
	@RequestMapping(value="/getproductlistbyshop",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getProductListByShop(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize =HttpServletRequestUtil.getInt(request, "pageSize");
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		if((pageIndex>-1)&&(pageSize>-1)&&(currentShop!=null)&&(currentShop.getShopId()!=null)) {
			long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
			String productName = HttpServletRequestUtil.getString(request, "productName");
			Product productCondition = compactProductCondition(currentShop.getShopId(),productCategoryId,productName);
			ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "Empty");
		}
		return modelMap;
	}
	
	private Product compactProductCondition(long shopId,long productCategoryId,String productName) {
		Product  productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		if(productCategoryId!=-1L) {
			ProductCategory productCategory =new ProductCategory();
			productCategory.setProductCategotyId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		if(productName!=null) {
			productCondition.setProductName(productName);
		}
		return productCondition;
	}
}
