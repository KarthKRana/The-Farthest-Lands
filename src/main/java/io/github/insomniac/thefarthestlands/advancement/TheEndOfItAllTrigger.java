package io.github.insomniac.thefarthestlands.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

// <(O)>

/**
 * Advancement trigger for "The End of it All".
 * Call {@link #trigger(ServerPlayer)} from gameplay; any matching criterion instance grants.
 */
public class TheEndOfItAllTrigger extends SimpleCriterionTrigger<TheEndOfItAllTrigger.TriggerInstance> {
    /** How this trigger is saved/loaded from advancement JSON. */
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    /** Grants the advancement to {@code player} if they have this criterion listening. */
    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    /** Criterion instance in JSON. Optional {@code player} predicate is vanilla-compatible. */
    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player)
        ).apply(instance, TriggerInstance::new));
    }
}
