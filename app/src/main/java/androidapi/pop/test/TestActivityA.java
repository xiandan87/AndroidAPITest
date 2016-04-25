package androidapi.pop.test;

import android.content.Intent;

/**
 * Created by pop on 2016/4/25.
 */
public class TestActivityA extends BaseActivity {
    @Override
    void initViewValue() {
        textView.setText(this.getClass().getSimpleName());
        button.setText("goto ActivityB");  //test1
//        button.setText("goto ActivityA");  //test2
    }

    @Override
    void gotoSpecifyActivity() {
//        Intent intent = new Intent(this,TestActivityB.class);   //test1
//        Intent intent = new Intent(this, TestActivityA.class);  //test2
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //test2
        Intent intent = new Intent("pop.test.activityb.action"); //test4
        startActivity(intent);
    }
}
