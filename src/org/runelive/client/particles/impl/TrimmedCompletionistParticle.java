package org.runelive.client.particles.impl;

import org.runelive.client.particles.Particle;
import org.runelive.client.particles.Position;

public final class TrimmedCompletionistParticle extends Particle {

	public TrimmedCompletionistParticle() {
		this.Z(new Position(0, -1, 0));
		this.getImage(new Position(0, -1, 0));
		this.getImage(19);
		this.setRGB(16767232);
		this.I(1);
		this.Z(1.5F);
		this.getImage(0.0F);
		this.I(0.075F);
		this.K();
		this.B(0);
	}

}