package com.it.j2ee.core.pdf;

import java.io.File;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * word2pdf
 * @author sswh
 * 
 */
public class JacobConvertor {

	static final int wdDoNotSaveChanges = 0;// 不保存待定的更改。
	static final int wdFormatPDF = 17;// PDF 格式

	public static void main(String[] args) {
//		System.out.println(System.getProperty("java.library.path"));
		
		String filename = "f:/测试文档.docx";
		String toFilename = filename + ".pdf";
		System.out.println("启动Word...");
		long start = System.currentTimeMillis();
		ActiveXComponent app = null;
		try {
			app = new ActiveXComponent("Word.Application");
			app.setProperty("Visible", false);

			Dispatch docs = app.getProperty("Documents").toDispatch();
			System.out.println("打开文档..." + filename);
			Dispatch doc = Dispatch.call(docs,//
					"Open", //
					filename,// FileName
					false,// ConfirmConversions
					true // ReadOnly
					).toDispatch();

			System.out.println("转换文档到PDF..." + toFilename);
			File tofile = new File(toFilename);
			if (tofile.exists()) {
				tofile.delete();
			}
			Dispatch.call(doc,//
					"SaveAs", //
					toFilename, // FileName
					wdFormatPDF);

			Dispatch.call(doc, "Close", false);
			long end = System.currentTimeMillis();
			System.out.println("转换完成..用时：" + (end - start) + "ms.");
		} catch (Exception e) {
			System.out.println("========Error:文档转换失败：" + e.getMessage());
		} finally {
			if (app != null)
				app.invoke("Quit", wdDoNotSaveChanges);
		}
	}
}
