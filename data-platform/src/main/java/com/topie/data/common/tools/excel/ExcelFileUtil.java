package com.topie.data.common.tools.excel;

import com.topie.data.common.tools.tabletoxls.TableToXls;
import com.topie.data.common.utils.FileUtil;
import com.topie.data.common.utils.PropertiesUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by cgj on 2016/1/6.
 */
public class ExcelFileUtil {

    public static void download(HttpServletResponse response, String filePath, String fileName) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-core");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso-8859-1"));
        File file = new File(filePath);
        InputStream inputStream = new FileInputStream(file);
        OutputStream os = response.getOutputStream();
        byte[] b = new byte[1024];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            os.write(b, 0, length);
        }
        inputStream.close();
    }

    public static <T> void responseXlsx(HttpServletResponse response, String fileName, List<T> list) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-core");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String(fileName.getBytes("gbk"), "iso-8859-1"));
        InputStream inputStream = getXlsxInputStreamByBean(list);
        OutputStream os = response.getOutputStream();
        byte[] b = new byte[1024];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            os.write(b, 0, length);
        }
        inputStream.close();
    }

    public static <T> void responseXlsx(HttpServletResponse response, String fileName, String[] headers, List<T> list)
            throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-core");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String(fileName.getBytes("gbk"), "iso-8859-1"));
        InputStream inputStream = getXlsxInputStreamByBean(headers, list);
        OutputStream os = response.getOutputStream();
        byte[] b = new byte[1024];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            os.write(b, 0, length);
        }
        inputStream.close();
    }

    public static <T> void responseXlsx(HttpServletResponse response, String fileName, List<String> sheetNames,
            List<String[]> headers, List<Collection<T>> list) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-core");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String(fileName.getBytes("gbk"), "iso-8859-1"));
        InputStream inputStream = getXlsxInputStreamByBean(sheetNames, headers, list);
        OutputStream os = response.getOutputStream();
        byte[] b = new byte[1024];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            os.write(b, 0, length);
        }
        inputStream.close();
    }

    public static <T> void responseXlsx(HttpServletResponse response, String fileName, List<String> sheetNames,
            List<String[]> mapHeaders, List<String[]> headers, List<Collection<T>> list) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-core");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String(fileName.getBytes("gbk"), "iso-8859-1"));
        InputStream inputStream = getXlsxInputStreamByBean(sheetNames, mapHeaders, headers, list);
        OutputStream os = response.getOutputStream();
        byte[] b = new byte[1024];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            os.write(b, 0, length);
        }
        inputStream.close();
    }

    public static <T> void responseXlsx(HttpServletResponse response, String fileName, String[] headers,
            String[] mapHeaders, List<T> list) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-core");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String(fileName.getBytes("gbk"), "iso-8859-1"));
        InputStream inputStream = getXlsxInputStreamByBean(headers, mapHeaders, list);
        OutputStream os = response.getOutputStream();
        byte[] b = new byte[1024];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            os.write(b, 0, length);
        }
        inputStream.close();
    }

    public static <T> InputStream getXlsxInputStreamByBean(String[] headers, List<T> list) throws IOException {
        File file = createTempFile();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ExcelUtil.exportExcelX(headers, list, out, null);
            InputStream inputStream = new FileInputStream(file);
            return inputStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            file.delete();
        }
    }

    public static <T> InputStream getXlsxInputStreamByBean(List<T> list) throws IOException {
        File file = createTempFile();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ExcelUtil.exportExcelX(list, out, "yyyy-MM-dd");
            InputStream inputStream = new FileInputStream(file);
            return inputStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            file.delete();
        }
    }

    public static <T> InputStream getXlsxInputStreamByBean(String[] headers, List<T> list, String filePath)
            throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ExcelUtil.exportExcelX(headers, list, out, null);
            InputStream inputStream = new FileInputStream(file);
            return inputStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            file.delete();
        }
    }

    public static <T> InputStream getXlsxInputStreamByBean(List<String> sheetNames, List<String[]> headers,
            List<Collection<T>> list) throws IOException {
        File file = createTempFile();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ExcelUtil.exportXlsx(sheetNames, headers, list, out, null);
            InputStream inputStream = new FileInputStream(file);
            return inputStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            file.delete();
        }
    }

    public static <T> InputStream getXlsxInputStreamByBean(List<String> sheetNames, List<String[]> mapHeaders,
            List<String[]> headers, List<Collection<T>> list) throws IOException {
        File file = createTempFile();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ExcelUtil.exportXlsx(sheetNames, mapHeaders, headers, list, out, null);
            InputStream inputStream = new FileInputStream(file);
            return inputStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            file.delete();
        }
    }

    public static <T> InputStream getXlsxInputStreamByBean(String[] headers, String[] mapHeaders, List<T> list)
            throws IOException {
        File file = createTempFile();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ExcelUtil.exportExcelX(headers, mapHeaders, list, out, null);
            InputStream inputStream = new FileInputStream(file);
            return inputStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            file.delete();
        }
    }

    private static File createTempFile() throws IOException {
        File temp = new File(PropertiesUtil.get("/config/properties/profile.properties", "temp.folder") + System
                .currentTimeMillis());
        return temp;
    }

    public static void responseXlsFromHtml(HttpServletResponse response, String fileName, String HtmlStr)
            throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-core");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String(fileName.getBytes("gbk"), "iso-8859-1"));
        File file = createTempFile();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fileInputStream = new FileOutputStream(file);
        TableToXls.process(HtmlStr, fileInputStream);
        InputStream inputStream = new FileInputStream(file);
        OutputStream os = response.getOutputStream();
        byte[] b = new byte[1024];
        int length;
        while ((length = inputStream.read(b)) > 0) {
            os.write(b, 0, length);
        }
        inputStream.close();
    }

    public static <T> void exportXlsx(String path, List<T> list) throws IOException {
        InputStream inputStream = getXlsxInputStreamByBean(list);
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) file.createNewFile();
        FileUtil.saveFileFromInputStream(inputStream, path);
    }

    public static <T> void exportXlsx(String path, List<T> list, String[] headers, String[] mapHeaders)
            throws IOException {
        InputStream inputStream = getXlsxInputStreamByBean(headers, mapHeaders, list);
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) file.createNewFile();
        FileUtil.saveFileFromInputStream(inputStream, path);
    }
}
