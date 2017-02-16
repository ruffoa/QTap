package com.example.alex.qtapandroid.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.users.User;
import com.example.alex.qtapandroid.common.database.users.UserManager;

import com.example.alex.qtapandroid.classes.downloadICS;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
//    private AutoCompleteTextView mEmailView;
//    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    public static final String TAG = downloadICS.class.getSimpleName();
    public static String icsURL = "";
    public static String useremail = "";

//    @JavascriptInterface
//    public void processHTML(String html) {          // This is for the old method, I'm keeping it for archival purposes, but this is depreciated.  It failed to work properly on some devices.
//        if (html == null)
//            return;
//
//        if (html.contains("Class Schedule")) {
//            html = html.replaceAll("\n", "");
//            int index = html.indexOf("Class Schedule");
//            html = html.substring(index);
//            String indexing = "Your URL for the Class Schedule Subscription pilot service is ";
//            index = html.indexOf(indexing) + indexing.length();
//            String URL = html.substring(index, index + 200);
//            URL.trim();
//            URL = URL.substring(0, URL.indexOf(".ics") + 4);
//            icsURL = URL;
//            Log.d("WEB", "URL: " + URL);
//
//            index = URL.indexOf("/FU/") + 4;
//            useremail = URL.substring(index, URL.indexOf("-", index + 1));
//            useremail += "@queensu.ca";
////            setText(useremail);
//            attemptLogin();
//
//        }
//    }

    public void tryProcessHtml (String html){
        if (html == null)
            return;

        if (html.contains("Class Schedule")) {
            html = html.replaceAll("\n", "");
            int index = html.indexOf("Class Schedule");
            html = html.substring(index);
            String indexing = "Your URL for the Class Schedule Subscription pilot service is ";
            index = html.indexOf(indexing) + indexing.length();
            String URL = html.substring(index, index + 200);
            URL.trim();
            URL = URL.substring(0, URL.indexOf(".ics") + 4);
            icsURL = URL;
            Log.d("WEB", "URL: " + URL);

            index = URL.indexOf("/FU/") + 4;
            useremail = URL.substring(index, URL.indexOf("-", index + 1));
            useremail += "@queensu.ca";
            setText(useremail);
            attemptLogin();
        }
    }

    private void setText(String useremail) {

        TextView dataInfo = (TextView) findViewById(R.id.userEmail);
        dataInfo.setText(useremail);
//        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
//        mEmailView.setText(useremail);
        Log.d("WEB", "User Email: " + useremail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final WebView browser = (WebView) findViewById(R.id.webView);
        browser.getSettings().setJavaScriptEnabled(true);
//        browser.addJavascriptInterface(this, "HTMLOUT");      // Old method, worked well but would crash with certain android 5.0+ implementations (eg. samsung)
        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
//                browser.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");  // Old method, worked well but would crash with certain android 5.0+ implementations (eg. samsung)

                browser.evaluateJavascript(
                        "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String html) {
//                                Log.d("HTML", html);
                                tryProcessHtml(html);
                            }
                        });


            }
        });
        browser.loadUrl("http://my.queensu.ca/software-centre");


        // Set up the login form.
//        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
//        populateAutoComplete();

//        mPasswordView = (EditText) findViewById(R.id.password);
//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//
//        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
//        mEmailSignInButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                attemptLogin();
//            }
//        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

//    Log.d("WEB", "URL: ");
//    if (icsURL != "" && icsURL.contains(".ics")) {
//        browser.setVisibility(View.INVISIBLE);
//        browser.destroy();
//
//        setText(useremail);
//        attemptLogin();
//    }

    /**
     * auto complete email used for login if permission for contacts is gained.
     */
//    private void populateAutoComplete() {
//        if (!mayRequestContacts()) {
//            return;
//        }
//
//        getLoaderManager().initLoader(0, null, this);
//    }


    /**
     * Check for contacts permission. Used to autocomplete email.
     * Ask for permission if do not have it.
     * Returns true if have permission, false if permission is requested.
     */
//    private boolean mayRequestContacts() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        }
//        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        }
//        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
//            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok, new View.OnClickListener() {
//                        @Override
//                        @TargetApi(Build.VERSION_CODES.M)
//                        public void onClick(View v) {
//                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//                        }
//                    });
//        } else {
//            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//        }
//        return false;
//    }

    /**
     * Callback received when a permissions request has been completed.
     * If received permission, continue with auto complete of email.
     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_READ_CONTACTS) {
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete();
//            }
//        }
//    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
//        mEmailView.setError(null);
//        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
//        String email = mEmailView.getText().toString();
//        String password = mPasswordView.getText().toString();
//
//        if (useremail != "" && useremail.contains("@"))
//            email = useremail;

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }

//         int  emailValid = isEmailValid(email);

        // Check for a valid email address.
//        if (TextUtils.isEmpty(email)) {
//            mEmailView.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
//            cancel = true;
//        } else if (emailValid != 0) {
//            if (emailValid == -1)
//                mEmailView.setError(getString(R.string.error_invalid_email_queensu));
//            else
//                mEmailView.setError(getString(R.string.error_invalid_email));
//
//            focusView = mEmailView;
//            cancel = true;
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
//            String netid = email.split("@")[0]; //take everything before the email starts
            // this is the net ID
            mAuthTask = new UserLoginTask(useremail, "", this);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * checks if email is of valid format.
     * TODO possibly verify email is an actual email
     *
     * @param email string the user used as an email to log in
     * @return 0 if the email has @queensu.ca and has at least 4 characters before that
     * 1 if the email has less than 4 before @queensu.ca
     * -1 if the email does not contain @queensu.ca
     */
    private int isEmailValid(String email) {
        //TODO: Replace this with your own logic
        if (email.contains("@queensu.ca"))
            if (email.length() > 15) return 0;
            else return 1;
        return -1;
    }

    /**
     * check to see if the user-entered password is acceptable
     *
     * @param password string the user entered as their password
     * @return true if valid, false if not valid
     */
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

//        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }


//    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
//        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
////        ArrayAdapter<String> adapter =
////                new ArrayAdapter<>(LoginActivity.this,
////                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
////
////        mEmailView.setAdapter(adapter);
//    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String netid;
        private final String mPassword;
        private UserManager mUserManager;

        UserLoginTask(String netid, String password, Context context) {
            this.mUserManager = new UserManager(context);
            this.netid = netid;
            this.mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            //TODO as of now just adding new users into database
            User userInDB = mUserManager.getRow(netid);
            if (userInDB == null) {
                User newUser = new User(netid, "", "", "", icsURL); //TODO ask for their name
                mUserManager.insertRow(newUser);
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(true);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());    // Get the default SharedPreferences context

            if (success) {

                SharedPreferences.Editor editor = preferences.edit();                                           // Allow for editing the preferences
                editor.putString("UserEmail", netid + "@queensu.ca");                                                          // Create a string called "UserEmail" equal to mEmail
                editor.apply();                                                                                 // Save changes


                // DO LOGIC FOR GENERATING ICS FILE HERE....
                if (icsURL != "" && icsURL.contains(".ics")) {
                    editor.putString("icsURL", icsURL);   // Create a string called "icsURL" to point to the ICS URL on SOLUS
                    editor.apply();
                } else {
                    editor.putString("icsURL", "https://mytimetable.queensu.ca/timetable/FU/14ar75-FUAWK2B34DKLKILZENGTK7DC7OFGY37RGCGSZVTWMNONMAPQ437Q.ics");   // Create a string called "icsURL" to point to the ICS URL on SOLUS
                    editor.apply();
                }

                if (preferences.getString("DatabaseDate", "noData") != "noData")                    // if the database is up to date
                {

                } else {
                    final downloadICS downloadICS = new downloadICS(LoginActivity.this);
                    String url = preferences.getString("icsURL", "noURL");
                    if (url != "noURL") {
                        Log.d(TAG, "PAY ATTENTION _________________________________________________________________________________________________________________________________________________________________________________!");
                        downloadICS.execute(preferences.getString("icsURL", "noURL"));
                        Log.d(TAG, "done!");

                    }
                }
                // replace later with actual logic code
                try {
                    // Simulate network access.
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }


                startActivity(new Intent(LoginActivity.this, MainTabActivity.class));
            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


}


