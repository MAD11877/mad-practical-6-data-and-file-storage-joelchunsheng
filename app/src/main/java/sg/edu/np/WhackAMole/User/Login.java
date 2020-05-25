package sg.edu.np.WhackAMole.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sg.edu.np.WhackAMole.Level.Levels;
import sg.edu.np.WhackAMole.R;
import sg.edu.np.WhackAMole.UserDataHandler;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button login;
    TextView signUp;
    String usernameText , passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username =  (EditText) findViewById(R.id.nameEditTxt);
        password =  (EditText) findViewById(R.id.passwordEditTxt);
        login = (Button) findViewById(R.id.loginBtn);
        signUp = (TextView) findViewById(R.id.signUpText);

        // when login btn clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usernameText = username.getText().toString();
                passwordText = password.getText().toString();


                // validate credentials
                if(validateLogin(v)){
                    //if true, login
                    Intent levelsActivity = new Intent(Login.this, Levels.class);
                    startActivity(levelsActivity);

                    SharedPreferences sharedPreferences = getSharedPreferences("sg.edu.np.WhackAMole.User", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("username", usernameText).apply();
                }
                else{
                    Toast.makeText(v.getContext(), "Invalid Username or Password.", Toast.LENGTH_LONG).show();

                }


            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(Login.this, Register.class);
                startActivity(registerActivity);
            }
        });

    }

    //method to validate credentials
    public Boolean validateLogin(View view){
        UserDataHandler userDataHandler = new UserDataHandler(this, null, null, 1);
        UserModel userModel = userDataHandler.findUser(usernameText);

        if(userModel!=null){
            //compare password
            if (userModel.getPassword().equals(passwordText)){
                Log.i("Login", "Password Match");
                return true;
            }
            else{
                Log.i("Login", "Incorrect Password");
                return false;
            }

        }
        else{
            System.out.println("Account does not exist");
            return false;
        }
    }
}
