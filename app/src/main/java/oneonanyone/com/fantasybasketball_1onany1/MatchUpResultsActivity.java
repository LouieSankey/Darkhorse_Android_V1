package oneonanyone.com.fantasybasketball_1onany1;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import oneonanyone.com.fantasybasketball_1onany1.DataModel.Player;

public class MatchUpResultsActivity extends Activity {


    private TextView playerHeader;
    private TextView opponentHeader;
    private TextView displayLastName;
    private TextView displayOpponentLastName;
    private TextView playerPtsBet;
    private TextView playerRebsBet;
    private TextView playerStlsBet;
    private TextView playerBlksBet;
    private TextView playerAstBet;

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

    private TextView oppPtsEquals;
    private TextView oppRebsEquals;
    private TextView oppAstEquals;
    private TextView oppStlsEquals;
    private TextView oppBlksEquals;

    private TextView playerPtsEquals;
    private TextView playerRebsEquals;
    private TextView playerAstEquals;
    private TextView playerStlsEquals;
    private TextView playerBlksEquals;

    private TextView oppPtsBet;
    private TextView oppRebsBet;
    private TextView oppAstBet;
    private TextView oppStlsBet;
    private TextView oppBlksBet;




    private TextView mPtsWinLose;
    private TextView mRebsWinLose;
    private TextView mAstWinLose;
    private TextView mStlsWinLose;
    private TextView mBlksWinLose;

    private TextView mResultsTextView;

    private TextView mWinOrLoseBottomView;

    private TextView mUsername;


    private Player mSelectedPlayer;
    private Player mOpponentPlayer;


    private Button mResultsButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_up_results);



        Bundle extras = getIntent().getExtras();
        final String ptPurchased = extras.getString("PT_PURCHASED");
        final String rebPurchased = extras.getString("REB_PURCHASED");
        final String stlPurchased = extras.getString("STL_PURCHASED");
        final String blkPurchased = extras.getString("BLK_PURCHASED");
        final String astPurchased = extras.getString("ASS_PURCHASED");

        String playerName = extras.getString("PLAYER_NAME");
        String opponentName = extras.getString("OPPONENT_NAME");
        String playerVs = extras.getString(("SELECTED_PLAYER_VS"));
        String opponentPlayerVs = extras.getString("OPPONENT_PLAYER_VS");

        final String opponentCap = extras.getString("OPPONENT_CAP");

        Integer selectedPlayerId = extras.getInt("SELECTED_PLAYER_ID");
        Integer opponentPlayerId = extras.getInt("OPPONENT_PLAYER_ID");

        Integer selectedPlayersGameId = extras.getInt("SELECTED_PLAYER_GAME_ID");
        Integer opponentPlayersGameId = extras.getInt("OPPONENT_PLAYERS_GAME_ID");

        final String vptsbet = extras.getString("VPTSBET");
        final String vrebsbet = extras.getString("VREBSBET");
        final String vastbet = extras.getString("VASTBET");
        final String vstlsbet = extras.getString("VSTLSBET");
        final String vblkbet = extras.getString("VBLKBET");




        String parseBySpaces = "[ ]+";
        String playerNameArray[] = playerName.split(parseBySpaces);
        String opponentNameArray[] = opponentName.split(parseBySpaces);


        mUsername = (TextView) findViewById(R.id.username);


        displayLastName = (TextView) findViewById(R.id.displayLastName);
        displayOpponentLastName = (TextView) findViewById(R.id.displayOpponentLastName);

        displayLastName.setText(playerNameArray[1]);
        displayOpponentLastName.setText(opponentNameArray[1]);



        playerHeader = (TextView) findViewById(R.id.playerHeader);
        opponentHeader = (TextView) findViewById(R.id.opponentHeader);

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
        mResultsTextView = (TextView) findViewById(R.id.resultsTextView);

        mResultsTextView.setVisibility(View.INVISIBLE);

        mResultsButton = (Button) findViewById(R.id.resultsButton);



        ParseUser currentUser = ParseUser.getCurrentUser();
        mUsername.setText(currentUser.getUsername());





        playerHeader.setText(Html.fromHtml(playerName + " <small><i>" + playerVs + "</i></small>"),TextView.BufferType.SPANNABLE );
        opponentHeader.setText(Html.fromHtml(opponentName + " <small><i>" + opponentPlayerVs + "</i></small>"),TextView.BufferType.SPANNABLE );



            playerPtsBet.setText(Html.fromHtml("<font color=''> - " + ptPurchased + "</font>"), TextView.BufferType.SPANNABLE);
            playerRebsBet.setText(Html.fromHtml("<font color=''> - " + rebPurchased + "</font>"), TextView.BufferType.SPANNABLE);
            playerStlsBet.setText(Html.fromHtml("<font color=''> - " + stlPurchased + "</font>"), TextView.BufferType.SPANNABLE);
            playerAstBet.setText(Html.fromHtml("<font color=''> - " + astPurchased + "</font>"), TextView.BufferType.SPANNABLE);

            playerBlksBet.setText(Html.fromHtml("<font color=''> - " + blkPurchased + "</font>"), TextView.BufferType.SPANNABLE);



        oppPtsBet.setText(Html.fromHtml("<font color=''> - " + vptsbet + "</font>"), TextView.BufferType.SPANNABLE);

        oppRebsBet.setText(Html.fromHtml("<font color=''> - " + vrebsbet + "</font>"), TextView.BufferType.SPANNABLE);

        oppAstBet.setText(Html.fromHtml("<font color=''> - " + vastbet + "</font>"), TextView.BufferType.SPANNABLE);

        oppStlsBet.setText(Html.fromHtml("<font color=''> - " + vstlsbet + "</font>"), TextView.BufferType.SPANNABLE);

        oppBlksBet.setText(Html.fromHtml("<font color=''> - " + vblkbet + "</font>"), TextView.BufferType.SPANNABLE);


        mSelectedPlayer = new Player();

        mSelectedPlayer.setSteals(-1);

        mOpponentPlayer = new Player();

        mOpponentPlayer.setSteals(-1);


        //virtualOpponent(Integer.parseInt(opponentCap));

        getPlayerStats(selectedPlayerId.toString(), selectedPlayersGameId.toString(), 0);
        getPlayerStats(opponentPlayerId.toString(), opponentPlayersGameId.toString(), 1);








        mResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mSelectedPlayer.getSteals() == -1 || mOpponentPlayer.getSteals() == -1){
                    Toast.makeText(MatchUpResultsActivity.this, "Try again in one second, still communicating with the data provider", Toast.LENGTH_LONG).show();


                }else {

//sets selected and opposing players game stats


                    playerPts.setText(mSelectedPlayer.getPoints().toString());
                    Integer totalPlayerRebounds = mSelectedPlayer.getDefensiveRebounds() + mSelectedPlayer.getOffensiveRebounds();
                    playerRebs.setText(totalPlayerRebounds.toString());
                    playerAst.setText(mSelectedPlayer.getAssists().toString());
                    playerStls.setText(mSelectedPlayer.getSteals().toString());
                    playerBlks.setText(mSelectedPlayer.getBlocks().toString());

                    Integer opponentPtsTotal = mOpponentPlayer.getPoints() - Integer.parseInt(ptPurchased);
                    opponentPts.setText(mOpponentPlayer.getPoints().toString());
                    oppPtsEquals.setText("= " + opponentPtsTotal);
                    playerPtsBet.setText(Html.fromHtml("- " + ptPurchased), TextView.BufferType.SPANNABLE);


                    Integer offAndDefRebounds = (mOpponentPlayer.getDefensiveRebounds() + mOpponentPlayer.getOffensiveRebounds());
                    Integer opponentRebsTotal = offAndDefRebounds - Integer.parseInt(rebPurchased);
                    opponentRebs.setText(offAndDefRebounds.toString());
                    oppRebsEquals.setText("= " + opponentRebsTotal);
                    playerRebsBet.setText(Html.fromHtml("- " + rebPurchased), TextView.BufferType.SPANNABLE);



                    Integer opponentAstTotal = mOpponentPlayer.getAssists() - Integer.parseInt(astPurchased);
                    opponentAst.setText(mOpponentPlayer.getAssists().toString());
                    oppAstEquals.setText("= " + opponentAstTotal);
                    playerAstBet.setText(Html.fromHtml("- " + astPurchased), TextView.BufferType.SPANNABLE);


                    Integer opponentStlsTotal = mOpponentPlayer.getSteals() - Integer.parseInt(stlPurchased);
                    opponentStls.setText(mOpponentPlayer.getSteals().toString());
                    oppStlsEquals.setText("= " + opponentStlsTotal);
                    playerStlsBet.setText(Html.fromHtml("- " + stlPurchased), TextView.BufferType.SPANNABLE);

                    Integer opponentBlksTotal = mOpponentPlayer.getBlocks() - Integer.parseInt(blkPurchased);
                    opponentBlks.setText(mOpponentPlayer.getBlocks().toString());
                    oppBlksEquals.setText("= " + opponentBlksTotal);
                    playerBlksBet.setText(Html.fromHtml("- " + blkPurchased), TextView.BufferType.SPANNABLE);




                   Integer playerPtsTotal = mSelectedPlayer.getPoints() - Integer.parseInt(vptsbet);

                        playerPtsEquals.setText("= " + playerPtsTotal);


                   Integer playerRebsTotal = totalPlayerRebounds - Integer.parseInt(vrebsbet);

                        playerRebsEquals.setText("= " + playerRebsTotal);


                    Integer playerAstTotal = mSelectedPlayer.getAssists() - Integer.parseInt(vastbet);
                    playerAstEquals.setText("= " + playerAstTotal);

                    Integer playerStealsTotal = mSelectedPlayer.getSteals() - Integer.parseInt(vstlsbet);
                      playerStlsEquals.setText("= " + playerStealsTotal);

                    Integer playerBlocksTotal = mSelectedPlayer.getBlocks() - Integer.parseInt(vblkbet);
                    playerBlksEquals.setText("= " + playerBlocksTotal);

//Html.fromHtml("<font color=''>" +  + "</font>"), TextView.BufferType.SPANNABLE


                    Integer winCount = 0;
                    Integer loseCount = 0;

                    if (opponentPtsTotal.equals(playerPtsTotal)) {
                        mPtsWinLose.setText(Html.fromHtml("<font color='grey'>" +  "Tie"+ "</font>"), TextView.BufferType.SPANNABLE);

                    } else if (opponentPtsTotal < playerPtsTotal) {
                        mPtsWinLose.setText(Html.fromHtml("<font color='#4c69ff'>" +  "Win" + "</font>"), TextView.BufferType.SPANNABLE);
                        winCount++;
                    } else {
                        mPtsWinLose.setText(Html.fromHtml("<font color='@android:color/holo_red_light'>" + "Lose"  + "</font>"), TextView.BufferType.SPANNABLE);
                        loseCount++;
                    }

                    if (opponentRebsTotal.equals(playerRebsTotal)) {
                        mRebsWinLose.setText(Html.fromHtml("<font color='grey'>" +  "Tie"+ "</font>"), TextView.BufferType.SPANNABLE);

                    } else if (opponentRebsTotal < playerRebsTotal) {
                        mRebsWinLose.setText(Html.fromHtml("<font color='#4c69ff'>" +  "Win" + "</font>"), TextView.BufferType.SPANNABLE);
                        winCount++;
                    } else {
                        mRebsWinLose.setText(Html.fromHtml("<font color='@android:color/holo_red_light'>" + "Lose"  + "</font>"), TextView.BufferType.SPANNABLE);
                        loseCount++;
                    }

                    if (opponentAstTotal.equals(playerAstTotal)) {
                        mAstWinLose.setText(Html.fromHtml("<font color='grey'>" +  "Tie"+ "</font>"), TextView.BufferType.SPANNABLE);

                    } else if (opponentAstTotal < playerAstTotal) {
                        mAstWinLose.setText(Html.fromHtml("<font color='#4c69ff'>" +  "Win" + "</font>"), TextView.BufferType.SPANNABLE);
                        winCount++;
                    } else {
                        mAstWinLose.setText(Html.fromHtml("<font color='@android:color/holo_red_light'>" + "Lose"  + "</font>"), TextView.BufferType.SPANNABLE);
                        loseCount++;
                    }

                    if (opponentStlsTotal.equals(playerStealsTotal)) {
                        mStlsWinLose.setText(Html.fromHtml("<font color='grey'>" +  "Tie"+ "</font>"), TextView.BufferType.SPANNABLE);

                    } else if (opponentStlsTotal < playerStealsTotal) {
                        mStlsWinLose.setText(Html.fromHtml("<font color='#4c69ff'>" +  "Win" + "</font>"), TextView.BufferType.SPANNABLE);
                        winCount++;
                    } else {
                        mStlsWinLose.setText(Html.fromHtml("<font color='@android:color/holo_red_light'>" + "Lose"  + "</font>"), TextView.BufferType.SPANNABLE);
                        loseCount++;
                    }

                    if (opponentBlksTotal.equals(playerBlocksTotal)) {
                        mBlksWinLose.setText(Html.fromHtml("<font color='grey'>" +  "Tie"+ "</font>"), TextView.BufferType.SPANNABLE);

                    } else if (opponentBlksTotal < playerBlocksTotal) {
                        mBlksWinLose.setText(Html.fromHtml("<font color='#4c69ff'>" +  "Win" + "</font>"), TextView.BufferType.SPANNABLE);
                        winCount++;
                    } else {
                        mBlksWinLose.setText(Html.fromHtml("<font color='@android:color/holo_red_light'>" + "Lose"  + "</font>"), TextView.BufferType.SPANNABLE);
                        loseCount++;
                    }

                    if (winCount.equals(loseCount)) {
                        mWinOrLoseBottomView.setText(Html.fromHtml("<font color='grey'>" +  "TIE + $0"+ "</font>"), TextView.BufferType.SPANNABLE);
                    } else if (winCount > loseCount) {
                        mWinOrLoseBottomView.setText(Html.fromHtml("<font color='#4c69ff'>" +  "WIN + $18,324" + "</font>"), TextView.BufferType.SPANNABLE);
                    } else {
                        mWinOrLoseBottomView.setText(Html.fromHtml("<font color='@android:color/holo_red_light'>" + "LOSE - $17,657"  + "</font>"), TextView.BufferType.SPANNABLE);
                    }

                    mResultsTextView.setText("RESULTS");
                }
                mResultsButton.setVisibility(View.INVISIBLE);







            }
        });








    }





    public int countNumberEqual(ArrayList<ClipData.Item> itemList, ClipData.Item itemToCheck) {
        int count = 0;
        for (ClipData.Item i : itemList) {
            if (i.equals(itemToCheck)) {
                count++;
            }
        }
        return count;
    }



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





                            if(identifier == 0) {

                                mSelectedPlayer = parsePlayerStats(jsonData);





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

    /*public void virtualOpponent(Integer opponentPlayerCap) {

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

                    oppRebsBet.setText("- " + vRebsBet);


                }

               if (virtualOpp.contains(600) && opponentPlayerCap >= 600) {
                    vPtsBet = vPtsBet + 1;
                    opponentPlayerCap = opponentPlayerCap - 600;
                    oppPtsBet.setText("- " + vPtsBet);


                }

                if (virtualOpp.contains(3250) && opponentPlayerCap >= 3250 && vStlBet < 2) {
                    vStlBet = vStlBet + 1;
                    opponentPlayerCap = opponentPlayerCap - 3250;
                    oppStlsBet.setText("- " + vStlBet);


                }

                if (virtualOpp.contains(1200) && opponentPlayerCap >= 1200) {
                    vAstBet = vAstBet + 1;
                    opponentPlayerCap = opponentPlayerCap - 1200;
                    oppAstBet.setText("- " + vAstBet);


                }

             if (virtualOpp.contains(3100) && opponentPlayerCap >= 3100 && vBlkBet < 2) {
                vBlkBet = vBlkBet + 1;
                opponentPlayerCap = opponentPlayerCap - 3100;
                oppBlksBet.setText("- " + vBlkBet);

            }



        }

    }*/

}
