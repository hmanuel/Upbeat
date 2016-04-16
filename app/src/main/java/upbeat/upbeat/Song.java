package upbeat.upbeat;

/**
 * Created by Chandler on 4/6/16.
 */
public class Song {
    private String title;
    private String formattedTitle;
    private String artistName;
    private int songID;
    private int upbeats;

    public String getTitle() {
        return title;
    }

    public int getUpbeats() {
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

    public void setUpbeats(int upbeats) {
        this.upbeats = upbeats;
    }

    public void setFormattedTitle(String formattedTitle) {this.formattedTitle = formattedTitle;}

    public void setArtistName(String authorName) {
        this.artistName = authorName;
    }
}
