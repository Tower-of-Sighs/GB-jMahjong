package cc.sighs.gb_jMahjong;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HandtilesTest {
    private void assertStringCase(String input, int expectedRet, String expectedStandard) {
        Handtiles ht = new Handtiles();
        int ret = ht.stringToHandtiles(input);
        assertEquals(expectedRet, ret);
        if (expectedRet == 0) {
            String outs = ht.handtilesToString();
            String option1 = expectedStandard;
            String option2 = expectedStandard + "|EE0000|";
            String option3 = expectedStandard + "|";
            assertTrue(outs.equals(option1) || outs.equals(option2) || outs.equals(option3),
                    "standard string mismatch: " + outs);
        }
    }

    @Test
    public void testStringToHandtilesBasicCases() {
        assertStringCase("[CCCC][FFFF][PPPP][NNNN]EE", 0, "[CCCC][FFFF][PPPP][NNNN]EE");
        assertStringCase("345s3p55p5567p[7777s]4p", 0, "[7777s]345s3555567p4p");
        assertStringCase("345s3pFF5567p[3333m,6]", 0, "[3333m,6]345s35567pFF ");
        assertStringCase("345s35[55567p[789m]4p", -1, "");
        assertStringCase("345s345m55567p[789m]|N1011|cbaghd", -1, "");
    }

    @Test
    public void testCalcTingExample() {
        Handtiles ht = new Handtiles();
        int ret = ht.stringToHandtiles("3344455566667m ");
        assertEquals(0, ret);
        Fan fan = new Fan();
        List<Tile> ting = fan.calcTing(ht);
        int[] expectedIds = {
                Tile.TILE_2m,
                Tile.TILE_3m,
                Tile.TILE_4m,
                Tile.TILE_5m,
                Tile.TILE_7m,
                Tile.TILE_8m
        };
        assertEquals(expectedIds.length, ting.size());
        for (int i = 0; i < expectedIds.length; i++) {
            assertEquals(expectedIds[i], ting.get(i).getId());
        }
    }
}
