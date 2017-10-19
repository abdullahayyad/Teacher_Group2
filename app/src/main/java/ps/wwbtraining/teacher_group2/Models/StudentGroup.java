package ps.wwbtraining.teacher_group2.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Maryam on 10/19/2017.
 */

public class StudentGroup {
    @SerializedName("gid")
    private int gid;
    @SerializedName("uid")
    private int uid;

    public StudentGroup(int gid, int uid) {
        this.gid = gid;
        this.uid = uid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
