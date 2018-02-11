// 所有页面加载时，先执行获取用户信息的操作，填写头部的用户信息
$(function () {

    $("#previewButton").click(function () {
        var content = $("#content").val();
        var html_content = markdown.toHTML(content);
        $("#preview").html(html_content);
    })
});