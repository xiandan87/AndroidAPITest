package androidapi.pop.test;

import android.content.Intent;

/**
 * Created by pop on 2016/4/25.
 */
public class TestActivityB extends BaseActivity {
    @Override
    void initViewValue() {
        textView.setText(this.getClass().getSimpleName());
        button.setText("goto MainActivity");
    }

    @Override
    void gotoSpecifyActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
