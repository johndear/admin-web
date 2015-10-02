package com.it.j2ee.core.pdf;

import jp.ne.so_net.ga2.no_ji.jcom.IDispatch;
import jp.ne.so_net.ga2.no_ji.jcom.ReleaseManager;
import jp.ne.so_net.ga2.no_ji.jcom.excel8.ExcelApplication;
 
/**
 * 目前只支持32位操作系统
 * @author Administrator
 *
 */
public class JcomConvertor {
 
    /**
     * JCom调用MS Office转换word为PDF
     * 
     * @param inputFile
     *            doc文档的绝对路径
     * @param pdfFile
     *            输出pdf文档的绝对路径，例如D:\\folder\\test.pdf
     */
    public void word2PDF(String inputFile, String pdfFile) {
        ReleaseManager rm = null;
        IDispatch app = null;
        try {
            rm = new ReleaseManager();
            app = new IDispatch(rm, "Word.Application");// 启动word
            app.put("Visible", false); // 设置word不可见
            IDispatch docs = (IDispatch) app.get("Documents"); // 获得word中所有打开的文档
            IDispatch doc = (IDispatch) docs.method("Open", new Object[] {
                    inputFile, false, true });// 打开文档
            doc.method("SaveAs", new Object[] { pdfFile, 17 });// 转换文档为pdf格式
            doc.method("Close", new Object[] { false });
            app.method("Quit", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                app = null;
                rm.release();
                rm = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * JCom调用MS Office转换excel为HTML
     * 
     * @param inputFile
     *            源文件绝对路径
     * @param htmlFile
     *            目标文件绝对路径
     */
    public void excel2HTML(String inputFile, String htmlFile) {
        ReleaseManager rm = null;
        IDispatch app = null;
        try {
            rm = new ReleaseManager();
            ExcelApplication ex = new ExcelApplication(rm);
            ex.put("Visible", false);
            IDispatch excs = (IDispatch) ex.get("Workbooks");
            IDispatch doc = (IDispatch) excs.method("Open", new Object[] {
                    inputFile, false, true });// 打开文档
            doc.method("SaveAs", new Object[] { htmlFile, 44 });
            doc.method("Close", new Object[] { false });
            ex.method("Quit", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                app = null;
                rm.release();
                rm = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * JCom调用MS Office转换Excel为PDF
     * 
     * @param inputFile
     *            源文件绝对路径
     * @param htmlFile
     *            目标文件绝对路径
     */
    public void excel2PDF(String inputFile, String pdfFile) {
        ReleaseManager rm = null;
        IDispatch app = null;
        try {
            rm = new ReleaseManager();
            ExcelApplication ex = new ExcelApplication(rm);
            ex.put("Visible", false);
            IDispatch excs = (IDispatch) ex.get("Workbooks");
            IDispatch doc = (IDispatch) excs.method("Open", new Object[] {
                    inputFile, false, true });// 打开文档
            doc.method("ExportAsFixedFormat", new Object[] { 0, pdfFile });
            doc.method("Close", new Object[] { false });
            ex.method("Quit", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                app = null;
                rm.release();
                rm = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * JCom调用MS Office转换Powerpoint为PDF
     * 
     * @param inputFile
     *            源文件绝对路径
     * @param pdfFile
     *            目标文件绝对路径
     */
    public void powerpoint2PDF(String inputFile, String pdfFile) {
        ReleaseManager rm = null;
        IDispatch app = null;
        try {
            rm = new ReleaseManager();
            app = new IDispatch(rm, "PowerPoint.Application");// 启动word
            // app.put("Visible", false); // 设置word不可见
            IDispatch docs = (IDispatch) app.get("Presentations"); // 获得word中所有打开的文档
            IDispatch doc = (IDispatch) docs.method("Open", new Object[] {
                    inputFile, false, true });// 打开文档
            doc.method("SaveAs", new Object[] { pdfFile, 32 });// 转换文档为pdf格式
            // doc.method("Close", new Object[] { false });
            app.method("Quit", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                app = null;
                rm.release();
                rm = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * JCom调用MS Office转换Powerpoint为JPG
     * 
     * @param inputFile
     * @param pdfFile
     */
    public void powerpoint2JPG(String inputFile, String jpgFile) {
        ReleaseManager rm = null;
        IDispatch app = null;
        try {
            rm = new ReleaseManager();
            app = new IDispatch(rm, "PowerPoint.Application");// 启动word
            // app.put("Visible", false); // 设置不可见
            IDispatch docs = (IDispatch) app.get("Presentations"); // 获得word中所有打开的文档
            IDispatch doc = (IDispatch) docs.method("Open", new Object[] {
                    inputFile, false, true });// 打开文档
            doc.method("SaveAs", new Object[] { jpgFile, 17 });// 转换文档为pdf格式
            // doc.method("Close", new Object[] { false });
            app.method("Quit", null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                app = null;
                rm.release();
                rm = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
    	JcomConvertor jc= new JcomConvertor();
    	jc.word2PDF("f:/测试文档.docx","f:/aa.pdf");
	}
}
