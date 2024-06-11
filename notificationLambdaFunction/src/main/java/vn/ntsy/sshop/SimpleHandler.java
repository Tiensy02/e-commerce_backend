package vn.ntsy.sshop;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import org.springframework.core.io.ClassPathResource;


public class SimpleHandler implements RequestHandler<SQSEvent, String> {
  private final Logger log = Logger.getLogger(SimpleHandler.class.getName());
  @Override
  public String handleRequest(SQSEvent sqsEvent, Context context) {
    try {
      FirebaseApp firebaseApp = getFirebaseApp();
      FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance(firebaseApp);
      ObjectMapper objectMapper = new ObjectMapper();
      for(SQSMessage sqsMessage : sqsEvent.getRecords()) {
        String payload = sqsMessage.getMessageAttributes().get("payload").getStringValue();
        TargetNotification targetNotification = objectMapper.readValue(payload,
            TargetNotification.class);
        System.out.println(targetNotification);

        sendNotification(targetNotification,firebaseMessaging);

      }

    } catch (IOException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException(e);

    } catch (ExecutionException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      System.out.println(e.getMessage());
      throw new RuntimeException(e);
    }
    return "success";
  }

  public FirebaseApp getFirebaseApp() throws IOException {
    GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
        new ClassPathResource("firebase-service-account.json").getInputStream());

    FirebaseOptions firebaseOptions = FirebaseOptions.builder()
        .setCredentials(googleCredentials)
        .build();

    if (FirebaseApp.getApps().size() == 0) {
      FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "myApp");
      return app;
    }
    return FirebaseApp.getApps().get(0);
  }
  public void sendNotification(TargetNotification targetNotification,FirebaseMessaging firebaseMessaging)
      throws ExecutionException, InterruptedException {
    List<String> targetTokens = new ArrayList<>(targetNotification.getTargetIds().values());
    String image;
    String link;
    try {
       image = targetNotification.getCustomField().get("image");
       link = targetNotification.getCustomField().get("link");
    } catch (Exception e) {
      link ="";
      image="";
    }
    if(image == null ) {
      image="http";
    }
    Notification notification =Notification.builder()
        .setTitle(targetNotification.getTitle())
        .setBody(targetNotification.getContent())
        .setImage(image)
        .build();
    System.out.println("image : "+image);
    System.out.println("link: " + link);



    Map<String,String> headerWebConfig = new HashMap<>();
    headerWebConfig.put("Urgency","high");

    Map<String,String> dataWeb = new HashMap<>();
    if(link != null) {

    dataWeb.put("link",link);
    }

    WebpushConfig webpushConfig  = WebpushConfig.builder()
        .putAllHeaders(headerWebConfig)
        .putAllData(dataWeb)
        .build();

    MulticastMessage multicastMessage = MulticastMessage.builder()
        .addAllTokens(targetTokens)
        .setNotification(notification)
        .setWebpushConfig(webpushConfig)
        .build();
    System.out.println(targetTokens);


    try {
      ApiFuture<BatchResponse> future = firebaseMessaging.sendMulticastAsync(multicastMessage);
      BatchResponse result = future.get();
      System.out.println("thành công: " + result.getSuccessCount() + ". thất bại: " + result.getFailureCount());

      if (result.getFailureCount() != 0) {
        result.getResponses().forEach(failure -> {
          System.out.println("Error sending message: " + failure.getException());
        });
      }

    } catch (ExecutionException | InterruptedException e) {
      System.out.println("Error sending FCM message: " + e.getMessage());
      throw e;
    }
  }
}