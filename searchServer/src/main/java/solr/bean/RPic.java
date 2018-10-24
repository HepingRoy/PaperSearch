package solr.bean;

import java.util.List;
import java.util.Map;

public class RPic {
	public static final int SUCCESS=1;
    public static final int FAILURE=0;
    String msg;
    int code;
    List<Map<String, String>> pic;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public List<Map<String, String>> getPic() {
		return pic;
	}
	public void setPic(List<Map<String, String>> pic) {
		this.pic = pic;
	}
}
