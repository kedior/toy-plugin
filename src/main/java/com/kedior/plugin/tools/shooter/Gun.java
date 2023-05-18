package com.kedior.plugin.tools.shooter;

import com.kedior.plugin.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import com.kedior.plugin.annotations.Init;
import com.kedior.plugin.annotations.NewRecipe;
import com.kedior.plugin.tools.DamageListener;
import com.kedior.plugin.tools.UsingListener;

import java.util.ArrayList;
import java.util.Map;

public enum Gun {
  POPPYGUN(new AbstractGun() {
    @Override
    public boolean identify(ItemStack itemStack) {
      Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();
      if (!enchantments.containsKey(Enchantment.ARROW_DAMAGE)) return false;
      ItemMeta itemMeta = itemStack.getItemMeta();
      if (itemMeta == null) return false;
      String displayName = itemMeta.getDisplayName();
      return displayName.contains("神圣玫瑰之枪");
    }
  }) {

    @Override
    public void initialize() {
      this.abstractGun.setName(this.name());
      this.abstractGun.setDamage(7);
      this.abstractGun.setBulletSpeed(4);
      this.abstractGun.setType(Material.POPPY);
    }
  };

  public final AbstractGun abstractGun;

  Gun(AbstractGun abstractGun) {
    this.abstractGun = abstractGun;
    this.initialize();
    DamageListener.registerDamage(this.abstractGun);
    UsingListener.registerUsable(this.abstractGun);
  }
  abstract void initialize();

  @Init
  public static void GunInit() {
    Gun.values();
  }

  @NewRecipe
  public static Recipe makePoppyGan() {
    ItemStack target = new ItemStack(Material.POPPY, 1);
    ItemMeta itemMeta = target.getItemMeta();
    itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
    itemMeta.setDisplayName("§6神圣玫瑰之枪");
    ArrayList<String> lore = new ArrayList<>();
    lore.add("§4感受玫瑰的力量！");
    lore.add("");
    lore.add("");
    lore.add("§3右键发射子弹(每发7伤害)");
    itemMeta.setLore(lore);
    target.setItemMeta(itemMeta);
    ShapedRecipe recipe = new ShapedRecipe(
        new NamespacedKey(Main.instance, "poppy_gan"),
        target
    );
    recipe.shape("dgd", "gag", "dgd");
    recipe.setIngredient('a', Material.POPPY);
    recipe.setIngredient('d', Material.DIAMOND);
    recipe.setIngredient('g', Material.GOLD_BLOCK);
    return recipe;
  }
}
