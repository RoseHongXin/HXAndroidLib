package hx.req.bean;

/**
 * Created by rose on 16-7-28.
 */

public class RespBase<T> {

    private int code;
    private String msg;
    private T data;

    public boolean isSuccess(){
        if(code == 1) return true;
        return false;
    }

    public boolean isExpire(){
        if(code == -101) return true;
        return false;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //sth like request is success, and data's null but has msg info.
    public void wrapMsgIfDataNull(){
        data = (T)msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
