package com.chous.imageservice.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    private final MongoDatabaseFactory dbFactory;
    private final GridFsOperations gridFsOperations;

    public ImageService(GridFsOperations gridFsOperations, MongoDatabaseFactory dbFactory) {
        this.gridFsOperations = gridFsOperations;
        this.dbFactory = dbFactory;
    }

    public String uploadImage(MultipartFile file, Long bookId) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put("book_id", bookId);

        InputStream inputStream = file.getInputStream();
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();

        return gridFsOperations.store(inputStream, fileName, contentType, metaData).toString();
    }

    public GridFsResource getImageByBookId(Long bookId) {

        Query query = Query.query(Criteria.where("metadata.book_id").is(bookId));
        GridFSFile gridFSFile = gridFsOperations.findOne(query);

        return (gridFSFile != null) ? new GridFsResource(gridFSFile, getGridFs().openDownloadStream(gridFSFile.getObjectId())) : null;
    }

    private GridFSBucket getGridFs() {
        MongoDatabase db = dbFactory.getMongoDatabase();
        return GridFSBuckets.create(db);
    }

    public void deleteBookImageById(Long bookId) {
        Query query = Query.query(Criteria.where("metadata.book_id").is(bookId));
        gridFsOperations.delete(query);
    }
}
