package aspx.ukindex.ac.brighton.httpswww.opuna2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserAreaActivity extends ActionBarActivity implements View.OnClickListener {

    Button bLogout;
    Button bUploadLink;
    Button bFindLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        bLogout = (Button) findViewById(R.id.bLogout);
        bLogout.setOnClickListener(this);

        bUploadLink = (Button) findViewById(R.id.bUploadLink);
        bUploadLink.setOnClickListener(this);

        bFindLink = (Button) findViewById(R.id.bFindLink);
        bFindLink.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
      switch(v.getId()) {
          case R.id.bLogout:
              startActivity(new Intent(UserAreaActivity.this, LoginActivity.class));
              break;
          case R.id.bUploadLink:
              startActivity(new Intent(UserAreaActivity.this, UploadActivity.class));
              break;
          case R.id.bFindLink:
              startActivity(new Intent(UserAreaActivity.this, FindMaterialActivity.class));

      }
    }

}
