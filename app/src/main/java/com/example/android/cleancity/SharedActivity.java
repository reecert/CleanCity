package com.example.android.cleancity;

import androidx.appcompat.app.AppCompatActivity;
import java.io.FileNotFoundException;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SharedActivity extends AppCompatActivity {
    private static ImageView sharedImageView;
    private static TextView sharedTextView, shared_textview_title,
            shared_imageview_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);
        sharedImageView = (ImageView) findViewById(R.id.shared_imageview);
        sharedTextView = (TextView) findViewById(R.id.shared_textview);
        shared_imageview_title = (TextView) findViewById(R.id.shared_imageview_title);

        // Call shared intent
        onSharedIntent();
    }

    // Shared intent method
    private void onSharedIntent() {
        Intent receiverdIntent = getIntent();// Receive intent
        String receivedAction = receiverdIntent.getAction();// Get Action from
        // receive intent
        String receivedType = receiverdIntent.getType();// Get type from receive
        // intent

        // If action is equal to action send
        if (receivedAction.equals(Intent.ACTION_SEND)) {

            // If nothing shared
            sharedTextView.setText("Nothing has shared");
            sharedTextView.setVisibility(View.VISIBLE);
            shared_textview_title.setVisibility(View.GONE);
            sharedImageView.setVisibility(View.GONE);
            shared_imageview_title.setVisibility(View.GONE);

            // If mime type is equal to text then show text and hide imageview
            if (receivedType.startsWith("text/")) {
                sharedImageView.setVisibility(View.GONE);
                shared_imageview_title.setVisibility(View.GONE);
                String receivedText = receiverdIntent
                        .getStringExtra(Intent.EXTRA_TEXT);
                if (receivedText != null) {
                    sharedTextView.setText(receivedText);
                    sharedTextView.setVisibility(View.VISIBLE);
                    shared_textview_title.setVisibility(View.VISIBLE);
                }
            }
            // If mime type is equal to image then show image and hide textview
            else if (receivedType.startsWith("image/")) {
                sharedTextView.setVisibility(View.GONE);
                shared_textview_title.setVisibility(View.GONE);
                Uri receiveUri = (Uri) receiverdIntent
                        .getParcelableExtra(Intent.EXTRA_STREAM);

                if (receiveUri != null) {
                    try {
                        Bitmap bitmap = Utils.decodeUri(SharedActivity.this,
                                receiveUri, 200);
                        sharedImageView.setImageBitmap(bitmap);
                        sharedImageView.setVisibility(View.VISIBLE);
                        shared_imageview_title.setVisibility(View.VISIBLE);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else if (receivedAction.equals(Intent.ACTION_MAIN)) {
            sharedTextView.setText("Nothing has shared");
            sharedTextView.setVisibility(View.VISIBLE);
            shared_textview_title.setVisibility(View.GONE);
            sharedImageView.setVisibility(View.GONE);
            shared_imageview_title.setVisibility(View.GONE);
        }
    }

}