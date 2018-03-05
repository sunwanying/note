/**
 * Created by lvyz on 2018/3/2.
 */
$(function () {
    $("#ModifyPassword").click(function () {
        var pwd = $("#NewPassword").val();
        var ConfirmPwd = $("#ConfirmNewPassword").val();
        if (pwd.trim() === "") {
            alert("请输入新密码");
            return false;
        } else if (ConfirmPwd.trim() === "") {
            alert("请输入确认密码");
            return false;
        } else if (ConfirmPwd != pwd) {
            alert("两次输入的密码不一致");
            return false;
        }
        return true;
    })
});