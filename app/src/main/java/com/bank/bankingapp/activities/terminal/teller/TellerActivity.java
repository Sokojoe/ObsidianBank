package com.bank.bankingapp.activities.terminal.teller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bank.bankingapp.R;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerBalanceFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerCreateAccountFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerDepositFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerGiveInterestFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerMessagesFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerUpdateInfoFragment;
import com.bank.bankingapp.activities.terminal.teller.fragments.TellerWithdrawFragment;
import com.bank.bankingapp.terminals.TellerTerminal;
import com.bank.bankingapp.user.User;

import java.math.BigDecimal;

public class TellerActivity extends AppCompatActivity {


    TellerTerminal tt;

    public TellerTerminal getTt() {
        return tt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teller);
        if (findViewById(R.id.teller_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            TellerCreateAccountFragment createAccountFragment = new TellerCreateAccountFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.teller_fragment_container, createAccountFragment).commit();
        }
        tt = new TellerTerminal(this);
        User currentCustomer;
        currentCustomer = (User) getIntent().getSerializableExtra("user");
        currentCustomer.setId(getIntent().getIntExtra("userId", 0));
        tt.setCurrentUser(currentCustomer);
    }

    public void displayCreateAccount(View view) {
        TellerCreateAccountFragment createAccountFragment = new TellerCreateAccountFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.teller_fragment_container, createAccountFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void createAccount(View view) {
        EditText nameField = (EditText) findViewById(R.id.teller_create_account_name);
        String name = nameField.getText().toString();

        EditText balanceField = (EditText) findViewById(R.id.teller_create_account_balance);
        BigDecimal balance = new BigDecimal(balanceField.getText().toString());

        EditText typeField = (EditText) findViewById(R.id.teller_Create_account_type);
        long type = Long.parseLong(typeField.getText().toString());

        int accountId = tt.makeNewAccount(name, balance, type);

        Toast t = Toast.makeText(this, "AccountId = " + accountId, Toast.LENGTH_LONG);
        t.show();
    }

    public void displayGiveInterest(View view) {
        TellerGiveInterestFragment giveInterestFragment = new TellerGiveInterestFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.teller_fragment_container, giveInterestFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void tellerGiveInterestAll(View view) {
        tt.giveInterestAll();
        final AlertDialog.Builder idNotification = new AlertDialog.Builder(this);
        idNotification.setTitle(R.string.dialog_create_title);
        idNotification.setMessage("Interest has been applied to all accounts.");
        idNotification.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        idNotification.create();
        idNotification.show();
        tt.giveInterestAll();

    }

    public void displayTellerDeposit(View view) {
        TellerDepositFragment depositFragment = new TellerDepositFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.teller_fragment_container, depositFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void displayTellerWithdraw(View view) {
        TellerWithdrawFragment withdrawFragment = new TellerWithdrawFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.teller_fragment_container, withdrawFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void displayBalance(View view) {
        TellerBalanceFragment balanceFragment = new TellerBalanceFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.teller_fragment_container, balanceFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void displayUpdateInfo(View view) {
        TellerUpdateInfoFragment updateInfoFragment = new TellerUpdateInfoFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.teller_fragment_container, updateInfoFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void updateInfo(View view) {
        TextView nameField = (TextView) findViewById(R.id.teller_update_name);
        String name = nameField.getText().toString();

        if (!name.equals("")) {
            tt.updateName(name);
        }

        TextView addressField = (TextView) findViewById(R.id.teller_update_address);
        String address = addressField.getText().toString();

        if (!address.equals("")) {
            tt.updateAddress(address);
        }

        TextView passwordField = (TextView) findViewById(R.id.teller_update_password);
        String password = passwordField.getText().toString();

        if (!password.equals("")) {
            tt.updatePassword(password);
        }

        TextView ageField = (TextView) findViewById(R.id.teller_update_age);
        String age = ageField.getText().toString();

        if (!age.equals("")) {
            tt.updateAge(Integer.parseInt(age));
        }

        final AlertDialog.Builder idNotification = new AlertDialog.Builder(this);
        idNotification.setTitle(R.string.dialog_create_title);
        idNotification.setMessage("Account information has been updated.");
        idNotification.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        idNotification.create();
        idNotification.show();

        ageField.setText("");
        passwordField.setText("");
        addressField.setText("");
        ageField.setText("");

    }

    public void displayMessages(View view) {
        TellerMessagesFragment messagesFragment = new TellerMessagesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.teller_fragment_container, messagesFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void closeSession(View view) {
        Intent intent = new Intent(this, TellerStartingMenuActivity.class);
        startActivity(intent);
    }
}