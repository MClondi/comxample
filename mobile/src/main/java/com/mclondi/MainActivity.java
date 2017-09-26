package com.mclondi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageApi;
import com.patloew.rxwear.RxWear;
import com.patloew.rxwear.transformers.MessageEventGetDataMap;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        final RxWear rxWear = new RxWear(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxWear.message().sendDataMapToAllRemoteNodes("/MOBILE")
                        .putString("test", "test")
                        .toObservable()
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Toast.makeText(MainActivity.this, "DATA SENT", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        rxWear.message().listen("/WEAR", MessageApi.FILTER_LITERAL)
                .compose(MessageEventGetDataMap.noFilter())
                .subscribe(new Consumer<DataMap>() {
                    @Override
                    public void accept(DataMap dataMap) throws Exception {
                        Toast.makeText(MainActivity.this, "DATA OBTAINED", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
