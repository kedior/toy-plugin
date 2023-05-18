package com.kedior.plugin.tools;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface Damager {
  float NOCHANGE = -1;
  float getDamage();

  String getName();

  void onDamage(EntityDamageByEntityEvent event);
}
