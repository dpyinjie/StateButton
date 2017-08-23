package deadline.statebutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import dpyinjie.statebutton.StateButton;

public class MainActivity extends AppCompatActivity {

    StateButton text;

    StateButton background;

    StateButton radius;

    StateButton stroke;

    StateButton dash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置不同状态下文字变色
        text = (StateButton) findViewById(R.id.text_test);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setEnabled(!text.isEnabled());
            }
        });

        //最常用的设置不同背景
        background = (StateButton) findViewById(R.id.background_test);
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                background.setEnabled(!background.isEnabled());
            }
        });

        //设置四个角不同的圆角
        radius = (StateButton) findViewById(R.id.different_radius_test);
        radius.setRadius(new float[]{0, 0, 20, 20, 40, 40, 60, 60});


        //设置不同状态下边框颜色，宽度
        stroke = (StateButton) findViewById(R.id.stroke_test);
        stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stroke.setEnabled(!stroke.isEnabled());
            }
        });

        //设置间断
        dash = (StateButton) findViewById(R.id.dash_test);
        dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dash.setEnabled(!dash.isEnabled());
            }
        });

        findViewById(R.id.test).setSelected(true);
    }
}
