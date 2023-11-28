package com.senior.healthcare;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class enterScreen extends Activity {
    private static final long SPLASH_TIME_OUT = 1000; // 1초

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_screen);

        // 로딩 이미지 회전 애니메이션 적용
        applyRotationAnimation();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(getApplicationContext(), birthdate.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void applyRotationAnimation() {
        ImageView loadingImageView = findViewById(R.id.loadingImageView);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);  // 회전 애니메이션의 지속 시간 (밀리초)
        rotateAnimation.setRepeatCount(Animation.INFINITE);  // 무한 반복
        loadingImageView.startAnimation(rotateAnimation);
    }
}
