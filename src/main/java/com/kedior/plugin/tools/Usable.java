package com.kedior.plugin.tools;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface Usable {
  Material getType();

  boolean identify(ItemStack itemStack);

  void onUse(PlayerInteractEvent event);

}
