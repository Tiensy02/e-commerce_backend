package SShop.mono.application.service.storage;

import SShop.mono.application.service.aws.DependencyFactory;
import SShop.mono.domain.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Slf4j
@Service
public class S3StorageImpl implements StorageService {
private final S3Client s3Client;
private final FileUtils fileUtils;

  public S3StorageImpl(FileUtils fileUtils) {
    this.fileUtils = fileUtils;
    this.s3Client = DependencyFactory.s3Client();
  }


  @Override
  public void createBucket(String bucketName) {
    try {
      s3Client.createBucket(CreateBucketRequest
          .builder()
          .bucket(bucketName)
          .build());
      log.info("Creating bucket: " + bucketName);
      s3Client.waiter().waitUntilBucketExists(
          software.amazon.awssdk.services.s3.model.HeadBucketRequest.builder()
              .bucket(bucketName)
              .build());
      log.info(bucketName + " is ready.");
    }catch (S3Exception e) {
      log.error(e.awsErrorDetails().errorMessage());
      throw new RuntimeException("Error creating bucket");
    }
  }

  @Override
  public void deleteBucket(String bucketName) {

  }

  @Override
  public List<String> uploadObjects(String bucketName, List<MultipartFile> contents)
      throws IOException {
    List<String> resultUrl = new ArrayList<>();
    for (MultipartFile content : contents) {
      try {
        // Convert MultipartFile to File
        File tempFile = fileUtils.convertMultiPartToFile(content);
        String fileName = tempFile.getName();
        System.out.println(fileName);
        // Upload the file to S3
        resultUrl.add(uploadFileToS3( bucketName, fileName,tempFile )) ;

      } catch (IOException e) {
        log.error(e.getMessage());

      }
    }
    return resultUrl;
    }


  @Override
  public void deleteObject(String bucketName, String key) {

  }

  @Override
  public String getObjectUrl(String bucketName, String keyName) {
    try {
      GetUrlRequest request = GetUrlRequest.builder()
          .bucket(bucketName)
          .key(keyName)
          .build();

      URL url = s3Client.utilities().getUrl(request);

      System.out.println("The URL for  " + keyName + " is " + url);
      return url.toString();
    } catch (S3Exception e) {
      log.error(e.awsErrorDetails().errorMessage());
      throw new RuntimeException("Error getting object URL");
    }
//    Region region = Region.AP_SOUTHEAST_1;
//    S3Presigner presigner = S3Presigner.builder()
//        .region(region)
//        .build();
//    getPresignedUrl(presigner, bucketName, keyName);
//    return null;
  }

  @Override
  public List<String> getObjectNames(String bucketName) {
    ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
        .bucket(bucketName )
//        .maxKeys(50) // Set the maxKeys parameter to control the page size
        .build();

//    ListObjectsV2Iterable listObjectsV2Iterable = s3Client.listObjectsV2Paginator(listObjectsV2Request);
    ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);
    List<S3Object> contents = listObjectsV2Response.contents();

    List<String> result = new ArrayList<>();

    for(S3Object obj : contents ) {
      result.add(obj.key());
    }
//    long totalObjects = 0;
//
//    for (ListObjectsV2Response page : listObjectsV2Iterable) {
//      long retrievedPageSize = page.contents().stream()
//          .peek(System.out::println)
//          .reduce(0, (subtotal, element) -> subtotal + 1, Integer::sum);
//      totalObjects += retrievedPageSize;
//      System.out.println("Page size: " + retrievedPageSize);
//    }
//    System.out.println("Total objects in the bucket: " + totalObjects);

    return result;
  }

  private String uploadFileToS3(String bucketName, String key, File file) {
    try {
      Map<String, String> metadata = new HashMap<>();
      metadata.put("x-amz-meta-myVal", "product");
      PutObjectRequest putOb = PutObjectRequest.builder()
          .bucket(bucketName)
          .key(key)
          .metadata(metadata)
          .build();

      s3Client.putObject(putOb, RequestBody.fromFile(file));
      key = key.replace(' ','+');
      log.info("File uploaded to S3");
      return "https://sshop-product-image2.s3.ap-southeast-1.amazonaws.com/"+key;

    } catch (S3Exception e) {
      log.error(e.awsErrorDetails().errorMessage());
      throw new RuntimeException("Error uploading file to S3");
    }
  }
  public void getPresignedUrl(S3Presigner presigner, String bucketName, String keyName) {
    try {
      GetObjectRequest getObjectRequest = GetObjectRequest.builder()
          .bucket(bucketName)
          .key(keyName)
          .build();

      GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
          .signatureDuration(Duration.ofMinutes(60))
          .getObjectRequest(getObjectRequest)
          .build();

      PresignedGetObjectRequest presignedGetObjectRequest = presigner.presignGetObject(getObjectPresignRequest);
      String theUrl = presignedGetObjectRequest.url().toString();
      System.out.println("Presigned URL: " + theUrl);
//      HttpURLConnection connection = (HttpURLConnection) presignedGetObjectRequest.url().openConnection();
//      presignedGetObjectRequest.httpRequest().headers().forEach((header, values) -> {
//        values.forEach(value -> {
//          connection.addRequestProperty(header, value);
//        });
//      });
//
//      // Send any request payload that the service needs (not needed when
//      // isBrowserExecutable is true).
//      if (presignedGetObjectRequest.signedPayload().isPresent()) {
//        connection.setDoOutput(true);
//
//        try (InputStream signedPayload = presignedGetObjectRequest.signedPayload().get().asInputStream();
//            OutputStream httpOutputStream = connection.getOutputStream()) {
//          IoUtils.copy(signedPayload, httpOutputStream);
//        }
//      }
//
//      // Download the result of executing the request.
//      try (InputStream content = connection.getInputStream()) {
//        System.out.println("Service returned response: ");
//        IoUtils.copy(content, System.out);
//      }

    } catch (S3Exception e) {
      log.error(e.awsErrorDetails().errorMessage());
      e.getStackTrace();
    }
  }

}
