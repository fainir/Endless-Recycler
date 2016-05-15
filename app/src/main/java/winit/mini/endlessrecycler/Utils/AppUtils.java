package winit.mini.endlessrecycler.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Fainir on 12/05/2016.
 */
public class AppUtils {

    public static void showToastLong(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showToastShort(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
