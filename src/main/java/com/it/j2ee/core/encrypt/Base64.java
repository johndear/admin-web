package com.it.j2ee.core.encrypt;

import java.io.UnsupportedEncodingException;

/**
 * Created by lihuam on 2015/5/21.
 */
public class Base64 {
    private static char[] base64EncodeChars = new char[] {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/' };

    private static byte[] base64DecodeChars = new byte[] {
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
            52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
            -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
            15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
            -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };

    public static String encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len)
            {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len)
            {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

    public static byte[] decode(String str) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        byte[] data = str.getBytes("US-ASCII");
        int len = data.length;
        int i = 0;
        int b1, b2, b3, b4;
        while (i < len) {
            /* b1 */
            do {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1) break;
            /* b2 */
            do {
                b2 = base64DecodeChars[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1) break;
            sb.append((char)((b1 << 2) | ((b2 & 0x30) >>> 4)));
            /* b3 */
            do {
                b3 = data[i++];
                if (b3 == 61) return sb.toString().getBytes("ISO-8859-1");
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1) break;
            sb.append((char)(((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));
            /* b4 */
            do {
                b4 = data[i++];
                if (b4 == 61) return sb.toString().getBytes("ISO-8859-1");
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1) break;
            sb.append((char)(((b3 & 0x03) << 6) | b4));
        }
        return sb.toString().getBytes("ISO-8859-1");
    }
    public static void main(String args[]) throws UnsupportedEncodingException {
        System.out.println(Base64.encode((new String("&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;&lt;data&gt;&lt;attrs&gt;&lt;attr name=\"{documentNo}\"&gt;佛[2015]SCM-PR第281号&lt;/attr&gt;&lt;/attrs&gt;&lt;contents/&gt;&lt;tables&gt;&lt;table index=\"1\"&gt;请购员拟稿||王学恩||2015-05-14 03:57:54||2015-05-14 03:57:54||请审核##室经理>审批||江涛||2015-05-14 03:57:54||2015-05-15 09:20:53||请审核##需求规范性审核||许炫||2015-05-15 09:20:53||2015-05-18 04:38:19||请处理##二次规范性审核||杨亚琦||2015-05-18 04:38:19||2015-05-19 10:48:21||请购单预算与立项费用明细表、请示金额不同，请修改或说明原因，并补充合同规范书，谢谢##请购员拟稿||王学恩||2015-05-19 10:48:21||2015-05-19 12:50:08||请审核##室经理审批||江涛||2015-05-19 12:50:08||2015-05-19 02:39:04||请审核##需求规范性审核||许炫||2015-05-19 02:39:04||2015-05-19 03:18:40||请再核。##二次规范性审核||杨亚琦||2015-05-19 03:18:40||2015-05-19 03:24:30||需求明确，可执行采购。##部门领导审批||陈建聪||2015-05-19 03:24:30||2015-05-19 04:44:36||同意##采购执行分配员||许炫||2015-05-19 04:44:36||2015-05-21 10:18:17||&lt;/table&gt;&lt;/tables&gt;&lt;processing_file&gt;&lt;tplfile&gt;SCM_TPL_ALL_SWBL.docx&lt;/tplfile&gt;&lt;/processing_file&gt;&lt;/data&gt;")).getBytes()));
    }
}
