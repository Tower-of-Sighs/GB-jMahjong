package cc.sighs.gb_jMahjong;

public class Print {
    public static void stdPrintTile(Tile t) {
        System.out.print(t.utf8());
    }

    public static void stdPrintHandtiles(Handtiles h) {
        int flag = 0;
        for (Pack p : h.fulu) {
            if (flag != 0) {
                System.out.print(", ");
            }
            System.out.print(Console.packToEmojiString(p));
            flag = 1;
        }
        System.out.print(" | ");
        for (Tile t : h.lipai) {
            stdPrintTile(t);
        }
        System.out.print(" | ");
        for (Tile t : h.huapai) {
            stdPrintTile(t);
        }
        System.out.println();
    }
}

