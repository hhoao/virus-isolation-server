package org.hhoa.vi.admin.service.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.BsonDocument;
import org.bson.Document;
import org.hhoa.vi.admin.bean.PageInfo;
import org.hhoa.vi.admin.service.OmsDocumentService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * OmsDocumentServiceImpl
 *
 * @author hhoa
 * @since 2023/3/20
 **/

@Service
public class OmsDocumentServiceImpl implements OmsDocumentService {
    final private MongoTemplate mongoTemplate;
    OmsDocumentServiceImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<String> list(String documentType, String param, PageInfo pageInfo) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(documentType);
        FindIterable<Document> documents;
        if (StringUtils.hasText(param)) {
            documents = collection.find(BsonDocument.parse(param));
        } else {
            documents = collection.find();
        }
        List<String> documentJsonList = new ArrayList<>();
        documents.forEach(document -> {
            documentJsonList.add(document.toJson());
        });
        return documentJsonList;
    }

    @Override
    public void addDocument(String documentType, String documentParam) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(documentType);
        collection.insertOne(Document.parse(documentParam));
    }

    @Override
    public void deleteDocument(String documentType, String documentParams) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(documentType);
        collection.deleteOne(BsonDocument.parse(documentParams));
    }

    @Override
    public void updateDocument(String documentType, String filter, String newDocument) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(documentType);
        collection.updateMany(BsonDocument.parse(filter), BsonDocument.parse(newDocument));
    }
}
