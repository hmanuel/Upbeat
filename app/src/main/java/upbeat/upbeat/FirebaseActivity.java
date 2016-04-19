package upbeat.upbeat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

public class FirebaseActivity extends AppCompatActivity {

    private Firebase upbeatReference1;
    private Firebase upbeatReference2;
    private Firebase upbeatReference3;

    Button mButton1;
    Button mButton2;
    Button mButton3;

    TextView upbeats1;
    TextView upbeats2;
    TextView upbeats3;

    long upbeats1counter=0;
    long upbeats2counter=0;
    long upbeats3counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);

        upbeats1 = (TextView) findViewById(R.id.upbeats1);
        upbeats2 = (TextView) findViewById(R.id.upbeats2);
        upbeats3 = (TextView) findViewById(R.id.upbeats3);

        upbeatReference1 = ((UpbeatApplication) getApplication()).getMyMainReference().child("upbeats").child("song1");
        upbeatReference2 = ((UpbeatApplication) getApplication()).getMyMainReference().child("upbeats").child("song2");
        upbeatReference3 = ((UpbeatApplication) getApplication()).getMyMainReference().child("upbeats").child("song3");

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upbeats1counter++;
                upbeatReference1.setValue(upbeats1counter);

                upbeatReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        upbeats1counter = (Long) dataSnapshot.getValue();
                        upbeats1.setText(String.valueOf(upbeats1counter));
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });


    }
}
