package ps.wwbtraining.teacher_group2.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eman on 10/15/2017.
 */

public class Group {

    @SerializedName("gid")
    private int gid;
    @SerializedName("group_name")
    private String group_name;
    @SerializedName("group_desc")
    private String group_desc;

    public Group(int gid, String group_name, String group_desc) {
        this.gid = gid;
        this.group_name = group_name;
        this.group_desc = group_desc;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_desc() {
        return group_desc;
    }

    public void setGroup_desc(String group_desc) {
        this.group_desc = group_desc;
    }
}
