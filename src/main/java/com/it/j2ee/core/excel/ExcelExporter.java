package com.it.j2ee.core.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jxl.Cell;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.it.j2ee.core.G4Constants;
import com.it.j2ee.core.util.G4Utils;

/**
 * Excel导出器(适用于WebAPP)
 * 
 * @author XiongChun
 * @since 2010-08-12
 */
public class ExcelExporter {
	
	private Log log = LogFactory.getLog(ExcelExporter.class);


	private String templatePath;
	private Map parametersMap;
	private List fieldsList;
	private String filename = "Excel.xls";
	
	
	private ExcelParser excelParser = null;

	/**
	 * 设置数据
	 * 
	 * @param pMap
	 *            参数集合
	 * @param pList
	 *            字段集合
	 */
	public void setData(Map pMap, List pList) {
		parametersMap = pMap;
		fieldsList = pList;
	}

	/**
	 * 导出Excel
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		filename = G4Utils.encodeChineseDownloadFileName(request, getFilename());
		response.setHeader("Content-Disposition", "attachment; filename=" + filename + ";");
		
		this.excelParser = new ExcelParser();
		excelParser.setTemplatePath(this.templatePath);
		excelParser.parse(request);
		
		ByteArrayOutputStream bos = fill(request);
		ServletOutputStream os = response.getOutputStream();
		os.write(bos.toByteArray());
		os.flush();
		os.close();
	}
	
	/**
	 * 数据填充 将ExcelData填入excel模板
	 * 
	 * @return ByteArrayOutputStream
	 */
	private ByteArrayOutputStream fill(HttpServletRequest request) {
		WritableSheet wSheet = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
	        InputStream is = this.getClass().getResourceAsStream(excelParser.getTemplatePath()); 
			Workbook wb = Workbook.getWorkbook(is);
			WritableWorkbook wwb = Workbook.createWorkbook(new File("e:/c.xls"), wb);
			wSheet = wwb.getSheet(0);
			
			fillStatics(wSheet);
			fillParameters(wSheet);
			fillFields(wSheet);
//			fillVariables(wSheet,4);
			
			if (G4Utils.isNotEmpty(fieldsList)) {
//				 fillFields(wSheet);
			}
			wwb.write();
			wwb.close();
			wb.close();
		} catch (Exception e) {
			log.error(G4Constants.Exception_Head + "基于模板生成可写工作表出错了!");
			e.printStackTrace();
		}
		return bos;
	}

	/**
	 * 写入静态对象
	 */
	private void fillStatics(WritableSheet wSheet) {
		List statics = excelParser.getStaticObject();
		for (int i = 0; i < statics.size(); i++) {
			Cell cell = (Cell) statics.get(i);
			Label label = new Label(cell.getColumn(), cell.getRow(), cell.getContents());
			label.setCellFormat(cell.getCellFormat());
			try {
				wSheet.addCell(label);
			} catch (Exception e) {
				log.error(G4Constants.Exception_Head + "写入静态对象发生错误!");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 写入参数对象
	 */
	private void fillParameters(WritableSheet wSheet) {
		List parameters = excelParser.getParameterObjct();
		Map parameterMap = parametersMap;
		for (int i = 0; i < parameters.size(); i++) {
			Cell cell = (Cell) parameters.get(i);
			String key = getKey(cell.getContents().trim());
			String type = getType(cell.getContents().trim());
			try {
				if (type.equalsIgnoreCase(G4Constants.ExcelTPL_DataType_Number)) {
					Number number = new Number(cell.getColumn(), cell.getRow(), new BigDecimal(String.valueOf(parameterMap.get(key)))
							.doubleValue());
					number.setCellFormat(cell.getCellFormat());
					wSheet.addCell(number);
				} else {
					Label label = new Label(cell.getColumn(), cell.getRow(), String.valueOf(parameterMap.get(key)));
					label.setCellFormat(cell.getCellFormat());
					wSheet.addCell(label);
				}
			} catch (Exception e) {
				log.error(G4Constants.Exception_Head + "写入表格参数对象发生错误!");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 写入表格字段对象
	 * 
	 * @throws Exception
	 */
	private void fillFields(WritableSheet wSheet) throws Exception {
		List fields = excelParser.getFieldObjct();
		List fieldList = fieldsList;
		for (int j = 0; j < fieldList.size(); j++) {
			Map dataMap = new HashMap();
			Object object = fieldList.get(j);
			if (object instanceof Map) {
				dataMap.putAll((Map)object);
//			} else if (object instanceof BaseVo) {
//				BaseVo vo = (BaseVo) object;
//				dataMap.putAll(vo.toMap());
//			} else if (object instanceof BaseMap) {
//				Map dto = (BaseMap) object;
//				dataMap.putAll(dto);
			} else {
				log.error(G4Constants.Exception_Head + "不支持的数据类型!");
			}
			for (int i = 0; i < fields.size(); i++) {
				Cell cell = (Cell) fields.get(i);
				String key = getKey(cell.getContents().trim());
				String type = getType(cell.getContents().trim());
				try {
					if (type.equalsIgnoreCase(G4Constants.ExcelTPL_DataType_Number)) {
						Number number = new Number(cell.getColumn(), cell.getRow() + j, new BigDecimal(String.valueOf(dataMap.get(key)))
								.doubleValue());
						number.setCellFormat(cell.getCellFormat());
						wSheet.addCell(number);
					} else {
						Label label = new Label(cell.getColumn(), cell.getRow() + j, String.valueOf(dataMap.get(key)));
						label.setCellFormat(cell.getCellFormat());
						wSheet.addCell(label);
					}
				} catch (Exception e) {
					log.error(G4Constants.Exception_Head + "写入表格字段对象发生错误!");
					e.printStackTrace();
				}
			}
		}
		int row = 0;
		row += fieldList.size();
		if (G4Utils.isEmpty(fieldList)) {
			if (G4Utils.isNotEmpty(fields)) {
				Cell cell = (Cell) fields.get(0);
				row = cell.getRow();
				wSheet.removeRow(row+5);
				wSheet.removeRow(row+4);
				wSheet.removeRow(row+3);
				wSheet.removeRow(row+2);
				wSheet.removeRow(row+1);
				wSheet.removeRow(row);
			}
		}else {
			Cell cell = (Cell) fields.get(0);
			row += cell.getRow();
			fillVariables(wSheet, row);
		}
	}

	/**
	 * 写入变量对象
	 */
	private void fillVariables(WritableSheet wSheet, int row) {
		List variables = excelParser.getVariableObject();
		Map parameterMap = parametersMap;
		for (int i = 0; i < variables.size(); i++) {
			Cell cell = (Cell) variables.get(i);
			String key = getKey(cell.getContents().trim());
			String type = getType(cell.getContents().trim());
			try {
				if (type.equalsIgnoreCase(G4Constants.ExcelTPL_DataType_Number)) {
					Number number = new Number(cell.getColumn(), row,new BigDecimal(String.valueOf(parameterMap.get(key))).doubleValue());
					number.setCellFormat(cell.getCellFormat());
					wSheet.addCell(number);
				} else {
					String content = String.valueOf(parameterMap.get(key));
					if (G4Utils.isEmpty(content) && !key.equalsIgnoreCase("nbsp")) {
						content = key;
					}
					Label label = new Label(cell.getColumn(), row, content);
					label.setCellFormat(cell.getCellFormat());
					wSheet.addCell(label);
				}
			} catch (Exception e) {
				log.error(G4Constants.Exception_Head + "写入表格变量对象发生错误!");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取模板键名
	 * 
	 * @param pKey
	 *            模板元标记
	 * @return 键名
	 */
	private static String getKey(String pKey) {
		String key = null;
		int index = pKey.indexOf(":");
		if (index == -1) {
			key = pKey.substring(3, pKey.length() - 1);
		} else {
			key = pKey.substring(3, index);
		}
		return key;
	}

	/**
	 * 获取模板单元格标记数据类型
	 * 
	 * @param pType
	 *            模板元标记
	 * @return 数据类型
	 */
	private static String getType(String pType) {
		String type = G4Constants.ExcelTPL_DataType_Label;
		if (pType.indexOf(":n") != -1 || pType.indexOf(":N") != -1) {
			type = G4Constants.ExcelTPL_DataType_Number;
		}
		return type;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public Map getParametersMap() {
		return parametersMap;
	}

	public void setParametersMap(Map parametersMap) {
		this.parametersMap = parametersMap;
	}

	public List getFieldsList() {
		return fieldsList;
	}

	public void setFieldsList(List fieldsList) {
		this.fieldsList = fieldsList;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
