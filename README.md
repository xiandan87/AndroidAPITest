# AndroidAPITest

#1.测试MainActivity启动模式为singleTask的话，从其他activity重新跳转回来并清理它之上的activity的话，是否还需要配合FLAG_ACTIVITY_CLEAR_TOP使用？
   测试流程为 MainActivity->TestActivityA->TestActivityB->MainActivity
   通过 adb shell dumpsys activity | grep androidapi.pop.test 命令查看栈信息

  最后两步的结果分别为：
   TaskRecord{21ad56a7 #253 A=androidapi.pop.test, isShadow:false U=0 sz=3}
   Run #7: ActivityRecord{36d8a82f u0 androidapi.pop.test/.TestActivityB, isShadow:false t253}
   Run #6: ActivityRecord{52957c u0 androidapi.pop.test/.TestActivityA, isShadow:false t253}
   Run #5: ActivityRecord{26909026 u0 androidapi.pop.test/.MainActivity, isShadow:false t253}

   TaskRecord{21ad56a7 #253 A=androidapi.pop.test, isShadow:false U=0 sz=1}
   Run #5: ActivityRecord{26909026 u0 androidapi.pop.test/.MainActivity, isShadow:false t253}

   可以看出没有FLAG_ACTIVITY_CLEAR_TOP的情况下，还是清理了MainActivity之上的activity的，所以说singleTask启动模式是自带FLAG_ACTIVITY_CLEAR_TOP的

# 2.测试FLAG_ACTIVITY_CLEAR_TOP对于standard启动模式activity会有怎么样的执行结果
   测试流程为MainActivity->TestActivityA->TestActivityA

   日志结果为：
   04-25 13:42:42.617 21752-21752/androidapi.pop.test I/BaseActivity: MainActivity:@81de62c create
   04-25 13:42:48.626 21752-21752/androidapi.pop.test I/BaseActivity: TestActivityA:@64aab3e create
   04-25 13:42:51.162 21752-21752/androidapi.pop.test I/BaseActivity: TestActivityA:@206e105a create
   04-25 13:42:51.601 21752-21752/androidapi.pop.test I/BaseActivity: TestActivityA:@64aab3e destroy

   最后栈信息为：
    TaskRecord{27bb320c #257 A=androidapi.pop.test, isShadow:false U=0 sz=2}
    Run #6: ActivityRecord{17e43769 u0 androidapi.pop.test/.TestActivityA, isShadow:false t257}
    Run #5: ActivityRecord{28040634 u0 androidapi.pop.test/.MainActivity, isShadow:false t257}

    可以看出当使用FLAG_ACTIVITY_CLEAR_TOP跳转到standard启动模式activity，而该activity的实例又处于栈顶时，则会销毁
    当前栈顶的实例，创建一个新的实例放入栈顶

# 3.测试使用FLAG_ACTIVITY_NEW_TASK时，如果android:taskAffinity未特别指定，是否会在新的task里面启动
   测试流程MainActivity->TestActivityA

   未特别指定taskAffinity时，栈信息为：
     Running activities (most recent first):
     TaskRecord{3fa1d0b6 #261 A=androidapi.pop.test, isShadow:false U=0 sz=2}
         Run #6: ActivityRecord{318046a7 u0 androidapi.pop.test/.TestActivityA, isShadow:false t261}
         Run #5: ActivityRecord{2cc0c22 u0 androidapi.pop.test/.MainActivity, isShadow:false t261}

   指定taskAffinity后，栈信息为：
     Running activities (most recent first):
     TaskRecord{c9c14fd #263 A=pop.new.task, isShadow:false U=0 sz=1}
         Run #6: ActivityRecord{130ef006 u0 androidapi.pop.test/.TestActivityA, isShadow:false t263}
     TaskRecord{3cc082c4 #262 A=androidapi.pop.test, isShadow:false U=0 sz=1}
         Run #5: ActivityRecord{584e528 u0 androidapi.pop.test/.MainActivity, isShadow:false t262}

   可以看出默认不指定taskAffinity时，taskAffinity的值即为包名，即使用了FLAG_ACTIVITY_NEW_TASK，也不会再新task里面启动

# 4.测试隐式启动activity，而该activity在manifest未配置任何category情况下，能否启动
   测试流程为MainActivity->TestActivityA->TestActivityB

   未配置的时候，抛出异常：
   E/AndroidRuntime: FATAL EXCEPTION: main Process: androidapi.pop.test, PID: 20323
          android.content.ActivityNotFoundException: No Activity found to handle Intent { act=pop.test.activityb.action }

   配置<category android:name="android.intent.category.DEFAULT"/>后：
       能正常启动TestActivityB，且栈信息为
        TaskRecord{388f10de #267 A=pop.new.task, isShadow:false U=0 sz=2}
            Run #7: ActivityRecord{3889c446 u0 androidapi.pop.test/.TestActivityB, isShadow:false t267}
            Run #6: ActivityRecord{210f6fc3 u0 androidapi.pop.test/.TestActivityA, isShadow:false t267}
        TaskRecord{15c202bf #266 A=androidapi.pop.test, isShadow:false U=0 sz=1}
            Run #5: ActivityRecord{22c296c0 u0 androidapi.pop.test/.MainActivity, isShadow:false t266}

   可以看出隐式启动activity，该activity必须要配置category才能匹配到。从栈信息又可以看到被启动的activity的taskAffinity如果没有特别指明，
   则与启动它的activity是相同的
