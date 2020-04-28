package com.ss.example.selectimage;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView ivBigImage;
    private List<int[]> list = new ArrayList<>();
    private SelectImage ivCircle;
    private List<PointBean> mList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        String data = "[{\"startX\": 40,\"endX\": 270,\"startY\": 900,\"endY\": 1300},{\"startX\": 730,\"endX\": 990,\"startY\": 820,\"endY\": 1000},{\"startX\": 310,\"endX\": 690,\"startY\": 900,\"endY\": 1200}]";
        Type type = new TypeToken<ArrayList<PointBean>>() {
        }.getType();
        List<PointBean> pointBeans = null;
        try {
            pointBeans = new Gson().fromJson(data, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        assert pointBeans != null;
        mList.addAll(pointBeans);
    }

    private void initView() {
        ivBigImage = (ImageView) findViewById(R.id.iv_big_image);
        ivCircle = (SelectImage) findViewById(R.id.iv_circle);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        ivBigImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();
                    drawCircle(x, y);
                }
                return false;
            }
        });
    }

    private void drawCircle(int x, int y) {
//        Toast.makeText(MainActivity.this, x + "----" + y, Toast.LENGTH_SHORT).show();
        double screenWidth = RatioUtil.getScreenWidth(this);
        double screenHeight = RatioUtil.getScreenHeight(this);
        //按我手机大小先确定一个坐标点
        double w = RatioUtil.round(screenWidth / 1080.00, 2);
        double h = RatioUtil.round(screenHeight / 2160.00, 2);
        Log.i("niyade", "drawCircle: " + w + "---------" + h);
        for (int i = 0; i < mList.size(); i++) {
            PointBean pointBean = mList.get(i);
            int startX = (int) (pointBean.getStartX() * w);
            int startY = (int) (pointBean.getStartY() * h);
            int endX = (int) (pointBean.getEndX() * w);
            int endY = (int) (pointBean.getEndY() * h);
            Log.i("niyade", "drawCircle: " + startX + "---------" + startY+"----------"+endX+"-----"+endY);
            if (x >= startX && x <= endX && y >= startY && y <= endY) {
                Iterator<int[]> iterator = list.iterator();
                while (iterator.hasNext()) {
                    int[] next = iterator.next();
                    if (next[0] == startX && next[1] == endX && next[2] == startY && next[3] == endY) {
                        return;
                    }
                }
                int radius = (endX - startX) / 2;
                int circleX = startX + (endX - startX) / 2;
                int circleY = startY + (endY - startY) / 2;
                ivCircle.getWz(circleX, circleY, radius);
                playMusic();
                int[] data = {startX, endX, startY, endY};
                list.add(data);
            }
        }
    }

    private void playMusic() {
        String path = "android.resource://" + this.getPackageName() + "/" + R.raw.icon_select;
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            }
        });
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(this, Uri.parse(path));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
