package net.airgame.bukkit.fix.listener.villager;

import org.bukkit.Material;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.List;

public class ButcherListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onVillagerAcquireTrade(VillagerAcquireTradeEvent event) {
        if (Math.random() > 0.25) {
            return;
        }
        AbstractVillager entity = event.getEntity();
        if (!(entity instanceof Villager)) {
            return;
        }
        Villager villager = (Villager) entity;
        if (villager.getProfession() != Villager.Profession.BUTCHER) {
            return;
        }

        MerchantRecipe oldRecipe = event.getRecipe();
        List<ItemStack> list = oldRecipe.getIngredients();
        int minAmount;
        int maxAmount;

        switch (list.get(0).getType()) {
            case DRIED_KELP_BLOCK: {
                minAmount = 2;
                maxAmount = 6;
                break;
            }
            case SWEET_BERRIES: {
                minAmount = 1;
                maxAmount = 3;
                break;
            }
            default: {
                return;
            }
        }

        MerchantRecipe newRecipe = new MerchantRecipe(
                new ItemStack(Material.LEATHER),
                oldRecipe.getUses(),
                oldRecipe.getMaxUses(),
                oldRecipe.hasExperienceReward(),
                oldRecipe.getVillagerExperience(),
                oldRecipe.getPriceMultiplier()
        );
        newRecipe.addIngredient(new ItemStack(Material.EMERALD, minAmount + (int) (Math.random() * maxAmount)));
        event.setRecipe(newRecipe);
    }
}
