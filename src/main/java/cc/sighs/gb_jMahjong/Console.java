package cc.sighs.gb_jMahjong;

public class Console {
    public static String packToEmojiString(Pack p) {
        StringBuilder ret = new StringBuilder();
        Tile t = p.getMiddleTile();
        int offer = p.getOffer();
        if (offer < 0) {
            offer = 0;
        }
        switch (p.getType()) {
            case Pack.PACK_TYPE_SHUNZI:
                if (offer != 0) {
                    ret.append('(');
                    ret.append(tileToEmojiString(t.getTileUsingOffset(offer - 2)));
                    ret.append(')');
                }
                for (int i = -1; i <= 1; i++) {
                    if (i != offer - 2) {
                        ret.append(tileToEmojiString(t.getTileUsingOffset(i)));
                    }
                }
                break;
            case Pack.PACK_TYPE_KEZI:
                for (int i = -1; i <= 1; i++) {
                    if (i != offer - 2) {
                        ret.append(tileToEmojiString(t));
                    } else {
                        ret.append('(');
                        ret.append(tileToEmojiString(t));
                        ret.append(')');
                    }
                }
                break;
            case Pack.PACK_TYPE_GANG:
                if (offer == 0) {
                    ret.append(tileToEmojiString(new Tile(Tile.TILE_MAJIANG)));
                    ret.append(tileToEmojiString(t));
                    ret.append(tileToEmojiString(t));
                    ret.append(tileToEmojiString(new Tile(Tile.TILE_MAJIANG)));
                } else {
                    int[] flag = new int[4];
                    for (int i = 0; i < 4; i++) {
                        if (i == offer - 1) {
                            flag[i] = 1;
                        }
                    }
                    int temp = flag[2];
                    flag[2] = flag[3];
                    flag[3] = temp;
                    offer -= 4;
                    for (int i = 0; i < 4; i++) {
                        if (i == offer - 1 || i == offer) {
                            flag[i] = 1;
                        }
                    }
                    for (int i = 0; i < 4; i++) {
                        if (flag[i] == 0) {
                            ret.append(tileToEmojiString(t));
                        } else {
                            ret.append('(');
                            ret.append(tileToEmojiString(t));
                            ret.append(')');
                        }
                    }
                }
                break;
            case Pack.PACK_TYPE_JIANG:
                ret.append(tileToEmojiString(t));
                ret.append(tileToEmojiString(t));
                break;
            case Pack.PACK_TYPE_ZUHELONG:
                for (int i = Tile.TILE_1m; i <= Tile.TILE_9p; i++) {
                    long bit = 1L << i;
                    if ((bit & p.getZuhelongBitmap()) != 0L) {
                        ret.append(tileToEmojiString(new Tile(i)));
                    }
                }
                break;
            default:
                break;
        }
        return ret.toString();
    }

    public static String tileToEmojiString(Tile t) {
        return t.utf8();
    }
}

