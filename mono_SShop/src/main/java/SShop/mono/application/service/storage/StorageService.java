package SShop.mono.application.service.storage;

import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface StorageService {
  // tạo 1 nơi chứa các object được đẩy lên
  void createBucket(String bucketName);
  void deleteBucket(String bucketName);
  List<String> uploadObjects(String bucketName, List<MultipartFile> contents) throws IOException;
  void deleteObject(String bucketName, String key);
  String getObjectUrl(String bucketName, String key);

  List<String> getObjectNames(String bucketName);
}
