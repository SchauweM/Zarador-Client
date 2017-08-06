package org.runelive.client.entity.player;

import org.runelive.Configuration;
import org.runelive.client.Client;
import org.runelive.client.FrameReader;
import org.runelive.client.List;
import org.runelive.client.cache.definition.Animation;
import org.runelive.client.cache.definition.IdentityKit;
import org.runelive.client.cache.definition.ItemDefinition;
import org.runelive.client.cache.definition.MobDefinition;
import org.runelive.client.cache.definition.SpotAnimDefinition;
import org.runelive.client.graphics.fonts.TextClass;
import org.runelive.client.io.ByteBuffer;
import org.runelive.client.renderable.Entity;
import org.runelive.client.util.SourceStopWatch;
import org.runelive.client.world.Model;

public final class Player extends Entity {

	public int[][] modifiedColors = new int[12][];
	public static List mruNodes = new List(260);
	public boolean aBoolean1699;
	private long aLong1697;
	private long aLong1718;
	public Model aModel_1714;
	public int myGender;
	public int anInt1707;
	public int anInt1708;
	public int anInt1709;
	public int anInt1711;
	public int anInt1712;
	public int anInt1713;
	public int anInt1719;
	public int anInt1720;
	public int anInt1721;
	public int anInt1722;
	public final int[] anIntArray1700;
	public int combatLevel;
	public int summoningAdd;
	public int totalLevel;
	public MobDefinition desc;
	public final int[] equipment;
	public int headIcon;
	public String name;
	public int team;
	public boolean visible;
	public int bountyHunterIcon;
	public int hintIcon;
	public boolean skulled;
	public int playerRights;
	public String title;
	public int[] compColor = new int[] {65214, 65200, 65186, 62995, 64639, 961, 5683};
	public int[] defaultColors = {65214, 65200, 65186, 62995, 64639, 961, 954, 5706, 5683, 5708};
	public SourceStopWatch compColorTimer = new SourceStopWatch();
	public Model compCape;

	public Player() {
		aLong1697 = -1L;
		aBoolean1699 = false;
		anIntArray1700 = new int[5];
		visible = false;
		equipment = new int[12];
		playerRights = 0;
	}

	@Override
	public Model getRotatedModel() {
		if (!visible) {
			return null;
		}

		Model model = method452();

		if (model == null) {
			return null;
		}

		super.height = model.modelHeight;
		model.aBoolean1659 = true;

		if (aBoolean1699) {
			return model;
		}

		if (super.gfxId != -1 && super.currentAnim != -1) {
			SpotAnimDefinition spotAnim = SpotAnimDefinition.cache[super.gfxId];
			Model model_2 = spotAnim.getModel();

			if (spotAnim.animation != null) {
				if (FrameReader.animationlist[Integer
						.parseInt(
								Integer.toHexString(spotAnim.animation.frameIDs[0]).substring(0,
										Integer.toHexString(spotAnim.animation.frameIDs[0]).length() - 4),
								16)].length == 0) {
					model_2 = null;
				}
			}

			if (model_2 != null) {
				Model model_3 = new Model(true, FrameReader.isNullFrame(super.currentAnim), false, model_2);
				int nextFrame = spotAnim.animation.frameIDs[super.nextGraphicsAnimationFrame];
				int cycle1 = spotAnim.animation.delays[super.currentAnim];
				int cycle2 = super.animCycle;
				model_3.method475(0, -super.graphicHeight, 0);
				model_3.method469();
				// .method470(spotAnim.animFrameSequence.frame2IDS[super.anInt1521]);
				model_3.interpolateFrames(spotAnim.animation.frameIDs[super.currentAnim], nextFrame, cycle1, cycle2);
				model_3.anIntArrayArray1658 = null;
				model_3.anIntArrayArray1657 = null;

				if (spotAnim.sizeXY != 128 || spotAnim.sizeZ != 128) {
					model_3.method478(spotAnim.sizeXY, spotAnim.sizeXY, spotAnim.sizeZ);
				}

				model_3.method479(64 + spotAnim.shadow, 850 + spotAnim.lightness, -30, -50, -30, true);
				Model aclass30_sub2_sub4_sub6_1s[] = { model, model_3 };
				model = new Model(aclass30_sub2_sub4_sub6_1s);
			}
		}

		if (aModel_1714 != null) {
			if (Client.loopCycle >= anInt1708) {
				aModel_1714 = null;
			}

			if (Client.loopCycle >= anInt1707 && Client.loopCycle < anInt1708) {
				Model model_1 = aModel_1714;
				model_1.method475(anInt1711 - super.x, anInt1712 - anInt1709, anInt1713 - super.y);

				if (super.turnDirection == 512) {
					model_1.method473();
					model_1.method473();
					model_1.method473();
				} else if (super.turnDirection == 1024) {
					model_1.method473();
					model_1.method473();
				} else if (super.turnDirection == 1536) {
					model_1.method473();
				}

				Model aclass30_sub2_sub4_sub6s[] = { model, model_1 };
				model = new Model(aclass30_sub2_sub4_sub6s);

				if (super.turnDirection == 512) {
					model_1.method473();
				} else if (super.turnDirection == 1024) {
					model_1.method473();
					model_1.method473();
				} else if (super.turnDirection == 1536) {
					model_1.method473();
					model_1.method473();
					model_1.method473();
				}

				model_1.method475(super.x - anInt1711, anInt1709 - anInt1712, super.y - anInt1713);
			}
		}

		model.aBoolean1659 = true;
		return model;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	public Model method452() {

		if (desc != null) {
			int currentFrame = -1;
			int nextFrame = -1;
			int cycle1 = 0;
			int cycle2 = 0;

			if (super.anim >= 0 && super.animationDelay == 0) {
				Animation animation = Animation.cache[super.anim];
				currentFrame = animation.frameIDs[super.currentAnimFrame];
				nextFrame = animation.frameIDs[super.nextAnimationFrame];
				cycle1 = animation.delays[super.currentAnimFrame];
				cycle2 = super.anInt1528;
			} else if (super.anInt1517 >= 0) {
				Animation animation = Animation.cache[super.anInt1517];
				currentFrame = animation.frameIDs[super.currentForcedAnimFrame];
				nextFrame = animation.frameIDs[super.nextIdleAnimationFrame];
				cycle1 = animation.delays[super.currentForcedAnimFrame];
				cycle2 = super.frameDelay;
			}
			Model model = desc.method164(-1, currentFrame, null, nextFrame, cycle1, cycle2);
			return model;
		}


		long l = aLong1718;
		int currentFrame = -1;
		int nextFrame = -1;
		int cycle1 = 0;
		int cycle2 = 0;
		int i1 = -1;
		int j1 = -1;
		int k1 = -1;

		if (super.anim >= 0 && super.animationDelay == 0) {
			Animation animation = Animation.cache[super.anim];
			currentFrame = animation.frameIDs[super.currentAnimFrame];
			if (super.nextAnimationFrame < animation.frameIDs.length)
				nextFrame = animation.frameIDs[super.nextAnimationFrame];
			cycle1 = animation.delays[super.currentAnimFrame];
			cycle2 = super.anInt1528;
			if (super.anInt1517 >= 0 && super.anInt1517 != super.anInt1511)
				i1 = Animation.cache[super.anInt1517].frameIDs[super.currentForcedAnimFrame];
			if (animation.leftHandItem >= 0) {
				j1 = animation.leftHandItem;
				l += j1 - equipment[5] << 40;
			}
			if (animation.rightHandItem >= 0) {
				k1 = animation.rightHandItem;
				l += k1 - equipment[3] << 48;
			}
		} else if (super.anInt1517 >= 0) {
			Animation animation = Animation.cache[super.anInt1517];
			currentFrame = animation.frameIDs[super.currentForcedAnimFrame];
			nextFrame = animation.frameIDs[super.nextIdleAnimationFrame];
			cycle1 = animation.delays[super.currentForcedAnimFrame];
			cycle2 = super.frameDelay;
		}

		Model model_1 = (Model) mruNodes.insertFromCache(l);
		/**
		 * Cause of fps bug
		 */
		int compCapeItem = equipment[1];
		if (compCapeItem >= 512 && recolorableItem(compCapeItem - 512)) {
			if(compColorTimer.elapsed(100)) {
				model_1 = null;
				compColorTimer.reset();
			} else {
				if(compCape != null) {
					model_1 = compCape;
				}
			}
		}
		if(model_1 == null)
		{
			boolean flag = false;
			for(int i2 = 0; i2 < 12; i2++)
			{
				int k2 = equipment[i2];
				if(k1 >= 0 && i2 == 3)
					k2 = k1;
				if(j1 >= 0 && i2 == 5)
					k2 = j1;
				if(k2 >= 256 && k2 < 925 && !IdentityKit.cache[k2 - 256].isBodyModelLoaded())
					flag = true;
				if(k2 >= 925 && !ItemDefinition.get(k2 - 512).method195(myGender))
					flag = true;
			}

			if(flag)
			{
				if(aLong1697 != -1L)
					model_1 = (Model) mruNodes.insertFromCache(aLong1697);
				if(model_1 == null) {
					return null;
				}
			}
		}
		if(model_1 == null)
		{
			Model aclass30_sub2_sub4_sub6s[] = new Model[12];
			int j2 = 0;
			for(int l2 = 0; l2 < 12; l2++)
			{
				int i3 = equipment[l2];
				if(k1 >= 0 && l2 == 3)
					i3 = k1;
				if(j1 >= 0 && l2 == 5)
					i3 = j1;

				if(i3 >= 256 && i3 <= 925)
				{
					Model model_3 = IdentityKit.cache[i3 - 256].getBodyModel();
					if(model_3 != null)
						aclass30_sub2_sub4_sub6s[j2++] = model_3;
				}
				if(i3 >= 925) {
					ItemDefinition def = ItemDefinition.get(i3 - 512);
					Model model_4 = ItemDefinition.get(i3 - 512).getEquippedModel(myGender);
					/*if(modifiedColors[l2] != null) {
						for (int i11 = 0; i11 < def.modifiedModelColors.length; i11++)
							model_4.method476(def.modifiedModelColors[i11], modifiedColors[l2][i11]);
					}*/
					if(model_4 != null) {
						if (def != null && recolorableItem(i3 - 512)) {
							//System.out.println("Get color model...");
							model_4 = getColorableItemModel(model_4, myGender, i3 - 512);
						}
						aclass30_sub2_sub4_sub6s[j2++] = model_4;
					}
				}
			}

			model_1 = new Model(j2, aclass30_sub2_sub4_sub6s);
			if (compCapeItem >= 512 && recolorableItem(compCapeItem - 512)) {
				compCape = model_1;
			}
			for(int j3 = 0; j3 < 5; j3++)
				if(anIntArray1700[j3] != 0) {
					model_1.method476(Client.anIntArrayArray1003[j3][0], Client.anIntArrayArray1003[j3][anIntArray1700[j3]]);
					if(j3 == 1)
						model_1.method476(Client.anIntArray1204[0], Client.anIntArray1204[anIntArray1700[j3]]);
				}

			model_1.method469();
			model_1.method478(132, 132, 132);
			model_1.method479(84, 1000, -90, -580, -90, true);
			mruNodes.removeFromCache(model_1, l);
			aLong1697 = l;
		}
		if(aBoolean1699) {
			return model_1;
		}
		Model model_2 = Model.aModel_1621;
		model_2.method464(model_1, FrameReader.isNullFrame(currentFrame) & FrameReader.isNullFrame(i1));
		if (currentFrame != -1 && i1 != -1) {
			model_2.method471(Animation.cache[super.anim].animationFlowControl, i1, currentFrame);
		} else if (currentFrame != -1 && nextFrame != -1 && Configuration.TWEENING_ENABLED) {
			model_2.interpolateFrames(currentFrame, nextFrame, cycle1, cycle2);
		} else {
			model_2.applyTransform(currentFrame);
		}
		model_2.method466();
		model_2.anIntArrayArray1658 = null;
		model_2.anIntArrayArray1657 = null;
		return model_2;
	}

	public Model getPlayerModel() {
		if (!visible) {
			return null;
		}
		if (desc != null) {
			return desc.method160();
		}
		boolean flag = false;
		for (int i = 0; i < 12; i++) {
			int j = equipment[i];
			if (j >= 256 && j < 925 && !IdentityKit.cache[j - 256].isDialogModelsLoaded()) {
				flag = true;
			}
			if (j >= 925 && !ItemDefinition.get(j - 512).dialogueModelFetched(myGender)) {
				flag = true;
			}
		}

		if (flag) {
			return null;
		}
		Model models[] = new Model[12];
		int k = 0;
		for (int l = 0; l < 12; l++) {
			int i1 = equipment[l];
			if (i1 >= 256 && i1 < 925) {

				Model model_1 = IdentityKit.cache[i1 - 256].getDialogModel();
				if (model_1 != null) {
					models[k++] = model_1;
				}
			}
			if (i1 >= 925) {
				Model model_2 = ItemDefinition.get(i1 - 512).method194(myGender);
				if (model_2 != null) {
					model_2.scale2(3);
					models[k++] = model_2;
				}
			}
		}

		Model model = new Model(k, models);
		for (int j1 = 0; j1 < 5; j1++) {
			if (anIntArray1700[j1] != 0) {
				model.method476(Client.anIntArrayArray1003[j1][0], Client.anIntArrayArray1003[j1][anIntArray1700[j1]]);
				if (j1 == 1) {
					model.method476(Client.anIntArray1204[0], Client.anIntArray1204[anIntArray1700[j1]]);
				}
			}
		}

		return model;
	}

	public boolean recolorableItem(int item) {
		return ((item == 14022) || (item == 22546) || (item == 22547));
	}

	public Model getColorableItemModel(Model model, int gender, int itemId) {
		int[] colors = null;
		if (itemId == 14022) {
			colors = new int[] {compColor[0], compColor[1], compColor[2], compColor[3], compColor[4],
					compColor[5], compColor[5], compColor[5], compColor[6], compColor[6]};
		}
		for(int i = 0; i < colors.length; i++) {
			model.setColor(Client.myPlayer.defaultColors[i], colors[i]);
		}
		return model;
	}

	public void updatePlayer(ByteBuffer stream) {
		stream.position = 0;
		myGender = stream.getUnsignedByte();//0 = male, 1 = female
		for (int i = 0; i < 7; i++) {
			compColor[i] = stream.getUnsignedShort();
		}
		headIcon = stream.getUnsignedByte();
		bountyHunterIcon = stream.getUnsignedByte();
		skulled = stream.getUnsignedShort() == 1;
		if (bountyHunterIcon > 4 && bountyHunterIcon != 255)
			bountyHunterIcon = 4;
		desc = null;
		team = 0;
		for (int partId = 0; partId < 12; partId++) {
			int k = stream.getUnsignedByte();
			if (k == 0) {
				equipment[partId] = 0;
				continue;
			}
			int i1 = stream.getUnsignedByte();
			equipment[partId] = (k << 8) + i1;
			if (partId == 0 && equipment[0] == 65535) {
				desc = MobDefinition.get(stream.getUnsignedShort());
				break;
			}
			/*if(partId == 1) {
				int length = stream.getUnsignedByte();
				if(length > 0) {
					int[] colors = new int[length];
					for(int i = 0; i < length; i++) {
						colors[i] = stream.getShort();
					}
					modifiedColors[partId] = colors;
				} else {
					modifiedColors[partId] = null;
				}
				mruNodes.unlinkAll();
				if(this == Client.myPlayer) {
					Client.getClient().updateMaxCapeColors(modifiedColors[partId]);
				}
			}*/
			if (partId == 8)
				Client.myHeadAndJaw[0] = equipment[partId] - 256;
			if (partId == 11)
				Client.myHeadAndJaw[1] = equipment[partId] - 256;
			if (equipment[partId] >= 925 && equipment[partId] - 512 < ItemDefinition.totalItems) {
				int l1 = ItemDefinition.get(equipment[partId] - 512).team;
				if (l1 != 0) {
					team = l1;
				}
			}
		}

		for (int l = 0; l < 5; l++) {
			int j1 = stream.getUnsignedByte();
			if (j1 < 0 || j1 >= Client.anIntArrayArray1003[l].length) {
				j1 = 0;
			}
			anIntArray1700[l] = j1;
		}

		super.anInt1511 = stream.getUnsignedShort();
		if (super.anInt1511 == 65535) {
			super.anInt1511 = -1;
		}
		super.anInt1512 = stream.getUnsignedShort();
		if (super.anInt1512 == 65535) {
			super.anInt1512 = -1;
		}
		super.anInt1554 = stream.getUnsignedShort();
		if (super.anInt1554 == 65535) {
			super.anInt1554 = -1;
		}
		super.anInt1555 = stream.getUnsignedShort();
		if (super.anInt1555 == 65535) {
			super.anInt1555 = -1;
		}
		super.anInt1556 = stream.getUnsignedShort();
		if (super.anInt1556 == 65535) {
			super.anInt1556 = -1;
		}
		super.anInt1557 = stream.getUnsignedShort();
		if (super.anInt1557 == 65535) {
			super.anInt1557 = -1;
		}

		super.runAnimation = stream.getUnsignedShort();
		if (super.runAnimation == 65535) {
			super.runAnimation = -1;
		}
		if(desc != null) {
			super.runAnimation = desc.walkAnimation;
			super.anInt1554 = desc.walkAnimation;
			super.anInt1511 = desc.standAnimation;
		}
		name = TextClass.fixName(TextClass.nameForLong(stream.getLong()));
		combatLevel = stream.getUnsignedByte();
		summoningAdd = stream.getUnsignedByte();
		totalLevel = stream.getUnsignedShort();
		if(Client.instance.saved_characters_usernames[0].equalsIgnoreCase(name)) {
			Client.instance.saved_total_levels[0] = ""+totalLevel;
		} else if(Client.instance.saved_characters_usernames[1].equalsIgnoreCase(name)) {
			Client.instance.saved_total_levels[1] = ""+totalLevel;
		} else if(Client.instance.saved_characters_usernames[2].equalsIgnoreCase(name)) {
			Client.instance.saved_total_levels[2] = ""+totalLevel;
		}
		playerRights = stream.getUnsignedShort();
		title = stream.getString();
		if(title.equals("null")) {
			title = null;
		}
		visible = true;
		aLong1718 = 0L;
		for (int k1 = 0; k1 < 12; k1++) {
			aLong1718 <<= 4;
			if (equipment[k1] >= 256) {
				aLong1718 += equipment[k1] - 256;
			}
		}

		if (equipment[0] >= 256) {
			aLong1718 += equipment[0] - 256 >> 4;
		}
		if (equipment[1] >= 256) {
			aLong1718 += equipment[1] - 256 >> 8;
		}
		for (int i2 = 0; i2 < 5; i2++) {
			aLong1718 <<= 3;
			aLong1718 += anIntArray1700[i2];
		}

		aLong1718 <<= 1;
		aLong1718 += myGender;
	}

	public int getRights() {
		return playerRights;
	}
}
