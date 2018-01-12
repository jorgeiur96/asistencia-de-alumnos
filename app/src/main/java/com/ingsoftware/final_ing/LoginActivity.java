package com.ingsoftware.final_ing;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        Bundle intentBundle = intent.getExtras();
        String loggedUser = intentBundle.getString("USERNAME");
        loggedUser = capitalizeFirstCharacter(loggedUser);
        String message = intentBundle.getString("MESSAGE");

        TextView loginUsername = (TextView)findViewById(R.id.login_user);
        TextView successMessage = (TextView)findViewById(R.id.message);
        loginUsername.setText(loggedUser);
        successMessage.setText(message);
    }

    public void Notificacion(View view){

        android.support.v4.app.NotificationCompat.Builder notificacion =new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setLargeIcon((((BitmapDrawable) getResources()
                .getDrawable(R.drawable.ic_launcher)).getBitmap()))
                .setContentTitle("Alerta Notificacion")
                .setContentText("pulsa para ver la alerta")
                .setTicker("Dmaapp")
                .setContentInfo("2");
        Intent inotificacion =new Intent(this,MainActivity.class);
        PendingIntent intentpediente =PendingIntent.getActivity(this,0,inotificacion,0);
        notificacion.setContentIntent(intentpediente);
        NotificationManager nm =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(10,notificacion.build());


    }
    public void BorrarTodaNotificacion(View v){
        NotificationManager nm =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private String capitalizeFirstCharacter(String textInput){
        String input = textInput.toLowerCase();
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        return output;
    }
}
