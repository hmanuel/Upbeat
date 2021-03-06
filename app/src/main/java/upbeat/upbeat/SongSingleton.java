package upbeat.upbeat;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.client.Firebase;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Chandler on 4/6/16.
 */
public class SongSingleton {
    private Firebase mainReference;
    private Firebase deleteReference;
    private Firebase songsReference;
    private Firebase individualReference;
    private ArrayList<Song> mSongs;
    private ArrayList<Integer> mSongIDS;
    private Context mAppContext;
    private static SongSingleton sSongs;
    private HashMap<String, Song> mKeyMap;

    public static final String TAG = UpActivity.class.getSimpleName();

    private SongSingleton (Context c) {
        mAppContext = c;
        mSongs = new ArrayList<>();
        populateSongs();
    }

    private void populateSongs() {
        String key;
        mKeyMap = new HashMap<String, Song>();
        mainReference = new Firebase("https://dazzling-fire-2122.firebaseio.com");
        deleteReference = new Firebase("https://dazzling-fire-2122.firebaseio.com/songs");
        deleteReference.removeValue();
        songsReference = mainReference.child("songs");
        Field[] fields = R.raw.class.getFields();
        Log.d(TAG, "Hello world: " + fields.length);
        Song tempSong;
        int tempSongID;
        int tempSongUpbeats = 0;
        String tempSongTitle;
        String tempSongFormattedTitle;
        String tempSongArtist;
        for (int i = 1; i < fields.length; i++) {
            Log.d(TAG, fields[i].getName());
            tempSong = new Song();
            tempSongTitle = fields[i].getName();
            tempSongFormattedTitle = getReformattedTitle(tempSongTitle);
            tempSongArtist = getArtistName(tempSongTitle);
            tempSongID = mAppContext.getResources()
                    .getIdentifier(tempSongTitle, "raw", mAppContext.getPackageName());

            tempSong.setSongID(tempSongID);
            tempSong.setTitle(tempSongTitle);
            tempSong.setUpbeats(tempSongUpbeats); // this is where the cloud does cloud things
            tempSong.setFormattedTitle(tempSongFormattedTitle);
            tempSong.setArtistName(tempSongArtist);
            //mSongs.add(tempSong);
            //key =
//          individualReference.push().setValue(tempSong);
            individualReference = songsReference.push();
            individualReference.setValue(tempSong);
            key = individualReference.getKey();
            //tempSong.setReference(individualReference.child("upbeats"));
            tempSong.setKey(key);
            mSongs.add(tempSong);
            mKeyMap.put(key, tempSong);
        }
    }

    private String getReformattedTitle(String tempSongTitle) {
        String formatted = "";
        if (tempSongTitle.equals("anaconda"))
            formatted = "Anaconda";
        if (tempSongTitle.equals("cocoabutterkisses"))
            formatted = "Cocoa Butter Kisses";
        if (tempSongTitle.equals("father"))
            formatted = "Father Stretch My Hands Pt. 1";
        if (tempSongTitle.equals("ishort"))
            formatted = "i";
        if (tempSongTitle.equals("myhumps"))
            formatted = "My Humps";
        if (tempSongTitle.equals("problemsshort"))
            formatted = "F**ckin' Problems";
        if (tempSongTitle.equals("septembershort"))
            formatted = "September";
        if (tempSongTitle.equals("threethousandfive"))
            formatted = "3005";
        if (tempSongTitle.equals("trapqueenshort"))
            formatted = "Trap Queen";
        if (tempSongTitle.equals("ultralight"))
            formatted = "Ultralight Beam";
        if (tempSongTitle.equals("uptownfunkshort"))
            formatted = "Uptown Funk";
        return formatted;
    }

    private String getArtistName(String tempSongTitle) {
        String artist = "";
        if (tempSongTitle.equals("anaconda"))
            artist = "Nicki Minaj";
        if (tempSongTitle.equals("cocoabutterkisses"))
            artist = "Chance the Rapper";
        if (tempSongTitle.equals("father"))
            artist = "Kanye West";
        if (tempSongTitle.equals("ishort"))
            artist = "Kendrick Lamar";
        if (tempSongTitle.equals("myhumps"))
            artist = "The Black Eyed Peas";
        if (tempSongTitle.equals("problemsshort"))
            artist = "A$AP Rocky";
        if (tempSongTitle.equals("septembershort"))
            artist = "Earth, Wind & Fire";
        if (tempSongTitle.equals("threethousandfive"))
            artist = "Childish Gambino";
        if (tempSongTitle.equals("trapqueenshort"))
            artist = "Fetty Wap";
        if (tempSongTitle.equals("ultralight"))
            artist = "Kanye West";
        if (tempSongTitle.equals("uptownfunkshort"))
            artist = "Bruno Mars & Mark Ronson";
        return artist;
    }

    public static SongSingleton get (Context c) {
        if (sSongs == null) {
            sSongs = new SongSingleton(c.getApplicationContext());
        }
        return sSongs;
    }

    public Firebase getReference(Song song) {
        return individualReference;
    }

    public ArrayList<Song> getSongs() {
        return mSongs;
    }

    public Song getSong(int index) {
        return mSongs.get(index);
    }

    public Song getSongFromKey(String key) {
        return mKeyMap.get(key);
    }

    //private ArrayList<Integer> getSongIDS() {return mSongIDS;}

    public void addSong(Song song) {
        mSongs.add(song);
    }

    public void removeSong(int position) {
        mSongs.remove(position);
    }

    public void updateSong(int position, Song song) {
        if (position >= 0 && position < mSongs.size()) {
            mSongs.set(position, song);
        }
    }

}
