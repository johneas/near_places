package com.gap.test.view.common.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S on 22/04/2017.
 */
public class PermissionsManager {

    public static final int PERMISSION_ALL = 1;

    /**
     * Check if is android version needs to validate permissions
     * @return is true or false
     */
    private static boolean doesAppNeedPermissions(){
        return android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    /**
     *  Get all permissions declared on manifest in order the validate it for android version that need it
     * @param context Context
     * @return List of permissions on manifest
     * @throws PackageManager.NameNotFoundException Application Package
     */
    private static String[] getPermissions(Context context)
            throws PackageManager.NameNotFoundException {
        PackageInfo info = context.getPackageManager().getPackageInfo(
                context.getPackageName(), PackageManager.GET_PERMISSIONS);

        return info.requestedPermissions;
    }

    /**
     * Ask for permissions
     *
     * @param activity Activity
     */
    public static void askPermissions(Activity activity){
        if(doesAppNeedPermissions()) {
            try {
                String[] permissions = getPermissions(activity);
                if(!checkPermissions(activity, permissions)){
                    ActivityCompat.requestPermissions(activity, permissions, PERMISSION_ALL);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Check if the permission currentli is granted
     *
     * @param context Context
     * @param permissions permissions
     * @return is granted or deny
     */
    private static boolean checkPermissions(Context context, String... permissions){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null &&
                permissions != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
