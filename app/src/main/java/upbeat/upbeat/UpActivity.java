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

        SongSingleton s = SongSingleton.get(getApplicationContext());
        mSongs = s.getSongs();

        mAdapter = new SongAdapter(this, mSongs);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song;
                song = SongSingleton.get(getApplicationContext()).getSong(position);
                song.setUpbeats(song.getUpbeats()+1);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
