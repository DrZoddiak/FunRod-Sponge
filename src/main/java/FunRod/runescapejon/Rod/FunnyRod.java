package FunRod.runescapejon.Rod;

import java.util.Optional;

import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.entity.projectile.Snowball;
import org.spongepowered.api.entity.projectile.source.ProjectileSource;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.CollideBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.world.LocatableBlock;
import org.spongepowered.api.world.World;

@Plugin(id = "funrod-plugin", name = "FunRod-Plugin", description = "It's a blaze rod you can right click to shoot snowballs with particles friendly EULA", version = "1.0")
public class FunnyRod {

	@Listener
	public void onGameInitlization(GameInitializationEvent event) {

	}

	@Listener
	public void interactEvent(InteractItemEvent.Secondary.MainHand e, @First Player p) {
		Optional<ItemStack> stack_ = p.getItemInHand(HandTypes.MAIN_HAND);
		if (p.hasPermission("rod.rod")) {
			if (stack_.isPresent()) {
				ItemStack stack = stack_.get();
				if (stack.getType().equals(ItemTypes.BLAZE_ROD)) {
					p.launchProjectile(Snowball.class).get();
				}
			}
		}
	}

	@Listener
	public void on(CollideBlockEvent.Impact e, @First Entity entity, @Root Projectile proj) {
		ProjectileSource player = proj.getShooter();
		if (((Subject) player).hasPermission("rod.rod")) {
			Optional<ItemStack> stack_ = ((ArmorEquipable) player).getItemInHand(HandTypes.MAIN_HAND);
			if (stack_.isPresent()) {
				ItemStack stack = stack_.get();
				if (stack.getType().equals(ItemTypes.BLAZE_ROD)) {
					Optional<LocatableBlock> Snowball = e.getImpactPoint().getLocatableBlock();
					World world = entity.getWorld();
					world.spawnParticles(
							(ParticleEffect) ParticleEffect.builder().type(ParticleTypes.FIREWORKS_SPARK).build(),
							Snowball.get().getLocation().getPosition().add(0, 1, 0));
					world.spawnParticles((ParticleEffect) ParticleEffect.builder().type(ParticleTypes.FLAME).build(),
							Snowball.get().getLocation().getPosition().add(0, 1, 0));
					world.spawnParticles((ParticleEffect) ParticleEffect.builder().type(ParticleTypes.HEART).build(),
							Snowball.get().getLocation().getPosition().add(0, 1, 0));
					world.spawnParticles(
							(ParticleEffect) ParticleEffect.builder().type(ParticleTypes.ENDER_TELEPORT).build(),
							Snowball.get().getLocation().getPosition().add(0, 1, 0));
					world.playSound(SoundTypes.ENTITY_CAT_AMBIENT, Snowball.get().getLocation().getPosition(), 1);
				}
			}
		}
	}
}
