package eu.depa.browsing.stack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class About extends BaseActivity {
    @Override
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.about);
        setTitle(R.string.menu_about);
    }

    public void sendEmail(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(About.this);
        builder.setTitle(getString(R.string.send_email));
        builder.setItems(new CharSequence[]{getString(R.string.repBug),
                        getString(R.string.ask),
                        getString(R.string.suggest)},
                new DialogInterface.OnClickListener() {
                    public String thing = "";
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                thing = "REP_BUG";
                                break;
                            case 1:
                                thing = "ASK";
                                break;
                            case 2:
                                thing = "SUGGEST";
                                break;
                        }
                        String TO = "depasquale.a@tuta.io";
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", TO, null));

                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "STACK_" + thing + ": ");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "API: " + android.os.Build.VERSION.SDK_INT +
                                "\nVERSION: " + BuildConfig.VERSION_CODE + "\n\n");

                        try {
                            startActivity(emailIntent);
                        }
                        catch (android.content.ActivityNotFoundException ex){
                            Toast.makeText(About.this, getString(R.string.email_noclient), Toast.LENGTH_SHORT).show();}
                    }
                });
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            builder.setIcon(getResources().getDrawable(R.drawable.mail));
        builder.show();
    }

    public void seeSource(View view) {
        PackageManager pm = getPackageManager();
        Intent newTabIntent = pm.getLaunchIntentForPackage("eu.depa.browsing.stack");
        newTabIntent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            newTabIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        newTabIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        newTabIntent.setData(Uri.parse("http://github.com/deeepaaa/stack-web-browser/"));
        startActivity(newTabIntent);
    }
}
