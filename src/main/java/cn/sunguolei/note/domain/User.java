package cn.sunguolei.note.domain;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String username;
    private String password;
    private LocalDateTime createTime;
    private String email;
    private int activateStatus;
    private String activateCode;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getActivateStatus() {
        return activateStatus;
    }

    public void setActivateStatus(int activateStatus) {
        this.activateStatus = activateStatus;
    }

    public String getActivateCode() {
        return activateCode;
    }

    public void setActivateCode(String activateCode) {
        this.activateCode = activateCode;
    }
}
