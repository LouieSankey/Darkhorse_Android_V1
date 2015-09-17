package oneonanyone.com.fantasybasketball_1onany1.DataModel;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import oneonanyone.com.fantasybasketball_1onany1.R;

/**
 * Created by lsankey on 7/13/15.
 */
public class PlayerAdapter extends BaseAdapter {

    private Context mContext;
    private Player[] mPlayers;

    public PlayerAdapter(Context context, Player[] players){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.player_list_item_layout, null);
            holder = new ViewHolder();
            holder.playerNameTextView = (TextView) convertView.findViewById(R.id.playerNameTextView);
            holder.playerValueTextView = (TextView) convertView.findViewById(R.id.playerValueTextView);
            holder.remainingCapTextView = (TextView) convertView.findViewById(R.id.remainingCapTextView);
            holder.username = (TextView) convertView.findViewById(R.id.opponentUsername);
            holder.check = (ImageView) convertView.findViewById(R.id.oppListCheckImageView);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        Player player = mPlayers[position];

        DecimalFormat formatter = new DecimalFormat("$#,###");

        holder.playerNameTextView.setText(player.getName());
        holder.playerValueTextView.setText(formatter.format(Double.parseDouble(player.getValue())));
        holder.remainingCapTextView.setText(formatter.format(Double.parseDouble(player.getCap())));
        holder.username.setText(player.getUsername());
        holder.check.setVisibility(View.INVISIBLE);


        if(player.isMatchMade()){
            holder.check.setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    private static class ViewHolder{
        TextView playerValueTextView;
        TextView playerNameTextView;
        TextView remainingCapTextView;
        TextView username;
        ImageView check;

    }

}
