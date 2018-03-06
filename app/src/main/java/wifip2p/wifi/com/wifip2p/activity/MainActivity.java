package wifip2p.wifi.com.wifip2p.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import wifip2p.wifi.com.wifip2p.R;

/**
 * Wifi P2P 技术并不会访问网络，但会使用到 Java socket 技术
 *
 * 总结：
 * 1、声明权限
 * 1、清单文件注册权限
 * 2、注册Wifi P2P相关广播
 * 3、创建客户端socket，把选择的文件解析成IO流，发送信息
 * 4、创建服务端server，在server内创建服务端socket，监听客户端socket端口，获取信息
 * 5、服务端创建连接的组群信息提供给客户端连接
 * 7、客户端连接信息组群和服务端建立WiFip2p连接
 * 8、客户端通过socket发送文件到服务端serversocket服务端监听到端口后就会获取信息，写入文件。
 */
public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //申请文件读写权限
        requireSomePermission();
    }

    public void sendFile(View v){
        startActivity(new Intent(this,SendFileActivity.class));
    }

    public void receiveFile(View v){
        startActivity(new Intent(this,ReceiveFileActivity.class));
    }

    @AfterPermissionGranted(1000)
    private void requireSomePermission() {
        String[] perms = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };
        if (EasyPermissions.hasPermissions(this, perms)) {
           //有权限
        } else {
            //没权限
            EasyPermissions.requestPermissions(this, "需要文件读取权限",
                    1000, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 权限申成功
     * @param i
     * @param list
     */
    @Override
    public void onPermissionsGranted(int i, @NonNull List<String> list) {
        Log.e(TAG,"权限申成功");
    }

    /**
     * 权限申请失败
     * @param i
     * @param list
     */
    @Override
    public void onPermissionsDenied(int i, @NonNull List<String> list) {
        Log.e(TAG,"权限申请失败");
    }
}
