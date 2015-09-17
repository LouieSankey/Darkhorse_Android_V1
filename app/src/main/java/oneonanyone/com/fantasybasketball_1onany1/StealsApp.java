package oneonanyone.com.fantasybasketball_1onany1;


import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by lsankey on 8/10/15.
 */
public class StealsApp extends android.app.Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "QlInZVL7opZD7Ys3wmQU7cMx3D2huhIvAVPNNu6c", "BXKV4VbrbOLPQu0FsiK9ECcSqgfw1IhJOIngUWWf");











    }

   
}
