package com.wechat.main.util.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: LiSir
 * @Description:com.wechat.main.util.poker
 * @Date: Create in 17:43 2020/5/6
 */
public class MakePoker {

    public static String makePoker(){

        //准备花色
        ArrayList<String> color = new ArrayList<String>();
        color.add("♠");
        color.add("♥");
        color.add("♦");
        color.add("♣");

        //准备数字
        ArrayList<String> number = new ArrayList<String>();
        Collections.addAll(number,"3","4","5","6","7","8","9","10","J","Q","K","A","2");

        //定义一个map集合：用来将数字与每一张牌进行对应
        HashMap<Integer, String> map = new HashMap<Integer, String>();

        int index = 0;
        for (String thisNumber : number) {
            for (String thisColor : color) {
                map.put(index++, thisColor + thisNumber);
            }
        }

        //一副54张的牌 ArrayList里边为0-53的数的新牌
        ArrayList<Integer> cards = new ArrayList<Integer>();

        for (int i = 0; i <= 53; i++) {
            cards.add(i);
        }

        //洗牌
        Collections.shuffle(cards);
        String s1 = map.get(cards.get(0));
        String s2 = map.get(cards.get(1));
        String s3 = map.get(cards.get(2));
        return s1 + " " + s2 + " " + s3;
    }

    public static List<List<String>> makePoker(int num){

        //准备花色
        ArrayList<String> color = new ArrayList<String>();
        color.add("♠");
        color.add("♥");
        color.add("♦");
        color.add("♣");

        //准备数字
        ArrayList<String> number = new ArrayList<String>();
        Collections.addAll(number,"3","4","5","6","7","8","9","10","J","Q","K","A","2");

        //定义一个map集合：用来将数字与每一张牌进行对应
        HashMap<Integer, String> map = new HashMap<Integer, String>();

        int index = 0;
        for (String thisNumber : number) {
            for (String thisColor : color) {
                map.put(index++, thisColor + thisNumber);
            }
        }

        //一副54张的牌 ArrayList里边为0-53的数的新牌
        ArrayList<Integer> cards = new ArrayList<Integer>();

        for (int i = 0; i <= 51; i++) {
            cards.add(i);
        }

        //洗牌
        Collections.shuffle(cards);
        ArrayList<List<String>> list = new ArrayList<>();
        int poker = 0;
        for (int i = 0 ; i < num ;i++){
            ArrayList<String> pokers = new ArrayList<>();
            String s1 = map.get(cards.get(poker));
            String s2 = map.get(cards.get(poker+1));
            String s3 = map.get(cards.get(poker+2));
            poker += 3;
            pokers.add(s1);
            pokers.add(s2);
            pokers.add(s3);
            list.add(pokers);
        }
        return list;
    }

}
