import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.Category;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.GroundItem;

@ScriptManifest(author = "You", name = "My First Script", version = 1.0, description = "Simple Tea Thiever", category = Category.THIEVING)
public class goblinKiller extends AbstractScript {
    Area killArea = new Area(3235, 3295, 3225, 3300);
    public static final String BRONZE_AXE = "Bronze axe";
    public static final String CHICKEN = "Chicken";
    GroundItem Feather = getGroundItems().closest("Feather");
    public static final Filter<NPC> CHICKEN_FILTER = new Filter<NPC>() {
        @Override
        public boolean match(NPC npc) {
            if (npc == null)
            {
                return false;
            }
            return npc.getName().equals(CHICKEN) && !npc.isHealthBarVisible();
        }
    };
    public void onStart() {

    }

    public void onExit() {

    }

    @Override
    public int onLoop() {
        if (getLocalPlayer().isInCombat())
        {
            //do nothing
        }
        else if (killArea.contains(getLocalPlayer()))
        {
            if (getEquipment().isSlotEmpty(EquipmentSlot.WEAPON.getSlot()))
            {
                if(getInventory().contains(BRONZE_AXE))
                    getInventory().interact(BRONZE_AXE, "Weild");
                else
                {
                    stop();
                    return -1;
                }
            }
            if (Feather != null)
            {
                if(!getLocalPlayer().isAnimating())
                {
                    if (Feather.interact("Take"))
                    {
                        sleepUntil(() -> !Feather.exists(), 15000);
                    }
                }
            }
            NPC chicken = getNpcs().closest(CHICKEN_FILTER);
            if (chicken != null)
            {
                chicken.interact("Attack");
            }

        }
        else
        {
            getWalking().walk(killArea.getRandomTile());
        }


        return Calculations.random(550, 610);
    }
}