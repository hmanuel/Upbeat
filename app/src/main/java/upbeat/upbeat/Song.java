package upbeat.upbeat;

/**
 * Created by Chandler on 4/6/16.
 */
public class Song {
    private String title;
    private int songID;
    private int upbeats;

    public String getTitle() {
        return title;
    }

    public int getUpbeats() {
        return upbeats;
    }

    //public int getSongID() { return ; }


    public void setSongID(int songID) {
        this.songID = songID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpbeats(int upbeats) {
        this.upbeats = upbeats;
    }
}
