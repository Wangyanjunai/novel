package com.potato369.novel.utils;

import java.io.*;

import org.springframework.web.multipart.MultipartFile;

import net.sf.jazzlib.ZipFile;

/**
 * <pre>
 * @PackageName com.potato369.novel.utils
 * @ClassName FileUtil
 * @Desc 文件操作工具类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2018/12/24 18:37
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class FileUtil {

    /**
               *  读取文件内容为二进制数组
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] read2ByteArray(String filePath) throws Exception {
        InputStream in = new FileInputStream(filePath);
        byte[] data = inputStream2ByteArray(in);
        in.close();
        return data;
    }

    /**
               * 流转二进制数组
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] inputStream2ByteArray(InputStream in) throws Exception {
        return WwwUtil.toByteArray(in);
    }

    /**
     * 
     * <pre>
     * save方法的作用：
     * 描述方法适用条件：
     * 描述方法的执行流程：
     * 描述方法的使用方法：
     * 描述方法的注意事项：
     *
     * @author Jack
     * @param filePath
     * @param fileName
     * @param content
     * @throws FileNotFoundException
     * @throws IOException
     * @since JDK 1.6
     * </pre>
     */
    public static void save(String filePath, String fileName, byte[] content) throws Exception{
    	File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File file = new File(fileDir, fileName);
        OutputStream os = new FileOutputStream(file);
        os.write(content, 0, content.length);
        os.flush();
        os.close();
    }
    /**
     * 
     * <pre>
     * toZipFile方法的作用：
     * 描述方法适用条件：
     * 描述方法的执行流程：
     * 描述方法的使用方法：
     * 描述方法的注意事项：
     *
     * @author Jack
     * @param file
     * @return
     * @throws Exception
     * @since JDK 1.6
     * </pre>
     */
    public static ZipFile toZipFile(MultipartFile file) throws Exception{
        File f = null;
        if(file.isEmpty() || file.getSize() <= 0) {
            return null;
        } else {
            InputStream ins = file.getInputStream();
            f = new File(file.getOriginalFilename());
            inputStreamToFile(ins, f);
            return new ZipFile(f);
        }
    }
    /**
     * 
     * <pre>
     * inputStreamToFile方法的作用：
     * 描述方法适用条件：
     * 描述方法的执行流程：
     * 描述方法的使用方法：
     * 描述方法的注意事项：
     *
     * @author Jack
     * @param ins
     * @param file
     * @throws Exception
     * @since JDK 1.6
     * </pre>
     */
    public static void inputStreamToFile(InputStream ins, File file) throws Exception{
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }
}
