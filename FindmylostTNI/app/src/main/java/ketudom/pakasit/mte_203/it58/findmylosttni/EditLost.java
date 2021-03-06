package ketudom.pakasit.mte_203.it58.findmylosttni;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class EditLost extends AppCompatActivity {

    EditText edtwhat, edtwhere, edtdate, edtname, edttel, edtid,edtdes;
    Button butEdit, butSend, butDel,buttel;
    //TextView txtstatus;
    Spinner spnStatus;


    HttpClient hc;
    HttpPost hp;
    HttpResponse hr;
    String url = "", data = "";
    BufferedReader br;

    AlertDialog myAlert;
    Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lost);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        edtwhat = (EditText) findViewById(R.id.edtWhat);
        edtwhere = (EditText) findViewById(R.id.edtWhere);
        edtdate = (EditText) findViewById(R.id.edtDate);
        edtname = (EditText) findViewById(R.id.edtName);
        edttel = (EditText) findViewById(R.id.edtTel);
        edtid = (EditText) findViewById(R.id.edtid);
        edtdes = (EditText)findViewById(R.id.edtdes);
        //edtstatus=(EditText)findViewById(R.id.edtstatus);
        //txtstatus = (TextView)findViewById(R.id.txtstatus);

        //status = (Switch)findViewById(R.id.swhstatus);
        //status.setClickable(true);
        spnStatus = (Spinner) findViewById(R.id.spnstatus);


        lockscreen();
        /*edtid.setEnabled(false);
        edtwhat.setEnabled(false);
        edtwhere.setEnabled(false);
        edtdate.setEnabled(false);
        edtname.setEnabled(false);
        edttel.setEnabled(false);
        edtid.setEnabled(false);
        //edtstatus.setEnabled(false);
        spnStatus.setEnabled(false);*/


        butEdit = (Button) findViewById(R.id.butEdit);
        butSend = (Button) findViewById(R.id.butSend);
        butSend.setVisibility(View.INVISIBLE);
        butDel = (Button) findViewById(R.id.butDel);
        butDel.setVisibility(View.INVISIBLE);
        buttel = (Button)findViewById(R.id.buttel);

        hc = new DefaultHttpClient();
        String id = getIntent().getStringExtra("id");

        try {
            url = "https://k-ketudom.000webhostapp.com/project/selectlost.php?id=" + id;
            hp = new HttpPost(url);

            hr = hc.execute(hp);
            br = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
            data = br.readLine();

            final String part[] = data.split(",");

            edtid.setText(part[0]);
            edtwhat.setText(part[1]);
            edtwhere.setText(part[2]);

            String indate = part[3].toString();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date olddate = fmt.parse(indate);

            SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yyyy");
            String newFdate = simpleDate.format(olddate);

            edtdate.setText(newFdate);

            edtdes.setText(part[4]);
            edtname.setText(part[5]);
            edttel.setText(part[6]);

            buttel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+part[6].toString()));
                    startActivity(intent);
                }
            });

            if(part[7].equals("ยังไม่ได้คืน")) {
                spnStatus.setSelection(0);


            }else if (part[7].equals("ได้คืนแล้ว")){
                spnStatus.setSelection(1);

            }

        } catch (Exception e) {
            Toast.makeText(EditLost.this, e.toString()+"1", Toast.LENGTH_SHORT).show();
        }














        butEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAlert = new AlertDialog.Builder(EditLost.this).create();
                myAlert.setTitle("แก้ไขข้อมูล");
                myAlert.setMessage("กรุณากรอกรหัสลับของคุณ");
                myAlert.setCancelable(false);
                final EditText input = new EditText(EditLost.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setRawInputType(Configuration.KEYBOARD_NOKEYS);
                myAlert.setView(input);
                myAlert.setButton(AlertDialog.BUTTON_POSITIVE, "ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hc = new DefaultHttpClient();
                        String id = getIntent().getStringExtra("id");
                        try {
                            url = "https://k-ketudom.000webhostapp.com/project/selectlost.php?id=" + id;
                            hp = new HttpPost(url);

                            hr = hc.execute(hp);
                            br = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
                            data = br.readLine();
                        } catch (Exception e) {
                            Toast.makeText(EditLost.this, e.toString()+"2", Toast.LENGTH_SHORT).show();
                        }


                        final String part[] = data.split(",");

                        if (input.getText().toString().equals(part[8])) {
                            edtwhat.setEnabled(true);
                            edtwhere.setEnabled(true);
                            edtdate.setEnabled(true);

                            edtname.setEnabled(true);
                            edttel.setEnabled(true);
                            //edtid.setEnabled(true);
                            //edtstatus.setEnabled(true);
                            spnStatus.setEnabled(true);
                            edtdes.setEnabled(true);


                            final DatePickerDialog.OnDateSetListener dd = new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                      int dayOfMonth) {
                                    // TODO Auto-generated method stub
                                    myCalendar.set(Calendar.YEAR, year);
                                    myCalendar.set(Calendar.MONTH, monthOfYear);
                                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                    updateLabel();
                                }

                            };

                            edtdate.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    new DatePickerDialog(EditLost.this, dd, myCalendar
                                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                                }
                            });



                            butSend.setVisibility(View.VISIBLE);
                            butDel.setVisibility(View.VISIBLE);
                            butEdit.setVisibility(View.INVISIBLE);


                        } else {
                            Toast.makeText(EditLost.this, "กรอกรหัสไม่ถูกต้อง กรุณากรอกใหม่", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                myAlert.setButton(AlertDialog.BUTTON_NEGATIVE, "ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                myAlert.show();

            }
        });


        butSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progress = new ProgressDialog(EditLost.this);
                progress.setTitle("In progress");
                progress.setMessage("Loading...");
                progress.setCancelable(false);
                progress.show();


                hc = new DefaultHttpClient();
                try {
                    String id = getIntent().getStringExtra("id");
                    String getwhat = edtwhat.getText().toString();
                    String getwhere = edtwhere.getText().toString();
                    final String getdate = edtdate.getText().toString();
                    String getname = edtname.getText().toString();
                    String gettel = edttel.getText().toString();
                    String getdes = edtdes.getText().toString();
                    //String getstatus =edtstatus.getText().toString();
                    //String getstatus ="ยังไม่ได้คืน";
                    String[] cut = getdate.split("-");
                    String Ndate = cut[2] + "-" + cut[1] + "-" + cut[0];


                    String getstatus ="";
                    if(spnStatus.getSelectedItem().toString().equals("ยังไม่ได้คืน")){
                        getstatus = "ยังไม่ได้คืน";
                    }else if (spnStatus.getSelectedItem().toString().equals("ได้คืนแล้ว")){
                        getstatus = "ได้คืนแล้ว";

                    }



                    url = "https://k-ketudom.000webhostapp.com/project/updatelost.php?id=" + id + "&whatlost=" + getwhat + "&wherelost=" + getwhere +
                            "&date=" + Ndate +"&description=" + getdes+ "&name=" + getname + "&tel=" + gettel + "&status=" + getstatus;
                    hp = new HttpPost(url);


                    hr = hc.execute(hp);
                    br = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
                    data = br.readLine();


                    if(data.equals("0")){
                        Toast.makeText(EditLost.this, "ไม่สามารถเพิ่มข้อมูลได้", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }else {
                        Toast.makeText(EditLost.this, "เพิ่มข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
                        lockscreen();
                        progress.dismiss();
                    }



                } catch (Exception e) {
                    Toast.makeText(EditLost.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        butDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progress = new ProgressDialog(EditLost.this);
                progress.setTitle("In progress");
                progress.setMessage("Loading...");
                progress.setCancelable(false);
                progress.show();


                myAlert = new AlertDialog.Builder(EditLost.this).create();
                myAlert.setTitle("ลบข้อมูล");
                myAlert.setMessage("ต้องการลบข้อมูลหรือไม่?");
                myAlert.setCancelable(false);
                myAlert.setButton(AlertDialog.BUTTON_POSITIVE, "ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hc = new DefaultHttpClient();
                        try {


                            String id = getIntent().getStringExtra("id");

                            url = "https://k-ketudom.000webhostapp.com/project/deletelost.php?id=" + id;
                            hp = new HttpPost(url);


                            hr = hc.execute(hp);
                            br = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
                            data = br.readLine();


                            Intent gAll = new Intent(EditLost.this, Allpost.class);
                            startActivity(gAll);

                            Toast.makeText(EditLost.this, "ลบข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
                            progress.dismiss();


                        } catch (Exception e) {
                            Toast.makeText(EditLost.this, e.toString()+"3", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                myAlert.setButton(AlertDialog.BUTTON_NEGATIVE, "ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                myAlert.show();


            }
        });

    }

    private void lockscreen() {

        edtwhat.setEnabled(false);
        edtwhere.setEnabled(false);
        edtdate.setEnabled(false);
        edtname.setEnabled(false);
        edttel.setEnabled(false);
        edtid.setEnabled(false);
        //edtstatus.setEnabled(false);
        spnStatus.setEnabled(false);
        edtdes.setEnabled(false);

    }private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtdate.setText(sdf.format(myCalendar.getTime()));
    }


}







