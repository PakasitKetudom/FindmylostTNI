package ketudom.pakasit.mte_203.it58.findmylosttni;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    HttpClient hc;
    HttpPost hp;
    HttpResponse hr;
    String url = "", data = "";
    BufferedReader br;

    TextView txtClost,txtCfound;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        txtClost = (TextView)findViewById(R.id.txtcountlost);
        txtCfound = (TextView)findViewById(R.id.txtcountfound);




        onResume();






    }

    public void Countlost() {

        //display2.setVisibility(View.INVISIBLE);

        hc = new DefaultHttpClient();

        url = "https://k-ketudom.000webhostapp.com/project/alllost.php";
        hp = new HttpPost(url);

        try {
            hr = hc.execute(hp);
            br = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
            data = br.readLine();

            String part[] = data.split(";");
            int count = 0;

            for (int i = 0; i < part.length; i++) {
                count++;
            }
            br.close();

            txtClost.setText("มีการแจ้งหายทั้งหมด "+count+" รายการ");
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void Countfound() {

        //display2.setVisibility(View.INVISIBLE);

        hc = new DefaultHttpClient();

        url = "https://k-ketudom.000webhostapp.com/project/allfound.php";
        hp = new HttpPost(url);

        try {
            hr = hc.execute(hp);
            br = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
            data = br.readLine();

            String part[] = data.split(";");
            int count = 0;

            for (int i = 0; i < part.length; i++) {
                count++;
            }
            br.close();

            txtCfound.setText("มีการแจ้งพบทั้งหมด "+count+" รายการ");
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    protected void onResume() {
        super.onResume();

        Countlost();
        Countfound();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

   /* @Override
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
*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
try {

    if (id == R.id.nav_Allpost) {
        Intent ap = new Intent(MainActivity.this, Allpost.class);
        startActivity(ap);
    } else if (id == R.id.nav_lost) {
        Intent lo = new Intent(MainActivity.this, Lostform.class);
        startActivity(lo);
    } else if (id == R.id.nav_found) {
        Intent fo = new Intent(MainActivity.this, Foundform.class);
        startActivity(fo);
    } else if (id==R.id.nav_contact){
        Intent con = new Intent(MainActivity.this, Contact.class);
        startActivity(con);
    }else  if (id==R.id.nav_home){
        Intent home = new Intent(MainActivity.this, MainActivity.class);
        startActivity(home);
    }
}catch (Exception e) {
    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
