package com.bitnei.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class CompressUtil {
	
	/**
	 * 描述:压缩数据
	 * 创建人:luhaiyou   
	 * 创建时间:2015年8月4日 上午10:11:44  
	 * @param @param message
	 * @param @return
	 * @param @throws IOException
	 * @return byte[]
	 * @throws
	 */
	public static byte[] compress(String message) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DeflaterOutputStream dos = new DeflaterOutputStream(baos);
		dos.write(message.getBytes("UTF-8"));
		dos.close();
		return baos.toByteArray();
	}
	/**
	 * 描述:解压缩
	 * 创建人:luhaiyou   
	 * 创建时间:2015年8月4日 上午10:15:54  
	 * @param @param message
	 * @param @return
	 * @param @throws IOException
	 * @return byte[]
	 * @throws
	 */
	public static String uncompress(byte[] message) throws IOException{
		ByteArrayInputStream bis = new ByteArrayInputStream(message);
		InflaterInputStream ii = new InflaterInputStream(bis);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int c = 0;
		byte[] buf = new byte[2048];
		while (true) {
			c = ii.read(buf);
			if (c == -1)
				break;
			baos.write(buf, 0, c);
		}
		ii.close();
		baos.close();
		String res = new String(baos.toByteArray(),"UTF-8");
		return res;
	}
}
