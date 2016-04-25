package androidapi.pop.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by pop on 2016/4/25.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = BaseActivity.class.getSimpleName();
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClassString() + " create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    protected void initView() {
        textView = (TextView) this.findViewById(R.id.show_text);
        button = (Button) this.findViewById(R.id.goto_btn);
        button.setOnClickListener(this);

        initViewValue();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goto_btn:
                gotoSpecifyActivity();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, getClassString() + " destroy");
        super.onDestroy();
    }

    private String getClassString() {
        String str = this.toString();
        return this.getClass().getSimpleName() + ":" + str.substring(str.indexOf("@"));
    }

    abstract void initViewValue();

    abstract void gotoSpecifyActivity();
}
