package upbeat.upbeat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PlaySongActivity extends AppCompatActivity {

    Button playAnacondaButton;
    Button humpsButton;
    Button otherButton;
    Button fatherButton;
    MediaPlayer mediaPlayer;
    MediaPlayer.OnCompletionListener doneListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        playAnacondaButton = (Button) findViewById(R.id.button0);
        humpsButton = (Button) findViewById(R.id.button1);
        otherButton = (Button) findViewById(R.id.button2);
        fatherButton= (Button) findViewById(R.id.button3);
        mediaPlayer = new MediaPlayer();
        doneListener = new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getApplicationContext(), "Media Completed", Toast.LENGTH_SHORT).show();
            }
        };
        mediaPlayer.setOnCompletionListener(doneListener);

        playAnacondaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song1);
                mediaPlayer.start();
            }
        }
        );

        humpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song3);
                mediaPlayer.start();
            }
        });

        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpActivity.class);
                startActivity(intent);
            }
        });

        fatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song4);
                mediaPlayer.start();
            }
        });
    }

}
