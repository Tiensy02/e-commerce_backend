package vn.ntsy.sshop;

import java.util.Map;

public class TargetNotification {
  // useId, target
  private Map<String,String> targetIds;

  private String settingName;

  private String notificationType;

  private String content;

  private String title;

  private Map<String,String> customField;

  public Map<String, String> getTargetIds() {
    return targetIds;
  }

  public void setTargetIds(Map<String, String> targetIds) {
    this.targetIds = targetIds;
  }

  public String getSettingName() {
    return settingName;
  }

  public void setSettingName(String settingName) {
    this.settingName = settingName;
  }

  public String getNotificationType() {
    return notificationType;
  }

  public void setNotificationType(String notificationType) {
    this.notificationType = notificationType;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Map<String, String> getCustomField() {
    return customField;
  }

  public void setCustomField(Map<String, String> customField) {
    this.customField = customField;
  }

  @Override
  public String toString() {
    return "TargetNotification{" +
        "targetIds=" + targetIds +
        ", settingName='" + settingName + '\'' +
        ", notificationType='" + notificationType + '\'' +
        ", content='" + content + '\'' +
        ", title='" + title + '\'' +
        ", customField=" + customField +
        '}';
  }
}
