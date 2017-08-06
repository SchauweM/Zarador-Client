package org.runelive.client.graphics.rsinterface;

import org.runelive.client.RSInterface;
import org.runelive.client.graphics.fonts.TextDrawingArea;

public class AchievementsInterface extends CustomInterfaces {

	public AchievementsInterface(TextDrawingArea[] tda) {
		super(tda);
	}

	public static void Tab(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(37000);
		addText(37001, "Achievements", tda, 2, 16750623, true, true);
		addSprite(37002, 650);
		
		addSprite3(37003, 45);
		addSprite3(37004, 46);
		addText(37005, "Progress: 0/0", tda, 0, 0xffffff, true, true);
		
		addHoverButtonWSpriteLoader3(37006, 47, 186, 56, "Open Achievements Diary", 0, 37007, 1);
		addHoveredImageWSpriteLoader3(37007, 48, 186, 56, 37008);
		addText(37009, "Skilling", tda, 0, 0xffffff, true, false);
		addText(37010, "0/0", tda, 0, 0xffffff, true, false);
		
		addHoverButtonWSpriteLoader3(37011, 49, 186, 56, "Open Achievements Diary", 0, 37012, 1);
		addHoveredImageWSpriteLoader3(37012, 50, 186, 56, 37013);
		addText(37014, "Combat", tda, 0, 0xffffff, true, false);
		addText(37015, "0/0", tda, 0, 0xffffff, true, false);
		
		addHoverButtonWSpriteLoader3(37016, 51, 186, 56, "Open Achievements Diary", 0, 37017, 1);
		addHoveredImageWSpriteLoader3(37017, 52, 186, 56, 37018);
		addText(37019, "Distractions", tda, 0, 0xffffff, true, false);
		addText(37020, "0/0", tda, 0, 0xffffff, true, false);
		
		tab.totalChildren(17);
		tab.child(0, 37001, 95, 4);
		tab.child(1, 37002, 0, 21);
		
		tab.child(2, 37003, 0, 24);
		tab.child(3, 37004, 2, 26);
		tab.child(4, 37005, 95, 28);
		
		tab.child(5, 37006, 2, 55);
		tab.child(6, 37007, 2, 55);
		tab.child(7, 37009, 45, 95);
		tab.child(8, 37010, 145, 95);
		
		tab.child(9, 37011, 2, 125);
		tab.child(10, 37012, 2, 125);
		tab.child(11, 37014, 45, 165);
		tab.child(12, 37015, 145, 165);
		
		tab.child(13, 37016, 2, 195);
		tab.child(14, 37017, 2, 195);
		tab.child(15, 37019, 45, 235);
		tab.child(16, 37020, 145, 235);
	}
	
	public static void mainInterface(TextDrawingArea[] tda) {
		RSInterface rsInterface = addInterface(61000);
		
		addSprite3(61001, 53);
		addText(61002, "Achievements", tda, 2, 16750623, true, true);
		
		rsInterface.child(0, 61001, 6, 14);
		rsInterface.child(1, 61002, 20, 200);
		
	}
	
}
