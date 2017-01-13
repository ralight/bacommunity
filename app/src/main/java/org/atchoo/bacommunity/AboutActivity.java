package org.atchoo.bacommunity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.text.method.LinkMovementMethod;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView aboutView =(TextView)findViewById(R.id.about_text);
        aboutView.setClickable(true);
        aboutView.setMovementMethod(LinkMovementMethod.getInstance());
        aboutView.setText(Html.fromHtml("Don't have enough community in your life? Get some insight from the king of community, Jono Bacon!<hr><br><br>Audio is taken from various <a href=\"http://badvoltage.org/\">Bad Voltage</a> episodes under the <a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">Creative Commons Attribution Share-Alike</a> license.<br><br>Image from <a href=\"https://jonobacon.org/\">jonobacon.org</a> with permission.<br><br>Bacommunity was created by Roger Light <a href=\"http://twitter.com/ralight\">@ralight</a>.<br><br><a href=\"https://github.com/ralight/bacommunity\">https://github.com/ralight/bacommunity</a>"));
    }
}
