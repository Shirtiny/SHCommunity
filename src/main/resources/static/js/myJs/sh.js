
//invitaionDetail.html里发送评论的方法
function sendComment() {
    var invitationId=$("#sendComment_InvitaionId").val();
    var commentContent=$("#sendComment_Content").val();
    $.ajax({
        url:"/sendComment",
        type:"post",
        cache:false,
        data:{
            "invitationId":invitationId,
            "commentContent":commentContent,
            "isAjax":true
        },
        success:function (result) {
            //200为成功发送评论
            if (result.code==200){
                //清空输入框的值
                $("#sendComment_Content").val("");
                //刷新页面
                window.location.reload();
                return;
            }
            //4001是我自定义的状态码，表示未登录
            if (result.code==4001){
                //弹出确认框，询问是否登录
                var isConfirmed=confirm(result.message);
                if (isConfirmed){
                    //document.location.origin返回当前domain地址
                    //window.location.pathname返回路径
                    //window.location.href完整url
                    //回调地址
                    var redirect=window.location.pathname;
                    //调转到登录页面，带上回调地址
                    window.location.href="/loginPage?shRedirect="+redirect;
                }
            }else {
                alert(result.code+'：'+result.message);
            }
        }
    })
}