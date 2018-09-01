/**
 * 
 */
$(function(){
	var shopId = getQueryString('shopId');
	var isEdit = shopId?true:false;
	
	var initUrl ='/o2o/shopadmin/getshopinitinfo';
	var registerShopUrl = '/o2o/shopadmin/registershop';
	var shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId='+shopId;
	var editShopUrl = '/o2o/shopadmin/modifyshop';
	if(!isEdit){
		getShopInitInfo();
	}else{
		getShopInfo(shopId);
	}
	function getShopInfo(shopId){
		$.getJSON(shopInfoUrl,function(data){
			if(data.success){
				var shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				var shopCategory = '<option data-id="'
					+ shop.shopCategory.shopCategoryId +'"selected>'
					+ shop.shopCategory.shopCategoryName + '</option>';
				var tempAreaHtml = '';
				data.areaList.map(function(item,index){
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
						+ item.areaName + '</option>';
				});
				$('#shop-category').html(shopCategory);
				$('#shop-category').attr('disabled','disabled');
				$('#area').html(tempAreaHtml);
				$("#area option[data-id='"+shop.area.areaId + "']").attr("selected","selected");
			}
		});
	}
	
	function getShopInitInfo(){
		$.getJSON(initUrl,function(data){
			if(data.success){
				var tempHtml = '';
				var tempAreaHtml = '';
				data.shopCategoryList.map(function(item,index){
					tempHtml +='<option data-id="'+item.shopCategoryId + '">'
						+ item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item,index){
					tempAreaHtml +='<option data-id="'+ item.areaId+'">'
						+ item.areaName + '</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml)
			}
		});
	}
		$('#submit').click(function(){
			var shop = {};
			if(isEdit){
				shop.shopId = shopId;
			}
			shop.shopName = $('#shop-name').val();
			if(shop.shopName == ""){
				alert("请填写商铺名称");
				return false;
			}
			shop.shopAddr = $('#shop-addr').val();
			if(shop.shopAddr == ""){
				alert("请填写详细地址");
				return false;
			}
			shop.phone = $('#shop-phone').val();
			if(shop.phone == ""){
				alert("请填写联系号码");
				return false;
			}
			var isMobile=/^(13[0-9]{9})|(15[89][0-9]{8})$/;
			if(!isMobile.test(shop.phone)){
				alert('请输入正确地手机号')
				return false;
			}
			shop.shopDesc = $('#shop-desc').val();
			if(shop.shopDesc == ""){
				alert("请填写店铺简介");
				return false;
			}
			shop.shopCategory = {
					shopCategoryId:$('#shop-category').find('option').not(function(){
						return !this.selected;
					}).data('id')
			};
			shop.area = {
					areaId:$('#area').find('option').not(function(){
						return !this.selected
					}).data('id')
			};
			var shopImg = $('#shop-img')[0].files[0];
			var formData = new FormData();
			formData.append('shopImg',shopImg);
			formData.append('shopStr',JSON.stringify(shop));
			var verifyCodeActual = $('#j_captcha').val();
			if(!verifyCodeActual){
				$.toast('请输入验证码!');
				return ;
			}
			formData.append('verifyCodeActual',verifyCodeActual);
			$.ajax({
				url:(isEdit?editShopUrl:registerShopUrl),
				type:'Post',
				contentType:false,
				processData:false,
				cache:false,
				data:formData,
				success:function(data){
					if(data.success){
						$.toast('提交成功！');
					}else{
						$.toast('提交失败！'+data.errMsg);
					}
					$('#captcha_img').click();
				}
			})
		})	
	})