package oneonanyone.com.fantasybasketball_1onany1.DataModel;

import java.math.BigDecimal;

/**
 * Created by lsankey on 7/11/15.
 */
public class Player {

    private String mUsername;
    private String Position;
    private String lastContestPlayer;
    private String mTotalScore;
    private String lastContestScore;
    private String mWinPercentage;
    private String mParseUsername;



    private String parseObjectId;
    private String mName;
    private String mValue;
    private String mCap;
    private String mPointsAverage;
    private String mReboundsAverage;
    private String mAssistsAverage;
    private String mStealsAverage;
    private String mBlocksAverage;
    private String mPlayerVs;


    private Integer mPoints;
    private Integer mDefensiveRebounds;
    private Integer mOffensiveRebounds;
    private Integer mRebounds;
    private Integer mVirtualOpponentRebounds;
    private Integer mPlayerId;
    private Integer mGameId;
    private Integer mOpponentId;
    private Integer mAssists;
    private Integer mSteals;
    private Integer mBlocks;

    private boolean mMatchMade;

    public String getmParseUsername() {
        return mParseUsername;
    }

    public void setmParseUsername(String mParseUsername) {
        this.mParseUsername = mParseUsername;
    }


    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getLastContestPlayer() {
        return lastContestPlayer;
    }

    public void setLastContestPlayer(String lastContestPlayer) {
        this.lastContestPlayer = lastContestPlayer;
    }

    public String getmTotalScore() {
        return mTotalScore;
    }

    public void setmTotalScore(String mTotalScore) {
        this.mTotalScore = mTotalScore;
    }

    public String getLastContestScore() {
        return lastContestScore;
    }

    public void setLastContestScore(String lastContestScore) {
        this.lastContestScore = lastContestScore;
    }

    public String getmWinPercentage() {
        return mWinPercentage;
    }

    public void setmWinPercentage(String mWinPercentage) {
        this.mWinPercentage = mWinPercentage;
    }


    public String getParseObjectId() {
        return parseObjectId;
    }

    public void setParseObjectId(String parseObjectId) {
        this.parseObjectId = parseObjectId;
    }

    public boolean isMatchMade() {
        return mMatchMade;
    }

    public void setMatchMade(boolean matchMade) {
        this.mMatchMade = matchMade;
    }


    public String getPlayerVs() {
        return mPlayerVs;
    }

    public void setPlayerVs(String mPlayerVs) {
        this.mPlayerVs = mPlayerVs;
    }


    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public Integer getRebounds() { return mRebounds;}
    public void setRebounds(Integer rebounds){mRebounds = rebounds; }


    public Integer getPoints() {
        return mPoints;
    }

    public void setPoints(Integer points) {
        mPoints = points;
    }

    public Integer getOffensiveRebounds() {
        return mOffensiveRebounds;
    }


    public Integer getVirtualOpponentRebounds() {
        return mVirtualOpponentRebounds;
    }

    public void setVirtualOpponentRebounds(Integer virtualOpponentRebounds) {
        mVirtualOpponentRebounds = virtualOpponentRebounds;
    }

    public void setOffensiveRebounds(Integer offensiveRebounds) {
        mOffensiveRebounds = offensiveRebounds;
    }


    public Integer getDefensiveRebounds() {
        return mDefensiveRebounds;
    }

    public void setDefensiveRebounds(Integer defensiveRebounds) {
        mDefensiveRebounds = defensiveRebounds;
    }

    public Integer getAssists() {
        return mAssists;
    }

    public void setAssists(Integer assists) {
        mAssists = assists;
    }

    public Integer getSteals() {
        return mSteals;
    }

    public void setSteals(Integer steals) {
        mSteals = steals;
    }

    public Integer getBlocks() {
        return mBlocks;
    }

    public void setBlocks(Integer blocks) {
        mBlocks = blocks;
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Integer getPlayerId() {
        return mPlayerId;
    }

    public void setPlayerId(Integer playerId) {
        mPlayerId = playerId;
    }

    public Integer getGameId() {
        return mGameId;
    }

    public void setGameId(Integer gameId) {
        mGameId = gameId;
    }

    public Integer getOpponentId() {
        return mOpponentId;
    }

    public void setOpponentId(Integer opponentId) {
        mOpponentId = opponentId;
    }

    public String getPointsAverage() {


            BigDecimal points = new BigDecimal(mPointsAverage);
            BigDecimal roundedPoints = points.setScale(2, BigDecimal.ROUND_HALF_UP);

            return roundedPoints + "";
        }

    public void setPointsAverage(String pointsAverage) {
        mPointsAverage = pointsAverage;
    }


    public String getReboundsAverage() {
        BigDecimal rebounds = new BigDecimal(mReboundsAverage);
        BigDecimal roundedRebounds = rebounds.setScale(2, BigDecimal.ROUND_HALF_UP);

        return roundedRebounds + "";
    }

    public void setReboundsAverage(String reboundsAverage) {
        mReboundsAverage = reboundsAverage;
    }

    public String getAssistsAverage() {
        BigDecimal assists = new BigDecimal(mAssistsAverage);
        BigDecimal roundedAssists = assists.setScale(2, BigDecimal.ROUND_HALF_UP);

        return roundedAssists + "";
    }

    public void setAssistsAverage(String assistsAverage) {
        mAssistsAverage = assistsAverage;
    }

    public String getStealsAverage() {
        BigDecimal steals = new BigDecimal(mStealsAverage);
        BigDecimal roundedSteals = steals.setScale(2, BigDecimal.ROUND_HALF_UP);

        return roundedSteals + "";
    }

    public void setStealsAverage(String stealsAverage) {
        mStealsAverage = stealsAverage;
    }

    public String getBlocksAverage() {
        BigDecimal blocks = new BigDecimal(mBlocksAverage);
        BigDecimal roundedBlocks = blocks.setScale(2, BigDecimal.ROUND_HALF_UP);

        return roundedBlocks + "";
    }

    public void setBlocksAverage(String blocksAverage) {
        mBlocksAverage = blocksAverage;
    }



    public String getCap() {


            BigDecimal playerPrice, totalBudget, remainingCap;

            playerPrice = new BigDecimal(getValue() + "");

        if(playerPrice.intValue() < 15000) {

            totalBudget = new BigDecimal("20000");
            remainingCap = totalBudget.subtract(playerPrice);


            return remainingCap + "";
        }else{
            return mCap;
        }

    }

    public void setCap(String cap) {
        mCap = cap;
    }


    public String getValue() {



            BigDecimal jsonDecimalValue, wholeValue, decimalRounded;

            jsonDecimalValue = new BigDecimal(mValue + "");

        if(jsonDecimalValue.intValue() < 2) {

            wholeValue = jsonDecimalValue.multiply(new BigDecimal("10000"));

            decimalRounded = wholeValue.setScale(0, BigDecimal.ROUND_HALF_UP);

            return decimalRounded + "";
        }else{

            return mValue;
        }

    }

    public void setValue(String value) {
        mValue = value;
    }



}

