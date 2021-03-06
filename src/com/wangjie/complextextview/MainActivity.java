package com.wangjie.complextextview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.*;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {
    private Context context;
    private TextView text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = this;
        text = (TextView) findViewById(R.id.text);

        String a = "@wangjie";
        NoLineClickSpan span = new NoLineClickSpan(context, a);

//        String html = "hello world use complex text";
        SpannableString sstr = new SpannableString(a + " he/:exp_0006llo!!! world use complex /:exp_0001 text, " +
                "hello world use com/:exp_0002plex text, " +
                "hello /:exp_0003 world use complex text, " +
                "hello wor/:exp_0004ld use co/:exp_0005mplex text");

        sstr.setSpan(span, 0, a.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        sstr.setSpan(new BackgroundColorSpan(Color.parseColor("#D1E9E9")), 0, a.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Matcher matcher = Pattern.compile("/:exp_.{4}").matcher(sstr.toString());
        while(matcher.find()){
            String baseName = matcher.group();
            System.out.println("find: " + baseName);
            System.out.println("start: " + matcher.start());
            if(null == baseName || baseName.isEmpty()){
                return;
            }
            int res = getResources().getIdentifier(baseName.substring(2), "raw", context.getPackageName());
            System.out.println("res: " + res);
            Drawable d = getResources().getDrawable(res);
            d.setBounds(0, 0, 70, 70);
            int start = matcher.start();
            int end = matcher.end();

            sstr.setSpan(new MDrawableSpan(d), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//            sstr.setSpan(new ForegroundColorSpan(Color.parseColor("#0066CC")),start, end,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }


        text.setMovementMethod(LinkMovementMethod.getInstance());
        text.setText(sstr);

    }







}
