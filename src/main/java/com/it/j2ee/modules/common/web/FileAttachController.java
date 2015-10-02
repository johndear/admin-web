package com.it.j2ee.modules.common.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.it.j2ee.base.DatagridVO;
import com.it.j2ee.modules.common.entity.FileAttach;
import com.it.j2ee.modules.common.service.FileAttachService;

@Controller
public class FileAttachController {
	
	@Autowired
	private FileAttachService fileAttachService;
	
	@RequestMapping(value = "/attachment")
	public String uploadFileList() {
		return "attachment/attachmentList";
	}
	
	@RequestMapping(value = "/attachment/allList")
	@ResponseBody
	public DatagridVO uploadFileList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			HttpServletRequest request, HttpServletResponse response) {
//		List<FileAttach> list = fileAttachService.();
//		DatagridVO vo =new DatagridVO(Long.parseLong(String.valueOf(list.size())),list); 
//		return vo;
		return null;
	}
	
	@RequestMapping(value = "/attachment/list")
	@ResponseBody
	public DatagridVO uploadFileList(
			@RequestParam(value = "foreignId",required=false) String foreignId,
			@RequestParam(value = "bizCode",required=false) String bizCode) {
		List<FileAttach> list = fileAttachService.findByforeignIdAndbizCode(foreignId, bizCode);
		DatagridVO vo =new DatagridVO(Long.parseLong(String.valueOf(list.size())),list); 
		return vo;
	}
	
	@RequestMapping(value = "/uploadFile/upload")
	public void upload(HttpServletResponse response,@RequestParam MultipartFile attach,
			@RequestParam(value = "foreignId",required=false) String foreignId)throws Exception {
	}
	
	@RequestMapping(value = "/uploadFile/download")
	public String uploadFileDownload(
			@RequestParam(value = "id",required=false) Long id,
            HttpServletResponse response,
            Model model){
		return null;
	}
	
	
	@RequestMapping(value = "/uploadFile/delete")
	@ResponseBody
	public Map<String,String> uploadFileDelete(@RequestParam(value = "ids[]")Long[] ids){
		return null;
	}

	
	
}
