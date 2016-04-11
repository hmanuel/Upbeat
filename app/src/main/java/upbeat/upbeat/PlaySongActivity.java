package upbeat.upbeat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PlaySongActivity extends AppCompatActivity {

    Button playAnacondaButton;
    Button otherButton;
    Button humpsButton;
    MediaPlayer mediaPlayer;
    MediaPlayer.OnCompletionListener doneListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        playAnacondaButton = (Button) findViewById(R.id.button0);
        otherButton = (Button) findViewById(R.id.button1);
        humpsButton = (Button) findViewById(R.id.button2);
        mediaPlayer = new MediaPlayer();
        doneListener = new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getApplicationContext(), "Media Completed", Toast.LENGTH_SHORT).show();
            }
        };

        playAnacondaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw._3005);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(doneListener);
            }
        }
        );

        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpActivity.class);
                startActivity(intent);
            }
        });

        humpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.myhumps);
                mediaPlayer.start();
            }
        });




    }

}
