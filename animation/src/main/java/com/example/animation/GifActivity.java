package com.example.animation;

import java.io.InputStream;

import com.example.animation.util.GifImage;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by ouyangshen on 2017/11/27.
 */
public class GifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        showGifAnimation(); // 显示GIF动画
    }

    // 显示GIF动画
    private void showGifAnimation() {
        // 从布局文件中获取名叫iv_gif的图像视图
        ImageView iv_gif = findViewById(R.id.iv_gif);
        // 从资源文件welcome.gif中获取输入流对象
        InputStream is = getResources().openRawResource(R.raw.welcome);
        // 创建一个GIF图像对象
        GifImage gifImage = new GifImage();
        // 从输入流中读取gif数据
        int code = gifImage.read(is);
        if (code == GifImage.STATUS_OK) { // 读取成功
            GifImage.GifFrame[] frameList = gifImage.getFrames();
            // 创建一个帧动画
            AnimationDrawable ad_gif = new AnimationDrawable();
            for (GifImage.GifFrame frame : frameList) {
                // 把Bitmap位图对象转换为Drawable图形格式
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), frame.image);
                // 给帧动画添加指定图形，以及该帧的播放延迟
                ad_gif.addFrame(bitmapDrawable, frame.delay);
            }
            // 设置帧动画是否只播放一次。为true表示只播放一次，为false表示循环播放
            ad_gif.setOneShot(false);
            // 设置图像视图的图形为帧动画
            iv_gif.setImageDrawable(ad_gif);
            ad_gif.start(); // 开始播放帧动画
        } else if (code == GifImage.STATUS_FORMAT_ERROR) {
            Toast.makeText(this, "该图片不是gif格式", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "gif图片读取失败:" + code, Toast.LENGTH_LONG).show();
        }
    }

}