package oneonanyone.com.fantasybasketball_1onany1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import oneonanyone.com.fantasybasketball_1onany1.DataModel.Player;
import oneonanyone.com.fantasybasketball_1onany1.DataModel.PlayerAdapter;

/**
 * A fragment representing a list of Items.
 * <p>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class OpponentListFragment extends ListFragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    protected List<ParseObject> mDraftedPlayers;
    protected ParseQuery<ParseObject> mQuery;
    protected Button mDraftPlayerButton;
    protected Button mRefreshButton;
    protected ParseUser mCurrentUser;

    protected Bundle mExtras;
    protected String mLeagueName;
    protected String mParseUsername;
    protected TextView mLeagueNameTextView;
    protected ImageView mCheck;
    protected String mPlayerName;
    protected String mPlayerVs;
    protected Player mSinglePlayer;
    protected String mDraftedOpponentUsername;
    protected ArrayList<String> mUsernameOpponents;
    protected Boolean mIsDrafted;




    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private PlayerAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static OpponentListFragment newInstance(String param1, String param2) {
        OpponentListFragment fragment = new OpponentListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OpponentListFragment() {
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mExtras = getActivity().getIntent().getExtras();
        mCurrentUser = ParseUser.getCurrentUser();
        mCurrentUser = ParseUser.getCurrentUser();
        mParseUsername = mCurrentUser.getUsername();
        mLeagueName = mCurrentUser.getString("leagueName");



    }

    @Override
    public void onResume() {
        super.onResume();



        ParseObject draftedPlayer = mCurrentUser.getParseObject("parent");


                try {
                    mUsernameOpponents = (ArrayList<String>) draftedPlayer.get("opponents");
                } catch (IllegalStateException e) {

                }catch (NullPointerException e){


                }




        ParseQuery<ParseObject> query = ParseQuery.getQuery("draftedPlayer");
        query.orderByDescending("updatedAt");
        query.whereEqualTo("LEAGUE_NAME", mLeagueName);
        query.whereNotEqualTo("USERNAME", mParseUsername);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> draftedPlayers, ParseException e) {

                if (e == null) {

                    mDraftedPlayers = draftedPlayers;

                    Player[] parsePlayers = new Player[mDraftedPlayers.size()];
                    for (int i = 0; i < mDraftedPlayers.size(); i++) {




                            ParseObject parsePlayer = mDraftedPlayers.get(i);



                            mSinglePlayer = new Player();
                            mDraftedOpponentUsername = parsePlayer.getString("USERNAME");
                            mSinglePlayer.setName(parsePlayer.getString("PLAYER_NAME"));
                            mSinglePlayer.setValue(parsePlayer.getString("PLAYER_VALUE"));
                            mSinglePlayer.setCap(parsePlayer.getString("PLAYER_CAP"));
                            mSinglePlayer.setUsername(mDraftedOpponentUsername);
                            mSinglePlayer.setPlayerVs(parsePlayer.getString("PLAYER_VS"));
                            mSinglePlayer.setPointsAverage(parsePlayer.getString("PLAYER_POINTS"));
                            mSinglePlayer.setReboundsAverage(parsePlayer.getString("PLAYER_REBOUNDS"));
                            mSinglePlayer.setAssistsAverage(parsePlayer.getString("PLAYER_ASSISTS"));
                            mSinglePlayer.setStealsAverage(parsePlayer.getString("PLAYER_STEALS"));
                            mSinglePlayer.setBlocksAverage(parsePlayer.getString("PLAYER_BLOCKS"));
                            mSinglePlayer.setParseObjectId(parsePlayer.getObjectId());
                            if(mUsernameOpponents != null &&
                                    mUsernameOpponents.contains(mDraftedOpponentUsername)){
                                mSinglePlayer.setMatchMade(true);

                            }




                        parsePlayers[i] = mSinglePlayer;
                    }

                    mAdapter = new PlayerAdapter(getActivity(),
                            parsePlayers);
                    setListAdapter(mAdapter);

                } else {
                    Log.d("USERNAME", "Error: " + e.getMessage());
                }
            }
        });


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opponent_list, container, false);



        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        (mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(OpponentListFragment.this);

        TextView emptyText = (TextView) view.findViewById(android.R.id.empty);
        mListView.setEmptyView(emptyText);




        Bundle bundle = this.getArguments();
        mPlayerName = mExtras.getString("PLAYER_NAME");
        mPlayerVs = bundle.getString("PLAYER_VS");

        final String playerValue = mExtras.getString("PLAYER_VALUE");
        final String playerCap = mExtras.getString("PLAYER_CAP");
        final String playerPoints = mExtras.getString("PLAYER_POINTS");
        final String playerRebs = mExtras.getString("PLAYER_REBOUNDS");
        final String playerAsts = mExtras.getString("PLAYER_ASSISTS");
        final String playerStls = mExtras.getString("PLAYER_STEALS");
        final String playerBlks = mExtras.getString("PLAYER_BLOCKS");

        final Integer gameId = bundle.getInt("SELECTED_PLAYER_GAME_ID");
        final Integer playerId = bundle.getInt("SELECTED_PLAYER_ID");



       // String callingActivity = mExtras.getString("CALLING_ACTIVITY");

        mLeagueNameTextView = (TextView) view.findViewById(R.id.leageNameTextView);
        mLeagueNameTextView.setText(mLeagueName);

        mCheck = (ImageView) view.findViewById(R.id.oppListCheckImageView);


        ParseObject draftedPlayer = mCurrentUser.getParseObject("parent");
        mIsDrafted = true;
        try{
            draftedPlayer.getString("USERNAME");
        }catch(IllegalStateException e){
            mIsDrafted = false;
        }catch(NullPointerException e){
            mIsDrafted = false;
        }


        mRefreshButton = (Button) view.findViewById(R.id.refreshButton);
        mDraftPlayerButton = (Button) view.findViewById(R.id.draftPlayerButton);
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LeaderBoardActivity.class);
                startActivity(intent);
            }
        });

        if(mIsDrafted){
            mDraftPlayerButton.setVisibility(View.INVISIBLE);

        }else{
            mRefreshButton.setVisibility(View.INVISIBLE);

        }



        mDraftPlayerButton.setText("DRAFT " + mPlayerName.toUpperCase());
        mDraftPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







            mQuery = ParseQuery.getQuery("draftedPlayer");
                mQuery.whereEqualTo("LEAGUE_NAME", mLeagueName);
                mQuery.whereEqualTo("USERNAME", mParseUsername);
                mQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {


                        if (list.size() == 0) {


                            new AlertDialog.Builder( getActivity() )
                                    .setTitle( "Draft " + mPlayerName )
                                    .setMessage( "Are you sure you want to draft " + mPlayerName + " for tonight's contest?" )
                                    .setPositiveButton( "Yes, Draft", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            ParseObject draftedPlayer = new ParseObject("draftedPlayer");
                                            draftedPlayer.put("LEAGUE_NAME", mLeagueName);
                                            draftedPlayer.put("USERNAME", mParseUsername);
                                            draftedPlayer.put("PLAYER_NAME", mPlayerName);
                                            draftedPlayer.put("PLAYER_VALUE", playerValue);
                                            draftedPlayer.put("PLAYER_CAP", playerCap);
                                            draftedPlayer.put("PLAYER_VS", mPlayerVs);
                                            draftedPlayer.put("PLAYER_POINTS", playerPoints);
                                            draftedPlayer.put("PLAYER_REBOUNDS", playerRebs);
                                            draftedPlayer.put("PLAYER_ASSISTS", playerAsts);
                                            draftedPlayer.put("PLAYER_STEALS", playerStls);
                                            draftedPlayer.put("PLAYER_BLOCKS", playerBlks);
                                            draftedPlayer.put("API_GAME_ID", gameId);
                                            draftedPlayer.put("API_PLAYER_ID", playerId);

                                            ParseUser currentUser = ParseUser.getCurrentUser();
                                            currentUser.put("parent", draftedPlayer);

                                            if(currentUser.get("totalWins") == null){
                                                currentUser.put("totalWins", 0);
                                            }
                                            if(currentUser.get("totalLosses") == null){
                                                currentUser.put("totalLosses", 0);
                                            }

                                            draftedPlayer.saveInBackground();
                                            currentUser.saveInBackground();

                                            mListener.onFragmentInteraction(true);
                                            mIsDrafted = true;

                                            Toast.makeText(getActivity(), "You have successfully drafted " + mPlayerName, Toast.LENGTH_LONG).show();
                                            mDraftPlayerButton.setVisibility(View.INVISIBLE);
                                            mRefreshButton.setVisibility(View.VISIBLE);
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Log.d("AlertDialog", "Negative");
                                        }
                                    })
                                    .show();

                        } else {
                            Toast.makeText(getActivity(), "You have already drafted a player for today's games", Toast.LENGTH_LONG).show();

                        }


                   /* DialogFragment newFragment = AlertDialogFragment.newInstance();// call the static method
                    newFragment.show(getActivity().getFragmentManager(), "dialog");*/

                    }
                });

            }
        });





        return view;
    }

   @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnFragmentInteractionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        if (null != mListener) {


            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.



        }


    }



        @Override
        public void onListItemClick (ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);


            ParseObject parsePlayer = mDraftedPlayers.get(position);
            String opponentName = parsePlayer.getString("PLAYER_NAME");
            String opponentValue = parsePlayer.getString("PLAYER_VALUE");
            String opponentCap = parsePlayer.getString("PLAYER_CAP");
            String opponentUsername = parsePlayer.getString("USERNAME");
            String opponentVs = parsePlayer.getString("PLAYER_VS");
            String opponentPoints = parsePlayer.getString("PLAYER_POINTS");
            String opponentRebounds = parsePlayer.getString("PLAYER_REBOUNDS");
            String opponentAssists = parsePlayer.getString("PLAYER_ASSISTS");
            String opponentSteals = parsePlayer.getString("PLAYER_STEALS");
            String opponentBlocks = parsePlayer.getString("PLAYER_BLOCKS");
            String parseId = parsePlayer.getObjectId();


            Bundle bundle = new Bundle();
            bundle.putString("PLAYER_NAME", mPlayerName);
            bundle.putString("PLAYER_VS", mPlayerVs);
            bundle.putString("LEAGUE_NAME", mLeagueName);
            bundle.putString("OPPONENT_NAME", opponentName);
            bundle.putString("OPPONENT_VALUE", opponentValue);
            bundle.putString("OPPONENT_CAP", opponentCap);
            bundle.putString("USERNAME", opponentUsername);
            bundle.putString("OPPONENT_VS", opponentVs);
            bundle.putString("OPPONENT_POINTS", opponentPoints);
            bundle.putString("OPPONENT_REBOUNDS", opponentRebounds);
            bundle.putString("OPPONENT_ASSISTS", opponentAssists);
            bundle.putString("OPPONENT_STEALS", opponentSteals);
            bundle.putString("OPPONENT_BLOCKS", opponentBlocks);
            bundle.putString("PARSE_ID", parseId);
            bundle.putBoolean("IS_DRAFTED", mIsDrafted);

                if ((mUsernameOpponents != null &&
                        mUsernameOpponents.contains(parsePlayer.getString("USERNAME")))) {
                    Intent intent = new Intent(getActivity(), ResultsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                    bundle.putBoolean("MATCH_SET", true);
                }else {


                    OpponentFragment opponentFragment = new OpponentFragment();
                    opponentFragment.setArguments(bundle);

                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, opponentFragment);
                    transaction.addToBackStack("opponentFragment");
                    transaction.commit();
                }

    }



    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Boolean checkMarkVisible);
    }

}
