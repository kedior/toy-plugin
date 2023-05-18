package com.kedior.plugin.tools.shooter;

import com.kedior.plugin.tools.Damager;
import com.kedior.plugin.tools.Usable;
import org.bukkit.event.player.PlayerInteractEvent;

public interface Shooter extends Usable {
  void shoot(PlayerInteractEvent event);

  @Override
  default void onUse(PlayerInteractEvent event) {
    shoot(event);
  }
}