package ultrapoulet.wifeygame.gamestate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2/10/2017.
 */

public class StoryBattles {

    private static List<StoryArea> storyBattles = new ArrayList<>();

    public static void addArea(StoryArea area){
        storyBattles.add(area);
    }

    public static List<StoryArea> getBattles(){
        return storyBattles;
    }
}
