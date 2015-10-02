package com.it.j2ee.modules.common.web;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelController {
	
	/**
	 * 获取指定对象的Excel模板文件
	 * @param pojoClass 有Excel注解的class类型
	 * @param dynamicColTitle 动态列数组
	 * @return excel文件
	 */
	public HSSFWorkbook getTemplateExcel(Class<?> pojoClass){
		return null;
	}
	
	/**
	 * 读取XLS 后缀的Excel File的内容，返回指定类型的collection集合
	 * 
	 * @param fileStream
	 *            文件（一般是excel文件）
	 * @param ignoreRows
	 *            忽略的行
	 * @param pojoClass
	 *            映射的pojo
	 * @return
	 * @throws ExcelImportException
	 *             数据格式不符合指定对象的类型时，转换抛出异常
	 */
	public Collection<?> readXLSExcel(InputStream fileStream, int ignoreRows,
			Class<?> pojoClass) throws Exception{
		return null;
	}
	
	/**
	 * 根据指定 的类型集合导出excel
	 * @param dataSet 类型集合
	 * @param pojoClass 映射的class
	 * @return HSSFWorkbook
	 */
	public HSSFWorkbook getXLSExcel(Collection<?> dataSet,Class<?> pojoClass){
		return null;
	}
	

}
