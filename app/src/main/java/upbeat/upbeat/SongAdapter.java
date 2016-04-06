package upbeat.upbeat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chandler on 4/6/16.
 */
public class SongAdapter extends ArrayAdapter<Song> {
    public SongAdapter(Context c, ArrayList<Song> song) {
        super(c,0,song);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Song song = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.song_view, parent, false);
        }

        TextView songTitle = (TextView) convertView.findViewById(R.id.song_title);
        TextView songUpbeats = (TextView) convertView.findViewById(R.id.song_upbeats);

        songTitle.setText(song.getTitle());
        songUpbeats.setText(String.valueOf(song.getUpbeats()));

        return convertView;

    }

}
