package upbeat.upbeat;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Chandler on 4/6/16.
 */
public class SongSingleton {
    private ArrayList<Song> mSongs;
    private ArrayList<Integer> mSongIDS;
    private Context mAppContext;
    private static SongSingleton sSongs;

    public static final String TAG = UpActivity.class.getSimpleName();

    private SongSingleton (Context c) {
        mAppContext = c;
        mSongs = new ArrayList<>();
        mSongIDS = new ArrayList<>(); // temporary fix. bad practice to have ids separate
        for (int i=1; i<=20; i++) {
            Song song = new Song();
            song.setTitle("Song " + i);
            mSongs.add(song);
        }
    }

    public static SongSingleton get (Context c) {
        if (sSongs == null) {
            sSongs = new SongSingleton(c.getApplicationContext());
        }
        return sSongs;
    }

    public ArrayList<Song> getSongs() {
        return mSongs;
    }
    public Song getSong(int index) {
        return mSongs.get(index);
    }
    public ArrayList<Integer> getSongIDS() {return mSongIDS;}
    public void addSong(Song song) {
        mSongs.add(song);
    }

    public void removeSong(int position) {
        mSongs.remove(position);
        mSongIDS.remove(position);
    }

    public void updateSong(int position, Song song) {
        if (position >= 0 && position < mSongs.size()) {
            mSongs.set(position, song);
        }
    }

    public void populateSongIDS() throws IllegalAccessException, IOException {
        int song_id;
        String songName;
        //AssetManager assetManager = mAppContext.getResources().getAssets();
        Field[] fields = R.raw.class.getFields();
        for(int i=0; i < fields.length-1; i++){ //because Fields has an extraneous field
            //i + 1 because it was put in like song1, song2.... can't believe this was the prob lol
            songName = "song" + (i+1);
            song_id = mAppContext.getResources().getIdentifier(songName, "raw", mAppContext.getPackageName());
            mSongIDS.add(song_id);
            Log.d(TAG, "Song ID: " + String.valueOf(mSongIDS.get(i)));
            mSongs.get(i).setSongID(song_id);
        }
    }
}
