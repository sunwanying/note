package cn.sunguolei.note.domain;

/**
 * 用户是否登录相关的信息
 * 所有的  restful 返回的 用户登录的 json 数据封装到一起
 */
public class TokenInfo {
    private String username;
    private Boolean isLogin;
    private int resultCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
