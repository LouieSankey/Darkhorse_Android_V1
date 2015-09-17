package oneonanyone.com.fantasybasketball_1onany1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import oneonanyone.com.fantasybasketball_1onany1.DataModel.PlayerAdapter;
import oneonanyone.com.fantasybasketball_1onany1.Login.LoginActivity;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();


    protected Button mPlayerListButton;
    protected TextView mLogOut;
    protected TextView mUsername;

    protected Button mOpponentListButton;
    //protected TextView mDraftTimer;
    protected ParseUser mCurrentUser;
    protected String mLeagueName;
    protected List<ParseObject> mParsePlayers;
    private PlayerAdapter mAdapter;
    private TextView mLeaderBoardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                    mCurrentUser = ParseUser.getCurrentUser();
                    if (mCurrentUser == null) {
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Log.i(TAG, mCurrentUser.getUsername());
                    }

        if(mCurrentUser.getNumber("totalScore") == null){
            mCurrentUser.put("totalScore", 0);
        }
        if(mCurrentUser.getNumber("lastScore") == null){
            mCurrentUser.put("lastScore", 0);
        }









        mLeagueName = mCurrentUser.getString("leagueName");





       /* mDraftTimer = (TextView) findViewById(R.id.draftTimer);
        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                mDraftTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mDraftTimer.setText("done!");
            }
        }.start();*/


                    mUsername = (TextView) findViewById(R.id.username);
                    mUsername.setText(mCurrentUser.getUsername());


                    mPlayerListButton = (Button) findViewById(R.id.selectPlayerButton);
                    mLogOut = (TextView) findViewById(R.id.logOutTextView);
                   /* mLeaderBoardButton = (TextView) findViewById(R.id.leaderBoard);
        mLeaderBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeaderBoardActivity.class);
                startActivity(intent);
            }
        });

*/


                    mPlayerListButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Date date = new Date();
                            dateFormat.format(date);
                            Log.v("thecurrentdate",  dateFormat.format(date));


                            ParseQuery<ParseObject> query = ParseQuery.getQuery("draftedPlayer");
                            query.whereEqualTo("LEAGUE_NAME", mLeagueName);
                            query.whereEqualTo("USERNAME", mCurrentUser.getUsername());

                            query.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> list, ParseException e) {
                                    if (e == null) {
                                        if (list.size() < 1) {
                                            Intent intent = new Intent(MainActivity.this, DraftActivity.class);
                                            intent.putExtra("LEAGUE_NAME", mLeagueName);
                                            startActivity(intent);
                                        } else {
                                            ParseObject parsePlayer = list.get(0);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("CALLING_ACTIVITY", "MAIN_ACTIVITY");
                                            bundle.putString("PLAYER_NAME", parsePlayer.getString("PLAYER_NAME"));
                                            bundle.putString("PLAYER_VALUE", parsePlayer.getString("PLAYER_VALUE"));
                                            bundle.putString("PLAYER_CAP", parsePlayer.getString("PLAYER_CAP"));
                                            bundle.putString("PLAYER_POINTS", parsePlayer.getString("PLAYER_POINTS"));
                                            bundle.putString("PLAYER_REBOUNDS", parsePlayer.getString("PLAYER_REBOUNDS"));
                                            bundle.putString("PLAYER_ASSISTS", parsePlayer.getString("PLAYER_ASSISTS"));
                                            bundle.putString("PLAYER_BLOCKS", parsePlayer.getString("PLAYER_BLOCKS"));
                                            bundle.putString("PLAYER_STEALS", parsePlayer.getString("PLAYER_STEALS"));
                                            bundle.putString("LEAGUE_NAME", mLeagueName);


                                            Intent intent = new Intent(MainActivity.this, MatchUpActivity.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);


                                        }
                                    }



                                }
                            });


                        }
                    });
                    mLogOut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ParseUser.logOut();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });
                }


            }
