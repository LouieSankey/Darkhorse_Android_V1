package oneonanyone.com.fantasybasketball_1onany1.DataModel;

/**
 * Created by lsankey on 8/1/15.
 */
public class Team {

    private String mAbbrev;
    private String mCity;
    private Integer mTeamId;
    private String mFullName;

    public String getAbbrev() {
        return mAbbrev;
    }

    public void setAbbrev(String abbrev) {
        mAbbrev = abbrev;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public Integer getTeamId() {
        return mTeamId;
    }

    public void setTeamId(Integer teamId) {
        mTeamId = teamId;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }
}
