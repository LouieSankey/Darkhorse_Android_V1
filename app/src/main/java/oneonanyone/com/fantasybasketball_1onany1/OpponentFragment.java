package oneonanyone.com.fantasybasketball_1onany1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import oneonanyone.com.fantasybasketball_1onany1.DataModel.Player;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OpponentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OpponentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpponentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private ImageView mOpponentPlayerImageView;
    //private ImageView mCheck;
    private Boolean mPlayerIsDrafted;
    private String mOpponentUsername;
    private String mUsername;
    private Button mSetMatchButton;
    private Double mPlayerRemainingValue;
    private Boolean mPlayerCapRed;
    private Double mTotalRemainingValue;
    private String mLeagueName;
    private String mOpponentPlayerName;
    private String mParseId;
    private Button mViewMatchButton;
    private String mPlayerName;
    private Boolean mMatchSet;



    private Player mPlayer;
    private Player mPlayerAlternate;
    private Player mOpponentPlayer;
    private Player mOpponentPlayerAlternate;




    private OnFragmentInteractionListener mListener;

    private EditText mPtEditText;
    private EditText mRebEditText;
    private EditText mAssEditText;
    private EditText mStlEditText;
    private EditText mBlkEditText;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OpponentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OpponentFragment newInstance(String param1, String param2) {
        OpponentFragment fragment = new OpponentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public OpponentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            ParseUser currentUser = ParseUser.getCurrentUser();
            mUsername = currentUser.getUsername();
            mLeagueName = currentUser.getString("leagueName");

        }
    }

    @Override
    public void onResume(){
        super.onResume();

        ParseQuery<ParseObject> draftedPlayer = ParseQuery.getQuery("draftedPlayer");
        draftedPlayer.whereEqualTo("LEAGUE_NAME", mLeagueName);
        draftedPlayer.whereEqualTo("USERNAME", mUsername);
        draftedPlayer.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list.size() == 0) {
                    mPlayerIsDrafted = false;
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_opponent, container, false);

        Bundle playerRemainingValueBundle = getActivity().getIntent().getExtras();
        Bundle bundle = this.getArguments();

        mParseId = bundle.getString("PARSE_ID", null);
        mOpponentUsername = bundle.getString("USERNAME", null);

        mPlayerName = bundle.getString("PLAYER_NAME", null);
        mOpponentPlayerName = bundle.getString("OPPONENT_NAME", null);
        mPlayerRemainingValue = Double.parseDouble(playerRemainingValueBundle.getString("PLAYER_CAP", null));

        final String playerVs = bundle.getString("PLAYER_VS", null);
        final String opponentCap = bundle.getString("OPPONENT_CAP", null);
        final String opponentVs = bundle.getString("OPPONENT_VS", null);

        String opponentValue = bundle.getString("OPPONENT_VALUE", null);
        String opponentPts = bundle.getString("OPPONENT_POINTS", null);
        String opponentRebs =  bundle.getString("OPPONENT_REBOUNDS", null);
        String opponentAsts = bundle.getString("OPPONENT_ASSISTS", null);
        String opponentStls = bundle.getString("OPPONENT_STEALS", null);
        String opponentBlks = bundle.getString("OPPONENT_BLOCKS", null);
        final Boolean isDrafted = bundle.getBoolean("IS_DRAFTED");


        mMatchSet = bundle.getBoolean("MATCH_SET");
        Log.v("isDrafted", isDrafted.toString());

        //mCheck = (ImageView) view.findViewById(R.id.opponentCheckImageView);
        RelativeLayout buyStatsRelativeLayout = (RelativeLayout) view.findViewById(R.id.buyStatsLayout);
        TextView setMatchTextView = (TextView) view.findViewById(R.id.setMatchEmptyView);
        TextView setMatchDescription = (TextView) view.findViewById(R.id.setMatchDescription);
        mViewMatchButton = (Button) view.findViewById(R.id.viewResultsButton);
        mSetMatchButton = (Button) view.findViewById(R.id.setMatchButton);





        if(isDrafted) {
            //mCheck.setVisibility(View.INVISIBLE);
            buyStatsRelativeLayout.setVisibility(View.VISIBLE);
            mViewMatchButton.setVisibility(View.INVISIBLE);
            setMatchTextView.setVisibility(View.INVISIBLE);
            setMatchDescription.setVisibility(View.INVISIBLE);
            mSetMatchButton.setVisibility(View.VISIBLE);



        }else{

            //mCheck.setVisibility(View.INVISIBLE);
            buyStatsRelativeLayout.setVisibility(View.INVISIBLE);
            //mViewMatchButton.setVisibility(View.VISIBLE);
            setMatchTextView.setVisibility(View.VISIBLE);
            setMatchDescription.setVisibility(View.VISIBLE);
            mSetMatchButton.setVisibility(View.VISIBLE);
            mViewMatchButton.setVisibility(View.INVISIBLE);
        }




        mOpponentPlayerImageView = (ImageView) view.findViewById(R.id.oppImageView);
        Picasso.with(getActivity()).load(
                "http://d2cwpp38twqe55.cloudfront.net/images/images-002/players/"
                        + getPlayerUrlName(mOpponentPlayerName)
                        + ".png"
        ).into(mOpponentPlayerImageView);


        TextView opponentNameTextView = (TextView) view.findViewById(R.id.opponentName);
        TextView opponentVsTextView = (TextView) view.findViewById(R.id.opponentVs);
        TextView opponentValueTextView = (TextView) view.findViewById(R.id.opponentValue);
        TextView opponentCapTextView = (TextView) view.findViewById(R.id.oppFundsRemaining);
        TextView opponentUsernameTextView = (TextView) view.findViewById(R.id.oppUserName);
        TextView opponentPointsTextView = (TextView) view.findViewById(R.id.opponentPtsAve);
        TextView opponentReboundsTextView = (TextView) view.findViewById(R.id.opponentRebsAve);
        TextView opponentAssistsTextView = (TextView) view.findViewById(R.id.opponentAstsAve);
        TextView opponentStealsTextView = (TextView) view.findViewById(R.id.opponentStlsAve);
        TextView opponentBlocksTextView = (TextView) view.findViewById(R.id.opponentBlksAve);

        mPtEditText = (EditText) view.findViewById(R.id.ptEditText);
        mRebEditText = (EditText) view.findViewById(R.id.rebEditText);
        mAssEditText = (EditText) view.findViewById(R.id.assEditText);
        mStlEditText = (EditText) view.findViewById(R.id.stlEditText);
        mBlkEditText = (EditText) view.findViewById(R.id.blkEditText);

        mPtEditText.setText("0");
        mRebEditText.setText("0");
        mAssEditText.setText("0");
        mStlEditText.setText("0");
        mBlkEditText.setText("0");



        mPtEditText.addTextChangedListener(watcher);
        mRebEditText.addTextChangedListener(watcher);
        mAssEditText.addTextChangedListener(watcher);
        mStlEditText.addTextChangedListener(watcher);
        mBlkEditText.addTextChangedListener(watcher);






        DecimalFormat formatter = new DecimalFormat("#,###");


        opponentNameTextView.setText(mOpponentPlayerName);
        opponentVsTextView.setText(opponentVs);
        opponentValueTextView.setText("Price: $"
                + formatter.format(Double.parseDouble(opponentValue)));
        opponentCapTextView.setText("Funds Remaining: $"
                + formatter.format(Double.parseDouble(opponentCap)));
        opponentUsernameTextView.setText(mOpponentUsername);
        opponentPointsTextView.setText(opponentPts.substring(0, opponentPts.length()-1));
        opponentReboundsTextView.setText(opponentRebs.substring(0, opponentRebs.length()-1));
        opponentAssistsTextView.setText(opponentAsts.substring(0, opponentAsts.length()-1));
        opponentStealsTextView.setText(opponentStls.substring(0, opponentStls.length()-1));
        opponentBlocksTextView.setText(opponentBlks.substring(0, opponentBlks.length()-1));






        mViewMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle matchUpExtras = new Bundle();

                matchUpExtras.putString("PLAYER_NAME", mPlayerName);
                matchUpExtras.putString("OPPONENT_NAME", mOpponentPlayerName);
                matchUpExtras.putString("PLAYER_VS", playerVs);
                matchUpExtras.putString("OPPONENT_VS", opponentVs);
                matchUpExtras.putString("USERNAME", mOpponentUsername);


                if (mPtEditText.getText().length() == 0) {
                    mPtEditText.setText("0");
                }
                if (mRebEditText.getText().length() == 0) {
                    mRebEditText.setText("0");
                }
                if (mAssEditText.getText().length() == 0) {
                    mAssEditText.setText("0");
                }
                if (mStlEditText.getText().length() == 0) {
                    mStlEditText.setText("0");
                }
                if (mBlkEditText.getText().length() == 0) {
                    mBlkEditText.setText("0");
                }

                matchUpExtras.putString("PT_PURCHASED", mPtEditText.getText().toString());
                matchUpExtras.putString("REB_PURCHASED", mRebEditText.getText().toString());
                matchUpExtras.putString("ASS_PURCHASED", mAssEditText.getText().toString());
                matchUpExtras.putString("STL_PURCHASED", mStlEditText.getText().toString());
                matchUpExtras.putString("BLK_PURCHASED", mBlkEditText.getText().toString());
                matchUpExtras.putString("LEAGUE_NAME", mLeagueName);
                if(mMatchSet){
                    matchUpExtras.putString("INTENT", "OPPONENT_LIST");
                }


                Intent intent = new Intent(getActivity(), ResultsActivity.class);
                intent.putExtras(matchUpExtras);
                startActivity(intent);
            }
        });



        mSetMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(mTotalRemainingValue != null && mTotalRemainingValue < 0){
                    Toast.makeText(getActivity(), "You do not have sufficient funds", Toast.LENGTH_LONG).show();

                }else if(!isDrafted){
                    Toast.makeText(getActivity(), "You must first draft a player on the previous screen", Toast.LENGTH_LONG).show();

                }

                else {

                    if(mPtEditText.getText().toString().equals("0") &&
                            mRebEditText.getText().toString().equals("0") &&
                            mAssEditText.getText().toString().equals("0")&&
                             mStlEditText.getText().toString().equals("0") &&
                             mBlkEditText.getText().toString().equals("0")) {

                        new AlertDialog.Builder(getActivity())
                                .setTitle("Are you sure?")
                                .setMessage(
                                        "You have not used your remaining funds" +
                                                " to purchase any negative stats against your opponent." +
                                                "This is not an effective strategy to win when your opponent" +
                                                " will surely do the opposite!"
                                )
                                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        setMatch(playerVs, opponentVs);

                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("AlertDialog", "Negative");
                                    }
                                })
                                .show();

                    }else{
                        setMatch(playerVs, opponentVs);

                    }






                }
            }
        });






        return view;
    }

    private void setMatch(String playerVs, String opponentVs) {
        ParseObject statsPurcahsed = new ParseObject("statsPurchased");
        statsPurcahsed.put("USERNAME", mUsername);
        statsPurcahsed.put("OPP_USERNAME", mOpponentUsername);
        statsPurcahsed.put("LEAGUE_NAME", mLeagueName);
        statsPurcahsed.put("PTS_PURCH", mPtEditText.getText().toString());
        statsPurcahsed.put("REBS_PURCH", mRebEditText.getText().toString());
        statsPurcahsed.put("ASTS_PURCH", mAssEditText.getText().toString());
        statsPurcahsed.put("STLS_PURCH", mStlEditText.getText().toString());
        statsPurcahsed.put("BLKS_PURCH", mBlkEditText.getText().toString());
        statsPurcahsed.put("OPPONENT_ID", mParseId);
        statsPurcahsed.put("MATCH_SET", true);



        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseObject draftedPlayer = currentUser.getParseObject("parent");
        draftedPlayer.add("opponents", mOpponentUsername);
        currentUser.saveInBackground();
        statsPurcahsed.saveInBackground();




        mSetMatchButton.setVisibility(View.INVISIBLE);
        mViewMatchButton.setVisibility(View.VISIBLE);

        Bundle matchUpExtras = new Bundle();

        matchUpExtras.putString("PLAYER_NAME", mPlayerName);
        matchUpExtras.putString("OPPONENT_NAME", mOpponentPlayerName);
        matchUpExtras.putString("PLAYER_VS", playerVs);
        matchUpExtras.putString("OPPONENT_VS", opponentVs);
        matchUpExtras.putString("USERNAME", mOpponentUsername);

        if (mPtEditText.getText().length() == 0) {
            mPtEditText.setText("0");
        }
        if (mRebEditText.getText().length() == 0) {
            mRebEditText.setText("0");
        }
        if (mAssEditText.getText().length() == 0) {
            mAssEditText.setText("0");
        }
        if (mStlEditText.getText().length() == 0) {
            mStlEditText.setText("0");
        }
        if (mBlkEditText.getText().length() == 0) {
            mBlkEditText.setText("0");
        }

        matchUpExtras.putString("PT_PURCHASED", mPtEditText.getText().toString());
        matchUpExtras.putString("REB_PURCHASED", mRebEditText.getText().toString());
        matchUpExtras.putString("ASS_PURCHASED", mAssEditText.getText().toString());
        matchUpExtras.putString("STL_PURCHASED", mStlEditText.getText().toString());
        matchUpExtras.putString("BLK_PURCHASED", mBlkEditText.getText().toString());
        matchUpExtras.putString("LEAGUE_NAME", mLeagueName);
        if(mMatchSet){
            matchUpExtras.putString("INTENT", "OPPONENT_LIST");
        }

        Intent intent = new Intent(getActivity(), ResultsActivity.class);
        intent.putExtras(matchUpExtras);
        startActivity(intent);

        new Timer().schedule(new TimerTask() {
             @Override
             public void run() {
                 getFragmentManager().popBackStack();
             }
         }, 300);
    }


    TextWatcher watcher = new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {




         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {

             if(s.length() != 0) {
                 if(mPtEditText.getText().length() == 0){
                     mPtEditText.setText("0");
                 }
                 if(mRebEditText.getText().length() == 0){
                     mRebEditText.setText("0");
                 }
                 if(mAssEditText.getText().length() == 0){
                     mAssEditText.setText("0");
                 }
                 if(mStlEditText.getText().length() == 0)
                 {
                     mStlEditText.setText("0");
                 }
                 if(mBlkEditText.getText().length() == 0) {
                     mBlkEditText.setText("0");
                 }


                 Double total = (Double.parseDouble(mPtEditText.getText().toString()) * 600) +
                         (Double.parseDouble(mRebEditText.getText().toString()) * 1000) +
                         (Double.parseDouble(mAssEditText.getText().toString()) * 1200) +
                         (Double.parseDouble(mStlEditText.getText().toString()) * 3250) +
                         (Double.parseDouble(mBlkEditText.getText().toString()) * 3100);

                mTotalRemainingValue = mPlayerRemainingValue - total;


                 Log.v("something", mTotalRemainingValue.toString());

                mListener.onFragmentInteraction(mTotalRemainingValue);


             }

         }

         @Override
         public void afterTextChanged(Editable s) {


         }
     };



 /*// TODO: Rename method, update argument and hook method into UI event
 */
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

 /**
  * This interface must be implemented by activities that contain this
  * fragment to allow an interaction in this fragment to be communicated
  * to the activity and potentially other fragments contained in that
  * activity.
  * <p/>
  * See the Android Training lesson <a href=
  * "http://developer.android.com/training/basics/fragments/communicating.html"
  * >Communicating with Other Fragments</a> for more information.
  */


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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Double newRemainingValue);


    }



}
