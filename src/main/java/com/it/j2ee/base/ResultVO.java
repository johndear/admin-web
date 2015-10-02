package com.it.j2ee.base;

/**
 * 
 *〈一句话功能简述〉
 * 更新结果(Create、Update、Delete)VO
 * @author liusu
 * @create 2014年10月29日 下午4:12:19 
 * @version 1.0.0
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ResultVO {

	public final static String SUCCESS = "success";
	
	public final static String FAIL = "fail";
	
	/**
	 * 错误编码
	 */
	private String errorCode;
	
	/**
	 * 错误描述
	 */
	private String errorMsg;

	/**
	 * 返回结果
	 */
	private Object result;
	
	
	public ResultVO(){}
	
	/**  
	 * 构造函数
	 * @param errorCode
	 * @param errorMsg  
	*/ 
	
	public ResultVO(String errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	/**  
	 * 构造函数
	 * @param errorCode
	 * @param errorMsg
	 * @param result  
	*/ 
	
	public ResultVO(String errorCode, String errorMsg, Object result) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.result = result;
	}

	/**  
	 * 返回 result 的值   
	 * @return result  
	 */
	
	public Object getResult() {
		return result;
	}

	/**  
	 * 设置 result 的值  
	 * @param result
	 */
	
	public void setResult(Object result) {
		this.result = result;
	}

	/**  
	 * 返回 errorCode 的值   
	 * @return errorCode  
	 */
	
	public String getErrorCode() {
		return errorCode;
	}

	/**  
	 * 设置 errorCode 的值  
	 * @param errorCode
	 */
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**  
	 * 返回 errorMsg 的值   
	 * @return errorMsg  
	 */
	
	public String getErrorMsg() {
		return errorMsg;
	}

	/**  
	 * 设置 errorMsg 的值  
	 * @param errorMsg
	 */
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
