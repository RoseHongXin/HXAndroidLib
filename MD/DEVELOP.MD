```
ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        for(ActivityManager.RunningServiceInfo rsi : am.getRunningServices(1000)){
//            Log4Android.e(this, "------------------" + rsi.pid + " " + rsi.process + " " + rsi.service + " " + rsi.clientPackage + " " + rsi.clientLabel);
//        }
        for(ActivityManager.RunningAppProcessInfo rsi : am.getRunningAppProcesses()){
            Log4Android.e(this, "------------------" + rsi.pid + "\t" + rsi.processName + "\t" + rsi.importance + "\t" + rsi.importanceReasonPid + "\t" + rsi.importanceReasonComponent);
        }
```