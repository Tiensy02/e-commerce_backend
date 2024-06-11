package SShop.mono.application.utils;

import java.util.List;
import java.util.Map;

public class QueryUtils {
  public static String completeMess(Map<String,String> args, String mess ) {
    for (String key : args.keySet()) {
      mess = mess.replace(key,args.get(key));
    }
    mess = mess.replace("{","");
    mess = mess.replace("}","");
    return mess;
  }
}
