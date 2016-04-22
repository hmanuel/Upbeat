package upbeat.upbeat;

import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class UpActivity extends AppCompatActivity {

    public static final String TAG = UpActivity.class.getSimpleName();
    ListView mListView;
    FloatingActionButton playButton;
    TextView currentSongTextView;
    TextView currentArtistTextView;

    ArrayList<Song> mSongs;
    SongAdapter mAdapter;

    MediaPlayer mediaPlayer;
    MediaPlayer.OnCompletionListener doneListener;
    boolean firstSongStarted;
    int songPosition;

    private long temp;

    private Firebase reference;
    private Firebase individualReference;
    private Firebase listenerReference;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up);

        mListView = (ListView) findViewById(R.id.songs_list);
        playButton = (FloatingActionButton) findViewById(R.id.play_button);
        firstSongStarted = false;
        currentSongTextView = (TextView) findViewById(R.id.current_song_textView);
        currentArtistTextView = (TextView) findViewById(R.id.current_artist_textView);

        reference = ((UpbeatApplication) getApplication()).getMyMainReference().child("songs");
        listenerReference = ((UpbeatApplication) getApplication()).getMyMainReference().child("listener");

        final SongSingleton s = SongSingleton.get(getApplicationContext());

        mSongs = s.getSongs();
        mAdapter = new SongAdapter(this, mSongs);
        mListView.setAdapter(mAdapter);

        currentSongTextView.setText(mSongs.get(0).getFormattedTitle());
        currentArtistTextView.setText(mSongs.get(0).getArtistName());
        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), s.getSong(0).getSongID());

        sort();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Song song;
                song = SongSingleton.get(getApplicationContext()).getSong(position);
                //song.setUpbeats(song.getUpbeats() + 1);
                key = song.getKey();
                //Log.d(TAG, "key is: " + key);
                individualReference = reference.child(key).child("upbeats");
                individualReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        temp = (Long) dataSnapshot.getValue();
                        temp++;
                        song.setUpbeats(temp);
                        individualReference.setValue(temp);
                        sort();
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                song.getUpbeats();
                sort();
                mAdapter.notifyDataSetChanged();
                currentSongTextView.setText(mSongs.get(0).getFormattedTitle());
                currentArtistTextView.setText(mSongs.get(0).getArtistName());
            }
        });


        // for second version!
//        listenerReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (((Long) dataSnapshot.getValue()==1)) {
//
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });

        playButton.setOnClickListener(new View.OnClickListener() { //only starts it, doesn't play / pause
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {
                    listenerReference.setValue(1); // tell Firebase that the song is playing
                    if (!firstSongStarted) {
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), s.getSong(0).getSongID());
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
                    listenerReference.setValue(0); // tell Firebase that the song is paused
                    mediaPlayer.pause();
                    songPosition = mediaPlayer.getCurrentPosition();
                    playButton.setImageDrawable(getDrawable(R.drawable.ic_play_arrow_white_48px));
                }
            }
        });

        doneListener = new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {
                // Toast.makeText(getApplicationContext(), "Media Completed", Toast.LENGTH_SHORT).show();
                // remove top song from list
                s.removeSong(0);
                mAdapter.notifyDataSetChanged();
                playButton.setImageDrawable(
                        getDrawable(R.drawable.ic_play_arrow_white_48px));
                //start playing the top song
                if (s.getSongs().size() > 0) {
                    Song playNow = SongSingleton.get(getApplicationContext()).getSong(0);
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), playNow.getSongID());
                    mediaPlayer.start();
                    playButton.setImageDrawable(
                            getDrawable(R.drawable.ic_pause_white_48px));
                    // this needs to be here to play through each song
                    mediaPlayer.setOnCompletionListener(doneListener);
                    mAdapter.notifyDataSetChanged();
                    currentSongTextView.setText(playNow.getFormattedTitle());
                    currentArtistTextView.setText(playNow.getArtistName());
                }
            }
        };
    }

    /*private void populateSongIDS() throws IllegalAccessException {
        int song_id;
        Field[] fields = R.raw.class.getFields();
        for (int i = 0; i < fields.length; i++) {
            songIDS.add(fields[i].getInt(fields[i]));
            song_id = fields[i].getInt(fields[i]);
            Log.d(TAG, "Song ID: " + String.valueOf(songIDS.get(i)));
            mSongs.get(i).setSongID(song_id);
        }
    }*/

    private void sort() {
        Song swapMe;
        for (int i = 0; i < mSongs.size(); i++ ) {
            for (int j = i; j< mSongs.size(); j++) {
                if (mSongs.get(i).getUpbeats() < mSongs.get(j).getUpbeats()) {
                    swapMe = mSongs.get(i);
                    mSongs.set(i, mSongs.get(j));
                    mSongs.set(j, swapMe);
                }
            }
        }

        mAdapter.notifyDataSetChanged();

        /*Song song2 = SongSingleton.get(getApplicationContext()).getSong(position - 1);
        while (song.getUpbeats() > song2.getUpbeats()) {
            s.updateSong(position - 1, song);
            s.updateSong(position, song2);
            position = position - 1;
            if (position - 1 >= 0)
                song2 = SongSingleton.get(getApplicationContext()).getSong(position - 1);
            else
                break;
        }*/

    }

}
