package kz.qstore.shafran;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SendMailActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        final Button send = (Button) this.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                Log.d("SEND", "Send Button Pressed");

                try {
                    GMailSender sender = new GMailSender("yeezy1881@gmail.com", "Xomap4ww");

                    sender.sendMail("asdfasdfasdfasdf",
                            "sdgfsdfgfsdfgsdfgsdgf",
                            "yeezy1881@gmail.com",
                            "browny1880@gmail.com");
                    Log.d("SENDING", "ALL OK");
                } catch (Exception e) {
                    Log.d("SendMail", e.getMessage(), e);
                }

            }
        });

    }

}