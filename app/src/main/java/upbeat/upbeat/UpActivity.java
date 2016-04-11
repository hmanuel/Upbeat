package upbeat.upbeat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class UpActivity extends AppCompatActivity {

    ListView mListView;
    ArrayList<Song> mSongs;
    SongAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up);

        mListView = (ListView) findViewById(R.id.songs_list);

        final SongSingleton s = SongSingleton.get(getApplicationContext());
        mSongs = s.getSongs();

        mAdapter = new SongAdapter(this, mSongs);
        mListView.setAdapter(mAdapter);

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
    }

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
