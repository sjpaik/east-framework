package com.east.framework.web.file;

import java.io.File;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.east.framework.core.util.idgen.UUIDGeneratorFactory;
import com.east.framework.web.file.model.FileMeta;

/**
 * 멀티 파트 파일 유틸.
 *
 * Updated on : 2015-12-08 Updated by : love.
 */
public class MultipartFileUtils {

    /**
     * 파일 업로드.
     *
     * @param multipartFile MultipartFile
     * @param filePath 파일 경로
     * @return FileMeta
     * @throws Exception Exception
     */
    public static FileMeta upload(MultipartFile multipartFile, String filePath) throws Exception {
        String fileName = MultipartFileUtils.fileNameGenerate();
        return MultipartFileUtils.upload(multipartFile, filePath, fileName, true);
    }

    /**
     * <pre>
     * 파일 업로드.  
     * 업로드파일 명이 중복 되어있을 경우 Exception이 발생 한다.
     * </pre>
     *
     * @param multipartFile MultipartFile
     * @param filePath 파일 경로
     * @param fileName 파일 이름
     * @return FileMeta
     * @throws Exception Exception
     */
    public static FileMeta upload(MultipartFile multipartFile, String filePath, String fileName) throws Exception {
        return MultipartFileUtils.upload(multipartFile, filePath, fileName, true);
    }

    /**
     * <pre>
     * 파일 업로드.  
     * 업로드파일 명이 중복 되어있을 경우 파일명+숫자 형태로 파일을 생성 한다.
     * </pre>
     *
     * @param multipartFile MultipartFile
     * @param filePath 파일 경로
     * @param fileName 파일 이름
     * @return FileMeta
     * @throws Exception Exception
     */
    public static FileMeta possibleUpload(MultipartFile multipartFile, String filePath, String fileName) throws Exception {
        String possibleFileName = MultipartFileUtils.getPossibleUploadFileName(filePath, fileName);
        return MultipartFileUtils.upload(multipartFile, filePath, possibleFileName, true);
    }

    /**
     * 파일 업로드.
     *
     * @param multipartFile MultipartFile
     * @param filePath 파일 경로
     * @param fileName 파일 이름
     * @param isExtension 파일 확장자 여부
     * @return FileMeta
     * @throws Exception Exception
     */
    public static FileMeta upload(MultipartFile multipartFile, String filePath, String fileName, boolean isExtension) throws Exception {
        CommonsMultipartFile cmf = (CommonsMultipartFile) multipartFile;

        if (StringUtils.isEmpty(cmf.getOriginalFilename())) {
            return null;
        }

        String newFullFileName;
        if (isExtension) {
            String extensionChar = FilenameUtils.getExtension(cmf.getOriginalFilename());
            if (!FilenameUtils.isExtension(fileName, extensionChar)) {
                newFullFileName = filePath + FileConstants.FILE_SEPARATORS + fileName + FileConstants.FILE_EXTENSION_SEPARATOR + extensionChar;
            } else {
                newFullFileName = filePath + FileConstants.FILE_SEPARATORS + fileName;
            }
        } else {
            String extensionChar = FilenameUtils.getExtension(cmf.getOriginalFilename());
            if (FilenameUtils.isExtension(fileName, extensionChar)) {
                newFullFileName = filePath + FileConstants.FILE_SEPARATORS + FilenameUtils.removeExtension(fileName);
            } else {
                newFullFileName = filePath + FileConstants.FILE_SEPARATORS + fileName;
            }
        }

        newFullFileName = FilenameUtils.separatorsToSystem(newFullFileName);

        MultipartFileUtils.uploadFile(cmf, newFullFileName);

        return MultipartFileUtils.fileMetaGenerate(cmf, newFullFileName);
    }

    /**
     * 파일 복사.
     *
     * @param src 대상
     * @param dest 목표
     * @throws Exception Exception
     */
    public static void copy(String src, String dest) throws Exception {
        File fsrc = new File(src);
        File fdest = new File(dest);

        if (!fsrc.isFile()) {
            throw new FileExistsException("복사하려는 파일이 존재 하고 있습니다.");
        }
        if (!fdest.isDirectory()) {
            fdest.getParentFile().mkdirs();
        }
        if (fdest.isFile()) {
            throw new FileExistsException("이미 파일이 존재 하고 있습니다.");
        }

        FileUtils.copyFile(fsrc, fdest);
    }

    /**
     * 파일 삭제.
     *
     * @param src 대상
     * @throws Exception Exception
     */
    public static void delete(String src) throws Exception {
        File fsrc = new File(src);

        if (!fsrc.isFile()) {
            throw new FileExistsException("파일이 존재 하지 않습니다.");
        }

        FileUtils.deleteQuietly(fsrc);
    }

    /**
     * 파일 이름 제너레이터.
     *
     * @return 파일 이름
     */
    private static String fileNameGenerate() {
        return UUIDGeneratorFactory.getFomatIns().getId();
    }

    /**
     * 파일 메타 정보 제너레이터.
     *
     * @param cmf CommonsMultipartFile
     * @param saveFileName 저장 파일 이름.
     * @return FileMeta
     */
    private static FileMeta fileMetaGenerate(CommonsMultipartFile cmf, String saveFileName) {
        FileMeta fileMeta = new FileMeta();
        fileMeta.setContentType(cmf.getContentType());
        fileMeta.setFileExtension(FilenameUtils.getExtension(cmf.getOriginalFilename()));
        fileMeta.setName(cmf.getName());
        fileMeta.setOriginalName(cmf.getOriginalFilename());
        fileMeta.setSaveFileName(FilenameUtils.getName(saveFileName));
        fileMeta.setSaveFilePath(FilenameUtils.getFullPath(saveFileName));
        fileMeta.setFileKey(FilenameUtils.getBaseName(saveFileName));
        fileMeta.setFileSize(cmf.getSize());
        return fileMeta;
    }

    /**
     * 파일 업로드.
     *
     * @param cmf CommonsMultipartFile
     * @param newFullFileName 파일 이름
     * @throws Exception Exception
     */
    private static void uploadFile(CommonsMultipartFile cmf, String newFullFileName) throws Exception {
        File f = new File(newFullFileName);
        if (!f.isDirectory()) {
            f.getParentFile().mkdirs();
        }
        if (f.isFile()) {
            throw new FileExistsException("이미 파일이 존재 하고 있습니다.");
        }
        cmf.transferTo(f);
    }

    /**
     * 파일 존재 여부를 체크 하여 생성 가능한 신규 파일 이름을 리턴.
     * 
     * @param filePath 파일 경로
     * @param fileName 파일 이름
     * @throws Exception Exception
     * 
     * @return 사용 가능한 파일 이름
     */
    public static String getPossibleUploadFileName(String filePath, String fileName) throws Exception {

        String fullFileName = filePath + FileConstants.FILE_SEPARATORS + fileName;

        String possibleUploadFileName = "";
        File f = new File(fullFileName);
        if (!f.isDirectory()) {
            f.getParentFile().mkdirs();
        }
        if (f.isFile()) {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                String newFileName = "";
                int extbeforelen = fullFileName.lastIndexOf(FileConstants.FILE_EXTENSION_SEPARATOR);
                if (extbeforelen > 0) {
                    newFileName = fullFileName.substring(0, extbeforelen) + FileConstants.FILE_ADD_CHAR + Integer.toString(i)
                            + fullFileName.substring(extbeforelen, fullFileName.length());
                } else {
                    newFileName = fullFileName + FileConstants.FILE_ADD_CHAR + Integer.toString(i);
                }
                File nf = new File(newFileName);
                if (nf.isFile()) {
                    continue;
                } else {
                    possibleUploadFileName = nf.getName();
                    break;
                }
            }
        } else {
            possibleUploadFileName = f.getName();
        }

        return possibleUploadFileName;
    }
}
