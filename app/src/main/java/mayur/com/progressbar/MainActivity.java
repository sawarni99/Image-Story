package mayur.com.progressbar;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Initialization
    int count;
    long totalTime;
    int i;
    long displayTime;
    ImageButton imageButtonRight, imageButtonLeft;
    ImageView image;
    ArrayList<Integer>imageArray = new ArrayList<>();
    ArrayList<ProgressBar>progressArray = new ArrayList<>();
    CountDownTimer timer;
    View.OnTouchListener holdRelease;

    //onCreate function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Tag", "onCreate");

        //Adding images to array
        imageArray.add(R.drawable.image0);
        imageArray.add(R.drawable.image1);
        imageArray.add(R.drawable.image2);

        //Adding progress bar to array
        progressArray.add((ProgressBar) findViewById(R.id.progressBar0));
        progressArray.add((ProgressBar) findViewById(R.id.progressBar1));
        progressArray.add((ProgressBar) findViewById(R.id.progressBar2));

        //Initializing objects by their id
        image = (ImageView) findViewById(R.id.imageView);
        imageButtonRight = (ImageButton) findViewById(R.id.right_swipe);
        imageButtonLeft = (ImageButton) findViewById(R.id.left_swipe);


        //Action on hold and release
        holdRelease = new View.OnTouchListener() {
            long a;
            long b;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.right_swipe || v.getId() == R.id.left_swipe) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        a = System.currentTimeMillis();
                        timer.cancel();

                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        b = System.currentTimeMillis()- a;
                        imageSlide();

                        //Checking duration of click
                        if(b <= 200){
                            v.performClick();
                        }
                    }
                    return true;
                }
                return false;
            }
        };

        imageButtonLeft.setOnTouchListener(holdRelease);
        imageButtonRight.setOnTouchListener(holdRelease);

    }


    //On click of left side of the image
    public void onClick_LeftSwipe(View view) {
        timer.cancel();

        //Reseting the values
        count = 0;
        displayTime = totalTime;
        progressArray.get(i).setProgress(0);

        //Decrement counter
        if(i != 0){
            i--;
            Log.d("Tag", Integer.toString(i));
        }

        //Setting ImageView to new Image
        image.setImageResource(imageArray.get(i));
        imageSlide();
    }


    //On click of Right side of the image
    public void onClick_RightSwipe(View view) {
        timer.cancel();

        //Reseting the values
        count = 0;
        displayTime = totalTime;
        progressArray.get(i).setProgress(100);

        //Increment counter
        if(i != imageArray.size()-1){
            i++;
            Log.d("Tag", Integer.toString(i));
            imageSlide();
        }

        //Setting ImageView to new Image
        image.setImageResource(imageArray.get(i));
    }


    //Timer to control imageSliding every 3 seconds..
    public void imageSlide(){
        //Timer for Sliding Image
        timer = new CountDownTimer(displayTime, 30) {

            //Function on time Interval of 30 millisec.
            @Override
            public void onTick(long l) {
                count++;
                progressArray.get(i).setProgress(count);
                displayTime = l;
            }

            //Function of completing the timer
            @Override
            public void onFinish() {
                progressArray.get(i).setProgress(100);
                if(i != imageArray.size()-1){
                    i++;
                }else{
                    return;
                }

                Log.d("Tag", Integer.toString(i));

                //Setting ImageView to new Image
                image.setImageResource(imageArray.get(i));

                //Resetting the values
                count=0;
                displayTime = totalTime;
                imageSlide();

            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Tag", "onPause");
        timer.cancel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Tag", "onStart");

        //Initializations
        i = 0;
        count = 0;
        totalTime = 3000;
        displayTime = totalTime;

        //Setting 1st image
        image.setImageResource(imageArray.get(0));

        //Setting all Progress Bar to zero
        for(int j=0; j<progressArray.size(); j++) {
            progressArray.get(j).setProgress(0);
        }
        //Initially starting the timer to automatically swipe images
        imageSlide();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Tag", "onStop");
    }
}
