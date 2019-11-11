package tw.org.iii.iiiandroid004;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private  String answer;
    private int dig = 3 ,temp=-1;
    private EditText input;
    private TextView log;
    private int counter;
    private long lastTime = 0;


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
    AlertDialog alertDialog = new AlertDialog.Builder(this)
            .setMessage("Exit?")
            .setCancelable(false)//不可以點旁邊
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override//選擇命令介面 要離開或不離開
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .setNegativeButton("Cancel", null)
            .create();
        alertDialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("brad","onDestory");
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(System.currentTimeMillis() - lastTime>3*1000){
           lastTime = System.currentTimeMillis();
            Toast.makeText(this,"back one more",Toast.LENGTH_SHORT).show();

        }else {
            super.onBackPressed();
        }

        Log.v("brad","onbackPress");

    }

    @Override
    public void finish() {
        super.finish();
        Log.v("brad","finish");
    }


    public void setting(View view) {
        String[] items ={"3","4","5","6"}; //宣告同時進行初始化 就不用new
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("select game mode")

                .setSingleChoiceItems(items, dig-3, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        temp= which;
                        Log.v("brad","which1"+ which);
                    }
                })

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dig=temp+3;
                        newGame(null);//遊戲初始化
                        Log.v("brad","which2"+ which);

                    }
                })
                .create();
        alertDialog.show();
    }

    //產生新遊戲--要講計次變成0 清空輸入跟創造新答案
    public void newGame(View view) {
        //Log.v("brad","new game");
        counter=0;
        input.setText("");
        log.setText("");
        answer= creatAnswer(dig);
    }
   //猜測對應機制
    public void guess(View view) {


        counter++;
        String strInput = input.getText().toString();
        if(!isRightNumber(strInput)){

            return;
        }
        String result = checkAB(strInput);
        log.append(counter+":"+strInput + "==>" + result + "\n");
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
    private boolean isRightNumber(String g){

        return g.matches("^[0-9]{"+dig+"}$");
    }
//對話框
    private void showDialog(boolean isWinner) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(isWinner ? "WINNER" : "Loser")
                .setMessage(isWinner ? "恭喜老爺" : "謎底為" + answer)
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
//確認AB
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
//
