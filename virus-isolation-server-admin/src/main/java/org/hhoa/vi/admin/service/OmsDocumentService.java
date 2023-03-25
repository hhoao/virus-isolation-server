package org.hhoa.vi.admin.service;

import org.hhoa.vi.admin.bean.PageInfo;

import java.util.List;

/**
 * DocumentService
 *
 * @author hhoa
 * @since 2023 /3/20
 */
public interface OmsDocumentService {
    /**
     * List list.
     *
     * @param documentType   the document type
     * @param documentParams the document params
     * @param pageInfo       the page info
     * @return the list
     */
    List<String> list(String documentType, String documentParams, PageInfo pageInfo);

    /**
     * Add document.
     *
     * @param documentType  the document type
     * @param documentParam the document param
     */
    void addDocument(String documentType, String documentParam);

    /**
     * Delete document.
     *
     * @param documentType   the document type
     * @param documentParams the document id
     */
    void deleteDocument(String documentType, String documentParams);

    /**
     * Update document.
     *
     * @param documentType the document type
     * @param filter       the document param
     * @param newDocument  the new document
     */
    void updateDocument(String documentType, String filter, String newDocument);
}
