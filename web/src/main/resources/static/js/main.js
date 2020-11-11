var $signUp = $('#signUp')
var windowWidth = $(window).width(),
	windowHeight = $(window).height();
if (windowWidth > 640) {
	$('body,#signUp').height(windowHeight).width(windowHeight * 320 / 504);
} else {
	$('body,#signUp').height(windowHeight);
}
var reBgImg = 640/1030,_reWindow = windowWidth/windowHeight;
if(reBgImg>=_reWindow){
	$signUp.addClass('s1');
}else{
	$signUp.addClass('s2');
}

function loading(imgArr, callback) {
	if (imgArr.constructor === Array && imgArr.length > 0) {
		var imgAmount = imgArr.length,
			loadAmount = 0,
			percent;
		for (var i = 0; i < imgAmount; i++) {
			var img = new Image();
			img.src = imgArr[i];
			if (img.complete) {
				_update();
				continue;
			}
			img.onload = function() {
				_update();
			}
		}
	} else {
		alert("载入图片资源出错喽");
	}
	function _update() {
		loadAmount++;
		percent = Math.round(loadAmount * 100 / imgAmount);
		$("#load-progress").text(percent);
		if (percent == 100 && callback) {
			$(".loading").remove();
			callback();
		}
	}
}

var t;
function dialogueTips(txt){
	var _html = '<div class="dialogue-tips" id="J_error">'+txt+'</div>';
	if($('.dialogue-tips').size()>0){
		$('.dialogue-tips').remove();
		clearTimeout(t);
	}
	$('body').append(_html);
	t = setTimeout(function(){
		$('#J_error').remove();
		clearTimeout(t);
	},1500);
}

$("input,select").blur(function(){
    var _tinput = setTimeout(function(){
    	window.scrollTo({'top':0});
    	clearTimeout(_tinput);
    });
});

var _run = true,_rePhone =/^1[3|4|5|6|7|8|9][0-9]{9}$/;
$signUp.on('click','.submit-btn',function(){
    $('#signUp input').blur();
    if (form.name.value == ''){
        dialogueTips('请输入您的姓名');
    }else if(form.tel.value == ''){
        dialogueTips('请输入您的联系方式');
    }else if(!_rePhone.test(form.tel.value)){
        dialogueTips('手机号码格式错误');
    }else if(form.room.value == ''){
        dialogueTips('请填写房号');
    }else if(form.time.value == ''){
        dialogueTips('请选择报名场次');
    }else{
        if(_run){
            _run = false;
            $.ajax({
                type: 'POST',
                url: 'http://bei.keaikai.com/allproject/user/insertUser',
                data: JSON.stringify({
                    userName: form.name.value,
                    mobile: form.tel.value,
                    roomNum: form.room.value,
                    time: form.time.value
                }),
                dataType: 'json',
                contentType:"application/json;charset=UTF-8",
                success: function (json) {
		            if(json.status){
		            	dialogueTips('恭喜报名成功');
		            }else{
		            	dialogueTips(json.message);
		            }
		            _run = true;
                },
                error: function (data) {
                    _run = true;
                    dialogueTips('服务器出小差，请给点耐心');
                }
            });
        }else{
            dialogueTips('正在提交……');
        }
    }
}).on('change','input[type="radio"]',function(){
    $(this).parents('.form-radio-item').addClass('current').siblings('.form-radio-item').removeClass('current');
});

var loadRES = ['img/p1.jpg','img/p1-1.png','img/p1-2.png','img/p1-3.png','img/p1-4.png','img/p1-5.png','img/p1-6.png','img/p1-7.png','img/p1-8.png','img/p1-9.png','img/p1-10.png','img/p1-11.png'];
loading(loadRES,function(){
	$signUp.removeClass('hide').addClass('animate');
});