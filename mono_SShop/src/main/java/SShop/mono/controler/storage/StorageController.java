package SShop.mono.controler.storage;

import SShop.mono.application.service.storage.StorageService;
import SShop.mono.domain.constant.ProductConstant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@AllArgsConstructor
@RestController
public class StorageController implements StorageApi {
  private final StorageService s3StorageImpl;
  @Override
  public ResponseEntity<?> uploadImagePoduct(MultipartFile[] files) {
    try {
      List<MultipartFile> newFiles = Arrays.stream(files).collect(Collectors.toList());
      var result = s3StorageImpl.uploadObjects(ProductConstant.BUCKET_IMAGE, newFiles);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Upload fail");
    }
  }

  @Override
  public ResponseEntity<?> createBucket(String bucketName) {
    try {
      s3StorageImpl.createBucket(bucketName);
      return ResponseEntity.ok("Create bucket success");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Create bucket fail");
    }
  }

  @Override
  public ResponseEntity<?> getProductImageUrl( String key) {
    try {
      return ResponseEntity.ok(s3StorageImpl.getObjectUrl(ProductConstant.BUCKET_IMAGE, key));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Get object url fail");
    }
  }

  @Override
  public ResponseEntity<?> getNames(String bucketName) {
    try {
          var result = s3StorageImpl.getObjectNames(bucketName);
          return ResponseEntity.ok(result);
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }
}
