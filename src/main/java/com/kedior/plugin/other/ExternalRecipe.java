package com.kedior.plugin.other;

import com.kedior.plugin.Main;
import com.kedior.plugin.annotations.NewRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class ExternalRecipe {
  @NewRecipe
  public static Recipe enableStick2Wood() {
    ShapedRecipe recipe = new ShapedRecipe(
        new NamespacedKey(Main.instance, "stick_to_wood"),
        new ItemStack(Material.OAK_WOOD, 4)
    );
    recipe.shape("aaa", "aaa", "aaa");
    recipe.setIngredient('a', Material.STICK);
    return recipe;
  }
}
