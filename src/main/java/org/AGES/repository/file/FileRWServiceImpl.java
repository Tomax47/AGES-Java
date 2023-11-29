package org.AGES.repository.file;

import org.AGES.model.FileInfo;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.UUID;

public class FileRWServiceImpl implements FileRWService {
    private FileCRUDRepo filesRepository;

    public FileRWServiceImpl(FileCRUDRepo filesRepository) {
        this.filesRepository = filesRepository;
    }

    @Override
    public void saveFileToStorage(InputStream file, String originalFileName, String contentType, Long size, Long userId, Long productId) {
        FileInfo fileInfo = FileInfo.builder()
                .originalFileName(originalFileName)
                .storageFileName(UUID.randomUUID().toString())
                .size(size)
                .type(contentType)
                .userId(userId)
                .productId(productId)
                .build();
        try {
            Files.copy(file, Paths.get("D:\\OPERAGX _Downloads\\Ancient Goods E-Store\\src\\main\\webapp\\files\\" + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]));
            filesRepository.save(fileInfo);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeUserFileFromStorage(Long userId, OutputStream outputStream) throws SQLException {
        //Find the file in the DB
        FileInfo fileInfo = getUserFileInfo(userId);

        //Find the file on the disk
       File file = new File("D:\\OPERAGX _Downloads\\Ancient Goods E-Store\\src\\main\\webapp\\files\\" + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]);
        try {
           //Write it in the response!
            Files.copy(file.toPath(), outputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void writeProductFileFromStorage(Long productId, OutputStream outputStream) throws SQLException {
        //Find the file in the DB
        FileInfo fileInfo = getProductFileInfo(productId);

        //Find the file on the disk
        File file = new File("D:\\OPERAGX _Downloads\\Ancient Goods E-Store\\src\\main\\webapp\\files\\" + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]);
        try {
            //Write it in the response!
            Files.copy(file.toPath(), outputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public FileInfo getUserFileInfo(Long userId) throws SQLException {
        return filesRepository.findByUserId(userId);
    }

    @Override
    public FileInfo getProductFileInfo(Long productId) throws SQLException {
        return filesRepository.findByProductId(productId);
    }

    @Override
    public int deleteProductImage(Long productId) throws SQLException {
        FileInfo productImage = filesRepository.findByProductId(productId);
        File file = new File("D:\\OPERAGX _Downloads\\Ancient Goods E-Store\\src\\main\\webapp\\files\\" + productImage.getStorageFileName() + "." + productImage.getType().split("/")[1]);

        file.delete();
        System.out.println("DELETED THE FILE FROM THE files PROJECT DIRECTORY!");
        return filesRepository.deleteProductImage(productId);
    }

    @Override
    public int deleteUserProfileImage(Long userId) throws SQLException {
        FileInfo userProfileImage = filesRepository.findByUserId(userId);
        if (userProfileImage != null) {
            //User has a profile image, deleting it from the files dir
            File file = new File("D:\\OPERAGX _Downloads\\Ancient Goods E-Store\\src\\main\\webapp\\files\\" + userProfileImage.getStorageFileName() + "." + userProfileImage.getType().split("/")[1]);
            file.delete();
            System.out.println("DELETED THE FILE FROM THE files PROJECT DIRECTORY!");

            //Deleting the image from the database!
            return filesRepository.deleteUserProfileImage(userId);
        } else {
            return 0;
        }
    }
}
