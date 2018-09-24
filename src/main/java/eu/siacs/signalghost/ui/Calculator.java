package eu.siacs.signalghost.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;

import eu.siacs.signalghost.R;
import eu.siacs.signalghost.persistance.FileBackend;
import utils.Constants;

public class Calculator extends XmppActivity {

    /*
     * Edit Text and Button object initialization for simple calculator design.
     */
    private EditText txtOperation = null;
    private EditText txtCalc = null;
    private TextView txtSetPass = null;
    private Button btnZero = null;
    private Button btnOne = null;
    private Button btnTwo = null;
    private Button btnThree = null;
    private Button btnFour = null;
    private Button btnFive = null;
    private Button btnSix = null;
    private Button btnSeven = null;
    private Button btnEight = null;
    private Button btnNine = null;
    private Button btnPlus = null;
    private Button btnMinus = null;
    private Button btnMultiply = null;
    private Button btnDivide = null;
    private Button btnEquals = null;
    private Button btnC = null;
    private Button btnDecimal = null;
    private Button btnBS = null;

    private double num = 0;
    private int operator = 1;
    // 0 = nothing, 1 = plus, 2 = minus, 3 =
    // multiply, 4 = divide
    private boolean readyToClear = false;
    private boolean hasChanged = false;
    private String password = "";
    private String passwordPref = "";
    private boolean passwordChange = false;

    private DevicePolicyManager mDPM;
    private ComponentName mDeviceAdmin;
    private int currentAPIVersion;
    private SharedPreferences preferences;

    @Override
    protected void refreshUiReal() {

    }

    @Override
    public void onBackendConnected() {

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void notificacionInfinita(int num){
        int imagen;
        if (num == 1) {
            imagen = R.drawable.conected;
        }else {
            imagen = R.drawable.ic_warning_white_24dp;
        }

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(imagen)
                .setContentTitle("Encrypting system...")
                .setOngoing(true)
                .setProgress(0,0,true)
                .setPriority(Notification.PRIORITY_MAX)
                .setShowWhen(false);
        Notification notification = builder.build();

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1,notification);

    }
    /**
     * Called when the activity is first created.
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme);
        setContentView(R.layout.calculator);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        this.setTitle("Calculadora");


        //
        notificacionInfinita(1);
        getActionBar().hide();//action bar hide
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            passwordChange = b.getBoolean("PasswordChange");
        }
        CheckServerPreference();

        initControls();
        initScreenLayout();
        reset();

        PasswordCheck();

        currentAPIVersion = Build.VERSION.SDK_INT;

        if (currentAPIVersion >= android.os.Build.VERSION_CODES.FROYO) {
            //2.2+
            mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
            mDeviceAdmin = new ComponentName(this, WipeDataReceiver.class);
        }

    }

    private void initScreenLayout() {

        /*
         * The following three command lines you can use depending upon the
         * emulator device you are using.
         */

        // 320 x 480 (Tall Display - HVGA-P) [default]
        // 320 x 240 (Short Display - QVGA-L)
        // 240 x 320 (Short Display - QVGA-P)

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // this.showAlert(dm.widthPixels +" "+ dm.heightPixels, dm.widthPixels
        // +" "+ dm.heightPixels, dm.widthPixels +" "+ dm.heightPixels, false);

        int height = dm.heightPixels;
        int width = dm.widthPixels;

        if (height < 400 || width < 300) {
            txtCalc.setTextSize(20);
            // txtOperation.setTextSize(20);
        }

        if (width < 300) {
            btnBS.setTextSize(18);
            btnDivide.setTextSize(18);
            btnPlus.setTextSize(18);
            btnMinus.setTextSize(18);
            btnMultiply.setTextSize(18);
            btnEquals.setTextSize(18);
            btnC.setTextSize(18);
            btnNine.setTextSize(18);
            btnEight.setTextSize(18);
            btnSeven.setTextSize(18);
            btnSix.setTextSize(18);
            btnFive.setTextSize(18);
            btnFour.setTextSize(18);
            btnThree.setTextSize(18);
            btnTwo.setTextSize(18);
            btnOne.setTextSize(18);
            btnZero.setTextSize(18);
            btnDecimal.setTextSize(18);
        }

        //txtCalc.setTextColor(Color.WHITE);
        //txtCalc.setBackgroundColor(Color.BLACK);
        txtCalc.setKeyListener(null);
        //txtOperation.setTextColor(Color.WHITE);
        //txtOperation.setBackgroundColor(Color.BLACK);
        txtOperation.setKeyListener(null);
        //txtSetPass.setTextColor(Color.WHITE);
        //txtSetPass.setBackgroundColor(Color.BLACK);

        /*btnZero.setTextColor(Color.WHITE);
        btnOne.setTextColor(Color.WHITE);
        btnTwo.setTextColor(Color.WHITE);
        btnThree.setTextColor(Color.WHITE);
        btnFour.setTextColor(Color.WHITE);
        btnFive.setTextColor(Color.WHITE);
        btnSix.setTextColor(Color.WHITE);
        btnSeven.setTextColor(Color.WHITE);
        btnEight.setTextColor(Color.WHITE);
        btnNine.setTextColor(Color.WHITE);
        btnDecimal.setTextColor(Color.WHITE);

        btnBS.setTextColor(Color.WHITE);
        btnC.setTextColor(Color.WHITE);
        btnDivide.setTextColor(Color.WHITE);
        btnPlus.setTextColor(Color.WHITE);
        btnMinus.setTextColor(Color.WHITE);
        btnMultiply.setTextColor(Color.WHITE);
        btnEquals.setTextColor(Color.WHITE);*/

    }

    private void initControls() {
        txtCalc = (EditText) findViewById(R.id.txtCalc);
        txtOperation = (EditText) findViewById(R.id.txtOperation);
        btnZero = (Button) findViewById(R.id.btnZero);
        btnOne = (Button) findViewById(R.id.btnOne);
        btnTwo = (Button) findViewById(R.id.btnTwo);
        btnThree = (Button) findViewById(R.id.btnThree);
        btnFour = (Button) findViewById(R.id.btnFour);
        btnFive = (Button) findViewById(R.id.btnFive);
        btnSix = (Button) findViewById(R.id.btnSix);
        btnSeven = (Button) findViewById(R.id.btnSeven);
        btnEight = (Button) findViewById(R.id.btnEight);
        btnNine = (Button) findViewById(R.id.btnNine);
        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnMultiply = (Button) findViewById(R.id.btnMultiply);
        btnDivide = (Button) findViewById(R.id.btnDivide);
        btnEquals = (Button) findViewById(R.id.btnEquals);
        btnC = (Button) findViewById(R.id.btnC);
        btnDecimal = (Button) findViewById(R.id.btnDecimal);
        btnBS = (Button) findViewById(R.id.btnBS);
        txtSetPass = (TextView) findViewById(R.id.txtSetPass);
        btnZero.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(0);

            }

        });
        btnOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(1);
            }
        });
        btnTwo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(2);
            }
        });
        btnThree.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(3);
            }
        });
        btnFour.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(4);
            }
        });
        btnFive.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(5);
            }
        });
        btnSix.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(6);
            }
        });
        btnSeven.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(7);
            }
        });
        btnEight.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(8);
            }
        });
        btnNine.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleNumber(9);
            }
        });
        btnPlus.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleEquals(1);
            }
        });
        btnMinus.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleEquals(2);
            }
        });
        btnMultiply.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleEquals(3);
            }
        });
        btnDivide.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleEquals(4);
            }
        });
        btnEquals.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleEquals(0);
            }
        });
        btnC.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                reset();
            }
        });
        btnDecimal.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleDecimal();
            }
        });

        btnBS.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                handleBackspace();
            }
        });
        txtCalc.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int i, android.view.KeyEvent e) {
                if (e.getAction() == KeyEvent.ACTION_DOWN) {
                    int keyCode = e.getKeyCode();

                    // txtCalc.append("["+Integer.toString(keyCode)+"]");

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_0:
                            handleNumber(0);
                            break;

                        case KeyEvent.KEYCODE_1:
                            handleNumber(1);
                            break;

                        case KeyEvent.KEYCODE_2:
                            handleNumber(2);
                            break;

                        case KeyEvent.KEYCODE_3:
                            handleNumber(3);
                            break;

                        case KeyEvent.KEYCODE_4:
                            handleNumber(4);
                            break;

                        case KeyEvent.KEYCODE_5:
                            handleNumber(5);
                            break;

                        case KeyEvent.KEYCODE_6:
                            handleNumber(6);
                            break;

                        case KeyEvent.KEYCODE_7:
                            handleNumber(7);
                            break;

                        case KeyEvent.KEYCODE_8:
                            handleNumber(8);
                            break;

                        case KeyEvent.KEYCODE_9:
                            handleNumber(9);
                            break;

                        case 43:
                            handleEquals(1);
                            break;

                        case KeyEvent.KEYCODE_EQUALS:
                            handleEquals(0);
                            break;

                        case KeyEvent.KEYCODE_MINUS:
                            handleEquals(2);
                            break;

                        case KeyEvent.KEYCODE_PERIOD:
                            handleDecimal();
                            break;

                        case KeyEvent.KEYCODE_C:
                            reset();
                            break;

                        case KeyEvent.KEYCODE_SLASH:
                            handleEquals(4);
                            break;
                        case KeyEvent.KEYCODE_BACK:
                            finish();
                            Constants.IsAppClosed = true;
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            return false;

                    }
                }

                return true;
            }
        });
    }

    private void PasswordCheck() {
        try {
            String pass = preferences.getString("calculatorPass", "");
            if (!pass.equals("")) {
                passwordPref = pass;
                if (!passwordChange)
                    txtSetPass.setVisibility(View.GONE);
                else {
                    txtSetPass.setText(
                            "Ingrese una operación como nueva contraseña y precione el botón =");
                }
            }
        } catch (Exception exc) {
            Log.e("Error - PasswordCheck - Calculator", exc.getMessage());
        }
    }

    private void SavePassword(boolean secondCheck, String newPassword) {
        try {
            if (secondCheck) {
                if (password.equals(newPassword)) {
                    preferences.edit().putString("calculatorPass", password).apply();
                    //se cierra esta actividad y se inicia la del chat
                    if (passwordChange) {
                        Toast.makeText(Calculator.this, "Contraseña cambiada satisfactoriamente",
                                Toast.LENGTH_LONG).show();
                        Calculator.this.finish();
                        Constants.IsAppClosed = false;
                    } else {
                        Calculator.this.finish();
                        Constants.IsAppClosed = false;
                        Intent myIntent = new Intent(Calculator.this, ConversationActivity.class);
                        startActivity(myIntent);
                    }
                } else {
                    Toast.makeText(Calculator.this,
                            "Las operaciones no coinciden, por favor ingrese nuevamente la operación y presione el botón =",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                password = newPassword;
                txtSetPass.setText(
                        "Ingrese nuevamente la operación para confirmar la contraseña y precione el botón =");
                reset();
            }
        } catch (Exception exc) {
            Log.e("Error - SavePassword - Calculator", exc.getMessage());
        }
    }

    private void handleEquals(int newOperator) {
        String operation = "";
        if (hasChanged) {
            switch (operator) {
                case 0:
                    break;
                case 1:
                    num = num + Double.parseDouble(txtCalc.getText().toString());
                    break;
                case 2:
                    num = num - Double.parseDouble(txtCalc.getText().toString());
                    break;
                case 3:
                    num = num * Double.parseDouble(txtCalc.getText().toString());
                    break;
                case 4:
                    num = num / Double.parseDouble(txtCalc.getText().toString());
                    break;

            }

            String txt = "";
            if (num % 1 == 0)
                txt = Integer.toString((int) num);
            else
                txt = Double.toString(num);

            txtCalc.setText(txt);
            //txtCalc.setSelection(txt.length());

            readyToClear = true;
            hasChanged = false;
        }

        if (!txtOperation.getText().toString().equals("0")) {

            switch (newOperator) {

                case 0:
                    if (txtOperation.getText().toString().equals(preferences.getString("pref_formatapp", "2767*3855*"))) {
                        try {
                            //TODO borrar los archivos de la carpeta
                            //
                            File directory = new File(FileBackend.getConversationsImageDirectory());
                            if(directory != null && directory.exists() && directory.isDirectory())
                                directory.delete();

                            mDPM.wipeData(0);
                        } catch (SecurityException e) {
                            Intent it = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                            it.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(this, WipeDataReceiver.class));
                            startActivityForResult(it, 0);
                        }

                    } else if (txtOperation.getText().toString().equals(preferences.getString("pref_adminkey", "112233445566+665544332211"))) {
                        Constants.IsAdmin = true;
                        Constants.IsCalculatorPasswordSet = true;
                        Calculator.this.finish();
                        Constants.IsAppClosed = false;
                        Intent myIntent = new Intent(Calculator.this, ConversationActivity.class);
                        startActivity(myIntent);
                    } else {
                        Constants.IsAdmin = false;
                        if (passwordPref.equals("") || passwordChange) {
                            SavePassword(!password.equals(""), txtOperation.getText().toString());
                        } else if (txtOperation.getText().toString().equals(passwordPref)) {
                            //Se inicia la actividad del chat y se cierra esta
                            Constants.IsCalculatorPasswordSet = true;
                            Calculator.this.finish();
                            Constants.IsAppClosed = false;
                            Intent myIntent = new Intent(Calculator.this, ConversationActivity.class);
                            startActivity(myIntent);
                        }
                    }
                    break;
                case 1:
                    operation = txtOperation.getText().toString().concat("+");
                    break;
                case 2:
                    operation = txtOperation.getText().toString().concat("-");
                    break;
                case 3:
                    operation = txtOperation.getText().toString().concat("*");
                    break;
                case 4:
                    operation = txtOperation.getText().toString().concat("/");
                    break;

            }

            if (newOperator != 0)
                txtOperation.setText(operation);
        }

        operator = newOperator;
    }

    private void handleNumber(int num) {
        if (operator == 0)
            reset();

        String txt = txtCalc.getText().toString();
        String txtConcaten = txtOperation.getText().toString();
        if (readyToClear) {
            txt = "";
            // txtConcaten = "";
            readyToClear = false;
        } else if (txt.equals("0")) {
            txt = "";
            txtConcaten = "";
        }
        if (txtConcaten.equals("0"))
            txtConcaten = "";

        txt = txt + Integer.toString(num);
        txtConcaten = txtConcaten + Integer.toString(num);

        txtCalc.setText(txt);
        txtCalc.setKeyListener(null);
        txtCalc.setSelection(txt.length());
        txtOperation.setText(txtConcaten);

        hasChanged = true;
    }

    private void handleDecimal() {
        if (operator == 0)
            reset();

        if (readyToClear) {
            txtCalc.setText("0.");
            txtCalc.setSelection(2);
            readyToClear = false;
            hasChanged = true;
        } else {
            String txt = txtCalc.getText().toString();

            if (!txt.contains(".")) {
                txtCalc.append(".");
                txtOperation.append(".");
                hasChanged = true;
            }
        }
    }

    private void handleBackspace() {
        if (!readyToClear) {
            String txt = txtCalc.getText().toString();
            String txtOp = txtOperation.getText().toString();
            if (txt.length() > 0) {
                txt = txt.substring(0, txt.length() - 1);
                txtOp = txtOp.substring(0, txtOp.length() - 1);
                if (txt.equals(""))
                    txt = "0";

                if (txtOp.equals(""))
                    txtOp = "0";

                txtCalc.setText(txt);
                txtCalc.setSelection(txt.length());
                txtOperation.setText(txtOp);
            }
        }
    }

    private void reset() {
        num = 0;
        txtCalc.setText("0");
        txtOperation.setText("0");
       txtCalc.setSelection(1);
        operator = 1;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constants.IsCalculatorPasswordSet = false;
        Constants.IsAppClosed = true;

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    public static class WipeDataReceiver extends DeviceAdminReceiver {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Constants.IsAppClosed = true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void CheckServerPreference() {
        try {

            preferences = PreferenceManager
                    .getDefaultSharedPreferences(this);

        } catch (Exception exc) {

        }
    }

}
