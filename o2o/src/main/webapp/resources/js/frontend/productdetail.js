/**
 * 
 */
$(function(){
	//获取商品详情的 url
	var productDetailUrl = '/o2o/frontend/listproductdetailpageinfo';
	var productId = getQueryString('productId');
	getProductDetail();
	function getProductDetail(){
		var url = productDetailUrl+"?productId=" +productId;
		$.getJSON(url,function(data){
			if(data.success){
				var html='';
				var product = data.product;
				$('#product-img').attr('src', product.imgAddr);
				$('#product-time').html(
						new Date(product.lastEditTime)
								.Format("yyyy-MM-dd"));
				$('#product-name').html(product.productName);
				$('#product-desc').html(product.productDesc);
				$('#normal-price').html(product.normalPrice);
				$('#promotion-price').html(product.promotionPrice);
				
				product.productImgList.map(function(item,index){
					html +='<div valign="bottom" class="card-header color-white no-border no-padding"><img class="card-cover" src="'
						+item.imgAddr
						+'"alt="" /></div>';
				});
				$('#imgList').html(html);
			}else{
				$.toast("获取商品详情失败");
			}
		})
	}
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
})