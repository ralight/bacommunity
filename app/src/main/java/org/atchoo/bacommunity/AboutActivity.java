/*
This file is part of Bacommunity.

Bacommunity is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Foobar is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Foobar.  If not, see <http://www.gnu.org/licenses/>.

*/
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
