package org.hhoa.vi.admin.service;


;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.mgb.model.generator.OmsFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * The interface Oms file service.
 */
public interface OmsFileService {

    /**
     * 分页获取文件信息.
     *
     * @param pageInfo  the page info
     * @param queryFile the query file
     * @return list file list
     */
    List<OmsFile> getFileList(PageInfo pageInfo, OmsFile queryFile);

    /**
     * 上传文件.
     *
     * @param file the file
     * @return file string
     */
    String upload(MultipartFile file);

    /**
     * 下载文件.
     *
     * @param filesUuid the files uuid
     * @param response  the response
     */
    void download(String filesUuid, HttpServletResponse response);

    /**
     * 删除文件信息.
     *
     * @param id the id
     */
    void deleteFileById(Long id);
}
