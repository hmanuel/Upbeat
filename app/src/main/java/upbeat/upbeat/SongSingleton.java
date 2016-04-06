package upbeat.upbeat;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Chandler on 4/6/16.
 */
public class SongSingleton {
    private ArrayList<Song> mSongs;
    private Context mAppContext;
    private static SongSingleton sSongs;

    private SongSingleton (Context c) {
        mAppContext = c;
        mSongs = new ArrayList<>();
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
