package com.kedior.plugin.tools.shooter;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;


public class Flyer implements Shooter {
  @Override
  public Material getType() {
    return Material.BONE;
  }

  @Override
  public boolean identify(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) return false;
    Map<Enchantment, Integer> enchants = itemMeta.getEnchants();
    return enchants.get(Enchantment.ARROW_FIRE).equals(1);
  }

  @Override
  public void shoot(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    World world = player.getWorld();
    Location loc = player.getLocation().add(0, 1.5, 0);
    Trident trident = (Trident) world.spawnEntity(loc, EntityType.TRIDENT, false);
    trident.addPassenger(player);
    Vector speed = loc.getDirection().normalize().multiply(2);
    trident.setVelocity(speed);
  }
}
