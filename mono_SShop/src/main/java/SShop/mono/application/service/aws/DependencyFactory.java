package SShop.mono.application.service.aws;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class DependencyFactory {

  private DependencyFactory() {

  }

  public static S3Client s3Client() {
    Region region = Region.AP_SOUTHEAST_1;
    S3Client s3 = S3Client.builder()
        .region(region)
        .build();

    return s3;
  }

}
