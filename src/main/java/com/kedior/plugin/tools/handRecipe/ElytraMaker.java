package com.kedior.plugin.tools.handRecipe;

import com.kedior.plugin.tools.Usable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ElytraMaker implements Usable {
  @Override
  public Material getType() {
    return Material.NETHER_STAR;
  }

  @Override
  public boolean identify(ItemStack itemStack) {
    return itemStack.getAmount() == 1;
  }

  @Override
  public void onUse(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    PlayerInventory inventory = player.getInventory();
    ItemStack offHand = inventory.getItemInOffHand();
    if (
        offHand.getType().equals(Material.PHANTOM_MEMBRANE)
            && offHand.getAmount() == 64
    ) {
      inventory.setItemInOffHand(null);
      inventory.setItemInMainHand(new ItemStack(Material.ELYTRA, 1));
      player.sendRawMessage("成功合成鞘翅！");
    }
  }
}
