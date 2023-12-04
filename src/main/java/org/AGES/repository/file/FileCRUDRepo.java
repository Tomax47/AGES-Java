package org.AGES.repository.file;

import org.AGES.model.FileInfo;
import org.AGES.repository.CRUDRepo;
import java.sql.SQLException;

public interface FileCRUDRepo extends CRUDRepo<FileInfo> {

    int save(FileInfo fileInfo) throws SQLException;
    FileInfo findByUserId(long userId) throws SQLException;
    FileInfo findByProductId(long productId) throws SQLException;
    int deleteProductImage(long productId) throws SQLException;
    int deleteUserProfileImage(long userId) throws SQLException;

}
