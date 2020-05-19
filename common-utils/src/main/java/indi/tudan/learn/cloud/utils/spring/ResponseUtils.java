package indi.tudan.learn.cloud.utils.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.pagehelper.PageInfo;

/**
 * http响应工具类
 * @author changfx
 *
 */
public class ResponseUtils {
	private ResponseUtils() {}
	
	@SuppressWarnings("rawtypes")
	public static ResponseEntity build(Object data) {
		return build(true, data, null, null, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	public static ResponseEntity pageBuild(PageInfo pageInfo) {
		Map<String,Object> params = new HashMap<>();
		params.put("success", true);
		params.put("data", pageInfo.getList());
		params.put("total", pageInfo.getTotal());
		params.put("pageNum", pageInfo.getPageNum());
		params.put("pageSize", pageInfo.getPageSize());
		return new ResponseEntity<>(params, HttpStatus.OK);
	}	
	
	/**
	 * @param isSuccess, 响应是否成功
	 * @param data, 响应数据
	 * @param message, 说明信息 
	 * @param code, 响应编码
	 * @param httpStatus 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static ResponseEntity build(boolean isSuccess, Object data, String message, String code, HttpStatus httpStatus) {
		Map<String,Object> params = new HashMap<>();
		params.put("success", isSuccess);
		params.put("data", data);
		if (message != null) {
			params.put("message", message);
		}
		if (code != null) {
			params.put("code", code);
		}
		return new ResponseEntity<>(params, httpStatus);
	}
	
}
