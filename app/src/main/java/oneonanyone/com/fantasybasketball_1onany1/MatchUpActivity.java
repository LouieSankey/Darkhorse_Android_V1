package oneonanyone.com.fantasybasketball_1onany1;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
//import android.support.annotation.NonNull;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.ParseUser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import oneonanyone.com.fantasybasketball_1onany1.DataModel.Player;
import oneonanyone.com.fantasybasketball_1onany1.DataModel.Team;

public class MatchUpActivity extends Activity implements OpponentFragment.OnFragmentInteractionListener,
        OpponentListFragment.OnFragmentInteractionListener
{

    private TextView mPlayerName;
    private TextView mPlayerValue;
    private TextView mPlayerRemainingValue;
    private TextView mPoints;
    private TextView mRebounds;
    private TextView mAssists;
    private TextView mSteals;
    private TextView mBlocks;
    private TextView mSelectedPlayerVs;
    private TextView mUsername;

    private Player mPlayer;
    private Player mPlayerAlternate;
    private Player mOpponentPlayer;
    private Player mOpponentPlayerAlternate;

    private ProgressBar mSelectedPlayerSpinner;
    private ImageView mSelectedPlayerImageView;
    private DecimalFormat mFormatter;
    private Double mRemainingValue;
    private ImageView mCheck;
    private String mSelectedPlayer;
    private Bundle mBundle;
    private boolean mCheckMarkVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_up);
        ParseUser currentUser = ParseUser.getCurrentUser();

        Bundle extras = getIntent().getExtras();
        String callingActivity = extras.getString("CALLING_ACTIVITY");
        String leagueName = extras.getString("LEAGUE_NAME");
        String playerValue = extras.getString("PLAYER_VALUE");
        mRemainingValue = Double.parseDouble(extras.getString("PLAYER_CAP"));
        mSelectedPlayer =  extras.getString("PLAYER_NAME");

        mUsername = (TextView) findViewById(R.id.username);
        mPlayerName = (TextView) findViewById(R.id.playerNameTextView);
        mPlayerValue = (TextView) findViewById(R.id.playerValueTextView);
        mPlayerRemainingValue = (TextView) findViewById(R.id.remainingCapTextView);
        mPoints = (TextView) findViewById(R.id.pointsTextView);
        mRebounds = (TextView) findViewById(R.id.reboundsTextView);
        mAssists = (TextView) findViewById(R.id.assistsTextView);
        mSteals = (TextView) findViewById(R.id.stealsTextView);
        mBlocks = (TextView) findViewById(R.id.blocksTextView);
        mSelectedPlayerSpinner = (ProgressBar) findViewById(R.id.selectedPlayerSpinner);
        mSelectedPlayerVs = (TextView) findViewById(R.id.selectedPlayerVs);
        mCheck = (ImageView) findViewById(R.id.checkImageView);

        mCheck.setVisibility(View.INVISIBLE);

        if(callingActivity != null && callingActivity.equals("MAIN_ACTIVITY")){
            mCheck.setVisibility(View.VISIBLE);

        }

        mSelectedPlayerImageView =(ImageView) findViewById(R.id.selectedPlayerImageView);

        Picasso.with(MatchUpActivity.this).load(
                "http://d2cwpp38twqe55.cloudfront.net/images/images-002/players/"
                        + getPlayerUrlName(mSelectedPlayer)
                        + ".png"
        ).into(mSelectedPlayerImageView);


        mPlayer = new Player();
        mOpponentPlayer = new Player();
        mOpponentPlayerAlternate = new Player();
        mPlayerAlternate = new Player();

        mOpponentPlayerAlternate.setGameId(0);
        mOpponentPlayerAlternate.setOpponentId(0);
        mPlayerAlternate.setGameId(0);
        mPlayerAlternate.setOpponentId(0);

        mFormatter = new DecimalFormat("$#,###");

        mPlayerRemainingValue.setText(mFormatter.format(Math.round(mRemainingValue)));
        mUsername.setText(currentUser.getUsername());
        mPlayerName.setText(mSelectedPlayer);
        mPlayerValue.setText("Price: " + mFormatter.format(Double.parseDouble(playerValue)));


        String points = extras.getString("PLAYER_POINTS");
        String rebounds = extras.getString("PLAYER_REBOUNDS");
        String assists = extras.getString("PLAYER_ASSISTS");
        String steals = extras.getString("PLAYER_STEALS");
        String blocks = extras.getString("PLAYER_BLOCKS");

        mPoints.setText(points.substring(0, points.length()-1));
        mRebounds.setText( rebounds.substring(0, rebounds.length()-1));
        mAssists.setText( assists.substring(0, assists.length()-1));
        mSteals.setText(steals.substring(0, steals.length()-1));
        mBlocks.setText( blocks.substring(0, blocks.length()-1));

        queryProBasketballApi(mSelectedPlayer, 0, "players", null);

    }


    private void queryProBasketballApi(final String selectedPlayer, final Integer identifier, final String ApiUrlSuffix, String playerId) {

        String parseBySpaces = "[ ]+";
        String parseSelectedPlayerName[] = selectedPlayer.split(parseBySpaces);
        String playerFirstName = parseSelectedPlayerName[0];
        String playerLastName = parseSelectedPlayerName[1];
        String api_key = "iSXHqcobFZlEJaWvs5mnk9eQU0fOpKMP";

//before api call isNetworkAvailable() checks to see if network is available

        if(isNetworkAvailable()) {

//uses okhttp to make the api call. Uses POST method to pass along api_key with additional parameters
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = null;

            if(ApiUrlSuffix == "players") {

                formBody = new FormEncodingBuilder()
                        .add("api_key", api_key)
                        .add("first_name", playerFirstName)
                        .add("last_name", playerLastName)
                        .build();
            }else{
                formBody = new FormEncodingBuilder()
                        .add("api_key", api_key)
                        .add("player_id", playerId)
                        .build();
            }

                final Request request = new Request.Builder()
                        .url("https://probasketballapi.com/" + ApiUrlSuffix)
                        .post(formBody)
                        .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        Log.v("tag", jsonData);
                        if (response.isSuccessful()) {
                            mBundle = new Bundle();


                                if(ApiUrlSuffix.equals("players")) {
                                    mPlayer = parsePlayerJson(jsonData, 0, "player_id", null, null);
                                    mPlayer.getPlayerId();

                                    mBundle.putInt("SELECTED_PLAYER_ID", mPlayer.getPlayerId());
                                    Log.v("logPlayerid",  mPlayer.getPlayerId().toString());
                                    queryProBasketballApi(selectedPlayer, 0, "stats/players", mPlayer.getPlayerId().toString());
                                }
                                else if(ApiUrlSuffix.equals("stats/players")) {
                                    mPlayerAlternate = parsePlayerJson(jsonData, 0, null, "game_id", "opponent_id");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String teamAbbrev = getTeamAbbrev(loadJSONFromAsset(), mPlayerAlternate.getOpponentId());

                                            mSelectedPlayerVs.setText(getVS() + teamAbbrev);
                                            mSelectedPlayerSpinner.setVisibility(View.INVISIBLE);
                                            mPlayerAlternate.getGameId();
                                            mPlayerAlternate.getOpponentId();
                                            mBundle.putInt("SELECTED_PLAYER_GAME_ID", mPlayerAlternate.getGameId());

                                            Log.v("playerIdgame",   mPlayerAlternate.getGameId().toString());

                                            if (findViewById(R.id.fragment_container) != null) {
                                               /* if (savedInstanceState != null) {
                                                    return;
                                                }*/


                                                mBundle.putString("PLAYER_VS", mSelectedPlayerVs.getText().toString());
                                                mBundle.putBoolean("CHECK_VISIBLE", mCheckMarkVisible);

                                                FragmentManager fragmentManager = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                OpponentListFragment opponentListFragment = new OpponentListFragment();
                                                opponentListFragment.setArguments(mBundle);
                                                fragmentTransaction.add(R.id.fragment_container, opponentListFragment, "Opponent_List_Fragment");
                                                fragmentTransaction.commit();

                                            }
                                        }
                                    });
                                }
                        }
                    } catch (IOException e) {
                        Log.e("tag", "Exception caught: ", e);
                    }
                }
            });
        }

    }

    private String getPlayerUrlName(String selectedPlayer) {
        String parseBySpaces = "[ ]+";
        String parseSelectedPlayerName[] = selectedPlayer.split(parseBySpaces);
        String playerFirstName = parseSelectedPlayerName[0];
        String playerLastName = parseSelectedPlayerName[1];

        if(playerLastName.length() >= 5) {
            playerLastName = playerLastName.substring(0, 5);
        }

        playerFirstName = playerFirstName.substring(0,2);

        String playerKey = playerLastName + playerFirstName;
        playerKey = playerKey.toLowerCase();

        Log.v("tag name pre", playerKey);


        if(playerKey.equals( "matthwe")
                || playerKey.equals( "davisan")
                || playerKey.equals( "greenda")
                || playerKey.equals( "hillge")){
            playerKey = playerKey  + "02";
        }

        else {
            playerKey = playerKey + "01";
        }

        Log.v("tag name", playerKey);


        return  playerKey;

    }

    @NonNull
    private String getVS() {
        Random random = new Random();

        if(random.nextBoolean()){
            return "Home vs: ";
        }else{
            return "Away vs: ";
        }


    }


    public Player parsePlayerJson (String jsonData, Integer jsonObject, String firstApiKey, String secondApiKey, String thirdApiKey){
       Player newPlayer = new Player();

        try {
            JSONArray root = new JSONArray(jsonData);
            JSONObject playerObject = root.getJSONObject(jsonObject);

            if(firstApiKey != null) {

                newPlayer.setPlayerId(playerObject.getInt(firstApiKey));

            }else {

              JSONObject randomPlayerObject = root.getJSONObject(new Random().nextInt(root.length()));

                newPlayer.setGameId(randomPlayerObject.getInt(secondApiKey));
                newPlayer.setOpponentId(randomPlayerObject.getInt(thirdApiKey));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newPlayer;
    }

    //called as a conditional on queryProBasketballApi()

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =  manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }



private String getTeamAbbrev(String jsonData, Integer opponentTeamId) {
    Team[] teams;
    String opponentAbbrev = null;
    try {
        JSONArray root = new JSONArray(jsonData);
        teams = new Team[root.length()];

        for (int i = 0; i < root.length(); i++){

            JSONObject jsonTeam = root.getJSONObject(i);
            Team team = new Team();

            team.setTeamId(jsonTeam.getInt("team_id"));
            team.setCity(jsonTeam.getString("city"));
            team.setAbbrev(jsonTeam.getString("abbreviation"));
            team.setFullName(jsonTeam.getString("team_name"));



            teams[i] = team;

            if(team.getTeamId().equals(opponentTeamId)) {
                opponentAbbrev = team.getAbbrev();

            }



        }

    } catch (JSONException e) {
        e.printStackTrace();
    }
        return opponentAbbrev;
}


    public String loadJSONFromAsset() {
        String jsonData;
        try {
            InputStream is = getAssets().open("teams.json");
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


    @Override
    public void onBackPressed() {



        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 1) {
            super.onBackPressed();
            mPlayerRemainingValue.setText(mFormatter.format(Math.round(mRemainingValue)));
        } else{
            finish();
        }



        if (mCheckMarkVisible && count == 0) {
            Intent intent = new Intent(MatchUpActivity.this, MainActivity.class);
            startActivity(intent);
        }




    }



    @Override
    public void onFragmentInteraction(Double newRemainingValue) {
        mPlayerRemainingValue.setText(mFormatter.format(Math.round(newRemainingValue)));

        if(newRemainingValue < 0){

           mPlayerRemainingValue.setTextColor(Color.parseColor("#ffff4444"));
       }else{
            mPlayerRemainingValue.setTextColor(Color.parseColor("#4c69ff"));

        }

    }

    @Override
    public void onFragmentInteraction(Boolean checkMarkVisible) {
        if(checkMarkVisible) {
            mCheckMarkVisible = true;
            mCheck.setVisibility(View.VISIBLE);
        }



    }


   /* public void virtualOpponent(Integer opponentPlayerCap) {
         Integer vPtsBet;
         Integer vRebsBet;
         Integer vAstBet;
         Integer vStlBet;
         Integer vBlkBet;

        vPtsBet = 0;
        vRebsBet = 0;
        vAstBet = 0;
        vStlBet = 0;
        vBlkBet = 0;

        List<Integer> virtualOpp = new ArrayList<Integer>();

        virtualOpp.add(600);
        virtualOpp.add(1000);
        virtualOpp.add(1200);
        virtualOpp.add(3250);
        virtualOpp.add(3100);

        Collections.shuffle(virtualOpp);

        virtualOpp.remove(0);
        virtualOpp.remove(1);


        Random random = new Random();
        if(random.nextBoolean()){
            virtualOpp.remove(2);
        }






        Collections.sort(virtualOpp);




        while (opponentPlayerCap >= virtualOpp.get(0)) {




            if (virtualOpp.contains(1000) && opponentPlayerCap >= 1000) {
                vRebsBet = vRebsBet + 1;
                opponentPlayerCap = opponentPlayerCap - 1000;

                Log.v("tag bet rebs", ("- " + vRebsBet));


            }

            if (virtualOpp.contains(600) && opponentPlayerCap >= 600) {
                vPtsBet = vPtsBet + 1;
                opponentPlayerCap = opponentPlayerCap - 600;
                Log.v("tag bet pts", ("- " + vPtsBet));


            }

            if (virtualOpp.contains(3250) && opponentPlayerCap >= 3250 && vStlBet < 2) {
                vStlBet = vStlBet + 1;
                opponentPlayerCap = opponentPlayerCap - 3250;
                Log.v("tag bet stls", ("- " + vStlBet));


            }

            if (virtualOpp.contains(1200) && opponentPlayerCap >= 1200) {
                vAstBet = vAstBet + 1;
                opponentPlayerCap = opponentPlayerCap - 1200;
                Log.v("tag bet ast", ("- " + vAstBet));


            }

            if (virtualOpp.contains(3100) && opponentPlayerCap >= 3100 && vBlkBet < 2) {
                vBlkBet = vBlkBet + 1;
                opponentPlayerCap = opponentPlayerCap - 3100;
                Log.v("tag bet blk", ("- " + vBlkBet));

            }



        }

    }*/


}
