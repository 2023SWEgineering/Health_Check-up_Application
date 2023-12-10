package com.senior.healthcare.searching;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.senior.healthcare.Main;
import com.senior.healthcare.R;
import com.senior.healthcare.setting.ApplicationSetting;
import com.senior.healthcare.setting.UserType;

import java.util.LinkedList;
import java.util.List;


//자신이 어떤 건강검진을 받을 수 있는지 리스트를 보여줌
public class HealthCheckList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_check_list);
        LinearLayout loadingLayout = findViewById(R.id.loadingLayout);

        List<String> healthCheckTypeList = getHealthCheckType();

        // 로딩 이미지 회전 애니메이션 적용
        applyRotationAnimation();
        ImageView back_icon = findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Thread 실행
        new Thread(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingLayout.setVisibility(View.GONE);
                        // 파싱된 결과를 사용하여 버튼 동적 생성
                        createButtons(healthCheckTypeList);
                    }
                });
            }
        }).start();
    }
    public List<String> getHealthCheckType(){
        UserType userType = ApplicationSetting.getUserType();
        List<String> list = new LinkedList<>();
        switch (userType){
            case MEN20UP :
                list.add("일반");
                break;
            case WOMEN20UP :
                list.add("일반");
                list.add("자궁경부암");
                break;
            case MEN40UP :
                list.add("일반");
                list.add("위암");
                list.add("간암");
                break;
            case WOMEN40UP :
                list.add("일반");
                list.add("자궁경부암");
                list.add("간암");
                list.add("유방암");
                list.add("위암");
                break;
            case MEN50UP :
                list.add("일반");
                list.add("위암");
                list.add("간암");
                list.add("대장암");
                break;
            case WOMEN50UP :
                list.add("일반");
                list.add("간암");
                list.add("위암");
                list.add("대장암");
                list.add("자궁경부암");
                list.add("유방암");
                break;
            case MENLASTGEN :
                list.add("일반");
                list.add("간암");
                list.add("위암");
                list.add("대장암");
                list.add("폐암");
                break;
            case WOMENLASTGEN :
                list.add("일반");
                list.add("간암");
                list.add("위암");
                list.add("대장암");
                list.add("폐암");
                list.add("자궁경부암");
                list.add("유방암");
                break;
        }
        return list;
    }

    private void applyRotationAnimation() {
        ImageView loadingImageView = findViewById(R.id.loadingImageView);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);  // 회전 애니메이션의 지속 시간 (밀리초)
        rotateAnimation.setRepeatCount(Animation.INFINITE);  // 무한 반복
        loadingImageView.startAnimation(rotateAnimation);
    }


    private void createButtons(List<String> checkList) {
        LinearLayout layout = findViewById(R.id.search);

        // 이미지 리소스 배열
        int[] buttonBackgrounds = {R.drawable.btn_color1, R.drawable.btn_color2, R.drawable.btn_color3,
                R.drawable.btn_color4, R.drawable.btn_color5, R.drawable.btn_color6};

        int imageIndex = 0; // 이미지 인덱스 변수

        for (int i = 0; i < checkList.size(); i++) {
            Button button = new Button(this);

            String btnText = checkList.get(i);

            //글자 길이에 따라 ... 붙이기
            if (btnText.length() > 10) {
                btnText = btnText.substring(0, 10) + "...";
            }
            button.setText(btnText);

            //굵게, 글자 크기 조절
            button.setTypeface(null, Typeface.BOLD);
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.button_text_size));
            // 순서대로 이미지 선택
            button.setBackgroundResource(buttonBackgrounds[imageIndex]);



            //버튼 크기 조절
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(920, 185);

            params.setMargins(0, 0, 0, 40);
            button.setLayoutParams(params);

            String finalBtnText = btnText;
            button.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                if (finalBtnText.equals("일반")){
                    bundle.putString("checkType", "일반");
                }
                else if (finalBtnText.equals("자궁경부암")){
                    bundle.putString("checkType", "자궁경부암");
                }
                else if (finalBtnText.equals("위암")){
                    bundle.putString("checkType", "위암");
                }
                else if (finalBtnText.equals("간암")){
                    bundle.putString("checkType", "간암");
                }
                else if (finalBtnText.equals("유방암")){
                    bundle.putString("checkType", "유방암");
                }
                else if (finalBtnText.equals("대장암")){
                    bundle.putString("checkType", "대장암");
                }
                else if (finalBtnText.equals("폐암")){
                    bundle.putString("checkType", "폐암");
                }
                Intent intent = new Intent(getApplicationContext(), IntroduceCheckType.class);
                intent.putExtras(bundle);// SpecificInfoForCheck 에 어떤 건강검진의 병원 목록인지 전달
                startActivity(intent);
            });

            // 이미지 인덱스 업데이트
            imageIndex = (imageIndex + 1) % buttonBackgrounds.length;

            layout.addView(button);
        }
    }
}
