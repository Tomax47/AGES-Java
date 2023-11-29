package org.AGES.repository.file;

import org.AGES.model.FileInfo;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public interface FileRWService {
    void saveFileToStorage(InputStream file, String originalFileName, String contentType, Long size, Long userId, Long productId);
    void writeUserFileFromStorage(Long userId, OutputStream outputStream) throws SQLException;
    FileInfo getUserFileInfo(Long userId) throws SQLException;
    void writeProductFileFromStorage(Long productId, OutputStream outputStream) throws SQLException;
    FileInfo getProductFileInfo(Long productId) throws SQLException;

    int deleteProductImage(Long productId) throws SQLException;
    int deleteUserProfileImage(Long userId) throws SQLException;
}
