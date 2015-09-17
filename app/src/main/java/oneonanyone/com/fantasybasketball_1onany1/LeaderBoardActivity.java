package oneonanyone.com.fantasybasketball_1onany1;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import oneonanyone.com.fantasybasketball_1onany1.DataModel.Player;
import oneonanyone.com.fantasybasketball_1onany1.DataModel.PlayerAdapter;

public class LeaderBoardActivity extends ListActivity {


    protected List<ParseUser> mParsePlayers;
    protected TextView mLeagueName;

    protected String mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        ParseUser parseUser = ParseUser.getCurrentUser();
        mCurrentUser = parseUser.getUsername();
        String leagueName = parseUser.getString("leagueName");
        mLeagueName = (TextView) findViewById(R.id.leagueName);
        mLeagueName.setText(leagueName);

        TextView username = (TextView) findViewById(R.id.username);
        username.setText(mCurrentUser);





        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.orderByDescending("totalScore");
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> users, ParseException e) {

                if (e == null) {

                    mParsePlayers = users;
                    int index = 0;
                    String stringIndex;

                    Player[] parsePlayers = new Player[mParsePlayers.size()];
                    for (int i = 0; i < mParsePlayers.size(); i++) {
                        ParseObject parsePlayer = mParsePlayers.get(i);
                        Player singlePlayer = new Player();
                        index++;
                        stringIndex = "";
                        if (index == 1){
                             stringIndex = index + "st";
                        }else if (index == 2){
                            stringIndex = index + "nd";

                        }else if(index == 3){
                            stringIndex = index + "rd";

                        }else{
                            stringIndex = index + "th";

                        }



                        singlePlayer.setUsername(parsePlayer.getString("username"));
                        singlePlayer.setmParseUsername(mCurrentUser);
                        singlePlayer.setLastContestPlayer(parsePlayer.getString("lastPlayer"));
                        singlePlayer.setPosition(stringIndex);


                        DecimalFormat formatter = new DecimalFormat("$###,###");
                        Integer totalScore = parsePlayer.getNumber("totalScore").intValue();
                        Integer lastScore = parsePlayer.getNumber("lastScore").intValue();

                        singlePlayer.setmTotalScore(formatter.format(totalScore));
                        singlePlayer.setLastContestScore(formatter.format(lastScore));

                        Double totalWins = parsePlayer.getNumber("totalWins").doubleValue();
                        Double totalLosses = parsePlayer.getNumber("totalLosses").doubleValue();
                        Double totalGames = totalWins + totalLosses;

                        DecimalFormat decimalFormat = new DecimalFormat("#.##");

                        Double winPerc = totalWins / totalGames;
                        singlePlayer.setmWinPercentage(decimalFormat.format(winPerc));


                        parsePlayers[i] = singlePlayer;

                    }

                    LeaderAdapter leaderAdapter = new LeaderAdapter(LeaderBoardActivity.this,
                            parsePlayers);
                    setListAdapter(leaderAdapter);

                } else {
                    Log.d("USERNAME", "Error: " + e.getMessage());
                }
            }
        });


    }

}
