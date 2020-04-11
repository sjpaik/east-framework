package com.east.framework.web.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 파일 다운로드 유틸.
 *
 * Updated on : 2015-12-08 Updated by : love.
 */
public class DownLoadUtils {

    /**
     * 생성자 - 객체 생성 불가.
     */
    private DownLoadUtils() {
        // do nothing;
    }

    /**
     * 지정된 파일을 다운로드 한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param file 다운로드할 파일
     *
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, File file) throws ServletException, IOException {
        download(request, response, file, file.getName());
    }

    /**
     * 지정된 파일을 다운로드 한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param file 다운로드할 파일
     * @param fileName 다운로드 파일 이름.
     *
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, File file, String fileName) throws ServletException, IOException {

        if (file == null || !file.exists() || file.length() <= 0 || file.isDirectory()) {
            throw new IOException("파일 객체가 Null 혹은 존재하지 않거나 길이가 0, 혹은 파일이 아닌 디렉토리이다.");
        }

        String mimetype = request.getSession().getServletContext().getMimeType(file.getName());

        InputStream is = null;

        try {
            is = new FileInputStream(file);
            download(request, response, is, fileName, file.length(), mimetype);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 지정된 파일목록을 zip파일로 다운로드 한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param files 다운로드할 파일 들
     * @param zipFileName 다운로드 파일 이름.
     *
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    public static void downloadZip(HttpServletRequest request, HttpServletResponse response, File[] files, String zipFileName)
            throws ServletException, IOException {

        String mime = "application/octet-stream;";

        try {
            if (response.getContentType() != null)
                response.setContentType(mime + "; charset=" + FileConstants.CHARSET);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String userAgent = request.getHeader("User-Agent");

        // attachment; 가 붙으면 IE의 경우 무조건 다운로드창이 뜬다. 상황에 따라 써야한다.
        if (userAgent != null && userAgent.indexOf("MSIE 5.5") > -1) { // MS IE 5.5 이하
            response.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(zipFileName, "UTF-8") + ";");
        } else if (userAgent != null && userAgent.indexOf("MSIE") > -1) { // MS IE (보통은 6.x 이상가정)
            response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(zipFileName, "UTF-8") + ";");
        } else { // 모질라나 오페라
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(zipFileName.getBytes(FileConstants.CHARSET), "latin1") + ";");
        }

        response.setHeader("Content-Transfer-Encoding", "binary");

        OutputStream os = response.getOutputStream();
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(os));

        for (File file : files) {

            ZipEntry ze = new ZipEntry(file.getName());
            zos.putNextEntry(ze);

            InputStream in = new BufferedInputStream(new FileInputStream(file));

            byte[] b = new byte[1024];
            int len;
            while ((len = in.read(b)) != -1) {
                zos.write(b, 0, len);
            }
            in.close();
            zos.closeEntry();
        }
        zos.close();

    }

    /**
     * 해당 입력 스트림으로부터 오는 데이터를 다운로드 한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param is 입력 스트림
     * @param filename 파일 이름
     * @param filesize 파일 크기
     * @param mimetype MIME 타입 지정
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, InputStream is, String filename, long filesize, String mimetype)
            throws ServletException, IOException {
        String mime = mimetype;

        if (mimetype == null || mimetype.length() == 0) {
            mime = "application/octet-stream;";
        }

        byte[] buffer = new byte[FileConstants.BUFFER_SIZE];

        try {
            if (response.getContentType() != null)
                response.setContentType(mime + "; charset=" + FileConstants.CHARSET);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 아래 부분에서 euc-kr 을 utf-8 로 바꾸거나 URLEncoding을 안하거나 등의 테스트를
        // 해서 한글이 정상적으로 다운로드 되는 것으로 지정한다.
        String userAgent = request.getHeader("User-Agent");

        // attachment; 가 붙으면 IE의 경우 무조건 다운로드창이 뜬다. 상황에 따라 써야한다.
        if (userAgent != null && userAgent.indexOf("MSIE 5.5") > -1) { // MS IE 5.5 이하
            response.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(filename, "UTF-8") + ";");
        } else if (userAgent != null && userAgent.indexOf("MSIE") > -1) { // MS IE (보통은 6.x 이상가정)
            response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8") + ";");
        } else { // 모질라나 오페라
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(filename.getBytes(FileConstants.CHARSET), "latin1") + ";");
        }

        // 파일 사이즈가 정확하지 않을때는 아예 지정하지 않는다.
        if (filesize > 0) {
            response.setHeader("Content-Length", "" + filesize);
        }

        BufferedInputStream fin = null;
        BufferedOutputStream outs = null;

        try {
            fin = new BufferedInputStream(is);
            outs = new BufferedOutputStream(response.getOutputStream());
            int read = 0;

            while ((read = fin.read(buffer)) != -1) {
                outs.write(buffer, 0, read);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            // Tomcat ClientAbortException을 잡아서 무시하도록 처리해주는게 좋다.
        } finally {
            try {
                outs.close();
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }

            try {
                fin.close();
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        } // end of try/catch
    }
}
