package com.kedior.plugin.tools.shooter;

import com.kedior.plugin.tools.Damager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public abstract class AbstractGun implements Shooter, Damager{
  private float damage;
  private float bulletSpeed;
  private String name;
  private Material type;

  @Override
  public void shoot(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    World world = player.getWorld();
    Location loc = player.getLocation().add(0, 1.5, 0);
    Snowball snowBall = (Snowball) world.spawnEntity(loc, EntityType.SNOWBALL);
    snowBall.setShooter(player);
    Vector speed = loc.getDirection().normalize().multiply(this.getBulletSpeed());
    snowBall.setVelocity(speed);
    snowBall.setFireTicks(200);
    snowBall.setVisualFire(true);
    snowBall.setCustomName(this.getName());
    snowBall.setCustomNameVisible(false);
  }

  @Override
  public float getDamage() {
    return this.damage;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Material getType() {
    return this.type;
  }

  @Override
  public void onDamage(EntityDamageByEntityEvent event) {

  }

  public void setDamage(float damage) {
    this.damage = damage;
  }

  public float getBulletSpeed() {
    return bulletSpeed;
  }

  public void setBulletSpeed(float bulletSpeed) {
    this.bulletSpeed = bulletSpeed;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setType(Material type) {
    this.type = type;
  }
}

