$(function(){

    $("#adminRegisterBtn").on('click', function(){
        var userId = $("#adminId").val();
        var userPw = $("#adminPw").val();
        var userName = $("#adminName").val();

        var userObj = {
            userId : userId,
            userPw : userPw,
            userName : userName
        };

        console.log(userObj);
        console.log(JSON.stringify(userObj));

        $.ajax({
            type : "POST",
            url : "/users/setup",
            data : JSON.stringify(userObj),
            contentType : "application/json; charset=UTF-8",
            success : function(){
                alert("SUCCESS");
            },
			error : function(xhr, textStatus, error) {
				alert("code:" + xhr.status + "\n"
					+ "message:" + xhr.responseText + "\n"
					+ "error:" + error);
			}
        });
    });

    $("#adminResetBtn").on('click', function(){
        $("#adminId").val("");
        $("#adminPw").val("");
        $("#adminName").val("");
    });

})