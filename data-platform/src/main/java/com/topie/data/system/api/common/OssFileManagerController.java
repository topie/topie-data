package com.topie.data.system.api.common;

import com.topie.data.common.tools.filemanager.FolderDto;
import com.topie.data.common.utils.FileUtil;
import com.topie.data.common.utils.ResponseUtil;
import com.topie.data.security.utils.SecurityUtil;
import com.topie.data.common.tools.filemanager.OssFileManagerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by cgj on 2015/12/8.
 */
@Controller
@RequestMapping("/api/ossFileManager")
public class OssFileManagerController {

    @Value("${upload.folder}")
    private String uploadFolder;

    @Autowired
    private OssFileManagerTool ossFileManagerTool;

    @RequestMapping("/folder")
    public
    @ResponseBody
    Object ossFolder(HttpServletRequest request, @RequestParam(value = "path", defaultValue = "") String path) {
        FolderDto folderDto = ossFileManagerTool.folder("", "", path);
        return ResponseUtil.success(folderDto);
    }

    @RequestMapping(value = "/createFolder", method = RequestMethod.POST)
    public
    @ResponseBody
    Object createFolder(@RequestParam(value = "dirPath") String dirPath) {
        ossFileManagerTool.createDir(dirPath);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    public
    @ResponseBody
    Object rename(HttpServletRequest httpServletRequest, @RequestParam(value = "folderPath") String folderPath,
            @RequestParam(value = "oldName") String oldName, @RequestParam(value = "newName") String newName) {
        return ResponseUtil.error("oss暂不支持重命名功能");
    }

    @RequestMapping(value = "/deleteFolder", method = RequestMethod.POST)
    public
    @ResponseBody
    Object deleteFolder(@RequestParam(value = "dirPath") String dirPath) {
        ossFileManagerTool.removeDir(dirPath);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public
    @ResponseBody
    Object deleteFile(@RequestParam(value = "filePath") String filePath) {
        ossFileManagerTool.removeFile(filePath);
        return ResponseUtil.success();
    }

    @RequestMapping("/download")
    public void downloadFile(HttpServletResponse response, HttpServletRequest httpServletRequest,
            @RequestParam(value = "folderPath") String folderPath, @RequestParam(value = "fileName") String fileName)
            throws UnsupportedEncodingException {
        ossFileManagerTool.download(response, folderPath, fileName);
    }

    @RequestMapping("/upload")
    public
    @ResponseBody
    Object singleUpload(@RequestParam(value = "file") MultipartFile multipartFile,
            @RequestParam(value = "folderPath") String folderPath, HttpServletRequest httpServletRequest)
            throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return ResponseUtil.error("请先上传附件");
        }
        String rootPath =
                httpServletRequest.getSession().getServletContext().getRealPath("/") + uploadFolder + "/" + SecurityUtil
                        .getCurrentUserName() + "/";
        String wholeRealPath = rootPath + folderPath;
        FileUtil.saveFileFromInputStream(multipartFile.getInputStream(), wholeRealPath,
                multipartFile.getOriginalFilename());
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/zip", method = RequestMethod.POST)
    public
    @ResponseBody
    Object zip(HttpServletRequest httpServletRequest, @RequestParam(value = "folderPath") String folderPath,
            @RequestParam(value = "name") String name, @RequestParam(value = "zipName") String zipName) {
        return ResponseUtil.error("oss暂不支持压缩功能");
    }

    @RequestMapping(value = "/unCompress", method = RequestMethod.POST)
    public
    @ResponseBody
    Object unCompress(HttpServletRequest httpServletRequest, @RequestParam(value = "folderPath") String folderPath,
            @RequestParam(value = "name") String name) throws Exception {
        return ResponseUtil.error("oss暂不支持解压缩功能");
    }

}
