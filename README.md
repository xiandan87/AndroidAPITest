# AndroidAPITest

#1.����MainActivity����ģʽΪsingleTask�Ļ���������activity������ת������������֮�ϵ�activity�Ļ����Ƿ���Ҫ���FLAG_ACTIVITY_CLEAR_TOPʹ�ã�
   ��������Ϊ MainActivity->TestActivityA->TestActivityB->MainActivity
   ͨ�� adb shell dumpsys activity | grep androidapi.pop.test ����鿴ջ��Ϣ

  ��������Ľ���ֱ�Ϊ��
   TaskRecord{21ad56a7 #253 A=androidapi.pop.test, isShadow:false U=0 sz=3}
   Run #7: ActivityRecord{36d8a82f u0 androidapi.pop.test/.TestActivityB, isShadow:false t253}
   Run #6: ActivityRecord{52957c u0 androidapi.pop.test/.TestActivityA, isShadow:false t253}
   Run #5: ActivityRecord{26909026 u0 androidapi.pop.test/.MainActivity, isShadow:false t253}

   TaskRecord{21ad56a7 #253 A=androidapi.pop.test, isShadow:false U=0 sz=1}
   Run #5: ActivityRecord{26909026 u0 androidapi.pop.test/.MainActivity, isShadow:false t253}

   ���Կ���û��FLAG_ACTIVITY_CLEAR_TOP������£�����������MainActivity֮�ϵ�activity�ģ�����˵singleTask����ģʽ���Դ�FLAG_ACTIVITY_CLEAR_TOP��

# 2.����FLAG_ACTIVITY_CLEAR_TOP����standard����ģʽactivity������ô����ִ�н��
   ��������ΪMainActivity->TestActivityA->TestActivityA

   ��־���Ϊ��
   04-25 13:42:42.617 21752-21752/androidapi.pop.test I/BaseActivity: MainActivity:@81de62c create
   04-25 13:42:48.626 21752-21752/androidapi.pop.test I/BaseActivity: TestActivityA:@64aab3e create
   04-25 13:42:51.162 21752-21752/androidapi.pop.test I/BaseActivity: TestActivityA:@206e105a create
   04-25 13:42:51.601 21752-21752/androidapi.pop.test I/BaseActivity: TestActivityA:@64aab3e destroy

   ���ջ��ϢΪ��
    TaskRecord{27bb320c #257 A=androidapi.pop.test, isShadow:false U=0 sz=2}
    Run #6: ActivityRecord{17e43769 u0 androidapi.pop.test/.TestActivityA, isShadow:false t257}
    Run #5: ActivityRecord{28040634 u0 androidapi.pop.test/.MainActivity, isShadow:false t257}

    ���Կ�����ʹ��FLAG_ACTIVITY_CLEAR_TOP��ת��standard����ģʽactivity������activity��ʵ���ִ���ջ��ʱ���������
    ��ǰջ����ʵ��������һ���µ�ʵ������ջ��

# 3.����ʹ��FLAG_ACTIVITY_NEW_TASKʱ�����android:taskAffinityδ�ر�ָ�����Ƿ�����µ�task��������
   ��������MainActivity->TestActivityA

   δ�ر�ָ��taskAffinityʱ��ջ��ϢΪ��
     Running activities (most recent first):
     TaskRecord{3fa1d0b6 #261 A=androidapi.pop.test, isShadow:false U=0 sz=2}
         Run #6: ActivityRecord{318046a7 u0 androidapi.pop.test/.TestActivityA, isShadow:false t261}
         Run #5: ActivityRecord{2cc0c22 u0 androidapi.pop.test/.MainActivity, isShadow:false t261}

   ָ��taskAffinity��ջ��ϢΪ��
     Running activities (most recent first):
     TaskRecord{c9c14fd #263 A=pop.new.task, isShadow:false U=0 sz=1}
         Run #6: ActivityRecord{130ef006 u0 androidapi.pop.test/.TestActivityA, isShadow:false t263}
     TaskRecord{3cc082c4 #262 A=androidapi.pop.test, isShadow:false U=0 sz=1}
         Run #5: ActivityRecord{584e528 u0 androidapi.pop.test/.MainActivity, isShadow:false t262}

   ���Կ���Ĭ�ϲ�ָ��taskAffinityʱ��taskAffinity��ֵ��Ϊ��������ʹ����FLAG_ACTIVITY_NEW_TASK��Ҳ��������task��������

# 4.������ʽ����activity������activity��manifestδ�����κ�category����£��ܷ�����
   ��������ΪMainActivity->TestActivityA->TestActivityB

   δ���õ�ʱ���׳��쳣��
   E/AndroidRuntime: FATAL EXCEPTION: main Process: androidapi.pop.test, PID: 20323
          android.content.ActivityNotFoundException: No Activity found to handle Intent { act=pop.test.activityb.action }

   ����<category android:name="android.intent.category.DEFAULT"/>��
       ����������TestActivityB����ջ��ϢΪ
        TaskRecord{388f10de #267 A=pop.new.task, isShadow:false U=0 sz=2}
            Run #7: ActivityRecord{3889c446 u0 androidapi.pop.test/.TestActivityB, isShadow:false t267}
            Run #6: ActivityRecord{210f6fc3 u0 androidapi.pop.test/.TestActivityA, isShadow:false t267}
        TaskRecord{15c202bf #266 A=androidapi.pop.test, isShadow:false U=0 sz=1}
            Run #5: ActivityRecord{22c296c0 u0 androidapi.pop.test/.MainActivity, isShadow:false t266}

   ���Կ�����ʽ����activity����activity����Ҫ����category����ƥ�䵽����ջ��Ϣ�ֿ��Կ�����������activity��taskAffinity���û���ر�ָ����
   ������������activity����ͬ��
