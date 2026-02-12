package cc.sighs.gb_jMahjong;

import java.util.List;

public class ExampleMain {
    public static void main(String[] args) {
        Handtiles ht = new Handtiles();
        ht.stringToHandtiles("[456s,1][456s,1][456s,3]45s55m |EE0000|fah");

        Fan fan = new Fan();
        List<Tile> ting = fan.calcTing(ht);
        for (Tile t : ting) {
            Print.stdPrintTile(t);
        }
        System.out.println();

        ht.setTile(new Tile(Tile.TILE_8s));
        System.out.printf("%s 是否和牌：%d%n", ht.handtilesToString(), fan.judgeHu(ht));

        ht.discardTile();
        ht.drawTile(new Tile(Tile.TILE_6s));
        System.out.printf("手牌：%s%n", ht.handtilesToString());
        fan.countFan(ht);
        System.out.printf("总番数：%d%n", fan.totFanRes);
        for (int i = 1; i < Fan.FAN_SCORE.length; i++) {
            for (List<Integer> v : fan.fanTableRes[i]) {
                System.out.printf("%s %d番", Fan.FAN_NAME[i], Fan.FAN_SCORE[i]);
                StringBuilder packString = new StringBuilder();
                for (int pid : v) {
                    packString.append(" ");
                    packString.append(Console.packToEmojiString(fan.fanPacksRes.get(pid)));
                }
                System.out.println(packString);
            }
        }
    }
}

