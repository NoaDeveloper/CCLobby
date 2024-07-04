package fr.nozkay;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Utils {
    public static ItemStack getItem(Material m, String name, List<String> lore){
        ItemStack it = new ItemStack(m,1);
        ItemMeta im = it.getItemMeta();
        im.setDisplayName(name);
        if(lore != null){im.setLore(lore);}
        im.addEnchant(Enchantment.ARROW_DAMAGE,1,true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        it.setItemMeta(im);
        return it;
    }

    public static ItemStack createTintedGlass(DyeColor color, String name, List<String> lore) {
        ItemStack tintedGlass = new ItemStack(Material.STAINED_GLASS, 1, color.getData());
        ItemMeta meta = tintedGlass.getItemMeta();
        if (meta != null) {
            if(name != null) {meta.setDisplayName(name);}
            if(lore != null){meta.setLore(lore);}
            tintedGlass.setItemMeta(meta);
        }

        return tintedGlass;
    }
}
