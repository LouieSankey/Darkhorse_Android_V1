package oneonanyone.com.fantasybasketball_1onany1;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import oneonanyone.com.fantasybasketball_1onany1.DataModel.Player;

public class ResultsActivity extends Activity {

    private TextView playerPts;
    private TextView playerRebs;
    private TextView playerAst;
    private TextView playerBlks;
    private TextView playerStls;

    private TextView opponentPts;
    private TextView opponentRebs;
    private TextView opponentAst;
    private TextView opponentStls;
    private TextView opponentBlks;

    private TextView playerPtsBet;
    private TextView playerRebsBet;
    private TextView playerStlsBet;
    private TextView playerBlksBet;
    private TextView playerAstBet;

    private TextView oppPtsBet;
    private TextView oppRebsBet;
    private TextView oppAstBet;
    private TextView oppStlsBet;
    private TextView oppBlksBet;

    private TextView playerPtsEquals;
    private TextView playerRebsEquals;
    private TextView playerAstEquals;
    private TextView playerStlsEquals;
    private TextView playerBlksEquals;

    private TextView oppPtsEquals;
    private TextView oppRebsEquals;
    private TextView oppAstEquals;
    private TextView oppStlsEquals;
    private TextView oppBlksEquals;

    private String mPtPurchased;
    private String mRebPurchased;
    private String mAstsPurchased;
    private String mStlsPurchased;
    private String mBlksPurchased;

    private TextView mPtsWinLose;
    private TextView mRebsWinLose;
    private TextView mAstWinLose;
    private TextView mStlsWinLose;
    private TextView mBlksWinLose;
    private TextView mWinOrLoseBottomView;


    private String ptPurchased;
    private String rebPurchased;
    private String astPurchased;
    private String blkPurchased;
    private String stlPurchased;

    private String vptsbet;
    private String vrebsbet;
    private String vastbet;
    private String vstlsbet;
    private String vblkbet;


    private ParseObject mParsePlayer;
    private ParseObject mParseOpponent;

    private Player mSelectedPlayer;
    private Player mOpponentPlayer;

    private Integer mApiGameId;
    private Integer mApiPlayerId;
    private Integer mOpponentApiGameId;
    private Integer mOpponentApiId;

    private Button mResultsButton;

    private  ParseUser mCurrentUser;
    private String mStatsObjectId;

    private String mOpponentName;
    private String mPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_up_results);

        mSelectedPlayer = new Player();
        mOpponentPlayer = new Player();

         mCurrentUser = ParseUser.getCurrentUser();



        TextView playerUsername = (TextView) findViewById(R.id.username);
        TextView opponentUsername = (TextView) findViewById(R.id.oppUsername);
        TextView playerHeader = (TextView) findViewById(R.id.playerHeader);
        TextView opponentHeader = (TextView) findViewById(R.id.opponentHeader);
        final TextView mResultsTextView = (TextView) findViewById(R.id.resultsTextView);
        TextView displayLastName = (TextView) findViewById(R.id.displayLastName);
        TextView displayOpponentLastName = (TextView) findViewById(R.id.displayOpponentLastName);

        TextView vsHeader = (TextView) findViewById(R.id.vsHeader);
        TextView oppVsHeader = (TextView) findViewById(R.id.oppVsHeader);


        playerPts = (TextView) findViewById(R.id.selectedPlayerPts);
        playerRebs = (TextView) findViewById(R.id.selectedPlayerRebs);
        playerStls = (TextView) findViewById(R.id.selectedPlayerStls);
        playerAst = (TextView) findViewById(R.id.selectedPlayerAss);
        playerBlks = (TextView) findViewById(R.id.selectedPlayerBlks);

        opponentPts = (TextView) findViewById(R.id.opponentPts);
        opponentRebs = (TextView) findViewById(R.id.opponentRebs);
        opponentAst =(TextView) findViewById(R.id.opponentAsts);
        opponentStls = (TextView) findViewById(R.id.opponentStls);
        opponentBlks = (TextView) findViewById(R.id.opponentBlks);

        playerPtsBet = (TextView) findViewById(R.id.playerPtsBet);
        playerRebsBet = (TextView) findViewById(R.id.playerRebsBet);
        playerAstBet = (TextView) findViewById(R.id.playerAstBet);
        playerStlsBet = (TextView) findViewById(R.id.playerStlsBet);
        playerBlksBet = (TextView) findViewById(R.id.playerBlksBet);

        oppPtsBet = (TextView) findViewById(R.id.oppPtsBet);
        oppRebsBet = (TextView) findViewById(R.id.oppRebsBet);
        oppAstBet = (TextView) findViewById(R.id.oppAstBet);
        oppStlsBet = (TextView) findViewById(R.id.oppStlsBet);
        oppBlksBet = (TextView) findViewById(R.id.oppBlksBet);

        playerPtsEquals  = (TextView) findViewById(R.id.playerPtsEquals);
        playerRebsEquals = (TextView) findViewById(R.id.playerRebsEquals);
        playerAstEquals = (TextView) findViewById(R.id.playerAstEquals);
        playerStlsEquals = (TextView) findViewById(R.id.playerStlsEquals);
        playerBlksEquals = (TextView) findViewById(R.id.playerBlksEquals);

        oppPtsEquals = (TextView) findViewById(R.id.oppPtsEquals);
        oppRebsEquals = (TextView) findViewById(R.id.oppRebsEquals);
        oppAstEquals = (TextView) findViewById(R.id.oppAstEquals);
        oppStlsEquals = (TextView) findViewById(R.id.oppStlsEquals);
        oppBlksEquals = (TextView) findViewById(R.id.oppBlksEquals);

        mPtsWinLose = (TextView) findViewById(R.id.ptsWinLose);
        mRebsWinLose = (TextView) findViewById(R.id.rebsWinLose);
        mStlsWinLose = (TextView) findViewById(R.id.stlsWinLose);
        mAstWinLose = (TextView) findViewById(R.id.astWinLose);
        mBlksWinLose = (TextView) findViewById(R.id.blksWinLose);

        mWinOrLoseBottomView = (TextView) findViewById(R.id.winOrLoseBottomView);
        mResultsButton = (Button) findViewById(R.id.resultsButton);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);



        ParseUser currentUser = ParseUser.getCurrentUser();
        String parseUsername = currentUser.getUsername();

        Bundle extras = getIntent().getExtras();
        String leagueName = extras.getString("LEAGUE_NAME");
        String oppUsername = extras.getString("USERNAME");
        mPlayerName = extras.getString("PLAYER_NAME");
        mOpponentName = extras.getString("OPPONENT_NAME");
        String playerVs = extras.getString("PLAYER_VS");
        String opponentVs = extras.getString("OPPONENT_VS");
        String intent = extras.getString("INTENT");



        //Will use for when querying probasketballapi




        playerUsername.setText(parseUsername);
        opponentUsername.setText(oppUsername);
        playerHeader.setText(mPlayerName);
        opponentHeader.setText(mOpponentName);
        vsHeader.setText(playerVs);
        oppVsHeader.setText(opponentVs);

        //mResultsTextView.setVisibility(View.INVISIBLE);



        // sets up a check to make sure the api call is finished before trying to set the values.




        //used to display player and opponents last name in the score summary
        String parseBySpaces = "[ ]+";
        String playerNameArray[] = mPlayerName.split(parseBySpaces);
        String opponentNameArray[] = mOpponentName.split(parseBySpaces);

        displayLastName.setText(playerNameArray[1].toUpperCase());
        displayOpponentLastName.setText(opponentNameArray[1].toUpperCase());


        ParseQuery<ParseObject> playerIdQuery = ParseQuery.getQuery("draftedPlayer");
        playerIdQuery.whereEqualTo("LEAGUE_NAME", leagueName);
        playerIdQuery.whereEqualTo("USERNAME", parseUsername);
        playerIdQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                ParseObject mParsePlayerIds = list.get(0);

                Number playerGameId = mParsePlayerIds.getNumber("API_GAME_ID");
                Number playerId = mParsePlayerIds.getNumber("API_PLAYER_ID");
                mApiGameId = playerGameId.intValue();
                mApiPlayerId = playerId.intValue();
                getPlayerStats(mApiPlayerId.toString(), mApiGameId.toString(), 0);


            }
        });

        ParseQuery<ParseObject> opponentIdQuery = ParseQuery.getQuery("draftedPlayer");
        opponentIdQuery.whereEqualTo("LEAGUE_NAME", leagueName);
        opponentIdQuery.whereEqualTo("USERNAME", oppUsername);
        opponentIdQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
               // if (list != null && list.size() > 0) {

                    ParseObject mParseOpponentIds = list.get(0);

                    Number opponentGameId = mParseOpponentIds.getNumber("API_GAME_ID");
                    Number opponentId = mParseOpponentIds.getNumber("API_PLAYER_ID");
                    mOpponentApiGameId = opponentGameId.intValue();
                    mOpponentApiId = opponentId.intValue();
                    getPlayerStats(mOpponentApiId.toString(), mOpponentApiGameId.toString(), 1);
               // }

            }
        });


        playerPtsBet.setText("");
        oppPtsBet.setText("");


        if(intent != null && intent.equals("OPPONENT_LIST")) {
            playerPtsBet.setText("- " + extras.getString("PT_PURCHASED"));
            playerRebsBet.setText("- " + extras.getString("REB_PURCHASED"));
            playerAstBet.setText("- " + extras.getString("ASS_PURCHASED"));
            playerStlsBet.setText("- " + extras.getString("STL_PURCHASED"));
            playerBlksBet.setText("- " + extras.getString("BLK_PURCHASED"));
        }




            ParseQuery<ParseObject> query = ParseQuery.getQuery("statsPurchased");
            query.whereEqualTo("OPP_USERNAME", oppUsername);
            query.whereEqualTo("LEAGUE_NAME", leagueName);
            query.whereEqualTo("USERNAME", parseUsername);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> playerList, ParseException e) {
                    if (playerList != null && playerList.size() > 0) {
                        progressBar.setVisibility(View.INVISIBLE);
                        mResultsTextView.setText("GAME IN PROGRESS...");

                        mParsePlayer = playerList.get(0);

                        playerPtsBet.setText("- " + mParsePlayer.getString("PTS_PURCH"));
                        playerRebsBet.setText("- " + mParsePlayer.getString("REBS_PURCH"));
                        playerAstBet.setText("- " + mParsePlayer.getString("ASTS_PURCH"));
                        playerStlsBet.setText("- " + mParsePlayer.getString("STLS_PURCH"));
                        playerBlksBet.setText("- " + mParsePlayer.getString("BLKS_PURCH"));


                        mStatsObjectId = mParsePlayer.getObjectId();
                        Log.v("statsObjectId", mStatsObjectId);








                    }else{

                        Log.v("something", "outside opp");
                    }


                }
            });







        ParseQuery<ParseObject> opponentQuery = ParseQuery.getQuery("statsPurchased");
        opponentQuery.whereEqualTo("LEAGUE_NAME", leagueName);
        opponentQuery.whereEqualTo("USERNAME", oppUsername);
        opponentQuery.whereEqualTo("OPP_USERNAME", parseUsername);
        opponentQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> playerList, ParseException e) {
                if (playerList != null && playerList.size() > 0) {

                    mParseOpponent = playerList.get(0);

                    oppPtsBet.setText("- " + mParseOpponent.getString("PTS_PURCH"));
                    oppRebsBet.setText("- " + mParseOpponent.getString("REBS_PURCH"));
                    oppAstBet.setText("- " + mParseOpponent.getString("ASTS_PURCH"));
                    oppStlsBet.setText("- " + mParseOpponent.getString("STLS_PURCH"));
                    oppBlksBet.setText("- " + mParseOpponent.getString("BLKS_PURCH"));
                    Log.v("something", "Inside user");

                }else{

                    Log.v("something", "outside USER");
                }

            }
        });




       mResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerPtsBet.getText().equals("")){
                    Toast.makeText(ResultsActivity.this, "Try again in one second, still communicating with the data provider", Toast.LENGTH_LONG).show();


                }else if(oppPtsBet.getText().equals("")){
                    Toast.makeText(ResultsActivity.this, "Your opponent has until tip off to finish setting matches", Toast.LENGTH_LONG).show();

                }

                else {

                    mOpponentPlayer.setRebounds(mOpponentPlayer.getDefensiveRebounds() + mOpponentPlayer.getOffensiveRebounds());

                    playerPts.setText(mSelectedPlayer.getPoints().toString());
                    Integer totalPlayerRebounds = mSelectedPlayer.getDefensiveRebounds() + mSelectedPlayer.getOffensiveRebounds();
                    playerRebs.setText(totalPlayerRebounds.toString());
                    playerAst.setText(mSelectedPlayer.getAssists().toString());
                    playerStls.setText(mSelectedPlayer.getSteals().toString());
                    playerBlks.setText(mSelectedPlayer.getBlocks().toString());

                    Integer opponentPtsTotal = mOpponentPlayer.getPoints() - Integer.parseInt(mParsePlayer.getString("PTS_PURCH"));
                    opponentPts.setText(mOpponentPlayer.getPoints().toString());
                    oppPtsEquals.setText("" + opponentPtsTotal);
                    playerPtsBet.setText(Html.fromHtml("- " + mParsePlayer.getString("PTS_PURCH")), TextView.BufferType.SPANNABLE);


                    Integer opponentRebsTotal = mOpponentPlayer.getRebounds() - Integer.parseInt(mParsePlayer.getString("REBS_PURCH"));
                    opponentRebs.setText(mOpponentPlayer.getRebounds().toString());
                    oppRebsEquals.setText("" + opponentRebsTotal);
                    playerRebsBet.setText(Html.fromHtml("- " + mParsePlayer.getString("REBS_PURCH")), TextView.BufferType.SPANNABLE);


                    Integer opponentAstTotal = mOpponentPlayer.getAssists() - Integer.parseInt(mParsePlayer.getString("ASTS_PURCH"));
                    opponentAst.setText(mOpponentPlayer.getAssists().toString());
                    oppAstEquals.setText("" + opponentAstTotal);
                    playerAstBet.setText(Html.fromHtml("- " + mParsePlayer.getString("ASTS_PURCH")), TextView.BufferType.SPANNABLE);


                    Integer opponentStlsTotal = mOpponentPlayer.getSteals() - Integer.parseInt(mParsePlayer.getString("STLS_PURCH"));
                    opponentStls.setText(mOpponentPlayer.getSteals().toString());
                    oppStlsEquals.setText("" + opponentStlsTotal);
                    playerStlsBet.setText(Html.fromHtml("- " + mParsePlayer.getString("STLS_PURCH")), TextView.BufferType.SPANNABLE);

                    Integer opponentBlksTotal = mOpponentPlayer.getBlocks() - Integer.parseInt(mParseOpponent.getString("BLKS_PURCH"));
                    opponentBlks.setText(mOpponentPlayer.getBlocks().toString());
                    oppBlksEquals.setText("" + opponentBlksTotal);
                    playerBlksBet.setText(Html.fromHtml("- " + mParseOpponent.getString("BLKS_PURCH")), TextView.BufferType.SPANNABLE);




                    Integer playerPtsTotal = mSelectedPlayer.getPoints() - Integer.parseInt(mParseOpponent.getString("PTS_PURCH"));

                    playerPtsEquals.setText("" + playerPtsTotal);


                    Integer playerRebsTotal = totalPlayerRebounds - Integer.parseInt(mParseOpponent.getString("REBS_PURCH"));

                    playerRebsEquals.setText("" + playerRebsTotal);


                    Integer playerAstTotal = mSelectedPlayer.getAssists() - Integer.parseInt(mParseOpponent.getString("ASTS_PURCH"));
                    playerAstEquals.setText("" + playerAstTotal);

                    Integer playerStealsTotal = mSelectedPlayer.getSteals() - Integer.parseInt(mParseOpponent.getString("STLS_PURCH"));
                    playerStlsEquals.setText("" + playerStealsTotal);

                    Integer playerBlocksTotal = mSelectedPlayer.getBlocks() - Integer.parseInt(mParseOpponent.getString("BLKS_PURCH"));
                    playerBlksEquals.setText("" + playerBlocksTotal);

//Html.fromHtml("<font color=''>" +  + "</font>"), TextView.BufferType.SPANNABLE


                    Integer winCount = 0;
                    Integer loseCount = 0;

                    if (opponentPtsTotal.equals(playerPtsTotal)) {
                        mPtsWinLose.setText(Html.fromHtml("<font color='grey'>" + "Tie" + "</font>"), TextView.BufferType.SPANNABLE);


                    } else if (opponentPtsTotal < playerPtsTotal) {
                        mPtsWinLose.setText(Html.fromHtml("<font color='#4997ff'>" + "Win" + "</font>"), TextView.BufferType.SPANNABLE);
                        winCount++;
                    } else {
                        mPtsWinLose.setText(Html.fromHtml("<font color='@android:color/holo_red_light'>" + "Lose" + "</font>"), TextView.BufferType.SPANNABLE);
                        loseCount++;
                    }

                    if (opponentRebsTotal.equals(playerRebsTotal)) {
                        mRebsWinLose.setText(Html.fromHtml("<font color='grey'>" + "Tie" + "</font>"), TextView.BufferType.SPANNABLE);

                    } else if (opponentRebsTotal < playerRebsTotal) {
                        mRebsWinLose.setText(Html.fromHtml("<font color='#4997ff'>" + "Win" + "</font>"), TextView.BufferType.SPANNABLE);
                        winCount++;
                    } else {
                        mRebsWinLose.setText(Html.fromHtml("<font color='@android:color/holo_red_light'>" + "Lose" + "</font>"), TextView.BufferType.SPANNABLE);
                        loseCount++;
                    }

                    if (opponentAstTotal.equals(playerAstTotal)) {
                        mAstWinLose.setText(Html.fromHtml("<font color='grey'>" + "Tie" + "</font>"), TextView.BufferType.SPANNABLE);

                    } else if (opponentAstTotal < playerAstTotal) {
                        mAstWinLose.setText(Html.fromHtml("<font color='#4997ff'>" + "Win" + "</font>"), TextView.BufferType.SPANNABLE);
                        winCount++;
                    } else {
                        mAstWinLose.setText(Html.fromHtml("<font color='@android:color/holo_red_light'>" + "Lose" + "</font>"), TextView.BufferType.SPANNABLE);
                        loseCount++;
                    }

                    if (opponentStlsTotal.equals(playerStealsTotal)) {
                        mStlsWinLose.setText(Html.fromHtml("<font color='grey'>" + "Tie" + "</font>"), TextView.BufferType.SPANNABLE);

                    } else if (opponentStlsTotal < playerStealsTotal) {
                        mStlsWinLose.setText(Html.fromHtml("<font color='#4997ff'>" + "Win" + "</font>"), TextView.BufferType.SPANNABLE);
                        winCount++;
                    } else {
                        mStlsWinLose.setText(Html.fromHtml("<font color='@android:color/holo_red_light'>" + "Lose" + "</font>"), TextView.BufferType.SPANNABLE);
                        loseCount++;
                    }

                    if (opponentBlksTotal.equals(playerBlocksTotal)) {
                        mBlksWinLose.setText(Html.fromHtml("<font color='grey'>" + "Tie" + "</font>"), TextView.BufferType.SPANNABLE);

                    } else if (opponentBlksTotal < playerBlocksTotal) {
                        mBlksWinLose.setText(Html.fromHtml("<font color='#4997ff'>" + "Win" + "</font>"), TextView.BufferType.SPANNABLE);
                        winCount++;
                    } else {
                        mBlksWinLose.setText(Html.fromHtml("<font color='@android:color/holo_red_light'>" + "Lose" + "</font>"), TextView.BufferType.SPANNABLE);
                        loseCount++;
                    }

                    ParseObject draftedPlayer = mCurrentUser.getParseObject("parent");
                    ArrayList<String> statsIds = (ArrayList<String>) draftedPlayer.get("statsIds");



                    if (winCount.equals(loseCount)) {
                        mWinOrLoseBottomView.setText(Html.fromHtml("<font color='grey'>" + "TIE + $0" + "</font>"), TextView.BufferType.SPANNABLE);
                    } else if (winCount > loseCount) {
                        mWinOrLoseBottomView.setText(Html.fromHtml("<font color='#4997ff'>" + "WIN +$20,000" + "</font>"), TextView.BufferType.SPANNABLE);


                        if(statsIds == null || !statsIds.contains(mStatsObjectId)){
                            Integer score = mCurrentUser.getNumber("totalScore").intValue();
                            score = score + 20000;
                            Integer wins = mCurrentUser.getNumber("totalWins").intValue();
                            wins++;
                            mCurrentUser.put("totalScore", score);
                            mCurrentUser.put("lastScore", score);
                            mCurrentUser.put("lastPlayer", mPlayerName);
                            mCurrentUser.put("totalWins", wins);
                            draftedPlayer.add("statsIds", mStatsObjectId);
                            mCurrentUser.saveInBackground();


                        }


                    } else {
                        mWinOrLoseBottomView.setText(Html.fromHtml("<font color='@android:color/holo_red_light'>" + "LOSE -$20,000" + "</font>"), TextView.BufferType.SPANNABLE);

                        if(statsIds == null || !statsIds.contains(mStatsObjectId)){

                            Integer score = mCurrentUser.getNumber("totalScore").intValue();
                            score = score - 20000;
                            Integer losses = mCurrentUser.getNumber("totalLosses").intValue();
                            losses++;
                            mCurrentUser.put("totalScore", score);
                            mCurrentUser.put("lastScore", score);
                            mCurrentUser.put("lastPlayer", mPlayerName);
                            mCurrentUser.put("totalLosses", losses);
                            draftedPlayer.add("statsIds", mStatsObjectId);
                            mCurrentUser.saveInBackground();

                        }

                    }

                    mResultsTextView.setText("RESULTS");


                }



            }
        });


    }

   /* public void onBackPressed()
    {

        Intent intent = new Intent(ResultsActivity.this, MatchUpActivity.class );
        startActivity(intent);
    }*/




    private void getPlayerStats(String playerId, String gameId, final Integer identifier) {

        String api_key = "iSXHqcobFZlEJaWvs5mnk9eQU0fOpKMP";



//before api call isNetworkAvailable() checks to see if network is available

        if(isNetworkAvailable()) {

//uses okhttp to make the api call. Uses POST method to pass along api_key with additional parameters

            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormEncodingBuilder()
                    .add("api_key", api_key)

                    .add("player_id", playerId)
                    .add("game_id", gameId)

                    .build();


            Request request = new Request.Builder()
                    .url("https://probasketballapi.com/stats/players")
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
                        if (response.isSuccessful()) {
                            String jsonData = response.body().string();
                            Log.v("jsonDatafromResponse", jsonData);
                            if(identifier == 0) {

                                mSelectedPlayer = parsePlayerStats(jsonData);







                              // Log.v("playerPoints",  mSelectedPlayer.getPoints().toString());

                            } else if(identifier == 1){
                                mOpponentPlayer = parsePlayerStats(jsonData);


                            }

                        } else{
                        }
                    } catch (IOException e) {
                        Log.e("tag", "Exception caught: ", e);
                    }
                }
            });
        }
    }

    //called as a conditional on getPlayerData()

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

    public Player parsePlayerStats (String jsonData){
        Player newPlayer = new Player();

        try {
            JSONArray root = new JSONArray(jsonData);
            JSONObject playerStats = root.getJSONObject(0);

            newPlayer.setPoints(playerStats.getInt("box_pts"));
            newPlayer.setDefensiveRebounds(playerStats.getInt("box_dreb"));
            newPlayer.setOffensiveRebounds(playerStats.getInt("box_oreb"));
            newPlayer.setAssists(playerStats.getInt("box_ast"));
            newPlayer.setBlocks(playerStats.getInt("box_blk"));
            newPlayer.setSteals(playerStats.getInt("box_stl"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newPlayer;
    }


}
