package br.com.thiengo.webviewusersignup.extras;

/**
 * Created by viniciusthiengo on 13/11/16.
 */

public class Util {
    public static boolean isPreLollipop(){
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        return currentapiVersion < android.os.Build.VERSION_CODES.LOLLIPOP;
    }
}
