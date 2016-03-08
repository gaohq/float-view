package cn.gg.floating_window.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import cn.gg.floating_window.provider.FloatingProvider;
import com.example.gg_foating_window.R;

/**
 * Created by gaohaoqing
 * Date : 15/11/19
 * Time : 15:09
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button start_id = (Button) findViewById(R.id.start_id);
        start_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatingProvider.startService(getApplicationContext(), R.layout.float_layout, R.id.float_id);
            }
        });
        Button remove_id = (Button) findViewById(R.id.remove_id);
        remove_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatingProvider.stopService(getApplicationContext());
            }
        });
    }
}
