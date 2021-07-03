package sg.edu.np.WhackAMole.User;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sg.edu.np.WhackAMole.Level.LevelModel;
import sg.edu.np.WhackAMole.LevelDataHandler;
import sg.edu.np.WhackAMole.R;
import sg.edu.np.WhackAMole.UserDataHandler;

public class Register extends AppCompatActivity {

    EditText username, password;
    Button cancel, create;

    String usernameTxt, passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.cNameEditTxt);
        password = (EditText) findViewById(R.id.cPasswordEditTxt);
        cancel = (Button) findViewById(R.id.cancelBtn);
        create = (Button) findViewById(R.id.createBtn);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(Register.this, Login.class);
                startActivity(loginActivity);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameTxt = username.getText().toString();
                passwordTxt = password.getText().toString();

                if (usernameTxt.isEmpty() && passwordTxt.isEmpty()){
                    Toast.makeText(v.getContext(), "Username and Password empty", Toast.LENGTH_LONG).show();
                }
                else if (usernameTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(v.getContext(), "Username or Password empty", Toast.LENGTH_LONG).show();
                }
                else{
                    // verify if account exist
                    if (findUser(v)){
                        Toast.makeText(v.getContext(), "User Already Exist. Please Try Again.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        newUser(v);
                        setUplevel(v);
                        Toast.makeText(v.getContext(), "User Created Successfully.", Toast.LENGTH_LONG).show();
                        Intent loginActivity = new Intent(Register.this, Login.class);
                        startActivity(loginActivity);
                    }
                }

            }
        });

    }

    public void newUser(View view){
        UserDataHandler userDataHandler = new UserDataHandler(this, null, null, 1);
        UserModel userModel = new UserModel(usernameTxt, passwordTxt);
        userDataHandler.addUser(userModel);
    }

    
    public Boolean findUser(View view){
        UserDataHandler userDataHandler = new UserDataHandler(this, null, null, 1);
        UserModel userModel = userDataHandler.findUser(usernameTxt);

        if(userModel!=null){
            return  true;
        }
        else{
            return false;
        }
    }

    public void setUplevel(View view){
        LevelDataHandler levelDataHandler = new LevelDataHandler(this,null,null,1);
        for(int level = 1; level <= 10 ; level++){
            LevelModel levelModel = new LevelModel(usernameTxt, String.valueOf(level) , "0");
            levelDataHandler.addLevel(levelModel);
            Log.i("Level Set Up","Level " + String.valueOf(level) + " Created");
        }
    }
}
