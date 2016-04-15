package upbeat.upbeat;

import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class UpActivity extends AppCompatActivity {

    public static final String TAG = UpActivity.class.getSimpleName();
    ListView mListView;
    FloatingActionButton playButton;

    ArrayList<Song> mSongs;
    ArrayList<Integer> songIDS;
    SongAdapter mAdapter;

    MediaPlayer mediaPlayer;
    MediaPlayer.OnCompletionListener doneListener;
    boolean firstSongStarted;
    int songPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up);

        mListView = (ListView) findViewById(R.id.songs_list);
        playButton = (FloatingActionButton) findViewById(R.id.play_button);
        firstSongStarted = false;

        final SongSingleton s = SongSingleton.get(getApplicationContext());
        try {
            s.populateSongIDS();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mSongs = s.getSongs();
        songIDS = new ArrayList<>();

        mAdapter = new SongAdapter(this, mSongs);
        mListView.setAdapter(mAdapter);
        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), s.getSongIDS().get(0));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song;
                song = SongSingleton.get(getApplicationContext()).getSong(position);
                song.setUpbeats(song.getUpbeats() + 1);
                mAdapter.notifyDataSetChanged();
                if(position > 0)
                    sort(song, position, s);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() { //only starts it, doesn't play / pause
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {
                    if (!firstSongStarted) {
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(doneListener);
                        firstSongStarted = true;
                        playButton.setImageDrawable(
                                getDrawable(R.drawable.ic_pause_white_48px));
                    } else {
                        mediaPlayer.seekTo(songPosition);
                        mediaPlayer.start();
                        playButton.setImageDrawable(
                                getDrawable(R.drawable.ic_pause_white_48px));
                    }
                } else if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    songPosition = mediaPlayer.getCurrentPosition();
                    playButton.setImageDrawable(getDrawable(R.drawable.ic_play_arrow_white_48px));
                }
            }
        });

        doneListener = new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getApplicationContext(), "Media Completed", Toast.LENGTH_SHORT).show();
                // remove top song from list
                s.removeSong(0);
                //start playing the top song
                if (s.getSongIDS().size() > 0) {
                    Song playNow = SongSingleton.get(getApplicationContext()).getSong(0);
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), playNow.getSongID());
                    mediaPlayer.start();
                    // this needs to be here to play through each song
                    mediaPlayer.setOnCompletionListener(doneListener);
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    /*private void populateSongIDS() throws IllegalAccessException {
        int song_id;
        Field[] fields=R.raw.class.getFields();
        for(int i=0; i < fields.length; i++){
            songIDS.add(fields[i].getInt(fields[i]));
            song_id = fields[i].getInt(fields[i]);
            Log.d(TAG, "Song ID: " + String.valueOf(songIDS.get(i)));
            mSongs.get(i).setSongID(song_id);
        }
    }*/

    public void sort(Song song, int position, SongSingleton s) {
        Song song2 = SongSingleton.get(getApplicationContext()).getSong(position-1);
        while (song.getUpbeats()>song2.getUpbeats()) {
            s.updateSong(position-1, song);
            s.updateSong(position, song2);
            position = position - 1;
            if (position - 1 >= 0)
                song2 = SongSingleton.get(getApplicationContext()).getSong(position-1);
            else
                break;
        }
        mAdapter.notifyDataSetChanged();
    }

}
