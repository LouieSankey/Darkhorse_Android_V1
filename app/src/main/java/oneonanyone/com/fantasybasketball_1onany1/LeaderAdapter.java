package oneonanyone.com.fantasybasketball_1onany1;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

import oneonanyone.com.fantasybasketball_1onany1.DataModel.Player;

/**
 * Created by lsankey on 8/31/15.
 */
public class LeaderAdapter extends BaseAdapter {



    private Context mContext;
    private Player[] mPlayers;

    public LeaderAdapter(Context context, Player[] players){
        mContext = context;
        mPlayers = players;
    }



    @Override
    public int getCount() {
        return mPlayers.length;
    }

    @Override
    public Object getItem(int position) {
        return mPlayers[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.leader_list_item_layout, null);
            holder = new ViewHolder();
            holder.totalScore = (TextView) convertView.findViewById(R.id.totalScore);
            holder.lastContestPlayer = (TextView) convertView.findViewById(R.id.lastContestPlayer);
            holder.lastContestScore = (TextView) convertView.findViewById(R.id.lastContestScore);
            holder.winPercent = (TextView) convertView.findViewById(R.id.winPercent);
            holder.position = (TextView) convertView.findViewById(R.id.position);
            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.arrowUp = (ImageView) convertView.findViewById(R.id.arrow);
            holder.arrowDown = (ImageView) convertView.findViewById(R.id.arrowDown);


            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        Player player = mPlayers[position];


        if(!player.getLastContestScore().substring(0,1).equals("-")){

            holder.arrowUp.setVisibility(View.VISIBLE);
            holder.arrowDown.setVisibility(View.INVISIBLE);
            holder.lastContestScore.setTextColor(Color.parseColor("#4c69ff"));
            holder.lastContestScore.setText("+" + player.getLastContestScore());
        }else{
            holder.arrowUp.setVisibility(View.INVISIBLE);
            holder.arrowDown.setVisibility(View.VISIBLE);
            holder.lastContestScore.setTextColor(Color.parseColor("#ffff4444"));
            holder.lastContestScore.setText(player.getLastContestScore());


        }

        if(player.getmParseUsername().equals(player.getUsername())){
            holder.username.setTextColor(Color.parseColor("#4c69ff"));
        }else{
            holder.username.setTextColor(Color.parseColor("#ffff4444"));

        }


        holder.username.setText(player.getUsername());
        holder.totalScore.setText(player.getmTotalScore());
        holder.lastContestPlayer.setText(player.getLastContestPlayer());
        holder.position.setText(player.getPosition());

        holder.winPercent.setText(player.getmWinPercentage().toString());

        return convertView;
    }

    private static class ViewHolder{
        TextView lastContestPlayer;
        TextView totalScore;
        TextView lastContestScore;
        TextView winPercent;
        TextView position;
        TextView username;
        ImageView arrowUp;
        ImageView arrowDown;



    }

}