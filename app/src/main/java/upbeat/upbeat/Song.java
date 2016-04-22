package upbeat.upbeat;

import com.firebase.client.Firebase;

/**
 * Created by Chandler on 4/6/16.
 */
public class Song {
    private String key;
    private String title;
    private String formattedTitle;
    private String artistName;
    private int songID;
    private long upbeats;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Song() {}

    public String getTitle() {
        return title;
    }

    public long getUpbeats() {
        return upbeats;
    }

    public int getSongID() { return songID; }

    public String getFormattedTitle() {return formattedTitle;}

    public String getArtistName() {
        return artistName;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpbeats(long upbeats) {
        this.upbeats = upbeats;
    }

    public void setFormattedTitle(String formattedTitle) {this.formattedTitle = formattedTitle;}

    public void setArtistName(String authorName) {
        this.artistName = authorName;
    }
}
