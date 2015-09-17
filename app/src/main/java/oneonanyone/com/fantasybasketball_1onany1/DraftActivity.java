package oneonanyone.com.fantasybasketball_1onany1;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import oneonanyone.com.fantasybasketball_1onany1.DataModel.Player;
import oneonanyone.com.fantasybasketball_1onany1.DataModel.PlayerAdapter;


public class DraftActivity extends ListActivity {

    public static final String TAG = DraftActivity.class.getSimpleName();

    private String mPlayerStatsJson;
    private TextView mUsername;
    private String mParseUsername;
    private String mParseUserObjectId;
    private String mParseUserSessionToken;
    private Button mViewLeaderboardButton;
    protected List<ParseObject> mDraftedPlayers;
    protected List<HashMap<String, String>> mUserDraftedPlayers;
    protected TextView mCountdownTimer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_player);




        mUsername = (TextView) findViewById(R.id.username);
        ParseUser currentUser = ParseUser.getCurrentUser();
        mParseUsername = currentUser.getUsername();

     mCountdownTimer = (TextView) findViewById(R.id.coundownTimer);

        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                mCountdownTimer.setText("Draft Timer: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mCountdownTimer.setText("done!");
            }
        }.start();




        mParseUserObjectId = currentUser.getObjectId();
        mParseUserSessionToken = currentUser.getSessionToken();

        mUsername.setText(mParseUsername);

        mViewLeaderboardButton = (Button) findViewById(R.id.viewLeaderboard);
        mViewLeaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DraftActivity.this, LeaderBoardActivity.class);
                startActivity(intent);
            }
        });
        ParseQuery<ParseObject> query = ParseQuery.getQuery("draftedPlayer");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> draftedPlayers, ParseException e) {
                if(e == null){
                    mDraftedPlayers = draftedPlayers;

                    mUserDraftedPlayers = new ArrayList<>();
                    for (int i = 0; i < mDraftedPlayers.size(); i++) {

                        ParseObject parsePlayer = mDraftedPlayers.get(i);

                        String playerName = parsePlayer.getString("PLAYER_NAME");
                        String username = parsePlayer.getString("USERNAME");

                        HashMap<String, String> userDraftedPlayer = new HashMap<>();
                        userDraftedPlayer.put(playerName, username);

                        mUserDraftedPlayers.add(userDraftedPlayer);


                    }

                    mPlayerStatsJson = loadJSONFromAsset();
                    PlayerAdapter adapter = new PlayerAdapter(DraftActivity.this, getPlayers(mPlayerStatsJson));
                    setListAdapter(adapter);


                }
            }
        });






    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);



        Player opponent = getPlayers(mPlayerStatsJson)[new Random().nextInt(getPlayers(mPlayerStatsJson).length)];
        String opponentName = opponent.getName();
        String opponentValue = opponent.getValue();
        String opponentCap = opponent.getCap();
        String opponentPoints = opponent.getPointsAverage();
        String opponentRebounds = opponent.getReboundsAverage();
        String opponentAssists = opponent.getAssistsAverage();
        String opponentSteals = opponent.getStealsAverage();
        String opponentBlocks = opponent.getBlocksAverage();

        String playerName = getPlayers(mPlayerStatsJson)[position].getName();
        String playerValue = getPlayers(mPlayerStatsJson)[position].getValue();
        String playerCap = getPlayers(mPlayerStatsJson)[position].getCap();
        String playerPoints = getPlayers(mPlayerStatsJson)[position].getPointsAverage();
        String playerRebounds = getPlayers(mPlayerStatsJson)[position].getReboundsAverage();
        String playerAssists = getPlayers(mPlayerStatsJson)[position].getAssistsAverage();
        String playerSteals = getPlayers(mPlayerStatsJson)[position].getStealsAverage();
        String playerBlocks = getPlayers(mPlayerStatsJson)[position].getBlocksAverage();
        String username =  getPlayers(mPlayerStatsJson)[position].getUsername();



        if(username == null) {


            Bundle bundle = getIntent().getExtras();
            String leagueName = bundle.getString("LEAGUE_NAME");

            Bundle extras = new Bundle();
            extras.putString("CALLING_ACTIVITY", "DRAFT_ACTIVITY");
            extras.putString("LEAGUE_NAME", leagueName);
            extras.putString("PLAYER_NAME", playerName);
            extras.putString("PLAYER_VALUE", playerValue);
            extras.putString("PLAYER_CAP", playerCap);
            extras.putString("PLAYER_POINTS", playerPoints);
            extras.putString("PLAYER_REBOUNDS", playerRebounds);
            extras.putString("PLAYER_ASSISTS", playerAssists);
            extras.putString("PLAYER_STEALS", playerSteals);
            extras.putString("PLAYER_BLOCKS", playerBlocks);

       /* ParseObject parsePlayer = new ParseObject("parsePlayer");
        parsePlayer.put("USERNAME_OBJECT_ID", mParseUserObjectId);
        parsePlayer.put("USERNAME_SESSION_TOKEN", mParseUserSessionToken);
        parsePlayer.put("USERNAME", mParseUsername);
        parsePlayer.put("PLAYER_NAME", playerName);
        parsePlayer.put("PLAYER_VALUE", playerValue);
        parsePlayer.put("PLAYER_CAP", playerCap);
        parsePlayer.put("PLAYER_POINTS", playerPoints);
        parsePlayer.put("PLAYER_REBOUNDS", playerRebounds);
        parsePlayer.put("PLAYER_ASSISTS", playerAssists);
        parsePlayer.put("PLAYER_STEALS", playerSteals);
        parsePlayer.put("PLAYER_BLOCKS", playerBlocks);
        parsePlayer.saveInBackground();*/

            extras.putString("OPPONENT_NAME", opponentName);
            extras.putString("OPPONENT_VALUE", opponentValue);
            extras.putString("OPPONENT_CAP", opponentCap);
            extras.putString("OPPONENT_POINTS", opponentPoints);
            extras.putString("OPPONENT_REBOUNDS", opponentRebounds);
            extras.putString("OPPONENT_ASSISTS", opponentAssists);
            extras.putString("OPPONENT_STEALS", opponentSteals);
            extras.putString("OPPONENT_BLOCKS", opponentBlocks);


            Intent intent = new Intent(this, MatchUpActivity.class);
            intent.putExtras(extras);
            startActivity(intent);
        }else{
            Toast.makeText(DraftActivity.this, "This player has already been drafted", Toast.LENGTH_LONG).show();

        }

    }

    private Player[] getPlayers(String jsonData) {
        Player[] players = null;
        try {
            JSONObject fullData = new JSONObject(jsonData);
            JSONArray arrayRoot = fullData.getJSONArray("Player");

              players = new Player[arrayRoot.length()];

            for (int i = 0; i < arrayRoot.length(); i++){

                JSONObject jsonPlayer = arrayRoot.getJSONObject(i);
                Player singlePlayer = new Player();

                String playerName = jsonPlayer.getString("Name");

                singlePlayer.setName(playerName);
                singlePlayer.setValue(jsonPlayer.getString("Value"));

                singlePlayer.setPointsAverage(jsonPlayer.getString("p/g"));
                singlePlayer.setReboundsAverage(jsonPlayer.getString("r/g"));
                singlePlayer.setAssistsAverage(jsonPlayer.getString("a/g"));
                singlePlayer.setStealsAverage(jsonPlayer.getString("s/g"));
                singlePlayer.setBlocksAverage(jsonPlayer.getString("b/g"));



                for (int ii = 0; ii < mUserDraftedPlayers.size(); ii++) {

                    HashMap<String, String> draftedPlayer = mUserDraftedPlayers.get(ii);
                    if(draftedPlayer.get(playerName) != null){
                        singlePlayer.setUsername(draftedPlayer.get(playerName));

                    }

                }

               /* if(jsonPlayer.getString("NAME").equals()){
                    singlePlayer.setUsername("Chode");
                }*/


                players[i] = singlePlayer;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return players;
    }

    public String loadJSONFromAsset() {
        String jsonData = null;
        try {
            InputStream is = getAssets().open("playerStats.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonData = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonData;
    }

}
