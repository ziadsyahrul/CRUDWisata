package com.ziadsyahrul.crudwisata.UI.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ziadsyahrul.crudwisata.R;
import com.ziadsyahrul.crudwisata.UI.main.MainActivity;
import com.ziadsyahrul.crudwisata.UI.register.RegisterActivity;
import com.ziadsyahrul.crudwisata.Utils.Constant;
import com.ziadsyahrul.crudwisata.model.Login.LoginData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.txtRegister)
    TextView txtRegister;

    private ProgressDialog progressDialog;
    private LoginPresenter loginPresenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter.checkLogin(this);
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void loginSuccess(String msg, LoginData loginData) {
        Toasty.success(this, "Login Success", Toast.LENGTH_SHORT).show();

        loginPresenter.saveDataUser(this, loginData);

        LoginData mloginData = new LoginData();
        mloginData.setId_user(loginData.getId_user());
        mloginData.setAlamat(loginData.getAlamat());
        mloginData.setJenkel(loginData.getJenkel());
        mloginData.setNama_user(loginData.getNama_user());
        mloginData.setNo_telp(loginData.getNo_telp());
        mloginData.setUsername(loginData.getUsername());
        mloginData.setPassword(loginData.getPassword());
        mloginData.setLevel(loginData.getLevel());

        startActivity(new Intent(this, MainActivity.class).putExtra(Constant.KEY_LOGIN, mloginData));

        finish();
    }

    @Override
    public void loginFailure(String msg) {
        Toasty.error(this, "Login error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void usernameError(String msg) {
        edtUsername.setError(msg);
        edtUsername.setFocusable(true);
    }

    @Override
    public void passwordError(String msg) {
        edtPassword.setError(msg);
        edtPassword.setFocusable(true);
    }

    @Override
    public void isLogin() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @OnClick({R.id.btnLogin, R.id.txtRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                loginPresenter.doLogin(edtUsername.getText().toString(), edtPassword.getText().toString());
                break;
            case R.id.txtRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}

