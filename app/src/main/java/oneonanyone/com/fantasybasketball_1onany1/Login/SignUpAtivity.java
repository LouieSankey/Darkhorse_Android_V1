package oneonanyone.com.fantasybasketball_1onany1.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import oneonanyone.com.fantasybasketball_1onany1.MainActivity;
import oneonanyone.com.fantasybasketball_1onany1.R;

public class SignUpAtivity extends Activity {

    protected EditText mUsername;
    protected EditText mPassword;
    protected EditText mEmail;
    protected Button mSignUpButton;
    protected EditText mLeagueName;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUsername = (EditText) findViewById(R.id.usernameField);
        mPassword = (EditText) findViewById(R.id.passwordField);
        mEmail = (EditText) findViewById(R.id.emailField);
        mSignUpButton = (Button) findViewById(R.id.signUpButton);
        mLeagueName = (EditText) findViewById(R.id.leagueName);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                String email = mEmail.getText().toString();
                String leagueName = mLeagueName.getText().toString();

                username = username.trim();
                password = password.trim();
                email = email.trim();
                leagueName.trim();

                if(username.isEmpty() || username.length() > 13
                        || password.isEmpty() || leagueName.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpAtivity.this);
                    builder.setMessage("Please make sure you enter a username, password, league name. " +
                            "Your username must be 13 letters or fewer")
                   .setTitle("Opps!")
                    .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(username);
                    newUser.setPassword(password);

                    newUser.put("lastPlayer", "--");
                    newUser.put("totalWins", 0);
                    newUser.put("totalLosses", 0);
                    newUser.put("totalScore", 0);
                    newUser.put("lastScore", 0);
                    //newUser.setEmail(email);
                    newUser.put("leagueName", leagueName);
                    newUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                //success
                                Intent intent = new Intent(SignUpAtivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpAtivity.this);
                                builder.setMessage(e.getMessage())
                                        .setTitle("Opps!")
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up_ativity, menu);
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
}
