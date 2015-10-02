package com.it.j2ee.modules.web.springmvc.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.it.j2ee.core.excel.ExcelExporter;


@Controller
@RequestMapping(value = "/excel")
public class ExcelController {
	
	@RequestMapping(method = RequestMethod.GET)
	public void list(HttpServletRequest request, HttpServletResponse response) {
		Map pDto =new HashMap();
		pDto.put("reportTitle", "1");
		pDto.put("jbr", "2");
		pDto.put("jbsj", "3");
		pDto.put("countXmid", 1);
		pDto.put("共", "共");
		
		List list =new ArrayList();
		Map childMap =new HashMap();
		childMap.put("xmid", "1");
		childMap.put("zfbl", 0.5);
		Map childMap2 =new HashMap();
		childMap2.put("xmid", "2");
		childMap2.put("zfbl", 0.5);
		Map childMap3 =new HashMap();
		childMap3.put("xmid", "3");
		childMap3.put("zfbl", 0.5);
		list.add(childMap);
		list.add(childMap2);
		list.add(childMap3);
		
		ExcelExporter excelExporter = new ExcelExporter();
		excelExporter.setTemplatePath("./hisCatalogReport.xls");
		excelExporter.setData(pDto, list);
		excelExporter.setFilename("北京市第一人民医院收费项目表.xls");
		try {
			excelExporter.export(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
