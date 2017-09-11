package com.topie.data.common.tools.filemanager;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by chenguojun on 2017/1/14.
 */
public interface IFileManager {

    FolderDto folder(String root, String rootUrl, String dir);

    void createDir(String dir);

    Map rename(String dir, String oldName, String newName);

    void removeDir(String dir);

    void removeFile(String filePath);

    void download(HttpServletResponse response, String filePath, String fileName);

}
