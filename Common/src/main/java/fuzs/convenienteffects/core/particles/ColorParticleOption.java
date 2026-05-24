package fuzs.convenienteffects.core.particles;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.FastColor;

/**
 * Copied from Minecraft 1.21.1.
 */
public record ColorParticleOption(SimpleParticleType type, int color) implements ParticleOptions {

    @Override
    public SimpleParticleType getType() {
        return this.type;
    }

    public float getRed() {
        return (float) FastColor.ARGB32.red(this.color) / 255.0F;
    }

    public float getGreen() {
        return (float) FastColor.ARGB32.green(this.color) / 255.0F;
    }

    public float getBlue() {
        return (float) FastColor.ARGB32.blue(this.color) / 255.0F;
    }

    public float getAlpha() {
        return (float) FastColor.ARGB32.alpha(this.color) / 255.0F;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeInt(this.color);
    }

    @Override
    public String writeToString() {
        return this.toString();
    }
}
