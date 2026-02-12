package cc.sighs.gb_jMahjong;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CppUnitTests {
    private void stringCase(int expectedRet, String s, String correctS, boolean flagCheck) {
        Handtiles ht = new Handtiles();
        int ret = ht.stringToHandtiles(s);
        assertEquals(expectedRet, ret);
        if (expectedRet == 0 && flagCheck) {
            String outs = ht.handtilesToString();
            String target = correctS.isEmpty() ? s : correctS;
            String option1 = target;
            String option2 = target + "|EE0000|";
            String option3 = target + "|";
            assertTrue(outs.equals(option1) || outs.equals(option2) || outs.equals(option3),
                    "standard string mismatch: " + outs + " vs " + target);
        }
    }

    private void stringCase(int expectedRet, String s, String correctS) {
        stringCase(expectedRet, s, correctS, true);
    }

    private void stringCase(int expectedRet, String s) {
        stringCase(expectedRet, s, "", true);
    }

    private void tingCase(String s, int... expectedIds) {
        Handtiles ht = new Handtiles();
        int ret = ht.stringToHandtiles(s);
        assertEquals(0, ret);
        Fan fan = new Fan();
        List<Tile> ting = fan.calcTing(ht);
        List<Integer> expected = new ArrayList<>();
        for (int id : expectedIds) {
            expected.add(id);
        }
        Collections.sort(expected);
        List<Integer> actual = new ArrayList<>();
        for (Tile t : ting) {
            actual.add(t.getId());
        }
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

    private List<Fan.FanType> collectFanTypes(Fan fan) {
        List<Fan.FanType> res = new ArrayList<>();
        for (int i = 1; i < Fan.FAN_SCORE.length; i++) {
            if (!fan.fanTableRes[i].isEmpty()) {
                for (int j = 0; j < fan.fanTableRes[i].size(); j++) {
                    res.add(Fan.FanType.values()[i]);
                }
            }
        }
        return res;
    }

    private List<Fan.FanType> sorted(List<Fan.FanType> list) {
        List<Fan.FanType> r = new ArrayList<>(list);
        r.sort(Comparator.comparingInt(Enum::ordinal));
        return r;
    }

    private void fanCase(String s, Fan.FanType[] v, Fan.FanType[] v2) {
        Handtiles ht = new Handtiles();
        int ret = ht.stringToHandtiles(s);
        assertEquals(0, ret);
        Fan fan = new Fan();
        fan.countFan(ht);
        List<Fan.FanType> res = collectFanTypes(fan);
        List<Fan.FanType> expected = Arrays.asList(v);
        expected = sorted(expected);
        List<Fan.FanType> resSorted = sorted(res);
        boolean ok = resSorted.equals(expected);
        if (!ok && v2 != null && v2.length > 0) {
            List<Fan.FanType> expected2 = sorted(Arrays.asList(v2));
            ok = resSorted.equals(expected2);
        }
        assertTrue(ok, "fan mismatch for " + s + ", got " + resSorted + " fan=" + fan.totFanRes);
    }

    private void fanCase(String s, Fan.FanType[] v) {
        fanCase(s, v, null);
    }

    @Test
    public void testStringsFromCpp() {
        stringCase(0, "[CCCC][FFFF][PPPP][NNNN]EE", "[CCCC][FFFF][PPPP][NNNN]EE");
        stringCase(0, "[345s,2]34555567p[789m]", "[345s,2][789m,1]3455556p7p");
        stringCase(0, "345s3555567p[789m]4p", "[789m,1]345s3555567p4p");
        stringCase(0, "345s3p55p5567p[789m]4p", "[789m,1]345s3555567p4p");
        stringCase(0, "345s3pFF5567p[789m]4p", "[789m,1]345s35567pFF4p");
        stringCase(0, " 345 s3pFF5 567p[789m]4p ", "[789m,1]345s35567pFF4p");
        stringCase(0, "345s345m55567p[789m]|NE1010", "[789m,1]345m345s5556p7p|NE1010");
        stringCase(0, "345s345m55567p[789m]|NE1010|5", "[789m,1]345m345s5556p7p|NE1010|abcde");
        stringCase(0, "345s345m55567p[789m]|NE1010|cbaghd", "[789m,1]345m345s5556p7p|NE1010|cbaghd");
        stringCase(0, "345s[777m,3]345m55567p|NE1010|cbaghd", "[777m,3]345m345s5556p7p|NE1010|cbaghd");
        stringCase(0, "345s[777m,3]345m55567p|NE1010|3", "[777m,3]345m345s5556p7p|NE1010|abc");
        stringCase(0, "345s[777m,3]345m55567p|NE1010|0", "[777m,3]345m345s5556p7p|NE1010|");
        stringCase(0, "345s[777m,3]345m55567p|NE1010|8", "[777m,3]345m345s5556p7p|NE1010|abcdefgh");
        stringCase(0, "345s3p55p5567p[7777s,1]4p", "[7777s,1]345s3555567p4p|EE0000|");
        stringCase(0, "345s3p55p5567p[7777s,2]4p", "[7777s,2]345s3555567p4p|EE0000|");
        stringCase(0, "345s3p55p5567p[7777s,3]4p", "[7777s,3]345s3555567p4p|EE0000|");
        stringCase(0, "345s3p55p5567p[7777s,5]4p", "[7777s,5]345s3555567p4p|EE0000|");
        stringCase(0, "345s3p55p5567p[7777s,6]4p", "[7777s,6]345s3555567p4p|EE0000|");
        stringCase(0, "345s3p55p5567p[7777s,7]4p", "[7777s,7]345s3555567p4p|EE0000|");
        stringCase(0, "345s3p55p5567p[7777s]4p|EE0100|cbaghdfe", "[7777s]345s3555567p4p|EE0100|cbaghdfe");
        stringCase(0, "345s3pFF5567p[3333m,6]", "[3333m,6]345s35567pFF ");
        stringCase(0, "345s3pFF5567pC[3333m,6]", "[3333m,6]345s35567pFFC");
        stringCase(0, "a5sghWSW[3333m,6]78m2s5s|EE0000|dc", "[3333m,6]78m25sSWWagh5s|EE0000|dc");
        stringCase(-1, "345s35[55567p[789m]4p", "");
        stringCase(-1, "34Fp5s35[55567p[789m]4p", "");
        stringCase(-1, "345s345m55567p[789m]|N1011|cbaghd", "");
        stringCase(-1, "345s345m55567p[789m]||cbaghd", "");
        stringCase(-1, "345s345m55567p[789m]|cbaghd", "");
        stringCase(-1, "345s345m55567p[789m]|NE1011|9", "");
        stringCase(-1, "345s345m55567p[789m]|NE1011|cbagjhd", "");
        stringCase(-1, "[345sF,2]34555567p[789m]", "");
        stringCase(-1, "[345,2]34555567p[789m]", "");
        stringCase(-1, "[3C45,2]34555567p[789m]", "");
        stringCase(-1, "[345s,2]34555[]567p[789m]", "");
        stringCase(-1, "345s3pFF5567p[789m,F]4p", "");
        stringCase(-1, "345s3pFF5567pC[346mC]", "");
        stringCase(-2, "345s3pFF5567pC[333m,6]", "");
        stringCase(-2, "345s3pFF5567pC[345m,5]", "");
        stringCase(-3, "345s3pFF5567pC[346m]", "");
        stringCase(-4, "345s3pFF5567pC[3456m]", "");
        stringCase(-5, "345s3pFFP5567pC[2222m]", "");
        stringCase(-5, "345s3pFFP7pC[2222m]", "");
        stringCase(-6, "24s2m3pFFP5567p[2222m]", "");
        stringCase(-6, "a5sghWSW[3333m,6]78m2s5s|EE0000|dca", "");
        stringCase(-7, "[234m,1][555m,1]567m55576p|EE1001|3", "");
        stringCase(-7, "[234m,1][555m,1]567m55566p|EE0001|3", "");
        stringCase(-7, "[234m,1][555m,1]567m55576p|EE0011|3", "");
        stringCase(-7, "[234m,1][555m,1]567m55566p|EE0100|3", "");
    }

    @Test
    public void testTingFromCpp() {
        tingCase("[CCCC][FFFF][PPPP][NNNN]E ", Tile.TILE_E);
        tingCase("19m19s19pESWNCFP ", Tile.TILE_1m, Tile.TILE_1p, Tile.TILE_1s, Tile.TILE_9m, Tile.TILE_9p, Tile.TILE_9s,
                Tile.TILE_E, Tile.TILE_S, Tile.TILE_W, Tile.TILE_N, Tile.TILE_C, Tile.TILE_F, Tile.TILE_P);
        tingCase("19m19s19pESWNNFP ", Tile.TILE_C);
        tingCase("22559m11sEESSPP ", Tile.TILE_9m);
        tingCase("47m28s369pESWCFP ", Tile.TILE_1m, Tile.TILE_5s, Tile.TILE_N);
        tingCase("28m47s369pESWCFP ", Tile.TILE_1s, Tile.TILE_5m, Tile.TILE_N);
        tingCase("28m47s369pESWCCP ", new int[]{});
        tingCase("1112345678999s ", Tile.TILE_1s, Tile.TILE_2s, Tile.TILE_3s, Tile.TILE_4s, Tile.TILE_5s, Tile.TILE_6s, Tile.TILE_7s, Tile.TILE_8s, Tile.TILE_9s);
        tingCase("[111s,1]2345678999s ", Tile.TILE_1s, Tile.TILE_2s, Tile.TILE_4s, Tile.TILE_5s, Tile.TILE_7s, Tile.TILE_8s);
        tingCase("1112345689999s ", Tile.TILE_7s);
        tingCase("1112346778999s ", Tile.TILE_5s, Tile.TILE_7s, Tile.TILE_8s);
        tingCase("3344455566667m ", Tile.TILE_2m, Tile.TILE_3m, Tile.TILE_4m, Tile.TILE_5m, Tile.TILE_7m, Tile.TILE_8m);
        tingCase("234m45s88899pEEE ", Tile.TILE_3s, Tile.TILE_6s);
        tingCase("234m35s88899pEEE ", Tile.TILE_4s);
        tingCase("234m345s8889pEEE ", Tile.TILE_7p, Tile.TILE_9p);
        tingCase("234m345s6669pEEE ", Tile.TILE_9p);
        tingCase("234m345s3399pEEE ", Tile.TILE_3p, Tile.TILE_9p);
        tingCase("34444556789pPP ", Tile.TILE_7p, Tile.TILE_P);
        tingCase("23344445m888pWW ", Tile.TILE_1m, Tile.TILE_W);
        tingCase("234m345s333pEEEE ", new int[]{});
    }

    @Test
    public void testFanFromCpp() {
        fanCase("[EEE,2][SSSS,1]WWWNN55pN|EE1000", new Fan.FanType[]{Fan.FanType.FAN_DASIXI, Fan.FanType.FAN_HUNYISE, Fan.FanType.FAN_SHUANGANKE, Fan.FanType.FAN_MINGGANG, Fan.FanType.FAN_ZIMO});
        fanCase("[EEE,2][SSSS,1]WWWNN555p|SN1000", new Fan.FanType[]{Fan.FanType.FAN_XIAOSIXI, Fan.FanType.FAN_HUNYISE, Fan.FanType.FAN_PENGPENGHU, Fan.FanType.FAN_QUANFENGKE, Fan.FanType.FAN_SHUANGANKE, Fan.FanType.FAN_MINGGANG, Fan.FanType.FAN_ZIMO});
        fanCase("[EEE,2][SSS,1]WWW78m55p9m|NN1000", new Fan.FanType[]{Fan.FanType.FAN_SANFENGKE, Fan.FanType.FAN_QUEYIMEN, Fan.FanType.FAN_ZIMO});
        fanCase("[EEE,2][SSS,1]WWW99m55p9m|NN1000", new Fan.FanType[]{Fan.FanType.FAN_SANFENGKE, Fan.FanType.FAN_PENGPENGHU, Fan.FanType.FAN_SHUANGANKE, Fan.FanType.FAN_YAOJIUKE, Fan.FanType.FAN_QUEYIMEN, Fan.FanType.FAN_ZIMO});
        fanCase("[PPP,2][FFF,3]CC66999sC|EE1000", new Fan.FanType[]{Fan.FanType.FAN_DASANYUAN, Fan.FanType.FAN_HUNYISE, Fan.FanType.FAN_PENGPENGHU, Fan.FanType.FAN_SHUANGANKE, Fan.FanType.FAN_YAOJIUKE, Fan.FanType.FAN_ZIMO});
        fanCase("[PPP,2][FFF,3]345p666sCC|EE1000", new Fan.FanType[]{Fan.FanType.FAN_XIAOSANYUAN, Fan.FanType.FAN_QUEYIMEN, Fan.FanType.FAN_DANDIAOJIANG, Fan.FanType.FAN_ZIMO});
        fanCase("[PPP,2][FFF,3]3335p999s4p|EE1000", new Fan.FanType[]{Fan.FanType.FAN_SHUANGJIANKE, Fan.FanType.FAN_YAOJIUKE, Fan.FanType.FAN_QUEYIMEN, Fan.FanType.FAN_ZIMO});
        fanCase("[EEE,2][SSS,1][FFF,3]WWNNW|WW1000", new Fan.FanType[]{Fan.FanType.FAN_XIAOSIXI, Fan.FanType.FAN_ZIYISE, Fan.FanType.FAN_QUANFENGKE, Fan.FanType.FAN_MENFENGKE, Fan.FanType.FAN_JIANKE, Fan.FanType.FAN_ZIMO});
        fanCase("EESSWWNNPPFFCC|EE1000", new Fan.FanType[]{Fan.FanType.FAN_ZIYISE, Fan.FanType.FAN_QIDUI, Fan.FanType.FAN_ZIMO});
    }
}

