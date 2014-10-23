package edu.qaware.cc.rxjava;

import rx.Observable;
import rx.functions.Action1;

public class Main {

    public static void main(String[] args) {
        hello("Josef","Maria","Hias","Franz");
    }

    public static void hello(String... names) {
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("Hallo " + s);
            }
        });
    }
}
