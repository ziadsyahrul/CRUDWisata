package com.ziadsyahrul.crudwisata.UI.profile;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.ziadsyahrul.crudwisata.R;
import com.ziadsyahrul.crudwisata.model.Login.LoginData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileContract.View {

    @BindView(R.id.picture)
    CircleImageView picture;
    @BindView(R.id.fabChoosePic)
    FloatingActionButton fabChoosePic;
    @BindView(R.id.layoutPicture)
    RelativeLayout layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_alamat)
    EditText edtAlamat;
    @BindView(R.id.edt_no_telp)
    EditText edtNoTelp;
    @BindView(R.id.spin_gender)
    Spinner spinGender;
    @BindView(R.id.layoutProfil)
    CardView layoutProfil;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.layoutJenkel)
    CardView layoutJenkel;
    Unbinder unbinder;

    // siapkan variable yang dibutuhkan
    private ProfilePresenter mProfilePresenter = new ProfilePresenter(this);
    private String idUser, name, alamat, noTelp;
    private int gender;
    private Menu action;

    private String mGender;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;
    private ProgressDialog progressDialog;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        setUpSpinner();

        mProfilePresenter.getDataUser(getContext());
        return view;
    }

    private void setUpSpinner() {
        // buat adapter spinner
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.array_gender_options, android.R.layout.simple_spinner_item);
        // Menampilkan spinner 1 line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Memasukkan adapter spinner ke dalam widget spinner kita
        spinGender.setAdapter(genderSpinnerAdapter);

        // Membuat Listener Spinner
        spinGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Mengambil posisi item yang dipilih
                String selection = (String) parent.getItemAtPosition(position);
                // cek posisi apakah ada isinya
                if (!TextUtils.isEmpty(selection)) {
                    // Mencek apakah yang dipilih user itu 1 atau 2
                    if (selection.equals(getString(R.string.gender_male))){
                        mGender = "L";
                    }else if (selection.equals(getString(R.string.gender_female))){
                        mGender = "P";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Just a sec ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();

    }

    @Override
    public void showSuccessUpdateUser(String msg) {
        Toasty.success(getContext(), msg, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void showDataUser(LoginData loginData) {
        readMode();

        // memasukkan data yg sudah diambil presenter
        idUser = loginData.getId_user();
        name = loginData.getNama_user();
        alamat = loginData.getAlamat();
        noTelp = loginData.getNo_telp();
        if (loginData.getJenkel().equals("L")) {
            gender = 1;
        } else {
            gender = 2;
        }

        if (!TextUtils.isEmpty(idUser)) {
            // set nama title actionbar
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profil " + name);

            edtName.setText(name);
            edtAlamat.setText(alamat);
            edtNoTelp.setText(noTelp);

            // cek gender dan memilih sesuai gender untuk ditampilkan di spinner
            switch (gender) {
                case GENDER_MALE:
                    spinGender.setSelection(1);
                    spinGender.setSelection(0);
                    break;
                case GENDER_FEMALE:
                    spinGender.setSelection(2);
                    spinGender.setSelection(1);
                    break;
            }
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        // perintah logout ke presenter
        mProfilePresenter.logoutSession(getContext());
        // menutup mainactivity
        getActivity().finish();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Menampilkan menu ke layar
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                editMode();

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);
                return true;

            case R.id.menu_save:
                // cek user apakah ada isinya
                if (!TextUtils.isEmpty(idUser)) {
                    // cek field nya apakah semuanya kosong
                    if (TextUtils.isEmpty(edtName.getText().toString()) ||
                        TextUtils.isEmpty(edtAlamat.getText().toString()) ||
                        TextUtils.isEmpty(edtNoTelp.getText().toString())) {

                        // Menampilkan alert dialog untuk memberi tahu kepada user kalo kolomnya tidak boleh kosong
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                        alertDialog.setMessage("Please complete the field");
                        alertDialog.setPositiveButton("Wokehh", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                editMode();
                                action.findItem(R.id.menu_edit).setVisible(false);
                                action.findItem(R.id.menu_save).setVisible(true);
                            }
                        });
                        alertDialog.show();
                    }else {
                        // apabila user telah melengkapi kolom
                        LoginData loginData = new LoginData();
                        // mengisi inputan user ke model logindata
                        loginData.setId_user(idUser);
                        loginData.setNama_user(edtName.getText().toString());
                        loginData.setAlamat(edtAlamat.getText().toString());
                        loginData.setNo_telp(edtNoTelp.getText().toString());
                        loginData.setJenkel(mGender);

                        // mengirim data ke presenter untuk dimasukkan ke database
                        mProfilePresenter.updateDataUser(getContext(), loginData);

                        readMode();
                        action.findItem(R.id.menu_edit).setVisible(false);
                        action.findItem(R.id.menu_save).setVisible(true);
                    }
                }else {
                    readMode();
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                }
                readMode();
                action.findItem(R.id.menu_edit).setVisible(true);
                action.findItem(R.id.menu_save).setVisible(false);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("RestrictedApi")
    private void editMode() {
        edtName.setFocusableInTouchMode(true);
        edtAlamat.setFocusableInTouchMode(true);
        edtNoTelp.setFocusableInTouchMode(true);

        spinGender.setEnabled(true);
        fabChoosePic.setVisibility(View.VISIBLE);
    }

    @SuppressLint("RestrictedApi")
    private void readMode() {
        edtName.setFocusableInTouchMode(false);
        edtAlamat.setFocusableInTouchMode(false);
        edtNoTelp.setFocusableInTouchMode(false);

        edtName.setFocusable(false);
        edtAlamat.setFocusable(false);
        edtNoTelp.setFocusable(false);

        spinGender.setEnabled(false);
        fabChoosePic.setVisibility(View.INVISIBLE);

    }
}
