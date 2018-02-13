package cn.sunguolei.note.domain;

/**
 * Created by guohuawei on 2018/2/13.
 */
public class BaseModel {

    /**
     * 返回状态码
     */
    private int ReturnCode;
    /**
     * 返回提示信息
     */
    private String Message;
    /**
     * 返回结果集
     */
    private Object Result;

    public int getReturnCode() {
        return ReturnCode;
    }

    public void setReturnCode(int returnCode) {
        ReturnCode = returnCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Object getResult() {
        return Result;
    }

    public void setResult(Object result) {
        Result = result;
    }

}
