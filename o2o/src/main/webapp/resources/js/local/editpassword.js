$(function(){
	var editPasswordUrl='/o2o/local/changelocalpwd';
	
	
	$('#submit').click(function(){
		var userName=$("#userName").val();
		if(userName==""){
			$.toast("用户名不能为空！");
			return;
		}
		var password=$("#password").val();
		if(password==""){
			$.toast("密码不能为空！");
			return;
		}
		var newPassword=$("#newPassword").val();
		if(password==""){
			$.toast("新密码密码不能为空！");
			return;
		}
		var verifyCodeActual = $('#j_captcha').val();
		if(verifyCodeActual==""){
			$.toast("验证码不能为空！");
			return;
		}
		var formData = new FormData();
		formData.append("userName",userName);
		formData.append("password",password);
		formData.append("newPassword",newPassword);
		formData.append("verifyCodeActual",verifyCodeActual);
		
		//提交表单
		$.ajax({
			url:editPasswordUrl,
			type:'POST',
			data:formData,
			contentType:false,
			processData:false,
			cache:false,
			success:function(data){
				if(data.success){
					$.toast('提交成功！');
					$('#captcha_img').click();
				}else{
					$.toast('提交失败！'+ data.errMsg);
					$('#captcha_img').click();
				}
			}
		});
	})
})