package com.example.flashcardplus;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Card> mCards;
    EditText editTitle;
    EditText editContent;
    EditText editMeaning;


    CardAdapter mCardAdapter;
    ListView mListView;

    final String SAVE_KEY_CARDS = "CardData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editContent = (EditText)findViewById(R.id.editContent);
        editMeaning = (EditText)findViewById(R.id.editMeaning);



        mListView = (ListView) findViewById(R.id.listView);
        mCards = new ArrayList<Card>();
        mCards.add(new Card(R.drawable.sing,getString(R.string.sing_title),getString(R.string.sing_content),getString(R.string.sing_meaning)));
        mCards.add(new Card(R.drawable.angry,getString(R.string.angry_title),getString(R.string.angry_content),getString(R.string.angry_meaning)));
        mCards.add(new Card(R.drawable.run,getString(R.string.run_title),getString(R.string.run_content),getString(R.string.run_meaning)));
        mCards.add(new Card(R.drawable.teacher,getString(R.string.teacher_title),getString(R.string.teacher_content),getString(R.string.teacher_meaning)));



        // mCards をロードする (mCards を作る)
        load();

        mCardAdapter = new CardAdapter(this,R.layout.card, mCards);
        mListView.setAdapter(mCardAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });


    }

    public void addCard(View v) {

        Card newCard = loadInput();

        if ("".equals(newCard.content) || "".equals(newCard.title) || "".equals(newCard.meaning) ) {
            // どれかが空欄の時
            Toast.makeText(this, "入力してください", Toast.LENGTH_SHORT).show();

        } else {
            // そうじゃない時
            mCards.add(newCard);
            mCardAdapter.notifyDataSetChanged();
            save();
        }


    }

    private Card loadInput() {
        String title;
        String content;
        String meaning;

        title = editTitle.getText().toString();
        content = editContent.getText().toString();
        meaning = editMeaning.getText().toString();
        // EditTextを初期化する
        iniEditTexts();

        Card newCard = new Card(R.drawable.teacher, title, content, meaning);


        return newCard;
    }

    private void iniEditTexts() {
        editTitle.setText("");
        editContent.setText("");
        editMeaning.setText("");
    }




    private void save() {
        // mCards を保存する
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        Gson gson = new Gson();
        // card list (List<Card>型) を Gson で String 型に変換する
        String cardListString = gson.toJson(mCards);
        pref.edit().putString(SAVE_KEY_CARDS, cardListString).apply();
    }

    private void load() {
        // 保存したデータを読み込んで、 mCards に入れる

        List<Card> arrayList;
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        Gson gson = new Gson();

        // 保存した値（変換した String 型の値）を取り出す
        String cardListString = pref.getString(SAVE_KEY_CARDS, "");

        // データを元の形 (List<Card>) に戻す
        arrayList = gson.fromJson(cardListString, new TypeToken<List<Card>>(){}.getType());

        // データを読み込んでなにもデータが無い時は、空のリストを作る
        if (arrayList == null) arrayList = new ArrayList<>();

        // 読み込んだデータを Activity で使っている mCards に入れ直す
        mCards = arrayList;

    }

    SharedPreferences sharedPreferences;



}

/**

 // 文字列の比較
 // "A" と "B" を比較する¥]:
 // "A".equals("B")
 https://www.javadrive.jp/start/string/index4.html

 **/