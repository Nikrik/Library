package krikun.rksi.library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    public static EditText []text=new EditText[6];
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        text[0]=(EditText) findViewById(R.id.number);
        text[1]=(EditText) findViewById(R.id.autor);
        text[2]=(EditText) findViewById(R.id.name);
        text[3]=(EditText) findViewById(R.id.year);
        text[4]=(EditText) findViewById(R.id.publisher);
        text[5]=(EditText) findViewById(R.id.pages);

        Intent intent=getIntent();
        if (intent.hasExtra("number"))
        {
            text[0].setText(Integer.toString(intent.getIntExtra("number",0)));
            text[1].setText(intent.getStringExtra("autor"));
            text[2].setText(intent.getStringExtra("name"));
            text[3].setText(Integer.toString(intent.getIntExtra("year",0)));
            text[4].setText(intent.getStringExtra("publisher"));
            text[5].setText(Integer.toString(intent.getIntExtra("pages",0)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        for (int i=0;i<text.length;i++)
        {
            if (text[i].getText().toString().isEmpty())
            {
                Toast.makeText(this,getString(R.string.enter)+" "+text[i].getHint(),Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
            }
        }
        switch (item.getItemId())
        {
            case R.id.save:
                Intent intent=new Intent();
                intent.putExtra("number",Integer.parseInt(text[0].getText().toString()));
                intent.putExtra("autor",text[1].getText().toString());
                intent.putExtra("name",text[2].getText().toString());
                intent.putExtra("year",Integer.parseInt(text[3].getText().toString()));
                intent.putExtra("publisher",text[4].getText().toString());
                intent.putExtra("pages",Integer.parseInt(text[5].getText().toString()));
                Toast.makeText(this,R.string.saved,Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK,intent);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
