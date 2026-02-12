package cc.sighs.gb_jMahjong;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FanBasicTest {
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

    @Test
    public void testQingyiseQiduiExample() {
        Handtiles ht = new Handtiles();
        int ret = ht.stringToHandtiles("1122559mNNCCCC9m|EE1000");
        assertEquals(0, ret);
        Fan fan = new Fan();
        fan.countFan(ht);
        List<Fan.FanType> res = collectFanTypes(fan);
        int totalFan = fan.totFanRes;
        boolean hasQidui = res.contains(Fan.FanType.FAN_QIDUI);
        assertTrue(hasQidui);
        assertTrue(totalFan >= Fan.FAN_SCORE[Fan.FanType.FAN_QIDUI.ordinal()]);
    }
}
