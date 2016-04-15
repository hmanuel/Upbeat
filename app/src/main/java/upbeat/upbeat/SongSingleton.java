package upbeat.upbeat;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Chandler on 4/6/16.
 */
public class SongSingleton {
    private ArrayList<Song> mSongs;
    private ArrayList<Integer> msongIDS;
    private Context mAppContext;
    private static SongSingleton sSongs;

    private SongSingleton (Context c) {
        mAppContext = c;
        mSongs = new ArrayList<>();
        msongIDS = new ArrayList<>(); // temporary fix. bad practice to have ids separate
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
    public ArrayList<Integer> getSongIDS() {return msongIDS;}
    public void addSong(Song song) {
        mSongs.add(song);
    }

    public void removeSong(int position) {
        mSongs.remove(position);
        msongIDS.remove(position);
    }

    public void updateSong(int position, Song song) {
        if (position >= 0 && position < mSongs.size()) {
            mSongs.set(position, song);
        }
    }

    public void populateSongIDS() throws IllegalAccessException {
        int song_id;
        Field[] fields=R.raw.class.getFields();
        for(int i=0; i < fields.length; i++){
            msongIDS.add(fields[i].getInt(fields[i]));
            song_id = fields[i].getInt(fields[i]);
            //Log.d(TAG, "Song ID: " + String.valueOf(songIDS.get(i)));
            mSongs.get(i).setSongID(song_id);
        }
    }
}
