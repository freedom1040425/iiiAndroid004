package tw.org.iii.iiiandroid004;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private  String answer;
    private int dig = 3;
    private EditText input;
    private TextView log;
    private int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
        log = findViewById(R.id.log);
        answer = creatAnswer(3);
        Log.v("brad",answer);

    }

    //產生謎底
    private String creatAnswer(int dig){
        LinkedList<Integer> list = new LinkedList<>();
        for (int i=0 ; i<10; i++) list.add(i); //產生0-9的數字
        Collections.shuffle(list); //洗牌
        StringBuffer sb = new StringBuffer(); // 將洗完的產生新物件
        for(int i=0 ; i<dig; i++){  //產生幾位數 不大於設定位數
            sb.append(list.get(i));
        }
        return sb.toString();
    }

    public void exit(View view) {
        creatAnswer(dig);
    }

    public void setting(View view) {
    }

    public void newGame(View view) {
        Log.v("brad","new game");
    }

    public void guess(View view) {
        counter++;
        String strInput = input.getText().toString();
        String result = checkAB(strInput);
        log.append(strInput+"==>"+result+"\n");
        input.setText("");


        //Log.v("brad","==>"+strInput);
        //log.append(strInput+"==>"+"1A1B\n");
        if (result.equals(dig+"A0B")){
            //winner
            showDialog(true);

        }else  if(counter==10){
            //loser
            showDialog(false);


        }

        input.setText("");

    }

    private void showDialog(boolean isWinnrer) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(isWinnrer ? "WINNER" : "Loser")
                .setMessage(isWinnrer ? "恭喜老爺" : "謎底為" + answer)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newGame(null);
                    }
                })
                .create();

        //AlertDialog alertDialog = null;  原本
        //AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setMessage("");
        //alertDialog = builder.create();
        alertDialog.show();
    }

    private String checkAB(String guess){
        int a, b; a = b = 0;
        for(int i=0;i<guess.length();i++){
            if(guess.charAt(i)==answer.charAt(i)){
                a++;
            }else if(answer.indexOf(guess.charAt(i))>=0){
                b++;
            }
        }
        return a + "A" + b + "B";

    }
}
