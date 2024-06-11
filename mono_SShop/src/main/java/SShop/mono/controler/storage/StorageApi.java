package SShop.mono.controler.storage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public interface StorageApi {
  @PostMapping("/product/upload-image")
  ResponseEntity<?> uploadImagePoduct(@RequestParam("files") MultipartFile[] files);

  @PostMapping("/product/create-bucket")
  ResponseEntity<?> createBucket(@RequestParam String bucketName);

  @GetMapping("/product/get-object-url")
  ResponseEntity<?> getProductImageUrl( @RequestParam String key);

  @GetMapping("/product/names")
  ResponseEntity<?> getNames(@RequestParam String bucketName);
}
